package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {

    private static ConnessioneDatabase instance;
    private static final String  NOME = "postgres";
    private static final String PASS = "Passwordhackathon";
    private static final String URL = "jdbc:postgresql://localhost:5432/Hackathon";
    private static final String DRIVER = "org.postgresql.Driver";

    private ConnessioneDatabase() throws SQLException {
        try{
            Class.forName(DRIVER);
        } catch(ClassNotFoundException e){
            System.out.println("Database connection creation failed : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static ConnessioneDatabase getInstance() throws SQLException {
        if(instance == null)
            instance = new ConnessioneDatabase();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, NOME, PASS);
    }

}
