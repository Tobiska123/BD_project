package View.dataBaseUI;

import javafx.geometry.Pos;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class FormPanel extends JPanel {

    private LinkedHashMap<JLabel, JTextField> formForCurTable;

    public void setForm(Vector<String> headers){
        this.removeAll();
        this.formForCurTable = new LinkedHashMap<>();
        GridBagConstraints gcd = new GridBagConstraints();
        int chordY = 0;
        for(String iter_header: headers){
              JLabel tmp_label = new JLabel(iter_header);
              gcd.anchor = GridBagConstraints.WEST;
              gcd.gridx = 0;
              gcd.gridy = chordY;
              add(tmp_label, gcd);

              JTextField textField = new JTextField(70);
              gcd.anchor = GridBagConstraints.EAST;
              gcd.gridx = GridBagConstraints.RELATIVE;
              gcd.gridy = chordY;
               gcd.weightx = 0.1;
              add(textField, gcd);
              this.formForCurTable.put(tmp_label, textField);
              chordY++;
        }
        this.setMaximumSize(new Dimension(600, 250));
        this.revalidate();
        this.repaint();
    }

    public void setValues(Vector<String> values){
        if(values == null) return;
        Iterator it_fields = formForCurTable.values().iterator();
        Iterator it_value = values.iterator();

        while(it_fields.hasNext() || it_value.hasNext()){
            JTextField tmp_field = ((JTextField)it_fields.next());
            String tmp_value = (String)it_value.next();
            tmp_field.setText(tmp_value);
        }
    }
    public Vector<String> getValues(){
        Vector<String> resultValues = new Vector<>();
        for(JTextField iter_field: this.formForCurTable.values())//FIX!!!
            resultValues.add(iter_field.getText());
        return resultValues;
    }

    public Map<String, String> getMap(){
        Map<String, String> jCompToStrMap = new LinkedHashMap<>();
        for(Map.Entry<JLabel, JTextField> iter: this.formForCurTable.entrySet()){
            jCompToStrMap.put(iter.getKey().getText(), iter.getValue().getText());
        }
        return jCompToStrMap;
    }
}
