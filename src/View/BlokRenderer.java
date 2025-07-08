package View;

import Events.AnimacjaPacmanaEvent;
import Events.AnimacjaPacmanaListener;
import Model.MapModel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

//warstwa widokowa
public class BlokRenderer extends JPanel implements TableCellRenderer, AnimacjaPacmanaListener {
    private int value;
    private int katSzczeki;
    private MapModel.Kierunek kierunek;

    public BlokRenderer() {
        this.value = -1;
        this.setBackground(Color.black);
    }

    @Override
    public void onAnimacjaPacmanaListener(AnimacjaPacmanaEvent e) {
        kierunek = e.getKierunek();
        katSzczeki = e.getKatSzczeki();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.value = (Integer) value;
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Color color = switch (this.value) {
            case 1 -> Color.BLUE;
            case 3 -> Color.GREEN;
            case 4 -> Color.RED;
            case 5 -> Color.MAGENTA;
            case 6 -> Color.ORANGE;
            default -> Color.BLACK;
        };
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (this.value == 2) {      //pacman
            Graphics2D g2d = (Graphics2D) g.create();
            if (kierunek == null) kierunek = MapModel.Kierunek.PRAWO;
            int size = Math.min(getWidth(), getHeight());
            int cx = getWidth() / 2;
            int cy = getHeight() / 2;

            switch (kierunek) {
                case GORA:
                    g2d.rotate(Math.toRadians(270), cx, cy);
                    break;
                case DOL:
                    g2d.rotate(Math.toRadians(90), cx, cy);
                    break;
                case LEWO:
                    g2d.rotate(Math.toRadians(180), cx, cy);
                    break;
                case PRAWO:
                    break;
            }

            g2d.setColor(Color.yellow);
            g2d.fillArc((getWidth() - size) / 2, (getHeight() - size) / 2, size, size, katSzczeki / 2, 360 - katSzczeki);

        } else if (this.value == 0) { //punkty
            int size = getWidth() < getHeight() ? getWidth() : getHeight();
            size /= 4;
            g.setColor(Color.WHITE);
            g.fillOval((getWidth() - size) / 2, (getHeight() - size) / 2, size, size);
        }
    }
}




