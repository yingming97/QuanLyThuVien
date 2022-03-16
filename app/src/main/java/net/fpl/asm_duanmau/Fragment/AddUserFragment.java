package net.fpl.asm_duanmau.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import net.fpl.asm_duanmau.DAO.ThuThuDAO;
import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.ThuThu;


public class AddUserFragment extends Fragment {
    private EditText edUserAddtk;
    private EditText edHotenAddtk;
    private EditText edPasswordAddtk;
    private EditText edNhaplaiPasswordAddtk;
    private Button btnLuuAddtk;
    private Button btnHuyAddtk;
   ThuThuDAO dao;

    public AddUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
        edUserAddtk = (EditText) view.findViewById(R.id.ed_user_addtk);
        edHotenAddtk = (EditText) view.findViewById(R.id.ed_hoten_addtk);
        edPasswordAddtk = (EditText) view.findViewById(R.id.ed_password_addtk);
        edNhaplaiPasswordAddtk = (EditText) view.findViewById(R.id.ed_nhaplai_password_addtk);
        btnLuuAddtk = (Button) view.findViewById(R.id.btn_luu_addtk);
        btnHuyAddtk = (Button) view.findViewById(R.id.btn_huy_addtk);
        dao = new ThuThuDAO(getActivity());



        btnHuyAddtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUserAddtk.setText("");
                edHotenAddtk.setText("");
                edPasswordAddtk.setText("");
                edNhaplaiPasswordAddtk.setText("");
            }
        });

        btnLuuAddtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()>0){
                    ThuThu thuThu = new ThuThu();
                    thuThu.setMaTT(edUserAddtk.getText().toString());
                    thuThu.setHoTen(edHotenAddtk.getText().toString());
                    thuThu.setMk(edPasswordAddtk.getText().toString());
                    long result = dao.insert(thuThu);
                    if (result>0){
                        Toast.makeText(getContext(), "Thêm Tài Khoản Thành Công", Toast.LENGTH_SHORT).show();
                        edUserAddtk.setText("");
                        edHotenAddtk.setText("");
                        edPasswordAddtk.setText("");
                        edNhaplaiPasswordAddtk.setText("");
                    }else {
                        Toast.makeText(getContext(), "Thêm Tài Khoản Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    private int validate() {
        int check=1;
        if (edUserAddtk.getText().toString().isEmpty()||edHotenAddtk.getText().toString().isEmpty()||edPasswordAddtk.getText().toString().isEmpty()||edNhaplaiPasswordAddtk.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check=-1;
        }else {
            String pass =edPasswordAddtk.getText().toString();
            String repass =edNhaplaiPasswordAddtk.getText().toString();
            if (!pass.equals(repass)){
                Toast.makeText(getContext(), "Mật khẩu nhập lại không trùng khớp", Toast.LENGTH_SHORT).show();
                check=-1;
            }
        }
        return check;
    }
}