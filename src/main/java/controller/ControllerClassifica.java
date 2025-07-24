package controller;

import gui.Classifica;
import implementazionepostgresdao.HackathonDAO;
import model.*;

import javax.swing.*;
import java.util.List;

/**
 * Controller per la gestione della schermata di classifica di una hackathon.
 * <p>
 * Si occupa di recuperare i dati della classifica tramite il DAO e aggiornare la GUI
 * per mostrare la posizione e i dettagli dei team partecipanti.
 * </p>
 */
public class ControllerClassifica {

    /** Riferimento alla GUI della classifica */
    private final Classifica classifica;

    /** Identificativo della hackathon per cui mostrare la classifica */
    private final Integer hackathonID;

    /** DAO per l'accesso ai dati della hackathon */
    private final HackathonDAO hdao = new HackathonDAO();

    /**
     * Costruttore del controller.
     *
     * @param hackathonID ID della hackathon per cui gestire la classifica.
     * @param azioneIndietro Runnable eseguito al ritorno alla schermata precedente.
     */
    public ControllerClassifica(Integer hackathonID, Runnable azioneIndietro) {
        this.hackathonID = hackathonID;
        this.classifica = new Classifica(this, azioneIndietro);
    }

    /**
     * Restituisce il pannello principale della schermata classifica.
     *
     * @return Il {@link JPanel} principale della schermata.
     */
    public JPanel getSchermataClassifica() {return classifica.getMainPanel();}

    /**
     * Mostra la classifica dei team in una lista GUI.
     * <p>
     * Recupera i team ordinati per classifica tramite il DAO, aggiorna il modello della lista
     * e rende visibile il pannello contenente la lista.
     * </p>
     *
     * @param list JList dove mostrare la classifica.
     * @param modelList Modello della lista per gestire dinamicamente gli elementi.
     * @param panel JScrollPane contenente la lista da rendere visibile.
     */
    public void mostraClassifica(JList<String> list, DefaultListModel<String> modelList, JScrollPane panel) {
        List<Team> teams = hdao.getClassificaTeams(hackathonID);

        modelList.clear();

        int posizioniClassifica = 0;

        for (Team t : teams) {
            modelList.addElement((++posizioniClassifica) + "° Posizione) "
                    + "ID: " + t.getId()
                    + " - Team: " + t.getNome()
                    + " - MediaVoti: " + t.getMediaVoti());
        }

        list.revalidate();
        list.repaint();
        panel.setVisible(true);
    }

}
