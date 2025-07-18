package controller;

import gui.SchermataUtente;
import implementazionepostgresdao.*;
import model.*;

import javax.swing.*;

public class ControllerSchermataUtente {
    private final SchermataUtente schermataUtente;
    TeamDAO tdao = new TeamDAO();
    UtenteDAO udao = new UtenteDAO();
    HackathonDAO hdao = new HackathonDAO();

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

    public void mostraProblemaHackathon(Utente utente){

        if(utente.getHackathonID() != null) {
            Hackathon h = hdao.getHackathonByID(utente.getHackathonID());
            String problema = h.getProblema();
            String problemaHTML = "<html>" + problema.replaceAll("(.{50})", "$1<br>") + "</html>";
            JOptionPane.showMessageDialog(schermataUtente.getMainPanel(), problemaHTML);
        } else {
            JOptionPane.showMessageDialog(schermataUtente.getMainPanel(),"Non partecipi a nessun Hackathon");
        }
    }

    public void schermataIscrizioneTeam(Utente utente){
        if(utente.getTeamID() != null) JOptionPane.showMessageDialog(schermataUtente.getMainPanel(),"Hai già un team");

        else mainController.showIscrizioneTeamUtente(utente);

    }

    public void logout() {
        mainController.logout();
    }


} //modificato - Fabio (Parametro Utente)
