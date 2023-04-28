package electricitybillingsystem;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;


public class PayBill extends JFrame implements ActionListener{
    
    Choice cmonth;
    JButton pay,back;
    String meter;
    int totalBill;
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
        
        JLabel lbpenalty = new JLabel("Penalty");
        lbpenalty.setBounds(35,320,200,20);
        add(lbpenalty);
        
        JLabel labelpenalty = new JLabel("");
        labelpenalty.setBounds(300,320,200,20);
        add(labelpenalty);
        
        JLabel lbtotalbill = new JLabel("Total Bill");
        lbtotalbill.setBounds(35,380,200,20);
        add(lbtotalbill);
        
        JLabel labeltotalbill = new JLabel("");
        labeltotalbill.setBounds(300,380,200,20);
        add(labeltotalbill);
        
        JLabel lbstatus = new JLabel("Status");
        lbstatus.setBounds(35,420,200,20);
        add(lbstatus);
        
        JLabel labelstatus = new JLabel("");
        labelstatus.setBounds(300,420,200,20);
        labelstatus.setForeground(Color.RED);               
        add(labelstatus);
        
           try {
                 Conn c = new Conn();
                   ResultSet rs = c.s.executeQuery("SELECT * FROM customer WHERE meter_no = '"+meter+"'");
                  while(rs.next()){
                  meternumber.setText(meter);
                  labelname.setText(rs.getString("name"));
               }
    
                  rs = c.s.executeQuery("SELECT * FROM bill WHERE meter_no = '"+meter+"' AND month = 'january'");
                 while(rs.next()){
                  String status = rs.getString("status");
                  Date dueDate = rs.getDate("paymentdate");
                  Date currentDate = new Date(System.currentTimeMillis());

        
                   // If the status is unpaid and the current date is greater than the due date
                    if(status.equals("Not Paid") && currentDate.after(dueDate)){
                      int penalty = rs.getInt("penalty");
                    if(penalty == 0){ // Only update the penalty once
                         penalty = 15;
                           totalBill = rs.getInt("totalbill");
                           totalBill += penalty;
                
                         // Update the penalty and totalbill columns in the bill table
                        String query = "UPDATE bill SET penalty = "+penalty+", totalbill = "+totalBill+" WHERE meter_no = '"+meter+"' AND month = 'january'";
                          c.s.executeUpdate(query);
                
                          // Update the UI
                          labelpenalty.setText(Integer.toString(penalty));
                          labeltotalbill.setText(Integer.toString(totalBill));
                     }
                  }
                  else{
            labelunits.setText(rs.getString("units"));
            labeltotalbill.setText(rs.getString("totalbill"));
            labelstatus.setText(status);
            labelpenalty.setText(rs.getString("penalty"));
        }
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
    
   public void actionPerformed(ActionEvent ae) {
    //int totalBill = 10;
     if (ae.getSource() == pay){
    String qrCodeText = "Total Bill: " + totalBill + " rupees";
    int size = 250;
    String fileType = "png";
    File qrFile = new File("qrcode.png");
    try {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hints);
        int width = bitMatrix.getWidth();
        BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        ImageIO.write(image, fileType, qrFile);
    } catch (WriterException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    BufferedImage qrCodeImage = null;
    try {
        qrCodeImage = ImageIO.read(qrFile);
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(size, size);
    frame.setLocationRelativeTo(null);
    QRCodePanel panel = new QRCodePanel(qrCodeImage);
    frame.setContentPane(panel);
    frame.setVisible(true);
      try{
               Conn c = new Conn();
               c.s.executeUpdate("Update bill set status = 'Paid',paymentdate=NOW() where meter_no = '"+meter+"' and month = '"+cmonth.getSelectedItem()+"'");
            }catch(Exception e)
            {       
              e.printStackTrace();
            }
     }
}


public static void main(String[] args)
{
    new PayBill("");
}
}
