package electricitybillingsystem;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class NewCustomer extends JFrame implements ActionListener {
   
    JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
    JButton next, cancel;
    JLabel lbmeter;
    NewCustomer()
            {
              setSize(700,500);
              setLocation(400,200);
              
              JPanel p = new JPanel();
              p.setLayout(null);
              p.setBackground(new Color(173,216,230));
              add(p);
              
              JLabel heading = new JLabel("New customer");
              heading.setBounds(180, 10, 200, 25);
              heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
              p.add(heading);
              
              JLabel lbname = new JLabel("Customer Name");
              lbname.setBounds(100, 80, 100, 20);
              p.add(lbname);
              
              tfname = new JTextField();
              tfname.setBounds(240, 80, 200,20);
              p.add(tfname);
            
              
              lbmeter = new JLabel("");
              lbmeter.setBounds(240, 120, 100, 20);
              p.add(lbmeter);
              
              
              JLabel lbmeterno = new JLabel("Meter Number");
              lbmeterno.setBounds(100, 120, 100, 20);
              p.add(lbmeterno);
              
              Random ran = new Random();
              long number = ran.nextLong() % 1000000;
              lbmeter.setText("" + Math.abs(number));
              
              JLabel lbaddress = new JLabel("Address");
              lbaddress.setBounds(100, 160, 100, 20);
              p.add(lbaddress);
              
              tfaddress = new JTextField();
              tfaddress.setBounds(240, 160, 200,20);
              p.add(tfaddress);
              
              JLabel lbcity = new JLabel("City");
              lbcity.setBounds(100, 200, 100, 20);
              p.add(lbcity);
              
              tfcity = new JTextField();
              tfcity.setBounds(240, 200, 200,20);
              p.add(tfcity);
              
              JLabel lbstate = new JLabel("State");
              lbstate.setBounds(100, 240, 100, 20);
              p.add(lbstate);
              
              tfstate = new JTextField();
              tfstate.setBounds(240, 240, 200,20);
              p.add(tfstate);
              
              JLabel lbemail = new JLabel("Email");
              lbemail.setBounds(100, 280, 100, 20);
              p.add(lbemail);
          
              tfemail = new JTextField();
              tfemail.setBounds(240, 280, 200,20);
              p.add(tfemail);
            
              
              JLabel lbphone = new JLabel("Phone Number");
              lbphone.setBounds(100, 320, 100, 20);
              p.add(lbphone);
          
              tfphone = new JTextField();
              tfphone.setBounds(240, 320, 200,20);
              p.add(tfphone);
              
              next = new JButton("Next");
              next.setBounds(120,390,100,25);
              next.setBackground(Color.BLACK);
              next.setForeground(Color.WHITE);
              next.addActionListener(this);
              p.add(next);
           
              cancel = new JButton("Cancel");
              cancel.setBounds(250,390,100,25);
              cancel.setBackground(Color.BLACK);
              cancel.setForeground(Color.WHITE);
              cancel.addActionListener(this);
              p.add(cancel);
              
              setLayout( new BorderLayout());
              add(p, "Center");
              
              ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon1.jpg"));
              Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
              ImageIcon i3 = new ImageIcon(i2);
              JLabel image = new JLabel(i3);
              add(image,"West");
              getContentPane().setBackground(Color.WHITE);
              
              setVisible(true);
            }
    
             public void actionPerformed(ActionEvent ae){
                 if (ae.getSource() == next){
                     String name = tfname.getText();
                     String meter = lbmeter.getText();
                     String address = tfaddress.getText();
                     String city = tfcity.getText();
                     String state = tfstate.getText();
                     String email = tfemail.getText();
                     String phone = tfphone.getText();
                     
                     String query1 = "insert into customer values('"+name+"', '"+meter+"', '"+address+"', '"+city+"', '"+state+"', '"+email+"', '"+phone+"')";
                     String query2 = "insert into login values('"+meter+"', '', '"+name+"','','')";
                     
                     try{
                        Conn c = new Conn();
                        c.s.executeUpdate(query1);
                        c.s.executeUpdate(query2);
                        
                        JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
                        setVisible(false);
                        
                        //new frame
                         new MeterInfo(meter);
                        
                     } catch(Exception e){
                         e.printStackTrace();
                     }
                 }
                 else{
                     setVisible(false);
                 }
             }  
    
    public static void main (String[] args){
        new NewCustomer();
    }
    
}
