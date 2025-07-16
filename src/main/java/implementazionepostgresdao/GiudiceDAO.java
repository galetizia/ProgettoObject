package implementazionepostgresdao;

import dao.IGiudiceDAO;
import database.ConnessioneDatabase;
import model.Giudice;
import model.Organizzatore;

import java.sql.*;
public class GiudiceDAO implements IGiudiceDAO {

    private Connection connection;
    public GiudiceDAO() {}

    @Override
    public Giudice login(String username, String password) {
        String sql = "SELECT * FROM giudice WHERE username = ? AND password = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Giudice g = new Giudice(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("hackathon_id")
                );
                return g;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
