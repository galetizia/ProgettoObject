package gui;

import controller.ControllerGestioneHackathon;
import model.*;

import javax.swing.*;
import java.awt.*;

public class GestioneHackathon {
    private JLabel area;
    private JList<String> list;
    private JButton elencoUtentiButton;
    private JButton elencoGiudiciButton;
    private JButton elencoTeamsButton;
    private JButton rimozioneUtenteGiudiceTeamButton;
    private JTextField idTextField;
    private JButton confermaButton;
    private JButton indietroButton;
    private JLabel idLabel;
    private JScrollPane panelHackathon;
    private JPanel mainpanel;
    private JCheckBox utenteCheckBox;
    private JCheckBox giudiceCheckBox;
    private JCheckBox teamCheckBox;
    private JButton terminaHackathonButton;
    private JButton aggiungiGiudiceButton;
    private JTextField usernameTextField;
    private JButton confermaGiudiceButton;
    private JLabel username;


    private JButton ultimoPulsantePremuto = null;


    private static final String SEGOEUI = "Segoe UI";


    private final DefaultListModel<String> modelList;

    public GestioneHackathon(ControllerGestioneHackathon controller, Organizzatore organizzatore) {
        mainpanel.setPreferredSize(new Dimension(600, 450));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        setVisibilityAggGiudice(false);
        setVisibilityRimozione(false);

        modelList = new DefaultListModel<>();
        list.setModel(modelList);

        aggiungiGiudiceButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        aggiungiGiudiceButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aggiungiGiudiceButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaAggGiudice(idLabel, list, modelList, panelHackathon, username, ultimoPulsantePremuto, aggiungiGiudiceButton));

        confermaGiudiceButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaGiudiceButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaGiudiceButton.addActionListener(ignored -> {
            controller.aggiungiGiudice(usernameTextField, organizzatore);
            controller.mostraPotenzialiGiudici(list, modelList, panelHackathon);
        });

        elencoUtentiButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        elencoUtentiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoUtentiButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaElencoUtenti(username,idLabel, ultimoPulsantePremuto, elencoUtentiButton, list, modelList, organizzatore));

        elencoGiudiciButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        elencoGiudiciButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoGiudiciButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaElencoGiudici(username, idLabel, ultimoPulsantePremuto, elencoGiudiciButton, list, modelList, organizzatore));

        elencoTeamsButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        elencoTeamsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoTeamsButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaElencoTeam(username, idLabel, ultimoPulsantePremuto, elencoTeamsButton, list, modelList, organizzatore));

        terminaHackathonButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        terminaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        terminaHackathonButton.addActionListener(ignored -> controller.terminaHackathon());

        rimozioneUtenteGiudiceTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        rimozioneUtenteGiudiceTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rimozioneUtenteGiudiceTeamButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaRimozione(idLabel, ultimoPulsantePremuto, rimozioneUtenteGiudiceTeamButton, modelList,  utenteCheckBox, giudiceCheckBox, teamCheckBox));

        utenteCheckBox.addActionListener(ignored -> {
            if (utenteCheckBox.isSelected()) {
                teamCheckBox.setSelected(false);
                giudiceCheckBox.setSelected(false);
                controller.mostraUtenti(list, modelList, panelHackathon, organizzatore);
            }
            else
                modelList.clear();
        });

        teamCheckBox.addActionListener(ignored -> {
            if (teamCheckBox.isSelected()) {
                utenteCheckBox.setSelected(false);
                giudiceCheckBox.setSelected(false);
                controller.mostraTeams(list, modelList, panelHackathon, organizzatore);
            }
            else
                modelList.clear();
        });

        giudiceCheckBox.addActionListener(ignored -> {
            if (giudiceCheckBox.isSelected()) {
                utenteCheckBox.setSelected(false);
                teamCheckBox.setSelected(false);
                controller.mostraGiudici(list, modelList, panelHackathon, organizzatore);
            }
            else
                modelList.clear();
        });

        confermaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(ignored -> controller.gestioneRimozioni(utenteCheckBox,giudiceCheckBox,teamCheckBox,idTextField,organizzatore));

        indietroButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> controller.getSchermataOrganizzatore());


    }

    private void setVisibility(boolean visible, JComponent... components) {
        for (JComponent c : components)
            c.setVisible(visible);
    }

    public JPanel getMainPanel() {
        return mainpanel;
    }

    public JScrollPane getPanelHackathon() {
        return panelHackathon;
    }

    public JLabel getUsernameLabel() {
        return username;
    }

    public void setVisibilityRimozione(boolean visible){
        setVisibility(visible, idLabel, idTextField, confermaButton, utenteCheckBox, giudiceCheckBox, teamCheckBox);
    }

    public void setVisibilityAggGiudice(boolean visible){
        setVisibility(visible, username, usernameTextField, confermaGiudiceButton);
    }
}
