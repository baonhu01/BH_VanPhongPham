/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import qlbh.dao.SQLServerDataProvider;
import javax.swing.table.TableModel;

/**
 *
 * @author Bao Nhu
 */
public class BanHangJPanel extends javax.swing.JPanel {

    private Connection conn = null;  
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    
    private boolean them=false, thanhToan=false, Pay = false;
    private String sql = "SELECT * FROM chitiethd";
    /**
     * Creates new form BanHangJPanel
     */
    public BanHangJPanel() {
        initComponents();
        setData();
        Update();
        
        connection();
        Pays();
        Load(sql);
        checkBill();
    }

    private void setData(){
        Disabled();
        lbDate.setText(String.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())));
    }
    
    private void Update(){
        lbTime.setText(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date())));
    }
    
    private void LoadLoaiSanPham(){
        try {
            String sql = "SELECT * FROM LoaiHH";
            SQLServerDataProvider provider = new SQLServerDataProvider();
            provider.open();
            ResultSet resultSet = provider.excuteQuery(sql);
            while(resultSet.next()){
                this.cbbLoaiSanPham.addItem(resultSet.getString("maloai").trim());
            }
            provider.Close();
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    private void connection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn=DriverManager.getConnection("jdbc:sqlserver://DESKTOP-Q1HN2VJ\\SQLEXPRESS:1433;databaseName=QL_BHTVPP;user=sa;password=123");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void Disabled(){
        cbbLoaiSanPham.setEnabled(false);
        txtSoLuong.setEnabled(false);
        cbbTenSanPham.setEnabled(false);
    }
    
    private void Refresh(){
        them=false;
        thanhToan=false;
        txtMaSanPham.setText("");
        txtGia.setText("");
        txtSoLuong.setText("");
        txtThanhTien.setText("");
        txtTienNhan.setText("");
        lbTienDu.setText("0 VND");
        cbbTenSanPham.removeAllItems();
        cbbLoaiSanPham.removeAllItems();
        btXoa.setEnabled(false);
        btLuu.setEnabled(false);
        Disabled();
    }
    
    private void Enabled(){
        cbbLoaiSanPham.setEnabled(true);
    }
    
    private void checkProducts(){
        String sqlCheck="SELECT soluong FROM hanghoa WHERE mahh='"+txtMaSanPham.getText()+"'";
        try{
            pst=conn.prepareCall(sqlCheck);
            rs=pst.executeQuery();
            while(rs.next()){
                if(rs.getInt("soluong")==0){
                    lbTrangThai.setText("Sản phẩm này đã hết hàng!!");
                    btLuu.setEnabled(false);
                    txtSoLuong.setEnabled(false);
                }
                else{
                    lbTrangThai.setText("Mặt hàng này còn "+rs.getInt("soluong")+" sản phẩm!!");
                    btLuu.setEnabled(true);
                    txtSoLuong.setEnabled(true);
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private boolean Check() {
        boolean kq=true;
        String sqlCheck="SELECT * FROM chitiethd";
        try{
            PreparedStatement pstCheck=conn.prepareStatement(sqlCheck);
            ResultSet rsCheck=pstCheck.executeQuery();
            while(rsCheck.next()){
                if(this.txtMaSanPham.getText().equals(rsCheck.getString("mahh").toString().trim())){
                    return false;                                           
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }
    
    private boolean checkNull(){
        boolean kq=true;
        if(String.valueOf(this.txtMaSanPham.getText()).length()==0){
            lbTrangThai.setText("Bạn chưa chọn sản phẩm!");
            return false;
        }
        else if(String.valueOf(this.txtSoLuong.getText()).length()==0){
                lbTrangThai.setText("Bạn chưa nhập số lượng sản phẩm!");
                return false;
        }
        return kq;
    }
    
    private void Sucessful(){
        btLuu.setEnabled(false);
        btThem.setEnabled(true);
        btHoaDonMoi.setEnabled(true);
        btXoa.setEnabled(true);
    }
    
    private void Load(String sql){
        tbSanPham.removeAll();
        try{
            String [] arr={"Mã Sản Phẩm","Tên Sản Phẩm","Số Lượng","Tổng Tiền"};
            DefaultTableModel modle=new DefaultTableModel(arr,0);
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("mahh").trim());
                vector.add(rs.getString("tenhh").trim());
                vector.add(rs.getInt("soluong"));
                vector.add(rs.getString("dongia").trim());
                modle.addRow(vector);
            }
            tbSanPham.setModel(modle);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void themHangHoa() {
        if(checkNull()){
            String sqlInsert="INSERT INTO chitiethd (mahh,tenhh,soluong,dongia) VALUES(?,?,?,?)";
            try{
                pst=conn.prepareStatement(sqlInsert);
                pst.setString(1, String.valueOf(txtMaSanPham.getText()));
                pst.setString(2, String.valueOf(cbbTenSanPham.getSelectedItem()));
                pst.setInt(3, Integer.parseInt(txtSoLuong.getText()));
                pst.setString(4, txtThanhTien.getText());
                pst.executeUpdate();
                lbTrangThai.setText("Thêm sản phẩm thành công!");
                Disabled();
                Sucessful();
                Load(sql);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private void checkBill(){
        if(tbSanPham.getRowCount()==0){
            lbTongTienHoaDon.setText("0 VND");
            txtTienNhan.setText("");
            lbTienDu.setText("0 VND");
            btThanhToan.setEnabled(false);
            txtTienNhan.setEnabled(false);
        }
        else {
            btThanhToan.setEnabled(true);
            txtTienNhan.setEnabled(true);
        }
    }
    
    private void Pays(){
        lbTongTienHoaDon.setText("0 VND");
        String sqlPay="SELECT * FROM chitiethd";
        try{
            pst=conn.prepareStatement(sqlPay);
            rs=pst.executeQuery();
            while(rs.next()){
                String []s1=rs.getString("dongia").toString().trim().split("\\s");
                String []s2=lbTongTienHoaDon.getText().split("\\s");
                double tongTien=Double.parseDouble(s1[0])+ Double.parseDouble(s2[0]);
                
                lbTongTienHoaDon.setText((tongTien)+" "+ " VND");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbbLoaiSanPham = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cbbTenSanPham = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btXoa = new javax.swing.JButton();
        btLuu = new javax.swing.JButton();
        btHoaDonMoi = new javax.swing.JButton();
        btThanhToan = new javax.swing.JButton();
        btThem = new javax.swing.JButton();
        pn = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        lbTongTienHoaDon = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTienNhan = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lbTienDu = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lbDate = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbTime = new javax.swing.JLabel();
        lbTrangThai = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 153, 204));

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BÁN HÀNG");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(366, 366, 366)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(439, 439, 439))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Loại sản phẩm:");

        cbbLoaiSanPham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbbLoaiSanPham.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbbLoaiSanPhamPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Tên sản phẩm:");

        cbbTenSanPham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbbTenSanPham.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbbTenSanPhamPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Giá:");

        txtGia.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Mã sản phẩm:");

        txtMaSanPham.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Số lượng:");

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Thành tiền:");

        txtThanhTien.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbLoaiSanPham, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaSanPham))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbTenSanPham, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoLuong)))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGia)
                    .addComponent(txtThanhTien))
                .addGap(26, 26, 26))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(cbbTenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 204));

        btXoa.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlbh/images/logoXoa.png"))); // NOI18N
        btXoa.setText("Xóa");
        btXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXoaActionPerformed(evt);
            }
        });

        btLuu.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btLuu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlbh/images/logoLuu.png"))); // NOI18N
        btLuu.setText("Lưu");
        btLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLuuActionPerformed(evt);
            }
        });

        btHoaDonMoi.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btHoaDonMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlbh/images/New.png"))); // NOI18N
        btHoaDonMoi.setText("Hóa Đơn Mới");
        btHoaDonMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHoaDonMoiActionPerformed(evt);
            }
        });

        btThanhToan.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlbh/images/Pay.png"))); // NOI18N
        btThanhToan.setText("Thanh Toán");
        btThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThanhToanActionPerformed(evt);
            }
        });

        btThem.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/qlbh/images/logoThem.png"))); // NOI18N
        btThem.setText("Thêm");
        btThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btHoaDonMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btLuu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addGap(163, 163, 163))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btThem, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .addComponent(btHoaDonMoi, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pn.setBackground(new java.awt.Color(0, 153, 204));

        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbSanPham);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Tổng tiền hóa đơn:");

        lbTongTienHoaDon.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Tiền nhận của khách:");

        txtTienNhan.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtTienNhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienNhanKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Tiền dư:");

        lbTienDu.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        javax.swing.GroupLayout pnLayout = new javax.swing.GroupLayout(pn);
        pn.setLayout(pnLayout);
        pnLayout.setHorizontalGroup(
            pnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(pnLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTongTienHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTienNhan)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbTienDu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnLayout.setVerticalGroup(
            pnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbTongTienHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTienNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addComponent(lbTienDu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Ngày:");

        lbDate.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbDate.setText("Date");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Giờ:");

        lbTime.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbTime.setText("Time");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(57, 57, 57)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTime, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)))
                    .addComponent(jLabel12))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lbDate))
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lbTime))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbTrangThai.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTrangThai.setText("Trạng thái");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThemActionPerformed
        // TODO add your handling code here:
        Refresh();
        them=true;
        btThem.setEnabled(false);
        btLuu.setEnabled(true);
        Enabled();
        LoadLoaiSanPham();
    }//GEN-LAST:event_btThemActionPerformed

    private void cbbTenSanPhamPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbbTenSanPhamPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        String sql = "SELECT * FROM hanghoa where tenhh=?";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1, this.cbbTenSanPham.getSelectedItem().toString());
            rs=pst.executeQuery();
            while(rs.next()){
                txtMaSanPham.setText(rs.getString("mahh").trim());
                txtGia.setText(rs.getString("gia").trim());
                txtSoLuong.setEnabled(true);
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
        checkProducts();
    }//GEN-LAST:event_cbbTenSanPhamPopupMenuWillBecomeInvisible

    private void cbbLoaiSanPhamPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbbLoaiSanPhamPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        cbbTenSanPham.removeAllItems();
        String sql = "SELECT * FROM hanghoa where maloai=?";
        try {
            pst=conn.prepareStatement(sql);
            pst.setString(1, this.cbbLoaiSanPham.getSelectedItem().toString());
            rs=pst.executeQuery();
            while(rs.next()){
                this.cbbTenSanPham.addItem(rs.getString("tenhh").trim());
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
        if(cbbTenSanPham.getItemCount()==0){
            cbbTenSanPham.setEnabled(false);
            txtSoLuong.setEnabled(false);
            txtMaSanPham.setText("");
            txtGia.setText("");
            txtSoLuong.setText("");
            txtThanhTien.setText("");
        }
        else cbbTenSanPham.setEnabled(true);
    }//GEN-LAST:event_cbbLoaiSanPhamPopupMenuWillBecomeInvisible

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
        // TODO add your handling code here:
        
        if(txtSoLuong.getText().equals("")){
            txtThanhTien.setText("0");
        }
        else{
            String sqlCheck="SELECT soluong FROM hanghoa WHERE mahh='"+txtMaSanPham.getText()+"'";
            try{
            pst=conn.prepareStatement(sqlCheck);
            rs=pst.executeQuery();
            
                while(rs.next()){
                    if((rs.getInt("soluong")-Integer.parseInt(txtSoLuong.getText()))<0){
                        txtThanhTien.setText("0");
                        
                        lbTrangThai.setText("Số lượng sản phẩm bán không được vượt quá số lượng hàng trong kho!!");
                        btLuu.setEnabled(false);
                    }
                    else{
                        int soluong=Integer.parseInt(txtSoLuong.getText().toString());
                        float gia = Float.parseFloat(txtGia.getText().toString());
                        String []s=txtGia.getText().split("\\s");
                        txtThanhTien.setText(soluong * gia+"");
                        lbTrangThai.setText("Số lượng sản phẩm bán hợp lệ!!");
                        btLuu.setEnabled(true);
                    }
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void btLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLuuActionPerformed
        // TODO add your handling code here:
        if(them==true){
            if(Check()){
                themHangHoa();
            }
            else lbTrangThai.setText("Sản phẩm đã tồn tại trong hóa đơn");
        }
        checkBill();
        Pays();
    }//GEN-LAST:event_btLuuActionPerformed

    private void btHoaDonMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHoaDonMoiActionPerformed
        // TODO add your handling code here:
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn tạo 1 hóa đơn bán hàng mới hay không?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            String sqlDelete="DELETE FROM chitiethd";
            try{
                pst=conn.prepareStatement(sqlDelete);
                pst.executeUpdate();
                this.lbTrangThai.setText("Đã tạo hóa đơn mới!");
                Load(sql);
                checkBill();
                Refresh();
                btThem.setEnabled(true);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btHoaDonMoiActionPerformed

    private void btXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXoaActionPerformed
        // TODO add your handling code here:
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa sản phẩm khỏi hóa đơn hay không?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            String sqlDelete="DELETE FROM chitiethd WHERE mahh = ?";
            try{
                pst=conn.prepareStatement(sqlDelete);
                pst.setString(1, String.valueOf(txtMaSanPham.getText()));
                pst.executeUpdate();
                this.lbTrangThai.setText("Xóa sản phẩm thành công!");
                Refresh();
                Load(sql);
                Sucessful();
                checkBill();
                Pays();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btXoaActionPerformed

    private void txtTienNhanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienNhanKeyReleased
        // TODO add your handling code here:
        if(txtTienNhan.getText().equals("")){
            String []s=lbTongTienHoaDon.getText().split("\\s");
            lbTienDu.setText("0"+" "+s[1]);
        }
        else{
            txtTienNhan.setText(txtTienNhan.getText());
            
            String s1=txtTienNhan.getText();
            String[] s2=lbTongTienHoaDon.getText().split("\\s");
            
            if((Double.parseDouble(s1)-Double.parseDouble(s2[0]))>=0){
                lbTienDu.setText((Double.parseDouble(s1)-Double.parseDouble(s2[0]))+" "+s2[1]+ " VND");
                lbTrangThai.setText("Số tiền khách hàng đưa đã hợp lệ!");
                Pay=true;
            }
            else {
                
                lbTienDu.setText((Double.parseDouble(s1)-Double.parseDouble(s2[0]))+" "+s2[1]+ " VND");
                lbTrangThai.setText("Số tiền khách hàng đưa nhỏ hơn tổng tiền mua hàng trong hóa đơn!");
                Pay=false;
            }
        }
    }//GEN-LAST:event_txtTienNhanKeyReleased

    private void btThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThanhToanActionPerformed
        // TODO add your handling code here:
        if(Pay==true){
            String []s=lbTongTienHoaDon.getText().split("\\s");
            String sqlPay="INSERT INTO hoadon (tongtien,tiennhan,tienthua) VALUES(?,?,?)";
            try{
                pst=conn.prepareStatement(sqlPay);
                pst.setString(1, lbTongTienHoaDon.getText());
                pst.setString(2, txtTienNhan.getText());
                pst.setString(3, lbTienDu.getText());
                pst.executeUpdate();
                lbTrangThai.setText("Thực hiện thanh toán thành công!");
                Disabled();
                Sucessful();
                
                btThem.setEnabled(false);
                btThanhToan.setEnabled(false);
                txtTienNhan.setEnabled(false);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else if(Pay==false){
            JOptionPane.showMessageDialog(null, "Bạn cần nhập số tiền khách hàng thanh toán !");
        }
    }//GEN-LAST:event_btThanhToanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btHoaDonMoi;
    private javax.swing.JButton btLuu;
    private javax.swing.JButton btThanhToan;
    private javax.swing.JButton btThem;
    private javax.swing.JButton btXoa;
    private javax.swing.JComboBox<String> cbbLoaiSanPham;
    private javax.swing.JComboBox<String> cbbTenSanPham;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbDate;
    private javax.swing.JLabel lbTienDu;
    private javax.swing.JLabel lbTime;
    private javax.swing.JLabel lbTongTienHoaDon;
    private javax.swing.JLabel lbTrangThai;
    private javax.swing.JPanel pn;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtThanhTien;
    private javax.swing.JTextField txtTienNhan;
    // End of variables declaration//GEN-END:variables

}
