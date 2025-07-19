package controller;

import gui.Classifica;

import implementazionepostgresdao.GiudiceDAO;
import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.TeamDAO;
import implementazionepostgresdao.UtenteDAO;
import model.*;

import javax.swing.*;
import java.util.List;

public class ControllerClassifica {

    private final Classifica classifica;

    private final MainController mainController;
    private Integer hackathonID;
    private HackathonDAO hdao = new HackathonDAO();

    public ControllerClassifica(MainController mainController, Integer hackathonID) {
        this.mainController = mainController;
        this.classifica = new Classifica(this, hackathonID);
        this.hackathonID = hackathonID;
    }

    public JPanel getSchermataClassifica(){return classifica.getMainPanel();}

    public void mostraClassifica(JList<String> list, DefaultListModel<String> modelList, JScrollPane panel){

        List<Team> teams = hdao.getClassificaTeams(hackathonID);

        modelList.clear();

        int posizioniClassifica = 0;

        for(Team t : teams){
            modelList.addElement((++posizioniClassifica) + ") " + "ID: " + t.getId() + " - Team: " + t.getNome() + " - MediaVoti: " + t.getMediaVoti());
        }
        list.revalidate();
        list.repaint();
        panel.setVisible(true);
    }

}
