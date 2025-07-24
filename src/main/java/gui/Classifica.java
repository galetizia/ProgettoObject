package gui;

import controller.ControllerClassifica;

import javax.swing.*;
import java.awt.*;

/**
 * Classe GUI per la schermata Classifica.
 * Qui verrà mostrata, una volta pubblicata, la classifica dell'hackathon.
 * <p>
 * Questa classe interagisce con {@link ControllerClassifica} per delegare la logica applicativa.
 * </p>
 */

public class Classifica {

    /** Tutte le componenti di design*/
    private JPanel mainPanel;
    private JLabel area;
    private JList<String> classificaList;
    private JScrollPane panelClassifica;
    private JButton indietroButton;

    /** Stringa che contiene il nome del font utilizzato */
    private static final String SEGOEUI = "Segoe UI";

    /**
     * Costruttore della schermata Classifica.
     *
     * @param controller Il controller associato alla schermata.
     * @param azioneIndietro Azione da eseguire quando l'utente clicca su "Indietro".
     */
    public Classifica(ControllerClassifica controller, Runnable azioneIndietro) {
        mainPanel.setPreferredSize(new Dimension(500,350));
        area.setFont(new Font(SEGOEUI, Font.BOLD, 38));

        /* Modello della lista per la visualizzazione dei dati nella GUI e collegata alla JList per
        * gestire gli elementi visualizzati dinamicamente */
        final DefaultListModel<String> modelList= new DefaultListModel<>();
        classificaList.setModel(modelList);

        controller.mostraClassifica(classificaList, modelList, panelClassifica);

        /* Bottone per tornare alla schermata precedente */
        indietroButton.setFont(new Font(SEGOEUI, Font.BOLD, 14));
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.addActionListener(ignored -> azioneIndietro.run() );
    }

    /**
     * Restituisce il pannello principale della schermata.
     *
     * @return Il {@link JPanel} principale della schermata.
     */
    public JPanel getMainPanel() {return mainPanel;}
}
