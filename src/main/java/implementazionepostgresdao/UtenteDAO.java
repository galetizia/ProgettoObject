package implementazionepostgresdao;
import dao.IUtenteDAO;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import java.sql.*;

/**
 * Implementazione concreta dell'interfaccia {@link IUtenteDAO}
 * per la gestione della persistenza degli oggetti {@link Utente}
 * su un database PostgresSQL.
 * <p>
 * Questa classe fornisce l'accesso al database per le operazioni come
 * login, ricerca di un utente e gestire quando un utente si iscrive/crea a un team
 * sfrutta una connessione al database ottenuta da {@link ConnessioneDatabase}
 * </p>
 */
public class UtenteDAO implements IUtenteDAO {

    /**
     * Crea una nuova istanza di un {@code UtenteDAO}, ha un costruttore vuoto perché
     * l'oggetto DAO non richiede l'inizializzazione di campi specifici al momento della creazione.
     */
    public UtenteDAO() {}

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene il nome dell'utente */
    private static final String NOME = "nome";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene il cognome dell'utente */
    private static final String COGNOME = "cognome";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene l'email dell'utente */
    private static final String EMAIL = "email";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene lo username dell'utente */
    private static final String USERNAME = "username";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene la password dell'utente */
    private static final String PASSWORD = "password";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene l'id dell'hackathon associato all'utente */
    private static final String HACKATHONID = "hackathon_id";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene l'id del team associato all'utente */
    private static final String TEAMID = "team_id";

    /** DAO per l'entità hackathon, usato per operazioni collegate */
    private final HackathonDAO hdao = new HackathonDAO();


    /**
     * Converte un {@link ResultSet} in un oggetto {@link Utente}.
     * <p>
     * I valori delle colonne sono letti in base ai nomi definiti come costanti,
     * e vengono gestiti anche eventuali valori {@code NULL} per gli ID di team e hackathon.
     * </p>
     *
     * @param rs il {@code ResultSet} ottenuto da una query.
     * @return un oggetto {@code Utente} popolato con i dati del database.
     * @throws SQLException se si verifica un errore durante la lettura dei dati.
     */
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


    /**
     * Esegue l'autenticazione di un utente sulla base di username e password.
     *
     * @param username lo username inserito dall'utente.
     * @param password la password associata allo username.
     * @return se l'autenticazione ha successo un oggetto {@code Utente}
     * convertito da un {@link ResultSet}, altrimenti {@code null}.
     */
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


    /**
     * Cerca un utente in base a un campo specifico del database.
     * <p>
     * Metodo di supporto privato riutilizzato da {@code findUtenteByUsername} e {@code findUtenteByEmail}.
     * </p>
     *
     * @param field il nome della colonna su cui effettuare la ricerca (esempio "email" o "username").
     * @param value il valore da confrontare nel campo specificato.
     * @return se trovato l'oggetto {@code Utente} convertito da un {@link ResultSet} (con il metodo mapResultSetToUtente), altrimenti {@code null}.
     */
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


    /**
     * Cerca un utente nel database in base allo username.
     *
     * @param username lo username dell'utente da cercare.
     * @return l'oggetto {@code Utente} se trovato, altrimenti {@code null}.
     */
    @Override
    public Utente findUtenteByUsername(String username) {
        return findUtenteByField(USERNAME, username);
    }


    /**
     * Cerca un utente nel database in base all'email.
     *
     * @param email l'email dell'utente da cercare.
     * @return l'oggetto {@code Utente} se trovato, altrimenti {@code null}.
     */
    @Override
    public Utente findUtenteByEmail(String email) {
        return findUtenteByField(EMAIL, email);
    }


    /**
     * Aggiorna l'ID del team e l'ID dell'hackathon associati a un utente, nessuna return
     * perché ci interessa solo aggiornare le modifica nel database
     *
     * @param team il nuovo {@link Team} a cui associare l'utente.
     * @param utente l'oggetto {@code Utente} da aggiornare.
     */
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


