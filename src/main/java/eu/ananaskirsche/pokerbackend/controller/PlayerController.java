package eu.ananaskirsche.pokerbackend.controller;

import eu.ananaskirsche.pokerbackend.dto.rest.CreateEditPlayerRequestDTO;
import eu.ananaskirsche.pokerbackend.service.PlayerService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerController {
    private static final Logger log = LoggerFactory.getLogger(PlayerController.class.getSimpleName());

    public static void getAllPlayers(Context ctx) {
        try{
            ctx.json(PlayerService.getAllPlayer());
        } catch (Exception ex){
            log.error("An error occurred when fetching all players!");
            log.error("Exception: ", ex);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void queryPlayers(Context ctx){
        try{
            String query = ctx.queryParam("q");
            if(StringUtils.isEmpty(query)){
                ctx.json(PlayerService.getAllPlayer());
            }
            else{
                ctx.json(PlayerService.queryPlayer(query));
            }
        }
        catch (Exception ex){
            log.error("An error occurred when querying players!");
            log.error("Exception: ", ex);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void createPlayerController(Context ctx){
        try{
            CreateEditPlayerRequestDTO body = ctx.bodyAsClass(CreateEditPlayerRequestDTO.class);
            PlayerService.createPlayer(body);
            ctx.status(HttpStatus.NO_CONTENT);
        }
        catch (Exception ex){
            log.error("An error occurred when creating new player!");
            log.error("Exception: ", ex);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void editPlayerController(Context ctx) {
        CreateEditPlayerRequestDTO body = ctx.bodyAsClass(CreateEditPlayerRequestDTO.class);
        try{
            PlayerService.editPlayer(ctx.pathParam("id"), body.getName());
            ctx.status(HttpStatus.NO_CONTENT);
        }
        catch (IllegalArgumentException ex){
            ctx.status(HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            log.error("An error occurred when updating player '%s'".formatted(ctx.pathParam("id")));
            log.error("Exception: ", ex);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void deletePlayerController(Context ctx) {
        try{
            PlayerService.deletePlayer(ctx.pathParam("id"));
            ctx.status(HttpStatus.NO_CONTENT);
        }
        catch (Exception ex){
            log.error("An error occurred when deleting player '%s'".formatted(ctx.pathParam("id")));
            log.error("Exception: ", ex);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
