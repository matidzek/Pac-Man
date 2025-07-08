package View;

import Events.*;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel implements ZebranePunktyListener, ZmianaCzasuListener, ZmianaZyciaListener {
    private int punkty;
    private int czas;
    private int zycia;
    private int best;
    JTextArea score;
    JTextArea time;
    JTextArea lifes;

    @Override
    public void onZebranePunkty(ZebranePunktyEvent evt) {
        score.setText("Points: " + evt.getPunkty());
    }

    @Override
    public void onZmianaCzasu(ZmianaCzasuEvent e) {
        time.setText("Timer: " + e.getCzas());
    }

    @Override
    public void onZmianaZycia(ZmianaZyciaEvent e) {
        lifes.setText("HP: " + e.getZycia());
    }

    public TopPanel() {
        super(new GridLayout(1,6));
        this.best = 100;
        this.czas = 0;
        this.punkty = 0;
        this.zycia = 2;
        this.lifes = new JTextArea("HP: " + zycia);
        this.time =  new JTextArea("Timer: " + czas);
        this.score = new JTextArea("Score: " + punkty);
        this.setBackground(Color.BLACK);



        score.setBackground(Color.BLACK);
        time.setBackground(Color.BLACK);
        lifes.setBackground(Color.BLACK);


        score.setForeground(Color.WHITE);
        time.setForeground(Color.WHITE);
        lifes.setForeground(Color.WHITE);


        score.setFont(new Font("PLAIN", Font.BOLD, 25));
        time.setFont(new Font("PLAIN", Font.BOLD, 25));
        lifes.setFont(new Font("PLAIN", Font.BOLD, 25));


        score.setLineWrap(true);
        time.setLineWrap(true);
        lifes.setLineWrap(true);


        score.setWrapStyleWord(true);
        time.setWrapStyleWord(true);
        lifes.setWrapStyleWord(true);


        score.setEditable(false);
        time.setEditable(false);
        lifes.setEditable(false);


        this.add(lifes);
        this.add(time);
        this.add(score);

    }

}
