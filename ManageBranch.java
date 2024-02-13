
import java.awt.Component;
import java.awt.Window;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.DatabaseMetaData;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public class ManageBranch extends javax.swing.JFrame {

    /**
     * Creates new form addBranch
     */
    public ManageBranch() {
        initComponents();
        setBranchDetailsToTable();
    }
    
    int branchId;
    String branchName;
    DefaultTableModel model;
    
    public void addNewBranch() {
    try {
        Connection con = DBConnection.getConnection();
        int branchId = Integer.parseInt(tx_branchId.getText());
        String branchName = tx_branchName.getText();

        // Check if branch with the same name and ID already exists
        String sql = "SELECT * FROM branchdetails WHERE branchId = ? OR branchName = ?";
        try (PreparedStatement pstCheck = con.prepareStatement(sql)) {
            pstCheck.setInt(1, branchId);
            pstCheck.setString(2, branchName);
            ResultSet rs = pstCheck.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Branch with the same ID already exists.");
            } else {
                // If no existing branch found, add the new branch
                String insertQuery = "INSERT INTO branchdetails (branchId, branchName) VALUES (?, ?)";
                try (PreparedStatement pst = con.prepareStatement(insertQuery)) {
                    pst.setInt(1, branchId);
                    pst.setString(2, branchName);
                    int rowCount = pst.executeUpdate();

                    if (rowCount > 0) {
                        JOptionPane.showMessageDialog(this, "New branch added.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add branch.");
                    }
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public boolean deleteBranch(){
        boolean isDeleted = false;
        branchId = Integer.parseInt(tx_branchId.getText());
        branchName = tx_branchName.getText();
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "delete from branchdetails where branchId = ?";
            String sql1 = "DROP TABLE IF EXISTS " + branchName;
            PreparedStatement pst = con.prepareStatement(sql);
            PreparedStatement pst1 = con.prepareStatement(sql1);
            pst.setInt(1, branchId);
            pst1.executeUpdate();
            
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isDeleted = true;
            }else {
                isDeleted = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isDeleted;   
    }
    public void setBranchDetailsToTable(){
        
        try{
            Connection con = DBConnection.getConnection();
            java.sql.Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from branchdetails");
            
            while (rs.next()){
            String branchId = rs.getString("branchId");
            String branchName = rs.getString("branchName");
            
            Object[] obj = {branchId, branchName};
            model = (DefaultTableModel)branchtable.getModel();
            model.addRow(obj);
       
            }
            
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void clearTable(){
        DefaultTableModel model = (DefaultTableModel) branchtable.getModel();
        model.setRowCount(0);
    }
    public void createBranch(){
        String branchName = tx_branchName.getText();
        
        try{
            Connection con = DBConnection.getConnection();
            // Check if the table for branch already exists.
        DatabaseMetaData metaData = con.getMetaData();
        ResultSet tables = metaData.getTables(null, null, branchName, null);
        if(!tables.next()){
            String sql = "CREATE TABLE " + branchName + " (drugId INT PRIMARY KEY, drugName VARCHAR(255), category VARCHAR(255), drugQuantity INT)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.executeUpdate();
        }else{
            JOptionPane.showMessageDialog(this, "branch " +branchName+ " already exists");
        }
            
          
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tx_branchName = new app.bolivia.swing.JCTextField();
        tx_branchId = new app.bolivia.swing.JCTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        branchtable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Enter Branch Name");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 110, 30));

        jLabel2.setText("Add New Branch");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 90, 30));

        tx_branchName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        tx_branchName.setPlaceholder("Enter Branch Name");
        tx_branchName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tx_branchNameFocusLost(evt);
            }
        });
        getContentPane().add(tx_branchName, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, -1, -1));

        tx_branchId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        tx_branchId.setPlaceholder("Enter Branch ID");
        tx_branchId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tx_branchIdFocusLost(evt);
            }
        });
        tx_branchId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_branchIdActionPerformed(evt);
            }
        });
        getContentPane().add(tx_branchId, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, -1, 30));

        jPanel2.setBackground(new java.awt.Color(255, 0, 0));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 51, 51));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Delete Branch");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 320, -1, 30));

        jLabel7.setBackground(new java.awt.Color(153, 255, 153));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Rewind_48px.png"))); // NOI18N
        jLabel7.setText("Back");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        branchtable.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        branchtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "branchId", "branchName"
            }
        ));
        branchtable.setGridColor(new java.awt.Color(0, 204, 204));
        branchtable.setRowHeight(30);
        branchtable.setShowGrid(true);
        branchtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                branchtableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(branchtable);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 620, 290));

        jPanel3.setBackground(new java.awt.Color(153, 255, 153));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(0, 51, 51));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel6.setText("Refresh");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 40, 30));

        jLabel5.setText("Enter Branch ID");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 90, 30));

        jPanel4.setBackground(new java.awt.Color(153, 255, 153));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(0, 51, 51));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Add Branch");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, -1, 30));

        setSize(new java.awt.Dimension(1075, 433));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tx_branchNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tx_branchNameFocusLost

    }//GEN-LAST:event_tx_branchNameFocusLost

    private void tx_branchIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tx_branchIdFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_branchIdFocusLost

    private void tx_branchIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_branchIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_branchIdActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
    int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure to delete the branch?", "Confirmation", JOptionPane.YES_NO_OPTION);

    if (confirmation == JOptionPane.YES_OPTION) {
        if (deleteBranch()) {
            clearTable();
            setBranchDetailsToTable();
            JOptionPane.showMessageDialog(this, "Branch deleted successfully");
        } else {
            JOptionPane.showMessageDialog(this, "Deletion failure!");
        }
    }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
      
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        Window window = SwingUtilities.windowForComponent((Component)evt.getSource());
        if (window instanceof JFrame) {
            ((JFrame) window).dispose();
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void branchtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_branchtableMouseClicked
        int rowNo = branchtable.getSelectedRow();
        TableModel model = branchtable.getModel();
        
        tx_branchId.setText(model.getValueAt(rowNo, 0).toString());
        tx_branchName.setText(model.getValueAt(rowNo,1).toString());
    }//GEN-LAST:event_branchtableMouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if (!tx_branchName.getText().equals("") &&(!tx_branchId.getText().equals(""))){
            addNewBranch();
            createBranch();
        }else{
            JOptionPane.showMessageDialog(this, "please used a valid id and name");

        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        ManageBranch branch = new ManageBranch();
        branch.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

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
            java.util.logging.Logger.getLogger(ManageBranch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageBranch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageBranch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageBranch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageBranch().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable branchtable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private app.bolivia.swing.JCTextField tx_branchId;
    private app.bolivia.swing.JCTextField tx_branchName;
    // End of variables declaration//GEN-END:variables
}
