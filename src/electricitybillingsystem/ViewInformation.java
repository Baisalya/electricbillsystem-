package electricitybillingsystem;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class ViewInformation extends JFrame implements ActionListener {
      JButton cancel;
      ViewInformation(String meter){
      setBounds(350,150,850,650);
      getContentPane().setBackground(Color.WHITE);
      setLayout(null);
      
      JLabel heading = new JLabel ("VIEW CUSTOMER INFORMATIOM");
      heading.setBounds(250,0,500,40);
      heading.setFont(new Font("tahoma", Font.PLAIN,20));
      add(heading);
      
      JLabel lbname = new JLabel ("Name");
      lbname.setBounds(70,80,100,20);
      add(lbname);
      
      JLabel name = new JLabel ("");
      name.setBounds(250,80,100,20);
      add(name);
      
      JLabel lbmeternumber = new JLabel ("Meter Number");
      lbmeternumber.setBounds(70,140,100,20);
      add(lbmeternumber);
      
      JLabel meternumber = new JLabel ("");
      meternumber.setBounds(250,140,100,20);
      add(meternumber);
      
      JLabel lbaddress = new JLabel ("Address");
      lbaddress.setBounds(70,200,100,20);
      add(lbaddress);
      
      JLabel address = new JLabel ("");
      address.setBounds(250,200,100,20);
      add(address);
      
      JLabel lbcity = new JLabel ("City");
      lbcity.setBounds(70,260,100,20);
      add(lbcity);
      
      JLabel city = new JLabel ("");
      city.setBounds(250,260,100,20);
      add(city);
      
      JLabel lbstate = new JLabel ("State");
      lbstate.setBounds(500,80,100,20);
      add(lbstate);
      
      JLabel state = new JLabel ("");
      state.setBounds(650,80,100,20);
      add(state); 
      
       JLabel lbemail = new JLabel ("Email");
      lbemail.setBounds(500,140,100,20);
      add(lbemail);
      
      JLabel email = new JLabel ("");
      email.setBounds(650,140,100,20);
      add(email); 
      
       JLabel lbphone = new JLabel ("Phone Number");
      lbphone.setBounds(500,200,100,20);
      add(lbphone);
      
      JLabel phone = new JLabel ("");
      phone.setBounds(650,200,100,20);
      add(phone);
      
      try{
          Conn c = new Conn();
          ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meter+"'");
          while(rs.next()){
              name.setText(rs.getString("name"));
              address.setText(rs.getString("address"));
              city.setText(rs.getString("city"));
              state.setText(rs.getString("state"));
              email.setText(rs.getString("email"));
              phone.setText(rs.getString("phone"));
              meternumber.setText(rs.getString("meter_no"));
          }
      }catch (Exception e){
          e.printStackTrace();
      }
      
         
      cancel = new JButton("Cancel");
      cancel.setBackground(Color.BLACK);
      cancel.setForeground(Color.WHITE);
      cancel.setBounds(250,340,100,25);
      add(cancel);
      cancel.addActionListener(this);
      
      ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/viewcustomer.jpg"));
      Image i2 = i1.getImage().getScaledInstance(600,300,Image.SCALE_DEFAULT);
      ImageIcon i3 = new ImageIcon(i2);
      JLabel image = new JLabel(i3);
      image.setBounds(20,350,600,300);
      add(image);
      
      setVisible(true);
      
  }
      public void actionPerformed(ActionEvent ae){
          setVisible(false);
      }
        
    
    public static void main(String[] args){
        new ViewInformation("");
        
    }
}
