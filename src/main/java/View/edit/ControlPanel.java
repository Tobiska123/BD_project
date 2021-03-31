package View.edit;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends Panel {

    public JComboBox getSelectTable() {
        return selectTable;
    }

    public void setSelectTable(JComboBox selectTable) {
        this.selectTable = selectTable;
    }

    private JComboBox selectTable;
    private String[] tables;

    private JButton addBtn;

    private JButton delBtn;

    private JButton arrowLeftBtn;
    private JButton arrowRightBtn;

    public ControlPanel(){
        this.setBounds(10,10,400,700);
        this.tables = null;
        selectTable = new JComboBox();

        addBtn = new JButton("ADD");
        delBtn = new JButton("DELETE");

        arrowLeftBtn = new JButton("PREV");
        arrowRightBtn = new JButton("NEXT");

        selectTable.addActionListener(e ->{
            JComboBox is = (JComboBox)e.getSource();
            String selectTable = (String)is.getSelectedItem();
        });

        addLocationAndSize();
        addComponentToPanel();

        this.setVisible(true);
    }

    public void addLocationAndSize(){
        setBackground(Color.CYAN);
//        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//        add(Box.createVerticalGlue());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        selectTable.setMaximumSize(new Dimension(300, 30));
        addBtn.setMaximumSize(new Dimension(400, 50));
        delBtn.setMaximumSize(new Dimension(400, 50));
        arrowRightBtn.setMaximumSize(new Dimension(400, 30));
        arrowLeftBtn.setMaximumSize(new Dimension(400, 30));



//        selectTable.setBounds(5, 30, 150, 20);
//        addBtn.setBounds(5, 60, 150, 30);
//        delBtn.setBounds(5, 120, 150, 30);
//        arrowRightBtn.setBounds(5, 180, 70, 30);
//        arrowLeftBtn.setBounds(100, 180, 70, 30);
    }

    public void addComponentToPanel(){
        add(selectTable);
        add(addBtn);
        add(delBtn);
        add(arrowLeftBtn);
        add(arrowRightBtn);
    }
}
