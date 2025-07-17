package controller;

import gui.IscrizioneTeam;

import implementazionepostgresdao.HackathonDAO;
import implementazionepostgresdao.TeamDAO;
import implementazionepostgresdao.UtenteDAO;
import model.Hackathon;
import model.Team;
import model.Utente;

import javax.swing.*;
import java.util.List;

public class ControllerIscrizioneTeam {

    private final IscrizioneTeam schermataIscrizioneTeam;

    private final MainController mainController;
    private Utente utente;
    private TeamDAO tdao = new TeamDAO();
    private HackathonDAO hdao = new HackathonDAO();
    private UtenteDAO udao = new UtenteDAO();

    public ControllerIscrizioneTeam(MainController mainController, Utente utente) {
        this.mainController = mainController;
        this.schermataIscrizioneTeam = new IscrizioneTeam(this, utente);
        this.utente=utente;
    }

    public void creazioneTeam(String nome, String idHackathon) {
        if(nome.isEmpty()) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Inserire un nome");
            return;
        }
        int id;
        try{
            id = Integer.parseInt(idHackathon);
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "L'ID deve essere un numero intero valido", "Errore di formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(hdao.getHackathonByID(id) == null) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "ID hackathon non valido" , "Error", JOptionPane.ERROR_MESSAGE);
        } else{
            Team t = new Team(nome,id);
            tdao.caricaTeamNelDB(t,utente);
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Il team caricato con successo","Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void visualizzaHackathonAttive(JList<String> list, DefaultListModel<String> modelList) {
        List<Hackathon> hackathons = hdao.getHackathons();
        modelList.clear();
        for (Hackathon h : hackathons) {
            modelList.addElement(h.getNome()+" (ID: "+h.getID()+")");
        }
        list.revalidate();
        list.repaint();
        schermataIscrizioneTeam.setVisiblePanelElenchi();

    }


    public void iscrizioneTeam(String idTeamTxt) {
        if(idTeamTxt.isEmpty()) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Inserisci un ID di un team" , "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int id= Integer.parseInt(idTeamTxt);
            List<Team> teams = tdao.getTeamByHackathon(id);
            if(teams.size() == (hdao.getMaxDimTeam(tdao.getHackathonByTeam(id)))) {
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Team Pieno" , "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Team t = tdao.getTeamByID(id);
                udao.changeIDTeam(t, utente);

                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Sei stato aggiunto al Team: "+t.getNome(), "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "L'ID Hackathon deve essere un numero valido.", "Errore di formato", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void visualizzaTeamHackathon(String idHackathonTxt, JList<String> list,DefaultListModel<String> modelList) {
        if(idHackathonTxt.isEmpty()) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Inserisci un ID Hackathon" , "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int hackathonID = Integer.parseInt(idHackathonTxt);
            List<Team> teams = tdao.getTeamByHackathon(hackathonID);
            modelList.clear();

            for (Team t : teams) {
                modelList.addElement(t.getNome()+" (ID: "+t.getId()+")");
            }

            list.revalidate();
            list.repaint();
            schermataIscrizioneTeam.setVisiblePanelElenchi();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "L'ID Hackathon deve essere un numero valido.", "Errore di formato", JOptionPane.ERROR_MESSAGE);
        }


    }

    public JPanel getIscrizioneTeam() {return schermataIscrizioneTeam.getMainPanel();}

    public void showUtente() {mainController.showSchermataUtente(utente); }

}
