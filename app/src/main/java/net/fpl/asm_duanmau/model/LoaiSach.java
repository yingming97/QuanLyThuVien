package net.fpl.asm_duanmau.model;

public class LoaiSach {
private int maLoai;
private String tenLoai;

    public static  final String TB_NAME = "LoaiSach";
    public static  final String COL_NAME_MALOAI_PK = "maLoai";
    public static  final String COL_NAME_TENLOAI = "tenLoai";
    public LoaiSach() {
    }

    public LoaiSach(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
