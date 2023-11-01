package eu.ananaskirsche.pokerbackend.service;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MigrationService {

    private static final Logger log = LoggerFactory.getLogger(MigrationService.class.getSimpleName());

    public static void migrate(){
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:sqlite:poker.db", "", "")
                .load();
        MigrateResult mr = flyway.migrate();
        log.info("Finished migrations!");
    }
}
