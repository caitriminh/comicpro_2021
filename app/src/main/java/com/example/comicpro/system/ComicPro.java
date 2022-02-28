package com.example.comicpro.system;


import com.example.comicpro.model.lichphathanh.LichPhatHanh;
import com.example.comicpro.model.danhmuc.LoaiTruyen;
import com.example.comicpro.model.menu.Menu;
import com.example.comicpro.model.danhmuc.NhaXuatBan;
import com.example.comicpro.model.phieunhap.PhieuNhap;
import com.example.comicpro.model.phieuxuat.PhieuXuat;
import com.example.comicpro.model.danhmuc.TacGia;
import com.example.comicpro.model.tentruyen.TenTruyen;
import com.example.comicpro.model.thongke.ThongKeTheoNCC;
import com.example.comicpro.model.thongke.ThongKeTheoNXB;
import com.example.comicpro.model.thongke.ThongKeTheoThang;
import com.example.comicpro.model.thongke.ThongKeTheoTuaTruyen;
import com.example.comicpro.model.tuatruyen.TuaTruyen;
import com.example.comicpro.model.tonkho.TonKho;

public class ComicPro {

    public  static String BASE_URL ="http://triminh.xyz/index.php/api/";

    //public  static String BASE_URL ="http://172.16.1.5:8081/COMIC/index.php/api/";
    public  static TuaTruyen objTuaTruyen;
    public  static PhieuNhap objPhieuNhap;
    public  static PhieuXuat objPhieuXuat;
    public  static TonKho objTonKho;
    public  static TacGia objTacgia;
    public  static NhaXuatBan objNXB;
    public  static ThongKeTheoThang objThongKeTheoThang;
    public  static ThongKeTheoNXB objThongKeTheoNXB;
    public  static ThongKeTheoNCC objThongKeTheoNCC;
    public  static LoaiTruyen objLoaiTruyen;
    public  static Integer intOption=1;
    public  static  String tendangnhap="ADMIN", pdffile="";
    public  static TenTruyen objTenTruyen;
    public  static LichPhatHanh objLichPhatHanh;
    public  static ThongKeTheoTuaTruyen objThongKeTenTruyen;
    public static Menu objMenu;
    public static Integer PhieuNhap=0;
    public static Integer PhieuXuat=0;
    public  static String strMaTua;
}
