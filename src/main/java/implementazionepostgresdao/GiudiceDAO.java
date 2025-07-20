package implementazionepostgresdao;

import dao.IGiudiceDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiudiceDAO implements IGiudiceDAO {

    private static final String EMAIL = "email";
    private static final String USERNAME = "username";

    public GiudiceDAO() {/* Costruttore vuoto perchè l'oggetto DAO non ha bisogno di campi da assegnare alla creazione*/}
    TeamDAO tdao = new TeamDAO();

    @Override
    public Giudice login(String username, String password) {
        String sql = "SELECT nome, cognome, email, username, password, hackathon_id FROM giudice WHERE username = ? AND password = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Giudice(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString(EMAIL),
                        rs.getString(USERNAME),
                        rs.getString("password"),
                        rs.getInt("hackathon_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Giudice findGiudiceByField(String field, String value) {
        String sql = "SELECT nome, cognome, email, username, password, hackathon_id  FROM giudice WHERE " + field + " = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Giudice(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString(EMAIL),
                        rs.getString(USERNAME),
                        rs.getString("password"),
                        rs.getInt("hackathon_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Giudice findGiudiceByUsername(String username) {
        return findGiudiceByField(USERNAME, username);
    }

    @Override
    public Giudice findGiudiceByEmail(String email) {
        return findGiudiceByField(EMAIL, email);
    }

    @Override
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

    @Override
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

    @Override
    public boolean controlloVotoTeam(Integer idTeam, String giudiceID) {
        String checksql = "SELECT id FROM voti WHERE team_id=? AND giudice_id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(checksql)) {

            stmt.setInt(1, idTeam);
            stmt.setString(2, giudiceID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void caricaVoto(Voto voto) {
        String insertsql = "INSERT INTO voti (team_id, voto, giudice_id) VALUES (?,?,?) RETURNING id";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement insertstmt = con.prepareStatement(insertsql)){

            insertstmt.setInt(1, voto.getTeam().getId());
            insertstmt.setInt(2, voto.getValutazione());
            insertstmt.setString(3, voto.getGiudice().getUsername());

            insertstmt.executeQuery();

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Team> getElaboratiFinaliTeam(Integer hackathonID) {
        String sql="SELECT team_id FROM aggiornamento WHERE iselaboratofinale=true";
        List<Team> teams = new ArrayList<>();

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){

            while(rs.next()) {
                int idTeam = rs.getInt("team_id");
                Team t = tdao.getTeamByID(idTeam);

                if(t!=null && t.getHackathonID().equals(hackathonID)) teams.add(t);
            }

        }catch(SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }


    public boolean isElaboratoFinale(Integer idTeam){
        String sql="SELECT id FROM aggiornamento WHERE iselaboratofinale=true AND team_id=?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setInt(1,idTeam);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return true;
            }

        }catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
