package net.fpl.asm_duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.fpl.asm_duanmau.DBHelper.DBHelper;
import net.fpl.asm_duanmau.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    SQLiteDatabase db;
    DBHelper dbHelper;

    public ThuThuDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put(ThuThu.COL_NAME_MATT_PK, thuThu.getMaTT());
        values.put(ThuThu.COL_NAME_HOTEN, thuThu.getHoTen());
        values.put(ThuThu.COL_NAME_MATKHAU, thuThu.getMk());
        return db.insert(ThuThu.TB_NAME, null, values);
    }

    public int update(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put(ThuThu.COL_NAME_MATT_PK, thuThu.getMaTT());
        values.put(ThuThu.COL_NAME_HOTEN, thuThu.getHoTen());
        values.put(ThuThu.COL_NAME_MATKHAU, thuThu.getMk());
        return db.update(ThuThu.TB_NAME, values, "maTT = ?", new String[]{thuThu.getMaTT()});
    }

    public int delete(String maTT) {
        return db.delete(ThuThu.TB_NAME, "maTT = ?", new String[]{maTT});
    }

    private List<ThuThu> getData(String sql, String... paramater) {
        List<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, paramater);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            @SuppressLint("Range") String ma = cursor.getString(cursor.getColumnIndex(ThuThu.COL_NAME_MATT_PK));
            @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex(ThuThu.COL_NAME_HOTEN));
            @SuppressLint("Range") String matKhau = cursor.getString(cursor.getColumnIndex(ThuThu.COL_NAME_MATKHAU));
            ThuThu thuThu = new ThuThu(ma, ten, matKhau);
            list.add(thuThu);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<ThuThu> getAll() {
        String sql = "SELECT * FROM " + ThuThu.TB_NAME;
        return getData(sql);
    }

    public ThuThu getID(String id) {
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu> list = getData(sql, id);
        return list.get(0);
    }

    public boolean checkLogin(String id, String pass) {
        String sql = "SELECT * FROM ThuThu WHERE maTT=? AND matKhau=?";
        List<ThuThu> list = getData(sql, id, pass);
        if (list.size() == 0) {
            return false;
        }
        return true;
    }
}
