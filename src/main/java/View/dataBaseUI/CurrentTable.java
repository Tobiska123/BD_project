package View.dataBaseUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class CurrentTable extends JTable {

    public CurrentTable(){
        this.setRowSelectionAllowed(true);
        this.setColumnSelectionAllowed(false);
        this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    }
    public void setTable(Vector<Vector<String>> data, Vector<String> headers){
        this.setModel(new DefaultTableModel(
                data,
                headers
        ));
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }

    public void setSelectionRow(int index){
        this.setRowSelectionInterval(index, index);
    }

}
