package implementazionepostgresdao;

import dao.IHackathonDAO;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.ConnessioneDatabase;

public class HackathonDAO implements IHackathonDAO {

    private static final String NOME = "nome";
    private static final String COGNOME = "cognome";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String HACKATHONID = "hackathon_id";
    private static final String TEAMID = "team_id";
    private static final String ID = "id";
    private static final String MEDIAVOTI = "mediavoti";
    private static final String CLASSIFICA = "classifica_pubblicata";
    private static final String MAXDIMTEAM = "max_dim_team";
    private static final String MAXISCRITTI = "max_iscritti";
    private static final String DATAINIZIO = "data_inizio";
    private static final String DATAFINE = "data_fine";
    private static final String UTENTE = "utente";
    private static final String ORGANIZZATORE = "organizzatore";
    private static final String PROBLEMA = "problema";
    private static final String TITOLO = "titolo";
    private static final String SEDE = "sede";
    private static final String HACKATHON = "hackathon";
    private static final String TEAM = "team";
    private static final String AGGIORNAMENTO = "aggiornamento";

    public HackathonDAO() {/* Costruttore vuoto perchè l'oggetto DAO non lo utiliziamo con dei campi a cui assegnare i valori*/}

    private boolean signIn(String tableName, String nome, String cognome, String email, String username, String password){
        String checksql="SELECT * FROM "+ tableName +" WHERE username=? OR email=?";
        String insertsql="INSERT INTO "+tableName+"(nome,cognome,email,username,password) VALUES(?,?,?,?,?)";

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

    @Override
    public boolean signInUtente(String nome, String cognome, String email, String username, String password){
        return signIn(UTENTE,nome,cognome,email,username,password);
    }

    @Override
    public boolean signInOrganizzatore(String nome, String cognome, String email, String username, String password){
        return signIn(ORGANIZZATORE,nome,cognome,email,username,password);
    }

    private Hackathon mapResultSetToHackathon(ResultSet rs) throws SQLException {
        LocalDate dataInizio = rs.getDate(DATAINIZIO).toLocalDate();
        LocalDate dataFine = rs.getDate(DATAFINE).toLocalDate();

        Hackathon h = new Hackathon(rs.getString(TITOLO),
                rs.getString(SEDE),
                dataInizio,
                dataFine,
                rs.getString(PROBLEMA),
                rs.getInt(MAXISCRITTI),
                rs.getInt(MAXDIMTEAM)
        );
        h.setID(rs.getInt(ID));
        return h;
    }
    @Override
    public Hackathon getHackathonByID(Integer id) {
        String sql = "SELECT id,titolo,sede,problema,data_inizio,data_fine,fine_periodo_prenotazioni, max_iscritti," +
                "max_dim_team, username_organizzatore, classifica_pubblicata FROM hackathon WHERE id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToHackathon(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Hackathon> getHackathons() {

        String sql = "SELECT id,titolo,sede,problema,data_inizio,data_fine,fine_periodo_prenotazioni, max_iscritti," +
                "max_dim_team, username_organizzatore, classifica_pubblicata FROM hackathon";
        List<Hackathon> hackathons = new ArrayList<>();

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                hackathons.add(mapResultSetToHackathon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hackathons;
    }
    private int getFieldOfTable(String field, String table, String where, Integer id){
        String sql = "SELECT " + field + "FROM"+ table +" WHERE "+ where +" = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(field);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getMaxDimTeam(Integer iD) {
        return getFieldOfTable(MAXDIMTEAM, HACKATHON,ID , iD);
    }

    @Override
    public int getMaxIscritti(Integer iD) {
        return getFieldOfTable(MAXISCRITTI, HACKATHON,ID ,iD);
    }

    @Override
    public Integer getHackathonByTeam(Integer id) {
        return getFieldOfTable(HACKATHONID, TEAM,ID, id);
    }

    @Override
    public Integer getIdAggiornamentoByTeam(Integer id) {
        return getFieldOfTable(ID,AGGIORNAMENTO,TEAMID,id);
    }

    @Override
    public void caricaHackathonDB(Hackathon hackathon, Organizzatore organizzatore) {
        String sql = "INSERT INTO hackathon (titolo, sede, problema, data_inizio, data_fine, fine_periodo_prenotazioni, max_iscritti, max_dim_team, username_organizzatore) VALUES (?,?,?,?,?,?,?,?,?) RETURNING id";
        String organizzatoreSQL = "UPDATE organizzatore SET hackathon_id = ? WHERE username = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql);
             PreparedStatement ostmt = con.prepareStatement(organizzatoreSQL)) {

            stmt.setString(1, hackathon.getNome());
            stmt.setString(2, hackathon.getSede());
            stmt.setString(3, hackathon.getProblema());
            stmt.setDate(4, java.sql.Date.valueOf(hackathon.getDataInizio()));
            stmt.setDate(5, java.sql.Date.valueOf(hackathon.getDataFine()));
            stmt.setDate(6, java.sql.Date.valueOf(hackathon.getFinePeriodoPrenotazioni()));
            stmt.setInt(7, hackathon.getMaxIscritti());
            stmt.setInt(8, hackathon.getMaxDimTeam());
            stmt.setString(9, organizzatore.getUsername());
            ResultSet rs=stmt.executeQuery();

            if (rs.next()) {
                int generatedId = rs.getInt(1);
                hackathon.setID(generatedId);
                organizzatore.setHackathonID(generatedId);
            } else {
                throw new SQLException("Inserimento hackathon fallito, nessun ID restituito.");
            }
            ostmt.setInt(1, hackathon.getID());
            ostmt.setString(2, organizzatore.getUsername());

            ostmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Utente> getUtenti(Integer id) {
        String sql = "SELECT nome, cognome, email, username, password, team_id, hackathon_id  FROM utente WHERE hackathon_id = ?";
        List<Utente> utenti = new ArrayList<>();
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utente u = new Utente(
                        rs.getString(NOME),
                        rs.getString(COGNOME),
                        rs.getString(EMAIL),
                        rs.getString(USERNAME),
                        rs.getString(PASSWORD)
                );
                u.setHackathonID(rs.getInt(HACKATHONID));
                int teamId = rs.getInt(TEAMID);
                if (rs.wasNull()) u.setTeamID(null);
                else u.setTeamID(teamId);

                utenti.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utenti;
    }


    @Override
    public List<Giudice> getGiudici(Integer id) {
        String sql = "SELECT nome, cognome, email, username, password, hackathon_id FROM giudice WHERE hackathon_id = ?";
        List<Giudice> giudici = new ArrayList<>();
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Giudice g = new Giudice(
                        rs.getString(NOME),
                        rs.getString(COGNOME),
                        rs.getString(EMAIL),
                        rs.getString(USERNAME),
                        rs.getString(PASSWORD),
                        rs.getInt(HACKATHONID)
                );
                giudici.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return giudici;
    }

    private List<Team> getTeamsByQuery(String sql, Integer hackathonID){
        List<Team> teams = new ArrayList<>();
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, hackathonID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                teams.add(new Team(
                        rs.getInt(ID),
                        rs.getString(NOME),
                        rs.getDouble(MEDIAVOTI),
                        rs.getInt(HACKATHONID)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }

    @Override
    public List<Team> getClassificaTeams(Integer hackathonID){
        String sql = "SELECT * FROM team WHERE hackathon_id = ? ORDER BY mediavoti DESC";
        return getTeamsByQuery(sql, hackathonID);
    }

    @Override
    public List<Team> getTeamByHackathon(Integer id) {
        String sql = "SELECT id,nome,mediavoti,hackathon_id FROM team WHERE hackathon_id = ?";
        return getTeamsByQuery(sql, id);
    }

    @Override
    public boolean isClassificaPubblicata(Integer id) {
        String sql = "SELECT classifica_pubblicata FROM hackathon WHERE id = ? ";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean(CLASSIFICA);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Utente> getPotenzialiGiudici(){
        String sql = "SELECT nome, cognome, email, username, password, team_id, hackathon_id FROM utente WHERE team_id IS NULL";
        List<Utente> potenzialiGiudici = new ArrayList<>();

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utente u = new Utente(
                        rs.getString(NOME),
                        rs.getString(COGNOME),
                        rs.getString(EMAIL),
                        rs.getString(USERNAME),
                        rs.getString(PASSWORD)
                );
                int hackathonId = rs.getInt(HACKATHONID);
                if (rs.wasNull()) u.setHackathonID(null);
                else u.setTeamID(hackathonId);

                int teamId = rs.getInt(TEAMID);
                if (rs.wasNull()) u.setTeamID(null);
                else u.setTeamID(teamId);

                potenzialiGiudici.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return potenzialiGiudici;
    }

}
