/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.control;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import qlbh.model.TaiKhoan;
import qlbh.service.TaiKhoanService;
import qlbh.service.TaiKhoanServiceImpl;
import qlbh.view.MainJFrame;

/**
 *
 * @author Bao Nhu
 */
public class TaiKhoanControl {
    private JDialog dialog;
    private JButton btnSubmit;
    private JTextField jtfTenDangNhap;
    private JPasswordField jtfMatKhau;
    private JLabel jlbMsg;
    
    private TaiKhoanService taiKhoanService = null;

    public TaiKhoanControl(JDialog dialog, JButton btnSubmit, JTextField jtfTenDangNhap, JPasswordField jtfMatKhau, JLabel jlbMsg) {
        this.dialog = dialog;
        this.btnSubmit = btnSubmit;
        this.jtfTenDangNhap = jtfTenDangNhap;
        this.jtfMatKhau = jtfMatKhau;
        this.jlbMsg = jlbMsg;
        
        taiKhoanService = new TaiKhoanServiceImpl();
    }

    public void setEvent(){
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(jtfTenDangNhap.getText().length() == 0 || jtfMatKhau.getText().length() == 0)
                    jlbMsg.setText("Vui lòng nhập dữ liệu bắt buộc");
                else{
                    TaiKhoan taiKhoan = taiKhoanService.login(jtfTenDangNhap.getText(), jtfMatKhau.getText());
                    if(taiKhoan == null){
                        jlbMsg.setText("Tên đăng nhập hoặc mật khẩu không đúng");
                    }
                    else{
                        if(!taiKhoan.isTinhTrang()){
                            jlbMsg.setText("Tài khoản đang bị bảo trì");
                        }
                        else{
                            MainJFrame frame = new MainJFrame();
                            frame.setTitle("Quản lí văn phòng phẩm");
                            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                            frame.setVisible(true);
                            dialog.dispose();
                        }
                    }
                }
            }
            
            public void mouseEncered(MouseEvent e){
                btnSubmit.setBackground(new Color(0, 200, 83));
            }
            
            @Override
            public void mouseExited(MouseEvent e){
                btnSubmit.setBackground(new Color(100, 221, 23));
            }
});
    }
}
