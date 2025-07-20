package implementazionepostgresdao;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import dao.IOrganizzatoreDAO;
import java.sql.*;

public class OrganizzatoreDAO implements IOrganizzatoreDAO {

    UtenteDAO udao = new UtenteDAO();

    private static final String EMAIL = "email";
    private static final String USERNAME = "username";

    public OrganizzatoreDAO() {/* Costruttore vuoto perchè l'oggetto DAO non ha bisogno di campi da assegnare alla creazione*/}

    private Organizzatore mapResultSetToOrganizzatore(ResultSet rs) throws SQLException {
        Organizzatore o = new Organizzatore(
                rs.getString("nome"),
                rs.getString("cognome"),
                rs.getString(EMAIL),
                rs.getString(USERNAME),
                rs.getString("password")

        );
        int hackathonId = rs.getInt("hackathon_id");
        if (rs.wasNull()) o.setHackathonID(null);
        else o.setHackathonID(hackathonId);

        return o;
    }

    @Override
    public Organizzatore login(String username, String password) {
        String sql = "SELECT nome, cognome, email, username, password, hackathon_id FROM organizzatore WHERE username = ? AND password = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToOrganizzatore(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Organizzatore findOrganizzatoreByField(String field, String value) {
        String sql = "SELECT nome, cognome, email, username, password, hackathon_id  FROM organizzatore WHERE " + field + " = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToOrganizzatore(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Organizzatore findOrganizzatoreByUsername(String username) {
        return findOrganizzatoreByField(USERNAME, username);
    }

    @Override
    public Organizzatore findOrganizzatoreByEmail(String email) {
        return findOrganizzatoreByField(EMAIL, email);
    }

    @Override
    public boolean aggiungiGiudice(String username, Integer idHackathon){
        Utente u = udao.findUtenteByUsername(username);
        if (u == null || u.getTeamID()!=null) {
            return false;
        }

        String insertsql = "INSERT INTO giudice (nome,cognome,email,username,password,hackathon_id) VALUES (?,?,?,?,?,?)";
        String deletesql = "DELETE FROM utente WHERE username=?";

        try(Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement insertstmt = con.prepareStatement(insertsql);
            PreparedStatement deletestmt = con.prepareStatement(deletesql)) {

            insertstmt.setString(1, u.getNome());
            insertstmt.setString(2, u.getCognome());
            insertstmt.setString(3, u.getEmail());
            insertstmt.setString(4, u.getUsername());
            insertstmt.setString(5, u.getPassword());
            insertstmt.setInt(6, idHackathon);

            int rows = insertstmt.executeUpdate();
            if (rows > 0) {

                deletestmt.setString(1, u.getUsername());
                deletestmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public void removeUtente(String username) {
        String sql = "UPDATE utente SET hackathon_id = null, team_id = null WHERE username = ?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeGiudice(String username) {
        String selectSql = "SELECT nome, cognome, email, username, password, hackathon_id FROM giudice WHERE username = ?";
        String deleteSql = "DELETE FROM giudice WHERE username = ?";
        String insertSql = "INSERT INTO utente (nome, cognome, email, username, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement selectstmt = con.prepareStatement(selectSql); PreparedStatement deletestmt = con.prepareStatement(deleteSql);
             PreparedStatement insertstmt = con.prepareStatement(insertSql)) {
            selectstmt.setString(1, username);
            ResultSet rs = selectstmt.executeQuery();

            if(rs.next()) {
                insertstmt.setString(1, rs.getString("nome"));
                insertstmt.setString(2, rs.getString("cognome"));
                insertstmt.setString(3, rs.getString(EMAIL));
                insertstmt.setString(4, rs.getString(USERNAME));
                insertstmt.setString(5, rs.getString("password"));
                insertstmt.executeUpdate();

                deletestmt.setString(1, username);
                deletestmt.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeTeam(Integer id) {
        String sql = "DELETE FROM team WHERE id = ?";
        String updatesql = "UPDATE utente SET hackathon_id = null WHERE team_id = ?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql); PreparedStatement updatestmt = con.prepareStatement(updatesql)) {
            updatestmt.setInt(1, id);
            updatestmt.executeUpdate();

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void terminaHackathon(Integer hackathonId) {
        String deletesql = "DELETE FROM hackathon WHERE id=?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(deletesql)) {

            stmt.setInt(1, hackathonId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setClassifica(Integer id) {
        String sql = "UPDATE hackathon SET classifica_pubblicata=TRUE WHERE id=?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}