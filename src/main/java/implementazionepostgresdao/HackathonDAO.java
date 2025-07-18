package implementazionepostgresdao;

import dao.IHackathonDAO;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.ConnessioneDatabase;

public class HackathonDAO implements IHackathonDAO {

    private Connection connection;
    public HackathonDAO() {}
    TeamDAO tdao = new TeamDAO();


    @Override
    public Hackathon getHackathonByID(Integer id) {
        String sql = "SELECT * FROM hackathon WHERE id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Date sqlDatei =  rs.getDate("data_inizio");
                LocalDate dataInizio = sqlDatei.toLocalDate();

                Date sqlDatef = rs.getDate("data_fine");
                LocalDate dataFine = sqlDatef.toLocalDate();
                Hackathon h = new Hackathon(rs.getString("titolo"),
                        rs.getString("sede"),
                        dataInizio,
                        dataFine,
                        rs.getString("problema"),
                        rs.getInt("max_iscritti"),
                        rs.getInt("max_dim_team")
                );
                h.setID(rs.getInt("id"));
                return h;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Utente findUtenteByUsername(String username) {
        String sql = "SELECT * FROM utente WHERE username = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Utente u = new Utente(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                u.setHackathonID(rs.getInt("hackathon_id"));
                u.setTeamID(rs.getInt("team_id"));

                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Organizzatore findOrganizzatoreByUsername(String username) {
        String sql = "SELECT * FROM organizzatore WHERE username = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Organizzatore o = new Organizzatore(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                o.setHackathonID(rs.getInt("hackathon_id"));

                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utente findUtenteByEmail(String email) {
        String sql = "SELECT * FROM utente WHERE email = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Utente u = new Utente(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                int hackathonId = rs.getInt("hackathon_id");
                if (rs.wasNull()) u.setHackathonID(null);
                else u.setHackathonID(hackathonId);


                int teamId = rs.getInt("team_id");
                if (rs.wasNull()) u.setTeamID(null);
                else u.setTeamID(teamId);

                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Organizzatore findOrganizzatoreByEmail(String email) {
        String sql = "SELECT * FROM organizzatore WHERE email = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Organizzatore o = new Organizzatore(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                int hackathonId = rs.getInt("hackathon_id");
                if (rs.wasNull()) o.setHackathonID(null);
                else o.setHackathonID(hackathonId);


                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Giudice findGiudiceByUsername(String username) {
        String sql = "SELECT * FROM giudice WHERE username = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Giudice(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("hackathon_id")
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Giudice findGiudiceByEmail(String email) {
        String sql = "SELECT * FROM giudice WHERE email = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Giudice(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("hackathon_id")
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getMaxDimTeam(Integer ID) {

        String sql = "SELECT max_dim_team FROM hackathon WHERE id = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
               return rs.getInt("max_dim_team");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Hackathon> getHackathons() {

        String sql = "SELECT * FROM hackathon";
        List<Hackathon> hackathons = new ArrayList<>();

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Date sqlDatei =  rs.getDate("data_inizio");
                LocalDate dataInizio = sqlDatei.toLocalDate();

                Date sqlDatef = rs.getDate("data_fine");
                LocalDate dataFine = sqlDatef.toLocalDate();
                Hackathon h = new Hackathon(rs.getString("titolo"),
                        rs.getString("sede"),
                        dataInizio,
                        dataFine,
                        rs.getString("problema"),
                        rs.getInt("max_iscritti"),
                        rs.getInt("max_dim_team"));
                h.setID(rs.getInt("id"));
                hackathons.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hackathons;
    }

    public void caricaHackathonDB(Hackathon hackathon, Organizzatore organizzatore) {
        String sql = "INSERT INTO hackathon (titolo, sede, problema, data_inizio, data_fine, fine_periodo_prenotazioni, max_iscritti, max_dim_team, username_organizzatore) VALUES (?,?,?,?,?,?,?,?,?) RETURNING id";
        String organizzatoreSQL = "UPDATE organizzatore SET hackathon_id = ? WHERE username = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql);
             PreparedStatement ostmt = con.prepareStatement(organizzatoreSQL)) {

            stmt.setString(1, hackathon.getNome());
            stmt.setString(2, hackathon.getSede());
            stmt.setString(3, hackathon.getProblema());
            stmt.setDate(4, java.sql.Date.valueOf(hackathon.getDataInizio()));
            stmt.setDate(5, java.sql.Date.valueOf(hackathon.getDataFine()));
            stmt.setDate(6, java.sql.Date.valueOf(hackathon.getFinePeriodoPrenotazioni()));
            stmt.setInt(7, hackathon.getMaxIscritti());
            stmt.setInt(8, hackathon.getMaxDimTeam());
            stmt.setString(9, organizzatore.getUsername());
            ResultSet rs=stmt.executeQuery();

            if (rs.next()) {
                int generatedId = rs.getInt(1);
                hackathon.setID(generatedId);
                organizzatore.setHackathonID(generatedId);
            } else {
                throw new SQLException("Inserimento hackathon fallito, nessun ID restituito.");
            }
            ostmt.setInt(1, hackathon.getID());
            ostmt.setString(2, organizzatore.getUsername());

            ostmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Utente> getUtenti(Integer id) {
        String sql = "SELECT * FROM utente WHERE hackathon_id = ?";
        List<Utente> utenti = new ArrayList<>();
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utente u = new Utente(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                u.setHackathonID(rs.getInt("hackathon_id"));
                int teamId = rs.getInt("team_id");
                if (rs.wasNull()) u.setTeamID(null);
                else u.setTeamID(teamId);

                utenti.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utenti;
    }


    public List<Giudice> getGiudici(Integer id) {
        String sql = "SELECT * FROM giudice WHERE hackathon_id = ?";
        List<Giudice> giudici = new ArrayList<>();
        try (Connection con = ConnessioneDatabase.getInstance().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Giudice g = new Giudice(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("hackathon_id")
                );
                giudici.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return giudici;
    }

}
