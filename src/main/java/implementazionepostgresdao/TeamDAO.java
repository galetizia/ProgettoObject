package implementazionepostgresdao;
import dao.ITeamDAO;
import model.*;
import java.sql.Connection;
import database.ConnessioneDatabase;
import java.sql.*;
public class TeamDAO implements ITeamDAO {
    private Connection connection;
    public TeamDAO() {}

    @Override
    public Team getTeamByID(Integer id){
        String sql = "SELECT * FROM team WHERE id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().connection; PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Team(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("mediaVoti"),
                        rs.getInt("HackathonID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
