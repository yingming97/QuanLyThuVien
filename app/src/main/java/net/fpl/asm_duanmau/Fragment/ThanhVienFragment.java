package net.fpl.asm_duanmau.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.fpl.asm_duanmau.Adapter.ThanhVienAdapter;
import net.fpl.asm_duanmau.DAO.ThanhVienDAO;
import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;


public class ThanhVienFragment extends Fragment {
ThanhVienAdapter adapter;
ArrayList<ThanhVien> list;
ThanhVienDAO dao;
Dialog dialog;
ThanhVien item;
private ListView lv;
private FloatingActionButton fab;
TextView tvTitleDialog;
TextView edTenTV;
TextView edNamSinh;
Button btnSave;
Button btnCancel;


    public ThanhVienFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        lv = (ListView) view.findViewById(R.id.lvThanhVien);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        dao = new ThanhVienDAO(getContext());

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
        list = (ArrayList<ThanhVien>) dao.getAll();
        adapter = new ThanhVienAdapter(getActivity(),this,list);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }

    private void openDialog(Context context,int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.thanh_vien_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         tvTitleDialog = dialog.findViewById(R.id.tv_ThanhVien_title_dialog);
         edTenTV = dialog.findViewById(R.id.ed_TenTV);
         edNamSinh = dialog.findViewById(R.id.ed_phone);
         btnSave = dialog.findViewById(R.id.btn_SaveTV);
         btnCancel = dialog.findViewById(R.id.btn_CancelTV);
        tvTitleDialog.setText("Thêm Thành Viên");
        if (type!=0){
            tvTitleDialog.setText("Sửa Thành Viên");
            edTenTV.setText(item.getHoTen());
            edNamSinh.setText(item.getPhone());
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
                ThanhVien item_insert = new ThanhVien();
                item_insert.setHoTen(edTenTV.getText().toString());
                item_insert.setPhone(edNamSinh.getText().toString());
                if (validate()>0){
                    if (type==0){
                        // type  0 insert
                        long result = dao.insert(item_insert);
                        if (result>0){
                            Toast.makeText(context, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        item_insert.setMaTV(item.getMaTV());
                        int result = dao.update(item_insert);
                        Log.d("mah2hh2 ", item.getMaTV()+"-"+item.getHoTen());
                        if (result>0){
                            Toast.makeText(context, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                            Log.d("mah2hh2 ", item.getMaTV()+"-"+item.getHoTen());
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
       if (edTenTV.getText().toString().isEmpty()||edNamSinh.getText().toString().isEmpty()){
           Toast.makeText(getContext(), "Không được để trống Thông Tin", Toast.LENGTH_SHORT).show();
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

                List<ThanhVien> list_check = dao.checkGetIDThanhVien(Integer.parseInt(id));
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