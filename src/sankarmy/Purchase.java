
package sankarmy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class Purchase extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Purchase.class.getName());

    public static Purchase currentInstance = null;
    public Purchase() {
        initComponents();
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
         currentInstance = this; 
        autoInvoiceNumber();
        loadItemsToCombo();
        txtid.requestFocus();
        cmbItem.setSelectedIndex(-1);
        txtprice.setText("0.00");
        txtAmount.setText("0.00");
        txtsale.setText("0.00");
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
        String sql = "SELECT m.invoice_no, m.party_name, m.date, m.payment_mode, m.total_amount, " +
                     "d.item_id, d.item_name, d.quantity, d.purchase_rate, d.sub_total " +
                     "FROM invoices m " +
                     "INNER JOIN invoice_details d ON m.invoice_no = d.invoice_no " +
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
                cmbpartyname.setSelectedItem(rs.getString("party_name")); 
                
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
                rs.getString("item_id"),
                rs.getString("item_name"),
                rs.getString("quantity"),
                rs.getString("purchase_rate"),
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
        jLabel1 = new javax.swing.JLabel();
        txt_invoice_no = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbpartyname = new javax.swing.JComboBox<>();
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
        jLabel9 = new javax.swing.JLabel();
        txtsale = new javax.swing.JTextField();
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
        btnitemdelete = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnserch = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1610, 785));
        setMinimumSize(new java.awt.Dimension(750, 768));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("Party Name");

        txt_invoice_no.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_invoice_no.setForeground(new java.awt.Color(204, 0, 0));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 0));
        jLabel2.setText("Invoice No");

        cmbpartyname.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        cmbpartyname.setForeground(new java.awt.Color(204, 0, 0));
        cmbpartyname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sankar", "Maa Bhagabati" }));

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

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 0, 0));
        jLabel9.setText("Sale Price");

        txtsale.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txtsale.setForeground(new java.awt.Color(204, 0, 0));

        btnAdd.setIcon(new javax.swing.ImageIcon("C:\\Users\\dell\\OneDrive\\Documents\\NetBeansProjects\\Sankarmynew\\src\\sankarmy\\plus_4604818.png")); // NOI18N
        btnAdd.addActionListener(this::btnAddActionPerformed);

        jTable1.setBackground(new java.awt.Color(242, 242, 242));
        jTable1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jTable1.setForeground(new java.awt.Color(255, 51, 51));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Item Name", "Qty", "Price", "Amonut"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false
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
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

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

        btnitemdelete.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnitemdelete.setForeground(new java.awt.Color(204, 0, 0));
        btnitemdelete.setText("Delete Invoice");
        btnitemdelete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnitemdelete.addActionListener(this::btnitemdeleteActionPerformed);

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

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 0, 0));
        jLabel12.setText("Purchase invoice barcode");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_invoice_no, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbpartyname, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbItem, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtprice, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtsale, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(datechose, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(71, 71, 71))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnserch, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnitemdelete, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnback, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(540, 540, 540)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbpmode, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(577, 577, 577)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_invoice_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbpartyname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(datechose, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtqty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtprice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtsale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbpmode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnback)
                    .addComponent(jButton4)
                    .addComponent(btnitemdelete)
                    .addComponent(btndelete)
                    .addComponent(btnserch)
                    .addComponent(btnSave))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            // आइटम नेम के आधार पर डेटाबेस से ID और बाकी डिटेल्स निकालना
            String sql = "SELECT Item_id, Purchase_rate, sale_rate FROM items WHERE item_name = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, selectedItem);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                // ID को ID फ़ील्ड में सेट करना (अपने सही वेरिएबल नाम जैसे txtId या txtid लिखें)
                txtid.setText(rs.getString("Item_id"));
                
                // बाकी फ़ील्ड्स को भी अपडेट करना
                txtqty.setText("1");
                txtprice.setText(rs.getString("Purchase_rate"));
                txtsale.setText(rs.getString("sale_rate"));
                
                calculateAmount();
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        jTable1.setRowHeight(35);
jTable1.getTableHeader().setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

// हेडर को लेफ्ट अलाइन (Left Align) करने के लिए
javax.swing.table.DefaultTableCellRenderer headerRenderer = (javax.swing.table.DefaultTableCellRenderer)
    jTable1.getTableHeader().getDefaultRenderer();
headerRenderer.setHorizontalAlignment(javax.swing.JLabel.LEFT);
    }//GEN-LAST:event_formWindowOpened

    private void txtidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidKeyReleased
         String id = txtid.getText().trim();
    
    //agar id field khali nehi he,tabhi serch kare
    if (!id.isEmpty()) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            //my connction class se conet kia
            con = MyConnection.getConnection();
            
            if (con != null) {
                // 2. SQL Query tayari karo table ka nam 'product' man kar)
                String sql = "SELECT item_name,Purchase_rate, sale_rate FROM items WHERE Item_id = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, id);
                
                // 3. Query ko Execute karo
                rs = pst.executeQuery();
                
                if (rs.next()) {
                    // Database deta lakar apki UI fields me bharana
            String itemName = rs.getString("item_name");
    cmbItem.setSelectedItem(itemName);
    if (cmbItem.getSelectedIndex() == -1) {
        cmbItem.addItem(itemName);
        cmbItem.setSelectedItem(itemName);
    }
                    txtqty.setText("1");
                    txtprice.setText(rs.getString("Purchase_rate"));
                    txtsale.setText(rs.getString("sale_rate"));
                    
                    calculateAmount();
                } else {
                    // agar id puri tar hata dia jae,to sab clear kare 
                    clearFields();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
                try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close(); 
            } catch (Exception ex) {
                System.out.println("Error closing resources: " + ex.getMessage());
            }
        }
    } else {
               clearFields();
    }
   }
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

// फ़ील्ड्स को खाली करने के लिए एक अलग हेल्पर मेथड (ताकि कोड साफ़ रहे)
private void clearFields() {
    cmbItem.setSelectedItem("");
    txtqty.setText("");
    txtprice.setText("0.00");
    txtsale.setText("0.00");
                                 

    }//GEN-LAST:event_txtidKeyReleased

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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        Connection con = null;
    PreparedStatement pstMaster = null;
    PreparedStatement pstDetails = null;

    // 1. Inputs fetch aur trim karein
    String invoiceNoStr = txt_invoice_no.getText().trim();
    String totalAmtStr = txtTotal.getText().trim();
    String partyName = cmbpartyname.getSelectedItem() != null ? cmbpartyname.getSelectedItem().toString().trim() : "";
    String payMode = cmbpmode.getSelectedItem() != null ? cmbpmode.getSelectedItem().toString().trim() : "";

    // 2. Validation Check
    if (invoiceNoStr.isEmpty() || 
        partyName.isEmpty() || 
        partyName.equalsIgnoreCase("Select Party") || 
        datechose.getDate() == null || 
        jTable1.getRowCount() == 0 || 
        totalAmtStr.isEmpty() || 
        totalAmtStr.equals("0") || 
        totalAmtStr.equals("0.0") || 
        totalAmtStr.equals("0.00") || 
        payMode.isEmpty()) {
        
        javax.swing.JOptionPane.showMessageDialog(this, "Krupaya check karein: Invoice No, Party Name, Date, Table ya Total Amount khali to nahi hai?");
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
        int finalInvoiceNo;
        double finalTotalAmt;
        try {
            finalInvoiceNo = Integer.parseInt(invoiceNoStr);
            finalTotalAmt = Double.parseDouble(totalAmtStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Invoice Number ya Total Amount sahi number format me nahi hai!");
            return;
        }

        // 5. Master Table (invoices) Entry
        String query1 = "INSERT INTO invoices (invoice_no, party_name, date, payment_mode, total_amount) VALUES (?, ?, ?, ?, ?)";
        pstMaster = con.prepareStatement(query1);

        pstMaster.setInt(1, finalInvoiceNo); 
        pstMaster.setString(2, partyName);

        // Date Format safely database ke liye convert karein
        java.util.Date selectedDate = datechose.getDate();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(selectedDate);
        pstMaster.setString(3, formattedDate);

        pstMaster.setString(4, payMode);
        pstMaster.setDouble(5, finalTotalAmt);

        pstMaster.executeUpdate(); 

        // 6. Detail Table (invoice_details) Entry loop
        String query2 = "INSERT INTO invoice_details (invoice_no, Item_id, item_name, quantity,purchase_rate, sub_total) VALUES (?, ?, ?, ?, ?, ?)";
        pstDetails = con.prepareStatement(query2);

        int totalRows = jTable1.getRowCount();

        for (int i = 0; i < totalRows; i++) {
            // Table ke cell values ko safely handle karein (Null check ke sath)
            String ItemIdStr = jTable1.getValueAt(i, 0) != null ? jTable1.getValueAt(i, 0).toString().trim() : "0";
            String itemName = jTable1.getValueAt(i, 1) != null ? jTable1.getValueAt(i, 1).toString().trim() : "";
            String qtyStr = jTable1.getValueAt(i, 2) != null ? jTable1.getValueAt(i, 2).toString().trim() : "0";
            String rateStr = jTable1.getValueAt(i, 3) != null ? jTable1.getValueAt(i, 3).toString().trim() : "0";
            String subTotalStr = jTable1.getValueAt(i, 4) != null ? jTable1.getValueAt(i, 4).toString().trim() : "0";

            // Row validation
            if(itemName.isEmpty()) {
                throw new Exception("Row " + (i+1) + " me Item Name khali hai!");
            }

            pstDetails.setInt(1, finalInvoiceNo); 
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
        javax.swing.JOptionPane.showMessageDialog(this, "Invoice Saved Successfully");
        
        // 8. Fields reset form state clear karein
        datechose.setDate(new java.util.Date()); 
        cmbpmode.setSelectedItem("Cash");         
        cmbpartyname.setSelectedItem("Sankar");   
        txtTotal.setText("0.00");
        
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        // Next invoice number aur focus set karein
        autoInvoiceNumber();
        txtid.requestFocus();

    } catch (Exception e) {
        // 9. Error aane par Rollback (Dono table safe rahengi)
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
            if (pstMaster != null) pstMaster.close();
            if (pstDetails != null) pstDetails.close();
            if (con != null) {
                con.setAutoCommit(true); // Default status wapas karein
                con.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
          Connection con = null;
    PreparedStatement pstMaster = null;
    PreparedStatement pstDeleteDetails = null;
    PreparedStatement pstInsertDetails = null;

    // 1. Inputs fetch aur trim karein
    String invoiceNoStr = txt_invoice_no.getText().trim();
    String totalAmtStr = txtTotal.getText().trim();
    String partyName = cmbpartyname.getSelectedItem() != null ? cmbpartyname.getSelectedItem().toString().trim() : "";
    String payMode = cmbpmode.getSelectedItem() != null ? cmbpmode.getSelectedItem().toString().trim() : "";

    // 2. Validation Check
    if (invoiceNoStr.isEmpty() || 
        partyName.isEmpty() || 
        partyName.equalsIgnoreCase("Select Party") || 
        datechose.getDate() == null || 
        jTable1.getRowCount() == 0 || 
        totalAmtStr.isEmpty() || 
        totalAmtStr.equals("0") || 
        totalAmtStr.equals("0.0") || 
        totalAmtStr.equals("0.00") || 
        payMode.isEmpty()) {
        
        javax.swing.JOptionPane.showMessageDialog(this, "Krupaya check karein: Sabhi fields aur table details sahi se bhari honi chahiye!");
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

        // Number format safely check karein
        int finalInvoiceNo;
        double finalTotalAmt;
        try {
            finalInvoiceNo = Integer.parseInt(invoiceNoStr);
            finalTotalAmt = Double.parseDouble(totalAmtStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Invoice Number ya Total Amount ka format sahi nahi hai!");
            return;
        }

        // 5. Master Table (invoices) Update Query
        String updateMasterQuery = "UPDATE invoices SET party_name = ?, date = ?, payment_mode = ?, total_amount = ? WHERE invoice_no = ?";
        pstMaster = con.prepareStatement(updateMasterQuery);

        pstMaster.setString(1, partyName);

        // Date Format Handling
        java.util.Date selectedDate = datechose.getDate();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(selectedDate);
        pstMaster.setString(2, formattedDate);

        pstMaster.setString(3, payMode);
        pstMaster.setDouble(4, finalTotalAmt);
        pstMaster.setInt(5, finalInvoiceNo); // WHERE clause ke liye

        int masterRowsAffected = pstMaster.executeUpdate();
        if (masterRowsAffected == 0) {
            throw new Exception("Ye Invoice Number database me nahi mila!");
        }

        // 6. Detail Table ke purane records pehle DELETE karein
        String deleteDetailsQuery = "DELETE FROM invoice_details WHERE invoice_no = ?";
        pstDeleteDetails = con.prepareStatement(deleteDetailsQuery);
        pstDeleteDetails.setInt(1, finalInvoiceNo);
        pstDeleteDetails.executeUpdate();

        // 7. Detail Table me naye/updated items batch me INSERT karein
        String insertDetailsQuery = "INSERT INTO invoice_details (invoice_no, Item_id, item_name, quantity,purchase_rate, sub_total) VALUES (?, ?, ?, ?, ?, ?)";
        pstInsertDetails = con.prepareStatement(insertDetailsQuery);

        int totalRows = jTable1.getRowCount();

        for (int i = 0; i < totalRows; i++) {
            // Null check ke sath table values nikalien
            String ItemIdStr = jTable1.getValueAt(i, 0) != null ? jTable1.getValueAt(i, 0).toString().trim() : "0";
            String itemName = jTable1.getValueAt(i, 1) != null ? jTable1.getValueAt(i, 1).toString().trim() : "";
            String qtyStr = jTable1.getValueAt(i, 2) != null ? jTable1.getValueAt(i, 2).toString().trim() : "0";
            String rateStr = jTable1.getValueAt(i, 3) != null ? jTable1.getValueAt(i, 3).toString().trim() : "0";
            String subTotalStr = jTable1.getValueAt(i, 4) != null ? jTable1.getValueAt(i, 4).toString().trim() : "0";

            if(itemName.isEmpty()) {
                throw new Exception("Row " + (i+1) + " me Item Name khali hai!");
            }

            pstInsertDetails.setInt(1, finalInvoiceNo); 
            pstInsertDetails.setInt(2, Integer.parseInt(ItemIdStr));    
            pstInsertDetails.setString(3, itemName);                 
            pstInsertDetails.setInt(4, Integer.parseInt(qtyStr));    
            pstInsertDetails.setDouble(5, Double.parseDouble(rateStr)); 
            pstInsertDetails.setDouble(6, Double.parseDouble(subTotalStr)); 

            pstInsertDetails.addBatch(); 
        }

        pstInsertDetails.executeBatch(); 
        
        // 8. Success - Commit transaction permanently
        con.commit(); 
        javax.swing.JOptionPane.showMessageDialog(this, "Invoice Updated Successfully");
        
        // 9. Fields aur Form state clear karein
        datechose.setDate(new java.util.Date()); 
        cmbpmode.setSelectedItem("Cash");         
        cmbpartyname.setSelectedItem("Sankar");   
        txtTotal.setText("0.00");
        txt_invoice_no.setText(""); // Invoice no clear karein taaki naya auto generate ho sake
        
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        // Naya number generate karein aur focus shift karein
        autoInvoiceNumber();
        txtid.requestFocus();

    } catch (Exception e) {
        // 10. Kuch bhi fail hone par ROLLBACK karein (Data purana hi surakshit rahega)
        try {
            if (con != null) con.rollback(); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    } finally {
        // 11. Connections properly close karein
        try {
            if (pstMaster != null) pstMaster.close();
            if (pstDeleteDetails != null) pstDeleteDetails.close();
            if (pstInsertDetails != null) pstInsertDetails.close();
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
        String query = "SELECT MAX(CAST(invoice_no AS UNSIGNED)) FROM invoices";
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

    private void txtqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtyKeyReleased
      calculateAmount();
    }//GEN-LAST:event_txtqtyKeyReleased

    private void btnserchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnserchActionPerformed
          // 1. नए फ़ॉर्म का ऑब्जेक्ट बनाएं
    NewJFrame newForm = new  NewJFrame();
    
    // 2. नए फ़ॉर्म को स्क्रीन पर दिखाएं
    newForm.setVisible(true);
    
    // 3. (ऑप्शनल) अगर आप इस पुराने परचेज़ वाले फ़ॉर्म को बंद करना चाहते हैं:
    // this.dispose(); 
    }//GEN-LAST:event_btnserchActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
          // 1. DefaultTableModel nikalen row remove karne ke liye
    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();

    // 2. Kaunsi row select hui hai uska index nikalen
    int selectedRowIndex = jTable1.getSelectedRow();

    // 3. Validation: Check karein ki user ne table me koi row select ki hai ya nahi
    if (selectedRowIndex == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Krupaya delete karne ke liye table se ek row select karein!");
        return;
    }

    // 4. User se confirmation lein
    int dialogResult = javax.swing.JOptionPane.showConfirmDialog(
        this,
        "Do you want to delete this row?",
        "Confirm Delete",
        javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (dialogResult == javax.swing.JOptionPane.YES_OPTION) {
        // 5. Table se selected row delete karein
        model.removeRow(selectedRowIndex);

        // 6. Total Amount recalculate karein
        double newTotal = 0.0;
        int totalRows = jTable1.getRowCount();

        for (int i = 0; i < totalRows; i++) {
            // Column 4 (Sub-Total) se value safely nikalen
            String subTotalStr = jTable1.getValueAt(i, 4) != null ? jTable1.getValueAt(i, 4).toString().trim() : "0";
            
            try {
                newTotal += Double.parseDouble(subTotalStr);
            } catch (NumberFormatException e) {
                // Invalid data ho toh skip karein
            }
        }

        // 7. Naye total ko txtTotal field me dikhayein
        txtTotal.setText(String.format("%.2f", newTotal));
    }



    }//GEN-LAST:event_btndeleteActionPerformed

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
    cmbpartyname.setSelectedItem("Sankar");   

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
                "Do You Went To Delete", 
                "Confirm Delete", 
                javax.swing.JOptionPane.YES_NO_OPTION
            );
            
            if (dialogResult == javax.swing.JOptionPane.YES_OPTION) {
                // Table se row delete karein
                model.removeRow(selectedRowIndex);
                
                // ERROR/MISMATCH SE BACHNE KE LIYE: Row delete hote hi Total Amount recalculate karein
                updateTotalAmount(); 
            }
        }
    }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnitemdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnitemdeleteActionPerformed
         // 1. Screen par dikh rahe txtid textfield se Invoice Number fetch aur trim karein
    String invoiceNoStr = txt_invoice_no.getText().trim();

    // Validation Check: Khali toh nahi hai?
    if (invoiceNoStr.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Screen par koi Invoice Number nahi mila jise delete kiya ja sake!");
        return;
    }

    // String ko Integer me convert karein safely
    int invoiceNo = 0;
    try {
        invoiceNo = Integer.parseInt(invoiceNoStr);
        if (invoiceNo <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Galat Invoice Number! Krupaya pehle koi valid invoice load karein.");
            return;
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Invoice ID sahi number format me nahi hai!");
        return;
    }

    // 2. User se double confirmation lena (Safety ke liye bahut zaroori hai)
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

        // 3. Transaction start (Auto-commit false) - Safety ke liye
        con.setAutoCommit(false);

        // 4. STEP 1: Pehle Details Table se delete karein (Child Table)
        String deleteDetailsQuery = "DELETE FROM invoice_details WHERE invoice_no = ?";
        pstDetails = con.prepareStatement(deleteDetailsQuery);
        pstDetails.setInt(1, invoiceNo);
        pstDetails.executeUpdate();

        // 5. STEP 2: Ab Master Table se delete karein (Master Table)
        String deleteMasterQuery = "DELETE FROM invoices WHERE invoice_no = ?";
        pstMaster = con.prepareStatement(deleteMasterQuery);
        pstMaster.setInt(1, invoiceNo);
        int masterRowsAffected = pstMaster.executeUpdate();

        // 6. Check karein ki kya woh Invoice database me tha bhi ya nahi
        if (masterRowsAffected > 0) {
            // Success - Commit data permanently
            con.commit();
            javax.swing.JOptionPane.showMessageDialog(this, "Invoice No: " + invoiceNo + " successfully delete ho gaya!");
            
            // Form state ko reset aur update karne ke liye
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
    }//GEN-LAST:event_btnitemdeleteActionPerformed

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbackActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnbackActionPerformed

    private void updateTotalAmount() {
    double grandTotal = 0.0;
    int rowCount = jTable1.getRowCount();
    
    for (int i = 0; i < rowCount; i++) {
        // Aapke table mein Amount 4th index (0, 1, 2, 3, 4) par hai, use uthayein
        double rowAmount = Double.parseDouble(jTable1.getValueAt(i, 4).toString());
        grandTotal += rowAmount;
    }
    
    // Naya total textbox mein set karein
    txtTotal.setText(String.valueOf(grandTotal)); 
}
   
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
        java.awt.EventQueue.invokeLater(() -> new Purchase().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnback;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnitemdelete;
    private javax.swing.JButton btnserch;
    private javax.swing.JComboBox<String> cmbItem;
    private javax.swing.JComboBox<String> cmbpartyname;
    private javax.swing.JComboBox<String> cmbpmode;
    private com.toedter.calendar.JDateChooser datechose;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txt_invoice_no;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtprice;
    private javax.swing.JTextField txtqty;
    private javax.swing.JTextField txtsale;
    // End of variables declaration//GEN-END:variables
}
