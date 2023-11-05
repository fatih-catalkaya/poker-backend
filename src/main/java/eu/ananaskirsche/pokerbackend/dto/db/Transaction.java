package eu.ananaskirsche.pokerbackend.dto.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    String id;
    String playerId;
    double amount;
    LocalDateTime createdAt;
    String comment;
}
