package com.longthph30891.ph30891_mob2041_asm.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longthph30891.ph30891_mob2041_asm.Model.ThanhVien;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.util.ArrayList;


public class SpinnerTenTvAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ThanhVien> list;

    public SpinnerTenTvAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.spinner,parent,false);
        //
        TextView tvMa = convertView.findViewById(R.id.tv_ma_sp);
        tvMa.setText(list.get(position).getMaTV()+".");
        TextView tvName = convertView.findViewById(R.id.tv_name_sp);
        tvName.setText(list.get(position).getHoTenTV());
        return convertView;
    }
}
