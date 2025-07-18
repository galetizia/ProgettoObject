package gui;

import controller.ControllerIscrizioneTeam;
import model.*;

import javax.swing.*;
import java.awt.*;

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
    private JLabel area;
    private DefaultListModel<String> modelLista;


    public IscrizioneTeam(ControllerIscrizioneTeam controller, Utente utente) {
        mainPanel.setPreferredSize(new Dimension(600,400));
        area.setFont(new Font("Segoe UI", Font.BOLD, 38));

        panelIscrizione.setVisible(false);
        modelLista = new DefaultListModel<>();
        listElenchi.setModel(modelLista);

        confermaButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(e -> {
            String nome = nomeTextField.getText();
            String idTxt = iscrizioneIDTextField.getText();
            controller.creazioneTeam(nome, idTxt);
        });

        hackathonAttiveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        hackathonAttiveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hackathonAttiveButton.addActionListener(e -> controller.visualizzaHackathonAttive(listElenchi, modelLista));

        iscrivitiAdUnTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        iscrivitiAdUnTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iscrivitiAdUnTeamButton.addActionListener(e -> {
            String idTeam = teamIDtextField.getText();
            controller.iscrizioneTeam(idTeam);
        });

        listaTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        listaTeamButton.addActionListener(e -> {
            String idHackathonTxt = hackathonIDtextField.getText();
            controller.visualizzaTeamHackathon(idHackathonTxt, listElenchi, modelLista);
        });
        creaTeamButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        creaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        creaTeamButton.addActionListener(e -> panelIscrizione.setVisible(true));

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> {
            controller.showUtente();
        });
    }

    public void setVisiblePanelElenchi() {
        panelElenchi.setVisible(true);
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }
}
