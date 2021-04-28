package View.dataBaseUI;


import javax.swing.*;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class CurrentQueryList extends JList {
    private DefaultListModel listModel;
    public CurrentQueryList() {
        listModel = new DefaultListModel();
        this.setModel(listModel);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void setQueryList(Set set){
        this.listModel.removeAllElements();
        set.forEach(element -> this.listModel.addElement(element));
    }
}
