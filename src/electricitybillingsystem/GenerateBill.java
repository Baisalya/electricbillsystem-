package electricitybillingsystem;

import com.itextpdf.text.Chunk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.FileOutputStream;
public class GenerateBill extends JFrame implements ActionListener {
    
    String meter;
    JButton bill;
    Choice cmonth;
    JTextArea area;
    GenerateBill(String meter){
        this.meter = meter;
        setSize(500, 800);
        setLocation(500, 30);
        
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        
        JLabel heading = new JLabel("Generate Bill");
        JLabel meternumber = new JLabel(meter);
                                
        
        cmonth = new Choice();
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
       
        area = new JTextArea(50, 15);
        area.setText("\n\n\t-------Click on the------------\n\t Generate Bill Button to get\n\t the bill of the selected month");
        area.setFont(new Font("Senserif", Font.ITALIC, 18));
        
        JScrollPane pane = new JScrollPane(area);
        
        bill = new JButton("Generate Bill");
        bill.addActionListener(this);
        
        panel.add(heading);
        panel.add(meternumber);
        panel.add(cmonth);
        add(panel, "North");
        
        add(pane, "Center");
        add(bill, "South");
        
        
        setVisible(true);
        
        
    }
    
 public void actionPerformed(ActionEvent ae) {
    try {
        Conn c = new Conn();
        String month = cmonth.getSelectedItem();
        
        // create a new document
        Document document = new Document();
        // create a PdfWriter instance to write the document to a file
        PdfWriter.getInstance(document, new FileOutputStream("bill.pdf"));
        document.open();
        
        // add the text to the document
        Paragraph heading = new Paragraph("\tReliance Power Limited\nELECTRICITY BILL GENERATED FOR THE MONTH\n\tOF " + month + ",2022\n\n\n");
        document.add(heading);
        
        ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '" + meter + "'");
        if (rs.next()) {
            Paragraph customerInfo = new Paragraph();
            customerInfo.add(new Phrase("    Customer Name : " + rs.getString("name")));
            customerInfo.add(Chunk.NEWLINE);
            customerInfo.add(new Phrase("    Meter Number    : " + rs.getString("meter_no")));
            customerInfo.add(Chunk.NEWLINE);
            customerInfo.add(new Phrase("    Address              : " + rs.getString("address")));
            customerInfo.add(Chunk.NEWLINE);
            customerInfo.add(new Phrase("    City                     : " + rs.getString("city")));
            customerInfo.add(Chunk.NEWLINE);
            customerInfo.add(new Phrase("    State                  : " + rs.getString("state")));
            customerInfo.add(Chunk.NEWLINE);
            customerInfo.add(new Phrase("    Email                 : " + rs.getString("email")));
            customerInfo.add(Chunk.NEWLINE);
            customerInfo.add(new Phrase("    Phone               : " + rs.getString("phone")));
            customerInfo.add(Chunk.NEWLINE);
            customerInfo.add(new LineSeparator());
            document.add(customerInfo);
                area.append("\n    Customer Name : " + rs.getString("name"));
             area.append("\n    Meter Number    : " + rs.getString("meter_no"));
             area.append("\n    Address              : " + rs.getString("address"));
             area.append("\n    City                     : " + rs.getString("city"));
             area.append("\n    State                  : " + rs.getString("state"));
             area.append("\n    Email                 : " + rs.getString("email"));
             area.append("\n    Phone               : " + rs.getString("phone"));
             area.append("\n---------------------------------------------------");
             area.append("\n");
        }
           rs = c.s.executeQuery("select * from meter_info where meter_no = '"+meter+"'");
         
         if(rs.next()){
         
             area.append("\n    Meter Location : " + rs.getString("meter_location"));
             area.append("\n    Meter Type      : " + rs.getString("meter_type"));
             area.append("\n    Phase Code    : " + rs.getString("phase_code"));
             area.append("\n    Bill Type          : " + rs.getString("bill_type"));
             area.append("\n    Days               : " + rs.getString("days"));
             area.append("\n---------------------------------------------------");
             area.append("\n");
             Paragraph meterInfo = new Paragraph();
             meterInfo.add(new Phrase("    Meter Location: " + rs.getString("meter_location")));
             meterInfo.add(Chunk.NEWLINE);
             meterInfo.add(new Phrase("    Meter Type: " + rs.getString("meter_type")));
             meterInfo.add(Chunk.NEWLINE);
             meterInfo.add(new Phrase("    Phase Code: " + rs.getString("phase_code")));
             meterInfo.add(Chunk.NEWLINE);
             meterInfo.add(new Phrase("    Bill Type : " + rs.getString("bill_type")));
             meterInfo.add(Chunk.NEWLINE);
             meterInfo.add(new Phrase("     Days   : " + rs.getString("days")));
             meterInfo.add(Chunk.NEWLINE);
             meterInfo.add(new LineSeparator());
             document.add(meterInfo);
         }  
                  rs = c.s.executeQuery("select * from tax");
         
         if(rs.next()){
         Paragraph taxinfo = new Paragraph();
            taxinfo.add(new Phrase("    Cost per unit: " + rs.getString("cost_per_unit")));
             taxinfo.add(Chunk.NEWLINE);
             taxinfo.add(new Phrase("    Meter Rent: " + rs.getString("meter_rent")));
             taxinfo.add(Chunk.NEWLINE);
             taxinfo.add(new Phrase("    Service Charge: " + rs.getString("service_charge")));
             taxinfo.add(Chunk.NEWLINE);
             taxinfo.add(new Phrase("    Service Tax: " + rs.getString("service_tax")));
             taxinfo.add(Chunk.NEWLINE);
             taxinfo.add(new Phrase("    Swacch Bharat Cess: " + rs.getString("swacch_bharat_cess")));
             taxinfo.add(Chunk.NEWLINE);
             taxinfo.add(new Phrase("    Fixed Tax: " + rs.getString("fixed_tax")));
             taxinfo.add(Chunk.NEWLINE);
             taxinfo.add(new Phrase("    Service Tax: " + rs.getString("service_tax")));
             taxinfo.add(Chunk.NEWLINE);
             taxinfo.add(new LineSeparator());
             document.add(taxinfo);
             area.append("\n");
             area.append("\n    Cost per unit                 : " + rs.getString("cost_per_unit"));
             area.append("\n    Meter Rent                   : " + rs.getString("meter_rent"));
             area.append("\n    Service Charge           : " + rs.getString("service_charge"));
             area.append("\n    Service Tax                 : " + rs.getString("service_tax"));
             area.append("\n    Swacch Bharat Cess : " + rs.getString("swacch_bharat_cess"));
             area.append("\n    Fixed Tax                    : " + rs.getString("fixed_tax"));
             area.append("\n");
             
         }  
                rs = c.s.executeQuery("select * from bill where meter_no = '"+meter+"' and month = '"+month+"'");
         
         if(rs.next()){
              Paragraph billinfo = new Paragraph();
             billinfo.add(new Phrase("    Current Month : " + rs.getString("month")));
             billinfo.add(Chunk.NEWLINE);
             billinfo.add(new Phrase("    Units Consumed : " + rs.getString("units")));
             billinfo.add(Chunk.NEWLINE);
              billinfo.add(new LineSeparator());
             document.add(billinfo);
             billinfo.add(new Phrase("    Current Month : " + rs.getString("totalbill")));
             billinfo.add(Chunk.NEWLINE);
             billinfo.add(new Phrase("    Total Payable  : " + rs.getString("totalbill")));
             billinfo.add(Chunk.NEWLINE);
             area.append("\n");
             area.append("\n    Current Month         : " + rs.getString("month"));
             area.append("\n    Units Consumed     : " + rs.getString("units"));
             area.append("\n    Total Charges         : " + rs.getString("totalbill"));
             area.append("\n---------------------------------------------------");
             area.append("\n    Total Payable         : " + rs.getString("totalbill"));
             area.append("\n");
           
             
         }  
        // close the document
        document.close();
        System.out.println("PDF generated successfully");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
    public static void main(String[] args){
        new GenerateBill("");
        
    }
    
}
