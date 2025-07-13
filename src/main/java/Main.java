import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.OrganizzatoreDAO;
import implementazionepostgresdao.UtenteDAO;
import model.*;

public class Main {

    public static void main(String[] args) {
        OrganizzatoreDAO dao = new OrganizzatoreDAO();
        HackathonDAO hdao = new HackathonDAO();

        // Test: cerca utente per username
        String username = "luca123";
        String password = "ciao";

        UtenteDAO daou = new UtenteDAO();
        Utente u1 = daou.login(username,password);
        if (u1 != null) System.out.println("Login success user");
        else System.out.println("Login fail user");

        Utente u = hdao.findUtenteByUsername(username);
        if (u != null) {
            System.out.println("Utente trovato:");
            System.out.println("Nome: " + u.getNome());
            System.out.println("Cognome: " + u.getCognome());
            System.out.println("Email: " + u.getEmail());
            System.out.println("Username: " + u.getUsername());
            System.out.println("Hackathon ID: " + u.getHackathonID());
            System.out.println("Team ID: " + u.getTeamID());

        } else {
            System.out.println("Utente non trovato");
        }

        String username2 = "gianx";
        String password2 = "passwordsbagliataperfaretest";

        Organizzatore o = dao.login(username2,password2);
        if (o != null) System.out.println("Login success organizzatore");
        else System.out.println("Login fail organizzatore");


        if(dao.signIn("pippo","baudo","pippobau@gmail.com","pip","pibaudo")){
            System.out.println("Organizzatore registrato");
        }
        else
            System.out.println("Organizzatore non registrato");









    }
}
