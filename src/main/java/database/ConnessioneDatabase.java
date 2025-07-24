package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe singleton per la gestione della connessione al database.
 * <p>
 * Si occupa di caricare il driver JDBC e fornire una connessione
 * al database PostgreSQL configurato con le credenziali e URL specificati.
 * </p>
 */
public class ConnessioneDatabase {

    /** Istanza singleton della connessione al database */
    private static ConnessioneDatabase instance;

    /** Username per l'accesso al database */
    private static final String NOME = "postgres";

    /** Password per l'accesso al database */
    private static final String PASS = "Passwordhackathon";

    /** URL di connessione al database PostgreSQL */
    private static final String URL = "jdbc:postgresql://localhost:5432/Hackathon";

    /** Nome del driver JDBC PostgreSQL */
    private static final String DRIVER = "org.postgresql.Driver";

    /**
     * Costruttore privato che carica il driver JDBC.
     * <p>
     * Se il driver non viene trovato, stampa lo stack trace dell'eccezione.
     * </p>
     */
    private ConnessioneDatabase() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Database connection creation failed : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Restituisce l'istanza singleton di {@link ConnessioneDatabase}.
     *
     * @return L'istanza singleton.
     */
    public static ConnessioneDatabase getInstance() {
        if (instance == null)
            instance = new ConnessioneDatabase();
        return instance;
    }

    /**
     * Fornisce una nuova connessione al database PostgreSQL.
     *
     * @return Una nuova {@link Connection} attiva.
     * @throws SQLException Se si verifica un errore durante la connessione.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, NOME, PASS);
    }

}
