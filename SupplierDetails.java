
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.Connection;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public class SupplierDetails extends javax.swing.JFrame {

    /**
     * Creates new form SupplierDetails
     */
    public SupplierDetails() {
        initComponents();
    }

    public void supplyDrugs(){
        String drugName = txt_drugName.getText();
        String category = txt_category.getText();
        int quantity = Integer.parseInt(txt_Quantity.getText());
        String supplier = txt_supplierName.getText();
        Date entryDate = date_entryDate.getDate();
        
        //covert date into long value
        long l1 = entryDate.getTime();
        
        //covert java.util date to java.sql date
        java.sql.Date sqlEntryDate = new java.sql.Date(l1);
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "insert into supplierDetails (drug, category, quantity, supplierName, date) values (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, drugName);
            pst.setString(2, category);
            pst.setInt(3, quantity);
            pst.setString(4, supplier);
            pst.setDate(5, sqlEntryDate);
            pst.executeUpdate();
                      
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void updateCount(){
    int quantity = Integer.parseInt(txt_Quantity.getText());
    String drugName = txt_drugName.getText();
    
    try{
        Connection con = DBConnection.getConnection();
        String sql = "UPDATE drugdetails SET quantity = quantity + ? WHERE drugName = ? ;";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, quantity);
        pst.setString(2, drugName);
        pst.executeUpdate();
        
    }catch(Exception e){
        e.printStackTrace();
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_drugName = new app.bolivia.swing.JCTextField();
        txt_category = new app.bolivia.swing.JCTextField();
        date_entryDate = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_Quantity = new app.bolivia.swing.JCTextField();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txt_supplierName = new app.bolivia.swing.JCTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        jLabel14.setText("Drug Entry");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, 170, 30));

        jLabel7.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 153));
        jLabel7.setText("Drug name");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 110, 20));

        txt_drugName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_drugName.setPlaceholder("Enter Drug Name");
        txt_drugName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_drugNameFocusLost(evt);
            }
        });
        jPanel3.add(txt_drugName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        txt_category.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_category.setPlaceholder("Category");
        txt_category.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_categoryFocusLost(evt);
            }
        });
        jPanel3.add(txt_category, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, -1, -1));
        jPanel3.add(date_entryDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 210, 150, 40));

        jLabel9.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 153));
        jLabel9.setText("Category");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 110, 20));

        jLabel10.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 153));
        jLabel10.setText("Entry Date:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, 120, -1));

        jLabel18.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 153, 153));
        jLabel18.setText("Quantity");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 110, 20));

        txt_Quantity.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_Quantity.setPlaceholder("Enter quantity");
        txt_Quantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_QuantityFocusLost(evt);
            }
        });
        jPanel3.add(txt_Quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        jButton4.setBackground(new java.awt.Color(0, 153, 153));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton4.setText("Add Supply");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 390, 160, -1));

        jLabel8.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 153));
        jLabel8.setText("Supplier name");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 110, 20));

        txt_supplierName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_supplierName.setPlaceholder("Enter Supplier Name");
        txt_supplierName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_supplierNameFocusLost(evt);
            }
        });
        jPanel3.add(txt_supplierName, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 740, 480));

        setSize(new java.awt.Dimension(1001, 561));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_drugNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_drugNameFocusLost
     
    }//GEN-LAST:event_txt_drugNameFocusLost

    private void txt_categoryFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_categoryFocusLost

    }//GEN-LAST:event_txt_categoryFocusLost

    private void txt_QuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_QuantityFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_QuantityFocusLost

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (!txt_drugName.getText().equals("")&& (!txt_category.getText().equals(""))&&(!txt_Quantity.getText().equals(""))&&(date_entryDate.getDate() != null)&&(!txt_supplierName.getText().equals(""))){
            supplyDrugs();
            updateCount();
            JOptionPane.showMessageDialog(this, "supply added Successfully");
        }else{
            JOptionPane.showMessageDialog(this, "Error adding supply!");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txt_supplierNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_supplierNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_supplierNameFocusLost

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
            java.util.logging.Logger.getLogger(SupplierDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SupplierDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SupplierDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SupplierDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SupplierDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date_entryDate;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private app.bolivia.swing.JCTextField txt_Quantity;
    private app.bolivia.swing.JCTextField txt_category;
    private app.bolivia.swing.JCTextField txt_drugName;
    private app.bolivia.swing.JCTextField txt_supplierName;
    // End of variables declaration//GEN-END:variables
}
