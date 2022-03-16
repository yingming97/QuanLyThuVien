package net.fpl.asm_duanmau.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;


import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.Top;

import java.util.ArrayList;

public class TopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Top> list;

    public TopAdapter(Context context, ArrayList<Top> list) {
        this.context = context;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.top_item,null);
            //anh xa
            viewHolder.tvTenSachTop = convertView.findViewById(R.id.tv_TenSach_Top);
            viewHolder.tvSoLuongTop = convertView.findViewById(R.id.tv_SoLuong_Top);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Top top = list.get(position);
        viewHolder.tvTenSachTop.setText("Tên Sách : "+top.getTenSach());
        viewHolder.tvSoLuongTop.setText("Số lượng : "+top.getSoLuong());

        if (position%2==0){
            convertView.setBackgroundColor(Color.parseColor("#03A9F4"));
        }else {
            convertView.setBackgroundColor(Color.parseColor("#FFEB3B"));
        }

        return convertView;
    }

    public class ViewHolder{
        TextView tvTenSachTop;
        TextView tvSoLuongTop;
    }
}
