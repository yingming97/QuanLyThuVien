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

import net.fpl.asm_duanmau.Fragment.LoaiSachFragment;
import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachAdapter extends BaseAdapter {
    Context context;
    LoaiSachFragment fragment;
    ArrayList<LoaiSach> lists;
    public LoaiSachAdapter(Context context, LoaiSachFragment fragment, ArrayList<LoaiSach> lists) {
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
        return lists.get(position).getMaLoai();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolDer viewHolDer;
        if (convertView==null){
            viewHolDer = new ViewHolDer();
            convertView = LayoutInflater.from(context).inflate(R.layout.loai_sach_item,null);
            viewHolDer.tvMaLoaiSach = convertView.findViewById(R.id.tv_MaLS);
            viewHolDer.tvTenLoaiSach = convertView.findViewById(R.id.tv_TenLS);
            viewHolDer.imgDel = convertView.findViewById(R.id.img_DeleteLS);
             convertView.setTag(viewHolDer);
        }else {
            viewHolDer = (ViewHolDer) convertView.getTag();
        }
        LoaiSach item = lists.get(position);
        viewHolDer.tvMaLoaiSach.setText("Mã LS: " + item.getMaLoai());
        viewHolDer.tvTenLoaiSach.setText("Tên LS: "+item.getTenLoai());
        //xoa
        viewHolDer.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment.xoa(String.valueOf(item.getMaLoai()));
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
        TextView tvMaLoaiSach;
        TextView tvTenLoaiSach;
        ImageView imgDel;

    }
}
