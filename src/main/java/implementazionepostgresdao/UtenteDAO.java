package implementazionepostgresdao;
import dao.IUtenteDAO;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import java.sql.*;

public class UtenteDAO implements IUtenteDAO {

    private Connection connection;
    public UtenteDAO() {}

    @Override
    public Utente login(String username, String password){
        String sql="SELECT * FROM utente WHERE username=? AND password=?";

        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Utente(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean signIn(String nome, String cognome, String email, String username, String password){
        String checksql="SELECT * FROM utente WHERE username=? OR email=?";
        String insertsql="INSERT INTO utente(nome,cognome,email,username,password) VALUES(?,?,?,?,?)";

        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement checkstmt = con.prepareStatement(checksql);
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
}


