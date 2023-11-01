package eu.ananaskirsche.pokerbackend.controller;

import eu.ananaskirsche.pokerbackend.dto.rest.LoginRequestDTO;
import eu.ananaskirsche.pokerbackend.service.AuthService;
import eu.ananaskirsche.pokerbackend.service.PropertiesService;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class.getSimpleName());
    private static Dotenv config;

    public static void loginController(Context ctx) {
        LoginRequestDTO body = ctx.bodyAsClass(LoginRequestDTO.class);
        if(config == null){
            config = PropertiesService.getEnv();
        }
        String configUsername = config.get("AUTH_USERNAME", "admin");
        String configPassword = config.get("AUTH_PASSWORD", "superduperpassword123");

        if (!body.getUsername().equals(configUsername) || !body.getPassword().equals(configPassword)) {
            ctx.status(HttpStatus.UNAUTHORIZED);
            return;
        }
        try{
            String jwt = AuthService.createJWT(configUsername);
            ctx.cookie("access_token", jwt, 1860).result(jwt);
        }
        catch (Exception ex){
            log.error("An error occured when accessing login controller!");
            log.error("Exception: ", ex);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void reauthController(Context ctx) {
        try{
            if(config == null){
                config = PropertiesService.getEnv();
            }
            String configUsername = config.get("AUTH_USERNAME", "admin");
            String jwt = AuthService.createJWT(configUsername);
            ctx.cookie("access_token", jwt, 1860).result(jwt);
        }
        catch (Exception ex){
            log.error("An error occured when accessing login controller!");
            log.error("Exception: ", ex);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
