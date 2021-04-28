package View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class LoginUI extends JPanel {

        JLabel urlLabel = new JLabel("ADDRESS");
        JLabel userLabel = new JLabel("USERNAME");
        JLabel passwordLabel = new JLabel("PASSWORD");

        JTextField urlTextField = new JTextField();
        JTextField userTextField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Авторизация");
        JButton resetButton = new JButton("Сброс");
        JCheckBox showPassword = new JCheckBox("Показать пароль");
        JLabel stat_label = new JLabel();


        public String getTextUserTextField(){
            return this.userTextField.getText();
        }

        public String getTextPasswordTextField(){
            String password = new String(this.passwordField.getPassword());
            return password;
        }

        public String getURLTextField(){
            return this.urlTextField.getText();
        }

        LoginUI()
        {
            this.urlTextField.setText("jdbc:oracle:thin:@84.237.50.81:1521:xe");
            this.userTextField.setText("18209_Levchenko");
            this.passwordField.setText("1111");
            this.setSize(400,700);
            this.setVisible(true);
            setLayoutManager();
            setLocationAndSize();
            addComponentsToContainer();

            showPassword.addActionListener(e->{
                    AbstractButton abstractButton = (AbstractButton)e.getSource();
                    ButtonModel buttonModel = abstractButton.getModel();
                    if(buttonModel.isSelected()){
                        passwordField.setEchoChar('*');
                    }else{
                        passwordField.setEchoChar((char)0);
                    }
            });
        }

        public JTextField getUserTextField(){
            return this.userTextField;
        }

        public JPasswordField getPasswordField(){
            return this.passwordField;
        }

        public JButton getLoginButton(){
            return this.loginButton;
        }

        public void setLayoutManager()
        {
            setLayout(null);
        }

        public void setLocationAndSize()
        {
            urlLabel.setBounds(50, 100, 100, 30);
            userLabel.setBounds(50,150,100,30);
            passwordLabel.setBounds(50,220,100,30);

            urlTextField.setBounds(150, 100, 150, 30);
            userTextField.setBounds(150,150,150,30);
            passwordField.setBounds(150,220,150,30);

            showPassword.setBounds(150,250,150,30);
            loginButton.setBounds(50,300,100,30);
            resetButton.setBounds(200,300,100,30);

            stat_label.setBounds(50, 350, 300, 200);
        }
        public void addComponentsToContainer()
        {
            add(userLabel);
            add(passwordLabel);
            add(urlLabel);
            add(userTextField);
            add(passwordField);
            add(urlTextField);
            add(showPassword);
            add(loginButton);
            add(resetButton);
            add(stat_label);
        }
    }