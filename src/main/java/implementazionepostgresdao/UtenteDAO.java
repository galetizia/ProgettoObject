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
                        rs.getString("password"),
                        rs.getInt("team_id"),
                        rs.getInt("hackathon_id")

                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


