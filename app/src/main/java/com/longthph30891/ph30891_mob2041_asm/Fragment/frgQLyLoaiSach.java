package com.longthph30891.ph30891_mob2041_asm.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.longthph30891.ph30891_mob2041_asm.Adapter.AdapterLoaiSach;
import com.longthph30891.ph30891_mob2041_asm.DAO.loaiSachDAO;
import com.longthph30891.ph30891_mob2041_asm.Model.LoaiSach;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.util.ArrayList;


public class frgQLyLoaiSach extends Fragment {
    public frgQLyLoaiSach() {
        // Required empty public constructor
    }

    private ArrayList<LoaiSach> list = new ArrayList<>();
    loaiSachDAO lsDAO;
    AdapterLoaiSach adapterLoaiSach;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_q_ly_loai_sach, container, false);
        RecyclerView rcvLS = view.findViewById(R.id.rcv_Ls);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd_Ls);
        //
        lsDAO = new loaiSachDAO(getActivity());
        list = lsDAO.selectAll();
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        rcvLS.setLayoutManager(layout);
        adapterLoaiSach = new AdapterLoaiSach(getActivity(),list);
        rcvLS.setAdapter(adapterLoaiSach);
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
        LayoutInflater inflater = ((Activity)getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.them_loaisach_layout,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        // anh xa
        TextView tvMaLS = view.findViewById(R.id.tvMaLS_them);
        TextInputLayout ilTenLS = view.findViewById(R.id.ilTenLS_them);
        TextInputEditText edTenLS = view.findViewById(R.id.edTenLS_them);
        Button btnSave = view.findViewById(R.id.btnSave_LS_them);
        Button btnCancel = view.findViewById(R.id.btnCancel_LS_them);
        //
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLs = edTenLS.getText().toString();
                if(tenLs.isEmpty()){
                    if (tenLs.equals("")) {
                        ilTenLS.setError("Không để trống tên loại sách !");
                    }else{
                        ilTenLS.setError(null);
                    }
                }else{
                    LoaiSach ls = new LoaiSach(tenLs);
                    if (lsDAO.insert(ls)){
                        list.clear();
                        list.addAll(lsDAO.selectAll());
                        adapterLoaiSach.notifyDataSetChanged();
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