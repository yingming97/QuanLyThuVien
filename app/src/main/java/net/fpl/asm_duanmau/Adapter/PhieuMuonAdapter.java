package net.fpl.asm_duanmau.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.fpl.asm_duanmau.DAO.SachDAO;
import net.fpl.asm_duanmau.DAO.ThanhVienDAO;
import net.fpl.asm_duanmau.Fragment.PhieuMuonFragment;
import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.PhieuMuon;
import net.fpl.asm_duanmau.model.Sach;
import net.fpl.asm_duanmau.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuMuonAdapter extends BaseAdapter {
    Context context;
    PhieuMuonFragment fragment;
    ArrayList<PhieuMuon> list;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public PhieuMuonAdapter(Context context, PhieuMuonFragment fragment, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getMaSach();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolDer;
        if (convertView==null){
            viewHolDer = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.phieu_muon_item,null);
            viewHolDer.tvMaPM = convertView.findViewById(R.id.tv_MaPM);
            viewHolDer.tvTenTV = convertView.findViewById(R.id.tv_TenTV_phieumuon);
            viewHolDer.tvTenSach = convertView.findViewById(R.id.tv_TenSach_phieumuon);
            viewHolDer.tvTienThue = convertView.findViewById(R.id.tv_TienThue_phieumuon);
            viewHolDer.tvTraSach = convertView.findViewById(R.id.tv_TraSach);
            viewHolDer.tvNgay = convertView.findViewById(R.id.tv_NgayThue);
            viewHolDer.imgDel = convertView.findViewById(R.id.img_DeletePM);
            convertView.setTag(viewHolDer);
        }else {
            viewHolDer = (ViewHolder) convertView.getTag();
        }
        PhieuMuon item = list.get(position);
        viewHolDer.tvMaPM.setText("Mã PM: " + item.getMaPM());
        SachDAO sachDAO = new SachDAO(context);
        Sach sach = sachDAO.getID(item.getMaSach()+"");
        viewHolDer.tvTenSach.setText("Tên Sách: "+sach.getTenSach());
        ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
        ThanhVien thanhVien = thanhVienDAO.getID(item.getMaTV()+"");
        viewHolDer.tvTenTV.setText("Tên TV: "+thanhVien.getHoTen());
        //Tien Thue
        viewHolDer.tvTienThue.setText("Tiền Thuê: "+item.getTienThue() + " VNĐ");
        //Ngay Thuê
        viewHolDer.tvNgay.setText("Ngày Thuê: "+simpleDateFormat.format(item.getNgay()));
        //tra sách
        if (item.getTrangThai()==1){
            viewHolDer.tvTraSach.setTextColor(Color.BLUE);
            viewHolDer.tvTraSach.setText("Đã Trả Sách");
        }else {
            viewHolDer.tvTraSach.setTextColor(Color.RED);
            viewHolDer.tvTraSach.setText("Chưa Trả Sách");
        }
        //xoa
        viewHolDer.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.getMaPM()));

            }
        });
        if (position%2==0){
            convertView.setBackgroundColor(Color.parseColor("#03A9F4"));
        }else {
            convertView.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }



        return convertView;
    }

    private class ViewHolder {
        TextView tvMaPM;
        TextView tvTenTV;
        TextView tvTenSach;
        TextView tvTraSach;
        TextView tvTienThue;
        TextView tvNgay;
        ImageView imgDel;
    }
}
