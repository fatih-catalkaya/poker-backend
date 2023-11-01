package eu.ananaskirsche.pokerbackend.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.config.JavalinConfig;
import io.javalin.json.JavalinJackson;

public class JavalinConfigurationService {
    public static void getJavalinConfig(JavalinConfig config){
        config.jsonMapper(new JavalinJackson(getJsonMapper()));
        config.accessManager(AuthService::manage);
    }

    public static ObjectMapper getJsonMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
