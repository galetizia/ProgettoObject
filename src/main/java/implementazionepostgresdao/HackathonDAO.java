package implementazionepostgresdao;

import dao.IHackathonDAO;
import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.ConnessioneDatabase;

public class HackathonDAO implements IHackathonDAO {

    private Connection connection;
    public HackathonDAO() {}

    public Utente findUtenteByUsername(String username) {
        String sql = "SELECT * FROM utente WHERE username = ?";

        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Utente u = new Utente(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                u.setHackathonID(rs.getInt("hackathon_id"));
                u.setTeamID(rs.getInt("team_id"));

                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Organizzatore findOrganizzatoreByUsername(String username) {
        String sql = "SELECT * FROM organizzatore WHERE username = ?";

        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Organizzatore o = new Organizzatore(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                o.setHackathonID(rs.getInt("hackathon_id"));

                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utente findUtenteByEmail(String email) {
        String sql = "SELECT * FROM utente WHERE email = ?";

        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Utente u = new Utente(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                u.setHackathonID(rs.getInt("hackathon_id"));
                u.setTeamID(rs.getInt("team_id"));

                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Organizzatore findOrganizzatoreByEmail(String email) {
        String sql = "SELECT * FROM organizzatore WHERE email = ?";

        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Organizzatore o = new Organizzatore(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                o.setHackathonID(rs.getInt("hackathon_id"));

                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getMaxDimTeam(Integer ID) {

        String sql = "SELECT max_dim_team FROM hackathon WHERE id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
               return rs.getInt("max_dim_team");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


}
