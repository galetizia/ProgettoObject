package controller;

import gui.SchermataGiudice;
import implementazionepostgresdao.HackathonDAO;
import model.*;
import javax.swing.*;

public class ControllerSchermataGiudice {
    private final SchermataGiudice schermataGiudice;

    private final MainController mainController;
    private final HackathonDAO hdao = new HackathonDAO();

    public ControllerSchermataGiudice(MainController mainController, Giudice giudice) {
        this.mainController = mainController;
        schermataGiudice = new SchermataGiudice(this, giudice);
    }
    public JPanel getSchermataGiudice() {
        return schermataGiudice.getMainPanel();
    }

    public void showSchermataVotiCommenti(Giudice giudice) {
        mainController.showSchermataVotiCommenti(giudice);
    }

    public void problemaHackathon(Giudice giudice, Hackathon hackathon) {
        if(giudice.getHackathonID() != null) {
            String problema = hackathon.getProblema();
            String problemaHTML = "<html>" + problema.replaceAll("(.{50})", "$1<br>") + "</html>";
            JOptionPane.showMessageDialog(schermataGiudice.getMainPanel(), problemaHTML);
        } else {
            JOptionPane.showMessageDialog(schermataGiudice.getMainPanel(),"Non partecipi a nessun Hackathon");
        }
    }

    public void getClassifica(Giudice giudice){
        if(!hdao.isClassificaPubblicata(giudice.getHackathonID())){
            JOptionPane.showMessageDialog(schermataGiudice.getMainPanel(), "Classifica non ancora pubblicata!", "Attenzione", JOptionPane.WARNING_MESSAGE);
        }else{
            mainController.showSchermataClassifica(giudice.getHackathonID(), giudice);
        }
    }

    public void logout(){mainController.logout();}


}
