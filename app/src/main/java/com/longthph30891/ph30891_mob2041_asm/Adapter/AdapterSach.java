package com.longthph30891.ph30891_mob2041_asm.Adapter;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.longthph30891.ph30891_mob2041_asm.DAO.loaiSachDAO;
import com.longthph30891.ph30891_mob2041_asm.DAO.sachDAO;
import com.longthph30891.ph30891_mob2041_asm.Model.LoaiSach;
import com.longthph30891.ph30891_mob2041_asm.Model.Sach;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.util.ArrayList;

public class AdapterSach extends RecyclerView.Adapter<AdapterSach.viewHolder> {
    private final Context context;
    private final ArrayList<Sach> list;
    TheLoaiSachAdapter theLoaiSachAdapter;
    ArrayList<LoaiSach>listLs;
    loaiSachDAO lsDAO;
    sachDAO sDAO;
    int matl;
    public AdapterSach( ArrayList<Sach> list,Context context) {
        this.list = list;
        this.context = context;
        sDAO = new sachDAO(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán layout và tạo view
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        // truy cập đối tượng tại vtri position
         Sach s = list.get(position);
        sDAO = new sachDAO(context);
        // hien thi du lieu len item
        holder.tvMaS.setText(String.valueOf(list.get(position).getMaSach()));
        holder.tvTenS.setText(list.get(position).getTenSach());
        holder.tvGiaS.setText(String.valueOf(list.get(position).getGiaThue()));
        int maloaisach = s.getMaTheLoai();   // Lấy mã loại sách từ đối tượng sách
        holder.tvLoaiS.setText(sDAO.getTenLoaiSach(maloaisach));
        // imgDelete
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
                        if (sDAO.delete(s.getMaSach())) {
                            list.clear();
                            list.addAll(sDAO.selectAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Lỗi xóa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.create().show();
            }
        });
        // longClick
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OnpenDiaLogUpdate(s);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tvMaS, tvTenS, tvGiaS, tvLoaiS;
        ImageButton imgDelete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaS = itemView.findViewById(R.id.tvMaSach_itS);
            tvTenS = itemView.findViewById(R.id.tvTenSach_itS);
            tvGiaS = itemView.findViewById(R.id.tvGiathue_itS);
            tvLoaiS = itemView.findViewById(R.id.tvLoaiSach_itS);
            imgDelete = itemView.findViewById(R.id.imgDelete_itS);
        }
    }

    private void OnpenDiaLogUpdate(Sach s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_sach_layout, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        // anh xa
        TextView tvMaS = view.findViewById(R.id.tvMaS_up);
        TextInputLayout ilTenS = view.findViewById(R.id.ilTenS_up);
        TextInputEditText edTenS = view.findViewById(R.id.edTenS_up);
        TextInputLayout ilGiaS = view.findViewById(R.id.ilGiaS_up);
        TextInputEditText edGiaS = view.findViewById(R.id.edGiaS_up);
        Spinner spinnerLs = view.findViewById(R.id.spLoaiSachS_up);
        Button btnUpdate = view.findViewById(R.id.btnUpdate_S_up);
        Button btnCancel = view.findViewById(R.id.btnCancel_S_up);
        // hien thi du lieu len Dialog
        tvMaS.setText(String.valueOf(s.getMaSach()));
        edTenS.setText(s.getTenSach());
        edGiaS.setText(String.valueOf(s.getGiaThue()));
        int currentMaLs = s.getMaTheLoai();

        // spinner
        listLs = new ArrayList<>();
        lsDAO = new loaiSachDAO(context);
        listLs = lsDAO.selectAll();
        theLoaiSachAdapter = new TheLoaiSachAdapter(context,listLs);
        spinnerLs.setAdapter(theLoaiSachAdapter);
        for (int i = 0; i < listLs.size(); i++) {
            if (listLs.get(i).getMaTheLoai() == currentMaLs) {
                spinnerLs.setSelection(i);
                matl = currentMaLs;
                break;
            }
        }
        spinnerLs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                matl = listLs.get(position).getMaTheLoai();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // btnUpdate
        btnUpdate.setOnClickListener(new View.OnClickListener() {
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
                    s.setTenSach(tenS);
                    s.setGiaThue(gia);
                    s.setMaTheLoai(ma);
                    if (sDAO.update(s)){
                        list.clear();
                        list.addAll(sDAO.selectAll());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Lỗi cập nhật !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // btnCancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
