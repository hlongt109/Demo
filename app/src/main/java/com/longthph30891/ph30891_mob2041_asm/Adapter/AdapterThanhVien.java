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
import com.longthph30891.ph30891_mob2041_asm.DAO.thanhVienDAO;
import com.longthph30891.ph30891_mob2041_asm.Model.ThanhVien;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.util.ArrayList;

public class AdapterThanhVien extends RecyclerView.Adapter<AdapterThanhVien.viewHolder>{
    private Context context;
    private ArrayList<ThanhVien> list;
    thanhVienDAO tvDAO;

    public AdapterThanhVien(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
        tvDAO = new thanhVienDAO(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanhvien,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        // hien thi du lieu len view
        holder.tvMa.setText(String.valueOf(list.get(position).getMaTV()));
        holder.tvTen.setText(list.get(position).getHoTenTV());
        holder.tvNamSinh.setText(list.get(position).getNamSinh());
        //
        ThanhVien tv = list.get(position);
        //
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_warning);
                builder.setTitle(" Thông Báo");
                builder.setMessage("Bạn có chắc muốn xóa thành viên này không ?");
                builder.setNegativeButton("Hủy",null);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(tvDAO.delete(tv.getMaTV())){
                            list.clear();
                            list.addAll(tvDAO.selectAll());
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
                OnpenDiaLogUpdate(tv);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView tvMa, tvTen, tvNamSinh;
        ImageButton imgDelete;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMaTv_itTv);
            tvTen = itemView.findViewById(R.id.tvHoten_itTv);
            tvNamSinh = itemView.findViewById(R.id.tvNamSinh_itTv);
            imgDelete = itemView.findViewById(R.id.imgDelete_itTv);
        }
    }

    private void OnpenDiaLogUpdate(ThanhVien tv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_tv_layout,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        //
        TextView tvMa = view.findViewById(R.id.tvMaTV_up);
        TextInputLayout ilTenTv = view.findViewById(R.id.ilTenTV_up);
        TextInputLayout ilNamSinh = view.findViewById(R.id.ilNamSinhTV_up);
        TextInputEditText edTentv = view.findViewById(R.id.edTenTV_up);
        TextInputEditText edNamStv = view.findViewById(R.id.edNamSinhTV_up);
        Button btnUpdate = view.findViewById(R.id.btnUpdate_TV_up);
        Button btnCancel = view.findViewById(R.id.btnCancel_TV_up);
        //
        tvMa.setText(String.valueOf(tv.getMaTV()));
        edTentv.setText(tv.getHoTenTV());
        edNamStv.setText(tv.getNamSinh());
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
                String tenTv = edTentv.getText().toString();
                String namS = edNamStv.getText().toString();
                if(tenTv.isEmpty()||namS.isEmpty()){
                    if (tenTv.isEmpty()){
                        ilTenTv.setError("Vui lòng nhập tên thành viên");
                    }else {
                        ilTenTv.setError(null);
                    }
                    if (namS.isEmpty()){
                        ilNamSinh.setError("Vui lòng nhập năm sinh");
                    }else {
                        ilNamSinh.setError(null);
                    }
                }else {
                    tv.setHoTenTV(tenTv); tv.setNamSinh(namS);
                    if (tvDAO.update(tv)){
                        list.clear();
                        list.addAll(tvDAO.selectAll());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Lỗi cập nhật!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
