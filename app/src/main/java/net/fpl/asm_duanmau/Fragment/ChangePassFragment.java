package net.fpl.asm_duanmau.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import net.fpl.asm_duanmau.DAO.ThuThuDAO;
import net.fpl.asm_duanmau.LoginActivity;
import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.ThuThu;

public class ChangePassFragment extends Fragment {
    private EditText edPassCuDoimk;
    private EditText edPasswordMoiDoimk;
    private EditText edNhaplaiPasswordDoimk;
    private Button btnLuuDoimk;
    private Button btnHuyDoimk;
    ThuThuDAO dao;


    public ChangePassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        edPassCuDoimk = (EditText) view.findViewById(R.id.ed_pass_cu_doimk);
        edPasswordMoiDoimk = (EditText) view.findViewById(R.id.ed_password_moi_doimk);
        edNhaplaiPasswordDoimk = (EditText) view.findViewById(R.id.ed_nhaplai_password_doimk);
        btnLuuDoimk = (Button) view.findViewById(R.id.btn_luu_doimk);
        btnHuyDoimk = (Button) view.findViewById(R.id.btn_huy_doimk);
        dao = new ThuThuDAO(getActivity());



        btnHuyDoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassCuDoimk.setText("");
                edPasswordMoiDoimk.setText("");
                edNhaplaiPasswordDoimk.setText("");
            }
        });


        btnLuuDoimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lay lai user, pass trong sharepreferces
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = sharedPreferences.getString("USERNAME", "");
                if (validate() > 0) {
                    ThuThu thuThu = dao.getID(user);//lay ra thu thu hien tai
                    thuThu.setMk(edPasswordMoiDoimk.getText().toString());//set lại mật khẩu
                    int result = dao.update(thuThu);
                    if (result > 0) {
                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edPassCuDoimk.setText("");
                        edPasswordMoiDoimk.setText("");
                        edNhaplaiPasswordDoimk.setText("");
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        return view;
    }

    private int validate() {
        int check = 1;
        if (edPassCuDoimk.getText().toString().isEmpty() || edPasswordMoiDoimk.getText().toString().isEmpty() || edNhaplaiPasswordDoimk.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = sharedPreferences.getString("PASS", "");

            String passOld_Nhap = edPassCuDoimk.getText().toString();
            String pass = edPasswordMoiDoimk.getText().toString();
            String repass = edNhaplaiPasswordDoimk.getText().toString();

            if (!passOld.equals(passOld_Nhap)) {
                Toast.makeText(getContext(), "Bạn  nhập sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(repass)) {
                Toast.makeText(getContext(), "Mật khẩu nhập lại không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}