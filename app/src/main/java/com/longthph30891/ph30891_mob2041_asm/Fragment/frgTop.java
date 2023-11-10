package com.longthph30891.ph30891_mob2041_asm.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longthph30891.ph30891_mob2041_asm.Adapter.AdapterTop;
import com.longthph30891.ph30891_mob2041_asm.DAO.thongKeDAO;
import com.longthph30891.ph30891_mob2041_asm.Model.Top;
import com.longthph30891.ph30891_mob2041_asm.R;
import java.util.ArrayList;

public class frgTop extends Fragment {
    public frgTop() {
    }
    private ArrayList<Top> list = new ArrayList<>();
    thongKeDAO tkDAO;
    AdapterTop adapterTop;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_frg_top, container, false);
        RecyclerView rcv = view.findViewById(R.id.rcv_top);
        //
        tkDAO = new thongKeDAO(getContext());
        list = tkDAO.getTop10();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapterTop = new AdapterTop(getContext(),list);
        rcv.setAdapter(adapterTop);
        return view;
    }
}