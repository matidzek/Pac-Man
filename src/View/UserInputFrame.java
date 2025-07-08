package View;

import javax.swing.*;
import java.awt.*;

public class UserInputFrame extends JFrame{
    private JTextField inputFieldx;
    private JTextField inputFieldy;
    private JButton ustawDaneButton;

    int numberx;
    int numbery;

    public UserInputFrame(){
        setTitle("User Input");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());
        JPanel inPanel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.add(inPanel, BorderLayout.CENTER);

        JLabel label = new JLabel("Set map size (from 10x10 to 100x100)", JLabel.CENTER);
        label.setFont(new Font("Monospaced", Font.BOLD, 18));
        label.setBackground(Color.BLACK);
        label.setForeground(Color.YELLOW);
        panel.add(label, BorderLayout.NORTH);


        inputFieldx = new JTextField(15);
        inputFieldy = new JTextField(15);
        ustawDaneButton = new JButton("Start");

        inputFieldx.setBackground(Color.BLACK);
        inputFieldy.setBackground(Color.BLACK);
        ustawDaneButton.setBackground(Color.BLACK);
        ustawDaneButton.setForeground(Color.YELLOW);
        inputFieldx.setForeground(Color.YELLOW);
        inputFieldy.setForeground(Color.YELLOW);
        ustawDaneButton.setForeground(Color.RED);
        ustawDaneButton.setFont(new Font("Monospaced", Font.BOLD, 30));


        ustawDaneButton.addActionListener(e -> {
            try {
                numberx = Integer.parseInt((inputFieldx.getText()));
                numbery = Integer.parseInt((inputFieldy.getText()));
                if (numberx >= 10 && numberx <= 100 && numbery >= 10 && numbery <= 100) {
                    SwingUtilities.invokeLater(() -> new MyFrame(numberx,numbery));
                    this.dispose();
                } else
                    JOptionPane.showMessageDialog(null, "Please enter a number between 10 and 100");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Not a number");
            }
        });
        inPanel.add(inputFieldx);
        inPanel.add(inputFieldy);
        inPanel.add(ustawDaneButton);
        inPanel.setBackground(Color.BLACK);
        this.setLocationRelativeTo(null);
        this.add(panel);
        this.setSize(500,400);
        this.setVisible(true);

    }

}
