package electricitybillingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener{
    JButton login,cancel,signup;
    JTextField username,password;
    Choice loggin;
    Login(){
        
        super("Login Page");
        
        getContentPane().setBackground(Color.WHITE);   //access the frame and change the color of frame
        setLayout(null);
        JLabel lbusername = new JLabel("Username");
        lbusername.setBounds(300, 20, 100, 20);
        add(lbusername);
        
        username = new JTextField();
        username.setBounds(400,20,150,20);
        add(username);
        
        JLabel lbpassword = new JLabel("Password");
        lbpassword.setBounds(300, 60, 100, 20);
        add(lbpassword);
        
        password = new JTextField();
        password.setBounds(400,60,150,20);
        add(password);
        
        JLabel lbloggininas = new JLabel("login as");
        lbloggininas.setBounds(300,100,100,20);
        add(lbloggininas);
        
        loggin = new Choice();
        loggin.add("Admin");
        loggin.add("Customer");
        loggin.setBounds(400,100,150,20);
        add(loggin);
        
        login = new JButton("Login");
        login.setBounds(330,160,100,20);
        login.addActionListener(this);
        add(login);
        
        cancel = new JButton("Cancel");
        cancel.setBounds(450,160,100,20);
        cancel.addActionListener(this);
        add(cancel);
        
        signup = new JButton("signup");
        signup.setBounds(380,200,100,20);
        signup.addActionListener(this);
        add(signup);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0,0,250,250);
        add(image);
        
        
        setSize(640,300);
        setLocation(400,300);
        setVisible(true);
        
        
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==login){
            String susername = username.getText();
            String spassword = password.getText();
            String user = loggin.getSelectedItem();
            
            try {
                Conn c = new Conn();
                String query = " Select * from login where username = '"+susername+"' and password = '"+spassword+"' and user = '"+user+"'";
                
              ResultSet rs = c.s.executeQuery(query);
              
              if (rs.next()){
                  String meter = rs .getString("meter_no");
                  setVisible(false);
                  new Project(user, meter);
              }             
              else{
                  JOptionPane.showMessageDialog(null, "Invalid login");
                  username.setText(" ");//to empty the text field
                  password.setText(" ");//to empty the text field
              }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        else if(ae.getSource()==cancel){
            setVisible(false);
            
        }
        else if(ae.getSource()==signup){
            setVisible(false);
            new Signup();
            
        }
    }
    
    public static void main(String[]args){
        new Login();
    }
    
}
