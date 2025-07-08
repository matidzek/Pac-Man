package View;

import Controller.Controller;
import Events.KoniecGryEvent;
import Events.KoniecGryListener;
import Events.PauzaEvent;
import Events.PauzaListener;
import Model.MapModel;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame implements PauzaListener, KoniecGryListener {
    private TopPanel topPanel;
    private MainPanel mainPanel;
    private MapModel model;
    private Controller controller;
    @Override
    public void onPauza(PauzaEvent evt) {
        this.dispose();
    }

    @Override
    public void onKoniecGry(KoniecGryEvent e) {
        this.dispose();
    }

    int rows;
    int cols;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserInputFrame::new);
    }
    public MyFrame(int rows, int cols) {
        super("My Frame");
        this.rows = rows;
        this.cols = cols;


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        topPanel = new TopPanel();

        controller = new Controller();
        controller.addZebranePunktyListener(topPanel);
        controller.addPauzaListener(this);
        controller.addZmianaCzasuListener(topPanel);
        controller.addZmianaZyciaListener(topPanel);
        controller.addKoniecGryListener(this);

        new Thread(() ->{
            model = new MapModel(rows,cols,4);  //mapa
            SwingUtilities.invokeLater(() -> {
                mainPanel = new MainPanel(model, controller);
                add(mainPanel, BorderLayout.CENTER);
                add(topPanel, BorderLayout.NORTH);
                pack();
                setVisible(true);

                SwingUtilities.invokeLater(mainPanel::requestFocusInWindow);
            });
        }).start();
    }
}

