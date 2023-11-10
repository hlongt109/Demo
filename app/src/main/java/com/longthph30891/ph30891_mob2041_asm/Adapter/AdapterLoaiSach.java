package com.longthph30891.ph30891_mob2041_asm.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.longthph30891.ph30891_mob2041_asm.DAO.loaiSachDAO;
import com.longthph30891.ph30891_mob2041_asm.Model.LoaiSach;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.util.ArrayList;

public class AdapterLoaiSach extends RecyclerView.Adapter<AdapterLoaiSach.viewHolder>{
   private final Context context;
   private final ArrayList<LoaiSach> list;
   loaiSachDAO lsDAO;

    public AdapterLoaiSach(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
        this.list = list;
        lsDAO = new loaiSachDAO(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loaisach,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        // hien thi du lieu len item
        holder.tvMaLS.setText(String.valueOf(list.get(position).getMaTheLoai()));
        holder.tvTenLS.setText(list.get(position).getTenTheLoai());
        // truy cập đối tượng tại vtri position
        LoaiSach ls = list.get(position);
        // imgDelete
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_warning);
                builder.setTitle(" Thông Báo");
                builder.setMessage("Bạn có chắc muốn xóa loại sách này không ?");
                builder.setNegativeButton("Hủy",null);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(lsDAO.delete(ls.getMaTheLoai())){
                            list.clear();
                            list.addAll(lsDAO.selectAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else {
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
               OnpenDiaLogUpdate(ls);
               return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView tvMaLS, tvTenLS;
        ImageButton imgDelete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaLS = itemView.findViewById(R.id.tvMaLoai_itLs);
            tvTenLS = itemView.findViewById(R.id.tvTenLoai_itLs);
            imgDelete = itemView.findViewById(R.id.imgDelete_itLs);
        }
    }
    private void OnpenDiaLogUpdate(LoaiSach ls) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_loaisach_layout,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        // anh xa
        TextView tvMaLS = view.findViewById(R.id.tvMaLS_up);
        TextInputLayout ilTenLS = view.findViewById(R.id.ilTenLS_up);
        TextInputEditText edTenLS = view.findViewById(R.id.edTenLS_up);
        Button btnUpdate = view.findViewById(R.id.btnUpdate_LS_up);
        Button btnCancel = view.findViewById(R.id.btnCancel_LS_up);
        // hien thi du lieu len Dialog
        tvMaLS.setText(String.valueOf(ls.getMaTheLoai()));
        edTenLS.setText(ls.getTenTheLoai());
        // btnUpdate
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTl = edTenLS.getText().toString();
                if(tenTl.isEmpty()){
                    if (tenTl.equals("")) {
                        ilTenLS.setError("Không để trống tên thể loại !");
                    }else{
                        ilTenLS.setError(null);
                    }
                }else{
                    ls.setTenTheLoai(tenTl);
                    if (lsDAO.update(ls)){
                        list.clear();
                        list.addAll(lsDAO.selectAll());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Lỗi cập nhật!", Toast.LENGTH_SHORT).show();
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
