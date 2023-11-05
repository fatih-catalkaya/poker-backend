package eu.ananaskirsche.pokerbackend.repository;

import eu.ananaskirsche.pokerbackend.dto.db.Transaction;
import eu.ananaskirsche.pokerbackend.service.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionRepository {
    public static void createTransaction(Transaction transaction) throws SQLException {
        try(Connection con = DatabaseService.getConnection()){
            PreparedStatement stmt = con.prepareStatement("INSERT INTO transactions(id, player_id, amount, created_at, comment) VALUES (?,?,?,?,?)");
            stmt.setString(1, transaction.getId());
            stmt.setString(2, transaction.getPlayerId());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setTimestamp(4, Timestamp.valueOf(transaction.getCreatedAt()));
            stmt.setString(5, transaction.getComment());
            stmt.execute();
            stmt.close();
        }
    }
}
