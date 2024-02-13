
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public class DispatchRecords extends javax.swing.JFrame {

    /**
     * Creates new form DispatchRecords
     */
    public DispatchRecords() {
        initComponents();
        setDispatchDetailsToTable();
    }
    int id;
    String name;
    String branch;
    String dispatch;
    String expiry;
    String statusT;
    int quantityQ;
    DefaultTableModel model;

    
    
    public void setDispatchDetailsToTable (){
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select id, name, branch, dispatch, expiry, statusT, quantityQ from dispatchdrugs";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
             
            while(rs.next()){
                String iD = rs.getString("id");
                String drugName = rs.getString("name");
                String branchName = rs.getString("branch");
                String dispatchDate = rs.getString("dispatch");
                String expiryDate = rs.getString("expiry");
                String status = rs.getString("statusT");
                int quantity = rs.getInt("quantityQ");
                
                Object[] obj = {iD, drugName, branchName, dispatchDate, expiryDate, status, quantity, quantity};
                model = (DefaultTableModel)dispatchRecordsTable.getModel();
                model.addRow(obj);
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void clearTable(){
        DefaultTableModel model = (DefaultTableModel) dispatchRecordsTable.getModel();
        model.setRowCount(0);
    }
    //method to search drug or branch
    public void searchTable(){
        String drugName = txt_Search.getText();
        String branchName = txt_Search.getText();
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select * from dispatchDrugs where name =? OR branch = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, drugName);
            pst.setString(2, branchName);
            
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String nameDrg = rs.getString("name");
                String branchNm = rs.getString("branch");
                int iD = rs.getInt("id");
                String dispatchDate = rs.getString("dispatch");
                String expiryDate = rs.getString("expiry");
                int quantity = rs.getInt("quantityQ");
                String status = rs.getString("statusT");

            Object[] obj = {iD, nameDrg, branchNm, dispatchDate, expiryDate, status, quantity};
            model = (DefaultTableModel) dispatchRecordsTable.getModel();
            model.addRow(obj);
                
            }
        }catch(Exception e){
                    e.printStackTrace();
                    }
    }
   public void searchDate() {
    Date dispatchStartDate = date_startDate1.getDate();
    Date dispatchEndDate = date_endDate1.getDate();

    // Converting date into long value
    long l1 = dispatchStartDate.getTime();
    long l2 = dispatchEndDate.getTime();

    // Convert java.util date to java.sql date
    java.sql.Date sqlDispatchStartDate = new java.sql.Date(l1);
    java.sql.Date sqlDispatchEndDate = new java.sql.Date(l2);

    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM dispatchdrugs WHERE dispatch BETWEEN ? AND ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setDate(1, sqlDispatchStartDate);  // start dispatch date
        pst.setDate(2, sqlDispatchEndDate);    // end dispatch date
 
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            String nameDrg = rs.getString("name");
            String branchNm = rs.getString("branch");
            int iD = rs.getInt("id");
            String dispatchD = rs.getString("dispatch");
            String expiryD = rs.getString("expiry");
            int quantity = rs.getInt("quantityQ");
            String status = rs.getString("statusT");

            Object[] obj = {iD, nameDrg, branchNm, dispatchD, expiryD, status, quantity};
            model = (DefaultTableModel) dispatchRecordsTable.getModel();
            model.addRow(obj);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
   public void searchDate2() {
    Date expiryStartDate = date_startDate.getDate();
    Date expiryEndDate = date_endDate.getDate();

    // Converting date into long value
    long l3 = expiryStartDate.getTime();
    long l4 = expiryEndDate.getTime();

    // Convert java.util date to java.sql date
    java.sql.Date sqlExpiryStartDate = new java.sql.Date(l3);
    java.sql.Date sqlExpiryEndDate = new java.sql.Date(l4);

    try {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM dispatchdrugs WHERE expiry BETWEEN ? AND ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setDate(1, sqlExpiryStartDate);    // start expiry date
        pst.setDate(2, sqlExpiryEndDate);      // end expiry date
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            String nameDrg = rs.getString("name");
            String branchNm = rs.getString("branch");
            int iD = rs.getInt("id");
            String dispatchD = rs.getString("dispatch");
            String expiryD = rs.getString("expiry");
            int quantity = rs.getInt("quantityQ");
            String status = rs.getString("statusT");

            Object[] obj = {iD, nameDrg, branchNm, dispatchD, expiryD, status, quantity};
            model = (DefaultTableModel) dispatchRecordsTable.getModel();
            model.addRow(obj);
        }
    } catch (Exception e) {
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

        txt_Search = new app.bolivia.swing.JCTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        dispatchRecordsTable = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        date_endDate = new com.toedter.calendar.JDateChooser();
        date_startDate = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        date_startDate1 = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        date_endDate1 = new com.toedter.calendar.JDateChooser();
        jLabel24 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_Search.setBackground(new java.awt.Color(204, 204, 204));
        txt_Search.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_Search.setPlaceholder("drug or branch");
        getContentPane().add(txt_Search, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 200, -1));

        jLabel22.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/search.png"))); // NOI18N
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 30, 30));

        dispatchRecordsTable.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        dispatchRecordsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Drug", "Branch", "Dispatch Date", "Expiry Date", "Status", "Quantity"
            }
        ));
        dispatchRecordsTable.setGridColor(new java.awt.Color(0, 204, 204));
        dispatchRecordsTable.setRowHeight(30);
        dispatchRecordsTable.setShowGrid(true);
        dispatchRecordsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispatchRecordsTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(dispatchRecordsTable);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 1350, 340));

        jButton4.setBackground(new java.awt.Color(204, 204, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton4.setText("view All");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 100, 30));

        jLabel7.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel7.setText("To");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 130, 20, 30));

        jLabel23.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/search.png"))); // NOI18N
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 130, 30, 30));

        jLabel8.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel8.setText("Expiry Date From");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 130, 160, 30));
        getContentPane().add(date_endDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 130, 150, 30));
        getContentPane().add(date_startDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 130, 150, 30));

        jLabel9.setFont(new java.awt.Font("Perpetua", 1, 36)); // NOI18N
        jLabel9.setText("Dispatch Records");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 290, 30));
        getContentPane().add(date_startDate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 70, 150, 30));

        jLabel10.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel10.setText("To");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 70, 20, 30));
        getContentPane().add(date_endDate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 70, 150, 30));

        jLabel24.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/search.png"))); // NOI18N
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 70, 30, 30));

        jLabel11.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel11.setText("Search");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 60, 30));

        jLabel12.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel12.setText("Dispatch Date From");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 70, 160, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AddNewBookIcons/AddNewBookIcons/icons8_Rewind_48px.png"))); // NOI18N
        jLabel1.setText("Back");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setSize(new java.awt.Dimension(1620, 769));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        clearTable();
        searchTable();
    }//GEN-LAST:event_jLabel22MouseClicked

    private void dispatchRecordsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dispatchRecordsTableMouseClicked

    }//GEN-LAST:event_dispatchRecordsTableMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        clearTable();
        setDispatchDetailsToTable();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        clearTable();
        searchDate2();
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        clearTable();
        searchDate();
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        welcomePage welcome = new welcomePage();
        welcome.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

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
            java.util.logging.Logger.getLogger(DispatchRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DispatchRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DispatchRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DispatchRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DispatchRecords().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date_endDate;
    private com.toedter.calendar.JDateChooser date_endDate1;
    private com.toedter.calendar.JDateChooser date_startDate;
    private com.toedter.calendar.JDateChooser date_startDate1;
    private javax.swing.JTable dispatchRecordsTable;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private app.bolivia.swing.JCTextField txt_Search;
    // End of variables declaration//GEN-END:variables
}
