package implementazionepostgresdao;

import dao.IHackathonDAO;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.ConnessioneDatabase;

/**
 * Implementazione concreta dell'interfaccia {@link IHackathonDAO}
 * per la gestione della persistenza degli oggetti {@link Hackathon}
 * su un database PostgresSQL.
 * <p>
 * Questa classe fornisce l'accesso al database per le operazioni come
 * sign-in, di recupero hackathon/utenti/giudici/team e classifica e altre funzionalità
 * sfrutta una connessione al database ottenuta da {@link ConnessioneDatabase}
 * </p>
 */
public class HackathonDAO implements IHackathonDAO {

    public HackathonDAO() {/* Costruttore vuoto perché l'oggetto DAO non lo utiliziamo con dei campi a cui assegnare i valori*/}

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene il nome del Team/di un utente/di un giudice/di un organizzatore */
    private static final String NOME = "nome";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene il cognome di un utente/di un giudice/di un organizzatore */
    private static final String COGNOME = "cognome";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene l'email dell'utente/organizzatore/giudice */
    private static final String EMAIL = "email";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene lo username del giudice/utente/organizzatore */
    private static final String USERNAME = "username";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene la password del giudice/utente/organizzatore */
    private static final String PASSWORD = "password";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene l'id dell'hackathon associato a un utente/team/giudice/organizzatore */
    private static final String HACKATHONID = "hackathon_id";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene l'id del team associato all'utente */
    private static final String TEAMID = "team_id";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene l'id di un a un team o di un hackathon */
    private static final String ID = "id";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene la media dei voti di un team */
    private static final String MEDIAVOTI = "mediavoti";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene un valore booleano che indica se la classifica è stata pubblicata */
    private static final String CLASSIFICA = "classifica_pubblicata";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene la dimensione massima di un team */
    private static final String MAXDIMTEAM = "max_dim_team";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene il numero massimo di team iscritti all'hackathon */
    private static final String MAXISCRITTI = "max_iscritti";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene la data di inizio dell'hackathon  */
    private static final String DATAINIZIO = "data_inizio";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene la data di fine dell'hackathon  */
    private static final String DATAFINE = "data_fine";

    /** Costante che rappresenta la Stringa del nome della tabella utente */
    private static final String UTENTE = "utente";

    /** Costante che rappresenta la Stringa del nome della tabella organizzatore */
    private static final String ORGANIZZATORE = "organizzatore";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene il problema dell'hackathon  */
    private static final String PROBLEMA = "problema";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene il titolo dell'hackathon */
    private static final String TITOLO = "titolo";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene la sede dell'hackathon  */
    private static final String SEDE = "sede";

    /** Costante che rappresenta la Stringa del nome della tabella hackathon */
    private static final String HACKATHON = "hackathon";

    /** Costante che rappresenta la Stringa del nome della tabella team */
    private static final String TEAM = "team";

    /** Costante che rappresenta la Stringa del nome della tabella aggiornamento */
    private static final String AGGIORNAMENTO = "aggiornamento";


    /**
     * Metodo generico per registrare un nuovo utente o un organizzatore nel database.
     * Controlla che username e/o email non siano già presenti prima dell'inserimento.
     *
     * @param tableName Nome della tabella in cui inserire i dati (esempio "utente" o "organizzatore").
     * @param nome Nome della persona.
     * @param cognome Cognome della persona.
     * @param email Email della persona.
     * @param username Username scelto.
     * @param password Password scelta.
     * @return {@code true} se l'inserimento ha avuto successo, {@code false} se username/email già esistenti.
     */
    private boolean signIn(String tableName, String nome, String cognome, String email, String username, String password){
        String checkSql="SELECT * FROM "+ tableName +" WHERE username=? OR email=?";
        String insertSql="INSERT INTO "+tableName+"(nome,cognome,email,username,password) VALUES(?,?,?,?,?)";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement checkStmt = con.prepareStatement(checkSql);
             PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return false;
            }

            insertStmt.setString(1, nome);
            insertStmt.setString(2, cognome);
            insertStmt.setString(3, email);
            insertStmt.setString(4, username);
            insertStmt.setString(5, password);

            return (insertStmt.executeUpdate() > 0);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Registra un nuovo utente nel sistema, utilizzando il metodo generale di sign-in.
     *
     * @param nome Nome dell'utente.
     * @param cognome Cognome dell'utente.
     * @param email Email dell'utente.
     * @param username Username scelto.
     * @param password Password scelta.
     * @return {@code true} se la registrazione ha avuto successo, {@code false} altrimenti.
     */
    @Override
    public boolean signInUtente(String nome, String cognome, String email, String username, String password){
        return signIn(UTENTE,nome,cognome,email,username,password);
    }


    /**
     * Registra un nuovo organizzatore nel sistema, utilizzando il metodo generale di sign-in.
     *
     * @param nome Nome dell'organizzatore.
     * @param cognome Cognome dell'organizzatore.
     * @param email Email dell'organizzatore.
     * @param username Username scelto.
     * @param password Password scelta.
     * @return {@code true} se la registrazione ha avuto successo, {@code false} altrimenti.
     */
    @Override
    public boolean signInOrganizzatore(String nome, String cognome, String email, String username, String password){
        return signIn(ORGANIZZATORE,nome,cognome,email,username,password);
    }


    /**
     * Converte un {@link ResultSet} in un oggetto {@link Hackathon}.
     * <p>
     * I valori delle colonne sono letti in base ai nomi definiti come costanti
     * </p>
     *
     * @param rs il {@code ResultSet} ottenuto da una query.
     * @return un oggetto {@code Utente} popolato con i dati del database.
     * @throws SQLException se si verifica un errore durante la lettura dei dati.
     */
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


    /**
     * Recupera un oggetto {@link Hackathon} dato il suo ID.
     *
     * @param id ID dell'hackathon da cercare.
     * @return Oggetto {@link Hackathon} corrispondente all'ID, oppure {@code null} se non trovato.
     */
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


    /**
     * Recupera la lista completa di tutti gli hackathon presenti nel database.
     *
     * @return Lista di oggetti {@link Hackathon}.
     */
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


    /**
     * Recupera un certo campo (int), in un certa tabella, in una certa condizione.
     *
     * @param field Il campo da cercare.
     * @param table La tabella in cui cercare
     * @param where Il campo da confrontare con id
     * @param id Un id di qualche tabella
     * @return Il valore (int) trovato.
     */
    private int getFieldOfTable(String field, String table, String where, Integer id){
        String sql = "SELECT " + field + " FROM "+ table +" WHERE "+ where +" = ?";

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


    /**
     * Recupera la dimensione massima del team per un determinato hackathon.
     *
     * @param iD ID dell'hackathon.
     * @return La dimensione massima consentita per un team.
     */
    @Override
    public int getMaxDimTeam(Integer iD) {
        return getFieldOfTable(MAXDIMTEAM, HACKATHON,ID , iD);
    }


    /**
     * Recupera il numero massimo di team iscritti consentiti per un determinato hackathon.
     *
     * @param iD ID dell'hackathon.
     * @return Il numero massimo di team.
     */
    @Override
    public int getMaxIscritti(Integer iD) {
        return getFieldOfTable(MAXISCRITTI, HACKATHON,ID ,iD);
    }


    /**
     * Recupera l'ID dell'hackathon associato a un determinato team.
     *
     * @param id ID del team.
     * @return ID dell'hackathon associato.
     */
    @Override
    public Integer getHackathonByTeam(Integer id) {
        return getFieldOfTable(HACKATHONID, TEAM,ID, id);
    }


    /**
     * Recupera l'ID dell'aggiornamento associato a un determinato team.
     *
     * @param id ID del team.
     * @return ID dell'aggiornamento associato.
     */
    @Override
    public Integer getIdAggiornamentoByTeam(Integer id) {
        return getFieldOfTable(ID,AGGIORNAMENTO,TEAMID,id);
    }


    /**
     * Carica un nuovo oggetto {@link Hackathon} nel database, associandolo a un organizzatore.
     * Aggiorna anche l'organizzatore con l'ID dell'hackathon generato.
     *
     * @param hackathon Oggetto Hackathon da salvare.
     * @param organizzatore Organizzatore a cui è associato l'hackathon.
     */
    @Override
    public void caricaHackathonDB(Hackathon hackathon, Organizzatore organizzatore) {
        String sql = "INSERT INTO hackathon (titolo, sede, problema, data_inizio, data_fine, fine_periodo_prenotazioni, max_iscritti, max_dim_team, username_organizzatore) VALUES (?,?,?,?,?,?,?,?,?) RETURNING id";
        String organizzatoreSQL = "UPDATE organizzatore SET hackathon_id = ? WHERE username = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql);
             PreparedStatement oStmt = con.prepareStatement(organizzatoreSQL)) {

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
            oStmt.setInt(1, hackathon.getID());
            oStmt.setString(2, organizzatore.getUsername());

            oStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Recupera la lista di tutti gli utenti registrati per un determinato hackathon.
     *
     * @param id ID dell'hackathon.
     * @return Lista di oggetti {@link Utente} associati all'hackathon.
     */
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


    /**
     * Recupera la lista di tutti i giudici associati a un determinato hackathon.
     *
     * @param id ID dell'hackathon.
     * @return Lista di oggetti {@link Giudice} associati all'hackathon.
     */
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


    /**
     * Recupera una lista di Team da un query passata al metodo.
     *
     * @param sql Stringa contenente la query.
     * @param hackathonID l'hackathonID associato al team
     * @return La lista di teams ottenuta.
     */
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


    /**
     * Recupera e ordina i team di un determinato hackathon in base alla media dei voti (in ordine decrescente).
     *
     * @param hackathonID ID dell'hackathon.
     * @return Lista ordinata di oggetti {@link Team} costituente la classifica.
     */
    @Override
    public List<Team> getClassificaTeams(Integer hackathonID){
        String sql = "SELECT * FROM team WHERE hackathon_id = ? ORDER BY mediavoti DESC";
        return getTeamsByQuery(sql, hackathonID);
    }


    /**
     * Recupera tutti i team associati a un determinato hackathon, senza ordinarli.
     *
     * @param id ID dell'hackathon.
     * @return Lista di oggetti {@link Team}.
     */
    @Override
    public List<Team> getTeamByHackathon(Integer id) {
        String sql = "SELECT id,nome,mediavoti,hackathon_id FROM team WHERE hackathon_id = ?";
        return getTeamsByQuery(sql, id);
    }


    /**
     * Verifica se la classifica è stata pubblicata per un determinato hackathon.
     *
     * @param id ID dell'hackathon.
     * @return {@code true} se la classifica è pubblicata, {@code false} altrimenti.
     */
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


    /**
     * Recupera tutti gli utenti che non sono attualmente assegnati a un team.
     * Questi utenti possono potenzialmente essere promossi al ruolo giudici.
     *
     * @return Lista di oggetti {@link Utente} che possono diventare giudici.
     */
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
