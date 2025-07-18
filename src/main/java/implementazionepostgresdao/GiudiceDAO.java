package implementazionepostgresdao;

import dao.IGiudiceDAO;
import database.ConnessioneDatabase;
import model.Giudice;
import model.Organizzatore;

import java.sql.*;
public class GiudiceDAO implements IGiudiceDAO {

    private Connection connection;
    public GiudiceDAO() {}
    TeamDAO tdao = new TeamDAO();

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


    public void saveCommento(String commento, Integer id, Giudice giudice) {
        String inssql = "INSERT INTO commenti(commento,giudice_id,team_id,aggiornamento_id) VALUES (?,?,?,?)";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(inssql)) {
            stmt.setString(1, commento);
            stmt.setString(2, giudice.getUsername());
            stmt.setInt(3, id);
            stmt.setInt(4, tdao.getIdAggiornamentoByTeam(id));

            int r = stmt.executeUpdate();
            if (r == 0) {
                throw new SQLException("Commento non inserito.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean haCommentatoAggiornamento(Integer id,Giudice giudice) {
        String sql = "SELECT id FROM commenti WHERE giudice_id = ? AND aggiornamento_id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, giudice.getUsername());
            stmt.setInt(2, tdao.getIdAggiornamentoByTeam(id));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
