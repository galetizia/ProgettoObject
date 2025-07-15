package gui;

import controller.ControllerOrganizzaHackathon;
import implementazionepostgresdao.*;
import model.*;

import javax.swing.*;
import java.awt.*;

public class OrganizzaHackathon {
    private JPanel mainPanel;
    private JPanel panelIscrizione;
    private JTextField nomeTextField;
    private JTextField iscrizioneIDTextField;
    private JButton confermaButton;
    private JScrollPane panelElenchi;
    private JList listElenchi;
    private JButton organizzaNuovaHackathonButton;
    private JButton indietroButton;
    private JButton hackathonAttiveButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;

    public OrganizzaHackathon(ControllerOrganizzaHackathon controller, Organizzatore organizzatore) {
        mainPanel.setPreferredSize(new Dimension(600,400));

    }
}
