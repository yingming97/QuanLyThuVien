package net.fpl.asm_duanmau.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.fpl.asm_duanmau.DBHelper.DBHelper;
import net.fpl.asm_duanmau.model.PhieuMuon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuMuonDAO  {
    SQLiteDatabase db;
    DBHelper dbHelper;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public PhieuMuonDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuon phieuMuon){
        ContentValues values = new ContentValues();
        values.put(PhieuMuon.COL_NAME_MATT_FK,phieuMuon.getMaTT());
        values.put(PhieuMuon.COL_NAME_MATV_FK,phieuMuon.getMaTV());
        values.put(PhieuMuon.COL_NAME_MASACH_FK,phieuMuon.getMaSach());
        values.put(PhieuMuon.COL_NAME_TIENTHUE,phieuMuon.getTienThue());
        values.put(PhieuMuon.COL_NAME_TRANG_THAI,phieuMuon.getTrangThai());
        values.put(PhieuMuon.COL_NAME_NGAY,simpleDateFormat.format(phieuMuon.getNgay()));
        return db.insert(PhieuMuon.TB_NAME,null,values);
    }

    public int update(PhieuMuon phieuMuon){
        ContentValues values = new ContentValues();
        values.put(PhieuMuon.COL_NAME_MATT_FK,phieuMuon.getMaTT());
        values.put(PhieuMuon.COL_NAME_MATV_FK,phieuMuon.getMaTV());
        values.put(PhieuMuon.COL_NAME_MASACH_FK,phieuMuon.getMaSach());
        values.put(PhieuMuon.COL_NAME_TIENTHUE,phieuMuon.getTienThue());
        values.put(PhieuMuon.COL_NAME_TRANG_THAI,phieuMuon.getTrangThai());
        values.put(PhieuMuon.COL_NAME_NGAY,simpleDateFormat.format(phieuMuon.getNgay()));
        return db.update(PhieuMuon.TB_NAME,values,"maPM=?",new String[]{String.valueOf(phieuMuon.getMaPM())});
    }
    public int delete(String id){
        return db.delete(PhieuMuon.TB_NAME,"maPM=?",new String[]{id});
    }

    @SuppressLint("Range")
    private List<PhieuMuon> getData(String sql, String... paramater){
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,paramater);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            @SuppressLint("Range") int maPM = cursor.getInt(cursor.getColumnIndex(PhieuMuon.COL_NAME_MAPM_PK));
            @SuppressLint("Range") String maTT = cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_NAME_MATT_FK));
            @SuppressLint("Range") int maTV = cursor.getInt(cursor.getColumnIndex(PhieuMuon.COL_NAME_MATV_FK));
            @SuppressLint("Range") int maSach = cursor.getInt(cursor.getColumnIndex(PhieuMuon.COL_NAME_MASACH_FK));
            Date ngay=null;
            try {
                ngay = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(PhieuMuon.COL_NAME_NGAY)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int tienThue = cursor.getInt(cursor.getColumnIndex(PhieuMuon.COL_NAME_TIENTHUE));
            int traSach = cursor.getInt(cursor.getColumnIndex(PhieuMuon.COL_NAME_TRANG_THAI));

            PhieuMuon phieuMuon = new PhieuMuon(maPM,maTV,maSach,tienThue,traSach,maTT,ngay);
            list.add(phieuMuon);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM " + PhieuMuon.TB_NAME;
        return getData(sql);
    }

    public PhieuMuon getID(String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon> list = getData(sql,id);
        return list.get(0);
    }
}
