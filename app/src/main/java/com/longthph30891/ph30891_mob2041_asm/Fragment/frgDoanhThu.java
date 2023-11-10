package com.longthph30891.ph30891_mob2041_asm.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.longthph30891.ph30891_mob2041_asm.DAO.thongKeDAO;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class frgDoanhThu extends Fragment {
    public frgDoanhThu() {

    }
   thongKeDAO tkDAO;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_doanh_thu, container, false);
        Button btnDayStart, btnDayTo, btnXemDthu;
        EditText edDayStart, edDayTo;
        TextView tvDoanhthu;
        btnDayStart = view.findViewById(R.id.btnDayStart);
        btnDayTo = view.findViewById(R.id.btnDayTo);
        btnXemDthu = view.findViewById(R.id.btnXemDoanhThu);
        edDayStart = view.findViewById(R.id.edDayStart);
        edDayTo = view.findViewById(R.id.edDayTo);
        tvDoanhthu = view.findViewById(R.id.tvDoanhThu);
        //
        btnDayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formattedDay = String.format("%02d", dayOfMonth);
                        String formattedMonth = String.format("%02d", month + 1);
                        edDayStart.setText(formattedDay +"/"+formattedMonth+"/"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        btnDayTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Định dạng ngày và tháng thành hai chữ số
                        String formattedDay = String.format("%02d", dayOfMonth);
                        String formattedMonth = String.format("%02d", month + 1);

                        edDayTo.setText(formattedDay +"/"+formattedMonth+"/"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        btnXemDthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tkDAO = new thongKeDAO(getActivity());
                String ngayBatDau = edDayStart.getText().toString();
                String ngayToi = edDayTo.getText().toString();
                //
                if (checkDay(ngayBatDau,ngayToi,edDayStart,edDayTo)){
                    int doanhThu = tkDAO.getDoanhThu(ngayBatDau,ngayToi);
                    tvDoanhthu.setText(String.valueOf(doanhThu));
                }else {
                    tvDoanhthu.setText("");
                }
            }
        });
        return view;
    }
    public boolean checkDay(String dayS , String dayT,EditText editText1,EditText editText2){
        if(TextUtils.isEmpty(dayS)|| TextUtils.isEmpty(dayT)){
            if (TextUtils.isEmpty(dayS)){
                editText1.setError("Vui lòng nhập ngày bắt đầu");
            }else{
                editText1.setError(null);
            }
            if (TextUtils.isEmpty(dayT)){
                editText2.setError("Vui lòng nhập ngày kiểm tra");
            }else{
                editText2.setError(null);
            }
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            // Kiểm tra xem các ngày có hợp lệ không
            Date startDate = sdf.parse(dayS);
            Date endDate = sdf.parse(dayT);
            Date curentDate = new Date(); // ngày hiện tai
            if(startDate.after(curentDate) || endDate.after(curentDate) ||startDate.after(endDate)){
                if(startDate.after(curentDate)){
                    editText1.setError("Không được lớn hơn ngày hiện tại");
                }else {
                    editText1.setError(null);
                }
                if(endDate.after(curentDate)){
                    editText2.setError("Không được lớn hơn ngày hiện tại");
                }else {
                    editText2.setError(null);
                }
                if(startDate.after(endDate)){
                    editText1.setError("Ngày bắt đầu không thể lớn hơn ngày đến");
                }else {
                    editText1.setError(null);
                }
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Lỗi xử lý ngày tháng", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}