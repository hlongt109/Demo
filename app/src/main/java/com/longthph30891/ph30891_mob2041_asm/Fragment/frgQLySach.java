package com.longthph30891.ph30891_mob2041_asm.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.longthph30891.ph30891_mob2041_asm.Adapter.AdapterSach;
import com.longthph30891.ph30891_mob2041_asm.Adapter.TheLoaiSachAdapter;
import com.longthph30891.ph30891_mob2041_asm.DAO.loaiSachDAO;
import com.longthph30891.ph30891_mob2041_asm.DAO.sachDAO;
import com.longthph30891.ph30891_mob2041_asm.Model.LoaiSach;
import com.longthph30891.ph30891_mob2041_asm.Model.Sach;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.util.ArrayList;

public class frgQLySach extends Fragment {
    public frgQLySach() {
    }
    private ArrayList<Sach> list = new ArrayList<>();
    sachDAO sDAO;
    AdapterSach adapterSach;
    TheLoaiSachAdapter theLoaiSachAdapter;
    ArrayList<LoaiSach>listLs;
    loaiSachDAO lsDAO;
    int matl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_q_ly_sach, container, false);
        RecyclerView rcvS = view.findViewById(R.id.rcv_S);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd_S);
        //
        sDAO = new sachDAO(getActivity());
        list = sDAO.selectAll();
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        rcvS.setLayoutManager(layout);
        adapterSach = new AdapterSach(list,getActivity());
        rcvS.setAdapter(adapterSach);
        // btnAdd
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpendialogAdd();
            }
        });
        return view;
    }

    private void OpendialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = ((Activity) getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.them_sach_layout, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        // anh xa
        TextView tvMaS = view.findViewById(R.id.tvMaS_them);
        TextInputLayout ilTenS = view.findViewById(R.id.ilTenS_them);
        TextInputEditText edTenS = view.findViewById(R.id.edTenS_them);
        TextInputLayout ilGiaS = view.findViewById(R.id.ilGiaS_them);
        TextInputEditText edGiaS = view.findViewById(R.id.edGiaS_them);
        Spinner spinnerLs = view.findViewById(R.id.spLoaiSachS_them);
        Button btnSave = view.findViewById(R.id.btnSave_S_them);
        Button btnCancel = view.findViewById(R.id.btnCancel_S_them);
        //
        // spinner
        listLs = new ArrayList<>();
        lsDAO = new loaiSachDAO(getActivity());
        listLs = lsDAO.selectAll();
        theLoaiSachAdapter = new TheLoaiSachAdapter(getActivity(),listLs);
        spinnerLs.setAdapter(theLoaiSachAdapter);
        spinnerLs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                matl = listLs.get(position).getMaTheLoai();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String tenS = edTenS.getText().toString();
                String giaS = edGiaS.getText().toString();
                if(tenS.isEmpty()||giaS.isEmpty()){
                    if (tenS.isEmpty()) {
                        ilTenS.setError("Không để trống tên sách !");
                    }else{
                        ilTenS.setError(null);
                    }
                    if (giaS.isEmpty()) {
                        ilGiaS.setError("Không để trống giá sách !");
                    }else{
                        ilGiaS.setError(null);
                    }
                }else{
                    try {
                        int money = Integer.parseInt(giaS);
                        if (money <= 0){
                            ilGiaS.setError("Giá phải lớn hơn 0");
                            return;
                        }
                    }catch (NumberFormatException e){
                        ilGiaS.setError("Giá phải là số!");
                        return;
                    }
                    int ma = matl;
                    int gia = Integer.parseInt(giaS);
                    Log.d("AAA","Ma :"+ma);
                   Sach item = new Sach(tenS,gia,ma);
                    if (sDAO.insert(item)){
                        list.clear();
                        list.addAll(sDAO.selectAll());
                        adapterSach.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Lỗi thêm !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}