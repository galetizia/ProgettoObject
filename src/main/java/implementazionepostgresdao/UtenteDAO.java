package implementazionepostgresdao;
import dao.IUtenteDAO;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import java.sql.*;

public class UtenteDAO implements IUtenteDAO {

    private Connection connection;
    public UtenteDAO() {}
    TeamDAO tdao = new TeamDAO();

    @Override
    public Utente login(String username, String password){
        String sql="SELECT * FROM utente WHERE username=? AND password=?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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

    public boolean signIn(String nome, String cognome, String email, String username, String password){
        String checksql="SELECT * FROM utente WHERE username=? OR email=?";
        String insertsql="INSERT INTO utente(nome,cognome,email,username,password) VALUES(?,?,?,?,?)";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement checkstmt = con.prepareStatement(checksql);
        PreparedStatement insertstmt = con.prepareStatement(insertsql)) {
            checkstmt.setString(1, username);
            checkstmt.setString(2, email);
            ResultSet rs = checkstmt.executeQuery();

            if (rs.next()) {
                return false;
            }

            insertstmt.setString(1, nome);
            insertstmt.setString(2, cognome);
            insertstmt.setString(3, email);
            insertstmt.setString(4, username);
            insertstmt.setString(5, password);

            return (insertstmt.executeUpdate() > 0);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


