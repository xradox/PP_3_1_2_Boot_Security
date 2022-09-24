let currentUser = {
    user : {},
    update: async () => {
        currentUser.user = await fetch('/api/user')
            .then(response => response.json())
        updateHeader();
        updateCurrentUserInfo()

    },
    hasRole: (name) => {
        let result = false
        currentUser.user.roles.forEach(role => {
            if (role.name === name) {
                result = true
            }
        })
        return result
    },
    getRoleLabelList: () => {
        let roles = []
        currentUser.user.roles.forEach(role => roles.push(role.label))
        return roles
    }
}

let users = {
    userMap: new Map(),
    update: async () => {
        let userList = await fetch('/api/admin/users').then(response => response.json())
        userList.forEach(user => users.userMap.set(user.id, user))
        updateUserTable()
    },
    remove: async (id) => {
        users.userMap.delete(id)
        updateUserTable()
    },
    save: async (user) => {
        users.userMap.set(user.id, user)
        updateUserTable()
    }
}

let allRoles = {
    list: [],
    update: async () => {
        allRoles.list = await fetch('/api/admin/roles')
            .then(response => response.json())
        await updateRolesInNewUserForm()
    }
}

async function updateHeader() {
    $('#currentUserName').text(currentUser.user.username)
    $('#currentUserRoles').text(currentUser.getRoleLabelList().join(" "));
}

async function updateCurrentUserInfo() {
    let row = $('#userInfoTable tbody tr:first')
    row.find('td').eq(0).text(currentUser.user.id)
    row.find('td').eq(1).text(currentUser.user.firstname)
    row.find('td').eq(2).text(currentUser.user.lastname)
    row.find('td').eq(3).text(currentUser.user.age)
    row.find('td').eq(4).text(currentUser.user.username)
    row.find('td').eq(5).text(currentUser.getRoleLabelList().join(" "))
}

async function updateUserTable() {
    let table = $("#userTable tbody")
    table.html("")
    users.userMap.forEach(user => {
        let button_edit   = '<button class="btn btn-info user-edit-button" data-toggle="modal" data-target="#userEditModal" data-user-id="'+user.id+'">Edit</button> '
        let button_delete = '<button class="btn btn-danger user-delete-button" data-toggle="modal" data-target="#userDeleteModal" data-user-id="'+user.id+'">Delete</button>'
        let roles = []
        user.roles.forEach(role => roles.push(role.label))
        let row = "<tr>" +
            "<td>"+user.id+"</td>" +
            "<td>"+user.firstname+"</td>" +
            "<td>"+user.lastname+"</td>" +
            "<td>"+user.age+"</td>" +
            "<td>"+user.username+"</td>" +
            "<td>"+roles.join(" ")+"</td>" +
            "<td>"+button_edit+" "+button_delete+"</td>" +
            "</tr>"

        table.append(row)
    })
}

async function updateRolesInNewUserForm() {
    let select = $('#newUserForm select')
    select.html('')
    allRoles.list.forEach(role => select.append("<option value='"+role.name+"'>"+role.label))
}


$(document).ready( async function() {

    let page = location.pathname.substr(1)
    $('#v-pills-'+page+'-tab').tab('show')
    $('#v-pills-tab a').on('click', function (event) {
        event.preventDefault()
        window.history.pushState('', '', event.target.href);
        $(this).tab('show')
    })

    //Init
    await currentUser.update()
    if (currentUser.hasRole('ROLE_ADMIN')) {
        await users.update()
        await allRoles.update()
    } else {
        $('#v-pills-admin-tab').remove()
    }

    // JSON from Form
    function getJsonFromUserForm(form) {
        let fd = new FormData(form);
        let user = {roles: []};
        fd.forEach((value, key) => {
            // Reflect.has in favor of: user.hasOwnProperty(key)
            if (!Reflect.has(user, key)) {
                user[key] = value;
                return;
            }
            if (!Array.isArray(user[key])) {
                user[key] = [user[key]];
            }
            user[key].push(value);
        });
        user.enabled = (user.enabled === 'on')
        return JSON.stringify(user);
    }

    // EDIT USER MODAL
    $('body').on('click', '.user-edit-button', function () {

        let userId = Number($(this).attr('data-user-id'))
        let user = users.userMap.get(userId)

        $('#userEditForm').trigger('reset')
        $('#userEditForm #edit_id').val(user.id)
        $('#userEditForm #edit_firstname').val(user.firstname)
        $('#userEditForm #edit_lastname').val(user.lastname)
        $('#userEditForm #edit_age').val(user.age)
        $('#userEditForm #edit_username').val(user.username)
        $('#userEditForm #edit_password').val(user.password)
        let select = $('#userEditForm').find('#edit_roles')
        select.html('')
        allRoles.list.forEach(role => select.append("<option value='"+role.name+"'>"+role.label))
        user.roles.forEach(role => select.find('option[value='+role.name+']').prop('selected', true))
    })

    // DELETE USER MODAL
    $('#userTable').on('click', '.user-delete-button', function () {
        let userId = Number($(this).attr('data-user-id'))
        let user = users.userMap.get(userId)

        $('#userDeleteForm').trigger('reset')
        $('#userDeleteForm #delete_id').val(user.id)
        $('#userDeleteForm #delete_firstname').val(user.firstname)
        $('#userDeleteForm #delete_lastname').val(user.lastname)
        $('#userDeleteForm #delete_age').val(user.age)
        $('#userDeleteForm #delete_username').val(user.username)
        $('#userDeleteForm').find('#delete_roles').html('')
        user.roles.forEach(role => $('#userDeleteForm').find('#delete_roles').append('<option value="'+role.name+'">'+role.label))
    })

    // ON SUBMIT DELETE FORM
    $('#userDeleteForm').on("submit", async function(event) {
        event.preventDefault(); // return false
        let userId = Number($(this).find('#delete_id').val())

        let response = await fetch('/api/admin/users/' + userId, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        if (response.status === 200) {
            // Delete user from user table...
            await users.remove(userId)
            $('#userDeleteModal').modal('hide');
        }
    })

    // ON SUBMIT EDIT FORM
    $('#userEditForm').on("submit", async function(event) {
        event.preventDefault(); // return false
        let userId = Number($(this).find('#edit_id').val())

        let json = getJsonFromUserForm(event.currentTarget)
        let response = await fetch('/api/admin/users/'+userId, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: json
        });

        if (response.status === 200) {
            await users.save(await response.json())
            $('#userEditModal').modal('hide');
            this.reset()
        }
    })

    // ON SUBMIT NEW USER FORM
    $('#newUserForm').on("submit", async function(event) {
        event.preventDefault(); // return false

        let json = getJsonFromUserForm(event.currentTarget)
        let response = await fetch('/api/admin/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: json
        });

        if (response.status === 200) {
            await users.save(await response.json())
            $('#adminTab a[href="#nav-usertable"]').tab('show')
            this.reset()
        }
    })

});
