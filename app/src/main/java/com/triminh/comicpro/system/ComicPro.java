package com.triminh.comicpro.system;


import com.triminh.comicpro.model.lichphathanh.LichPhatHanh;
import com.triminh.comicpro.model.danhmuc.LoaiTruyen;
import com.triminh.comicpro.model.menu.Menu;
import com.triminh.comicpro.model.danhmuc.NhaXuatBan;
import com.triminh.comicpro.model.phieunhap.PhieuNhap;
import com.triminh.comicpro.model.phieuxuat.PhieuXuat;
import com.triminh.comicpro.model.danhmuc.TacGia;
import com.triminh.comicpro.model.tentruyen.TenTruyen;
import com.triminh.comicpro.model.thongke.ThongKeTheoNCC;
import com.triminh.comicpro.model.thongke.ThongKeTheoNXB;
import com.triminh.comicpro.model.thongke.ThongKeTheoThang;
import com.triminh.comicpro.model.thongke.ThongKeTheoTuaTruyen;
import com.triminh.comicpro.model.tuatruyen.TuaTruyen;
import com.triminh.comicpro.model.tonkho.TonKho;

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
