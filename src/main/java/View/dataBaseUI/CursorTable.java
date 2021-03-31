package View.dataBaseUI;

import Model.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class CursorTable {
    private Vector<Vector<String>> data;
    private Vector<String> headers;
    private int indexCur;

    private FormPanel formPanel;
    private CurrentTable table;

    private Vector<String> curRowData;

    public CursorTable(CurrentTable table, FormPanel formPanel){
        this.table = table;
        this.formPanel = formPanel;
    }

    public void setHeaderAndData(Vector<Vector<String>> data, Vector<String> headers){
        this.curRowData = null;
        this.data = data;
        this.headers = headers;
        indexCur = 0;
        if(data.size() != 0)
            this.curRowData = data.elementAt(indexCur);
            table.setTable(data, headers);
            formPanel.setForm(headers);
            formPanel.setValues(this.curRowData);
            if(data.size() != 0)this.table.setSelectionRow(indexCur);
    }

    public Vector<String> getHeaders(){
        return this.headers;
    }

    public void setIndexCur(int index){
        if(index < data.size() || index >= data.size()){
            this.indexCur = index;
            this.curRowData = data.elementAt(this.indexCur);
            formPanel.setValues(this.curRowData);
        }
    }

    public void next(){
        if(this.indexCur + 1 < data.size()){
            this.indexCur += 1;
            this.curRowData = data.elementAt(this.indexCur);
            formPanel.setValues(this.curRowData);
            table.setSelectionRow(this.indexCur);
        }
    }
    public void prev(){
        if(this.indexCur - 1 >= 0) {
            this.indexCur -= 1;
            this.curRowData = data.elementAt(this.indexCur);
            formPanel.setValues(this.curRowData);
            table.setSelectionRow(this.indexCur);
        }
    }

    public Vector<String> getCurRowData(){
        return this.curRowData;
    }
}
