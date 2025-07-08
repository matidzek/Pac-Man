package View;

import Controller.OdczytWynikuController;
import Events.OdczytWynikuEvent;
import Model.ZapisOdczytWyniku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//, ResumeListener
public class MainMenu extends JFrame implements ActionListener {
    private JButton gameButton;
    private JButton scoreButton;
    private JButton exitButton;


    public MainMenu() {
        super("Main menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JPanel inpanel = new JPanel();
        inpanel.setLayout(new GridLayout(1, 3));
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.BLACK);
        JLabel title = new JLabel("PAC-MAN", JLabel.CENTER);
        title.setBackground(Color.BLACK);
        title.setForeground(Color.YELLOW);
        title.setFont(new Font("Arial", Font.BOLD, 50));

        gameButton = new JButton("New Game");
        scoreButton = new JButton("High Scores");
        exitButton = new JButton("Exit");

        gameButton.setBackground(Color.BLACK);
        scoreButton.setBackground(Color.BLACK);
        exitButton.setBackground(Color.BLACK);
        gameButton.setForeground(Color.YELLOW);
        scoreButton.setForeground(Color.YELLOW);
        exitButton.setForeground(Color.YELLOW);

        gameButton.addActionListener(this);
        scoreButton.addActionListener(this);
        exitButton.addActionListener(this);

        gameButton.setFocusable(false);
        scoreButton.setFocusable(false);
        exitButton.setFocusable(false);

        panel.add(title, BorderLayout.NORTH);
        panel.add(inpanel, BorderLayout.CENTER);
        inpanel.add(gameButton, BorderLayout.LINE_START);
        inpanel.add(scoreButton, BorderLayout.CENTER);
        inpanel.add(exitButton, BorderLayout.LINE_END);

        this.add(panel);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == gameButton) {
            this.dispose();
            SwingUtilities.invokeLater(UserInputFrame::new);
        } else if(e.getSource() == exitButton) {
            System.exit(0);
        }
        else if(e.getSource() == scoreButton) {
            this.dispose();
            ZapisOdczytWyniku zapisOdczytWyniku = new ZapisOdczytWyniku();
            OdczytWynikuController controller = new OdczytWynikuController();
            SwingUtilities.invokeLater(() ->{
                HighScores highScores = new HighScores(controller);
                zapisOdczytWyniku.addWynikiOdczytaneListener(highScores);
                controller.addWynikiOdczytaneListener(highScores);
                controller.addOdczytWynikuListener(zapisOdczytWyniku);
                controller.onOdczytWyniku(new OdczytWynikuEvent(this));
            });
        }
    }

}
