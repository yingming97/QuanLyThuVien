package net.fpl.asm_duanmau.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import net.fpl.asm_duanmau.Fragment.ThanhVienFragment;
import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends BaseAdapter {
    Context context;
    ThanhVienFragment fragment;
    ArrayList<ThanhVien> lists;

    public ThanhVienAdapter(Context context, ThanhVienFragment fragment, ArrayList<ThanhVien> lists) {
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        if (lists!=null){
            return lists.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lists.get(position).getMaTV();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolDer viewHolDer;
        if (convertView==null){
            viewHolDer = new ViewHolDer();
            convertView = LayoutInflater.from(context).inflate(R.layout.thanh_vien_item,null);
            viewHolDer.tvMaTV = convertView.findViewById(R.id.tv_MaTV);
            viewHolDer.tvTen = convertView.findViewById(R.id.tv_TenTV);
            viewHolDer.tvPhone = convertView.findViewById(R.id.tv_phone);
            viewHolDer.imgDel = convertView.findViewById(R.id.img_DeleteTV);
             convertView.setTag(viewHolDer);
        }else {
            viewHolDer = (ViewHolDer) convertView.getTag();
        }
         ThanhVien item = lists.get(position);
        viewHolDer.tvMaTV.setText("Mã TV: " + item.getMaTV());
        viewHolDer.tvTen.setText("Tên TV: "+item.getHoTen());
        viewHolDer.tvPhone.setText("SĐT "+item.getPhone());

        //xoa
        viewHolDer.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gọi method xoa  trong ThanhVien Fragment
                fragment.xoa(String.valueOf(item.getMaTV()));

            }
        });

        if (position%2==0){
            convertView.setBackgroundColor(Color.parseColor("#03A9F4"));
        }else {
            convertView.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }
        return convertView;
    }



    public class ViewHolDer{
        TextView tvMaTV;
        TextView tvTen;
        TextView tvPhone;
        ImageView imgDel;

    }
}
