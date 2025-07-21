package controller;

import gui.SchermataOrganizzatore;
import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.OrganizzatoreDAO;
import implementazionepostgresdao.TeamDAO;
import model.*;

import javax.swing.*;
import java.util.List;

public class ControllerSchermataOrganizzatore {
    private final SchermataOrganizzatore schermataOrganizzatore;

    private final MainController mainController;
    private final HackathonDAO hdao = new HackathonDAO();
    private final OrganizzatoreDAO odao = new OrganizzatoreDAO();
    private final TeamDAO tdao = new TeamDAO();

    public ControllerSchermataOrganizzatore(MainController mainController, Organizzatore organizzatore) {
        this.mainController = mainController;
        this.schermataOrganizzatore = new SchermataOrganizzatore(this,organizzatore);
    }
    public JPanel getSchermataOrganizzatore() {
        return schermataOrganizzatore.getMainPanel();
    }

    public void schermataOrganizzaHackathon(Organizzatore organizzatore) {
        if(organizzatore.getHackathonID() == null)
            mainController.showOrganizzaHackathon(organizzatore);
        else JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(),"È già l'organizzatore di un Hackathon");
    }

    public void logout() {
        mainController.logout();
    }

    public void mostraProblemaHackathon(Organizzatore organizzatore) {
        if(organizzatore.getHackathonID() != null) {
            Hackathon h = hdao.getHackathonByID(organizzatore.getHackathonID());
            String problema = h.getProblema();
            String problemaHTML = "<html>" + problema.replaceAll("(.{50})", "$1<br>") + "</html>";
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), problemaHTML);
        } else {
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(),"Non gestisci nessun Hackathon");
        }
    }

    public void getSchermataGestioneHack(Organizzatore organizzatore) {
        if(organizzatore.getHackathonID() != null) mainController.showSchermataGestioneHack(organizzatore);
        else JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(),"Al momento non sta gestendo alcun Hackathon");
    }

    public void getClassifica(Organizzatore organizzatore) {
        if(organizzatore.getHackathonID() == null) {
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), "Al momento non sta gestendo alcun Hackathon!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(!hdao.isClassificaPubblicata(organizzatore.getHackathonID())) JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), "Classifica non ancora pubblicata!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        else mainController.showSchermataClassifica(organizzatore.getHackathonID(), organizzatore);
    }

    public void pubblicaClassifica(Organizzatore organizzatore){
        if(organizzatore.getHackathonID() == null) {
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), "Al momento non sta gestendo alcun Hackathon!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        List<Team> teams = hdao.getTeamByHackathon(organizzatore.getHackathonID());

        if(teams.isEmpty()) {
            JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), "Nessun Team iscritto!", "Error", JOptionPane.ERROR_MESSAGE);
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
        JOptionPane.showMessageDialog(schermataOrganizzatore.getMainPanel(), "Classifica già pubblicata!", "Info", JOptionPane.INFORMATION_MESSAGE);

    }

} //modificato
