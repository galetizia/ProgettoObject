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
    private final Utente utente;
    private final TeamDAO tdao = new TeamDAO();
    private final HackathonDAO hdao = new HackathonDAO();
    private final UtenteDAO udao = new UtenteDAO();

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
            return;
        }
        List<Team> teams = hdao.getTeamByHackathon(id);

        if(teams.size() >= hdao.getMaxDimTeam(id)) {
            JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Raggiunto numero massimo di Team" , "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Team t = new Team(nome,id);
        tdao.caricaTeamNelDB(t,utente);
        JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Il team caricato con successo","Success", JOptionPane.INFORMATION_MESSAGE);
        showUtente();
    }

    public void visualizzaHackathonAttive(JList<String> list, DefaultListModel<String> modelList) {
        List<Hackathon> hackathons = hdao.getHackathons();
        modelList.clear();
        for (Hackathon h : hackathons) {
            List<Team> teams = hdao.getTeamByHackathon(h.getID());
            modelList.addElement(h.getNome()+" (ID: "+h.getID()+") "+"("+teams.size()+"/"+hdao.getMaxIscritti(h.getID())+")");
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
            Team t = tdao.getTeamByID(id);
            if(t == null) {
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Team non iscritto all Hackathon" , "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Utente> teams = tdao.membriTeam(id);

            if(teams.size() >= (hdao.getMaxDimTeam(hdao.getHackathonByTeam(id)))) {
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Team Pieno" , "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                udao.changeIDTeam(t, utente);
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Sei stato aggiunto al Team: "+t.getNome(), "Success", JOptionPane.INFORMATION_MESSAGE);
                showUtente();
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
            List<Team> teams = hdao.getTeamByHackathon(hackathonID);
            modelList.clear();
            if(teams.isEmpty()) {
                JOptionPane.showMessageDialog(schermataIscrizioneTeam.getMainPanel(), "Nessun Team iscritto a quest Hackathon!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            for (Team t : teams) {
                modelList.addElement(t.getNome()+" (ID: "+t.getId()+") "+"("+teams.size()+"/"+hdao.getMaxDimTeam(hackathonID)+")");
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
