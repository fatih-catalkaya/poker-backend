package eu.ananaskirsche.pokerbackend.controller;

import eu.ananaskirsche.pokerbackend.dto.rest.CreateTransactionRequestDTO;
import eu.ananaskirsche.pokerbackend.service.TransactionService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class TransactionController {

    private static final Logger log = LoggerFactory.getLogger(PlayerController.class.getSimpleName());

    public static void createTransaction(Context ctx){
        try{
            CreateTransactionRequestDTO body = ctx.bodyAsClass(CreateTransactionRequestDTO.class);
            TransactionService.createTransaction(body);
            ctx.status(HttpStatus.NO_CONTENT);
        }
        catch (IllegalArgumentException ex){
            ctx.status(HttpStatus.BAD_REQUEST);
            log.error("An error occurred when creating new transaction!");
            log.error("Exception: ", ex);
        }
        catch (SQLException ex){
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("An error occurred when creating new transaction!");
            log.error("Exception: ", ex);
        }
    }
}
