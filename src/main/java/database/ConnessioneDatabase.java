package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {

    private static ConnessioneDatabase instance;
    public Connection connection = null;
    private String nome = "postgres";
    private String password = "password";
    private String url = "jdbc:postgresql://localhost:5432/Hackathon";
    private String driver = "org.postgresql.Driver";

    private ConnessioneDatabase() throws SQLException {
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);
        } catch(ClassNotFoundException e){
            System.out.println("Database connection creation failed : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static ConnessioneDatabase getInstance() throws SQLException {
        if(instance == null || instance.connection.isClosed())
            instance = new ConnessioneDatabase();
        return instance;
    }


}
