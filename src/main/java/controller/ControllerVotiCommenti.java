package controller;

import gui.SchermataVotiCommenti;

import implementazionepostgresdao.GiudiceDAO;
import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.TeamDAO;
import implementazionepostgresdao.UtenteDAO;
import model.*;

import javax.swing.*;
import java.util.List;
public class ControllerVotiCommenti {

    private final SchermataVotiCommenti votiCommenti;

    private final MainController mainController;
    private Giudice giudice;
    private TeamDAO tdao = new TeamDAO();
    private HackathonDAO hdao = new HackathonDAO();
    private UtenteDAO udao = new UtenteDAO();
    private GiudiceDAO gdao = new GiudiceDAO();

    public ControllerVotiCommenti(MainController mainController, Giudice giudice) {
        this.mainController = mainController;
        this.votiCommenti = new SchermataVotiCommenti(this, giudice);
        this.giudice = giudice;
    }

    public void getTeams(Giudice giudice, JList<String> listTeams,DefaultListModel<String> modelTeams) {
        List<Team> teams = tdao.getTeamByHackathon(giudice.getHackathonID());
        if(teams.isEmpty()) {
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Nessun Team iscritto!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        modelTeams.clear();
        for (Team team : teams) {
            modelTeams.addElement(team.getNome()+" (ID: "+team.getId()+")");
        }
        listTeams.revalidate();
        listTeams.repaint();
        votiCommenti.setVisiblePanelElenchi();
    }

    public void elaboratiFinali(JList<String> list, DefaultListModel<String> modelList) {
        List<Team> teams = gdao.getElaboratiFinaliTeam(giudice.getHackathonID());
        if(teams.isEmpty()) {
            JOptionPane.showMessageDialog(votiCommenti.getMainPanel(), "Nessun Team ha consegnato l'elaborato finale!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        modelList.clear();
        for (Team team : teams) {
            modelList.addElement(team.getNome()+" (ID: "+team.getId()+")");
        }
        list.revalidate();
        list.repaint();
        votiCommenti.setVisiblePanelElenchi();

    }

    public JPanel getSchermataVotiCommenti() {
        return votiCommenti.getMainPanel();
    }

    public void getSchermataGiudice() {mainController.showSchermataGiudice(giudice);}

}
