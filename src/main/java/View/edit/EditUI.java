package View.edit;

import javax.swing.*;
import java.awt.*;

public class EditUI extends JPanel {

    private ControlPanel controlPanel;
    private ViewTablePanel viewPanel;

    public EditUI(){
        setPreferredSize(new Dimension(700, 300));
        setLayout(new BorderLayout());

        controlPanel = new ControlPanel();//??

        add(controlPanel, BorderLayout.WEST);

        viewPanel = new ViewTablePanel();

        add(viewPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public ViewTablePanel getViewPanel(){ return  viewPanel; }
}
