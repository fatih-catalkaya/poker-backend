package eu.ananaskirsche.pokerbackend.service;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MigrationService {

    private static final Logger log = LoggerFactory.getLogger(MigrationService.class.getSimpleName());

    public static void migrate(){
        String filePath = PropertiesService.getEnv().get("DB_FILE", "data/poker.db");
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:sqlite:%s".formatted(filePath), "", "")
                .load();
        flyway.migrate();
        log.info("Finished migrations!");
    }
}
