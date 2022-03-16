package net.fpl.asm_duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.fpl.asm_duanmau.DBHelper.DBHelper;
import net.fpl.asm_duanmau.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    SQLiteDatabase db;
    DBHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put(ThanhVien.COL_NAME_HOTEN, thanhVien.getHoTen());
        values.put(ThanhVien.COL_NAME_phone, thanhVien.getPhone());
        long result = db.insert(ThanhVien.TB_NAME, null, values);
        return result;
    }

    public int update(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put(ThanhVien.COL_NAME_HOTEN, thanhVien.getHoTen());
        values.put(ThanhVien.COL_NAME_phone, thanhVien.getPhone());
        int result = db.update(ThanhVien.TB_NAME, values, "maTV=?", new String[]{String.valueOf(thanhVien.getMaTV())});
        return result;
    }


    public int delete(String id) {
        int result = db.delete(ThanhVien.TB_NAME, "maTV=?", new String[]{id});
        return result;
    }

    private List<ThanhVien> getData(String sql, String... paramater) {
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, paramater);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            @SuppressLint("Range") int ma = cursor.getInt(cursor.getColumnIndex(ThanhVien.COL_NAME_MATV_PK));
            @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex(ThanhVien.COL_NAME_HOTEN));
            @SuppressLint("Range") String namSinh = cursor.getString(cursor.getColumnIndex(ThanhVien.COL_NAME_phone));
            ThanhVien thanhVien = new ThanhVien(ma, ten, namSinh);
            list.add(thanhVien);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<ThanhVien> getAll() {
        String sql = "SELECT * FROM " + ThanhVien.TB_NAME;
        return getData(sql);
    }

    public ThanhVien getID(String id) {
        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> list = getData(sql, id);
        return list.get(0);
    }


    public List<ThanhVien> checkGetIDThanhVien(int id) {
        String sql = "SELECT * FROM ThanhVien as tv INNER JOIN PhieuMuon as pm ON tv.maTV = pm.maTV WHERE tv.maTV=?";
        List<ThanhVien> list = getData(sql, String.valueOf(id));
        return list;
    }
}
