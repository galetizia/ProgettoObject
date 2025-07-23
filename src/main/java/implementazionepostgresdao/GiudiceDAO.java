package implementazionepostgresdao;

import dao.IGiudiceDAO;
import database.ConnessioneDatabase;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione concreta dell'interfaccia {@link IGiudiceDAO}
 * per la gestione della persistenza degli oggetti {@link Giudice}
 * su un database PostgresSQL.
 * <p>
 * Questa classe fornisce l'accesso al database per le operazioni come
 * login, ricerca di un giudice e gestire le operazioni che deve un giudice
 * deve effettuare, come le votazioni e i commenti degli aggiornamenti.
 * Sfrutta una connessione al database ottenuta da {@link ConnessioneDatabase}
 * </p>
 */
public class GiudiceDAO implements IGiudiceDAO {

    /**
     * Crea una nuova istanza di un {@code GiudiceDAO}, ha un costruttore vuoto perché
     * l'oggetto DAO non richiede l'inizializzazione di campi specifici al momento della creazione.
     */
    public GiudiceDAO() {}

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene il nome del giudice/il nome del Team di un utente */
    private static final String NOME = "nome";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene il cognome del giudice */
    private static final String COGNOME = "cognome";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene l'email del giudice */
    private static final String EMAIL = "email";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene lo username del giudice */
    private static final String USERNAME = "username";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene lo username del giudice */
    private static final String PASSWORD = "password";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene l'id dell'hackathon associato a un utente/team */
    private static final String HACKATHONID = "hackathon_id";

    /** Costante che rappresenta la Stringa del nome della colonna che nel database contiene l'id del team associato all'utente/a un hackathon */
    private static final String TEAMID = "team_id";

    /** DAO per le entità hackathon e team, usati per operazioni collegate */
    HackathonDAO hdao = new HackathonDAO();
    TeamDAO tdao = new TeamDAO();


    /**
     * Esegue l'autenticazione di un giudice sulla base di username e password.
     * Converte un {@link ResultSet} in un oggetto {@link Giudice}.
     * <p>
     * I valori delle colonne sono letti in base ai nomi definiti come costanti.
     * </p>
     *
     * @param username lo username inserito del giudice.
     * @param password la password associata allo username.
     * @return se l'autenticazione ha successo un oggetto {@code Giudice}
     * convertito da un {@link ResultSet}, altrimenti {@code null}.
     */
    @Override
    public Giudice login(String username, String password) {
        String sql = "SELECT nome, cognome, email, username, password, hackathon_id FROM giudice WHERE username = ? AND password = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Giudice(
                        rs.getString(NOME),
                        rs.getString(COGNOME),
                        rs.getString(EMAIL),
                        rs.getString(USERNAME),
                        rs.getString(PASSWORD),
                        rs.getInt(HACKATHONID)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Cerca un giudice in base a un campo specifico del database.
     * <p>
     * Metodo di supporto privato riutilizzato da {@code findGiudiceByUsername} e {@code findGiudiceByEmail}.
     * </p>
     *
     * @param field il nome della colonna su cui effettuare la ricerca (esempio "email" o "username").
     * @param value il valore da confrontare nel campo specificato.
     * @return se trovato l'oggetto {@code Giudice} convertito da un {@link ResultSet}, altrimenti {@code null}.
     */
    private Giudice findGiudiceByField(String field, String value) {
        String sql = "SELECT nome, cognome, email, username, password, hackathon_id  FROM giudice WHERE " + field + " = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, value);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Giudice(
                        rs.getString(NOME),
                        rs.getString(COGNOME),
                        rs.getString(EMAIL),
                        rs.getString(USERNAME),
                        rs.getString(PASSWORD),
                        rs.getInt(HACKATHONID)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Cerca un giudice nel database in base allo username.
     *
     * @param username lo username del giudice da cercare.
     * @return l'oggetto {@code Giudice} se trovato, altrimenti {@code null}.
     */
    @Override
    public Giudice findGiudiceByUsername(String username) {
        return findGiudiceByField(USERNAME, username);
    }


    /**
     * Cerca un giudice nel database in base all'email.
     *
     * @param email l'email del giudice da cercare.
     * @return l'oggetto {@code Giudice} se trovato, altrimenti {@code null}.
     */
    @Override
    public Giudice findGiudiceByEmail(String email) {
        return findGiudiceByField(EMAIL, email);
    }


    /**
     * Salva un commento di un giudice relativo a un aggiornamento di un team.
     *
     * @param commento il testo del commento.
     * @param id l'ID del team associato all'aggiornamento.
     * @param giudice il giudice che sta scrivendo il commento.
     */
    @Override
    public void saveCommento(String commento, Integer id, Giudice giudice) {
        String insSql = "INSERT INTO commenti(commento,giudice_id,team_id,aggiornamento_id) VALUES (?,?,?,?)";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(insSql)) {
            stmt.setString(1, commento);
            stmt.setString(2, giudice.getUsername());
            stmt.setInt(3, id);
            stmt.setInt(4, hdao.getIdAggiornamentoByTeam(id));

            int r = stmt.executeUpdate();
            if (r == 0) {
                throw new SQLException("Commento non inserito.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Verifica se un giudice ha già commentato un aggiornamento associato a un team.
     *
     * @param id l'ID del team.
     * @param giudice il giudice da controllare.
     * @return {@code true} se il giudice ha già commentato l'aggiornamento, altrimenti {@code false}.
     */
    @Override
    public boolean haCommentatoAggiornamento(Integer id,Giudice giudice) {
        String sql = "SELECT id FROM commenti WHERE giudice_id = ? AND aggiornamento_id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, giudice.getUsername());
            stmt.setInt(2, hdao.getIdAggiornamentoByTeam(id));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Controlla se un giudice ha già espresso un voto per un determinato team.
     *
     * @param idTeam l'ID del team.
     * @param giudiceID lo username del giudice.
     * @return {@code true} se il giudice ha già votato il team, altrimenti {@code false}.
     */
    @Override
    public boolean controlloVotoTeam(Integer idTeam, String giudiceID) {
        String checkSql = "SELECT id FROM voti WHERE team_id=? AND giudice_id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(checkSql)) {

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


    /**
     * Inserisce un nuovo voto espresso da un giudice per un team nel database.
     *
     * @param voto l'oggetto {@link Voto} contenente le informazioni da salvare.
     */
    @Override
    public void caricaVoto(Voto voto) {
        String insertSql = "INSERT INTO voti (team_id, voto, giudice_id) VALUES (?,?,?) RETURNING id";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement insertstmt = con.prepareStatement(insertSql)){

            insertstmt.setInt(1, voto.getTeam().getId());
            insertstmt.setInt(2, voto.getValutazione());
            insertstmt.setString(3, voto.getGiudice().getUsername());

            insertstmt.executeQuery();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Restituisce una lista dei team che hanno caricato un elaborato finale
     * all'interno di un determinato hackathon.
     *
     * @param hackathonID l'ID dell'hackathon di riferimento.
     * @return la lista dei team che hanno consegnato l'elaborato finale.
     */
    @Override
    public List<Team> getElaboratiFinaliTeam(Integer hackathonID) {
        String sql="SELECT team_id FROM aggiornamento WHERE iselaboratofinale=true";
        List<Team> teams = new ArrayList<>();

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){

            while(rs.next()) {
                int idTeam = rs.getInt(TEAMID);
                Team t = tdao.getTeamByID(idTeam);

                if(t!=null && t.getHackathonID().equals(hackathonID)) teams.add(t);
            }

        }catch(SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }


    /**
     * Verifica se un determinato team ha caricato un elaborato marcato come finale.
     *
     * @param idTeam l'ID del team da controllare.
     * @return {@code true} se il team ha un elaborato finale, altrimenti {@code false}.
     */
    @Override
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
