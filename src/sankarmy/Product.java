package sankarmy;


import sankarmy.MyConnection;


public class Product extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Product.class.getName());

      public Product() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
         loadTableData();
         autoIdGenerate();
         
         
    }
public void loadTableData() {
    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); 

    String query = "SELECT Item_id, item_name, purchase_rate, sale_rate FROM items";

    try (java.sql.Connection con = MyConnection.getConnection();
         java.sql.PreparedStatement pst = con.prepareStatement(query);
         java.sql.ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("Item_id");
            String itemName = rs.getString("item_name");
            double purchasePrice = rs.getDouble("purchase_rate");
            double salePrice = rs.getDouble("sale_rate");
            
                        // --- DECIMAL FORMATTING CORRECTION (0.00 ke liye) ---
            String formattedPurchase = String.format("%.2f", purchasePrice);
            String formattedSale = String.format("%.2f", salePrice);

            model.addRow(new Object[]{id, itemName, formattedPurchase, formattedSale});
        }

    } catch (java.sql.SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    
}
public void autoIdGenerate() {
    String query = "SELECT MAX(Item_id) FROM items";
    try (java.sql.Connection con = MyConnection.getConnection();
         java.sql.PreparedStatement pst = con.prepareStatement(query);
         java.sql.ResultSet rs = pst.executeQuery()) {
         
        if (rs.next()) {
            int maxId = rs.getInt(1);
            txtid.setText(String.valueOf(maxId + 1)); // Agli ID set karega
        } else {
            txtid.setText("1"); // Agar table khali hai to 1 se shuru hoga
        }
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }
}
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtItemName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPurchaseprice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtsaleprice = new javax.swing.JTextField();
        btnclear = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btnback = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("ID");

        txtid.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtid.setForeground(new java.awt.Color(204, 0, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 0));
        jLabel2.setText("Item Name");

        txtItemName.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtItemName.setForeground(new java.awt.Color(204, 0, 0));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 0));
        jLabel3.setText("Purchase Price");

        txtPurchaseprice.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtPurchaseprice.setForeground(new java.awt.Color(204, 0, 0));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 0, 0));
        jLabel4.setText("Sale Price");

        txtsaleprice.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtsaleprice.setForeground(new java.awt.Color(204, 0, 0));

        btnclear.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnclear.setForeground(new java.awt.Color(255, 0, 51));
        btnclear.setText("Clear");
        btnclear.addActionListener(this::btnclearActionPerformed);

        btnupdate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnupdate.setForeground(new java.awt.Color(255, 0, 51));
        btnupdate.setText("Update");
        btnupdate.addActionListener(this::btnupdateActionPerformed);

        btnback.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnback.setForeground(new java.awt.Color(255, 0, 51));
        btnback.setText("Back");
        btnback.addActionListener(this::btnbackActionPerformed);

        btnsave.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnsave.setForeground(new java.awt.Color(255, 0, 51));
        btnsave.setText("Save");
        btnsave.addActionListener(this::btnsaveActionPerformed);

        btndelete.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btndelete.setForeground(new java.awt.Color(255, 0, 51));
        btndelete.setText("Delete");
        btndelete.addActionListener(this::btndeleteActionPerformed);

        jTable1.setBackground(new java.awt.Color(204, 204, 204));
        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Item Name", "Purchase Price", "Sale Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setShowGrid(true);
        jTable1.setSurrendersFocusOnKeystroke(true);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(300);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnback)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnclear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPurchaseprice)
                            .addComponent(txtItemName)
                            .addComponent(txtsaleprice)
                            .addComponent(txtid))
                        .addGap(190, 190, 190)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 906, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPurchaseprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsaleprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnback, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnclear, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btndelete, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnupdate)
                        .addComponent(btnsave)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // 1. रो (Row) की ऊंचाई बढ़ाएं ताकि टेक्स्ट साफ दिखे
jTable1.setRowHeight(30);

// 2. ग्रिड लाइन्स का कलर हल्का ग्रे (Light Grey) करें जो आँखों को चुभे नहीं
jTable1.setGridColor(new java.awt.Color(230, 230, 230));

// 3. हेडर (Header) का बैकग्राउंड और फॉन्ट बदलें
jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
jTable1.getTableHeader().setBackground(new java.awt.Color(51, 153, 255)); // ब्लू हेडर
jTable1.getTableHeader().setForeground(java.awt.Color.white); // व्हाइट टेक्स्ट

// 4. टेबल के अंदर के फॉन्ट को बदलें
jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
    }//GEN-LAST:event_formWindowOpened

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
    // 1. Validation: Check if inputs are empty (ID auto-generated hai isliye use check nahi karenge)
    if(txtItemName.getText().trim().isEmpty() || 
       txtPurchaseprice.getText().trim().isEmpty() || txtsaleprice.getText().trim().isEmpty()) {
        
        javax.swing.JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        // 2. TextFields se data nikalna
        int Item_id = Integer.parseInt(txtid.getText().trim());
        String itemName = txtItemName.getText().trim();
        double purchaserate = Double.parseDouble(txtPurchaseprice.getText().trim());
        double salerate = Double.parseDouble(txtsaleprice.getText().trim());

        // phpMyAdmin table ke columns ke hisab se query
        String query = "INSERT INTO items (Item_id, item_name, purchase_rate, sale_rate) VALUES (?, ?, ?, ?)";
        
        try (java.sql.Connection con = MyConnection.getConnection();
             java.sql.PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setInt(1, Item_id);
            pst.setString(2, itemName);
            pst.setDouble(3, purchaserate);
            pst.setDouble(4, salerate);
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Product Saved Successfully!");
                
                // --- UPDATE 1: SAVE HOTE HI TABLE AUTO-REFRESH HOGA ---
                loadTableData(); 
                
                // Text fields ko clear karna (ID ko chhod kar)
                txtItemName.setText("");
                txtPurchaseprice.setText("");
                txtsaleprice.setText("");
                
                // --- UPDATE 2: ID AUTOMATIC EK BADH JAYEGI ---
                int nextId = Item_id + 1;
                txtid.setText(String.valueOf(nextId));
                
                // Cursor automatically Item Name par chala jaye
                txtItemName.requestFocus();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Failed to Save Product.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            } catch (java.sql.SQLException e) {
            // यहाँ हम चेक कर रहे हैं कि एरर डुप्लिकेट आईडी (1062) का है या कुछ और
           if (e.getErrorCode() == 1062) {
           javax.swing.JOptionPane.showMessageDialog(this, " Duplicate id ' Please update button press kare", "Message", javax.swing.JOptionPane.WARNING_MESSAGE);
    }      else {
            // बाकी किसी भी एरर के लिए यह असली मैसेज दिखाएगा
            javax.swing.JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
            e.printStackTrace();
}
     
    }     catch (NumberFormatException e) {
           javax.swing.JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Rates!", "Input Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
            if(txtid.getText().trim().isEmpty() || txtItemName.getText().trim().isEmpty() || 
       txtPurchaseprice.getText().trim().isEmpty() || txtsaleprice.getText().trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Please select or enter an item to update.");
        return;
    }

    try {
        int Item_id = Integer.parseInt(txtid.getText().trim());
        String itemName = txtItemName.getText().trim();
        double purchaserate = Double.parseDouble(txtPurchaseprice.getText().trim());
        double salerate = Double.parseDouble(txtsaleprice.getText().trim());

        String query = "UPDATE items SET item_name=?, purchase_rate=?, sale_rate=? WHERE Item_id=?";

        try (java.sql.Connection con = MyConnection.getConnection();
             java.sql.PreparedStatement pst = con.prepareStatement(query)) {
            
            pst.setString(1, itemName);
            pst.setDouble(2, purchaserate);
            pst.setDouble(3, salerate);
            pst.setInt(4, Item_id);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Item Updated Successfully!");
                loadTableData(); // Table refresh
                btnclearActionPerformed(null); // Fields reset karne ke liye
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Item ID not found.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
           txtItemName.setText("");
    txtPurchaseprice.setText("");
    txtsaleprice.setText("");
    
    // Nayi fresh auto ID generate karna
    autoIdGenerate(); 
    
    txtItemName.requestFocus();
    }//GEN-LAST:event_btnclearActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
         // 1. चेक करें कि आईडी बॉक्स खाली तो नहीं है
    if (txtid.getText().trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "कृपया डिलीट करने के लिए आइटम आईडी चुनें!");
        return;
    }

    // 2. यूजर से कन्फर्मेशन लें
    int p = javax.swing.JOptionPane.showConfirmDialog(null, "Do You Went To Delete", "Delete", javax.swing.JOptionPane.YES_NO_OPTION);
    
    if (p == 0) { // अगर यूजर YES दबाता है
        int Item_id = Integer.parseInt(txtid.getText().trim());
        
        // सभी टेबल्स से डेटा हटाने की SQL क्वेरीज
        String queryItems = "DELETE FROM items WHERE Item_id=?";
        String queryStock = "DELETE FROM stock_report WHERE Item_id=?"; 
        String querySale = "DELETE FROM sale_invoice_details WHERE item_id=?"; 
        String queryPurchase = "DELETE FROM invoice_details WHERE item_id=?"; 

        java.sql.Connection con = null;
        java.sql.PreparedStatement pstItems = null;
        java.sql.PreparedStatement pstStock = null;
        java.sql.PreparedStatement pstSale = null;
        java.sql.PreparedStatement pstPurchase = null;

        try {
            con = MyConnection.getConnection();
            
            // 1. Sale Details से डिलीट करें
            pstSale = con.prepareStatement(querySale);
            pstSale.setInt(1, Item_id);
            pstSale.executeUpdate();

            // 2. Purchase Details से डिलीट करें
            pstPurchase = con.prepareStatement(queryPurchase);
            pstPurchase.setInt(1, Item_id);
            pstPurchase.executeUpdate();

            // 3. Stock टेबल से डिलीट करें
            pstStock = con.prepareStatement(queryStock);
            pstStock.setInt(1, Item_id);
            pstStock.executeUpdate();

            // 4. मुख्य Items टेबल से डिलीट करें
            pstItems = con.prepareStatement(queryItems);
            pstItems.setInt(1, Item_id);
            int rowsAffected = pstItems.executeUpdate();

            if (rowsAffected > 0) {
                // हिंदी फॉन्ट सपोर्ट न होने पर स्क्वायर बॉक्स न दिखें, इसलिए मैसेज अंग्रेजी में रखा है
                javax.swing.JOptionPane.showMessageDialog(null, "Item deleted successfully!");
                
                // फॉर्म को रीसेट और नई आईडी जनरेट करें
                
                 loadTableData(); 
                
                // Text fields ko clear karna (ID ko chhod kar)
                txtItemName.setText("");
                txtPurchaseprice.setText("");
                txtsaleprice.setText("");
                
                // --- UPDATE 2: ID AUTOMATIC EK BADH JAYEGI ---
              
                 autoIdGenerate();
                // Cursor automatically Item Name par chala jaye
                txtItemName.requestFocus();
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Item ID not found!");
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // सभी रिसोर्सेज को क्लोज करना (ब्रैकेट्स को बिल्कुल सही तरीके से बंद किया गया है)
            try {
                if (pstItems != null) pstItems.close();
                if (pstStock != null) pstStock.close();
                if (pstSale != null) pstSale.close();
                if (pstPurchase != null) pstPurchase.close();
                if (con != null) con.close();
            } catch (Exception ex) {
            }
        }
            }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbackActionPerformed
        // Pichle frame ka naam likhein (Jaise Dashboard frame)
    // Dashboard dash = new Dashboard();
    // dash.setVisible(true);
    
    this.dispose(); // Current Product window close ho jayegi
    }//GEN-LAST:event_btnbackActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int selectedRow = jTable1.getSelectedRow();
    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
    
    txtid.setText(model.getValueAt(selectedRow, 0).toString());
    txtItemName.setText(model.getValueAt(selectedRow, 1).toString());
    txtPurchaseprice.setText(model.getValueAt(selectedRow, 2).toString());
    txtsaleprice.setText(model.getValueAt(selectedRow, 3).toString());
    }//GEN-LAST:event_jTable1MouseClicked


    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> new Product().setVisible(true));
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnback;
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtItemName;
    private javax.swing.JTextField txtPurchaseprice;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtsaleprice;
    // End of variables declaration//GEN-END:variables
}
