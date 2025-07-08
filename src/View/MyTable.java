package View;

import Events.AnimacjaPacmanaEvent;
import Events.AnimacjaPacmanaListener;
import Events.ZmianaDanychEvent;
import Events.ZmianaDanychListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MyTable extends AbstractTableModel implements ZmianaDanychListener{
    private List<List<Integer>> data;
    public MyTable(List<List<Integer>> data) {
        super();
        this.data = data;
    }

    @Override
    public int getRowCount() {
        if(data == null) return 0;
        return data.size();
    }
    @Override
    public int getColumnCount() {
        if(data == null) return 0;
        return data.get(0).size();
        //str 20
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public void onZmianaDanych(ZmianaDanychEvent evt) {
        SwingUtilities.invokeLater(() -> fireTableCellUpdated(evt.getX(), evt.getY()));
    }

}





