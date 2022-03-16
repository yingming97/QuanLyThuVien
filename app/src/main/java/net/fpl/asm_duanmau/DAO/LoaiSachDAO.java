package net.fpl.asm_duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.fpl.asm_duanmau.DBHelper.DBHelper;
import net.fpl.asm_duanmau.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    SQLiteDatabase db;
    DBHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put(LoaiSach.COL_NAME_TENLOAI, loaiSach.getTenLoai());
        return db.insert(LoaiSach.TB_NAME, null, values);

    }

    public int update(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put(LoaiSach.COL_NAME_TENLOAI, loaiSach.getTenLoai());
        return db.update(LoaiSach.TB_NAME, values, "maLoai=?", new String[]{String.valueOf(loaiSach.getMaLoai())});
    }

    public int delete(String id) {
        return db.delete(LoaiSach.TB_NAME, "maLoai=?", new String[]{id});
    }

    private List<LoaiSach> getData(String sql, String... paramater) {
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, paramater);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            @SuppressLint("Range") int ma = cursor.getInt(cursor.getColumnIndex(LoaiSach.COL_NAME_MALOAI_PK));
            @SuppressLint("Range") String tenLoai = cursor.getString(cursor.getColumnIndex(LoaiSach.COL_NAME_TENLOAI));
            LoaiSach loaiSach = new LoaiSach(ma, tenLoai);
            list.add(loaiSach);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public List<LoaiSach> getAll() {
        String sql = "SELECT * FROM " + LoaiSach.TB_NAME;
        return getData(sql);
    }

    public LoaiSach getID(String id) {
        String sql = "SELECT * FROM LoaiSach WHERE maLoai=?";
        List<LoaiSach> list = getData(sql, id);
        return list.get(0);
    }

    public List<LoaiSach> checkGetIDLoaiSach(int id) {
        String sql = "SELECT * FROM LoaiSach as ls INNER JOIN Sach as s ON ls.maLoai = s.maLoai WHERE ls.maLoai=?";
        List<LoaiSach> list = getData(sql, String.valueOf(id));
        return list;
    }
}
