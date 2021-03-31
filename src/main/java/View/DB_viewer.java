package View;

import Controller.ControllerDB;
import Model.Pair;
import View.dataBaseUI.DataBaseUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class DB_viewer extends JFrame{

    private ControllerDB controllerDB;

    private LoginUI loginUI;
    private MainUI mainUI;

    private DataBaseUI dataBaseUI;

    public DB_viewer(ControllerDB controllerDB){
        this.controllerDB = controllerDB;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        this.setPreferredSize(new Dimension(400,500));
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)((size.getWidth() - size.width/4) / 2), (int)((size.getHeight() - size.height / 2) / 2));
        this.setLocationRelativeTo(null);
        this.loginUI = new LoginUI();
        this.add(this.loginUI);
        addListenersLoginUI();

        this.setVisible(true);
        this.pack();
    }
    //LoginUI

    public void setStatusLabel(String status){
        this.loginUI.stat_label.setText(status);
    }

    public void setActionListenerLoginUI(ActionListener actionListener){
        loginUI.getLoginButton().addActionListener(actionListener);
    }

    public void setActionListenerMainUIQuery(ActionListener actionListener){
        this.mainUI.getQueryBtn().addActionListener(actionListener);
    }

    public void setActionListenerMainUIEdit(ActionListener actionListener){
        this.mainUI.getEditBtn().addActionListener(actionListener);
    }

    public void setLoginUI(LoginUI loginUI) {
        this.loginUI = loginUI;
    }

    public LoginUI getLoginUI() {
        return loginUI;
    }

    //--LoginUI listeners

    public void addListenersLoginUI(){
        this.loginUI.getLoginButton().addActionListener(e -> {
            try {
                this.controllerDB.loginButtonAction();
                startMainFrame();
            }catch (Exception ex) {
                setStatusLabel(ex.getMessage());
        }
        });
    }

    //MainUI

    public void setSelectableTables(List<String> listOfTables){
        for(String iter : listOfTables)
            this.dataBaseUI.getTableSelect().addItem(iter);
    }

    public void startMainFrame(){
        this.getContentPane().removeAll();
        this.mainUI = new MainUI();
        addListenersMainUI();
        this.getContentPane().add(this.mainUI);
        this.setVisible(true);
    }
    //--MainUI-listeners

    public void addListenersMainUI(){
        this.mainUI.getEditBtn().addActionListener(e -> {
            try{
                this.getContentPane().removeAll();
                this.setMinimumSize(new Dimension(800,600));
                this.dataBaseUI = new DataBaseUI();
                this.controllerDB.editButtonAction();
                this.getContentPane().add(this.dataBaseUI.getRootPanel());
                this.setVisible(true);

            }catch (Exception ex){
                ex.printStackTrace();
            }
        });

        this.mainUI.getQueryBtn().addActionListener(e -> {

        });

        this.mainUI.getLogOutBtn().addActionListener(e -> {

        });

        this.mainUI.getReadOnlyBtn().addActionListener(e -> {

        });
    }

    public void addListenerControlPane(){
        this.dataBaseUI.getTableSelect().addActionListener(e -> {
            try {
                JComboBox is = (JComboBox) e.getSource();
                String selectTable = (String) is.getSelectedItem();
                Pair<Vector<Vector<String>>, Vector<String>> tmp = this.controllerDB.selectTableData(selectTable);
                dataBaseUI.getCursorDB().setHeaderAndData(tmp.getFirst(), tmp.getSecond());
                this.dataBaseUI.blockedManipulatedButton(true);
                dataBaseUI.setStatusActionLabel("Success load table: " + selectTable);
            }catch (Exception ex){
                dataBaseUI.setStatusActionLabel(ex.getMessage());
                ex.printStackTrace();
            }
        });

        this.dataBaseUI.getADDButton().addActionListener(e -> {
            try{
                Pair<Vector<Vector<String>>, Vector<String>> tmp = this.controllerDB.addRowToModel(this.dataBaseUI.getSelectionCombox(), this.dataBaseUI.getFormTablePanel().getValues(), dataBaseUI.getCursorDB().getHeaders());
                this.dataBaseUI.getCursorDB().setHeaderAndData(tmp.getFirst(), tmp.getSecond());
                dataBaseUI.setStatusActionLabel("Success addition to table");
            }catch (Exception ex){
                dataBaseUI.setStatusActionLabel(ex.getMessage());
            }
        });

        this.dataBaseUI.getDELETEButton().addActionListener(e ->{
            try{
                Pair<Vector<Vector<String>>, Vector<String>> tmp = this.controllerDB.deleteRowFromModel(this.dataBaseUI.getSelectionCombox(),
                        dataBaseUI.getCursorDB().getHeaders(), this.dataBaseUI.getFormTablePanel().getValues());
                this.dataBaseUI.getCursorDB().setHeaderAndData(tmp.getFirst(), tmp.getSecond());
                dataBaseUI.setStatusActionLabel("Success has deleted from table");
            }catch (Exception ex){
                dataBaseUI.setStatusActionLabel(ex.getMessage());
            }
        });

        this.dataBaseUI.getUPDATEButton().addActionListener(e -> {
            try{
                Pair<Vector<Vector<String>>, Vector<String>> tmp = this.controllerDB.updateModel(this.dataBaseUI.getSelectionCombox(),
                        this.dataBaseUI.getFormTablePanel().getValues(), dataBaseUI.getCursorDB().getHeaders());
                this.dataBaseUI.getCursorDB().setHeaderAndData(tmp.getFirst(), tmp.getSecond());
                dataBaseUI.setStatusActionLabel("Success update table");
            }catch (Exception ex){
                dataBaseUI.setStatusActionLabel(ex.getMessage());
            }
        });

        this.dataBaseUI.getQUERYButton().addActionListener(e -> {
            try{
                Pair<Vector<Vector<String>>, Vector<String>> tmp = this.controllerDB.someQuery(this.dataBaseUI.getSomeQueryField().getText());
                this.dataBaseUI.getCursorDB().setHeaderAndData(tmp.getFirst(), tmp.getSecond());
                this.dataBaseUI.blockedManipulatedButton(false);
                dataBaseUI.setStatusActionLabel("Success has executed your query");
            }catch (Exception ex){
                dataBaseUI.setStatusActionLabel(ex.getMessage());
            }
        });

        this.dataBaseUI.getREFRESHButton().addActionListener( e ->{
            try{
                if(this.controllerDB.refreshConnetctionModel())
                    dataBaseUI.setStatusActionLabel("Connection refreshed");
                else
                    dataBaseUI.setStatusActionLabel("Connection is valid");
            }catch (Exception ex){
                dataBaseUI.setStatusActionLabel(ex.getMessage());
            }
        });

        this.dataBaseUI.getLogOut().addActionListener(e ->{
            try {
                this.controllerDB.loginOut();
                this.getContentPane().removeAll();
                this.getContentPane().add(loginUI);
                this.setMinimumSize(new Dimension(400,500));
                this.setVisible(true);
                pack();
            }catch (Exception ex){
                dataBaseUI.setStatusActionLabel(ex.getMessage());
            }
        });
    }


}
