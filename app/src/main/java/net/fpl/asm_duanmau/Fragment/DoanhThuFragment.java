package net.fpl.asm_duanmau.Fragment;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import net.fpl.asm_duanmau.DAO.PhieuMuonDAO;
import net.fpl.asm_duanmau.DAO.ThongKeDAO;
import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.PhieuMuon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class DoanhThuFragment extends Fragment {
    private TextView tvTuNgay;
    private TextView tvDenNgay;
    private Button btnShow;
    private TextView tvTong;
    private TextView tvTongtientheokhoang;
    ThongKeDAO thongKeDAO;
    ArrayList<PhieuMuon> list;
    PhieuMuonDAO phieuMuonDAO;
    int Tong;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public DoanhThuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        tvTuNgay = (TextView) view.findViewById(R.id.tv_tu);
        tvDenNgay = (TextView) view.findViewById(R.id.tv_den);
        btnShow = (Button) view.findViewById(R.id.btn_show);
        tvTong = (TextView) view.findViewById(R.id.tv_tong);
        tvTongtientheokhoang = (TextView) view.findViewById(R.id.tv_tongtientheokhoang);
        thongKeDAO = new ThongKeDAO(getContext());
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        //tong tien
        list= (ArrayList<PhieuMuon>) phieuMuonDAO.getAll();
        for (int i = 0; i < list.size(); i++) {
            Tong += list.get(i).getTienThue();
        }
        //bat dau
        tvTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                       tvTuNgay.setText(dayOfMonth+ "-"+(month+1)+"-"+year);

                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        //ket thuc
        tvDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvDenNgay.setText(dayOfMonth+ "-"+(month+1)+"-"+year);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvTong.setText(Tong+" VNĐ");
//                Date bd = null;
//                Date kt=null;
//                try {
//                     bd = simpleDateFormat.parse(tvTuNgay.getText().toString());
//                     kt = simpleDateFormat.parse(tvDenNgay.getText().toString());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                String batDau= simpleDateFormat.format(bd);
//                String ketThuc= simpleDateFormat.format(kt);
////                String batDau = tvTuNgay.getText().toString();
////                String ketThuc = tvDenNgay.getText().toString();
//                Log.e("TAG", "onClick: " + batDau );
//                Log.e("TAG", "onClick: " + ketThuc );
//                tvTongtientheokhoang.setText(thongKeDAO.getDanhThu(batDau,ketThuc)+ " VNĐ");

                int Tongtientheokhoang=0;
                String batDau = tvTuNgay.getText().toString();
                String ketThuc = tvDenNgay.getText().toString();
                for (int i = 0; i < list.size(); i++) {
                    try {
                        if (list.get(i).getNgay().compareTo(simpleDateFormat.parse(batDau))>=0&&list.get(i).getNgay().compareTo(simpleDateFormat.parse(ketThuc))<=0){
                            Tongtientheokhoang+=list.get(i).getTienThue();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                tvTongtientheokhoang.setText(Tongtientheokhoang+ " VNĐ");


            }
        });
        return view;
    }
}