
package sankarmy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class Sale extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Sale.class.getName());

 public static Sale currentInstance = null;
 // 1. Apni class ke andar sabse upar variables ke sath ye ek line add karein
private boolean isUpdating = false; 

    public Sale() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        datechose.setDate(new java.util.Date());
        currentInstance = this; 
        loadItemsToCombo();
        txtid.requestFocus();
        cmbItem.setSelectedIndex(-1);
        txtprice.setText("0.00");
        txtAmount.setText("0.00");
        //table qty colum Edit karena
        // Isko Form ke Constructor (Public YourClassName()) ke andar dalein, initComponents(); ke niche
     jTable1.getModel().addTableModelListener(new javax.swing.event.TableModelListener() {
      @Override
    public void tableChanged(javax.swing.event.TableModelEvent e) {
        // Agar badlaav kisi row ke UPDATE hone par hua hai
        if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
            int row = e.getFirstRow();
            int column = e.getColumn();

            // Hum sirf tabhi calculation karenge jab Column 2 (Qty) badla jaye
            if (column == 2) { 
                try {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    
                    // 1. New Qty aur Price fetch karein
                    String qtyStr = model.getValueAt(row, 2).toString().trim();
                    String priceStr = model.getValueAt(row, 3).toString().trim();
                    
                    int newQty = Integer.parseInt(qtyStr);
                    double price = Double.parseDouble(priceStr);
                    
                    // Validation: Agar user galti se quantity 0 ya minus me dal de
                    if (newQty <= 0) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Quantity 1 ya usse zyada honi chahiye!");
                        model.setValueAt("1", row, 2); // Wapas 1 set kar dega
                        return;
                    }
                    
                    // 2. Naya Row Amount calculate karein
                    double newAmount = newQty * price;
                    
                    // 3. Bina loop fasaye smoothly Table model update karein (Sirf Amount Column 4)
                    // Taaki ye listener baar-baar loop me na phase, column check lagaya hai
                    model.setValueAt(String.format("%.2f", newAmount), row, 4);
                    
                    // 4. Grand Total calculate karein (Pura Total update karne ke liye)
                    double grandTotal = 0;
                    for (int i = 0; i < model.getRowCount(); i++) {
                        Object amtValue = model.getValueAt(i, 4);
                        if (amtValue != null) {
                            grandTotal += Double.parseDouble(amtValue.toString());
                        }
                    }
                    
                    // Total Box me set karein
                    txtTotal.setText(String.format("%.2f", grandTotal));
                    
                } catch (NumberFormatException ex) {
                    // Agar user ne quantity me number ki jagah abc ya khali chor diya
                    javax.swing.JOptionPane.showMessageDialog(null, "Krupaya sahi number dalein!");
                    jTable1.getModel().setValueAt("1", row, 2); // Safe fallback to 1
                }
            }
        }
    }
});

        
    }

public void updateInvoiceData(String invoiceNo) {
         Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        con = MyConnection.getConnection();
        if (con == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Connection nahi mila!");
            return;
        }
        
        // CORRECTION: Pehle codes ke hisab se 'invoice_no' par INNER JOIN lagaya hai 
        // Aur details table se 'item_id' fetch kiya hai
        String sql = "SELECT m.invoice_no, m.date, m.payment_mode, m.total_amount, " +
                     "d.Item_id, d.item_name, d.quantity, d.sale_rate, d.sub_total " +
                     "FROM sales_invoices m " +
                     "INNER JOIN sale_invoice_details d ON m.invoice_no = d.invoice_no " +
                     "WHERE m.invoice_no = ?";
                     
        pst = con.prepareStatement(sql);
        
        // Agar database me invoice_no INT hai toh Integer me parse karke set karein
        try {
            pst.setInt(1, Integer.parseInt(invoiceNo.trim()));
        } catch (NumberFormatException nfe) {
            pst.setString(1, invoiceNo.trim()); // Fallback agar string ho
        }

        rs = pst.executeQuery();
        
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Purani table details saaf karne ke liye
        
        boolean isMasterDataSet = false;
        
        while (rs.next()) {
            // 1. Main Master Fields me data set karein (Sirf ek baar chalega)
            if (!isMasterDataSet) {
                txt_invoice_no.setText(rs.getString("invoice_no")); 
                                
                // Date parsing safe handling
                String dateStr = rs.getString("date");
                if (dateStr != null && !dateStr.isEmpty()) {
                    try {
                        java.util.Date date = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                        datechose.setDate(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                txtTotal.setText(rs.getString("total_amount"));
                cmbpmode.setSelectedItem(rs.getString("payment_mode")); 
                
                isMasterDataSet = true; 
            }
            
            // 2. Table me Items Row data add karein (CORRECTION: "id" ki jagah "item_id" kiya hai)
            Object[] rowData = {
                rs.getString("Item_id"),
                rs.getString("item_name"),
                rs.getString("quantity"),
                rs.getString("sale_rate"),
                rs.getString("sub_total")
            };
            model.addRow(rowData);
        }
        
        // Agar database me data mila hi nahi toh user ko batayein
        if (!isMasterDataSet) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ye Invoice Number nahi mila!");
        } else {
            this.toFront();
            this.requestFocus();
            if (txtid != null) {
                txtid.requestFocus(); // Naye item entries ke liye focus ready
            }
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    } finally {
        // Resources close karna safe coding practice hai
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (con != null) con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_invoice_no = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        datechose = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbItem = new javax.swing.JComboBox<>();
        txtqty = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtprice = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cmbpmode = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();
        btnback = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btn_invoice_Delete = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnserch = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnSavePrint = new javax.swing.JButton();
        btn_total_daywise = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        txt_invoice_no.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_invoice_no.setForeground(new java.awt.Color(204, 0, 0));
        txt_invoice_no.addActionListener(this::txt_invoice_noActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 0));
        jLabel2.setText("Invoice No");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 0));
        jLabel3.setText("Date");

        datechose.setDateFormatString("yyyy-MM-dd");
        datechose.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 0, 0));
        jLabel4.setText("ID");

        txtid.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtid.setForeground(new java.awt.Color(204, 0, 0));
        txtid.addActionListener(this::txtidActionPerformed);
        txtid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 0, 0));
        jLabel5.setText("Item Name");

        cmbItem.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmbItem.setForeground(new java.awt.Color(204, 0, 0));
        cmbItem.addActionListener(this::cmbItemActionPerformed);

        txtqty.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtqty.setForeground(new java.awt.Color(204, 0, 0));
        txtqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtqtyKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 0));
        jLabel6.setText("Qty");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 0));
        jLabel7.setText("Price");

        txtprice.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtprice.setForeground(new java.awt.Color(204, 0, 0));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 0, 0));
        jLabel8.setText("Amount");

        txtAmount.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtAmount.setForeground(new java.awt.Color(204, 0, 0));

        btnAdd.setIcon(new javax.swing.ImageIcon("C:\\Users\\dell\\OneDrive\\Documents\\NetBeansProjects\\Sankarmynew\\src\\sankarmy\\plus_4604818.png")); // NOI18N
        btnAdd.addActionListener(this::btnAddActionPerformed);

        jTable1.setBackground(new java.awt.Color(242, 242, 242));
        jTable1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jTable1.setForeground(new java.awt.Color(255, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Item Name", "Qty", "Price", "Amonut", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setShowGrid(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 0, 0));
        jLabel10.setText("Total Amount");

        txtTotal.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(204, 0, 0));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 0, 0));
        jLabel11.setText("Pay Mode");

        cmbpmode.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        cmbpmode.setForeground(new java.awt.Color(204, 0, 0));
        cmbpmode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Digital" }));

        btnUpdate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(204, 0, 0));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnback.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnback.setForeground(new java.awt.Color(204, 0, 0));
        btnback.setText("Back");
        btnback.addActionListener(this::btnbackActionPerformed);

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(204, 0, 0));
        jButton4.setText("Clear");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        btn_invoice_Delete.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_invoice_Delete.setForeground(new java.awt.Color(204, 0, 0));
        btn_invoice_Delete.setText("Invoice Delete");
        btn_invoice_Delete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_invoice_Delete.addActionListener(this::btn_invoice_DeleteActionPerformed);

        btndelete.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btndelete.setForeground(new java.awt.Color(204, 0, 0));
        btndelete.setText("Delete");
        btndelete.addActionListener(this::btndeleteActionPerformed);

        btnserch.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnserch.setForeground(new java.awt.Color(204, 0, 0));
        btnserch.setText("Search Menage");
        btnserch.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnserch.addActionListener(this::btnserchActionPerformed);

        btnSave.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSave.setForeground(new java.awt.Color(204, 0, 0));
        btnSave.setText("Save");
        btnSave.addActionListener(this::btnSaveActionPerformed);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 51, 0));
        jLabel1.setText("Sale Invoice Barcode");

        btnSavePrint.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSavePrint.setForeground(new java.awt.Color(204, 0, 0));
        btnSavePrint.setText("Save And Print");
        btnSavePrint.addActionListener(this::btnSavePrintActionPerformed);

        btn_total_daywise.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_total_daywise.setForeground(new java.awt.Color(204, 0, 0));
        btn_total_daywise.setText("Day wise Total sale");
        btn_total_daywise.addActionListener(this::btn_total_daywiseActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(datechose, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(349, 349, 349))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(txt_invoice_no, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(18, 18, 18)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cmbItem, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(49, 49, 49)
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGap(65, 65, 65)))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtprice, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAdd))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1325, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnserch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(719, 719, 719)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbpmode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 6, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_total_daywise, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btn_invoice_Delete, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnback, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSavePrint, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(555, 555, 555))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_invoice_no, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(datechose, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbItem)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(cmbpmode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUpdate)
                            .addComponent(btnback)
                            .addComponent(jButton4)
                            .addComponent(btn_invoice_Delete)
                            .addComponent(btndelete)
                            .addComponent(btnSave)
                            .addComponent(btnSavePrint))
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnserch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_total_daywise)
                        .addGap(24, 24, 24))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidKeyReleased
       
        }

    // फ़ील्ड्स को खाली करने के लिए एक अलग हेल्पर मेथड (ताकि कोड साफ़ रहे)
    private void clearFields() {
      cmbItem.setSelectedItem("");
      txtqty.setText("");
      txtprice.setText("0.00");
            
    }//GEN-LAST:event_txtidKeyReleased

      private void calculateAmount() {
            try {
                double qty = 0;
                double price = 0;

                // क्वांटिटी चेक करें
                if (txtqty.getText() != null && !txtqty.getText().trim().isEmpty()) {
                    qty = Double.parseDouble(txtqty.getText().trim());
                }

                // प्राइस चेक करें
                if (txtprice.getText() != null && !txtprice.getText().trim().isEmpty()) {
                    price = Double.parseDouble(txtprice.getText().trim());
                }

                // अमाउंट कैलकुलेट करें
                double amount = qty * price;

                // डिस्प्ले करें
                txtAmount.setText(String.format("%.2f", amount));

            } catch (NumberFormatException e) {
                txtAmount.setText("0.00");
            }
        }
        private void loadItemsToCombo() {
            Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;

            try {
                // ComboBox को पहले से खाली करें ताकि नाम रिपीट न हों
                cmbItem.removeAllItems();

                con = MyConnection.getConnection();
                if (con != null) {
                    // अपनी सही टेबल का नाम डालें (अगर टेबल का नाम product है तो product रखें)
                    String sql = "SELECT item_name FROM items ORDER BY item_name ASC";
                    pst = con.prepareStatement(sql);
                    rs = pst.executeQuery();

                    while (rs.next()) {
                        // एक-एक करके सारे आइटम ड्रॉपडाउन में जोड़ना
                        cmbItem.addItem(rs.getString("item_name"));
                    }
                }
            } catch (Exception e) {
                System.out.println("Error loading items: " + e.getMessage());
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (pst != null) pst.close();
                    if (con != null) con.close();
                } catch (Exception ex) {
                    // close error log
                }
            }
        }
    private void cmbItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbItemActionPerformed
   if (cmbItem.getSelectedIndex() == -1 || cmbItem.getSelectedItem() == null) {
    return;
}

// यह लाइन पक्का करेगी कि जब ID फ़ील्ड कॉम्बो बॉक्स को बदल रही हो, तब यह कोड न चले
if (!cmbItem.isFocusOwner()) {
    return;
}

String selectedItem = cmbItem.getSelectedItem().toString();

Connection con = null;
PreparedStatement pst = null;
ResultSet rs = null;

try {
    con = MyConnection.getConnection();
    if (con != null) {
        // SQL क्वेरी: आइटम नेम के आधार पर ID, sale_rate और available_stock निकालना
        String sql = "SELECT i.Item_id, i.sale_rate, s.available_stock "
                   + "FROM items i "
                   + "LEFT JOIN stock_report s ON i.Item_id = s.item_id "
                   + "WHERE i.item_name = ?";
        
        pst = con.prepareStatement(sql);
        pst.setString(1, selectedItem);
        rs = pst.executeQuery();

        if (rs.next()) {
            String itemId = rs.getString("Item_id");
            double saleRate = rs.getDouble("sale_rate");
            
            // स्टॉक चेक करना
            int stock = 0;
            if (rs.getString("available_stock") != null) {
                stock = rs.getInt("available_stock");
            }
            
            // 🛑 वैलिडेशन: यदि स्टॉक 0 या उससे कम है तो टेबल में डेटा ऐड नहीं होगा
            if (stock <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Out Of Stock! Stock Negetive", 
                    "", 
                    javax.swing.JOptionPane.WARNING_MESSAGE);
                return; // कोड यहीं रुक जाएगा, टेबल में रो ऐड नहीं होगी
            }
            
            int defaultQty = 1;
            double initialAmount = defaultQty * saleRate;

            // टेबल मॉडल प्राप्त करना
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
            
            // टेबल में नई रो (Row) जोड़ना
            // Column 0 = ID, 1 = Name, 2 = Qty, 3 = Price, 4 = Amount, 5 = Available Stock
            model.addRow(new Object[]{
                itemId,                                 // Column 0
                selectedItem,                           // Column 1
                String.valueOf(defaultQty),            // Column 2
                String.format("%.2f", saleRate),        // Column 3
                String.format("%.2f", initialAmount),    // Column 4
                String.valueOf(stock)                   // Column 5
            });
            
            // ग्रैंड टोटल को अपडेट करने के लिए (यदि आवश्यक हो)
            // calculateGrandTotal(); 
        }
    }
} catch (Exception e) {
    System.out.println("Error on combo select: " + e.getMessage());
} finally {
    try {
        if (rs != null) rs.close();
        if (pst != null) pst.close();
        if (con != null) con.close();
    } catch (Exception ex) {
        // close error log
    }
}


    }//GEN-LAST:event_cmbItemActionPerformed

    private void txtqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtyKeyReleased
        calculateAmount();
    }//GEN-LAST:event_txtqtyKeyReleased

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            // 1. आपके द्वारा टेक्स्टबॉक्स/कॉम्बोबॉक्स से लिया गया डेटा (यह आपके पहले वाले कोड जैसा ही है)
            String id = txtid.getText().trim();
            String itemName = cmbItem.getSelectedItem() != null ? cmbItem.getSelectedItem().toString() : "";
            String quantity = txtqty.getText().trim();
            String price = txtprice.getText().trim();
            String amount = txtAmount.getText().trim();

            // वैलिडेशन: चेक करें कि कोई फील्ड खाली तो नहीं है
            if (id.isEmpty() || itemName.isEmpty() || quantity.isEmpty() || price.isEmpty() || amount.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Kripaya saari jankari sahi se bhare.");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            int rowCount = model.getRowCount();
            boolean itemExists = false;

            // 2. पूरी तालिका में चेक करें कि क्या यह ID पहले से मौजूद है
            for (int i = 0; i < rowCount; i++) {
                String tableId = model.getValueAt(i, 0).toString(); // Column 0: ID

                if (tableId.equals(id)) {
                    // अगर आइटम मिल गया, तो पुरानी मात्रा में नई मात्रा जोड़ें
                    int oldQty = Integer.parseInt(model.getValueAt(i, 2).toString()); // Column 2: Qty
                    int newQty = oldQty + Integer.parseInt(quantity);

                    // नया अमाउंट (Amount = New Qty * Price) कैलकुलेट करें
                    double itemPrice = Double.parseDouble(price);
                    double newAmount = newQty * itemPrice;

                    // तालिका की उसी रो (Row) में डेटा अपडेट करें
                    model.setValueAt(String.valueOf(newQty), i, 2); // Qty कॉलम अपडेट
                    model.setValueAt(String.valueOf(newAmount), i, 4); // Amount कॉलम अपडेट

                    itemExists = true;
                    break; // लूप से बाहर निकलें क्योंकि आइटम मिल गया है
                }
            }

            // 3. अगर आइटम पहले से मौजूद नहीं था, तभी नई लाइन (Row) जोड़ें
            if (!itemExists) {
                model.addRow(new Object[]{id, itemName, quantity, price, amount});
            }

            // 4. कुल टोटल (Total Amount) कैलकुलेट करने का लूप
            double total = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                Object value = jTable1.getValueAt(i, 4); // Column 4: Amount
                if (value != null) {
                    total += Double.parseDouble(value.toString());
                }
            }

            // टोटल अमाउंट को टेक्स्टबॉक्स में सेट करें
            txtTotal.setText(String.valueOf(total));

            // 5. इनपुट फील्ड्स को साफ करें
            clearFields();
            txtid.setText("");
            txtid.requestFocus(); // वापस ID वाले बॉक्स पर कर्सर ले जाएं

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // Check karein ki user ne DOUBLE CLICK (2 clicks) kiya hai ya nahi
        if (evt.getClickCount() == 2) {

            // DefaultTableModel nikalen row remove karne ke liye
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();

            // Kaunsi row select hui hai uska index nikalen
            int selectedRowIndex = jTable1.getSelectedRow();

            // Agar koi row selected hai (index -1 nahi hai)
            if (selectedRowIndex != -1) {

                // Row delete karne se pehle user se confirmation lein (Safe Tarika)
                int dialogResult = javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    "Do you went To Delete",
                    "Confirm Delete",
                    javax.swing.JOptionPane.YES_NO_OPTION
                );

                if (dialogResult == javax.swing.JOptionPane.YES_OPTION) {
                    // Table se row delete karein
                    model.removeRow(selectedRowIndex);

                    // ERROR/MISMATCH SE BACHNE KE LIYE: Row delete hote hi Total Amount recalculate karein
                    
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
                                                 
    Connection con = null;
    PreparedStatement pstMaster = null;
    PreparedStatement pstDeleteOld = null;
    PreparedStatement pstDetails = null;

    // 1. Inputs fetch aur trim karein
    String totalAmtStr = txtTotal.getText().trim();
    String payMode = cmbpmode.getSelectedItem() != null ? cmbpmode.getSelectedItem().toString().trim() : "";
    
    // ध्यान दें: अपडेट करने के लिए आपके पास स्क्रीन पर पुराना Invoice No. होना ज़रूरी है।
    // मान लेते हैं कि उसका नाम txtInvoiceNo है (इसे अपने वेरिएबल नाम से बदल लें)
    String invoiceNoStr = txt_invoice_no.getText().trim(); 

    // 2. Validation Check (इसमें Invoice No का खाली न होना भी शामिल है)
    if (invoiceNoStr.isEmpty() ||
        datechose.getDate() == null ||
        jTable1.getRowCount() == 0 ||
        totalAmtStr.isEmpty() ||
        totalAmtStr.equals("0") ||
        totalAmtStr.equals("0.0") ||
        totalAmtStr.equals("0.00") ||
        payMode.isEmpty()) {

        javax.swing.JOptionPane.showMessageDialog(this, "Krupaya check karein: Invoice No, Date, Table ya Total Amount khali to nahi hai?");
        return;
    }

    try {
        // 3. Database Connection checked
        con = MyConnection.getConnection();
        if (con == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Connection nahi mila! Database check karein.");
            return;
        }

        // 4. Transaction start (Auto-commit false)
        con.setAutoCommit(false);

        // Parsing variables safely
        int updateInvoiceNo = Integer.parseInt(invoiceNoStr);
        double finalTotalAmt;
        try {
            finalTotalAmt = Double.parseDouble(totalAmtStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Total Amount sahi number format me nahi hai!");
            return;
        }

        // 5. Master Table Update - INSERT की जगह UPDATE क्वेरी
        String query1 = "UPDATE sales_invoices SET date = ?, payment_mode = ?, total_amount = ? WHERE invoice_no = ?";
        pstMaster = con.prepareStatement(query1);

        // Date Format safely database ke liye convert karein
        java.util.Date selectedDate = datechose.getDate();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(selectedDate);
        
        pstMaster.setString(1, formattedDate);
        pstMaster.setString(2, payMode);
        pstMaster.setDouble(3, finalTotalAmt);
        pstMaster.setInt(4, updateInvoiceNo); // WHERE क्लॉज के लिए

        int rowsAffected = pstMaster.executeUpdate();
        if (rowsAffected == 0) {
            throw new Exception("Yeh Invoice Number database me nahi mila!");
        }

        // 6. Detail Table से पुराने सभी आइटम्स को DELETE करना
        String deleteQuery = "DELETE FROM sale_invoice_details WHERE invoice_no = ?";
        pstDeleteOld = con.prepareStatement(deleteQuery);
        pstDeleteOld.setInt(1, updateInvoiceNo);
        pstDeleteOld.executeUpdate();

        // 7. Detail Table (sale_invoice_details) में नए आइटम्स का Entry loop (Batch Insert)
        String query2 = "INSERT INTO sale_invoice_details (invoice_no, Item_id, item_name, quantity, sale_rate, sub_total) VALUES (?, ?, ?, ?, ?, ?)";
        pstDetails = con.prepareStatement(query2);

        int totalRows = jTable1.getRowCount();

        for (int i = 0; i < totalRows; i++) {
            // Table ke cell values ko safely handle karein
            String ItemIdStr = jTable1.getValueAt(i, 0) != null ? jTable1.getValueAt(i, 0).toString().trim() : "0";
            String itemName = jTable1.getValueAt(i, 1) != null ? jTable1.getValueAt(i, 1).toString().trim() : "";
            String qtyStr = jTable1.getValueAt(i, 2) != null ? jTable1.getValueAt(i, 2).toString().trim() : "0";
            String rateStr = jTable1.getValueAt(i, 3) != null ? jTable1.getValueAt(i, 3).toString().trim() : "0";
            String subTotalStr = jTable1.getValueAt(i, 4) != null ? jTable1.getValueAt(i, 4).toString().trim() : "0";

            // Row validation
            if(itemName.isEmpty()) {
                throw new Exception("Row " + (i+1) + " me Item Name khali hai!");
            }

            // मौज़ूदा स्क्रीन वाले updateInvoiceNo को ही पहले parameter me daalein
            pstDetails.setInt(1, updateInvoiceNo); 
            pstDetails.setInt(2, Integer.parseInt(ItemIdStr));
            pstDetails.setString(3, itemName);
            pstDetails.setInt(4, Integer.parseInt(qtyStr));
            pstDetails.setDouble(5, Double.parseDouble(rateStr));
            pstDetails.setDouble(6, Double.parseDouble(subTotalStr));

            pstDetails.addBatch();
        }

        pstDetails.executeBatch();

        // 8. Success - Commit data permanently
        con.commit();
        javax.swing.JOptionPane.showMessageDialog(this, "Updated Successfully!");

        // 9. Fields reset form state clear karein
        if (txt_invoice_no != null) txt_invoice_no.setText(""); // पुराना नंबर हटा दें
        datechose.setDate(new java.util.Date());
        cmbpmode.setSelectedItem("Cash");
        txtTotal.setText("0.00");

        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        // Agli entry ke liye screen par naya number dikhana hai toh ye function chalayein
        autoInvoiceNumber(); 
        txtTotal.requestFocus(); 

    } catch (Exception e) {
        // 10. Error aane par Rollback (डेटा आधा-अधूरा अपडेट नहीं होगा)
        try {
            if (con != null) con.rollback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    } finally {
        // 11. Connections safely close karein
        try {
            if (pstMaster != null) pstMaster.close();
            if (pstDeleteOld != null) pstDeleteOld.close();
            if (pstDetails != null) pstDetails.close();
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    
}

        }

        public void autoInvoiceNumber() {
            Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;

            try {
                con = MyConnection.getConnection();

                // SQL क्वेरी जो सबसे बड़ा नंबर ढूंढ कर लाएगी
                String query = "SELECT MAX(CAST(invoice_no AS UNSIGNED)) FROM sales_invoices";
                pst = con.prepareStatement(query);
                rs = pst.executeQuery();

                if (rs.next()) {
                    int maxId = rs.getInt(1); // सबसे बड़ा नंबर मिलेगा (जैसे 1)
                    int nextId = maxId + 1;   // उसमें 1 जोड़ेंगे (जैसे 1 + 1 = 2)

                    txt_invoice_no.setText(String.valueOf(nextId)); // सीधे '2' सेट होगा
                } else {
                    // अगर डेटाबेस में कोई भी बिल नहीं है, तो पहला बिल नंबर 1 होगा
                    txt_invoice_no.setText("1");
                }
            } catch (Exception e) {
                txt_invoice_no.setText("1");
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (pst != null) pst.close();
                    if (con != null) con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbackActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnbackActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // 1. Text Fields ko khali karein
        txtTotal.setText("0.00");
        txt_invoice_no.setText("");

        // Agar aapke paas item id ya koi aur text field hai toh unhe bhi clear karein
        if (txtid != null) {
            txtid.setText("");
        }

        // 2. Dropdowns (ComboBoxes) ko default par set karein
        cmbpmode.setSelectedItem("Cash");
                // 3. Date Chooser me aaj ki current date set karein
        datechose.setDate(new java.util.Date());

        // 4. JTable ke saare rows ko delete/clear karein
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        // 5. Naya Invoice Number auto-generate karein
        autoInvoiceNumber();

        // 6. Primary input field par focus wapas le aayin
        txtid.requestFocus();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btn_invoice_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_invoice_DeleteActionPerformed

        // 1. User se Invoice Number input lena (Aap text box se bhi le sakte hain)
String invoiceNoStr = javax.swing.JOptionPane.showInputDialog(this, "Krupaya delete karne ke liye Invoice Number enter karein:");

if (invoiceNoStr == null || invoiceNoStr.trim().isEmpty()) {
    return; // Agar cancel kiya ya khali chhoda toh ruk jayein
}

// Check karein ki input valid number hai ya nahi
int invoiceNo = 0;
try {
    invoiceNo = Integer.parseInt(invoiceNoStr.trim());
} catch (NumberFormatException e) {
    javax.swing.JOptionPane.showMessageDialog(this, "Krupaya sahi Invoice Number dalein!");
    return;
}

// 2. User se double confirmation lena (Safety ke liye)
int confirmation = javax.swing.JOptionPane.showConfirmDialog(this, 
        "Kya aap sach me Invoice No: " + invoiceNo + " ko permanently delete karna chahte hain?", 
        "Confirm Delete", 
        javax.swing.JOptionPane.YES_NO_OPTION, 
        javax.swing.JOptionPane.WARNING_MESSAGE);

if (confirmation != javax.swing.JOptionPane.YES_OPTION) {
    return; // Agar No select kiya toh ruk jayein
}

Connection con = null;
PreparedStatement pstDetails = null;
PreparedStatement pstMaster = null;

try {
    con = MyConnection.getConnection();
    if (con == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Connection nahi mila! Database check karein.");
        return;
    }

    // 3. Transaction start (Auto-commit false) - Save code ki tarah safety ke liye
    con.setAutoCommit(false);

    // 4. STEP 1: Pehle Details Table se delete karein (Child Table)
    String deleteDetailsQuery = "DELETE FROM sale_invoice_details WHERE invoice_no = ?";
    pstDetails = con.prepareStatement(deleteDetailsQuery);
    pstDetails.setInt(1, invoiceNo);
    pstDetails.executeUpdate();

    // 5. STEP 2: Ab Master Table se delete karein (Master Table)
    String deleteMasterQuery = "DELETE FROM sales_invoices WHERE invoice_no = ?";
    pstMaster = con.prepareStatement(deleteMasterQuery);
    pstMaster.setInt(1, invoiceNo);
    int masterRowsAffected = pstMaster.executeUpdate();

    // 6. Check karein ki kya woh Invoice database me tha bhi ya nahi
    if (masterRowsAffected > 0) {
        // Success - Commit data permanently
        con.commit();
        javax.swing.JOptionPane.showMessageDialog(this, "Invoice No: " + invoiceNo + " successfully delete ho gaya!");
        
        // Form state ko reset aur update karne ke liye (Aapke save code ki tarah)
        datechose.setDate(new java.util.Date());
        cmbpmode.setSelectedItem("Cash");
        txtTotal.setText("0.00");
        
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        autoInvoiceNumber(); // Naya active invoice number update karne ke liye
        txtid.requestFocus();
    } else {
        // Agar record nahi mila toh rollback karein aur user ko batayein
        con.rollback();
        javax.swing.JOptionPane.showMessageDialog(this, "Yeh Invoice Number database me nahi mila!");
    }

} catch (Exception e) {
    // 7. Error aane par Rollback takki data safe rahe
    try {
        if (con != null) con.rollback();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    e.printStackTrace();
    javax.swing.JOptionPane.showMessageDialog(this, "Error while deleting: " + e.getMessage());
} finally {
    // 8. Connections safely close karein
    try {
        if (pstDetails != null) pstDetails.close();
        if (pstMaster != null) pstMaster.close();
        if (con != null) {
            con.setAutoCommit(true);
            con.close();
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

    }//GEN-LAST:event_btn_invoice_DeleteActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
    Connection con = null;
    PreparedStatement pstDeleteDetails = null;
    PreparedStatement pstDeleteMaster = null;

    // 1. Invoice Number fetch aur trim karein
    String invoiceNoStr = txt_invoice_no.getText().trim(); 

    // 2. Validation Check - Delete ke liye sirf Invoice Number zaroori hai
    if (invoiceNoStr.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Krupaya check karein: Invoice Number khali hai! Pehle koi invoice select ya search karein.");
        return;
    }

    // 3. User se confirmation lena (Bohat zaroori hai delete karne se pehle)
    int dialogResult = javax.swing.JOptionPane.showConfirmDialog(this, 
            "Kya aap pakka is Invoice No: " + invoiceNoStr + " ko delete karna chahte hain?", 
            "Warning", 
            javax.swing.JOptionPane.YES_NO_OPTION);
            
    if(dialogResult != javax.swing.JOptionPane.YES_OPTION){
        return; // Agar user NO select kare toh code yahin ruk jaye
    }

    try {
        // 4. Database Connection checked
        con = MyConnection.getConnection();
        if (con == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Connection nahi mila! Database check karein.");
            return;
        }

        // 5. Transaction start (Auto-commit false) - Dono tables ko safe rakhne ke liye
        con.setAutoCommit(false);

        // Number format safely check karein
        int targetInvoiceNo;
        try {
            targetInvoiceNo = Integer.parseInt(invoiceNoStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Invoice Number sahi number format me nahi hai!");
            return;
        }

        // 6. Pehle Detail Table se saare items delete karein (Foreign Key ki wajah se)
        String queryDeleteDetails = "DELETE FROM sale_invoice_details WHERE invoice_no = ?";
        pstDeleteDetails = con.prepareStatement(queryDeleteDetails);
        pstDeleteDetails.setInt(1, targetInvoiceNo);
        pstDeleteDetails.executeUpdate();

        // 7. Ab Main Master Table (sales_invoices) se record delete karein
        String queryDeleteMaster = "DELETE FROM sales_invoices WHERE invoice_no = ?";
        pstDeleteMaster = con.prepareStatement(queryDeleteMaster);
        pstDeleteMaster.setInt(1, targetInvoiceNo);
        
        int masterRowsAffected = pstDeleteMaster.executeUpdate();

        // Agar database me wo invoice number tha hi nahi
        if (masterRowsAffected == 0) {
            throw new Exception("Ye Invoice Number database me nahi mila!");
        }

        // 8. Success - Commit data permanently
        con.commit();
        javax.swing.JOptionPane.showMessageDialog(this, "Deleted Successfully! Invoice No: " + targetInvoiceNo);

        // 9. Fields aur Form reset karein
        datechose.setDate(new java.util.Date());
        cmbpmode.setSelectedItem("Cash");
        txtTotal.setText("0.00");
        txt_invoice_no.setText("");

        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        // Naye bill ke liye auto-number call karein aur focus sahi jagah karein
        autoInvoiceNumber(); 
        txtTotal.requestFocus(); 

    } catch (Exception e) {
        // 10. Error aane par Rollback (Agar aadha kaam hua toh wo wapas pehle jaisa ho jayega)
        try {
            if (con != null) con.rollback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    } finally {
        // 11. Connections safely close karein
        try {
            if (pstDeleteDetails != null) pstDeleteDetails.close();
            if (pstDeleteMaster != null) pstDeleteMaster.close();
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnserchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnserchActionPerformed
        // 1. नए फ़ॉर्म का ऑब्जेक्ट बनाएं
         NewJFrame1 newForm = new  NewJFrame1();

        // 2. नए फ़ॉर्म को स्क्रीन पर दिखाएं
        newForm.setVisible(true);

        // 3. (ऑप्शनल) अगर आप इस पुराने परचेज़ वाले फ़ॉर्म को बंद करना चाहते हैं:
        // this.dispose();
    }//GEN-LAST:event_btnserchActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
    Connection con = null;
    PreparedStatement pstMaster = null;
    PreparedStatement pstDetails = null;
    java.sql.ResultSet rsKeys = null; // Generated key store karne ke liye

    // 1. Inputs fetch aur trim karein
    String totalAmtStr = txtTotal.getText().trim();
    String payMode = cmbpmode.getSelectedItem() != null ? cmbpmode.getSelectedItem().toString().trim() : "";

    // 2. Validation Check (Invoice No check hata diya kyunki ab ye auto-increment hai)
    if (datechose.getDate() == null ||
        jTable1.getRowCount() == 0 ||
        totalAmtStr.isEmpty() ||
        totalAmtStr.equals("0") ||
        totalAmtStr.equals("0.0") ||
        totalAmtStr.equals("0.00") ||
        payMode.isEmpty()) {

        javax.swing.JOptionPane.showMessageDialog(this, "Krupaya check karein: Date, Table ya Total Amount khali to nahi hai?");
        return;
    }

    try {
        // 3. Database Connection checked
        con = MyConnection.getConnection();
        if (con == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Connection nahi mila! Database check karein.");
            return;
        }

        // 4. Transaction start (Auto-commit false)
        con.setAutoCommit(false);

        // Number format safely check karne ke liye
        double finalTotalAmt;
        try {
            finalTotalAmt = Double.parseDouble(totalAmtStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Total Amount sahi number format me nahi hai!");
            return;
        }

        // 5. Master Table Entry - FIXED: invoice_no hata diya aur RETURN_GENERATED_KEYS add kiya
        String query1 = "INSERT INTO sales_invoices (date, payment_mode, total_amount) VALUES (?, ?, ?)";
        pstMaster = con.prepareStatement(query1, java.sql.Statement.RETURN_GENERATED_KEYS);

        // Date Format safely database ke liye convert karein
        java.util.Date selectedDate = datechose.getDate();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(selectedDate);
        pstMaster.setString(1, formattedDate);

        pstMaster.setString(2, payMode);
        pstMaster.setDouble(3, finalTotalAmt);

        pstMaster.executeUpdate();

        // **Sabse Important Step**: Auto-Generated Invoice Number nikalna
        int generatedInvoiceNo = 0;
        rsKeys = pstMaster.getGeneratedKeys();
        if (rsKeys.next()) {
            generatedInvoiceNo = rsKeys.getInt(1); // Ye hai aapka naya auto-incremented invoice_no
        } else {
            throw new Exception("Invoice Number generate nahi ho paya!");
        }

        // 6. Detail Table (sale_invoice_details) Entry loop
        String query2 = "INSERT INTO sale_invoice_details (invoice_no, Item_id, item_name, quantity, sale_rate, sub_total) VALUES (?, ?, ?, ?, ?, ?)";
        pstDetails = con.prepareStatement(query2);

        int totalRows = jTable1.getRowCount();

        for (int i = 0; i < totalRows; i++) {
            // Table ke cell values ko safely handle karein
            String ItemIdStr = jTable1.getValueAt(i, 0) != null ? jTable1.getValueAt(i, 0).toString().trim() : "0";
            String itemName = jTable1.getValueAt(i, 1) != null ? jTable1.getValueAt(i, 1).toString().trim() : "";
            String qtyStr = jTable1.getValueAt(i, 2) != null ? jTable1.getValueAt(i, 2).toString().trim() : "0";
            String rateStr = jTable1.getValueAt(i, 3) != null ? jTable1.getValueAt(i, 3).toString().trim() : "0";
            String subTotalStr = jTable1.getValueAt(i, 4) != null ? jTable1.getValueAt(i, 4).toString().trim() : "0";

            // Row validation
            if(itemName.isEmpty()) {
                throw new Exception("Row " + (i+1) + " me Item Name khali hai!");
            }

            // Database se mili hui generatedInvoiceNo ko pehle parameter me daalein
            pstDetails.setInt(1, generatedInvoiceNo); 
            pstDetails.setInt(2, Integer.parseInt(ItemIdStr));
            pstDetails.setString(3, itemName);
            pstDetails.setInt(4, Integer.parseInt(qtyStr));
            pstDetails.setDouble(5, Double.parseDouble(rateStr));
            pstDetails.setDouble(6, Double.parseDouble(subTotalStr));

            pstDetails.addBatch();
        }

        pstDetails.executeBatch();

        // 7. Success - Commit data permanently
        con.commit();
        javax.swing.JOptionPane.showMessageDialog(this, "Saved Successfully!");

        // 8. Fields reset form state clear karein
        datechose.setDate(new java.util.Date());
        cmbpmode.setSelectedItem("Cash");
        txtTotal.setText("0.00");

        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        // Agli entry ke liye screen par naya number dikhana hai toh ye function chalayein
        autoInvoiceNumber(); 
        txtid.requestFocus(); 

    } catch (Exception e) {
        // 9. Error aane par Rollback
        try {
            if (con != null) con.rollback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    } finally {
        // 10. Connections safely close karein
        try {
            if (rsKeys != null) rsKeys.close();
            if (pstMaster != null) pstMaster.close();
            if (pstDetails != null) pstDetails.close();
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    
}

    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidActionPerformed
    String id = txtid.getText().trim();
// Agar ID field khali hai, toh fields clear karein aur ruk jayein
if (id.isEmpty()) {
    clearFields();
    return;
}

Connection con = null;
PreparedStatement pst = null;
ResultSet rs = null;

try {
    con = MyConnection.getConnection();

    if (con != null) {
        // 1. Database se Item details aur Stock Report se available_stock nikalna (Aapka Naya SQL Logic)
        String sql = "SELECT i.item_name, i.sale_rate, s.available_stock "
                   + "FROM items i "
                   + "LEFT JOIN stock_report s ON i.Item_id = s.item_id "
                   + "WHERE i.Item_id = ?";
        
        pst = con.prepareStatement(sql);
        pst.setString(1, id);

        rs = pst.executeQuery();

        if (rs.next()) {
            String itemName = rs.getString("item_name");
            double price = rs.getDouble("sale_rate");
            
            // Stock check karna
            int stock = 0;
            if (rs.getString("available_stock") != null) {
                stock = rs.getInt("available_stock");
            }

            // 🛑 VALIDATION: Agar stock 0 ya negative hai toh ruk jayein
            if (stock <= 0) {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Out Of Stock! Stock Negative", 
                    "", 
                    javax.swing.JOptionPane.WARNING_MESSAGE);
                txtid.setText("");
                txtid.requestFocus();
                return; 
            }

            int quantity = 1; // Direct enter par default quantity 1 rahegi
            double amount = quantity * price;

            // UI fields mein dikhane ke liye
            cmbItem.setSelectedItem(itemName);
            if (cmbItem.getSelectedIndex() == -1) {
                cmbItem.addItem(itemName);
                cmbItem.setSelectedItem(itemName);
            }
            txtqty.setText(String.valueOf(quantity));
            txtprice.setText(String.format("%.2f", price));
            txtAmount.setText(String.format("%.2f", amount));

            // 2. Table ka Model nikalna aur Duplicate check karna
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            int rowCount = model.getRowCount();
            boolean itemExists = false;

            for (int i = 0; i < rowCount; i++) {
                String tableId = model.getValueAt(i, 0).toString(); // Column 0: ID

                if (tableId.equals(id)) {
                    // Agar item pehle se table mein hai, toh sirf Quantity +1 karein
                    int oldQty = Integer.parseInt(model.getValueAt(i, 2).toString()); // Column 2: Qty
                    int newQty = oldQty + 1;

                    // 🛑 Stock Limit Validation Check
                    if (newQty > stock) {
                        javax.swing.JOptionPane.showMessageDialog(this, 
                            "Stock limit se zyada item add nahi ho sakta! Available: " + stock, 
                            "Warning", 
                            javax.swing.JOptionPane.WARNING_MESSAGE);
                        txtid.setText("");
                        txtid.requestFocus();
                        return;
                    }

                    double newAmount = newQty * price;

                    model.setValueAt(String.valueOf(newQty), i, 2); // Column 2: Qty update
                    model.setValueAt(String.format("%.2f", newAmount), i, 4); // Column 4: Amount update

                    itemExists = true;
                    break;
                }
            }

            // 3. Agar item table mein nahi tha, toh Nayi Row insert karein (Aapke exact columns ke hisab se)
            if (!itemExists) {
                model.addRow(new Object[]{
                    id,                                     // Column 0
                    itemName,                               // Column 1
                    String.valueOf(quantity),               // Column 2
                    String.format("%.2f", price),           // Column 3
                    String.format("%.2f", amount),          // Column 4
                    String.valueOf(stock)                   // Column 5: Available Stock
                });
            }

            // 4. Grand Total (Total Amount) nikalne ka loop
            double total = 0;
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                Object value = jTable1.getValueAt(i, 4); // Column 4: Amount
                if (value != null) {
                    total += Double.parseDouble(value.toString());
                }
            }

            // Total textbox mein set karein
            txtTotal.setText(String.format("%.2f", total));

            // 5. Naye item ke liye reset aur focus automatic ready karein
            txtid.setText("");
            txtprice.setText("");
            txtAmount.setText("");
            txtid.requestFocus();

        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Item ID nahi mili! Krupaya check karein.");
            txtid.selectAll();
            txtid.requestFocus();
        }
    }
} catch (Exception e) {
    e.printStackTrace();
    javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
} finally {
    try {
        if (rs != null) rs.close();
        if (pst != null) pst.close();
        if (con != null) con.close();
    } catch (Exception ex) {
        System.out.println("Error closing resources: " + ex.getMessage());
    }
}

    }//GEN-LAST:event_txtidActionPerformed

    private void txt_invoice_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_invoice_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_invoice_noActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
         jTable1.setRowHeight(35);
jTable1.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

// हेडर को लेफ्ट अलाइन (Left Align) करने के लिए
javax.swing.table.DefaultTableCellRenderer headerRenderer = (javax.swing.table.DefaultTableCellRenderer)
    jTable1.getTableHeader().getDefaultRenderer();
headerRenderer.setHorizontalAlignment(javax.swing.JLabel.LEFT);
    }//GEN-LAST:event_formWindowOpened

    private void btnSavePrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePrintActionPerformed
 Connection con = null;
    PreparedStatement pstMaster = null;
    PreparedStatement pstDetails = null;
    java.sql.ResultSet rsKeys = null; // Generated key store karne ke liye

    // 1. Inputs fetch aur trim karein
    String totalAmtStr = txtTotal.getText().trim();
    String payMode = cmbpmode.getSelectedItem() != null ? cmbpmode.getSelectedItem().toString().trim() : "";

    // 2. Validation Check (Invoice No check hata diya kyunki ab ye auto-increment hai)
    if (datechose.getDate() == null ||
        jTable1.getRowCount() == 0 ||
        totalAmtStr.isEmpty() ||
        totalAmtStr.equals("0") ||
        totalAmtStr.equals("0.0") ||
        totalAmtStr.equals("0.00") ||
        payMode.isEmpty()) {

        javax.swing.JOptionPane.showMessageDialog(this, "Krupaya check karein: Date, Table ya Total Amount khali to nahi hai?");
        return;
    }

    try {
        // 3. Database Connection checked
        con = MyConnection.getConnection();
        if (con == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Connection nahi mila! Database check karein.");
            return;
        }

        // 4. Transaction start (Auto-commit false)
        con.setAutoCommit(false);

        // Number format safely check karne ke liye
        double finalTotalAmt;
        try {
            finalTotalAmt = Double.parseDouble(totalAmtStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Total Amount sahi number format me nahi hai!");
            return;
        }

        // 5. Master Table Entry - FIXED: invoice_no hata diya aur RETURN_GENERATED_KEYS add kiya
        String query1 = "INSERT INTO sales_invoices (date, payment_mode, total_amount) VALUES (?, ?, ?)";
        pstMaster = con.prepareStatement(query1, java.sql.Statement.RETURN_GENERATED_KEYS);

        // Date Format safely database ke liye convert karein
        java.util.Date selectedDate = datechose.getDate();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(selectedDate);
        pstMaster.setString(1, formattedDate);

        pstMaster.setString(2, payMode);
        pstMaster.setDouble(3, finalTotalAmt);

        pstMaster.executeUpdate();

        // **Sabse Important Step**: Auto-Generated Invoice Number nikalna
        int generatedInvoiceNo = 0;
        rsKeys = pstMaster.getGeneratedKeys();
        if (rsKeys.next()) {
            generatedInvoiceNo = rsKeys.getInt(1); // Ye hai aapka naya auto-incremented invoice_no
        } else {
            throw new Exception("Invoice Number generate nahi ho paya!");
        }

        // 6. Detail Table (sale_invoice_details) Entry loop
        String query2 = "INSERT INTO sale_invoice_details (invoice_no, Item_id, item_name, quantity, sale_rate, sub_total) VALUES (?, ?, ?, ?, ?, ?)";
        pstDetails = con.prepareStatement(query2);

        int totalRows = jTable1.getRowCount();

        for (int i = 0; i < totalRows; i++) {
            // Table ke cell values ko safely handle karein
            String ItemIdStr = jTable1.getValueAt(i, 0) != null ? jTable1.getValueAt(i, 0).toString().trim() : "0";
            String itemName = jTable1.getValueAt(i, 1) != null ? jTable1.getValueAt(i, 1).toString().trim() : "";
            String qtyStr = jTable1.getValueAt(i, 2) != null ? jTable1.getValueAt(i, 2).toString().trim() : "0";
            String rateStr = jTable1.getValueAt(i, 3) != null ? jTable1.getValueAt(i, 3).toString().trim() : "0";
            String subTotalStr = jTable1.getValueAt(i, 4) != null ? jTable1.getValueAt(i, 4).toString().trim() : "0";

            // Row validation
            if(itemName.isEmpty()) {
                throw new Exception("Row " + (i+1) + " me Item Name khali hai!");
            }

            // Database se mili hui generatedInvoiceNo ko pehle parameter me daalein
            pstDetails.setInt(1, generatedInvoiceNo); 
            pstDetails.setInt(2, Integer.parseInt(ItemIdStr));
            pstDetails.setString(3, itemName);
            pstDetails.setInt(4, Integer.parseInt(qtyStr));
            pstDetails.setDouble(5, Double.parseDouble(rateStr));
            pstDetails.setDouble(6, Double.parseDouble(subTotalStr));

            pstDetails.addBatch();
        }

        pstDetails.executeBatch();

        // 7. Success - Commit data permanently
             con.commit();
        javax.swing.JOptionPane.showMessageDialog(this, "Saved Successfully!");

        // 🔥 AAPKE USI FORM SE YAHAN CALL HOGA 🔥
        printJasperInvoice(generatedInvoiceNo);

        // 8. Fields reset form state clear karein (Aapka purana baki code...)
        datechose.setDate(new java.util.Date());

        // 8. Fields reset form state clear karein
        datechose.setDate(new java.util.Date());
        cmbpmode.setSelectedItem("Cash");
        txtTotal.setText("0.00");

        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        // Agli entry ke liye screen par naya number dikhana hai toh ye function chalayein
        autoInvoiceNumber(); 
        txtid.requestFocus(); 

    } catch (Exception e) {
        // 9. Error aane par Rollback
        try {
            if (con != null) con.rollback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    } finally {
        // 10. Connections safely close karein
        try {
            if (rsKeys != null) rsKeys.close();
            if (pstMaster != null) pstMaster.close();
            if (pstDetails != null) pstDetails.close();
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }     
    }//GEN-LAST:event_btnSavePrintActionPerformed

    private void btn_total_daywiseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_total_daywiseActionPerformed
                                                  
    // 1. DateChooser se selected date nikalna
    if (datechose.getDate() == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Krupaya pehle koi Date select karein!");
        return;
    }

    try {
        java.sql.Connection connection = MyConnection.getConnection();
        
        // Date format convert karna database compatibility ke liye (yyyy-MM-dd)
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String selectedDateStr = sdf.format(datechose.getDate());
        
        // 2. Nayi day wise XML file ko stream se load karna
        java.io.InputStream reportStream = getClass().getResourceAsStream("/daysale.jrxml");
        if (reportStream == null) {
            reportStream = new java.io.FileInputStream(new java.io.File("src/daysale.jrxml"));
        }
        
        if (reportStream == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: daysale.jrxml file nahi mili!");
            return;
        }
        
        // 3. Report compile karna
        net.sf.jasperreports.engine.JasperReport jasperReport = 
            net.sf.jasperreports.engine.JasperCompileManager.compileReport(reportStream);
        
        // 4. Parameter pass karna (Yahan hum 'sale_date' pass kar rahe hain)
        java.util.Map<String, Object> parameters = new java.util.HashMap<>();
        parameters.put("sale_date", selectedDateStr);
        
        // 5. Data fill karna
        net.sf.jasperreports.engine.JasperPrint jasperPrint = 
            net.sf.jasperreports.engine.JasperFillManager.fillReport(jasperReport, parameters, connection);
        
        // 6. View Report (Iske liye screen par full preview popup aana chahiye)
        net.sf.jasperreports.view.JasperViewer.viewReport(jasperPrint, false);
        
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Report Error: " + e.getMessage());
    }


    }//GEN-LAST:event_btn_total_daywiseActionPerformed

    private void printJasperInvoice(int invoiceNo) {
   
    try {
        // 1. Database connection lena
        java.sql.Connection connection = MyConnection.getConnection();
        
        // 🔥 BADLAV YAHAN HAI: Is line ko fresh input stream load karne ke liye yahan dalein 🔥
        java.io.InputStream reportStream = getClass().getResourceAsStream("/receipt.jrxml");
        if (reportStream == null) {
            reportStream = new java.io.FileInputStream(new java.io.File("src/receipt.jrxml"));
        }
        
        // 2. Agar dono jagah file na mile toh check karein
        if (reportStream == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: receipt.jrxml file nahi mili!");
            return;
        }
        
        // 3. Report design compile karna
        net.sf.jasperreports.engine.JasperReport jasperReport = 
            net.sf.jasperreports.engine.JasperCompileManager.compileReport(reportStream);
        
        // 4. Invoice number ko parameter me pass karna
        java.util.Map<String, Object> parameters = new java.util.HashMap<>();
        parameters.put("invoice_no", invoiceNo);
        
        // 5. Data fill karna
        net.sf.jasperreports.engine.JasperPrint jasperPrint = 
            net.sf.jasperreports.engine.JasperFillManager.fillReport(jasperReport, parameters, connection);
        
        // 6. Direct Print Command
        net.sf.jasperreports.engine.JasperPrintManager.printReport(jasperPrint, false);
        
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Print Error: " + e.getMessage());
    }


}

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
        java.awt.EventQueue.invokeLater(() -> new Sale().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSavePrint;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btn_invoice_Delete;
    private javax.swing.JButton btn_total_daywise;
    private javax.swing.JButton btnback;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnserch;
    private javax.swing.JComboBox<String> cmbItem;
    private javax.swing.JComboBox<String> cmbpmode;
    private com.toedter.calendar.JDateChooser datechose;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txt_invoice_no;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtprice;
    private javax.swing.JTextField txtqty;
    // End of variables declaration//GEN-END:variables
}
