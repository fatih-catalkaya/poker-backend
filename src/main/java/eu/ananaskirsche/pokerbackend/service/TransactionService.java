package eu.ananaskirsche.pokerbackend.service;

import eu.ananaskirsche.pokerbackend.dto.db.Transaction;
import eu.ananaskirsche.pokerbackend.dto.rest.CreateTransactionRequestDTO;
import eu.ananaskirsche.pokerbackend.repository.PlayerRepository;
import eu.ananaskirsche.pokerbackend.repository.TransactionRepository;

import java.sql.SQLException;
import java.util.UUID;

public class TransactionService {
    public static void createTransaction(CreateTransactionRequestDTO dto) throws SQLException, IllegalArgumentException {
        if(!PlayerRepository.existsPlayer(dto.getPlayerId())){
            throw new IllegalArgumentException("Player with id %s does not exist!".formatted(dto.getPlayerId()));
        }
        Transaction t = new Transaction();
        t.setId(UUID.randomUUID().toString());
        t.setPlayerId(dto.getPlayerId());
        t.setAmount(dto.getAmount());
        t.setCreatedAt(dto.getTimestamp());
        t.setComment("");
        TransactionRepository.createTransaction(t);
    }
}
