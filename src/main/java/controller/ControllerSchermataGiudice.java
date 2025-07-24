package controller;

import gui.SchermataGiudice;
import implementazionepostgresdao.HackathonDAO;
import model.*;
import javax.swing.*;

/**
 * Controller per la {@link SchermataGiudice}.
 * Gestisce le interazioni dell'interfaccia giudice,
 * tra cui la visualizzazione dei problemi, l'inserimento di voti/commenti,
 * l'accesso alla classifica e il logout.
 */
public class ControllerSchermataGiudice {

    /** Interfaccia grafica della schermata del giudice. */
    private final SchermataGiudice schermataGiudice;

    /** Controller principale dell'applicazione. */
    private final MainController mainController;

    /** DAO per le operazioni sugli hackathon. */
    private final HackathonDAO hdao = new HackathonDAO();

    /**
     * Costruttore della classe ControllerSchermataGiudice.
     *
     * @param mainController il controller principale
     * @param giudice        il giudice loggato
     */
    public ControllerSchermataGiudice(MainController mainController, Giudice giudice) {
        this.mainController = mainController;
        schermataGiudice = new SchermataGiudice(this, giudice);
    }

    /**
     * Restituisce il pannello principale della schermata del giudice.
     *
     * @return il {@link JPanel} principale
     */
    public JPanel getSchermataGiudice() {
        return schermataGiudice.getMainPanel();
    }

    /**
     * Mostra la schermata per l'inserimento di voti e commenti da parte del giudice.
     *
     * @param giudice il giudice corrente
     */
    public void showSchermataVotiCommenti(Giudice giudice) {
        mainController.showSchermataVotiCommenti(giudice);
    }

    /**
     * Mostra il problema proposto nell'hackathon assegnato al giudice.
     * Se il giudice non è assegnato ad alcun hackathon, mostra un messaggio di warning.
     *
     * @param giudice   il giudice corrente
     * @param hackathon l'hackathon associato
     */
    public void problemaHackathon(Giudice giudice, Hackathon hackathon) {
        if(giudice.getHackathonID() != null) {
            String problema = hackathon.getProblema();
            String problemaHTML = "<html>" + problema.replaceAll("(.{50})", "$1<br>") + "</html>";
            JOptionPane.showMessageDialog(schermataGiudice.getMainPanel(), problemaHTML);
        } else {
            JOptionPane.showMessageDialog(schermataGiudice.getMainPanel(),"Non partecipi a nessun Hackathon!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Mostra la classifica dell'hackathon se questa è stata pubblicata.
     * In caso contrario, mostra un messaggio di warning.
     *
     * @param giudice il giudice corrente
     */
    public void getClassifica(Giudice giudice){
        if(!hdao.isClassificaPubblicata(giudice.getHackathonID())){
            JOptionPane.showMessageDialog(schermataGiudice.getMainPanel(), "Classifica non ancora pubblicata!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }else{
            mainController.showSchermataClassifica(giudice.getHackathonID(), giudice);
        }
    }

    /**
     * Esegue il logout del giudice e ritorna alla schermata iniziale.
     */
    public void logout(){mainController.logout();}


}
