package eu.ananaskirsche.pokerbackend.service;

import io.github.cdimascio.dotenv.Dotenv;

public class PropertiesService {
    private static Dotenv dotenv;

    public static Dotenv getEnv() {
        if(dotenv == null){
            dotenv = Dotenv.load();
        }
        return dotenv;
    }
}
