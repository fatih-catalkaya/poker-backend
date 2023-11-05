package eu.ananaskirsche.pokerbackend.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequestDTO {
    private String playerId;
    private double amount;
    private boolean isWithdrawal;
    private LocalDateTime timestamp;
}
