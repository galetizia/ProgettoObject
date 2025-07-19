package gui;

import controller.ControllerClassifica;
import model.Hackathon;

import javax.swing.*;
import java.awt.*;

public class Classifica {
    private JPanel mainPanel;
    private JLabel area;
    private JButton cercaButton;
    private JList classificaList;
    private JTextField textField1;
    private JScrollPane panelClassifica;

    private final DefaultListModel<String> modelList;

    public Classifica(ControllerClassifica controller, Integer hackathonID) {
        mainPanel.setPreferredSize(new Dimension(500,350));

        modelList = new DefaultListModel<>();
        classificaList.setModel(modelList);

        controller.mostraClassifica(classificaList, modelList, panelClassifica);
    }

    public JPanel getMainPanel() {return mainPanel;}
}
