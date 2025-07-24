package implementazionepostgresdao;
import dao.ITeamDAO;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link ITeamDAO} per la gestione
 * per la gestione della persistenza degli oggetti {@link TeamDAO}
 * su un database PostgresSQL.
 * <p>
 * Questa classe fornisce l'accesso al database per le operazioni come
 * ricerca di un team, impostare alcuni campi di team e gestire quando un team carica un aggiornamento
 * sfrutta una connessione al database ottenuta da {@link ConnessioneDatabase}
 * </p>
 */
public class TeamDAO implements ITeamDAO {
    /**
     * Crea una nuova istanza di {@code TeamDAO}, costruttore vuoto poiché
     * L'oggetto DAO non ha bisogno di campi da assegnare alla creazione.
     */
    public TeamDAO() {}

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

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene l'id di un a un team o di un hackathon */
    private static final String ID = "id";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene la media dei voti di un team */
    private static final String MEDIAVOTI = "mediavoti";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene un singolo voto */
    private static final String VOTO = "voto";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene un singolo aggiornamento */
    private static final String DOCUMENTO = "documento";


    /**
     * Recupera un oggetto {@link Team} dal database tramite il suo ID.
     * Converte un {@link ResultSet} in un oggetto {@link Team}
     * @param id ID del team.
     * @return Oggetto {@link Team} se trovato, altrimenti {@code null}.
     */
    @Override
    public Team getTeamByID(Integer id){
        String sql = "SELECT id,nome,mediavoti,hackathon_id FROM team WHERE id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Team(
                        rs.getInt(ID),
                        rs.getString(NOME),
                        rs.getInt(MEDIAVOTI),
                        rs.getInt(HACKATHONID)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Recupera la lista dei voti assegnati a un team.
     * Estrae i valori dei voti (campo {@code voto}) dal {@link ResultSet}
     * e li inserisce in una lista di {@link Double}.
     *
     * @param id ID del team.
     * @return Lista di voti (Double).
     */
    @Override
    public List<Double> getVotiPerTeam(Integer id){
        List<Double> votiPerTeam = new ArrayList<>();
        String sql = "SELECT voto FROM voti WHERE team_id = ?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                votiPerTeam.add(rs.getDouble(VOTO));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return votiPerTeam;
    }


    /**
     * Imposta il valore medio dei voti per un team nel database.
     *
     * @param id ID del team.
     * @param media Media dei voti da assegnare.
     */
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


    /**
     * Inserisce un nuovo team nel database e associa l'utente al team appena creato.
     *
     * @param team Oggetto {@link Team} da inserire.
     * @param utente Utente da associare al team.
     */
    @Override
    public void caricaTeamNelDB(Team team, Utente utente) {
        String sql = "INSERT INTO team (nome,mediaVoti,Hackathon_id) VALUES (?,?,?) RETURNING id";
        String utenteSql = "UPDATE utente SET team_id = ?,hackathon_id=? WHERE username = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql);
             PreparedStatement uStmt = con.prepareStatement(utenteSql)) {

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
            uStmt.setInt(1, team.getId());
            uStmt.setInt(2,team.getHackathonID());
            uStmt.setString(3,utente.getUsername());

            utente.setTeamID(team.getId());
            utente.setHackathonID(team.getHackathonID());

            uStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Restituisce la lista dei membri del team.
     * Estrae i dati degli utenti dal {@link ResultSet}
     * e li inserisce in una lista di {@link Utente}.
     *
     * @param id ID del team.
     * @return Lista di oggetti {@link Utente} che appartengono al team.
     */
    @Override
    public List<Utente> membriTeam(Integer id){
        String sql = "SELECT nome,cognome,email,username,password FROM utente WHERE team_id = ?";
        List<Utente> membri = new ArrayList<>();
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
                membri.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return membri;
    }


    /**
     * Rimuove l'associazione di un utente da un team e hackathon, per rimuoverlo viene aggiornato a null il teamID associato
     *
     * @param username Username dell'utente da rimuovere dal team.
     */
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


    /**
     * Carica o aggiorna un oggetto {@link Aggiornamento} per un team specifico.
     * Se l'aggiornamento già esiste, lo sovrascrive e cancella eventuali commenti associati.
     *
     * @param utente Utente che effettua l'aggiornamento.
     * @param agg Oggetto {@link Aggiornamento} da caricare o aggiornare.
     */
    @Override
    public void caricaAggiornamentoDB(Utente utente, Aggiornamento agg) {

        String checkSql = "SELECT id FROM aggiornamento WHERE team_id=?";
        String updateSql = "UPDATE aggiornamento SET nome=?,documento=?,utente_username=?, isElaboratoFinale=? WHERE team_id=? RETURNING id";
        String deleteSql = "DELETE FROM commenti WHERE team_id=?";
        String insertSql = "INSERT INTO aggiornamento (nome, documento,team_id,utente_username, isElaboratoFinale) VALUES (?,?,?,?,?) RETURNING id";

            try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement checkStmt = con.prepareStatement(checkSql);
                 PreparedStatement insertStmt = con.prepareStatement(insertSql); PreparedStatement updateStmt = con.prepareStatement(updateSql);
                 PreparedStatement deleteStmt = con.prepareStatement(deleteSql)) {

                checkStmt.setInt(1, agg.getTeamID());
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    updateStmt.setString(1, agg.getNome());
                    updateStmt.setString(2, agg.getDocumento());
                    updateStmt.setString(3, agg.getUsernameUtente());
                    updateStmt.setBoolean(4, agg.getElaboratoFinale());
                    updateStmt.setInt(5, agg.getTeamID());

                    updateStmt.executeQuery();


                    deleteStmt.setInt(1, agg.getTeamID());
                    deleteStmt.executeUpdate();

                } else {
                    insertStmt.setString(1, agg.getNome());
                    insertStmt.setString(2, agg.getDocumento());
                    insertStmt.setInt(3, agg.getTeamID());
                    insertStmt.setString(4, agg.getUsernameUtente());
                    insertStmt.setBoolean(5, agg.getElaboratoFinale());

                    insertStmt.executeQuery();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    /**
     * Recupera il contenuto dell'ultimo aggiornamento del team.
     * Estrae il campo {@code documento} dalla tabella {@code aggiornamento}.
     *
     * @param id ID del team.
     * @return Documento dell'aggiornamento come stringa, oppure {@code null} se non trovato.
     */
    @Override
    public String getUltimoAggiornamento(Integer id) {
        String sql = "SELECT documento FROM aggiornamento WHERE team_id=?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString(DOCUMENTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Verifica se esiste un aggiornamento per il team specificato
     * che è stato contrassegnato come elaborato finale.
     *
     * @param idTeam ID del team.
     * @return {@code true} se esiste un elaborato finale per quel team, {@code false} altrimenti.
     */
    @Override
    public boolean getElaboratoFinaleUltimoAggiornamento(Integer idTeam) {

        String checkSql = "SELECT isElaboratoFinale FROM aggiornamento WHERE team_id=? AND isElaboratoFinale=?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(checkSql)) {
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
