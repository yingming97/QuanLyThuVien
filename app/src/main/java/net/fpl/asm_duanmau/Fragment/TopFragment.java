package net.fpl.asm_duanmau.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


import net.fpl.asm_duanmau.Adapter.TopAdapter;
import net.fpl.asm_duanmau.DAO.ThongKeDAO;
import net.fpl.asm_duanmau.R;
import net.fpl.asm_duanmau.model.Top;

import java.util.ArrayList;


public class TopFragment extends Fragment {

    private ListView lv;
    TopAdapter adapter;
    ArrayList<Top> list;
    ThongKeDAO dao;


    public TopFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        lv = (ListView) view.findViewById(R.id.lvTop);
        dao = new ThongKeDAO(getActivity());
        list = (ArrayList<Top>) dao.getTop();
        adapter = new TopAdapter(getActivity(),list);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
        return view;
    }
}