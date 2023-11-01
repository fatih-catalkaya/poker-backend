package eu.ananaskirsche.pokerbackend.repository;

import com.zaxxer.q2o.Q2ObjList;
import eu.ananaskirsche.pokerbackend.dto.db.Player;
import eu.ananaskirsche.pokerbackend.service.DatabaseService;

import java.sql.*;
import java.util.List;

public class PlayerRepository {
    public static void createPlayer(final Player p) throws SQLException {
        try(Connection con = DatabaseService.getConnection()){
            PreparedStatement stmt = con.prepareStatement("INSERT INTO player(id, name) VALUES (?, ?)");
            stmt.setString(1, p.getId().toString());
            stmt.setString(2, p.getName());
            stmt.execute();
            stmt.close();
        }
    }

    public static boolean existsPlayer(final String uuid) throws SQLException {
        try(Connection con = DatabaseService.getConnection()){
            PreparedStatement stmt = con.prepareStatement("SELECT count(id) FROM player WHERE id = ?");
            stmt.setString(1, uuid);
            ResultSet rs = stmt.executeQuery();
            boolean result = rs.next();
            rs.close();
            stmt.close();
            return result;
        }
    }

    public static List<Player> getAllPlayers() throws SQLException{
        try(Connection con = DatabaseService.getConnection()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player ORDER BY player.name");
            List<Player> playerList = Q2ObjList.fromResultSet(rs, Player.class);
            rs.close();
            stmt.close();
            return playerList;
        }
    }

    public static void updatePlayer(Player p) throws SQLException {
        try(Connection con = DatabaseService.getConnection()){
            PreparedStatement stmt = con.prepareStatement("UPDATE player SET player.name = ? WHERE player.id = ?");
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getId().toString());
            stmt.execute();
            stmt.close();
        }
    }

    public static void deletePlayer(String id) throws SQLException{
        try(Connection con = DatabaseService.getConnection()){
            PreparedStatement stmt = con.prepareStatement("DELETE FROM player WHERE id = ?");
            stmt.setString(1, id);
            stmt.execute();
            stmt.close();
        }
    }
}
