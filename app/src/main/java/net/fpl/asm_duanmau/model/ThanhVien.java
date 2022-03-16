package net.fpl.asm_duanmau.model;

public class ThanhVien {
    private int maTV;
    private String hoTen, phone;
    public static final String TB_NAME = "ThanhVien";
    public static final String COL_NAME_MATV_PK = "maTV";
    public static final String COL_NAME_HOTEN = "hoTen";
    public static final String COL_NAME_phone = "phone";

    public ThanhVien() {
    }

    public ThanhVien(int maTV, String hoTen, String phone) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.phone = phone;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
