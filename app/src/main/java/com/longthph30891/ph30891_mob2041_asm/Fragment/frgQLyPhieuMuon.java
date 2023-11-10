package com.longthph30891.ph30891_mob2041_asm.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.longthph30891.ph30891_mob2041_asm.Adapter.AdapterPhieuMuon;
import com.longthph30891.ph30891_mob2041_asm.Adapter.SpinnerTenSachAdapter;
import com.longthph30891.ph30891_mob2041_asm.Adapter.SpinnerTenTvAdapter;
import com.longthph30891.ph30891_mob2041_asm.DAO.phieuMuonDAO;
import com.longthph30891.ph30891_mob2041_asm.DAO.sachDAO;
import com.longthph30891.ph30891_mob2041_asm.DAO.thanhVienDAO;
import com.longthph30891.ph30891_mob2041_asm.Model.PhieuMuon;
import com.longthph30891.ph30891_mob2041_asm.Model.Sach;
import com.longthph30891.ph30891_mob2041_asm.Model.ThanhVien;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class frgQLyPhieuMuon extends Fragment {
    public frgQLyPhieuMuon() {
        // Required empty public constructor
    }
    private ArrayList<PhieuMuon> list = new ArrayList<>();
    phieuMuonDAO pmDAO;
    AdapterPhieuMuon adapter;
    SpinnerTenTvAdapter spinnerTenTvAdapter;
    SpinnerTenSachAdapter spinnerTenSachAdapter;
    ArrayList<ThanhVien> listTv;
    ArrayList<Sach> listSach;
    sachDAO sDAO;
    thanhVienDAO tvDAO;
    int maTv, maS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_q_ly_phieu_muon, container, false);
        RecyclerView rcv = view.findViewById(R.id.rcv_pm);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd_pm);
        //
        pmDAO = new phieuMuonDAO(getActivity());
        list = pmDAO.selectAll();
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layout);
        adapter = new AdapterPhieuMuon(getActivity(),list);
        rcv.setAdapter(adapter);
        //
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDialogAdd();
            }
        });
        return view;
    }

    private void OpenDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = ((Activity) getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.them_pm_layout, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //
        TextView tvMaPhieu = view.findViewById(R.id.tvMaPM_them);
        Spinner spTenTV = view.findViewById(R.id.sp_TenTv_Pm_them);
        Spinner spTenSach = view.findViewById(R.id.sp_TenS_Pm_them);
        TextView tvTienThue = view.findViewById(R.id.tvTienPM_them);
        TextView tvNgayThue = view.findViewById(R.id.tvNgayPM_them);
        CheckBox chkTrThai = view.findViewById(R.id.chkTrangThai_Pm_them);
        Button btnSave = view.findViewById(R.id.btnSave_Pm_them);
        Button btnCancel = view.findViewById(R.id.btnCancel_Pm_them);
        // spinner
        listTv = new ArrayList<>();
        tvDAO = new thanhVienDAO(getActivity());
        listTv = tvDAO.selectAll();
        spinnerTenTvAdapter = new SpinnerTenTvAdapter(getActivity(), listTv);
        spTenTV.setAdapter(spinnerTenTvAdapter);
        spTenTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTv = listTv.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //
        listSach = new ArrayList<>();
        sDAO = new sachDAO(getActivity());
        listSach = sDAO.selectAll();
        spinnerTenSachAdapter = new SpinnerTenSachAdapter(getActivity(), listSach);
        spTenSach.setAdapter(spinnerTenSachAdapter);
        spTenSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maS = listSach.get(position).getMaSach();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String currentDate = sdf.format(new Date());
                String maThuThu = matt();
                int tienThue = pmDAO.getGiaTien(maS);
                int status = trangThai(chkTrThai);
                Log.d("Testpm","TienThue : "+tienThue);
                Log.d("Testpm","mas"+maS + " maThuThu"+maThuThu + " maTv"+maTv +" ngay"+currentDate + " trangThai"+status);
                PhieuMuon phieuM = new PhieuMuon(maS,maThuThu,maTv,tienThue,currentDate,status);
                if (pmDAO.insert(phieuM)){
                    list.clear();
                    list.addAll(pmDAO.selectAll());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Lỗi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public int trangThai(CheckBox chk){
        if(chk.isChecked()){
            return 1;
        }else{
            return 0;
        }
    }
    public String matt(){
        Intent intent = getActivity().getIntent();
        String maThuT = intent.getStringExtra("USERNAME");
        return maThuT;
    }
    public int gia (int ma){
        int giaTien = pmDAO.getGiaTien(ma);
        return giaTien;
    }
}