package gui;

import controller.ControllerClassifica;

import javax.swing.*;
import java.awt.*;

public class Classifica {
    private JPanel mainPanel;
    private JLabel area;
    private JList<String> classificaList;
    private JScrollPane panelClassifica;
    private JButton indietroButton;

    private final DefaultListModel<String> modelList;

    public Classifica(ControllerClassifica controller, Integer hackathonID, Runnable azioneIndietro) {
        mainPanel.setPreferredSize(new Dimension(500,350));
        area.setFont(new Font("Segoe UI", Font.BOLD, 38));

        modelList = new DefaultListModel<>();
        classificaList.setModel(modelList);

        controller.mostraClassifica(classificaList, modelList, panelClassifica);

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(e -> azioneIndietro.run() );
    }

    public JPanel getMainPanel() {return mainPanel;}
}
