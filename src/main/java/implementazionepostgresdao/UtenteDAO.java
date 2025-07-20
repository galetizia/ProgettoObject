package implementazionepostgresdao;
import dao.IUtenteDAO;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import java.sql.*;

public class UtenteDAO implements IUtenteDAO {

    public UtenteDAO() {}
    TeamDAO tdao = new TeamDAO();

    private Utente mapResultSetToUtente(ResultSet rs) throws SQLException {

        Utente u = new Utente(
                rs.getString("nome"),
                rs.getString("cognome"),
                rs.getString("email"),
                rs.getString("username"),
                rs.getString("password")
        );
        int hackathonId = rs.getInt("hackathon_id");
        if (rs.wasNull()) u.setHackathonID(null);
        else u.setHackathonID(hackathonId);


        int teamId = rs.getInt("team_id");
        if (rs.wasNull()) u.setTeamID(null);
        else u.setTeamID(teamId);

        return u;
    }
    @Override
    public Utente login(String username, String password){
        String sql="SELECT nome, cognome, email, username, password, team_id, hackathon_id FROM utente WHERE username=? AND password=?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUtente(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Utente findUtenteByField(String field, String value) {
        String sql = "SELECT nome, cognome, email, username, password, team_id, hackathon_id  FROM utente WHERE " + field + " = ?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUtente(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utente findUtenteByUsername(String username) {
        return findUtenteByField("username", username);
    }

    @Override
    public Utente findUtenteByEmail(String email) {
        return findUtenteByField("email", email);
    }

    @Override
    public void changeIDTeam(Team team, Utente utente){
        String sql="UPDATE utente SET team_id=?, hackathon_id=? WHERE username=?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, team.getId());
            stmt.setInt(2, tdao.getHackathonByTeam(team.getId()));
            stmt.setString(3, utente.getUsername());

            utente.setTeamID(team.getId());
            utente.setHackathonID(tdao.getHackathonByTeam(team.getId()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


