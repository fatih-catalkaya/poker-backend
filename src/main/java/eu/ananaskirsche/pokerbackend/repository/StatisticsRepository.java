package eu.ananaskirsche.pokerbackend.repository;

import eu.ananaskirsche.pokerbackend.dto.rest.PlayerBalanceResponseDTO;
import eu.ananaskirsche.pokerbackend.service.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatisticsRepository {

    public static List<PlayerBalanceResponseDTO> getPlayerBalances() throws SQLException {
        try (Connection con = DatabaseService.getConnection()) {
            final String sql = """
                     SELECT player.name, SUM(transactions.amount) AS balance
                     FROM transactions
                     LEFT JOIN player ON player.id = transactions.player_id
                     GROUP BY player_id
                     ORDER BY balance DESC
                    """;
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            List<PlayerBalanceResponseDTO> playerBalances = getPlayerBalanceListFromResultSet(rs);
            rs.close();
            stmt.close();
            return playerBalances;
        }
    }

    private static List<PlayerBalanceResponseDTO> getPlayerBalanceListFromResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMeta = rs.getMetaData();
        int colCount = rsMeta.getColumnCount();
        List<PlayerBalanceResponseDTO> playerBalances = new ArrayList<>();
        while (rs.next()) {
            PlayerBalanceResponseDTO playerBalance = new PlayerBalanceResponseDTO();
            for(int i = 1; i <= colCount; i++){
                switch (rsMeta.getColumnName(i)){
                    case "name" -> playerBalance.setPlayerName(rs.getString(i));
                    case "balance" -> playerBalance.setBalance(rs.getDouble(i));
                }
            }
            playerBalances.add(playerBalance);
        }
        return playerBalances;
    }
}
