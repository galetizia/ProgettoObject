package implementazionepostgresdao;
import dao.ITeamDAO;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO implements ITeamDAO {
    public TeamDAO() {/* Costruttore vuoto perchè l'oggetto DAO non ha bisogno di campi da assegnare alla creazione*/}

    @Override
    public Team getTeamByID(Integer id){
        String sql = "SELECT id,nome,mediavoti,hackathon_id FROM team WHERE id = ?";

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

    @Override
    public List<Double> getVotiPerTeam(Integer id){
        List<Double> votiPerTeam = new ArrayList<>();
        String sql = "SELECT voto FROM voti WHERE team_id = ?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                votiPerTeam.add(rs.getDouble("voto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votiPerTeam;
    }

    @Override
    public void setVotiPerTeam(Integer id, Double media){
        String sql = "UPDATE team SET mediavoti=? WHERE id = ?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDouble(1, media);
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
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
    public Integer getHackathonByTeam(Integer id) {
        String sql = "SELECT hackathon_id FROM team WHERE id = ?";
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
    public void caricaAggiornamentoDB(Utente utente, Aggiornamento agg, boolean isElaboratoFinale) {

        String checksql = "SELECT id FROM aggiornamento WHERE team_id=?";
        String updatesql = "UPDATE aggiornamento SET nome=?,documento=?,utente_username=?, isElaboratoFinale=? WHERE team_id=? RETURNING id";
        String deletesql = "DELETE FROM commenti WHERE team_id=?";
        String insertsql = "INSERT INTO aggiornamento (nome, documento,team_id,utente_username, isElaboratoFinale) VALUES (?,?,?,?,?) RETURNING id";

            try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement checkstmt = con.prepareStatement(checksql);
                 PreparedStatement insertstmt = con.prepareStatement(insertsql); PreparedStatement updatestmt = con.prepareStatement(updatesql);
                 PreparedStatement deletestmt = con.prepareStatement(deletesql)) {

                checkstmt.setInt(1, agg.getTeamID());
                ResultSet rs = checkstmt.executeQuery();

                if (rs.next()) {
                    updatestmt.setString(1, agg.getNome());
                    updatestmt.setString(2, agg.getDocumento());
                    updatestmt.setString(3, agg.getUsernameUtente());
                    updatestmt.setBoolean(4, isElaboratoFinale);
                    updatestmt.setInt(5, agg.getTeamID());

                    ResultSet updateRs = updatestmt.executeQuery();
                    if (updateRs.next()) {
                        agg.setIdAggiornamento(updateRs.getInt(1));
                    }

                    deletestmt.setInt(1, agg.getTeamID());
                    deletestmt.executeUpdate();

                } else {
                    insertstmt.setString(1, agg.getNome());
                    insertstmt.setString(2, agg.getDocumento());
                    insertstmt.setInt(3, agg.getTeamID());
                    insertstmt.setString(4, agg.getUsernameUtente());
                    insertstmt.setBoolean(5, isElaboratoFinale);

                    ResultSet insertRs = insertstmt.executeQuery();
                    if (insertRs.next()) {
                        agg.setIdAggiornamento(insertRs.getInt(1));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    public String getUltimoAggiornamento(Integer id) {
        String sql = "SELECT documento FROM aggiornamento WHERE team_id=?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("documento");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getIdAggiornamentoByTeam(Integer id) {
            String sql = "SELECT id FROM aggiornamento WHERE team_id=?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean getElaboratoFinaleUltimoAggiornamento(Integer idTeam) {

        String checksql = "SELECT isElaboratoFinale FROM aggiornamento WHERE team_id=? AND isElaboratoFinale=?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(checksql)) {
            stmt.setInt(1, idTeam);
            stmt.setBoolean(2, true);

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
