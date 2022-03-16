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
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.fpl.asm_duanmau.Adapter.LoaiSachAdapter;
import net.fpl.asm_duanmau.DAO.LoaiSachDAO;
import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;


public class LoaiSachFragment extends Fragment {
    private ListView lv;
    private FloatingActionButton fab;
    ArrayList<LoaiSach> list;
    Dialog dialog;
    TextView tvTitleDialog;
    EditText edTenLS;
    Button btnSave;
    Button btnCancel;
    LoaiSach item;
    LoaiSachDAO dao;
    LoaiSachAdapter adapter;

    public LoaiSachFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        lv = (ListView) view.findViewById(R.id.lvLoaiSach);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        dao = new LoaiSachDAO(getContext());
        capnhatLv();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getContext(),0);
            }
        });
        //update du lieu
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

    private void capnhatLv() {
        list = (ArrayList<LoaiSach>) dao.getAll();
        adapter = new LoaiSachAdapter(getActivity(),this,list);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }

    private void openDialog(Context context,int type) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.loai_sach_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        edTenLS = dialog.findViewById(R.id.ed_TenLS);
        tvTitleDialog = dialog.findViewById(R.id.tv_LoaiSach_title_dialog);
        btnSave = dialog.findViewById(R.id.btn_SaveLS);
        btnCancel = dialog.findViewById(R.id.btn_CancelLS);
        tvTitleDialog.setText("Thêm Loại Sách");
        if (type!=0){
            tvTitleDialog.setText("Sửa Loại Sách");
            edTenLS.setText(item.getTenLoai());
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
                LoaiSach item_insert = new LoaiSach();
                item_insert.setTenLoai(edTenLS.getText().toString());
                if (validate()>0) {
                    if (type == 0) {
                        // type=0 : insert
                        long result = dao.insert(item_insert);
                        if (result > 0) {
                            Toast.makeText(context, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //type=1 : update
                        item_insert.setMaLoai(item.getMaLoai());
                        int result = dao.update(item_insert);
                        if (result > 0) {
                            Toast.makeText(context, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capnhatLv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();


    }

    private int validate() {
        int check =1;
        if (edTenLS.getText().toString().isEmpty()){
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
                List<LoaiSach> list_check = dao.checkGetIDLoaiSach(Integer.parseInt(id));
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
                    capnhatLv();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });
    }

}