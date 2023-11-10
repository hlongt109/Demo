package com.longthph30891.ph30891_mob2041_asm.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.longthph30891.ph30891_mob2041_asm.DAO.phieuMuonDAO;
import com.longthph30891.ph30891_mob2041_asm.DAO.sachDAO;
import com.longthph30891.ph30891_mob2041_asm.DAO.thanhVienDAO;
import com.longthph30891.ph30891_mob2041_asm.Model.PhieuMuon;
import com.longthph30891.ph30891_mob2041_asm.Model.Sach;
import com.longthph30891.ph30891_mob2041_asm.Model.ThanhVien;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.util.ArrayList;

public class AdapterPhieuMuon extends RecyclerView.Adapter<AdapterPhieuMuon.viewHolder> {
    private final Context context;
    private final ArrayList<PhieuMuon> list;
    phieuMuonDAO pmDAO;
    SpinnerTenTvAdapter spinnerTenTvAdapter;
    SpinnerTenSachAdapter spinnerTenSachAdapter;
    ArrayList<ThanhVien> listTv;
    ArrayList<Sach> listSach;
    sachDAO sDAO;
    thanhVienDAO tvDAO;
    int maTv, maS;

    public AdapterPhieuMuon(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        pmDAO = new phieuMuonDAO(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieu_muon, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        PhieuMuon pm = list.get(position);
        // hien thi du lieu len view
        holder.tvMaPhieu.setText(String.valueOf(list.get(position).getMaPhieu()));
        String maThuThu = pm.getMaTT();
        holder.tvThuthu.setText(pmDAO.getTenThuThu(maThuThu));
        int maThanhV = pm.getMaTV();
        holder.tvThanhVien.setText(pmDAO.getTenThanhVien(maThanhV));
        int maSach = pm.getMaSach();
        holder.tvTenSach.setText(pmDAO.getTenSach(maSach));
        holder.tvTienThue.setText(String.valueOf(pmDAO.getGiaTien(maSach)));
        holder.tvNgayThue.setText(list.get(position).getNgayMuon());
        if (list.get(position).getTrangThai() == 1) {
            holder.tvTrangThai.setText("Đã trả sách");
            holder.tvTrangThai.setTextColor(ContextCompat.getColor(context, R.color.trangThai));
        }
        else {
            holder.tvTrangThai.setText("Chưa trả sách");
            holder.tvTrangThai.setTextColor(ContextCompat.getColor(context, R.color.chuatra));
        }

        //
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_warning);
                builder.setTitle(" Thông Báo");
                builder.setMessage("Bạn có chắc muốn xóa loại sách này không ?");
                builder.setNegativeButton("Hủy", null);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (pmDAO.delete(pm.getMaPhieu())) {
                            list.clear();
                            list.addAll(pmDAO.selectAll());
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Lỗi xóa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.create().show();
            }
        });
        // onLongClick
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDiaLogUpdate(pm);
                return true;
            }
        });
    }
    @SuppressLint("MissingInflatedId")
    private void openDiaLogUpdate(PhieuMuon pm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_pm_layout, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //
        TextView tvMaPhieu = view.findViewById(R.id.tvMaPM_up);
        Spinner spTenTV = view.findViewById(R.id.sp_TenTv_Pm_up);
        Spinner spTenSach = view.findViewById(R.id.sp_TenS_Pm_up);
        TextView tvThuthu = view.findViewById(R.id.tvThuThu_up);
        TextView tvTienThue = view.findViewById(R.id.tvTienPM_up);
        TextView tvNgayThue = view.findViewById(R.id.tvNgayPM_up);
        CheckBox chkTrThai = view.findViewById(R.id.chkTrangThai_Pm_up);
        Button btnUpdate = view.findViewById(R.id.btnUpdate_Pm_Up);
        Button btnCancel = view.findViewById(R.id.btnCancel_Pm_Up);
        // hien thi du lieu len dialog
        tvMaPhieu.setText(String.valueOf(pm.getMaPhieu()));
        String tentt = pm.getMaTT();
        tvThuthu.setText(pmDAO.getTenThuThu(tentt));
        tvTienThue.setText(String.valueOf(pm.getTienThue()));
        tvNgayThue.setText(pm.getNgayMuon());
        if (pm.getTrangThai() == 1) {
            chkTrThai.setChecked(true);
        }
        int currentMaTv = pm.getMaTV();
        int currentMaSach = pm.getMaSach();

        // spinner
        listTv = new ArrayList<>();
        tvDAO = new thanhVienDAO(context);
        listTv = tvDAO.selectAll();
        spinnerTenTvAdapter = new SpinnerTenTvAdapter(context, listTv);
        spTenTV.setAdapter(spinnerTenTvAdapter);
        for (int i = 0; i < listTv.size(); i++) {
            if (listTv.get(i).getMaTV() == currentMaTv) {
                spTenTV.setSelection(i);
                maTv = currentMaSach;
                break;
            }
        }
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
        sDAO = new sachDAO(context);
        listSach = sDAO.selectAll();
        spinnerTenSachAdapter = new SpinnerTenSachAdapter(context, listSach);
        spTenSach.setAdapter(spinnerTenSachAdapter);
        for (int i = 0; i < listSach.size(); i++) {
            if (listSach.get(i).getMaSach() == currentMaSach) {
                spTenSach.setSelection(i);
                maS = currentMaSach;
                break;
            }
        }
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
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status;
                pm.setMaTV(maTv);
                pm.setMaSach(maS);
                if (chkTrThai.isChecked()) {
                    status = 1;
                    pm.setTrangThai(status);
                } else {
                    status = 0;
                    pm.setTrangThai(status);
                }
                if(pmDAO.update(pm)){
                    list.clear();
                    list.addAll(pmDAO.selectAll());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPhieu, tvThanhVien,tvThuthu, tvTenSach, tvTienThue, tvNgayThue, tvTrangThai;
        ImageButton imgDelete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieu = itemView.findViewById(R.id.tvMaPM_itPm);
            tvThanhVien = itemView.findViewById(R.id.tvThanhVien_itPm);
            tvTenSach = itemView.findViewById(R.id.tvTenSach_itPm);
            tvThuthu = itemView.findViewById(R.id.tvThuThu_itPm);
            tvTienThue = itemView.findViewById(R.id.tvTienThue_itPm);
            tvNgayThue = itemView.findViewById(R.id.tvNgayThue_itPm);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai_itPm);
            imgDelete = itemView.findViewById(R.id.imgDelete_itPm);
        }
    }
}
