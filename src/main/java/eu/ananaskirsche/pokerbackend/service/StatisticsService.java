package eu.ananaskirsche.pokerbackend.service;

import eu.ananaskirsche.pokerbackend.dto.rest.PlayerBalanceResponseDTO;
import eu.ananaskirsche.pokerbackend.repository.StatisticsRepository;

import java.sql.SQLException;
import java.util.List;

public class StatisticsService {
    public static List<PlayerBalanceResponseDTO> getPlayerBalances() throws SQLException {
        return StatisticsRepository.getPlayerBalances();
    }
}
