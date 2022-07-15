/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbh.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import qlbh.bean.DanhMuc;
import qlbh.view.BanHangJPanel;
import qlbh.view.ChucVuJPanel;
import qlbh.view.HangHoaJPanel;
import qlbh.view.KhachHangJPanel;
import qlbh.view.NhaCungCapJPanel;
import qlbh.view.NhanVienJPanel;
import qlbh.view.PhieuNhapHHJPanel;
import qlbh.view.TrangChuJPanel;



/**
 *
 * @author DELL
 */
public class ChuyenMangHinh {
    JPanel Root;
    String kindSelected =" ";
    List<DanhMuc> listTtem = null;
    
    
    public ChuyenMangHinh(JPanel jpnRoot){
        this.Root = jpnRoot;
    }
    
    public void setView(JPanel jpnItem, JLabel jlbItem){
        kindSelected = "TrangChu";
        jpnItem.setBackground(new Color(255, 51, 153));
        jlbItem.setBackground(new Color(255, 51, 153));
        Root.removeAll();
        Root.setLayout(new BorderLayout());
        Root.add(new TrangChuJPanel());
        Root.validate();
        Root.repaint();
    }
    public void setEven(List<DanhMuc> listTtem){
        this.listTtem = listTtem;
        for(DanhMuc item: listTtem){
            item.getJlb().addMouseListener(new LabelEvent(item.getLoai(), item.getJpn(), item.getJlb()));
        }  
    }
    
    class LabelEvent implements MouseListener{
  
        JPanel node;
        String kind;
        
        JPanel jpnItem;
        JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }
        
        
        @Override
        public void mouseClicked(MouseEvent e) {
            switch(kind){
                case "NhanVien":
                    node = new NhanVienJPanel();
                    break;
                case "HangHoa":
                    node = new HangHoaJPanel();
                    break;
                case "KhachHang":
                    node = new KhachHangJPanel();
                    break;
                case "NhaCungCap":
                    node = new NhaCungCapJPanel();
                    break;
                case "PhieuNhap":
                    node = new PhieuNhapHHJPanel();
                    break;
                case "ChucVu":
                    node = new ChucVuJPanel();
                    break;
                case "TrangChu":
                    node = new TrangChuJPanel();
                    break;
                case "BanHang":
                    node = new BanHangJPanel();
                    break;
                default:
                    break;
            }
            Root.removeAll();
            Root.setLayout(new BorderLayout());
            Root.add(node);
            Root.validate();
            Root.repaint();
            setChangeBackground(kind);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(26, 83, 255));
            jlbItem.setBackground(new Color(26, 83, 255));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(26, 83, 255));
            jlbItem.setBackground(new Color(26, 83, 255));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(!kindSelected.equalsIgnoreCase(kind)){
                jpnItem.setBackground(new Color(217, 102, 255));
                jlbItem.setBackground(new Color(217, 102, 255));
            }
        }
        
    }
    void setChangeBackground(String kind){
        for(DanhMuc item : listTtem){
            if(item.getLoai().equalsIgnoreCase(kind)){
                item.getJpn().setBackground(new Color(64, 255, 0));
                item.getJlb().setBackground(new Color(64, 255, 0));
            }else{
                item.getJpn().setBackground(new Color(217, 102, 255));
                item.getJlb().setBackground(new Color(217, 102, 255));
            }
        }
    }
}
