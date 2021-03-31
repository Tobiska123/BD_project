package View.dataBaseUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class DataBaseUI {
    private JPanel rootPanel;
    private JComboBox tableSelect;

    public JTextField getSomeQueryField() {
        return someQueryField;
    }

    public void blockedManipulatedButton(boolean flag){
        ADDButton.setEnabled(flag);
        UPDATEButton.setEnabled(flag);
        DELETEButton.setEnabled(flag);
    }

    private JTextField someQueryField;

    public JButton getQUERYButton() {
        return QUERYButton;
    }

    private JButton QUERYButton;

    public String getSelectionCombox(){
        return (String) this.tableSelect.getSelectedItem();
    }

    public JButton getADDButton() {
        return ADDButton;
    }

    public JButton getDELETEButton() {
        return DELETEButton;
    }

    private JButton ADDButton;
    private JButton DELETEButton;
    private JButton PREVButton;
    private JButton NEXTButton;
    private JScrollPane JPanel;
    private JLabel exceptionLabel;
    private JScrollPane formScrollPanel;

    public FormPanel getFormTablePanel() {
        return formTablePanel;
    }

    private FormPanel formTablePanel;

    public CurrentTable getCurrentTable() {
        return currentTable;
    }

    private CurrentTable currentTable;

    public JButton getUPDATEButton() {
        return UPDATEButton;
    }

    private JButton UPDATEButton;

    public JButton getREFRESHButton() {
        return REFRESHButton;
    }

    private JButton REFRESHButton;

    public JButton getLogOut() {
        return LogOut;
    }

    private JButton LogOut;
    private CursorTable cursorDB;

    public DataBaseUI(){
        setListeners();
    }

    public JPanel getRootPanel(){
        return rootPanel;
    }

    public JComboBox getTableSelect(){
        return tableSelect;
    }

    public void setStatusActionLabel(String status){
        if(status.equals("Success"))
            exceptionLabel.setForeground(Color.GREEN);
        else
            exceptionLabel.setForeground(Color.RED);
        exceptionLabel.setText(status);
    }

    public CursorTable getCursorDB() {
        return this.cursorDB;
    }

    private void createUIComponents() {
        this.formTablePanel = new FormPanel();
        this.currentTable = new CurrentTable();
        cursorDB = new CursorTable(this.currentTable, this.formTablePanel);
        this.formTablePanel.setLayout(new GridBagLayout());
    }

    public void setListeners(){
        NEXTButton.addActionListener(e -> {
            cursorDB.next();
        });

        PREVButton.addActionListener(e ->{
            cursorDB.prev();
        });
    }
}
