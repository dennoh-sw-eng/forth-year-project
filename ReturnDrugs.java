
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public class ReturnDrugs extends javax.swing.JFrame {

    /**
     * Creates new form ReturnDrugs
     */
    public ReturnDrugs() {
        initComponents();
    }
    
    //fetching drug details from the database to display on labels
    public void getDrugDetails(){
        String drugName = txt_drugName.getText();
        
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from drugdetails where drugName = ?");
            pst.setString(1, drugName);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()){
                lb_DrugId.setText(rs.getString("drugId"));
                lb_DrugName.setText(rs.getString("drugName"));
                lb_category.setText(rs.getString("category"));
                lb_quantity.setText(rs.getString("quantity"));
            }else{
                txt_drugError.setText("Drug Not Found!");
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //fetching branch details
    public void getBranchDetails(){
        String branchName = txt_BranchName.getText();
        
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from branchdetails where branchName = ?");
            pst.setString(1, branchName);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()){
                lb_BranchId.setText(rs.getString("branchId"));
                lb_BranchName.setText(rs.getString("branchName"));
            }else{
                txt_branchError.setText("invalid Branch Name!");
            }
            
            // Fetching drug details from the specific branch table
        PreparedStatement pstDrug = con.prepareStatement("select * from " + branchName + " where drugName = ?");
        pstDrug.setString(1, txt_drugName.getText());
        ResultSet rsDrug = pstDrug.executeQuery();

        if (rsDrug.next()) {
            lb_BrDrugName.setText(rsDrug.getString("drugName"));
            lb_BrDrugQuantity.setText(rsDrug.getString("drugQuantity"));
        }else{
                txt_branchError.setText("drug not found in " +branchName+ " branch");
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    // Method to dispatch drugs to a branch
    public void updateQuantity() {
    try {
        Connection con = DBConnection.getConnection();
        String branchName = lb_BranchName.getText();
        String drugName = lb_BrDrugName.getText();
        int dispatchQuantity = Integer.parseInt(txt_DispatchQuantity.getText());


        // Update drug quantity in the specific branch table
        String updateBranchQuery = "UPDATE " + branchName + " SET drugQuantity = drugQuantity - ? WHERE drugName = ?";
        try (PreparedStatement pstUpdateBranch = con.prepareStatement(updateBranchQuery)) {
            pstUpdateBranch.setInt(1, dispatchQuantity);
            pstUpdateBranch.setString(2, drugName);
            pstUpdateBranch.executeUpdate();
        }

        // Update drug quantity in the drugDetails table
        String updateDrugDetailsQuery = "UPDATE drugDetails SET quantity = quantity + ? WHERE drugName = ?";
        try (PreparedStatement pstUpdateDrugDetails = con.prepareStatement(updateDrugDetailsQuery)) {
            pstUpdateDrugDetails.setInt(1, dispatchQuantity);
            pstUpdateDrugDetails.setString(2, drugName);
            pstUpdateDrugDetails.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, dispatchQuantity + " pieces of " + drugName + " removed from " + branchName);

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    
    public boolean returnDrugs (){
        boolean isReturned = false;
        
        int drugId = Integer.parseInt(lb_DrugId.getText());
        String drugName = txt_drugName.getText();
        String branchName = lb_BranchName.getText();
        int branchId = Integer.parseInt(lb_BranchId.getText());
        int quantity = Integer.parseInt(txt_DispatchQuantity.getText());

        
        Date uReturnDate = date_dispatchDate.getDate();        
        //covert date into long value
        long l1 = uReturnDate.getTime();
        
        //covert java.util date to java.sql date
        java.sql.Date sqlDispatchDate = new java.sql.Date(l1);
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "insert into returndrugs(drugId, drugName, branchId, branchName, quantity, returnDate, status) values(?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, drugId);
            pst.setString(2, drugName);
            pst.setInt(3, branchId);
            pst.setString(4, branchName);
            pst.setInt (5, quantity);
            pst.setDate(6, sqlDispatchDate);
            pst.setString(7, "Returned");
            
            int rowCount = pst.executeUpdate();
            if(rowCount > 0){
                isReturned = true;
            }else{
                isReturned = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isReturned;
        
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lb_quantity = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lb_DrugId = new javax.swing.JLabel();
        lb_category = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lb_DrugName = new javax.swing.JLabel();
        txt_drugError = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lb_BranchId = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lb_BranchName = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lb_BrDrugName = new javax.swing.JLabel();
        lb_BrDrugQuantity = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_branchError = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_drugName = new app.bolivia.swing.JCTextField();
        txt_BranchName = new app.bolivia.swing.JCTextField();
        date_dispatchDate = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_DispatchQuantity = new app.bolivia.swing.JCTextField();
        rSMaterialButtonCircle1 = new rojerusan.RSMaterialButtonCircle();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(153, 0, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Rewind_48px.png"))); // NOI18N
        jLabel1.setText("Back");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lb_quantity.setBackground(new java.awt.Color(255, 255, 255));
        lb_quantity.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_quantity.setText("quantity");
        jPanel2.add(lb_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 370, 140, 30));

        jLabel3.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        jLabel3.setText("Drug Details");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 150, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Drug Name: ");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 110, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Drug Category: ");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 140, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Drug Quantity: ");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 140, 30));

        lb_DrugId.setBackground(new java.awt.Color(255, 255, 255));
        lb_DrugId.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_DrugId.setText("id");
        jPanel2.add(lb_DrugId, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 140, 30));

        lb_category.setBackground(new java.awt.Color(255, 255, 255));
        lb_category.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_category.setText("Dr category");
        jPanel2.add(lb_category, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 140, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Drug Id: ");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 90, 30));

        lb_DrugName.setBackground(new java.awt.Color(255, 255, 255));
        lb_DrugName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_DrugName.setText("Dr name");
        jPanel2.add(lb_DrugName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 140, 30));

        txt_drugError.setEditable(false);
        txt_drugError.setBackground(new java.awt.Color(255, 153, 255));
        txt_drugError.setForeground(new java.awt.Color(255, 0, 0));
        txt_drugError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_drugErrorActionPerformed(evt);
            }
        });
        jPanel2.add(txt_drugError, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 310, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, 480));

        jPanel4.setBackground(new java.awt.Color(204, 0, 51));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        jLabel12.setText("Branch Details");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 170, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Branch Id: ");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 100, 30));

        lb_BranchId.setBackground(new java.awt.Color(255, 255, 255));
        lb_BranchId.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_BranchId.setText("id");
        jPanel4.add(lb_BranchId, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 140, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("Branch Name: ");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 130, 30));

        lb_BranchName.setBackground(new java.awt.Color(255, 255, 255));
        lb_BranchName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_BranchName.setText("Br name");
        jPanel4.add(lb_BranchName, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 230, 140, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setText("Drug Name: ");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 140, 30));

        lb_BrDrugName.setBackground(new java.awt.Color(255, 255, 255));
        lb_BrDrugName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_BrDrugName.setText("Dr name");
        jPanel4.add(lb_BrDrugName, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 140, 30));

        lb_BrDrugQuantity.setBackground(new java.awt.Color(255, 255, 255));
        lb_BrDrugQuantity.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_BrDrugQuantity.setText("quantity");
        jPanel4.add(lb_BrDrugQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 370, 140, 30));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setText("Drug Quantity: ");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 140, 30));

        txt_branchError.setEditable(false);
        txt_branchError.setBackground(new java.awt.Color(255, 153, 255));
        txt_branchError.setForeground(new java.awt.Color(255, 0, 0));
        txt_branchError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_branchErrorActionPerformed(evt);
            }
        });
        jPanel4.add(txt_branchError, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 310, 30));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 310, 480));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Sylfaen", 1, 24)); // NOI18N
        jLabel14.setText("Return Drug");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 170, 30));

        jLabel7.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 153));
        jLabel7.setText("Drug name");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 110, 20));

        txt_drugName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_drugName.setPlaceholder("Enter Drug Name");
        txt_drugName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_drugNameFocusLost(evt);
            }
        });
        jPanel3.add(txt_drugName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 120, -1, -1));

        txt_BranchName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_BranchName.setPlaceholder("Enter Branch Name");
        txt_BranchName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_BranchNameFocusLost(evt);
            }
        });
        jPanel3.add(txt_BranchName, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));
        jPanel3.add(date_dispatchDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 150, 40));

        jLabel9.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 153));
        jLabel9.setText("Branch name");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 110, 20));

        jLabel10.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 153));
        jLabel10.setText("Return Date:");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 120, 20));

        jLabel18.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 153, 153));
        jLabel18.setText("Quantity");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 110, 20));

        txt_DispatchQuantity.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_DispatchQuantity.setPlaceholder("Enter quantity");
        txt_DispatchQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_DispatchQuantityFocusLost(evt);
            }
        });
        jPanel3.add(txt_DispatchQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        rSMaterialButtonCircle1.setBackground(new java.awt.Color(0, 153, 153));
        rSMaterialButtonCircle1.setText("Return");
        rSMaterialButtonCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle1ActionPerformed(evt);
            }
        });
        jPanel3.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 150, 40));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, 350, 480));

        setSize(new java.awt.Dimension(1003, 509));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        welcomePage welcome = new welcomePage();
        welcome.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void txt_drugErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_drugErrorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_drugErrorActionPerformed

    private void txt_branchErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_branchErrorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_branchErrorActionPerformed

    private void txt_drugNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_drugNameFocusLost
        if (!txt_drugName.getText().equals("")){
            getDrugDetails();
        }
    }//GEN-LAST:event_txt_drugNameFocusLost

    private void txt_BranchNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_BranchNameFocusLost
        if (!txt_BranchName.getText().equals("")){
            getBranchDetails();
        }
    }//GEN-LAST:event_txt_BranchNameFocusLost

    private void txt_DispatchQuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_DispatchQuantityFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DispatchQuantityFocusLost

    private void rSMaterialButtonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1ActionPerformed

        if(lb_quantity.getText().equals("0")){
            JOptionPane.showMessageDialog(this, "Cannot return zero stock");

        }else{
            if (returnDrugs() == true){
                JOptionPane.showMessageDialog(this, "drugs returned");
            }else{
                JOptionPane.showMessageDialog(this, "return error");
            }
            updateQuantity();
        }

    }//GEN-LAST:event_rSMaterialButtonCircle1ActionPerformed

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
            java.util.logging.Logger.getLogger(ReturnDrugs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnDrugs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnDrugs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnDrugs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnDrugs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date_dispatchDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lb_BrDrugName;
    private javax.swing.JLabel lb_BrDrugQuantity;
    private javax.swing.JLabel lb_BranchId;
    private javax.swing.JLabel lb_BranchName;
    private javax.swing.JLabel lb_DrugId;
    private javax.swing.JLabel lb_DrugName;
    private javax.swing.JLabel lb_category;
    private javax.swing.JLabel lb_quantity;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle1;
    private app.bolivia.swing.JCTextField txt_BranchName;
    private app.bolivia.swing.JCTextField txt_DispatchQuantity;
    private javax.swing.JTextField txt_branchError;
    private javax.swing.JTextField txt_drugError;
    private app.bolivia.swing.JCTextField txt_drugName;
    // End of variables declaration//GEN-END:variables
}
