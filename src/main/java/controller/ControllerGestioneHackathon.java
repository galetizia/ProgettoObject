package controller;

import gui.GestioneHackathon;
import gui.IscrizioneTeam;
import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.OrganizzatoreDAO;
import model.Organizzatore;

import javax.swing.*;

public class ControllerGestioneHackathon {
    private final GestioneHackathon schermataGestioneHackathon;
    private final MainController mainController;
    private final Organizzatore organizzatoreLoggato;
    OrganizzatoreDAO odao = new OrganizzatoreDAO();

    public ControllerGestioneHackathon(MainController mainController, Organizzatore organizzatore) {
        this.mainController = mainController;
        this.schermataGestioneHackathon = new GestioneHackathon(this, organizzatore);
        this.organizzatoreLoggato = organizzatore;
    }

    public void terminaHackathon(int conferma) {
        if (conferma == JOptionPane.YES_OPTION) {
            odao.terminaHackathon(organizzatoreLoggato.getHackathonID(), organizzatoreLoggato.getUsername());
            organizzatoreLoggato.setHackathonID(null);
            JOptionPane.showMessageDialog(schermataGestioneHackathon.getMainPanel(), "Hai terminato l'Hackathon con successo.");
        }
        mainController.showSchermataOrganizzatore(organizzatoreLoggato);
    }

    public JPanel getGestioneHackathon() {
        return schermataGestioneHackathon.getMainPanel();
    }

    public void getSchermataOrganizzatore() {
        mainController.showSchermataOrganizzatore(organizzatoreLoggato);
    }

}
