
import java.sql.PreparedStatement;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public class EmployeeManager extends javax.swing.JFrame {

    /**
     * Creates new form EmployeeManager
     */
    public EmployeeManager() {
        initComponents();
        setEmployeeDetailsToTable();
    }
    
    String employeeName;
    String branch;
    String level;
    int employee_Id;
    DefaultTableModel model;
    
    
    //put drug details inside the table
    public void setEmployeeDetailsToTable(){
        
        try{
            Connection con = DBConnection.getConnection();
            java.sql.Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from employeedetails");
            
            while (rs.next()){
            String employee_Id = rs.getString("employee_id");
            String employeeName = rs.getString("Name");
            String level = rs.getString ("level");
            String branch = rs.getString ("branch");
            
            Object[] obj = {employee_Id, employeeName, level, branch};
            model = (DefaultTableModel)managetable.getModel();
            model.addRow(obj);
       
            }
            
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public boolean addEmployees(){
        boolean isAdded = false;
        employee_Id = Integer.parseInt(txt_employeeId.getText());
        employeeName = txt_employeeName.getText();
        branch = txt_branchName.getSelectedItem().toString();
        level = txt_levelName.getSelectedItem().toString();
        
        
        
        try{
                Connection con = DBConnection.getConnection();
                String sql = "insert into employeedetails values(?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                
                pst.setInt(1, employee_Id);
                pst.setString(2, employeeName);
                pst.setString(3, branch);
                pst.setString(4, level);
                
                int rowCount = pst.executeUpdate();
                if (rowCount > 0) {
                    isAdded = true;
                }else {
                    isAdded = false;
                }
        }catch(Exception e){
            e.printStackTrace();
            }
        return isAdded;
    
    }
    
    public void clearTable(){
        DefaultTableModel model = (DefaultTableModel) managetable.getModel();
        model.setRowCount(0);
    }
    
    //method too update drugs
    public boolean updateEmployees(){
        boolean isUpdated = false;
        employee_Id = Integer.parseInt(txt_employeeId.getText());
        employeeName = txt_employeeName.getText();
        branch = txt_branchName.getSelectedItem().toString();
        level = txt_levelName.getSelectedItem().toString();
        
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "update employeedetails set Name = ?, level = ?, branch = ? where employee_Id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, employeeName);
            pst.setString(2, branch);
            pst.setString(3, level);
            pst.setInt(4, employee_Id);
            
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                isUpdated = true;
            }else {
                isUpdated = false;
            }
        
    }catch (Exception e){
        e.printStackTrace();    
    }
    return isUpdated;
    }
    //deleting drugs 
    public boolean removeEmployees(){
        boolean isDeleted = false;
        employee_Id = Integer.parseInt(txt_employeeId.getText());
        
        try{
            Connection con = DBConnection.getConnection();
            String sql = "delete from employeedetails where employee_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, employee_Id);
            
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
    public boolean searchEmployee() {
    boolean isDisplay = false;
        String searchText = txt_employeeSearch.getText();
        
        try {
        employee_Id = Integer.parseInt(searchText);
        // If search succeeds, it means the user entered an employee ID.
        // search only by employee ID.
        employeeName = "";
        branch = "";
        level = "";
    } catch (NumberFormatException e) {
        // search brings an error, so it's not an employee ID, it is name, branch, or level.
        employee_Id = 0;
        employeeName = searchText;
        branch = searchText;
        level = searchText;
    }
    try {
        Connection con = DBConnection.getConnection();
        String sql = "select * from employeedetails where employee_id = ? OR name = ? OR branch = ? OR level = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, employee_Id);
        pst.setString(2, employeeName);
        pst.setString(3, branch);
        pst.setString(4, level);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            String employee_Id = rs.getString("employee_id");
            String employeeName = rs.getString("name");
            String branch = rs.getString("branch");
            String level = rs.getString("level");

            Object[] obj = {employee_Id, employeeName, branch, level};
            model = (DefaultTableModel) managetable.getModel();
            model.addRow(obj);
        }

        isDisplay = true;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return isDisplay;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_employeeName = new app.bolivia.swing.JCTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_employeeId = new app.bolivia.swing.JCTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txt_levelName = new javax.swing.JComboBox<>();
        txt_branchName = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        managetable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_employeeSearch = new app.bolivia.swing.JCTextField();
        jLabel22 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
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

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 90, 40));

        jLabel17.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/quantity.png"))); // NOI18N
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, 100, 60));

        jLabel15.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Choose Level");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 520, 130, 20));

        txt_employeeName.setBackground(new java.awt.Color(0, 153, 153));
        txt_employeeName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_employeeName.setForeground(new java.awt.Color(255, 255, 255));
        txt_employeeName.setPlaceholder("Enter Employee Name");
        txt_employeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_employeeNameActionPerformed(evt);
            }
        });
        jPanel1.add(txt_employeeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, -1, -1));

        jLabel9.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/nameid.png"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 80, 50));

        jLabel4.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Employee name");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 280, 130, 20));

        jLabel16.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Employee Id");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 100, 20));

        jLabel18.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/id.png"))); // NOI18N
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 60, 60));

        txt_employeeId.setBackground(new java.awt.Color(0, 153, 153));
        txt_employeeId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_employeeId.setForeground(new java.awt.Color(255, 255, 255));
        txt_employeeId.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_employeeId.setPlaceholder("Enter Employee ID");
        txt_employeeId.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_employeeIdFocusLost(evt);
            }
        });
        txt_employeeId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_employeeIdActionPerformed(evt);
            }
        });
        jPanel1.add(txt_employeeId, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, -1, -1));

        jLabel19.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Choose Branch");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 120, 20));

        jLabel20.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/category.png"))); // NOI18N
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, 60, 60));

        jButton1.setBackground(new java.awt.Color(51, 51, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton1.setText("Update");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(51, 51, 51), new java.awt.Color(102, 0, 102), new java.awt.Color(0, 102, 102)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 620, 150, -1));

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 680, 140, -1));

        jButton3.setBackground(new java.awt.Color(0, 153, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton3.setText("Add");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 620, 150, -1));

        txt_levelName.setBackground(new java.awt.Color(0, 153, 153));
        txt_levelName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entry-level", "Supervisory", "Executive", "Top management", "Middle Management", "Branch Manager", "HR", "Administrative", " ", " ", " " }));
        txt_levelName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_levelNameActionPerformed(evt);
            }
        });
        jPanel1.add(txt_levelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 550, 150, 40));

        txt_branchName.setBackground(new java.awt.Color(0, 153, 153));
        txt_branchName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Westlands", "CBD", "Chuka", "Kilimani", "Buruburu", "Karen", " " }));
        jPanel1.add(txt_branchName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 440, 150, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 750));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel4MouseMoved(evt);
            }
        });
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        jLabel2.setText("X");
        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel2MouseMoved(evt);
            }
        });
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 20, 20));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 0, -1, 30));

        managetable.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        managetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee_id", "EmployeeName", "Branch", "Level"
            }
        ));
        managetable.setGridColor(new java.awt.Color(0, 204, 204));
        managetable.setRowHeight(30);
        managetable.setShowGrid(true);
        managetable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                managetableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(managetable);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 1010, 260));

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel3.setText("Manage Employee Records");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 320, 30));

        jLabel6.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel6.setText("Search");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 60, 30));

        txt_employeeSearch.setBackground(new java.awt.Color(204, 204, 204));
        txt_employeeSearch.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 0, 153)));
        txt_employeeSearch.setPlaceholder("Search Employee (Id, name, branch, level)");
        jPanel3.add(txt_employeeSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, 240, -1));

        jLabel22.setFont(new java.awt.Font("Perpetua", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/search.png"))); // NOI18N
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 110, 30, 30));

        jButton4.setBackground(new java.awt.Color(204, 204, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton4.setText("view All");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 100, 30));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 1020, 750));

        setSize(new java.awt.Dimension(1353, 748));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        Admin admin = new Admin();
        admin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void txt_employeeIdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_employeeIdFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeIdFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (updateEmployees()== true){
        JOptionPane.showMessageDialog(this, "Employee update Successful");
        clearTable();
        setEmployeeDetailsToTable();
        }
        else{
             JOptionPane.showMessageDialog(this, "Update failure!");
                        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         if (removeEmployees()== true){
        JOptionPane.showMessageDialog(this, "One employee removed");
        clearTable();
        setEmployeeDetailsToTable();
        }
        else{
             JOptionPane.showMessageDialog(this, "Remove failure!");
                        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (addEmployees()== true){
        JOptionPane.showMessageDialog(this, "New employee added");
        clearTable();
        setEmployeeDetailsToTable();
        }
        else{
             JOptionPane.showMessageDialog(this, "addition failure!");
                        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
       System.exit(0);
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel4MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseMoved
        jPanel4.setBackground(Color.RED);
    }//GEN-LAST:event_jPanel4MouseMoved

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        jPanel4.setBackground(Color.white);
    }//GEN-LAST:event_jPanel4MouseExited

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void managetableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_managetableMouseClicked
        
        int rowNo = managetable.getSelectedRow();
        TableModel model = managetable.getModel();
        
        txt_employeeId.setText(model.getValueAt(rowNo, 0).toString());
        txt_employeeName.setText(model.getValueAt(rowNo,1).toString());
        txt_levelName.setSelectedItem(model.getValueAt(rowNo, 2).toString());
        txt_branchName.setSelectedItem(model.getValueAt(rowNo, 3).toString());

    }//GEN-LAST:event_managetableMouseClicked

    private void jLabel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseMoved
        jLabel2.setBackground(Color.RED);
    }//GEN-LAST:event_jLabel2MouseMoved

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        jLabel2.setBackground(Color.RED);
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        jLabel2.setBackground(Color.RED);
    }//GEN-LAST:event_jLabel2MouseExited

    private void txt_levelNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_levelNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_levelNameActionPerformed

    private void txt_employeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_employeeNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeNameActionPerformed

    private void txt_employeeIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_employeeIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_employeeIdActionPerformed

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        clearTable();
        searchEmployee();
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        clearTable();
        setEmployeeDetailsToTable();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(DrugManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DrugManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DrugManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DrugManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DrugManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable managetable;
    private javax.swing.JComboBox<String> txt_branchName;
    private app.bolivia.swing.JCTextField txt_employeeId;
    private app.bolivia.swing.JCTextField txt_employeeName;
    private app.bolivia.swing.JCTextField txt_employeeSearch;
    private javax.swing.JComboBox<String> txt_levelName;
    // End of variables declaration//GEN-END:variables
}
