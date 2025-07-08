package View;

import Events.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class EndScreen extends JFrame implements Serializable {
    private JTextField inputField;
    private JButton validateButton;

    private String playerName;
    private int zebranePunkty;

    private List<ZapisWynikuListener> ZapisWynikuListeners = new ArrayList<>();

    public void fireZapisWyniku(int punkty) {
        for(ZapisWynikuListener scl : this.ZapisWynikuListeners)
            scl.onZapisWyniku(
                    new ZapisWynikuEvent(this, playerName, zebranePunkty)
            );
    }

    public EndScreen(int punkty){
        this.zebranePunkty = punkty;
        setTitle("Koniec gry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("GAME OVER");
        label.setFont(new Font("Arial", Font.BOLD, 36));
        label.setForeground(Color.RED);

        inputField = new JTextField(20);
        inputField.setForeground(Color.YELLOW);
        inputField.setBackground(Color.BLACK);

        validateButton = new JButton("Zapisz wynik");
        validateButton.setForeground(Color.YELLOW);
        validateButton.setBackground(Color.DARK_GRAY);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        topPanel.add(label);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.BLACK);
        inputPanel.add(inputField);
        inputPanel.add(validateButton);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);

        validateButton.addActionListener(e -> {
            playerName = inputField.getText();
            fireZapisWyniku(zebranePunkty);
            dispose();
        });

        setSize(500, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void addZapisWynikuListener(ZapisWynikuListener ZapisWynikuListener) {
        this.ZapisWynikuListeners.add(ZapisWynikuListener);
    }
    public void removeZapisWynikuListener(ZapisWynikuListener ZapisWynikuListener) {
        this.ZapisWynikuListeners.remove(ZapisWynikuListener);
    }

}
