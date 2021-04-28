package View;

import Controller.ControllerDB;
import Model.Pair;
import View.dataBaseUI.BLOCK_BUTTON_FLAGS;
import View.dataBaseUI.CurrentQueryList;
import View.dataBaseUI.DataBaseUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class DB_viewer extends JFrame{

    private ControllerDB controllerDB;

    private LoginUI loginUI;
    private MainUI mainUI;

    private Map<String, Vector<String>> queries;

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
        this.setLocation((int)((size.getWidth() - size.width/4) / 2), (int)((size.getHeight() - size.height / 2) / 2));//BLEAT GOVNO
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
                this.setMinimumSize(new Dimension(1000,600));
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

    public void setQueries(Map<String, Vector<String>> queries){
        this.queries = queries;
        this.dataBaseUI.getQUERIESlist().setQueryList(queries.keySet());
    }

    public void addListenerControlPane(){
        this.dataBaseUI.getTableSelect().addActionListener(e -> {
            try {
                JComboBox is = (JComboBox) e.getSource();
                String selectTable = (String) is.getSelectedItem();
                Pair<Vector<Vector<String>>, Vector<String>> tmp = this.controllerDB.selectTableData(selectTable);
                dataBaseUI.getCursorDB().setHeaderAndData(tmp.getFirst(), tmp.getSecond());
                this.dataBaseUI.blockedManipulatedButton(BLOCK_BUTTON_FLAGS.ALL_BUTTON_BLOCK, true);
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

        this.dataBaseUI.getQUERIESlist().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                 dataBaseUI.getFormPanel().setForm(queries.get(dataBaseUI.getQUERIESlist().getSelectedValue()));
                 dataBaseUI.blockedManipulatedButton(BLOCK_BUTTON_FLAGS.ALL_BUTTON_BLOCK, false);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

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
                this.dataBaseUI.blockedManipulatedButton(BLOCK_BUTTON_FLAGS.PART_BUTTON_BLOCK, false);
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

        this.dataBaseUI.getGoButton().addActionListener(e -> {
            CurrentQueryList tmpList = this.dataBaseUI.getQUERIESlist();
            if(!tmpList.isSelectionEmpty()){
                try {
                    String queryName = (String) tmpList.getSelectedValue();
                    Map<String, String> queryParams = this.dataBaseUI.getFormPanel().getMap();
                    Pair<String, Map<String, String>> query = new Pair<>(queryName, queryParams);
                    Pair<Vector<Vector<String>>, Vector<String>> resultQuery = this.controllerDB.userRequestExec(query);
                    this.dataBaseUI.getCursorDB().setHeaderAndData(resultQuery.getFirst(), resultQuery.getSecond());
                    this.dataBaseUI.blockedManipulatedButton(BLOCK_BUTTON_FLAGS.PART_BUTTON_BLOCK, true);//BlockedGoButton!!!!
                }catch (Exception ex){
                    dataBaseUI.setStatusActionLabel(ex.getMessage());
                }
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
