package implementazionepostgresdao;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import dao.IOrganizzatoreDAO;
import java.sql.*;

public class OrganizzatoreDAO implements IOrganizzatoreDAO {
    /**
     * Crea una nuova istanza di {@code OrganizzatoreDAO}, costruttore vuoto poiché
     * L'oggetto DAO non ha bisogno di campi da assegnare alla creazione.
     */
    public OrganizzatoreDAO() {/* Costruttore vuoto perché l'oggetto DAO non ha bisogno di campi da assegnare alla creazione*/}

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

    /** DAO per l'entità utente, usato per operazioni collegate */
    private final UtenteDAO udao = new UtenteDAO();


    /**
     * Converte un {@link ResultSet} in un oggetto {@link Organizzatore}.
     * <p>
     * I valori delle colonne sono letti in base ai nomi definiti come costanti,
     * e vengono gestiti anche eventuali valori {@code NULL} per hackathonID.
     * </p>
     *
     * @param rs il {@code ResultSet} ottenuto da una query.
     * @return un oggetto {@code Organizzatore} popolato con i dati del database.
     * @throws SQLException se si verifica un errore durante la lettura dei dati.
     */
    private Organizzatore mapResultSetToOrganizzatore(ResultSet rs) throws SQLException {
        Organizzatore o = new Organizzatore(
                rs.getString(NOME),
                rs.getString(COGNOME),
                rs.getString(EMAIL),
                rs.getString(USERNAME),
                rs.getString(PASSWORD)
        );
        int hackathonId = rs.getInt(HACKATHONID);
        if (rs.wasNull()) o.setHackathonID(null);
        else o.setHackathonID(hackathonId);

        return o;
    }


    /**
     * Esegue l'autenticazione di un organizzatore sulla base di username e password.
     *
     * @param username lo username inserito dall'organizzatore.
     * @param password la password associata allo username.
     * @return se l'autenticazione ha successo un oggetto {@code Organizzatore} convertito
     * da un {@link ResultSet} (con il metodo mapResultSetToOrganizzatore), altrimenti {@code null}.
     */
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


    /**
     * Cerca un organizzatore in base a un campo specifico del database.
     * <p>
     * Metodo di supporto privato riutilizzato da {@code findOrganizzatoreByUsername} e {@code findOrganizzatoreByEmail}.
     * </p>
     *
     * @param field il nome della colonna su cui effettuare la ricerca (esempio "email" o "username").
     * @param value il valore da confrontare nel campo specificato.
     * @return se trovato l'oggetto {@code Organizzatore} convertito da un {@link ResultSet} (con il metodo mapResultSetToOrganizzatore),
     * altrimenti {@code null}.
     */
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


    /**
     * Cerca un organizzatore nel database in base allo username.
     *
     * @param username lo username dell'organizzatore da cercare.
     * @return l'oggetto {@code Organizzatore} se trovato, altrimenti {@code null}.
     */
    @Override
    public Organizzatore findOrganizzatoreByUsername(String username) {
        return findOrganizzatoreByField(USERNAME, username);
    }


    /**
     * Cerca un organizzatore nel database in base all'email.
     *
     * @param email l'email dell'organizzatore da cercare.
     * @return l'oggetto {@code Organizzatore} se trovato, altrimenti {@code null}.
     */
    @Override
    public Organizzatore findOrganizzatoreByEmail(String email) {
        return findOrganizzatoreByField(EMAIL, email);
    }


    /**
     * Converte un utente in giudice, associandolo a un hackathon.
     *
     * @param username username dell’utente da convertire.
     * @param idHackathon ID dell’hackathon da associare.
     * @return {@code true} se la conversione è avvenuta con successo, {@code false} altrimenti.
     */
    @Override
    public boolean aggiungiGiudice(String username, Integer idHackathon){
        Utente u = udao.findUtenteByUsername(username);
        if (u == null || u.getTeamID()!=null) {
            return false;
        }

        String insertSql = "INSERT INTO giudice (nome,cognome,email,username,password,hackathon_id) VALUES (?,?,?,?,?,?)";
        String deleteSql = "DELETE FROM utente WHERE username=?";

        try(Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement insertStmt = con.prepareStatement(insertSql);
            PreparedStatement deleteStmt = con.prepareStatement(deleteSql)) {

            insertStmt.setString(1, u.getNome());
            insertStmt.setString(2, u.getCognome());
            insertStmt.setString(3, u.getEmail());
            insertStmt.setString(4, u.getUsername());
            insertStmt.setString(5, u.getPassword());
            insertStmt.setInt(6, idHackathon);

            int rows = insertStmt.executeUpdate();
            if (rows > 0) {

                deleteStmt.setString(1, u.getUsername());
                deleteStmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


    /**
     * Rimuove un utente da un team e da un hackathon.
     *
     * @param username username dell’utente da scollegare.
     * @param organizzatore organizzatore utilizzato per controllare se la classifica è già stata pubblicata.
     */
    @Override
    public boolean removeUtente(String username, Organizzatore organizzatore) {
        String sql = "UPDATE utente SET hackathon_id = null, team_id = null WHERE username = ?";
        String oSql = "SELECT classifica_pubblicata FROM hackathon WHERE username_organizzatore = ?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql);
             PreparedStatement oStmt = con.prepareStatement(oSql)) {
            oStmt.setString(1, organizzatore.getUsername());
            ResultSet rs = oStmt.executeQuery();
            if (rs.next()) {
                boolean  classPubblicata = rs.getBoolean("classifica_pubblicata");
                if(classPubblicata) {
                    return false;
                }
                stmt.setString(1, username);
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Rimuove un giudice e lo reinserisce come utente nel sistema.
     *
     * @param username username del giudice da rimuovere.
     * @param organizzatore organizzatore utilizzato per controllare se la classifica è già stata pubblicata.
     */
    @Override
    public boolean removeGiudice(String username, Organizzatore organizzatore) {
        String selectSql = "SELECT nome, cognome, email, username, password, hackathon_id FROM giudice WHERE username = ?";
        String deleteSql = "DELETE FROM giudice WHERE username = ?";
        String insertSql = "INSERT INTO utente (nome, cognome, email, username, password) VALUES (?, ?, ?, ?, ?)";
        String oSql = "SELECT classifica_pubblicata FROM hackathon WHERE username_organizzatore = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement selectStmt = con.prepareStatement(selectSql); PreparedStatement deleteStmt = con.prepareStatement(deleteSql);
             PreparedStatement insertStmt = con.prepareStatement(insertSql); PreparedStatement oStmt = con.prepareStatement(oSql)) {
            oStmt.setString(1, organizzatore.getUsername());
            ResultSet oRs = oStmt.executeQuery();
            if(oRs.next()) {
                boolean classPubblicata = oRs.getBoolean("classifica_pubblicata");
                if(classPubblicata)
                    return false;
            }
            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();

            if(rs.next()) {
                insertStmt.setString(1, rs.getString(NOME));
                insertStmt.setString(2, rs.getString(COGNOME));
                insertStmt.setString(3, rs.getString(EMAIL));
                insertStmt.setString(4, rs.getString(USERNAME));
                insertStmt.setString(5, rs.getString(PASSWORD));
                insertStmt.executeUpdate();

                deleteStmt.setString(1, username);
                deleteStmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Elimina un team dal sistema e aggiorna gli utenti che ne facevano parte.
     *
     * @param id ID del team da eliminare.
     * @param idHackathon ID dell'hackathon.
     * @param bool parametro booleano utilizzato per controllare se la classifica è già pubblicata.
     */
    @Override
    public boolean removeTeam(Integer id, Integer idHackathon, Boolean bool) {
        String sql = "DELETE FROM team WHERE id = ?";
        String updateSql = "UPDATE utente SET hackathon_id = null WHERE team_id = ?";
        String oSql = "SELECT classifica_pubblicata FROM hackathon WHERE id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql); PreparedStatement updateStmt = con.prepareStatement(updateSql);
             PreparedStatement oStmt = con.prepareStatement(oSql)) {
            oStmt.setInt(1, idHackathon);
            ResultSet rs = oStmt.executeQuery();
            if(rs.next() && bool) {
                boolean  classPubblicata = rs.getBoolean("classifica_pubblicata");
                if(classPubblicata) {
                    return false;
                }
                updateStmt.setInt(1, id);
                updateStmt.executeUpdate();
                stmt.setInt(1, id);
                stmt.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Termina un hackathon eliminandolo dal database.
     *
     * @param hackathonId ID dell’hackathon da eliminare.
     */
    @Override
    public void terminaHackathon(Integer hackathonId) {
        String deleteSql = "DELETE FROM hackathon WHERE id=?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(deleteSql)) {

            stmt.setInt(1, hackathonId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Segna la classifica come pubblicata per l'hackathon specificato.
     *
     * @param id ID dell’hackathon.
     */
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