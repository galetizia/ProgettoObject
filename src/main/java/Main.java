import model.*;
import java.util.ArrayList;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        ArrayList<Utente> partecipanti = new ArrayList<>(); //creiamo un arraylist dove andremo a inserire tutti gli utenti

        Utente u = new Utente("Giancarlo", "Felice", "Felice@gmail.com", "Felix", "GiaNfeliCe");
        partecipanti.add(u);
        Utente u1 = new Utente("Mario", "Rossi", "Rossi@gmail.com", "MarioRossi", "RoSsiMaRio");
        partecipanti.add(u1);
        Utente u2 = new Utente("Margherita", "Esposito", "Esposito@gmail.com", "Margherita", "MarGesP");
        partecipanti.add(u2);
        Utente u3 = new Utente("Fabio", "Iannicelli", "Fabio@gmail.com", "Fabi", "IanNicEllI");
        partecipanti.add(u3);
        Utente u4 = new Utente("Gabriele", "Letizia", "Gabriele@gmail.com", "Gabri", "LeTiZia");
        partecipanti.add(u4);


        System.out.println("\nStampa dei partecipanti prima della rimozione di " + u.getNome() + " " + u.getCognome() + ": ");
        for (Utente p : partecipanti) { //testiamo che tutti gli utenti creati siano correttamente inseriti nell'arraylist "partecipanti"
            System.out.println("|" + p.ruolo + ": " + p.getNome() + " " + p.getCognome());
        }

        LocalDate dInizio = LocalDate.of(2027, 2, 12); //Creiamo una data di inizio e una data di fine che poi saranno
        LocalDate dFine = LocalDate.of(2027, 2, 15);   // quelle effettive dell'Hackathon

        //Creiamo Hackathon
        Hackathon hackathon = new Hackathon("Primo Hackathon", "Napoli", dInizio, dFine, 200, 10);


        Organizzatore organizzatore = new Organizzatore("Francesco", "Ricciardi", "Ricciardi@gmail.com", "Ricciardi", "FrArIcC");


        Giudice g = organizzatore.aggiungiGiudice(u, partecipanti, hackathon); //l'organizzatore promuove un partecipante a giudice
        Giudice g1= organizzatore.aggiungiGiudice(u1, partecipanti, hackathon);


        System.out.println("\nStampa dei partecipanti post rimozione di " + u.getNome() + " " + u.getCognome() + ": ");
        for (Utente p : partecipanti) { //testiamo che sia stato rimosso l'utente diventato giudice
            System.out.println("|" + p.ruolo + " " + p.getNome() + " " + p.getCognome());
        }

        System.out.println("\n" + organizzatore.ruolo + ": " + organizzatore.getNome() + " " + organizzatore.getCognome());


        g.pubblicaProblema("Traccia problema", hackathon);
        System.out.println("\nQuesta Hackathon inizia il " + dInizio + " e termina il " + dFine);
        System.out.println("Il problema di questo hackathon è: " + hackathon.getProblema());
        System.out.println("Iscritti max: " + hackathon.getMaxIscritti());
        System.out.println("Dimensione max Team: " + hackathon.getMaxDimTeam());

        hackathon.stampaGiudici();

        hackathon.iscriviUtente(u1);   //iscriviamo utenti all'Hackathon
        hackathon.iscriviUtente(u2);
        hackathon.iscriviUtente(u3);
        hackathon.iscriviUtente(u4);

        System.out.println();
        Team team = new Team("Team 1", hackathon); //Creiamo il primo Team
        team.aggiungiMembro(u1);
        team.aggiungiMembro(u2);
        team.stampaTeam(); //stampa dell'intero Team

        System.out.println();
        Team team2 = new Team("Team 2", hackathon); //Creiamo un secondo Team
        team2.aggiungiMembro(u3);
        team2.aggiungiMembro(u4);
        team2.stampaTeam(); //stampa dell'intero Team


        hackathon.aggiungiTeam(team); //Iscrivere i Team all'Hackathon
        hackathon.aggiungiTeam(team2);
        System.out.println();
        hackathon.mostraTeam(); //elenco di tutti i team partecipanti (senza indicare partecipanti)


        if (!u.login("Felix", "GiaNfeliCe")) //test del metodo di login di un utente
            System.out.println("\nLogin errato");
        else
            System.out.println("\nLogin successo utente " + u.username);




        System.out.println();

        g.valutaTeam(team, 4);
        g1.valutaTeam(team, 9);
        g1.valutaTeam(team2, 2);
        g.valutaTeam(team2, 3);

        team.stampaVoti();
        team2.stampaVoti();


        if (team.isPieno()) //metodo per controllare che un team non sia pieno
            System.out.println("\nTeam pieno");
        else
            System.out.println("\nTeam non pieno");

        if (team.isVuoto()) //metodo per controllare che un team non sia vuoto
            System.out.println("\nTeam vuoto");
        else
            System.out.println("\nTeam non vuoto");


        if (hackathon.prenotazioniAperte()) //Controllo se le prenotazioni sono aperte o chiuse
            System.out.println("\nPrenotazione aperta\n");
        else
            System.out.println("\nPrenotazione chiusa\n");

        //un team crea periodicamente un aggiornamento del documento e un giudice può valutarlo
        Aggiornamento a = team.creaAggiornamento("Primo aggiornamento", "Prova");
        g.commentaAggiornamento(a, "Ottimo");
        System.out.println("Aggiornamento: " + a.nome); //stampiamo il nome dell'aggiornamento, il documento e il commento
        System.out.println("Documento: " + a.documento + "\n" + "Commento del Giudice: " + a.commento);


        hackathon.pubblicaClassifica();

        System.out.println();
        team.espelliMembro(u1); //metodo per espellere membri da un team
        team.stampaTeam(); //testiamo che sia stato rimosso stampando il team
    }
}
