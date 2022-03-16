package net.fpl.asm_duanmau.model;

public class Sach {
    private int maSach,giaThue,maLoai;
    private String tenSach;
    public static  final String TB_NAME = "Sach";
    public static  final String COL_NAME_MASACH_PK = "maSach";
    public static  final String COL_NAME_TENSACH = "tenSach";
    public static  final String COL_NAME_GIATHUE = "giaThue";
    public static  final String COL_NAME_MALOAI_FK = "maLoai";
    public Sach() {
    }

    public Sach(int maSach, int giaThue, int maLoai, String tenSach) {
        this.maSach = maSach;
        this.giaThue = giaThue;
        this.maLoai = maLoai;
        this.tenSach = tenSach;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
