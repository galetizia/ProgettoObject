package implementazionepostgresdao;
import dao.ITeamDAO;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO implements ITeamDAO {
    private Connection connection;
    public TeamDAO() {}

    @Override
    public Team getTeamByID(Integer id){
        String sql = "SELECT * FROM team WHERE id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Team(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("mediaVoti"),
                        rs.getInt("Hackathon_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Utente> membriTeam(Integer id){
        String sql = "SELECT nome,cognome,email,username,password FROM utente WHERE team_id = ?";
        List<Utente> membri = new ArrayList<>();
        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                Utente u = new Utente(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );

                membri.add(u);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membri;
    }

    @Override
    public void rimuoviUtenteDalTeam(String username) {
        String sql = "UPDATE utente SET team_id = NULL WHERE username = ?";
        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // In produzione usa logger e gestione errori migliore
        }
    }

}//Modificato - Fabio (Rimuovi Utente dal team)
