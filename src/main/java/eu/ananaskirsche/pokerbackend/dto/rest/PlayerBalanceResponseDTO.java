package eu.ananaskirsche.pokerbackend.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerBalanceResponseDTO {
    private String playerName;
    private double balance;
}
