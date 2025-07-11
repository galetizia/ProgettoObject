package implementazionepostgresdao;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import dao.IOrganizzatoreDAO;
import java.sql.*;

public class OrganizzatoreDAO implements IOrganizzatoreDAO {

    private Connection connection;

    public OrganizzatoreDAO() {
    }

    @Override
    public Utente trovaUtentePerUsername(String username) {
        String sql = "SELECT * FROM Utente WHERE username = ?";

        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Utente(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("team_id"),
                        rs.getInt("hackathon_id")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Giudice aggiungiGiudice(String username, Hackathon h){
        Utente u = trovaUtentePerUsername(username);
        if (u == null) {
            System.out.println("User not found");
            return null;
        }

        String sql = "INSERT INTO giudice (nome,cognome,email,username,password,hackathon_id) VALUES (?,?,?,?,?,?)";

        try(Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getCognome());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getUsername());
            stmt.setString(5, u.getPassword());
            stmt.setInt(6, u.getHackathonID());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return new Giudice(u.getNome(), u.getCognome(), u.getEmail(), u.getUsername(), u.getPassword(), u.getHackathonID());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

        }
    }