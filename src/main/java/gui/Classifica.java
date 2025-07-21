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

    public Classifica(ControllerClassifica controller, Runnable azioneIndietro) {
        mainPanel.setPreferredSize(new Dimension(500,350));
        area.setFont(new Font("Segoe UI", Font.BOLD, 38));
        final DefaultListModel<String> modelList= new DefaultListModel<>();
        classificaList.setModel(modelList);

        controller.mostraClassifica(classificaList, modelList, panelClassifica);

        indietroButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> azioneIndietro.run() );
    }

    public JPanel getMainPanel() {return mainPanel;}
}
