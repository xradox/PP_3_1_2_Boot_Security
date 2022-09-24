package ru.kata.spring.boot_security.demo.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.DAO.RoleDao;

import java.io.IOException;


@Component
public class RoleDeserializer extends StdDeserializer<Role> {

    @Autowired
    private RoleDao roleDao;

    public RoleDeserializer() {
        this(null);
    }

    public RoleDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Role deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        String name = node.asText();

        try {
            Role role = roleDao.findByName(name);
            return role;
        } catch(Exception e) {
            System.out.println("Error."+ e);
            return null;
        }
    }
}
