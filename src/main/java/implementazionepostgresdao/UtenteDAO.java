package implementazionepostgresdao;
import dao.IUtenteDAO;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import java.sql.*;

public class UtenteDAO implements IUtenteDAO {

    public UtenteDAO() { /* Costruttore vuoto perchè l'oggetto DAO non ha bisogno di campi da assegnare alla creazione*/ }

    private static final String NOME = "nome";
    private static final String COGNOME = "cognome";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String HACKATHONID = "hackathon_id";
    private static final String TEAMID = "team_id";
    private final HackathonDAO hdao = new HackathonDAO();


    private Utente mapResultSetToUtente(ResultSet rs) throws SQLException {
        Utente u = new Utente(
                rs.getString(NOME),
                rs.getString(COGNOME),
                rs.getString(EMAIL),
                rs.getString(USERNAME),
                rs.getString(PASSWORD)
        );
        int hackathonId = rs.getInt(HACKATHONID);
        if (rs.wasNull()) u.setHackathonID(null);
        else u.setHackathonID(hackathonId);


        int teamId = rs.getInt(TEAMID);
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
        return findUtenteByField(USERNAME, username);
    }

    @Override
    public Utente findUtenteByEmail(String email) {
        return findUtenteByField(EMAIL, email);
    }

    @Override
    public void changeIDTeam(Team team, Utente utente){
        String sql="UPDATE utente SET team_id=?, hackathon_id=? WHERE username=?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, team.getId());
            stmt.setInt(2, hdao.getHackathonByTeam(team.getId()));
            stmt.setString(3, utente.getUsername());

            utente.setTeamID(team.getId());
            utente.setHackathonID(hdao.getHackathonByTeam(team.getId()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


