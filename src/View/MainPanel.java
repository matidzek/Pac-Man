package View;

import Controller.Controller;
import Model.MapModel;
import Model.ZapisOdczytWyniku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainPanel extends JPanel{
    private Controller controller;
    private JTable table;
    private MyTable myTable;
    private BlokRenderer blokRenderer;

    public MainPanel(MapModel model, Controller controller) {
        super(new BorderLayout());
        blokRenderer = new BlokRenderer();
        myTable = new MyTable(model.getMapa());

        this.controller = controller;
        controller.addZmianaDanychListener(myTable);
        controller.addSterowanieListener(model);
        controller.addPauzaListener(model);
        controller.addAnimacjaPacmanaListener(blokRenderer);

        model.addZmianaDanychListener(controller);
        model.addZebranePunktyListener(controller);
        model.addZmianaCzasuListener(controller);
        model.addZmianaZyciaListener(controller);
        model.addKoniecGryListener(controller);
        model.addAnimacjaPacmanaListener(controller);


        table = new JTable(myTable);
        table.setDefaultRenderer(Integer.class, blokRenderer);
        table.setTableHeader(null);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.setRowHeight(16);
        for (int i = 0; i < model.getColumns(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(16);
        }

        this.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
                int rows = myTable.getRowCount();
                int cols = myTable.getColumnCount();

                int cellWidth = getWidth() / cols;
                int cellHeight = getHeight() / rows;
                int cellSize = Math.max(8, Math.min(cellWidth, cellHeight)); // minimalny rozmiar np. 8px

                table.setRowHeight(cellSize);
                for (int i = 0; i < cols; i++) {
                    table.getColumnModel().getColumn(i).setPreferredWidth(cellWidth);
                }
                table.revalidate();
                table.repaint();
            }
        });



        table.setFocusable(false);
        this.setFocusable(true);
        addKeyListener(controller);
        this.add(table, BorderLayout.CENTER);
    }


}
