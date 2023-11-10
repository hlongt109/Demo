package com.longthph30891.ph30891_mob2041_asm.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longthph30891.ph30891_mob2041_asm.DAO.thongKeDAO;
import com.longthph30891.ph30891_mob2041_asm.Model.Top;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.util.ArrayList;


public class AdapterTop extends RecyclerView.Adapter<AdapterTop.viewHolder>{
    private final Context context;
    private final ArrayList<Top>list;
    thongKeDAO tkDAO;

    public AdapterTop(Context context, ArrayList<Top> list) {
        this.context = context;
        this.list = list;
        tkDAO = new thongKeDAO(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
     holder.tvTenSach.setText(list.get(position).tenSach);
     holder.tvSoLuong.setText(String.valueOf(list.get(position).soLuong));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView tvTenSach, tvSoLuong;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSach_itTop);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong_itTop);
        }
    }
}
