
import com.mysql.jdbc.Statement;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public class welcomePage extends javax.swing.JFrame {

    /**
     * Creates new form welcomePage
     */
    Color mouseEnterColor = new Color(255,255,255); //// not working !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    Color mouseExitColor = new Color (255,255,255);
    
    public welcomePage() {
        initComponents();
        setDrugsToTable();
        setBranchDetailsToTable();
        setDataOnPanel();
        lowStock();
        startImageRotation();
    }
    
    DefaultTableModel model;
    
    private String username;

    public welcomePage(String username) {
        initComponents();
        this.username = username;
        jTextField1.setText(username);
                
        setGreetingMessage();//activate this method based on the time the username is received
    }
    private void setGreetingMessage() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;

        if (hour >= 0 && hour < 12) {
            greeting = "Good morning";
        } else if (hour >= 12 && hour < 16) {
            greeting = "Good afternoon";
        } else {
            greeting = "Good evening";
        }

        // Autofill jTextField2 with the greeting
        jTextField2.setText(greeting);
    }
    public void setDrugsToTable(){
        
        try{
            Connection con = DBConnection.getConnection();
            java.sql.Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from DrugDetails");
            
            while (rs.next()){
            String drugId = rs.getString("drugId");
            String drugName = rs.getString("drugName");
            String category = rs.getString ("category");
            String quantity = rs.getString ("quantity");
            
            Object[] obj = {drugId, drugName, category, quantity};
            model = (DefaultTableModel)drugDetailsTable.getModel();
            model.addRow(obj);
       
            }
            
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setBranchDetailsToTable() {
    try {
        Connection con = DBConnection.getConnection();
        java.sql.Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT drugName, category, drugQuantity, 'cbd' AS branch FROM cbd " +
                "UNION " +
                "SELECT drugName, category, drugQuantity, 'kilimani' AS branch FROM kilimani " +
                "UNION " +
                "SELECT drugName, category, drugQuantity, 'karen' AS branch FROM karen " +
                "UNION " +
                "SELECT drugName, category, drugQuantity, 'meru' AS branch FROM meru " +
                "UNION " +
                "SELECT drugName, category, drugQuantity, 'chuka' AS branch FROM chuka");

        while (rs.next()) {
            String drugId = rs.getString("drugName");
            String drugName = rs.getString("category");
            String category = rs.getString("branch");
            String quantity = rs.getString("drugQuantity");

            Object[] obj = {drugId, drugName, category, quantity};
            model = (DefaultTableModel)branchDetailsTable.getModel();
            model.addRow(obj);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void clearTable(){
        DefaultTableModel model = (DefaultTableModel) branchDetailsTable.getModel();
        model.setRowCount(0);
    }

    public void setDataOnPanel(){
        Statement st = null;
        ResultSet rs = null;
        
        long L1 = System.currentTimeMillis();
        Date today = new Date(L1);
        
        try{
            Connection con = DBConnection.getConnection();
            st= (Statement) con.createStatement();
            rs = st.executeQuery("select * from drugdetails");
            rs.last();
            drugRecordCard.setText(Integer.toString(rs.getRow()));
            
            rs = st.executeQuery("select * from employeedetails");
            rs.last();
            employeeCard.setText(Integer.toString(rs.getRow()));
            
            rs = st.executeQuery("select * from dispatchdrugs WHERE DATE(dispatch) = CURDATE()");
            rs.last();
            dispatchCard.setText(Integer.toString(rs.getRow()));
            
            rs = st.executeQuery("select * from supplierdetails WHERE DATE(date) = CURDATE()");
            rs.last();
            supplyCard.setText(Integer.toString(rs.getRow()));
            
            
            // Calculating short expiry (10 days from today)
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.add(java.util.Calendar.DAY_OF_MONTH, 10);
            java.sql.Date endDate = new java.sql.Date(cal.getTimeInMillis());

            String query = "SELECT * FROM drugdetails WHERE DATE(expiry_date) BETWEEN CURDATE() AND ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setDate(1, endDate);

            // Execueting query to get updated result set
            ResultSet rsExpiry = pst.executeQuery();

            rsExpiry.last();
            shortExpiryCard.setText(Integer.toString(rsExpiry.getRow()));
            

             // Getting all expired drugs
           java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
           
           String query1 = "SELECT * FROM drugdetails WHERE DATE(expiry_date) < ?";
           PreparedStatement pst1 = con.prepareStatement(query1);
           pst1.setDate(1, currentDate);

         
           ResultSet rs1 = pst1.executeQuery();
           rs1.last();
           int rowCount = rs1.getRow();
           expiredStockCard.setText(Integer.toString(rowCount));

            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void lowStock(){
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select drugName, quantity from drugdetails where quantity < 20";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                String name = rs.getString("drugName");
                int quantity = rs.getInt("quantity");
                
                Object[] obj = {name, quantity};
                model = (DefaultTableModel)lowStockTable.getModel();
                model.addRow(obj);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void startImageRotation() {
    // Create a timer to periodically update the rotation angle and rotate the image
    Timer timer = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Update rotation angle
            rotationAngle += Math.PI / 180; // Rotate by 1 degree (you can adjust the rotation speed as needed)

            // Rotate the image
            ImageIcon originalIcon = (ImageIcon) expiredStockCard.getIcon();
            Image originalImage = originalIcon.getImage();
            BufferedImage bufferedImage = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.rotate(rotationAngle, bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
            g2d.drawImage(originalImage, 0, 0, null);
            g2d.dispose();

            // Set the rotated image to all relevant components
            expiredStockCard.setIcon(new ImageIcon(bufferedImage));
            shortExpiryCard.setIcon(new ImageIcon(bufferedImage));
        }
    });
    
    // Start the timer
    timer.start();
}
  
    // Declare a class variable to store the rotation angle
double rotationAngle = 0;
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        no = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        supplyCard = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        drugRecordCard = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        employeeCard = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        dispatchCard = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        branchDetailsTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        lowStockTable = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        shortExpiryCard = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        expiredStockCard = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        drugDetailsTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        branchDetailsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_menu_48px_1.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Pharmer Manager");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("X");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 10, 20, 40));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(0, 204, 204));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField1.setBorder(null);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 20, 60, 30));

        jLabel20.setBackground(new java.awt.Color(0, 0, 0));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/male_user_50px.png"))); // NOI18N
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 10, -1, -1));

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(0, 204, 204));
        jTextField2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField2.setBorder(null);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 10, 90, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1530, 70));

        jPanel2.setBackground(new java.awt.Color(153, 255, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));
        jPanel3.setVerifyInputWhenFocusTarget(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 17, -1, -1));

        jLabel7.setBackground(new java.awt.Color(204, 255, 204));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/helmet (1).png"))); // NOI18N
        jLabel7.setText(" Manage Employees ");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 220, 40));

        jLabel9.setBackground(new java.awt.Color(204, 255, 204));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/pharmacy (1).png"))); // NOI18N
        jLabel9.setText("Create New Branch Drug");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 470, 240, 40));

        jLabel10.setBackground(new java.awt.Color(204, 51, 0));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/drugs (1).png"))); // NOI18N
        jLabel10.setText(" Manage Drugs");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
        });
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 220, -1));

        jLabel12.setBackground(new java.awt.Color(204, 255, 204));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/no-drugs (2).png"))); // NOI18N
        jLabel12.setText(" Return Drugs");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 220, 40));

        jLabel13.setBackground(new java.awt.Color(204, 255, 204));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/file (1).png"))); // NOI18N
        jLabel13.setText(" Branch Records");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 220, 30));

        jLabel17.setBackground(new java.awt.Color(204, 255, 204));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/fast-delivery (2).png"))); // NOI18N
        jLabel17.setText(" Dispatch Drugs");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 220, 30));

        jLabel18.setBackground(new java.awt.Color(204, 255, 204));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Sell_26px.png"))); // NOI18N
        jLabel18.setText("Dispatch Records");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, 180, 30));

        jLabel19.setBackground(new java.awt.Color(204, 255, 204));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Conference_26px.png"))); // NOI18N
        jLabel19.setText("Supplier Records");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 220, 30));

        jPanel4.setBackground(new java.awt.Color(255, 51, 51));

        jLabel11.setBackground(new java.awt.Color(0, 153, 153));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/icons8_Exit_26px_1.png"))); // NOI18N
        jLabel11.setText("LogOut");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel4.add(jLabel11);

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 560, 280, 40));

        jPanel5.setBackground(new java.awt.Color(51, 51, 255));

        no.setBackground(new java.awt.Color(204, 255, 204));
        no.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        no.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/service (2).png"))); // NOI18N
        no.setText("Pharmer Dashboard");
        no.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                noMouseClicked(evt);
            }
        });
        jPanel5.add(no);

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 320, -1));

        jPanel6.setBackground(new java.awt.Color(51, 51, 255));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Features");
        jPanel6.add(jLabel2);

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 110, 30));

        jPanel13.setBackground(new java.awt.Color(102, 102, 255));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("admin");
        jPanel13.add(jLabel28);

        jPanel2.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, 110, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 340, 3340));

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(0, 204, 204)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        supplyCard.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        supplyCard.setForeground(new java.awt.Color(0, 51, 51));
        supplyCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/defaulter.png"))); // NOI18N
        supplyCard.setText("0");
        jPanel8.add(supplyCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 100, 40));

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, 230, 140));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 51));
        jLabel8.setText("Dispatched today");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 180, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 51));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/refresh-.png"))); // NOI18N
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel7.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 460, 50, 40));

        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(0, 204, 204)));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        drugRecordCard.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        drugRecordCard.setForeground(new java.awt.Color(0, 51, 51));
        drugRecordCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/file (1).png"))); // NOI18N
        drugRecordCard.setText("0");
        jPanel9.add(drugRecordCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 100, 30));

        jPanel7.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 220, 140));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 51, 51));
        jLabel21.setText("Employees");
        jPanel7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 120, 30));

        jPanel10.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(0, 204, 204)));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        employeeCard.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        employeeCard.setForeground(new java.awt.Color(0, 51, 51));
        employeeCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/employees.png"))); // NOI18N
        employeeCard.setText("0");
        jPanel10.add(employeeCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 100, 30));

        jPanel7.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 230, 140));

        jPanel11.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(0, 204, 204)));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dispatchCard.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        dispatchCard.setForeground(new java.awt.Color(0, 51, 51));
        dispatchCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/dispatch drug.png"))); // NOI18N
        dispatchCard.setText("0");
        jPanel11.add(dispatchCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 100, 40));

        jPanel7.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 220, 140));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 51, 51));
        jLabel24.setText("Supplied today");
        jPanel7.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 10, 160, 30));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 51, 51));
        jLabel25.setText("Drug Record");
        jPanel7.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 120, 30));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 51, 51));
        jLabel26.setText("Low Stock drugs");
        jPanel7.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 370, 170, 30));

        jPanel12.setBackground(new java.awt.Color(102, 0, 102));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        branchDetailsTable1.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        branchDetailsTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Drug Name", "Category", "Branch", "Quantity"
            }
        ));
        branchDetailsTable1.setGridColor(new java.awt.Color(0, 204, 204));
        branchDetailsTable1.setRowHeight(30);
        branchDetailsTable1.setShowGrid(true);
        jScrollPane3.setViewportView(branchDetailsTable1);

        jPanel12.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 480, 150));

        lowStockTable.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        lowStockTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lowStockTable.setGridColor(new java.awt.Color(0, 204, 204));
        lowStockTable.setRowHeight(30);
        lowStockTable.setShowGrid(true);
        jScrollPane2.setViewportView(lowStockTable);

        jPanel12.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 450, 230));

        jPanel7.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 410, 450, 250));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 51, 51));
        jLabel27.setText("Drug details");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 120, 30));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 51, 51));
        jLabel29.setText("Branch details");
        jPanel7.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 150, 40));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 51, 51));
        jLabel30.setText("Short Expiry");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 200, 160, 30));

        jPanel14.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(0, 204, 204)));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        shortExpiryCard.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        shortExpiryCard.setForeground(new java.awt.Color(0, 51, 51));
        shortExpiryCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/defaulter.png"))); // NOI18N
        shortExpiryCard.setText("0");
        shortExpiryCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                shortExpiryCardMouseClicked(evt);
            }
        });
        jPanel14.add(shortExpiryCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 100, 40));

        jPanel16.setBackground(new java.awt.Color(255, 51, 51));
        jPanel14.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 190, 10));

        jPanel7.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 240, 190, 110));

        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(0, 204, 204)));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        expiredStockCard.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        expiredStockCard.setForeground(new java.awt.Color(0, 51, 51));
        expiredStockCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/adminIcons/adminIcons/defaulter.png"))); // NOI18N
        expiredStockCard.setText("0");
        expiredStockCard.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                expiredStockCardFocusGained(evt);
            }
        });
        expiredStockCard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expiredStockCardMouseClicked(evt);
            }
        });
        jPanel15.add(expiredStockCard, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 100, 40));

        jPanel17.setBackground(new java.awt.Color(255, 51, 51));
        jPanel15.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 20));

        jPanel7.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 240, 190, 110));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 51, 51));
        jLabel31.setText("Expired Stock");
        jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 200, 140, 30));

        drugDetailsTable.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        drugDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Drug ID", "Drug Name", "Category", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        drugDetailsTable.setGridColor(new java.awt.Color(0, 204, 204));
        drugDetailsTable.setRowHeight(30);
        drugDetailsTable.setShowGrid(true);
        jScrollPane4.setViewportView(drugDetailsTable);

        jPanel7.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 480, 190));

        branchDetailsTable.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        branchDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Drug Name", "Category", "Branch", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        branchDetailsTable.setGridColor(new java.awt.Color(0, 204, 204));
        branchDetailsTable.setRowHeight(30);
        branchDetailsTable.setShowGrid(true);
        jScrollPane1.setViewportView(branchDetailsTable);

        jPanel7.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 480, 150));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 1170, 700));

        setSize(new java.awt.Dimension(1523, 741));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed

    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        loginPage login = new loginPage();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        DrugManager manager = new DrugManager();
        manager.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
        jLabel10.setBackground(mouseEnterColor);
    }//GEN-LAST:event_jLabel10MouseEntered

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
        jLabel10.setBackground(mouseExitColor);
    }//GEN-LAST:event_jLabel10MouseExited

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        EmployeeManager manage = new EmployeeManager();
        manage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        DispatchDrugs disp = new DispatchDrugs();
        disp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
       ReturnDrugs drugs = new ReturnDrugs();
       drugs.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        DispatchRecords dispatch = new DispatchRecords();
        dispatch.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
        AdminLogin admin = new AdminLogin();
        admin.setVisible(true);
        
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        BranchRecords reco = new BranchRecords();
        reco.setVisible(true);
        
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        AddBranchDrugs add = new AddBranchDrugs();
        add.setVisible(true);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        SupplierDetails supply = new SupplierDetails();
        supply.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        clearTable();
        setBranchDetailsToTable();
    }//GEN-LAST:event_jLabel15MouseClicked

    private void expiredStockCardFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_expiredStockCardFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_expiredStockCardFocusGained

    private void noMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_noMouseClicked
    
    }//GEN-LAST:event_noMouseClicked

    private void expiredStockCardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expiredStockCardMouseClicked
       startImageRotation();
        
    }//GEN-LAST:event_expiredStockCardMouseClicked

    private void shortExpiryCardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_shortExpiryCardMouseClicked
        startImageRotation();
    }//GEN-LAST:event_shortExpiryCardMouseClicked

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
            java.util.logging.Logger.getLogger(welcomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(welcomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(welcomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(welcomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new welcomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable branchDetailsTable;
    private javax.swing.JTable branchDetailsTable1;
    private javax.swing.JLabel dispatchCard;
    private javax.swing.JTable drugDetailsTable;
    private javax.swing.JLabel drugRecordCard;
    private javax.swing.JLabel employeeCard;
    private javax.swing.JLabel expiredStockCard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable lowStockTable;
    private javax.swing.JLabel no;
    private javax.swing.JLabel shortExpiryCard;
    private javax.swing.JLabel supplyCard;
    // End of variables declaration//GEN-END:variables
}
