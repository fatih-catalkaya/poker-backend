package eu.ananaskirsche.pokerbackend.service;

import eu.ananaskirsche.pokerbackend.dto.db.Player;
import eu.ananaskirsche.pokerbackend.dto.rest.CreateEditPlayerRequestDTO;
import eu.ananaskirsche.pokerbackend.repository.PlayerRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PlayerService {
    public static void createPlayer(CreateEditPlayerRequestDTO body) throws SQLException {
        Player p = new Player(UUID.randomUUID().toString(), body.getName());
        PlayerRepository.createPlayer(p);
    }

    public static List<Player> getAllPlayer() throws SQLException {
        return PlayerRepository.getAllPlayers();
    }

    public static void editPlayer(String id, String newName) throws SQLException, IllegalArgumentException {
        if(!PlayerRepository.existsPlayer(id)){
            throw new IllegalArgumentException("Player with id '%s' does not exist!".formatted(id));
        }
        Player p = new Player(id, newName);
        PlayerRepository.updatePlayer(p);
    }

    public static void deletePlayer(String id) throws SQLException {
        PlayerRepository.deletePlayer(id);
    }
}
