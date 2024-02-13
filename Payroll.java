
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public class Payroll extends javax.swing.JFrame {

    /**
     * Creates new form Payroll
     */
    public Payroll() {
        initComponents();
    }
    
    public void getPayRoll(){
        String name = txt_name.getText();
        String branch = txt_branch.getText();
        String level = txt_level.getText();
        int rate = Integer.parseInt(txt_rate.getText());
        int hours = Integer.parseInt (txt_hours.getText());
        int deductions = Integer.parseInt(txt_deductions.getText());
        int gross = Integer.parseInt(txt_gross.getText());
        int net = Integer.parseInt(txt_totals.getText());
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO payroll (name, branch, level, rate, hours, deductions, gross, net, timestamp_clm) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, name);
            pst.setString(2, branch);
            pst.setString(3, level);
            pst.setInt(4, rate);
            pst.setInt(5, hours);
            pst.setInt(6, deductions);
            pst.setInt(7, gross);
            pst.setInt(8, net);
            pst.executeUpdate();
            
            
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    
    public void displayDetails() throws BadLocationException {
        
    String name = txt_name.getText();
    String branch = txt_branch.getText();
    String level = txt_level.getText();
    int rate = Integer.parseInt(txt_rate.getText());
    int hours = Integer.parseInt(txt_hours.getText());
    int deductions = Integer.parseInt(txt_deductions.getText());
    int gross = Integer.parseInt(txt_gross.getText());
    int net = Integer.parseInt(txt_totals.getText());
        
    
    
JTextPane textPane = new JTextPane();
textPane.setEditable(false);

textPane.setPreferredSize(new Dimension(360, 200));

// Append the details to the text pane
StyledDocument doc = textPane.getStyledDocument();

// Setting the left margin
int leftMargin = 70;
SimpleAttributeSet leftMarginAttributes = new SimpleAttributeSet();
StyleConstants.setLeftIndent(leftMarginAttributes, leftMargin);

doc.setParagraphAttributes(0, doc.getLength(), leftMarginAttributes, false);

// Create a simple attribute set for bold text
SimpleAttributeSet boldAttributes = new SimpleAttributeSet();
StyleConstants.setBold(boldAttributes, true);
StyleConstants.setAlignment(boldAttributes, StyleConstants.ALIGN_CENTER); // Align center

doc.insertString(doc.getLength(), "Pharmer System Payroll\n\n", boldAttributes);

SimpleAttributeSet centerAttributes = new SimpleAttributeSet();
StyleConstants.setAlignment(centerAttributes, StyleConstants.ALIGN_CENTER);

doc.insertString(doc.getLength(), "Name: " + name + "\n", centerAttributes);
doc.insertString(doc.getLength(), "Branch: " + branch + "\n", centerAttributes);
doc.insertString(doc.getLength(), "Level: " + level + "\n", centerAttributes);
doc.insertString(doc.getLength(), "Rate: " + rate + "\n", centerAttributes);
doc.insertString(doc.getLength(), "Hours: " + hours + "\n", centerAttributes);
doc.insertString(doc.getLength(), "Deductions: " + deductions + "\n", centerAttributes);
doc.insertString(doc.getLength(), "Gross: " + gross + "\n", centerAttributes);
doc.insertString(doc.getLength(), "Net: " + net + "\n\n", centerAttributes);
doc.insertString(doc.getLength(), "Total pay: " + net + "\n", centerAttributes);

// Add the JTextPane to the jPanel1
jPanel1.setLayout(new BorderLayout());
jPanel1.add(new JScrollPane(textPane), BorderLayout.CENTER);


// Creating a JTextPane for signing details
    JTextPane signingPane = new JTextPane();
    signingPane.setEditable(false);

    // Creating a Font object for bold style
    Font boldFont = new Font(signingPane.getFont().getFamily(), Font.BOLD, signingPane.getFont().getSize());

    signingPane.setText("Approved By:\n" +
            "________\n\n" +
            "Date: ________");

    JTextPane rightSigningPane = new JTextPane();
    rightSigningPane.setEditable(false);

    rightSigningPane.setFont(boldFont);

    rightSigningPane.setText("Manager's Signature:\n" +
            "______\n\n" +
            "Date: _____");

    JTextPane nameSignaturePane = new JTextPane();
    nameSignaturePane.setEditable(false);

    nameSignaturePane.setFont(boldFont);

    nameSignaturePane.setText("Name Signature:\n" +
            "________");

    // Create a panel to hold signing components
    JPanel signingPanel = new JPanel();
    signingPanel.setLayout(new GridLayout(1, 3));
    signingPanel.add(signingPane);
    signingPanel.add(rightSigningPane);
    signingPanel.add(nameSignaturePane);

   // Set FlowLayout for jPanel1
jPanel1.removeAll();
jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));

// Add the JScrollPane containing the details
jPanel1.add(new JScrollPane(textPane));

// Add the signing panel
jPanel1.add(signingPanel);

// Update the UI
revalidate();
repaint();


}

    public void printDetails() {
    // Capture the contents of jPanel1
    BufferedImage image = new BufferedImage(jPanel1.getWidth(), jPanel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.getGraphics();
    jPanel1.paint(g);
    g.dispose();

    // Create a PrinterJob
    PrinterJob job = PrinterJob.getPrinterJob();

    // Setting up a printable object that will print the captured image
    job.setPrintable(new Printable() {
        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }

            // Draw the captured image on the printer graphics
            graphics.drawImage(image, 0, 0, jPanel1.getWidth(), jPanel1.getHeight(), null);
            return PAGE_EXISTS;
        }
    });

    if (job.printDialog()) {
        try {
            job.print();
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }
}

    public void calculate(){
        int rate = Integer.parseInt(txt_rate.getText());
        int hours = Integer.parseInt(txt_hours.getText());
        int deductions = Integer.parseInt(txt_deductions.getText());
        
    int grossPay = rate * hours;

    int netPay = grossPay - deductions;

    txt_gross.setText(String.valueOf(grossPay));
    txt_net.setText(String.valueOf(netPay));
    txt_totals.setText(String.valueOf(netPay));
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_branch = new app.bolivia.swing.JCTextField();
        txt_level = new app.bolivia.swing.JCTextField();
        txt_rate = new app.bolivia.swing.JCTextField();
        txt_hours = new app.bolivia.swing.JCTextField();
        txt_totals = new app.bolivia.swing.JCTextField();
        txt_name = new app.bolivia.swing.JCTextField();
        txt_deductions = new app.bolivia.swing.JCTextField();
        txt_gross = new app.bolivia.swing.JCTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_net = new app.bolivia.swing.JCTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 16)); // NOI18N
        jLabel1.setText("Totals");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 480, 60, 30));

        jLabel2.setText("Net Pay");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 460, 80, 30));

        jLabel3.setText("Hourly Rate");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 80, 30));

        jLabel4.setText("Employee Level");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 90, 30));

        jLabel5.setText("Employee Branch");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 100, 30));

        jLabel6.setText("Employee name");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 90, 30));

        txt_branch.setBackground(new java.awt.Color(0, 204, 204));
        txt_branch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_branch.setForeground(new java.awt.Color(255, 255, 255));
        txt_branch.setPlaceholder("employee branch");
        txt_branch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_branchActionPerformed(evt);
            }
        });
        getContentPane().add(txt_branch, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, -1, -1));

        txt_level.setBackground(new java.awt.Color(0, 204, 204));
        txt_level.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_level.setForeground(new java.awt.Color(255, 255, 255));
        txt_level.setPlaceholder("employee level");
        txt_level.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_levelActionPerformed(evt);
            }
        });
        getContentPane().add(txt_level, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, -1, -1));

        txt_rate.setBackground(new java.awt.Color(0, 204, 204));
        txt_rate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_rate.setForeground(new java.awt.Color(255, 255, 255));
        txt_rate.setPlaceholder("hourly rate");
        txt_rate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rateActionPerformed(evt);
            }
        });
        getContentPane().add(txt_rate, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, -1, -1));

        txt_hours.setBackground(new java.awt.Color(0, 204, 204));
        txt_hours.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_hours.setForeground(new java.awt.Color(255, 255, 255));
        txt_hours.setPlaceholder("work hours");
        txt_hours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hoursActionPerformed(evt);
            }
        });
        getContentPane().add(txt_hours, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 260, -1, -1));

        txt_totals.setEditable(false);
        txt_totals.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_totals.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_totals.setPlaceholder("Totals");
        txt_totals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalsActionPerformed(evt);
            }
        });
        getContentPane().add(txt_totals, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 470, -1, 50));

        txt_name.setBackground(new java.awt.Color(0, 204, 204));
        txt_name.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_name.setForeground(new java.awt.Color(255, 255, 255));
        txt_name.setPlaceholder("employee name");
        txt_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nameActionPerformed(evt);
            }
        });
        getContentPane().add(txt_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, -1, -1));

        txt_deductions.setBackground(new java.awt.Color(0, 204, 204));
        txt_deductions.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_deductions.setForeground(new java.awt.Color(255, 255, 255));
        txt_deductions.setPlaceholder("deductions");
        txt_deductions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_deductionsActionPerformed(evt);
            }
        });
        getContentPane().add(txt_deductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 310, -1, -1));

        txt_gross.setEditable(false);
        txt_gross.setBackground(new java.awt.Color(0, 204, 204));
        txt_gross.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_gross.setForeground(new java.awt.Color(255, 255, 255));
        txt_gross.setPlaceholder("gross pay");
        txt_gross.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_grossActionPerformed(evt);
            }
        });
        getContentPane().add(txt_gross, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 420, -1, -1));

        jButton1.setText("print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 520, -1, -1));

        jButton2.setText("calculate");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 520, -1, -1));

        jButton3.setText("save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 520, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setToolTipText("");
        jPanel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.setName(""); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 430, 350));

        jLabel7.setText("Work Hours ");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 80, 30));

        jLabel8.setText("Deductions ");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 80, 30));

        jLabel9.setText("Gross Pay");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 420, 80, 30));

        txt_net.setEditable(false);
        txt_net.setBackground(new java.awt.Color(0, 204, 204));
        txt_net.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_net.setForeground(new java.awt.Color(255, 255, 255));
        txt_net.setPlaceholder("net pay");
        txt_net.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_netActionPerformed(evt);
            }
        });
        getContentPane().add(txt_net, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 460, -1, -1));

        setSize(new java.awt.Dimension(999, 629));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_branchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_branchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_branchActionPerformed

    private void txt_levelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_levelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_levelActionPerformed

    private void txt_rateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rateActionPerformed

    private void txt_hoursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hoursActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hoursActionPerformed

    private void txt_totalsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalsActionPerformed

    private void txt_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nameActionPerformed

    private void txt_deductionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_deductionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_deductionsActionPerformed

    private void txt_grossActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_grossActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_grossActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!txt_net.getText().isEmpty()){
        printDetails();
        } else{
            JOptionPane.showMessageDialog(this, "Unprintable empty fields!");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(!txt_name.getText().isEmpty()&&!txt_level.getText().isEmpty()&&!txt_rate.getText().isEmpty()&&!txt_hours.getText().isEmpty()&&!txt_deductions.getText().isEmpty()){
        calculate();
        } else{
            JOptionPane.showMessageDialog(this, "Please fill in the fields");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {

        if (!txt_net.getText().isEmpty()) {
            displayDetails();
            getPayRoll();
            JOptionPane.showMessageDialog(this, "Successfully Saved");
        } else {
            JOptionPane.showMessageDialog(this, "Please calculate to save");
        }
    } catch (BadLocationException e) {
        e.printStackTrace();
    } 
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txt_netActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_netActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_netActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Payroll().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private app.bolivia.swing.JCTextField txt_branch;
    private app.bolivia.swing.JCTextField txt_deductions;
    private app.bolivia.swing.JCTextField txt_gross;
    private app.bolivia.swing.JCTextField txt_hours;
    private app.bolivia.swing.JCTextField txt_level;
    private app.bolivia.swing.JCTextField txt_name;
    private app.bolivia.swing.JCTextField txt_net;
    private app.bolivia.swing.JCTextField txt_rate;
    private app.bolivia.swing.JCTextField txt_totals;
    // End of variables declaration//GEN-END:variables
}
