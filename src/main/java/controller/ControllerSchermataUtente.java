package controller;

import gui.SchermataUtente;
import implementazionepostgresdao.*;
import model.*;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;

public class ControllerSchermataUtente {
    private final SchermataUtente schermataUtente;
    TeamDAO tdao = new TeamDAO();
    UtenteDAO udao = new UtenteDAO();

    private final MainController mainController;

    public ControllerSchermataUtente(MainController mainController, Utente utente) {
        this.mainController = mainController;
        this.schermataUtente = new SchermataUtente(this,utente);
    }
    public JPanel getSchermataUtente() {
        return schermataUtente.getMainPanel();
    }

    public void schermataTeamUtente(Utente utente){
        if(utente.getTeamID() == null) JOptionPane.showMessageDialog(schermataUtente.getMainPanel(),"Non fai ancora parte di un team");
        else {
            Team team = tdao.getTeamByID(utente.getTeamID());
            mainController.showTeamSchermataUtente(team, utente);
        }
    }

    public void mostraProblemaHackathon(Utente utente,JTextArea testoCentrale){

        if(utente.getHackathonID() != null) {
            String problema = udao.getHackathonProblemByID(utente.getHackathonID());
            testoCentrale.setText("Problema:\n" + problema);
            testoCentrale.setLineWrap(true);
            testoCentrale.setWrapStyleWord(true);
            testoCentrale.setEditable(false);
        } else {
            JOptionPane.showMessageDialog(schermataUtente.getMainPanel(),"Non partecipi a nessun Hackathon");
        }
    }

    public void schermataIscrizioneTeam(Utente utente){
        if(utente.getTeamID() != null) JOptionPane.showMessageDialog(schermataUtente.getMainPanel(),"Hai già un team");

        else mainController.showIscrizioneTeamUtente(utente);

    }

    public void hackathonAttuale(Utente utente, JTextArea testoCentrale){
        if(utente.getHackathonID() != null) {
            String titolo = udao.getHackathonTitleByID(utente.getHackathonID());
            testoCentrale.setText("Hackathon: \n" + titolo + " (ID: " + utente.getHackathonID() + ")");
            testoCentrale.setLineWrap(true);
            testoCentrale.setWrapStyleWord(true);
            testoCentrale.setEditable(false);
        } else {
            JOptionPane.showMessageDialog(schermataUtente.getMainPanel(),"Non partecipi a nessun Hackathon");
        }
    }
    public void logout() {
        mainController.logout();
    }


} //modificato - Fabio (Parametro Utente)
