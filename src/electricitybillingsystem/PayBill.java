package electricitybillingsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;


public class PayBill extends JFrame implements ActionListener{
    
    Choice cmonth;
    JButton pay,back;
    String meter;
    PayBill(String meter){
        this.meter = meter;
        setLayout(null);
        setBounds(300,150,900,600);
        
        JLabel heading = new JLabel("Electricity Bill");
        heading.setFont(new Font("Tahoma", Font.BOLD,24));
        heading.setBounds(120,5,400,30);
        add(heading);
        setVisible(true);
        
        JLabel lbmeternumber = new JLabel("Meter Number");
        lbmeternumber.setBounds(35,80,200,20);
        add(lbmeternumber);
        
        JLabel meternumber = new JLabel("");
        meternumber.setBounds(300,80,200,20);
        add(meternumber);
        
        JLabel lbname = new JLabel("Name");
        lbname.setBounds(35,140,200,20);
        add(lbname);
        
        JLabel labelname = new JLabel("");
        labelname.setBounds(300,140,200,20);
        add(labelname);
        
        JLabel lbmonth = new JLabel("Month");
        lbmonth.setBounds(35,200,200,20);
        add(lbmonth);
        cmonth = new Choice();
        cmonth.setBounds(300,200,200,20);
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
        add(cmonth);
        
        JLabel lbunits = new JLabel("Units");
        lbunits.setBounds(35,260,200,20);
        add(lbunits);
        
        JLabel labelunits = new JLabel("");
        labelunits.setBounds(300,260,200,20);
        add(labelunits);
        
        
        JLabel lbtotalbill = new JLabel("Total Bill");
        lbtotalbill.setBounds(35,320,200,20);
        add(lbtotalbill);
        
        JLabel labeltotalbill = new JLabel("");
        labeltotalbill.setBounds(300,320,200,20);
        add(labeltotalbill);
        
        JLabel lbstatus = new JLabel("Status");
        lbstatus.setBounds(35,380,200,20);
        add(lbstatus);
        
        JLabel labelstatus = new JLabel("");
        labelstatus.setBounds(300,380,200,20);
        labelstatus.setForeground(Color.RED);               
        add(labelstatus);
        
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meter+"'");
            while(rs.next()){
            meternumber.setText(meter);
            labelname.setText(rs.getString("name"));
            }
            
            rs = c.s.executeQuery("select * from bill where meter_no = '"+meter+"' and month = 'january'");
            while(rs.next()){
            labelunits.setText(rs.getString("units"));
            labeltotalbill.setText(rs.getString("totalbill"));
            labelstatus.setText(rs.getString("status"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        cmonth.addItemListener(new ItemListener(){
            @Override
        public void itemStateChanged(ItemEvent ae) {
            try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from bill where meter_no = '"+meter+"' and month = '"+cmonth.getSelectedItem()+"'");
            while(rs.next()){
            labelunits.setText(rs.getString("units"));
            labeltotalbill.setText(rs.getString("totalbill"));
            labelstatus.setText(rs.getString("status"));
            }
        }catch(Exception e){
            e.printStackTrace();
            }
          }
        
        });
        
        pay = new JButton("Pay");
        pay.setBackground(Color.BLACK);
        pay.setForeground(Color.WHITE);
        pay.setBounds(100, 460, 100, 25);
        pay.addActionListener(this);
        add(pay); 
        
        back = new JButton("Back");
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBounds(230, 460, 100, 25);
        back.addActionListener(this);
        add(back); 
        
        getContentPane().setBackground(Color.WHITE);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bill.png"));
        Image i2 = i1.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400, 120, 600, 300);
        add(image);
        
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == pay){
            
            try{
               Conn c = new Conn();
               c.s.executeUpdate("Update bill set status = 'Paid' where meter_no = '"+meter+"' and month = '"+cmonth.getSelectedItem()+"'");
            }catch(Exception e)
            {       
              e.printStackTrace();
            }
        setVisible(false);
        new Paytm(meter);
        }
        else {
            setVisible(false);
        }
    }

public static void main(String[] args)
{
    new PayBill("");
}
}
