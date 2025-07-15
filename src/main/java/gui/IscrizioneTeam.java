package gui;

import controller.ControllerIscrizioneTeam;
import implementazionepostgresdao.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
//import java.util.concurrent.atomic.AtomicReference;

public class IscrizioneTeam {

    private JPanel mainPanel;
    private JButton listaTeamButton;
    private JButton creaTeamButton;
    private JList<String> listElenchi;
    private JButton iscrivitiAdUnTeamButton;
    private JTextField teamIDtextField;
    private JScrollPane panelElenchi;
    private JTextField nomeTextField;
    private JTextField iscrizioneIDTextField;
    private JButton confermaButton;
    private JTextField hackathonIDtextField;
    private JPanel panelIscrizione;
    private JButton indietroButton;
    private JButton hackathonAttiveButton;

    private DefaultListModel<String> modelLista;
    TeamDAO tdao = new TeamDAO();
    HackathonDAO hdao = new HackathonDAO();
    UtenteDAO udao = new UtenteDAO();

    public IscrizioneTeam(ControllerIscrizioneTeam controller, Utente utente) {
        mainPanel.setPreferredSize(new Dimension(600,400));

        panelIscrizione.setVisible(false);
        modelLista = new DefaultListModel<>();
        listElenchi.setModel(modelLista);

        confermaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(e -> {
            String nome = nomeTextField.getText();
            String idTxt = iscrizioneIDTextField.getText();

            if(nome.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Inserire un nome");
                return;
            }
            int id;
            try{
                id = Integer.parseInt(idTxt);

            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(mainPanel, "L'ID deve essere un numero intero valido", "Errore di formato", JOptionPane.ERROR_MESSAGE);
                return;
            }


            if(hdao.getHackathonByID(id) == null) {
                JOptionPane.showMessageDialog(mainPanel, "ID hackathon non valido" , "Error", JOptionPane.ERROR_MESSAGE);
            } else{
                Team t = new Team(nome,id);
                tdao.caricaTeamNelDB(t,utente);
                JOptionPane.showMessageDialog(mainPanel, "Il team caricato con successo","Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        hackathonAttiveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hackathonAttiveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hackathonAttiveButton.addActionListener(e -> {
            List<Hackathon> hackathons = hdao.getHackathons();
            modelLista.clear();
            for (Hackathon h : hackathons) {
                modelLista.addElement(h.getNome()+" (ID: "+h.getID()+")");
            }
            listElenchi.revalidate();
            listElenchi.repaint();
            panelElenchi.setVisible(true);

        });

        iscrivitiAdUnTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        iscrivitiAdUnTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iscrivitiAdUnTeamButton.addActionListener(e -> {
            String idTeams = teamIDtextField.getText();

            if(idTeams.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Inserisci un ID di un team" , "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int idteam= Integer.parseInt(idTeams);
                List<Team> teams = tdao.getTeamByHackathon(idteam);
                if(teams.size() == (hdao.getMaxDimTeam(tdao.getHackathonByTeam(idteam)))) {
                    JOptionPane.showMessageDialog(mainPanel, "Team Pieno" , "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Team t = tdao.getTeamByID(idteam);
                    udao.changeIDTeam(t, utente);

                    JOptionPane.showMessageDialog(mainPanel, "Sei stato aggiunto al Team: "+t.getNome(), "Success", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "L'ID Hackathon deve essere un numero valido.", "Errore di formato", JOptionPane.ERROR_MESSAGE);
            }

        });

        listaTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        listaTeamButton.addActionListener(e -> {
            String idHackathon = hackathonIDtextField.getText();

            if(idHackathon.isEmpty()) {
                JOptionPane.showMessageDialog(mainPanel, "Inserisci un ID Hackathon" , "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {

                int hackathonID = Integer.parseInt(idHackathon);
                List<Team> teams = tdao.getTeamByHackathon(hackathonID);
                modelLista.clear();

                for (Team t : teams) {
                    modelLista.addElement(t.getNome()+" (ID: "+t.getId()+")");
                }

                listElenchi.revalidate();
                listElenchi.repaint();
                panelElenchi.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "L'ID Hackathon deve essere un numero valido.", "Errore di formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        creaTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        creaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        creaTeamButton.addActionListener(e -> {
            panelIscrizione.setVisible(true);
        });

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> {
            controller.showUtente();
        });
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }
}
