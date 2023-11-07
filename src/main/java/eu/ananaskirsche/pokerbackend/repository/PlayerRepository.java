package eu.ananaskirsche.pokerbackend.repository;

import eu.ananaskirsche.pokerbackend.dto.db.Player;
import eu.ananaskirsche.pokerbackend.service.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {
    public static void createPlayer(final Player p) throws SQLException {
        try(Connection con = DatabaseService.getConnection()){
            PreparedStatement stmt = con.prepareStatement("INSERT INTO player(id, name) VALUES (?, ?)");
            stmt.setString(1, p.getId());
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
            List<Player> playerList = new ArrayList<>();
            while(rs.next()){
                playerList.add(getPlayerFromResultSet(rs));
            }
            rs.close();
            stmt.close();
            return playerList;
        }
    }

    public static void updatePlayer(Player p) throws SQLException {
        try(Connection con = DatabaseService.getConnection()){
            PreparedStatement stmt = con.prepareStatement("UPDATE player SET name = ? WHERE id = ?");
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getId());
            stmt.execute();
            stmt.close();
        }
    }

    public static void deletePlayer(String id) throws SQLException{
        try(Connection con = DatabaseService.getConnection()){
            PreparedStatement stmt = con.prepareStatement("DELETE FROM transactions WHERE player_id = ?");
            stmt.setString(1, id);
            stmt.execute();
            stmt.close();
            stmt = con.prepareStatement("DELETE FROM player WHERE id = ?");
            stmt.setString(1, id);
            stmt.execute();
            stmt.close();
        }
    }

    public static List<Player> findPlayersByName(String query) throws SQLException {
        try(Connection con = DatabaseService.getConnection()){
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM player WHERE name LIKE ? ORDER BY player.name");
            stmt.setString(1, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            List<Player> playerList = new ArrayList<>();
            while(rs.next()){
                playerList.add(getPlayerFromResultSet(rs));
            }
            rs.close();
            stmt.close();
            return playerList;
        }
    }

    private static Player getPlayerFromResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMeta = rs.getMetaData();
        final int colCount = rsMeta.getColumnCount();
        Player p = new Player();
        for(int i = 1; i <= colCount; i++){
            switch (rsMeta.getColumnName(i)){
                case "id" -> p.setId(rs.getString(i));
                case "name" -> p.setName(rs.getString(i));
            }
        }
        return p;
    }
}
