package net.fpl.asm_duanmau.model;

import java.util.Date;

public class PhieuMuon {
private int maPM,maTV,maSach,tienThue,trangThai;
private String maTT;
private Date ngay;
    public static  final String TB_NAME = "PhieuMuon";
    public static  final String COL_NAME_MAPM_PK = "maPM";
    public static  final String COL_NAME_MATT_FK = "maTT";
    public static  final String COL_NAME_MATV_FK = "maTV";
    public static  final String COL_NAME_MASACH_FK = "maSach";
    public static  final String COL_NAME_NGAY = "ngay";
    public static  final String COL_NAME_TIENTHUE = "tienThue";
    public static  final String COL_NAME_TRANG_THAI = "trangThai";

    public PhieuMuon() {
    }

    public PhieuMuon(int maPM, int maTV, int maSach, int tienThue, int trangThai, String maTT, Date ngay) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.trangThai = trangThai;
        this.maTT = maTT;
        this.ngay = ngay;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }
}
