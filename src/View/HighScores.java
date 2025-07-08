package View;

import Controller.OdczytWynikuController;
import Events.WynikiOdczytaneEvent;
import Events.WynikiOdczytaneListener;
import Model.PlayersData;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class HighScores extends JFrame implements WynikiOdczytaneListener {
    private OdczytWynikuController odczytWynikuController;
    private JTextArea highScoresTextArea;

    @Override
    public void onWynikiOdczytane(WynikiOdczytaneEvent e) {
        highScoresTextArea.setText("");
        List<PlayersData> sorted = e.getWyniki().stream().sorted(
                (a,b) -> Integer.compare(b.getPlayerScore(), a.getPlayerScore()))
                .collect(Collectors.toList());
        for(PlayersData p : sorted){
            highScoresTextArea.append(p.getPlayerName() + " : " + p.getPlayerScore() + "\n");
        }
    }

    public HighScores(OdczytWynikuController controller) {
        super("High Scores");
        this.odczytWynikuController = controller;
        this.highScoresTextArea = new JTextArea();
        this.highScoresTextArea.setEditable(false);
        this.highScoresTextArea.setLineWrap(true);
        this.highScoresTextArea.setWrapStyleWord(true);
        this.highScoresTextArea.setFont(new Font("Monospaced", Font.PLAIN, 40));
        this.highScoresTextArea.setBackground(Color.BLACK);
        this.highScoresTextArea.setForeground(Color.YELLOW);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane(highScoresTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        this.add(panel);
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}
