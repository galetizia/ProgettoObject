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

    public ControllerClassifica(MainController mainController, Integer hackathonID, Runnable azioneIndietro) {
        this.mainController = mainController;
        this.hackathonID = hackathonID;
        this.classifica = new Classifica(this, hackathonID, azioneIndietro);
    }

    public JPanel getSchermataClassifica(){return classifica.getMainPanel();}

    public void mostraClassifica(JList<String> list, DefaultListModel<String> modelList, JScrollPane panel){

        List<Team> teams = hdao.getClassificaTeams(hackathonID);

        modelList.clear();

        int posizioniClassifica = 0;

        for(Team t : teams){
            modelList.addElement((++posizioniClassifica) + "° Posizione) " + "ID: " + t.getId() + " - Team: " + t.getNome() + " - MediaVoti: " + t.getMediaVoti());
        }
        list.revalidate();
        list.repaint();
        panel.setVisible(true);
    }
    public void indietro(){

    }



}
