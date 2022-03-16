package net.fpl.asm_duanmau.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "QuanLySach.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng thủ thư
        String createTableThuThu ="create table ThuThu (maTT text primary key, hoTen text not null, matKhau text not null)";
        db.execSQL(createTableThuThu);

        // Bảng TV
        String createTableThanhVien ="create table ThanhVien (maTV integer primary key autoincrement, hoTen text not null, phone text not null)";
        db.execSQL(createTableThanhVien);

        // Bang Loại sách
        String createTableLoaiSach ="create table LoaiSach (maLoai integer primary key autoincrement, tenLoai text not null)";
        db.execSQL(createTableLoaiSach);

        //  Bảng sách
        String createTableSach ="create table Sach (maSach integer primary key autoincrement, tenSach text not null, giaThue integer not null, maLoai integer references LoaiSach(maLoai))";
        db.execSQL(createTableSach);

        // bảng phiếu mượn
        String createTablePhieuMuon ="create table PhieuMuon (maPM integer primary key autoincrement, maTT text references ThuThu(maTT), maTV integer references ThanhVien(maTV), maSach integer references Sach(maSach), tienThue integer not null, ngay date not null, trangThai integer not null)";
        db.execSQL(createTablePhieuMuon);


        // data
        createTableThuThu = "INSERT INTO ThuThu VALUES('hien', 'Phạm Văn Hiển','123')";
        db.execSQL(createTableThuThu);

        createTableThanhVien = "INSERT INTO ThanhVien VALUES(1, 'PVH','082342343243')";
        db.execSQL(createTableThanhVien);

        createTableLoaiSach = "INSERT INTO LoaiSach VALUES(null, 'Xa Hoi')";
        db.execSQL(createTableLoaiSach);

        createTableSach = "INSERT INTO Sach VALUES(null, 'Văn học TK',2500,1)";
        db.execSQL(createTableSach);

        createTablePhieuMuon = "INSERT INTO PhieuMuon VALUES(null, 'admin',1,1,2500,'25-02-2022',1)";
        db.execSQL(createTablePhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String dropTableThuThu = "drop table if exists ThuThu";
        db.execSQL(dropTableThuThu);
        String dropTableThanhVien = "drop table if exists ThanhVien";
        db.execSQL(dropTableThanhVien);
        String dropTableLoaiSach = "drop table if exists LoaiSach";
        db.execSQL(dropTableLoaiSach);
        String dropTableSach = "drop table if exists Sach";
        db.execSQL(dropTableSach);
        String dropTablePhieuMuon = "drop table if exists PhieuMuon";
        db.execSQL(dropTablePhieuMuon);

        onCreate(db);
    }
}
