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
import com.longthph30891.ph30891_mob2041_asm.Adapter.AdapterThanhVien;
import com.longthph30891.ph30891_mob2041_asm.DAO.thanhVienDAO;
import com.longthph30891.ph30891_mob2041_asm.Model.ThanhVien;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.util.ArrayList;

public class frgQLyThanhVien extends Fragment {
    public frgQLyThanhVien() {
        // Required empty public constructor
    }
    private ArrayList<ThanhVien> list = new ArrayList<>();
    thanhVienDAO tvDAO;
    AdapterThanhVien adapterThanhVien;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_q_ly_thanh_vien, container, false);
        RecyclerView rcv = view.findViewById(R.id.rcv_Tv);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd_Tv);
        //
        tvDAO = new thanhVienDAO(getActivity());
        list = tvDAO.selectAll();
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layout);
        adapterThanhVien = new AdapterThanhVien(getActivity(),list);
        rcv.setAdapter(adapterThanhVien);
        //
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
        View view = inflater.inflate(R.layout.them_tv_layout,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //
        TextView tvMa = view.findViewById(R.id.tvMaTV_them);
        TextInputLayout ilTenTv = view.findViewById(R.id.ilTenTV_them);
        TextInputLayout ilNamSinh = view.findViewById(R.id.ilNamSinhTV_them);
        TextInputEditText edTentv = view.findViewById(R.id.edTenTV_them);
        TextInputEditText edNamStv = view.findViewById(R.id.edNamSinhTV_them);
        Button btnSave = view.findViewById(R.id.btnSave_TV_them);
        Button btnCancel = view.findViewById(R.id.btnCancel_TV_them);
        //
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTv = edTentv.getText().toString();
                String namS = edNamStv.getText().toString();
                if (tenTv.isEmpty()||namS.isEmpty()){
                    if (tenTv.isEmpty()){
                        ilTenTv.setError("Vui lòng nhập tên thành viên");
                    }else {
                        ilTenTv.setError(null);
                    }
                    if (namS.isEmpty()){
                        if(namS.isEmpty()){
                            ilNamSinh.setError("Vui lòng nhập năm sinh");
                        }else {
                            ilNamSinh.setError(null);
                        }
                    }
                }else {
                    ThanhVien tv = new ThanhVien(tenTv,namS);
                    if (tvDAO.insert(tv)){
                        list.clear();
                        list.addAll(tvDAO.selectAll());
                        adapterThanhVien.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Error !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}