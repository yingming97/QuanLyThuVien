package net.fpl.asm_duanmau.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.fpl.asm_duanmau.Adapter.LoaiSachSpinnerAdapter;
import net.fpl.asm_duanmau.Adapter.SachAdapter;
import net.fpl.asm_duanmau.DAO.LoaiSachDAO;
import net.fpl.asm_duanmau.DAO.SachDAO;
import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.LoaiSach;
import net.fpl.asm_duanmau.model.Sach;

import java.util.ArrayList;
import java.util.List;


public class SachFragment extends Fragment {

    private ListView lv;
    private FloatingActionButton fab;
    ArrayList<Sach> list;
    SachAdapter adapter;
    Dialog dialog;
    TextView tvTitleDialog;
    EditText edTenSach;
    Spinner spinnerLoai;
    EditText edGiaThue;
    Button btnSave;
    Button btnCancel;
    Sach item;
    int maLoaiSach,positon;
    LoaiSachSpinnerAdapter spinnerAdapter;
    LoaiSachDAO loaiSachDAO;
    ArrayList<LoaiSach> listLoaiSach;
    SachDAO dao;

    public SachFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach, container, false);
        lv = (ListView) view.findViewById(R.id.lvSach);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        dao = new SachDAO(getContext());

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
        list = (ArrayList<Sach>) dao.getAll();
        adapter = new SachAdapter(getActivity(),this,list);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }


    private void openDialog(Context context,int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.sach_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvTitleDialog = dialog.findViewById(R.id.tv_Sach_title_dialog);
        edTenSach = dialog.findViewById(R.id.ed_TenSach);
        edGiaThue = dialog.findViewById(R.id.ed_GiaThue);
        spinnerLoai = dialog.findViewById(R.id.spinner_loai);
        btnSave = dialog.findViewById(R.id.btn_SaveSach);
        btnCancel = dialog.findViewById(R.id.btn_CancelSach);
        tvTitleDialog.setText("Thêm Sách");

        listLoaiSach = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        spinnerAdapter  = new LoaiSachSpinnerAdapter(context,listLoaiSach);
        spinnerLoai.setAdapter(spinnerAdapter);
        spinnerLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = listLoaiSach.get(position).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (type!=0){
            tvTitleDialog.setText("Sửa Sách");
            edTenSach.setText(item.getTenSach());
            edGiaThue.setText(item.getGiaThue()+"");
            for (int i = 0; i < listLoaiSach.size(); i++) {
                if (item.getMaLoai()==listLoaiSach.get(i).getMaLoai()){
                    positon = i;
                    break;
                }
            }
            spinnerLoai.setSelection(positon);
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
                Sach item_insert = new Sach();
                item_insert.setTenSach(edTenSach.getText().toString());
                item_insert.setGiaThue(Integer.parseInt(edGiaThue.getText().toString()));
                item_insert.setMaLoai(maLoaiSach);
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
                        item_insert.setMaSach(item.getMaSach());
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

    private int validate() {
        int check =1;
        if (edTenSach.getText().toString().isEmpty()||edGiaThue.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Không được để trống Thông Tin", Toast.LENGTH_SHORT).show();
            check=-1;
        }
        if (listLoaiSach.size()<=0){
            Toast.makeText(getContext(), "Loại Sách Chưa Có Dữ Liệu!!!", Toast.LENGTH_SHORT).show();
            check=-1;
        }
        return check;
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

                List<Sach> list_check = dao.checkGetIDSach(Integer.parseInt(id));
                if (list_check.size()>0){
                    AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_e,null);
                    builder.setView(view);
                    AlertDialog alertDialog2 = builder.create();
                    alertDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialog2.show();
                    Button btn_e = view.findViewById(R.id.btn_e);
                    btn_e.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog2.dismiss();
                            alertDialog.dismiss();
                        }
                    });
                    return;
                }



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


}