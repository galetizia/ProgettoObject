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
    private JTextField nomeNuovoTeamTextField;
    private JTextField creaTeamIDTextField;
    private JButton confermaCreaTeamButton;
    private JTextField hackathonIDtextField;
    private JPanel panelIscrizione;
    private JButton indietroButton;
    private JButton hackathonAttiveButton;
    private JLabel area;
    private JButton confermaListaTeamButton;
    private JButton confermaIscrTramiteButton;
    private JLabel hackathonIDLabel;
    private JLabel teamIDLabel;
    private final DefaultListModel<String> modelList;
    private JButton ultimoPulsantePremuto = null;

    private static final String SEGOEUI = "Segoe UI";

    public IscrizioneTeam(ControllerIscrizioneTeam controller) {
        mainPanel.setPreferredSize(new Dimension(600,400));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        panelIscrizione.setVisible(false);

        hackathonIDLabel.setVisible(false);
        hackathonIDtextField.setVisible(false);
        confermaListaTeamButton.setVisible(false);

        teamIDLabel.setVisible(false);
        teamIDtextField.setVisible(false);
        confermaIscrTramiteButton.setVisible(false);

        modelList = new DefaultListModel<>();
        listElenchi.setModel(modelList);

        hackathonAttiveButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        hackathonAttiveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hackathonAttiveButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaHackathonAttive(listElenchi, modelList, confermaListaTeamButton, confermaIscrTramiteButton, panelIscrizione, hackathonAttiveButton, ultimoPulsantePremuto));

        listaTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        listaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        listaTeamButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaListaTeam(listElenchi, modelList, listaTeamButton, confermaIscrTramiteButton, panelIscrizione, hackathonIDtextField, ultimoPulsantePremuto));

        confermaListaTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaListaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaListaTeamButton.addActionListener(ignored ->
            controller.visualizzaTeamHackathon(hackathonIDtextField, listElenchi, modelList));

        iscrivitiAdUnTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        iscrivitiAdUnTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iscrivitiAdUnTeamButton.addActionListener(ignored ->
            ultimoPulsantePremuto = controller.visibilitaIscrivitiTeam(confermaListaTeamButton, iscrivitiAdUnTeamButton, panelIscrizione, teamIDtextField, ultimoPulsantePremuto));

        confermaIscrTramiteButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaIscrTramiteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaIscrTramiteButton.addActionListener(ignored ->
            controller.iscrizioneTeam(teamIDtextField));


        creaTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        creaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        creaTeamButton.addActionListener(ignored ->
                ultimoPulsantePremuto = controller.visibilitaCreaTeam(modelList, creaTeamButton, confermaListaTeamButton, confermaIscrTramiteButton, panelIscrizione, ultimoPulsantePremuto));

        confermaCreaTeamButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        confermaCreaTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confermaCreaTeamButton.addActionListener(ignored ->
            controller.creazioneTeam(nomeNuovoTeamTextField, creaTeamIDTextField));

        indietroButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> controller.showUtente());
    }

    public void setVisiblePanelElenchi() {
        panelElenchi.setVisible(true);
    }

    private void setVisibility(boolean visible, JComponent... components) {
        for (JComponent c : components)
            c.setVisible(visible);
    }

    public void setVisibilityListaTeam(Boolean visible){
        setVisibility(visible, hackathonIDLabel, hackathonIDtextField, confermaListaTeamButton);
    }

    public void setVisibilityIscrivitiTeam(Boolean visible){
        setVisibility(visible, teamIDLabel, teamIDtextField, confermaIscrTramiteButton);
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }
}
