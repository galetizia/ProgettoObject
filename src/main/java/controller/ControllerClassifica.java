package controller;

import gui.Classifica;

import implementazionepostgresdao.HackathonDAO;
import model.*;

import javax.swing.*;
import java.util.List;

public class ControllerClassifica {

    private final Classifica classifica;

    private final Integer hackathonID;
    private final HackathonDAO hdao = new HackathonDAO();

    public ControllerClassifica(Integer hackathonID, Runnable azioneIndietro) {

        this.hackathonID = hackathonID;
        this.classifica = new Classifica(this, azioneIndietro);
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

}
