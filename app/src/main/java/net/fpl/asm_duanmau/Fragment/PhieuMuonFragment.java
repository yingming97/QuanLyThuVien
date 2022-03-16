package net.fpl.asm_duanmau.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.fpl.asm_duanmau.Adapter.PhieuMuonAdapter;
import net.fpl.asm_duanmau.Adapter.SachSpinnerAdapter;
import net.fpl.asm_duanmau.Adapter.ThanhVienSpinnerAdapter;
import net.fpl.asm_duanmau.DAO.PhieuMuonDAO;
import net.fpl.asm_duanmau.DAO.SachDAO;
import net.fpl.asm_duanmau.DAO.ThanhVienDAO;
import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.PhieuMuon;
import net.fpl.asm_duanmau.model.Sach;
import net.fpl.asm_duanmau.model.ThanhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class PhieuMuonFragment extends Fragment {
    private ListView lv;
    private FloatingActionButton fab;
    Dialog dialog;
    PhieuMuonDAO dao;
    TextView tvTitleDialog;
    EditText edNgay;
    TextView tvTienThue;
    Spinner spTV,spSach;
    CheckBox chkTraSach;
    Button btnSave,btnCancel;
    PhieuMuonAdapter adapter;

    PhieuMuon item;
    ArrayList<PhieuMuon> list;
    ArrayList<ThanhVien> listThanhVien;
    ThanhVienDAO thanhVienDAO;
    ThanhVienSpinnerAdapter spinnerThanhVienAdapter;
    int maThanhVien;

    ArrayList<Sach> listSach;
    SachDAO sachDAO;
    SachSpinnerAdapter spinnerSachAdapter;
    int maSach;
    int tienThue;
    int positonTV,positonSach;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public PhieuMuonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        lv = (ListView) view.findViewById(R.id.lvPhieuMuon);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        dao = new PhieuMuonDAO(getContext());

        capNhatLv();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getContext(),0);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getContext(),1);
                return false;
            }
        });
        return view;
    }

    private void capNhatLv() {
        list = (ArrayList<PhieuMuon>) dao.getAll();

        adapter = new PhieuMuonAdapter(getActivity(),this,list);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }


    private void openDialog(Context context, int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.phieu_muon_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvTitleDialog = dialog.findViewById(R.id.tv_PhieuMuon_title_dialog);
        edNgay = dialog.findViewById(R.id.ed_NgayMuon);
        tvTienThue = dialog.findViewById(R.id.tv_TienThue);
        spTV = dialog.findViewById(R.id.spinner_maTV);
        spSach = dialog.findViewById(R.id.spinner_maSach);
        chkTraSach = dialog.findViewById(R.id.chk_TraSach);
        btnSave = dialog.findViewById(R.id.btn_SavePM);
        btnCancel = dialog.findViewById(R.id.btn_CancelPM);
        tvTitleDialog.setText("Thêm Phiếu Mượn");
        tvTienThue.setText("Tiền Thuê: ");

        listThanhVien = new ArrayList<>();
        thanhVienDAO = new ThanhVienDAO(context);
        listThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        spinnerThanhVienAdapter  = new ThanhVienSpinnerAdapter(context,listThanhVien);
        spTV.setAdapter(spinnerThanhVienAdapter);
        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = listThanhVien.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listSach = new ArrayList<>();
        sachDAO = new SachDAO(context);
        listSach = (ArrayList<Sach>) sachDAO.getAll();
        spinnerSachAdapter  = new SachSpinnerAdapter(context,listSach);
        spSach.setAdapter(spinnerSachAdapter);
        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                tienThue = listSach.get(position).getGiaThue();
                tvTienThue.setText("Tiền Thuê: " + tienThue+" VNĐ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog =new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edNgay.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        

        if (type!=0){
            tvTitleDialog.setText("Sửa Sách");
            edNgay.setText(simpleDateFormat.format(item.getNgay()));
            tvTienThue.setText("Tiền Thuê: "+item.getTienThue());
            //Thanh vien
            for (int i = 0; i < listThanhVien.size(); i++) {
                if (item.getMaTV()==listThanhVien.get(i).getMaTV()){
                    positonTV = i;
                    break;
                }
            }
            spTV.setSelection(positonTV);
            //Sach
            for (int i = 0; i < listSach.size(); i++) {
                if (item.getMaSach()==listSach.get(i).getMaSach()){
                    positonSach = i;
                    break;
                }
            }
            spSach.setSelection(positonSach);

            if (item.getTrangThai()==1){
                chkTraSach.setChecked(true);
            }else {
                chkTraSach.setChecked(false);
            }





        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuon item_insert = new PhieuMuon();
                item_insert.setMaSach(maSach);
                item_insert.setMaTV(maThanhVien);
                try {
                    item_insert.setNgay(simpleDateFormat.parse(edNgay.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                item_insert.setTienThue(tienThue);
                if (chkTraSach.isChecked()){
                    item_insert.setTrangThai(1);
                }else {
                    item_insert.setTrangThai(0);
                }
                if (validate()>0){
                    if (type==0){
                        // type=0 : insert
                        long result = dao.insert(item_insert);
                        if (result>0){
                            Toast.makeText(context, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //type=1 : update
                        item_insert.setMaPM(item.getMaPM());
                        int result = dao.update(item_insert);
                        if (result>0){
                            Toast.makeText(context, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }

    public void xoa(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_delete,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        Button btn_delete = view.findViewById(R.id.btn_ok_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = dao.delete(id);
                if (result>0){
                    Toast.makeText(getContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    capNhatLv();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
    }

    private int validate() {
        int check =1;
        if (edNgay.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Không được để trống Thông Tin", Toast.LENGTH_SHORT).show();
            check=-1;
        }
        if (listSach.size()<=0){
            Toast.makeText(getContext(), "Sách Chưa Có Dữ Liệu!!!", Toast.LENGTH_SHORT).show();
            check=-1;
        }
        if (listThanhVien.size()<=0){
            Toast.makeText(getContext(), "Thành Viên Chưa Có Dữ Liệu!!!", Toast.LENGTH_SHORT).show();
            check=-1;
        }
        return check;
    }
}