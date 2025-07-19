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

    private final DefaultListModel<String> modelList;

    public GestioneHackathon(ControllerGestioneHackathon controller, Organizzatore organizzatore) {
        mainpanel.setPreferredSize(new Dimension(600, 450));
        area.setFont(new Font("Segoe UI", Font.BOLD, 38));

        idLabel.setVisible(false);
        idTextField.setVisible(false);
        confermaButton.setVisible(false);
        utenteCheckBox.setVisible(false);
        giudiceCheckBox.setVisible(false);
        teamCheckBox.setVisible(false);
        username.setVisible(false);
        usernameTextField.setVisible(false);
        confermaGiudiceButton.setVisible(false);

        modelList = new DefaultListModel<>();
        list.setModel(modelList);

        aggiungiGiudiceButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        aggiungiGiudiceButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aggiungiGiudiceButton.addActionListener(e ->{
            idLabel.setVisible(false);
            idTextField.setVisible(false);
            confermaButton.setVisible(false);
            utenteCheckBox.setVisible(false);
            giudiceCheckBox.setVisible(false);
            teamCheckBox.setVisible(false);
            if(!controller.mostraPotenzialiGiudici(list, modelList, panelHackathon)){
                username.setVisible(false);
                usernameTextField.setVisible(false);
                confermaGiudiceButton.setVisible(false);
                return;
            }
            boolean check=!username.isVisible();
            username.setVisible(!check);
            usernameTextField.setVisible(!check);
            confermaGiudiceButton.setVisible(!check);
        });

        confermaGiudiceButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaGiudiceButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaGiudiceButton.addActionListener(e -> controller.aggiungiGiudice(usernameTextField, organizzatore));

        elencoUtentiButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        elencoUtentiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoUtentiButton.addActionListener(e -> {
                controller.mostraUtenti(list, modelList, panelHackathon, organizzatore);
        });

        elencoGiudiciButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        elencoGiudiciButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoGiudiciButton.addActionListener(e -> {
            controller.mostraGiudici(list, modelList, panelHackathon, organizzatore);
        });

        elencoTeamsButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        elencoTeamsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoTeamsButton.addActionListener(e -> {
            controller.mostraTeams(list, modelList, panelHackathon, organizzatore);
        });

        terminaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        terminaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        terminaHackathonButton.addActionListener(e -> controller.terminaHackathon());

        rimozioneUtenteGiudiceTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rimozioneUtenteGiudiceTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rimozioneUtenteGiudiceTeamButton.addActionListener(e -> {
            username.setVisible(false);
            usernameTextField.setVisible(false);
            confermaGiudiceButton.setVisible(false);
            if(!idLabel.isVisible()) {
                idLabel.setVisible(true);
                idTextField.setVisible(true);
                confermaButton.setVisible(true);
                utenteCheckBox.setVisible(true);
                giudiceCheckBox.setVisible(true);
                teamCheckBox.setVisible(true);
                return;
            }
                idLabel.setVisible(false);
                idTextField.setVisible(false);
                confermaButton.setVisible(false);
                utenteCheckBox.setVisible(false);
                giudiceCheckBox.setVisible(false);
                teamCheckBox.setVisible(false);

            mainpanel.revalidate();
            mainpanel.repaint();
        });

        utenteCheckBox.addActionListener(e -> {
            if (utenteCheckBox.isSelected()) { teamCheckBox.setSelected(false); giudiceCheckBox.setSelected(false); }});

        teamCheckBox.addActionListener(e -> {
            if (teamCheckBox.isSelected()) { utenteCheckBox.setSelected(false); giudiceCheckBox.setSelected(false); }});

        giudiceCheckBox.addActionListener(e -> {
            if (giudiceCheckBox.isSelected()) { utenteCheckBox.setSelected(false); teamCheckBox.setSelected(false); }});

        confermaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(e -> controller.gestioneRimozioni(utenteCheckBox,giudiceCheckBox,teamCheckBox,idTextField,organizzatore));

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> controller.getSchermataOrganizzatore());


    }
    public JPanel getMainPanel() {
        return mainpanel;
    }
}
