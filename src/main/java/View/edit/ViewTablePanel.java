package View.edit;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

public class ViewTablePanel extends JPanel{

    private JTable table1;

    private Vector<String> columnsHeader;
    private Vector<Vector<String>> arrayData;

    public ViewTablePanel(){
        setBackground(Color.ORANGE);
        setLayout(new BorderLayout());
    }

    public void setData(Vector<Vector<String>> arrayData, Vector<String> columnsHeader){
        this.removeAll();
        this.arrayData = arrayData;
        this.columnsHeader = columnsHeader;

        this.table1 = new JTable(this.arrayData, this.columnsHeader);
        this.add(this.table1);
        setVisible(true);
        repaint();
    }
}
