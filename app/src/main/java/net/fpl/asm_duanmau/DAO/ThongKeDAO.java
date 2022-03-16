package net.fpl.asm_duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.fpl.asm_duanmau.DBHelper.DBHelper;
import net.fpl.asm_duanmau.model.PhieuMuon;
import net.fpl.asm_duanmau.model.Sach;
import net.fpl.asm_duanmau.model.Top;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    SQLiteDatabase db;
    DBHelper dbHelper;
    Context context;

    public ThongKeDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<Top> getTop() {
        List<Top> list = new ArrayList<>();
        SachDAO sachDAO = new SachDAO(context);
        String sqlTop = "SELECT maSach, count(maSach) as soLuong FROM PhieuMuon Group by maSach ORDER BY soLuong DESC LIMIT 10";
        Cursor cursor = db.rawQuery(sqlTop, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            @SuppressLint("Range") String maSach = cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_NAME_MASACH_FK));
            Sach sach = sachDAO.getID(maSach);
            Top top = new Top();
            top.setTenSach(sach.getTenSach());
            @SuppressLint("Range") String soLuong = cursor.getString(cursor.getColumnIndex("soLuong"));
            top.setSoLuong(Integer.parseInt(soLuong));
            list.add(top);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int getDoanhThu(String tuNgay, String denNgay) {
        String sqlDanhThu = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlDanhThu, new String[]{tuNgay, denNgay});
        while (cursor.moveToNext()) {
            try {
                @SuppressLint("Range") String doanhthu = cursor.getString(cursor.getColumnIndex("doanhThu"));
                Log.e("TAG", doanhthu);
                list.add(Integer.parseInt(doanhthu));
            } catch (Exception e) {
                list.add(0);
            }
        }
        cursor.close();
        return list.get(0);
    }

}
