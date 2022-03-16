package net.fpl.asm_duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.fpl.asm_duanmau.DBHelper.DBHelper;
import net.fpl.asm_duanmau.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    SQLiteDatabase db;
    DBHelper dbHelper;
    public SachDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(Sach sach){
        ContentValues values = new ContentValues();
        values.put(Sach.COL_NAME_TENSACH,sach.getTenSach());
        values.put(Sach.COL_NAME_GIATHUE,sach.getGiaThue());
        values.put(Sach.COL_NAME_MALOAI_FK,sach.getMaLoai());
        long result=db.insert(Sach.TB_NAME,null,values);
        return result;
    }

    public int update(Sach sach){
        ContentValues values = new ContentValues();
        values.put(Sach.COL_NAME_TENSACH,sach.getTenSach());
        values.put(Sach.COL_NAME_GIATHUE,sach.getGiaThue());
        values.put(Sach.COL_NAME_MALOAI_FK,sach.getMaLoai());
        int result=db.update(Sach.TB_NAME,values,"maSach=?",new String[]{String.valueOf(sach.getMaSach())});
        return result;
    }

    public int delete(String id){
        int result=db.delete(Sach.TB_NAME,"maSach=?",new String[]{id});
        return result;
    }

    private List<Sach> getData(String sql, String... paramater){
        List<Sach> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,paramater);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            @SuppressLint("Range") int ma = cursor.getInt(cursor.getColumnIndex(Sach.COL_NAME_MASACH_PK));
            @SuppressLint("Range") String ten = cursor.getString(cursor.getColumnIndex(Sach.COL_NAME_TENSACH));
            @SuppressLint("Range") int giaThue = cursor.getInt(cursor.getColumnIndex(Sach.COL_NAME_GIATHUE));
            @SuppressLint("Range") int maLoai = cursor.getInt(cursor.getColumnIndex(Sach.COL_NAME_MALOAI_FK));
            Sach sach = new Sach(ma,giaThue,maLoai,ten);
            list.add(sach);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public List<Sach> getAll(){
        String sql = "SELECT * FROM " + Sach.TB_NAME;
        return getData(sql);
    }

    public Sach getID(String id){
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach> list = getData(sql,id);
        return list.get(0);
    }

    public List<Sach> checkGetIDSach(int id){
        String sql = "SELECT * FROM Sach as s INNER JOIN PhieuMuon as pm ON s.maSach = pm.maSach WHERE s.maSach=?";
        List<Sach> list = getData(sql, String.valueOf(id));
        return list;
    }
}
