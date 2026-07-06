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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        startdate = new com.toedter.calendar.JDateChooser();
        enddate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        cmbmode = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnExportToExcel = new javax.swing.JButton();
        btnExportToPDF = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        PurchaseTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(670, 1600));
        setName("SearchInvoice"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1600, 789));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Form");

        startdate.setDateFormatString("yyyy-MM-dd");
        startdate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        enddate.setDateFormatString("yyyy-MM-dd");
        enddate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("TO");

        cmbmode.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        cmbmode.setForeground(new java.awt.Color(204, 0, 0));
        cmbmode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cash", "Digital" }));

        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(204, 0, 0));
        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnBack.setForeground(new java.awt.Color(204, 0, 0));
        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnExportToExcel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnExportToExcel.setForeground(new java.awt.Color(204, 0, 0));
        btnExportToExcel.setText("Export To Excel");
        btnExportToExcel.addActionListener(this::btnExportToExcelActionPerformed);

        btnExportToPDF.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnExportToPDF.setForeground(new java.awt.Color(204, 0, 0));
        btnExportToPDF.setText("Export To PDF");
        btnExportToPDF.addActionListener(this::btnExportToPDFActionPerformed);

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

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 0, 0));
        jLabel2.setText("Purchase searchase Invoice");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(641, 641, 641)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(startdate, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(enddate, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbmode, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBack)
                        .addGap(180, 180, 180)
                        .addComponent(btnExportToPDF)
                        .addGap(51, 51, 51)
                        .addComponent(btnExportToExcel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1594, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 38, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(2, 2, 2)
                            .addComponent(jLabel1))
                        .addComponent(enddate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(startdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbmode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBack)
                            .addComponent(btnSearch)
                            .addComponent(btnExportToExcel)
                            .addComponent(btnExportToPDF)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(46, 46, 46)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 38;
        gridBagConstraints.ipady = 52;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 38, 0, 0);
        getContentPane().add(jPanel1, gridBagConstraints);

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

    private void btnExportToExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportToExcelActionPerformed
           // 1. सबसे पहले चेक करें कि आपकी जे-टेबल (jTable1) में डेटा है या नहीं
if (PurchaseTable.getRowCount() == 0) {
    javax.swing.JOptionPane.showMessageDialog(this, "टेबल में एक्सपोर्ट करने के लिए कोई डेटा नहीं है!");
    return;
}

try {
    // 2. नया एक्सेल वर्कबुक और शीट बनाएं
    org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
    org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Purchase Report");
    
    // 3. एक्सेल में हेडर रो (Columns के नाम) बनाएं
    org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
    for (int i = 0; i < PurchaseTable.getColumnCount(); i++) {
        org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
        cell.setCellValue(PurchaseTable.getColumnName(i));
    }
    
    // 4. जे-टेबल का सारा डेटा (Rows और Columns) एक्सेल में ट्रांसफर करें
    for (int r = 0; r < PurchaseTable.getRowCount(); r++) {
        org.apache.poi.ss.usermodel.Row row = sheet.createRow(r + 1);
        for (int c = 0; c < PurchaseTable.getColumnCount(); c++) {
            org.apache.poi.ss.usermodel.Cell cell = row.createCell(c);
            Object value = PurchaseTable.getValueAt(r, c);
            if (value != null) {
                cell.setCellValue(value.toString());
            }
        }
    }
    
    // 5. डायरेक्ट डेस्कटॉप का पाथ निकालें (OneDrive को ध्यान में रखते हुए)
    String userHome = System.getProperty("user.home");
    
    // पहले चेक करेंगे कि क्या वनड्राइव वाला डेस्कटॉप मौजूद है
    String folderPath = userHome + java.io.File.separator + "OneDrive" + java.io.File.separator + "Desktop" + java.io.File.separator + "MyPersonalReports";
    java.io.File directory = new java.io.File(folderPath);
    
    // अगर वनड्राइव वाला डेस्कटॉप नहीं मिलता, तो नॉर्मल डेस्कटॉप का इस्तेमाल करेंगे
    if (!directory.getParentFile().exists()) {
        folderPath = userHome + java.io.File.separator + "Desktop" + java.io.File.separator + "MyPersonalReports";
        directory = new java.io.File(folderPath);
    }
    
    // 6. डेस्कटॉप पर नया पर्सनल फोल्डर बनाएं
    if (!directory.exists()) {
        directory.mkdirs(); 
    }
    
    // 7. फाइल का नाम और पूरा रास्ता (Path) सेट करें
    String filePath = folderPath + java.io.File.separator + "PurchaseReport.xlsx";
    // 8. फाइल को बिना संवाद बॉक्स (Dialog) के सीधे बैकग्राउंड में राइट और क्लोज करें
    try (java.io.FileOutputStream out = new java.io.FileOutputStream(filePath)) {
        workbook.write(out);
        workbook.close();
        
        // सफलता का मैसेज
        javax.swing.JOptionPane.showMessageDialog(this, "file destop 'MyPersonalReports' save file!");
    }
    
} catch (Exception e) {
    e.printStackTrace();
    javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
}
    }//GEN-LAST:event_btnExportToExcelActionPerformed

    private void btnExportToPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportToPDFActionPerformed
          // 1. सबसे पहले चेक करें कि आपकी जे-टेबल (jTable1) में डेटा है या नहीं
if (PurchaseTable.getRowCount() == 0) {
    javax.swing.JOptionPane.showMessageDialog(this, "table me koi data nehi he");
    return;
}

try {
    // 2. डायरेक्ट डेस्कटॉप (Desktop) का पाथ निकालें (OneDrive को ध्यान में रखते हुए)
    String userHome = System.getProperty("user.home");
    String folderPath = userHome + java.io.File.separator + "OneDrive" + java.io.File.separator + "Desktop" + java.io.File.separator + "MyPersonalReports";
    java.io.File directory = new java.io.File(folderPath);
    
    // अगर वनड्राइव वाला डेस्कटॉप नहीं मिलता, तो नॉर्मल डेस्कटॉप का इस्तेमाल करेंगे
    if (!directory.getParentFile().exists()) {
        folderPath = userHome + java.io.File.separator + "Desktop" + java.io.File.separator + "MyPersonalReports";
        directory = new java.io.File(folderPath);
    }
    
    // अगर फोल्डर डेस्कटॉप पर नहीं बना है, तो कोड उसे अपने आप बना देगा
    if (!directory.exists()) {
        directory.mkdirs(); 
    }
    
    // PDF फाइल का पूरा नाम और रास्ता (Path) सेट करें
    String filePath = folderPath + java.io.File.separator + "PurchaseReport.pdf";

    // 3. iText 9+ के मुख्य ऑब्जेक्ट्स सेटअप करें
    com.itextpdf.kernel.pdf.PdfWriter writer = new com.itextpdf.kernel.pdf.PdfWriter(filePath);
    com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
    com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDoc);

    // 4. iText 9 के अनुसार सिंपल और एरर-फ्री हेडिंग बनाएं
    com.itextpdf.layout.element.Paragraph heading = new com.itextpdf.layout.element.Paragraph("Purchase Report");
    heading.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
    document.add(heading);
    
    // थोड़ा स्पेस देने के लिए खाली पैराग्राफ
    document.add(new com.itextpdf.layout.element.Paragraph("\n"));

    // 5. टेबल के कॉलम्स की संख्या के हिसाब से iText टेबल सेटअप करें
    int totalColumns = PurchaseTable.getColumnCount();
    com.itextpdf.layout.element.Table pdfTable = new com.itextpdf.layout.element.Table(totalColumns);

    // 6. PDF टेबल में हेडर (Columns के नाम) जोड़ें
    for (int i = 0; i < totalColumns; i++) {
        com.itextpdf.layout.element.Cell cell = new com.itextpdf.layout.element.Cell();
        cell.add(new com.itextpdf.layout.element.Paragraph(PurchaseTable.getColumnName(i)));
        pdfTable.addHeaderCell(cell);
    }

    // 7. jTable1 का सारा डेटा (Rows और Columns) PDF टेबल में ट्रांसफर करें
    for (int r = 0; r < PurchaseTable.getRowCount(); r++) {
        for (int c = 0; c < totalColumns; c++) {
            Object value = PurchaseTable.getValueAt(r, c);
            String cellValue = (value != null) ? value.toString() : "";
            pdfTable.addCell(new com.itextpdf.layout.element.Cell().add(new com.itextpdf.layout.element.Paragraph(cellValue)));
        }
    }

    // 8. टेबल को डॉक्यूमेंट में जोड़ें और फाइल बंद करें
    document.add(pdfTable);
    document.close();
    javax.swing.JOptionPane.showMessageDialog(this, "PDF save");
    // सफलता का मैसेज
   } catch (Exception e) {
    e.printStackTrace();
    javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
}
    
    }//GEN-LAST:event_btnExportToPDFActionPerformed

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
    private javax.swing.JButton btnExportToExcel;
    private javax.swing.JButton btnExportToPDF;
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
