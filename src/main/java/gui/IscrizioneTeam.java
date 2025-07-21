package gui;

import controller.ControllerIscrizioneTeam;

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
    private final DefaultListModel<String> modelLista;

    private static final String SEGOEUI = "Segoe UI";

    public IscrizioneTeam(ControllerIscrizioneTeam controller) {
        mainPanel.setPreferredSize(new Dimension(600,400));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        panelIscrizione.setVisible(false);
        modelLista = new DefaultListModel<>();
        listElenchi.setModel(modelLista);

        confermaButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaButton.addActionListener(ignored -> {
            String nome = nomeTextField.getText();
            String idTxt = iscrizioneIDTextField.getText();
            controller.creazioneTeam(nome, idTxt);
        });

        hackathonAttiveButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        hackathonAttiveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hackathonAttiveButton.addActionListener(ignored -> controller.visualizzaHackathonAttive(listElenchi, modelLista));

        iscrivitiAdUnTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        iscrivitiAdUnTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iscrivitiAdUnTeamButton.addActionListener(ignored -> {
            String idTeam = teamIDtextField.getText();
            controller.iscrizioneTeam(idTeam);
        });

        listaTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        listaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        listaTeamButton.addActionListener(ignored -> {
            String idHackathonTxt = hackathonIDtextField.getText();
            controller.visualizzaTeamHackathon(idHackathonTxt, listElenchi, modelLista);
        });

        creaTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        creaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        creaTeamButton.addActionListener(ignored -> panelIscrizione.setVisible(true));

        indietroButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> controller.showUtente());
    }

    public void setVisiblePanelElenchi() {
        panelElenchi.setVisible(true);
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }
}
