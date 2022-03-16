package net.fpl.asm_duanmau.model;

public class ThuThu {
    private String maTT, hoTen, mk;
    public static final String TB_NAME = "ThuThu";
    public static final String COL_NAME_MATT_PK = "maTT";
    public static final String COL_NAME_HOTEN = "hoTen";
    public static final String COL_NAME_MATKHAU = "matKhau";

    public ThuThu(String maTT, String hoTen, String mk) {
        this.maTT = maTT;
        this.hoTen = hoTen;
        this.mk = mk;
    }

    public ThuThu() {
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }
}
