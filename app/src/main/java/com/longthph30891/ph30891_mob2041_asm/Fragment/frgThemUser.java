package com.longthph30891.ph30891_mob2041_asm.Fragment;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.longthph30891.ph30891_mob2041_asm.DAO.thuThuDAO;
import com.longthph30891.ph30891_mob2041_asm.Model.ThuThu;
import com.longthph30891.ph30891_mob2041_asm.R;

import java.util.ArrayList;

public class frgThemUser extends Fragment {
    public frgThemUser() {
    }
    TextInputLayout ilUsn, ilFullN, ilPas, ilConP;
    TextInputEditText edUsn, edFn, edPas, edCon;
    Button btnSave, btnCancel;
    thuThuDAO ttDAO;
    private ArrayList<ThuThu> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frg_them_user, container, false);
        ilUsn = view.findViewById(R.id.il_username_themTT);
        ilFullN = view.findViewById(R.id.il_Fullname_themTT);
        ilPas = view.findViewById(R.id.il_Password_themTT);
        ilConP = view.findViewById(R.id.il_Confirm_themTT);
        edUsn = view.findViewById(R.id.edUsername_themTT);
        edFn = view.findViewById(R.id.edFullname_themTT);
        edPas = view.findViewById(R.id.edPassword_themTT);
        edCon = view.findViewById(R.id.edConfirmPass_themTT);
        btnSave = view.findViewById(R.id.btnSave_themTT);
        btnCancel = view.findViewById(R.id.btnCancel_themTT);
        //
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackMenu();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn = edUsn.getText().toString();
                String fn = edFn.getText().toString();
                String pas = edPas.getText().toString();
                String confPas = edCon.getText().toString();
                ttDAO = new thuThuDAO(getActivity());
                if (!(ttDAO.checkUserNameExist(usn)) && pas.equals(confPas) && !fn.equals("") && !pas.equals("")) {
                    ThuThu tt = new ThuThu(usn,fn,pas);
                    if(ttDAO.insert(tt)){
                        Toast.makeText(getActivity(), "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                        edUsn.setText("");
                        edFn.setText("");
                        edCon.setText("");
                        edPas.setText("");
                    }else {
                        Toast.makeText(getActivity(), "Lỗi thêm người dùng!", Toast.LENGTH_SHORT).show();
                    }
                } else if (!pas.equals(confPas)) {
                    ilPas.setError("Mật khẩu không khớp");
                    ilConP.setError("Mật khẩu không khớp");
                    if(pas.equals(confPas)) {
                        ilPas.setError(null);
                        ilConP.setError(null);
                    }
                }else {
                    if(usn.isEmpty() || fn.isEmpty() || pas.isEmpty() || confPas.isEmpty()){
                        if (usn.isEmpty()){
                            ilUsn.setError("Không để trống tên đăng nhập");
                        }else {
                            ilUsn.setError(null);
                        }
                        if(fn.isEmpty()){
                            ilFullN.setError("Không để trống họ tên");
                        }else {
                            ilFullN.setError(null);
                        }
                        if(pas.isEmpty()){
                            ilPas.setError("Không để trống mật khẩu");
                        }else {
                            ilPas.setError(null);
                        }
                        if(confPas.isEmpty()){
                            ilConP.setError("Không để trống xác nhận mật khẩu");
                        }else {
                            ilConP.setError(null);
                        }
                    }else {
                        ilUsn.setError("Tên đăng nhập đã tồn tại");
                    }
                }


            }
        });
        return view;
    }
    public void onBackMenu(){
        edUsn.setText("");
        edFn.setText("");
        edCon.setText("");
        edPas.setText("");
        // mở navigation Menu
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }
}