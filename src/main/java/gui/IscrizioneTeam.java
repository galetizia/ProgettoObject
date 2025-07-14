package gui;

import controller.ControllerIscrizioneTeam;
import implementazionepostgresdao.TeamDAO;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class IscrizioneTeam {

    private JPanel mainPanel;
    private JButton listaTeamButton;
    private JButton creaTeamButton;
    private JList<String> listTeam;
    private JButton iscrivitiAdUnTeamButton;
    private JTextField teamIDtextField;
    private JScrollPane panelTeam;
    private JTextField nomeTextField;
    private JTextField iscrizioneIDTextField;
    private JButton confermaButton;
    private JTextField hackathonIDtextField;
    private JPanel panelIscrizione;


    private DefaultListModel<String> modelListaTeam;
    TeamDAO tdao = new TeamDAO();

    public IscrizioneTeam(ControllerIscrizioneTeam controller, Utente utente) {
        mainPanel.setPreferredSize(new Dimension(600,400));

        panelIscrizione.setVisible(false);
        modelListaTeam = new DefaultListModel<>();
        listTeam.setModel(modelListaTeam);


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
                System.out.println(hackathonID);
                modelListaTeam.clear();

                for (Team t : teams) {
                    modelListaTeam.addElement(t.getNome());
                }

                listTeam.revalidate();
                listTeam.repaint();
                panelTeam.setVisible(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "L'ID Hackathon deve essere un numero valido.", "Errore di formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        creaTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        creaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        creaTeamButton.addActionListener(e -> {
            panelIscrizione.setVisible(true);
        });
    }



    public JPanel gerMainPanel(){
        return mainPanel;
    }
}
