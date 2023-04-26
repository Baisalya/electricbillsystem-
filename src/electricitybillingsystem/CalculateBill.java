package electricitybillingsystem;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculateBill extends JFrame implements ActionListener {
   
    JTextField tfname, tfaddress, tfstate, tfunits, tfemail, tfphone;
    JButton next, cancel;
    JLabel lbname, labeladdress;
    Choice meternumber, cmonth;
    
    CalculateBill()
            {
              setSize(700,500);
              setLocation(400,150);
              
              JPanel p = new JPanel();
              p.setLayout(null);
              p.setBackground(new Color(173,216,230));
              add(p);
              
              JLabel heading = new JLabel("Calculate Electricity Bill");
              heading.setBounds(100, 10, 400, 25);
              heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
              p.add(heading);
              
              JLabel lbmeternumber = new JLabel("Meter Number");
              lbmeternumber.setBounds(100, 80, 100, 20);
              p.add(lbmeternumber);
              
              meternumber = new Choice();
              
              try{
                  Conn c = new Conn();
                  ResultSet rs = c.s.executeQuery("Select * from customer");
                  while(rs.next()){
                      meternumber.add(rs.getString("meter_no"));
                  }
              }catch (Exception e){
                  e.printStackTrace();
              }
              meternumber.setBounds(240, 80, 200, 20);
              p.add(meternumber);
              
              JLabel lbmeterno = new JLabel("Name");
              lbmeterno.setBounds(100, 120, 100, 20);
              p.add(lbmeterno);
              
              lbname = new JLabel("");
              lbname.setBounds(240, 120, 100, 20);
              p.add(lbname);
              
              
              JLabel lbaddress = new JLabel("Address");
              lbaddress.setBounds(100, 160, 100, 20);
              p.add(lbaddress);
              
              labeladdress = new JLabel();
              labeladdress.setBounds(240, 160, 200,20);
              p.add(labeladdress);
              
              try{
                 Conn c = new Conn();
                 ResultSet rs = c.s.executeQuery("Select * from customer where meter_no ='"+meternumber.getSelectedItem()+"'");
                 while(rs.next()){
                     lbname.setText(rs.getString("name"));
                     labeladdress.setText(rs.getString("address"));
                 }
              }catch (Exception e){
                   e.printStackTrace();
                  
              }
              
              meternumber.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent ie){
                     
                   try{
                        Conn c = new Conn();
                        ResultSet rs = c.s.executeQuery("Select * from customer where meter_no ='"+meternumber.getSelectedItem()+"'");
                        while(rs.next()){
                        lbname.setText(rs.getString("name"));
                        labeladdress.setText(rs.getString("address"));
                      }
                     }catch (Exception e){
                     e.printStackTrace();
                  
                   }
                  
                  }
              });
              
              JLabel lbcity = new JLabel("Unit Consume");
              lbcity.setBounds(100, 200, 100, 20);
              p.add(lbcity);
              
              tfunits = new JTextField();
              tfunits.setBounds(240, 200, 200,20);
              p.add(tfunits);
              
              JLabel lbstate = new JLabel("Month");
              lbstate.setBounds(100, 240, 100, 20);
              p.add(lbstate);
              
              cmonth = new Choice();
              cmonth.setBounds(240, 240, 200,20);
              cmonth.add("January");
              cmonth.add("February");
              cmonth.add("March");
              cmonth.add("April");
              cmonth.add("May");
              cmonth.add("June");
              cmonth.add("July");
              cmonth.add("August");
              cmonth.add("September");
              cmonth.add("October");
              cmonth.add("November");
              cmonth.add("December");
              p.add(cmonth);
           
              next = new JButton("Submit");
              next.setBounds(120,350,100,25);
              next.setBackground(Color.BLACK);
              next.setForeground(Color.WHITE);
              next.addActionListener(this);
              p.add(next);
           
              cancel = new JButton("Cancel");
              cancel.setBounds(250,350,100,25);
              cancel.setBackground(Color.BLACK);
              cancel.setForeground(Color.WHITE);
              cancel.addActionListener(this);
              p.add(cancel);
              
              setLayout( new BorderLayout());
              add(p, "Center");
              
              ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon2.jpg"));
              Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
              ImageIcon i3 = new ImageIcon(i2);
              JLabel image = new JLabel(i3);
              add(image,"West");
              getContentPane().setBackground(Color.WHITE);
              
              setVisible(true);
            }
    
             public void actionPerformed(ActionEvent ae){
                 if (ae.getSource() == next){
                     String meter = meternumber.getSelectedItem();
                     String units = tfunits.getText();
                     String month = cmonth.getSelectedItem();
                     // Get the current date and add 5 days
                    LocalDate currentDate = LocalDate.now();
                   LocalDate dateAfterFiveDays = currentDate.plusDays(5);

                  // Format the date as a string using the MySQL date format
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                   String formattedDate = dateAfterFiveDays.format(formatter);

                     int totalbill = 0;  
                     int unit_consumed = Integer.parseInt(units);
                     
                     String query = "select * from tax";
                     
                     try{
                       Conn c = new Conn();
                       ResultSet rs =  c.s.executeQuery(query);
                       while(rs.next()){
                       totalbill+= unit_consumed  * Integer.parseInt(rs.getString("cost_per_unit"));
                       totalbill+= Integer.parseInt(rs.getString("meter_rent"));
                       totalbill+= Integer.parseInt(rs.getString("service_charge"));
                       totalbill+= Integer.parseInt(rs.getString("service_tax"));
                       totalbill+= Integer.parseInt(rs.getString("swacch_bharat_cess"));
                       totalbill+= Integer.parseInt(rs.getString("fixed_tax"));
                       }
                     } catch(Exception e){
                         e.printStackTrace();
                     }
                     
                     String query2 = "INSERT INTO bill (meter_no, month, units, totalbill, penalty, status, billdate, duedate) " +
                "VALUES ('" + meter + "', '" + month + "', '" + units + "', '" + totalbill + "', '0', 'Not Paid', NOW(), '" + formattedDate + "')";

                     
                     try{
                         Conn c = new Conn();
                         c.s.executeUpdate(query2);
                         
                         JOptionPane.showMessageDialog(null, "Customer Bill Updated Successfully");
                         setVisible(false);
                     }catch (Exception e){
                         e.printStackTrace();
                     }
                 }
                 else{
                     setVisible(false);
                 }
             }  
    
    public static void main (String[] args){
        new CalculateBill();
    }
    
}

