package View;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JPanel{

    private JLabel frameHeader;

    private JButton queryBtn;
    private JButton editBtn;
    private JButton readOnlyBtn;

    public JButton getQueryBtn() {
        return queryBtn;
    }

    public JButton getEditBtn() {
        return editBtn;
    }

    public JButton getReadOnlyBtn() {
        return readOnlyBtn;
    }

    public JButton getLogOutBtn() {
        return logOutBtn;
    }

    private JButton logOutBtn;

    public MainUI(){

        this.setBounds(10,10,400,700);
        frameHeader = new JLabel("Menu");
        queryBtn = new JButton("Queries");
        editBtn = new JButton("Edit");
        readOnlyBtn = new JButton("Only read mode");
        logOutBtn = new JButton("LogOut");

        setManagerLayout();
        addToPanel();
        this.setVisible(true);
    }

    public void addToPanel(){
        add(this.frameHeader);
        add(this.queryBtn);
        add(this.editBtn);
        add(this.readOnlyBtn);
        add(this.logOutBtn);
    }

    public void setManagerLayout(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }



}
