package sankarmy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class NewJFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(NewJFrame.class.getName());

   
    public NewJFrame() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
         btnSearch.doClick();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PurchaseTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        startdate = new com.toedter.calendar.JDateChooser();
        enddate = new com.toedter.calendar.JDateChooser();
        cmbmode = new javax.swing.JComboBox<>();
        btnBack = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("SearchInvoice"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        PurchaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice No", "Party Name", "Date", "Pamenty Mode", "Total Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        PurchaseTable.setShowGrid(true);
        PurchaseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PurchaseTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(PurchaseTable);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Form");

        startdate.setDateFormatString("yyyy-MM-dd");
        startdate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        enddate.setDateFormatString("yyyy-MM-dd");
        enddate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        cmbmode.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        cmbmode.setForeground(new java.awt.Color(204, 0, 0));
        cmbmode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cash", "Digital" }));

        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBack.setForeground(new java.awt.Color(204, 0, 0));
        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(204, 0, 0));
        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("TO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startdate, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(enddate, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbmode, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addContainerGap(525, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(2, 2, 2)
                            .addComponent(jLabel1))
                        .addComponent(enddate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(startdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbmode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBack)
                            .addComponent(btnSearch)))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 0, 0));
        jLabel2.setText("Purchase searchase Invoice");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(664, 664, 664)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(32, 32, 32)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
                // 1. रो (Row) की ऊंचाई बढ़ाएं ताकि टेक्स्ट साफ दिखे
PurchaseTable.setRowHeight(35);
PurchaseTable.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

// हेडर को लेफ्ट अलाइन (Left Align) करने के लिए
javax.swing.table.DefaultTableCellRenderer headerRenderer = (javax.swing.table.DefaultTableCellRenderer)
    PurchaseTable.getTableHeader().getDefaultRenderer();
headerRenderer.setHorizontalAlignment(javax.swing.JLabel.LEFT);
    }//GEN-LAST:event_formWindowOpened

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
             // 1. Dropdown safe handling
    String paymentMode = cmbmode.getSelectedItem() != null ? cmbmode.getSelectedItem().toString().trim() : "Select";
    
    java.util.Date fromDate = startdate.getDate();
    java.util.Date toDate = enddate.getDate();
    
    // Agar तारीखें खाली हैं, तो आज की तारीख सेट करें
    if (fromDate == null) fromDate = new java.util.Date();
    if (toDate == null) toDate = new java.util.Date();
    
    java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
    java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());
    
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    try {
        con = MyConnection.getConnection(); 
        if (con == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Database connection fail!");
            return;
        }
        
        String query = "";
        
        // Agar 'Select' chuna hai toh bina payment mode ke sirf date se filter karein
        if (paymentMode.equalsIgnoreCase("Select")) {
            query = "SELECT invoice_no, party_name, date, payment_mode, total_amount "
                  + "FROM invoices WHERE date BETWEEN ? AND ?";
            pst = con.prepareStatement(query);
            pst.setDate(1, sqlFromDate);
            pst.setDate(2, sqlToDate);
        } 
        // Agar Cash ya Digital chuna hai toh payment mode ke sath filter karein
        else {
            query = "SELECT invoice_no, party_name, date, payment_mode, total_amount "
                  + "FROM invoices WHERE date BETWEEN ? AND ? AND payment_mode = ?";
            pst = con.prepareStatement(query);
            pst.setDate(1, sqlFromDate);
            pst.setDate(2, sqlToDate);
            pst.setString(3, paymentMode);
        }
        
        rs = pst.executeQuery();
        
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) PurchaseTable.getModel();
        model.setRowCount(0); // Purana data saaf karein
        
        boolean hasData = false;
        boolean firstrow = true;
        
        while (rs.next()) {
            hasData = true;
            if (firstrow) {
                java.sql.Date currentDataDate = rs.getDate("date");
                if (currentDataDate != null) {
                    startdate.setDate(currentDataDate);
                    enddate.setDate(currentDataDate);
                }
                firstrow = false; 
            }
            
            // CORRECTION: MouseClicked event ke sath index match karne ke liye 
            // Index 0 par 'invoice_no' rakha hai, 'id' ko remove kiya hai
            Object[] row = {
                rs.getString("invoice_no"),  
                rs.getString("party_name"),  
                rs.getDate("date"),          
                rs.getString("payment_mode"),
                rs.getDouble("total_amount") 
            };
            model.addRow(row);
        }
        
        // ------------------ FALLBACK LOGIC (IF NO DATA FOUND) ------------------
        if (!hasData) {
            enddate.setDate(new java.util.Date());
            
            // Resources close karein naye query chalane se pehle
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            
            String fallbackQuery = "";
            if (paymentMode.equalsIgnoreCase("Select")) {
                fallbackQuery = "SELECT invoice_no, party_name, date, payment_mode, total_amount FROM invoices ORDER BY date ASC";
                pst = con.prepareStatement(fallbackQuery);
            } else {
                fallbackQuery = "SELECT invoice_no, party_name, date, payment_mode, total_amount FROM invoices WHERE payment_mode = ? ORDER BY date ASC";
                pst = con.prepareStatement(fallbackQuery);
                pst.setString(1, paymentMode);
            }
            
            rs = pst.executeQuery();
            
            boolean firstRowFallback = true;
            while (rs.next()) {
                if (firstRowFallback) {
                    java.sql.Date firstAvailableDate = rs.getDate("date");
                    if (firstAvailableDate != null) {
                        startdate.setDate(firstAvailableDate); 
                    }
                    firstRowFallback = false; 
                }
                
                // CORRECTION: Fallback row me bhi same column sequence maintain kiya hai
                Object[] row = {
                    rs.getString("invoice_no"),  
                    rs.getString("party_name"),  
                    rs.getDate("date"),          
                    rs.getString("payment_mode"),
                    rs.getDouble("total_amount") 
                };
                model.addRow(row);
            }
        }
                
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        e.printStackTrace();
    } finally {
        // CORRECTION: Saare database resources aur connection safely close kiye hain
        try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (pst != null) pst.close(); } catch (Exception e) { e.printStackTrace(); }
        try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
    
}
    }//GEN-LAST:event_btnSearchActionPerformed

    private void PurchaseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PurchaseTableMouseClicked
      // 1. Selected Row index nikalyein
    int row = PurchaseTable.getSelectedRow();
    
    // Agar row sahi me select hui hai tabhi andar jayein
    if (row != -1) {
        
        // SAFE CHECK: Null value handle karne ke liye direct .toString() ki jagah check lagaya hai
        Object invoiceObj = PurchaseTable.getValueAt(row, 0);
        if (invoiceObj == null || invoiceObj.toString().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selected row me Invoice Number nahi mila!");
            return;
        }
        
        String invoiceNo = invoiceObj.toString().trim();
        
        // 2. Check karein kya Purchase Form ka instance pehle se open aur active hai
        if (Purchase.currentInstance != null && Purchase.currentInstance.isVisible()) {
            
            // Purane open form me hi naya data reflect karwayein
            Purchase.currentInstance.updateInvoiceData(invoiceNo);
            Purchase.currentInstance.toFront();
            Purchase.currentInstance.requestFocus();
            
        } else {
            
            // 3. Agar Form band hai ya pehle baar khul raha hai toh naya object banayein
            Purchase pFrm = new Purchase();
            
            // Static instance ko assign karein taaki agli baar ye track ho sake
            Purchase.currentInstance = pFrm; 
            
            pFrm.setLocationRelativeTo(null);
            pFrm.setVisible(true);
            
            // Naye khule form me selected invoice ka data pass karke load karein
            pFrm.updateInvoiceData(invoiceNo);
        }
    }    
    }//GEN-LAST:event_PurchaseTableMouseClicked

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
    this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new NewJFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable PurchaseTable;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbmode;
    private com.toedter.calendar.JDateChooser enddate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser startdate;
    // End of variables declaration//GEN-END:variables
}
