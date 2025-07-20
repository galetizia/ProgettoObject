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
    private JButton ultimoPulsantePremuto = null; //variabile utilizzata per svuotare la lista
                                                  // al secondo click di uno stesso bottone

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
            if(idLabel.isVisible()) {
                idLabel.setVisible(false);
                idTextField.setVisible(false);
                confermaButton.setVisible(false);
                utenteCheckBox.setVisible(false);
                giudiceCheckBox.setVisible(false);
                teamCheckBox.setVisible(false);
            }
            if(!controller.mostraPotenzialiGiudici(list, modelList, panelHackathon)){
                username.setVisible(false);
                usernameTextField.setVisible(false);
                confermaGiudiceButton.setVisible(false);
                modelList.clear();
                return;
            }
            boolean check=!username.isVisible();
            username.setVisible(check);
            usernameTextField.setVisible(check);
            confermaGiudiceButton.setVisible(check);

            if (ultimoPulsantePremuto == aggiungiGiudiceButton) {
                modelList.clear();
                ultimoPulsantePremuto = null;
            } else {
                ultimoPulsantePremuto = aggiungiGiudiceButton;
            }

        });

        confermaGiudiceButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaGiudiceButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaGiudiceButton.addActionListener(e -> {
            controller.aggiungiGiudice(usernameTextField, organizzatore);
            controller.mostraPotenzialiGiudici(list, modelList, panelHackathon);
        });

        elencoUtentiButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        elencoUtentiButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoUtentiButton.addActionListener(e -> {
            if(username.isVisible()) {
                username.setVisible(false);
                usernameTextField.setVisible(false);
                confermaGiudiceButton.setVisible(false);
            }
            if(idLabel.isVisible()) {
                idLabel.setVisible(false);
                idTextField.setVisible(false);
                confermaButton.setVisible(false);
                utenteCheckBox.setVisible(false);
                giudiceCheckBox.setVisible(false);
                teamCheckBox.setVisible(false);
            }

            if (ultimoPulsantePremuto == elencoUtentiButton) {
                modelList.clear();
                ultimoPulsantePremuto = null;
            } else {
                controller.mostraUtenti(list, modelList, panelHackathon, organizzatore);
                ultimoPulsantePremuto = elencoUtentiButton;
            }
        });

        elencoGiudiciButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        elencoGiudiciButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoGiudiciButton.addActionListener(e -> {
            if(username.isVisible()) {
                username.setVisible(false);
                usernameTextField.setVisible(false);
                confermaGiudiceButton.setVisible(false);
            }
            if(idLabel.isVisible()) {
                idLabel.setVisible(false);
                idTextField.setVisible(false);
                confermaButton.setVisible(false);
                utenteCheckBox.setVisible(false);
                giudiceCheckBox.setVisible(false);
                teamCheckBox.setVisible(false);
            }

            if (ultimoPulsantePremuto == elencoGiudiciButton) {
                modelList.clear();
                ultimoPulsantePremuto = null;
            } else {
                controller.mostraGiudici(list, modelList, panelHackathon, organizzatore);
                ultimoPulsantePremuto = elencoGiudiciButton;
            }

        });

        elencoTeamsButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        elencoTeamsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        elencoTeamsButton.addActionListener(e -> {
            if(username.isVisible()) {
                username.setVisible(false);
                usernameTextField.setVisible(false);
                confermaGiudiceButton.setVisible(false);
            }
            if(idLabel.isVisible()) {
                idLabel.setVisible(false);
                idTextField.setVisible(false);
                confermaButton.setVisible(false);
                utenteCheckBox.setVisible(false);
                giudiceCheckBox.setVisible(false);
                teamCheckBox.setVisible(false);
            }
            if (ultimoPulsantePremuto == elencoTeamsButton) {
                modelList.clear();
                ultimoPulsantePremuto = null;
            } else {
                controller.mostraTeams(list, modelList, panelHackathon, organizzatore);
                ultimoPulsantePremuto = elencoTeamsButton;
            }
        });

        terminaHackathonButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        terminaHackathonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        terminaHackathonButton.addActionListener(e -> controller.terminaHackathon());

        rimozioneUtenteGiudiceTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rimozioneUtenteGiudiceTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rimozioneUtenteGiudiceTeamButton.addActionListener(e -> {
            if(username.isVisible()) {
                username.setVisible(false);
                usernameTextField.setVisible(false);
                confermaGiudiceButton.setVisible(false);
            }
            if(!idLabel.isVisible()) {
                idLabel.setVisible(true);
                idTextField.setVisible(true);
                confermaButton.setVisible(true);
                utenteCheckBox.setVisible(true);
                giudiceCheckBox.setVisible(true);
                teamCheckBox.setVisible(true);
            }else {
                idLabel.setVisible(false);
                idTextField.setVisible(false);
                confermaButton.setVisible(false);
                utenteCheckBox.setVisible(false);
                giudiceCheckBox.setVisible(false);
                teamCheckBox.setVisible(false);
            }

            if(!utenteCheckBox.isSelected() && !giudiceCheckBox.isSelected() && !teamCheckBox.isSelected())
                modelList.clear();

            if (ultimoPulsantePremuto == rimozioneUtenteGiudiceTeamButton) {
                modelList.clear();
                ultimoPulsantePremuto = null;
            } else {
                ultimoPulsantePremuto = rimozioneUtenteGiudiceTeamButton;
            }

            mainpanel.revalidate();
            mainpanel.repaint();
        });

        utenteCheckBox.addActionListener(e -> {
            if (utenteCheckBox.isSelected()) {
                teamCheckBox.setSelected(false);
                giudiceCheckBox.setSelected(false);
                controller.mostraUtenti(list, modelList, panelHackathon, organizzatore);
            }
            else
                modelList.clear();
        });

        teamCheckBox.addActionListener(e -> {
            if (teamCheckBox.isSelected()) {
                utenteCheckBox.setSelected(false);
                giudiceCheckBox.setSelected(false);
                controller.mostraTeams(list, modelList, panelHackathon, organizzatore);
            }
            else
                modelList.clear();
        });

        giudiceCheckBox.addActionListener(e -> {
            if (giudiceCheckBox.isSelected()) {
                utenteCheckBox.setSelected(false);
                teamCheckBox.setSelected(false);
                controller.mostraGiudici(list, modelList, panelHackathon, organizzatore);
            }
            else
                modelList.clear();
        });

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
