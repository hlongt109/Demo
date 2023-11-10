package com.longthph30891.ph30891_mob2041_asm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.longthph30891.ph30891_mob2041_asm.DAO.AdminDAO;
import com.longthph30891.ph30891_mob2041_asm.DAO.thuThuDAO;

public class Login extends AppCompatActivity {
    TextInputLayout ilUs, ilPs;
    TextInputEditText edUs, edPs;
    CheckBox chkRemember;
    Button btnLogin, btnCancel;
    Context context = this;
    AdminDAO adminDAO;
    thuThuDAO thuThuDAO;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ilUs = findViewById(R.id.il_Username_login);
        ilPs = findViewById(R.id.il_Password_login);
        edUs = findViewById(R.id.edUsername_login);
        edPs = findViewById(R.id.edPassword_login);
        chkRemember = findViewById(R.id.chkRememberPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel_lg);
        checkRemember();
        // btnCancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Thoát ứng dụng", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        //
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminDAO = new AdminDAO(context);
                thuThuDAO = new thuThuDAO(context);
                String username = edUs.getText().toString();
                String password = edPs.getText().toString();
                if (adminDAO.checkTKAdmin(username, password)) {
                    Toast.makeText(context, "Welcome "+username, Toast.LENGTH_SHORT).show();
                    rememberAcc(username, password, true);
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    finish();
                } else if (thuThuDAO.checkThuThuAc(username, password)) {
                    Toast.makeText(context, "Welcome "+username, Toast.LENGTH_SHORT).show();
                    rememberAcc(username, password, true);
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.putExtra("USERNAME", username);
                    startActivity(intent);
                    finish();
                }else {
                    if(username.isEmpty() || password.isEmpty()){
                        if (username.equals("")){
                            ilUs.setError("Không để trống Username");
                        }else{
                            ilUs.setError(null);
                        }
                        if(password.equals("")){
                            ilPs.setError("Không để trống Password");
                        }else{
                            ilPs.setError(null);
                        }
                    }else {
                        Toast.makeText(context, "Tên đăng nhập hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void rememberAcc(String usn, String pas, boolean chk) {
        SharedPreferences s = getSharedPreferences("RememberAcc.txt", MODE_PRIVATE);// tạo đối tượng SharedPreferences
        SharedPreferences.Editor e = s.edit(); // tạo một đoi tượng Editor để chỉnh sửa SharedPreferences
        e.putString("USN", usn); // Đặt username vào SharedPreferences với khóa "USN"
        e.putString("PAS", pas); // Đặt password vào SharedPreferences với khóa "PAS"
        e.putBoolean("CHK", chk); // Đặt checkBox vào SharedPreferences với khóa "CHK"
        e.apply(); // áp dụng các thay đổi vào SharesPreferences
    }

    public void checkRemember() {
        SharedPreferences s = getSharedPreferences("RememberAcc.txt", MODE_PRIVATE);// tạo đối tượng SharedPreferences
        String usn = s.getString("USN", ""); // Lấy giá trị tên đăng nhập từ SharedPreferences với khóa "USN", mặc định là chuỗi rỗng
        String pas = s.getString("PAS", ""); // Lấy giá trị mật khẩu từ SharedPreferences với khóa "PAS"
        boolean isChecked = s.getBoolean("CHK", false); // Lấy giá trị trạng thái của checkbox "Remember Me" từ SharedPreferences với khóa "CHK"
        chkRemember.setChecked(isChecked);  // Đặt trạng thái của checkbox theo giá trị đã lưu trong SharedPreferences
        if (chkRemember.isChecked()) { // Nếu checkbox "Remember Me" đã được chọn, đặt tên đăng nhập và mật khẩu vào trường EditText
            edUs.setText(usn);
            edPs.setText(pas);
        }
    }
}