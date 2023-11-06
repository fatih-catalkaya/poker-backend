package eu.ananaskirsche.pokerbackend.controller;

import eu.ananaskirsche.pokerbackend.service.StatisticsService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class StatisticsController {
    private static final Logger log = LoggerFactory.getLogger(StatisticsController.class.getSimpleName());
    public static void getPlayerBalances(Context ctx){
        try{
            var balances = StatisticsService.getPlayerBalances();
            ctx.json(balances);
        }
        catch (SQLException ex){
            log.error("An error occurred when fetching player balances!");
            log.error("Exception: ", ex);
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
