package controller;

import gui.IscrizioneTeam;
import implementazionepostgresdao.TeamDAO;
import model.Utente;

public class ControllerIscrizioneTeam {

    private final IscrizioneTeam schermataIscrizioneTeam;

    private final MainController mainController;

    public ControllerIscrizioneTeam(MainController mainController, Utente utente) {
        this.schermataIscrizioneTeam = new IscrizioneTeam(this, utente);
        this.mainController = mainController;
    }

}
