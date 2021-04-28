package View.dataBaseUI;

import javax.swing.*;
import java.awt.*;

public class DataBaseUI{
    private JPanel rootPanel;
    private JComboBox tableSelect;

    public JTextField getSomeQueryField() {
        return someQueryField;
    }

    public void blockedManipulatedButton(BLOCK_BUTTON_FLAGS block_button_flags, boolean flag){
        if(block_button_flags == BLOCK_BUTTON_FLAGS.PART_BUTTON_BLOCK){
            ADDButton.setEnabled(flag);
            UPDATEButton.setEnabled(flag);
            DELETEButton.setEnabled(flag);
        }else if(block_button_flags == BLOCK_BUTTON_FLAGS.ALL_BUTTON_BLOCK){
            ADDButton.setEnabled(flag);
            UPDATEButton.setEnabled(flag);
            DELETEButton.setEnabled(flag);
            NEXTButton.setEnabled(flag);
            PREVButton.setEnabled(flag);
        }
        }

    public JButton getGoButton() {
        return goButton;
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
    private JButton LogOut;
    private CurrentQueryList QUERIESlist;
    private JButton QUERIESButton;
    private CursorTable cursorDB;

    public CurrentQueryList getQUERIESlist(){
        return this.QUERIESlist;
    }

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
    private JButton goButton;

    public JButton getLogOut() {
        return LogOut;
    }

    public JButton getQUERIESButton(){
        return this.QUERIESButton;
    }

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
        this.QUERIESlist = new CurrentQueryList();
        this.formTablePanel.setLayout(new GridBagLayout());
    }

    public FormPanel getFormPanel(){
        return this.formTablePanel;
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
