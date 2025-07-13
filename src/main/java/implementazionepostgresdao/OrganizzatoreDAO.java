package implementazionepostgresdao;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import dao.IOrganizzatoreDAO;
import java.sql.*;

public class OrganizzatoreDAO implements IOrganizzatoreDAO {

    private Connection connection;
    HackathonDAO dao = new HackathonDAO();

    public OrganizzatoreDAO() {}

    public Organizzatore login(String username, String password) {
        String sql = "SELECT * FROM organizzatore WHERE username = ? AND password = ?";

        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Organizzatore(
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
        String checksql="SELECT * FROM organizzatore WHERE username=? OR email=?";
        String insertsql="INSERT INTO organizzatore(nome,cognome,email,username,password) VALUES(?,?,?,?,?)";

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

    public Giudice aggiungiGiudice(String username, Hackathon h){
        Utente u = dao.findUtenteByUsername(username);
        if (u == null) {
            System.out.println("User not found");
            return null;
        }

        String insertsql = "INSERT INTO giudice (nome,cognome,email,username,password,hackathon_id) VALUES (?,?,?,?,?,?)";
        String deletesql = "DELETE FROM utente WHERE username=?";

        try(Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement insertstmt = con.prepareStatement(insertsql);
            PreparedStatement deletestmt = con.prepareStatement(deletesql)) {

            insertstmt.setString(1, u.getNome());
            insertstmt.setString(2, u.getCognome());
            insertstmt.setString(3, u.getEmail());
            insertstmt.setString(4, u.getUsername());
            insertstmt.setString(5, u.getPassword());
            insertstmt.setInt(6, h.getID());

            int rows = insertstmt.executeUpdate();
            if (rows > 0) {

                deletestmt.setString(1, u.getUsername());
                deletestmt.executeUpdate();
                return new Giudice(u.getNome(), u.getCognome(), u.getEmail(), u.getUsername(), u.getPassword(), h.getID());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

        }
    }