import implementazionePostgresDAO.OrganizzatoreDAO;
import model.*;

public class Main {

    public static void main(String[] args) {
        OrganizzatoreDAO dao = new OrganizzatoreDAO();

        // Test: cerca utente per username
        String username = "luca123";
        Utente u = dao.trovaUtentePerUsername(username);
        if (u != null) {
            System.out.println("Utente trovato:");
            System.out.println("Nome: " + u.getNome());
            System.out.println("Cognome: " + u.getCognome());
            System.out.println("Email: " + u.getEmail());
            System.out.println("Username: " + u.getUsername());
            System.out.println("Hackathon ID: " + u.getHackathonID());
            System.out.println("Team ID: " + u.getTeamID());
            //ciao

        } else {
            System.out.println("Utente non trovato");
        }

    }
}
