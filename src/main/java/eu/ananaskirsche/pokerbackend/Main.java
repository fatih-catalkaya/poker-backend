package eu.ananaskirsche.pokerbackend;

import eu.ananaskirsche.pokerbackend.controller.AuthController;
import eu.ananaskirsche.pokerbackend.controller.PlayerController;
import eu.ananaskirsche.pokerbackend.controller.TransactionController;
import eu.ananaskirsche.pokerbackend.service.JavalinConfigurationService;
import eu.ananaskirsche.pokerbackend.service.MigrationService;
import eu.ananaskirsche.pokerbackend.service.PropertiesService;
import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class.getSimpleName());
    private static Javalin app;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(app != null){
                app.stop();
            }
        }));

        try{
            Dotenv config = PropertiesService.getEnv();
            MigrationService.migrate();

            app = Javalin.create(JavalinConfigurationService::getJavalinConfig);
            app.before(ctx -> log.debug("Received request to '{} {}'", ctx.req().getMethod(), ctx.req().getRequestURI()));
            app.routes(() -> path("api", () -> {
                path("player", () -> {
                    get("/", PlayerController::getAllPlayers, Role.USER);
                    post("/", PlayerController::createPlayerController, Role.USER);
                    put("/{id}", PlayerController::editPlayerController, Role.USER);
                    delete("/{id}", PlayerController::deletePlayerController, Role.USER);
                });
                path("transaction", () -> {
                    post("/", TransactionController::createTransaction, Role.USER);
                });
                path("auth", () -> {
                    post("login", AuthController::loginController, Role.ANONYMOUS);
                    post("reauth", AuthController::reauthController, Role.USER);
                });
            }));
            app.start(config.get("HTTP_HOST", "127.0.0.1"), Integer.parseInt(config.get("HTTP_PORT", "8080")));
        }
        catch (Exception ex){
            log.error("Failed to start backend server!");
            log.error("Exception: ", ex);
        }
    }
}