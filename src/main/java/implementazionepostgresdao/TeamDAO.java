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

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
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

    public void caricaTeamNelDB(Team team, Utente utente) {
        String sql = "INSERT INTO team (nome,mediaVoti,Hackathon_id) VALUES (?,?,?) RETURNING id";
        String utenteSql = "UPDATE utente SET team_id = ?,hackathon_id=? WHERE username = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql);
             PreparedStatement ustmt = con.prepareStatement(utenteSql)) {

            stmt.setString(1, team.getNome());
            stmt.setDouble(2, team.getMediaVoti());
            stmt.setInt(3, team.getHackathonID());
            ResultSet rs=stmt.executeQuery();

            if (rs.next()) {
                int generatedId = rs.getInt(1);
                team.setId(generatedId);
                utente.setTeamID(generatedId);
            } else {
                throw new SQLException("Inserimento team fallito, nessun ID restituito.");
            }
            ustmt.setInt(1, team.getId());
            ustmt.setInt(2,team.getHackathonID());
            ustmt.setString(3,utente.getUsername());

            utente.setTeamID(team.getId());
            utente.setHackathonID(team.getHackathonID());

            ustmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

}

@Override
public List<Utente> membriTeam(Integer id){
    String sql = "SELECT nome,cognome,email,username,password FROM utente WHERE team_id = ?";
    List<Utente> membri = new ArrayList<>();
    try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
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
    String sql = "UPDATE utente SET team_id = NULL, hackathon_id = NULL WHERE username = ?";
    try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

@Override
public List<Team> getTeamByHackathon(Integer id) {
    String sql = "SELECT * FROM team WHERE hackathon_id = ?";
    List<Team> membri = new ArrayList<>();
    try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Team t = new Team(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("mediavoti"),
                    rs.getInt("hackathon_id")
            );
            membri.add(t);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return membri;

}

public Integer getHackathonByTeam(Integer id) {
    String sql = "SELECT hackathon_id FROM team WHERE id = ?";
    List<Team> membri = new ArrayList<>();
    try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("hackathon_id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

@Override
public void caricaAggiornamentoDB(Team team, Aggiornamento agg) {

    String sql = "INSERT INTO aggiornamento (nome, documento) VALUES (?,?) RETURNING id";

    try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql);){

        stmt.setString(1, agg.getNome());
        stmt.setString(2, agg.getDocumento());

        ResultSet rs=stmt.executeQuery();

        if (rs.next()) {
            int generatedId = rs.getInt(1);
            agg.setIdAggiornamento(generatedId);
        } else
            throw new SQLException("Inserimento aggiornamento fallito, nessun ID restituito.");


    } catch (SQLException e) {
        e.printStackTrace();
    }


}

} //Modificato - Gabriele (modifica cosi da restituire solo i team di un hackathon)
