package controller;

import gui.SchermataOrganizzatore;
import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.OrganizzatoreDAO;
import implementazionepostgresdao.TeamDAO;
import model.*;

import javax.swing.*;
import java.util.List;

/**
 * Controller per la {@link SchermataOrganizzatore}.
 * Gestisce la logica dell'interfaccia organizzatore,
 * tra cui la gestione o l'organizzazione di un hackathon, pubblicazione della classifica,
 * visualizzazione dei problemi e logout.
 */
public class ControllerSchermataOrganizzatore {

    /** Interfaccia grafica associata all'organizzatore. */
    private final SchermataOrganizzatore schermataOrganizzatore;

    /** Controller principale per la navigazione. */
    private final MainController mainController;

    /** DAO per la gestione degli hackathon. */
    private final HackathonDAO hdao = new HackathonDAO();

    /** DAO per la gestione degli organizzatori. */
    private final OrganizzatoreDAO odao = new OrganizzatoreDAO();

    /** DAO per la gestione dei team. */
    private final TeamDAO tdao = new TeamDAO();

    /**
     * Costruttore della classe ControllerSchermataOrganizzatore.
     *
     * @param mainController il controller principale dell'applicazione
     * @param organizzatore  l'organizzatore loggato
     */
    public ControllerSchermataOrganizzatore(MainController mainController, Organizzatore organizzatore) {
        this.mainController = mainController;
        this.schermataOrganizzatore = new SchermataOrganizzatore(this,organizzatore);
    }

    /**
     * Restituisce il pannello principale della schermata organizzatore.
     *
     * @return il {@link JPanel} principale
     */
    public JPanel getSchermataOrganizzatore() {
        return schermataOrganizzatore.getMainPanel();
    }

    /**
     * Mostra la schermata per organizzare un nuovo hackathon.
     * Se l'organizzatore ne sta già gestendo uno, mostra un messaggio di warning.
     *
     * @param organizzatore l'organizzatore corrente
     */
    public void schermataOrganizzaHackathon(Organizzatore organizzatore) {
        if(organizzatore.getHackathonID() == null)
            mainController.showOrganizzaHackathon(organizzatore);
        else JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(),"È già l'organizzatore di un Hackathon", "Attenzione", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Esegue il logout dell'organizzatore e torna alla schermata iniziale.
     */
    public void logout() {
        mainController.logout();
    }

    /**
     * Mostra il problema associato all'hackathon gestito.
     * Se non c'è alcun hackathon assegnato, mostra un messaggio di warning.
     *
     * @param organizzatore l'organizzatore corrente
     */
    public void mostraProblemaHackathon(Organizzatore organizzatore) {
        if(organizzatore.getHackathonID() != null) {
            Hackathon h = hdao.getHackathonByID(organizzatore.getHackathonID());
            String problema = h.getProblema();
            String problemaHTML = "<html>" + problema.replaceAll("(.{50})", "$1<br>") + "</html>";
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), problemaHTML);
        } else {
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(),"Al momento non sta gestendo alcun Hackathon!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Mostra la schermata di gestione dell'hackathon, se l'organizzatore ne ha uno assegnato.
     * Se non c'è alcun hackathon assegnato, mostra un messaggio di warning.
     *
     * @param organizzatore l'organizzatore corrente
     */
    public void getSchermataGestioneHack(Organizzatore organizzatore) {
        if(organizzatore.getHackathonID() != null) mainController.showSchermataGestioneHack(organizzatore);
        else JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(),"Al momento non sta gestendo alcun Hackathon!", "Attenzione", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Mostra la classifica dell'hackathon, se disponibile e pubblicata.
     * Altrimenti, mostra un messaggio di warning.
     *
     * @param organizzatore l'organizzatore corrente
     */
    public void getClassifica(Organizzatore organizzatore) {
        if(organizzatore.getHackathonID() == null) {
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), "Al momento non sta gestendo alcun Hackathon!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!hdao.isClassificaPubblicata(organizzatore.getHackathonID())) JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), "Classifica non ancora pubblicata!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        else mainController.showSchermataClassifica(organizzatore.getHackathonID(), organizzatore);
    }

    /**
     * Calcola e pubblica la classifica dell'hackathon.
     * Se i voti non sono disponibili per un team, viene assegnato 0.0.
     * Se la classifica è già pubblicata, viene mostrato un messaggio.
     *
     * @param organizzatore l'organizzatore corrente
     */
    public void pubblicaClassifica(Organizzatore organizzatore){
        if(organizzatore.getHackathonID() == null) {
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), "Al momento non sta gestendo alcun Hackathon!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Team> teams = hdao.getTeamByHackathon(organizzatore.getHackathonID());

        if(teams.isEmpty()) {
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), "Nessun Team iscritto!", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(!hdao.isClassificaPubblicata(organizzatore.getHackathonID())) {
            for(Team team : teams) {
                odao.setClassifica(organizzatore.getHackathonID());
                int teamID = team.getId();

                List<Double> votiPerTeam = tdao.getVotiPerTeam(teamID);
                if (votiPerTeam.isEmpty()) { tdao.setVotiPerTeam(teamID, 0.00);continue; }

                double somma = 0;
                for(Double voti : votiPerTeam) {
                    somma += voti;
                }
                double media = somma / votiPerTeam.size();
                tdao.setVotiPerTeam(teamID, media);
            }
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), "Classifica pubblicata!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), "Classifica già pubblicata!", "Attenzione", JOptionPane.WARNING_MESSAGE);

    }

}
