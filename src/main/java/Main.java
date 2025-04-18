import model.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        ArrayList<Utente> partecipanti = new ArrayList<>();

        Utente u=new Utente("giancarlo","tro","tro@gmail.com",234, "peppino","si");
        Utente u1 =new Utente("gabriele","letizia","ciao@gmail.com",112, "ciao", "ok");
        Utente u2 =new Utente("trallalero","trallala","dddd@gmail.com",233,  "trallala", "no");
        partecipanti.add(u);
        partecipanti.add(u1);
        partecipanti.add(u2);

        Utente u3=new Utente("giancarlo","tro","tro@gmail.com",234, "Il Meme","si");
        Utente u4 =new Utente("gabriele","letizia","ciao@gmail.com",112, "Porfirio", "ok");

        partecipanti.add(u3);
        partecipanti.add(u4);

        Organizzatore o=new Organizzatore("franco","ricciardi","gab@gmail.com",342,"sono", "vivo");

        ArrayList<Giudice> giudici = new ArrayList<>();
        Giudice g=o.aggiungiGiudice(u);
        giudici.add(g);
        partecipanti.remove(u);// per eliminare l'utente poichè diventato giudice

        System.out.println();
        for(Utente p : partecipanti){
            System.out.println(p.ruolo +" " +p.nome + " " + p.cognome + " ID: " + p.id + " " +p.email +" " + p.username + " " + p.password);
        }
        System.out.println();
        for(Giudice p : giudici){
            System.out.println(p.ruolo +": " +p.nome + " " + p.cognome +" ID: " + p.id +" " +p.email +" " +p.username +" " +p.password);
        }

        System.out.println(o.ruolo +": " +o.nome +" " +o.cognome +" ID: " +o.id +" " +o.email +" " +o.username +" " +o.password);


        LocalDate dInizio = LocalDate.of(2027, 2,12);
        LocalDate dFine = LocalDate.of(2027, 2,15);

        String problema="dbjwjbfvfiencineijvnbjejciebiecn";

        Hackathon hackathon = new Hackathon("Primo Hackathon", "Napoli", dInizio, dFine,200,10, problema);
        System.out.println("\nQuesta Hackathon inizia il "+dInizio+" e termina il "+dFine);
        System.out.println("Il problema di questo hackathon è: "+hackathon.getProblema());

        hackathon.iscriviUtente(u1);
        hackathon.iscriviUtente(u2);
        hackathon.iscriviUtente(u3);
        hackathon.iscriviUtente(u4);

        System.out.println();
        Team team=new Team("BRR BRR PATAPIM", hackathon.getMaxDimTeam());
        team.aggiungiMembro(u1);
        team.aggiungiMembro(u2);
        team.stampaTeam();

        System.out.println();
        Team team2= new Team("LIRILì LARILà", hackathon.getMaxDimTeam());
        team2.aggiungiMembro(u3);
        team2.aggiungiMembro(u4);
        team2.stampaTeam();


        System.out.println();

        team.stampaTeam();
        System.out.println(team.getNome());


        hackathon.aggiungiTeam(team);
        hackathon.aggiungiTeam(team2);
        hackathon.mostraTeam(); //elenco di tutti i team partecipanti

        if(!u.login("peppino", "si"))
            System.out.println("\nLogin errato");
        else
            System.out.println("\nLogin successo");

        Voto voto=g.valutaTeam(g,team,30);
        System.out.println(voto.getTeam().getNome());
        System.out.println(voto.getGiudice().nome + " " + voto.getGiudice().cognome);
        System.out.println(voto.getValutazione());

        if(team.isPieno())
            System.out.println("\nTeam pieno");
        else
            System.out.println("\nTeam non pieno");


        if(hackathon.prenotazioniAperte())
            System.out.println("\nPrenotazione aperta");
        else
            System.out.println("\nPrenotazione chiusa");

        Aggiornamento a = team.creaAggiornamento("Primo aggiornamento", "Scimpanzini Bananini");

        g.commentaAggiornamento(a, "Trippi Troppi");

        System.out.println("Aggiornamento: " + a.nome);
        System.out.println("Documento: " + a.documento + "\n" + "Commento del Giudice: " + a.commento);
    }

}
