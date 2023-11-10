package com.longthph30891.ph30891_mob2041_asm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.longthph30891.ph30891_mob2041_asm.Fragment.frgQLyPhieuMuon;
import com.longthph30891.ph30891_mob2041_asm.Fragment.frgQLyLoaiSach;
import com.longthph30891.ph30891_mob2041_asm.Fragment.frgQLySach;
import com.longthph30891.ph30891_mob2041_asm.Fragment.frgQLyThanhVien;
import com.longthph30891.ph30891_mob2041_asm.Fragment.frgDoanhThu;
import com.longthph30891.ph30891_mob2041_asm.Fragment.frgDoiMk;
import com.longthph30891.ph30891_mob2041_asm.Fragment.frgThemUser;
import com.longthph30891.ph30891_mob2041_asm.Fragment.frgTop;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView nv;
    Toolbar toolbar;
    Context context = this;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nvView);
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // điều hướng
        NavigationUser();
        // mặc định hiển thị frameLayout Phieu mươn
        if (savedInstanceState == null) {
            frgQLyPhieuMuon frgQLyPhieuMuon = new frgQLyPhieuMuon();
            replaceFrg(frgQLyPhieuMuon);
            toolbar.setTitle("QUẢN LÝ PHIẾU MƯỢN");
        }
        // click menu
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_PhieuMuon) {
                    frgQLyPhieuMuon frgQLyPhieuMuon = new frgQLyPhieuMuon();
                    replaceFrg(frgQLyPhieuMuon);
                    toolbar.setTitle("QUẢN LÝ PHIẾU MƯỢN");
                } else if (item.getItemId() == R.id.nav_LoaiSach) {
                    frgQLyLoaiSach frgQLyLoaiSach = new frgQLyLoaiSach();
                    replaceFrg(frgQLyLoaiSach);
                    toolbar.setTitle("QUẢN LÝ LOẠI SÁCH");
                } else if (item.getItemId() == R.id.nav_Sach) {
                    frgQLySach frgQLySach = new frgQLySach();
                    replaceFrg(frgQLySach);
                    toolbar.setTitle("QUẢN LÝ SÁCH");
                } else if (item.getItemId() == R.id.nav_ThanhVien) {
                    frgQLyThanhVien frgQLyThanhVien = new frgQLyThanhVien();
                    replaceFrg(frgQLyThanhVien);
                    toolbar.setTitle("QUẢN LÝ THÀNH VIÊN");
                } else if (item.getItemId() == R.id.nav_Top10) {
                    frgTop frgTop = new frgTop();
                    replaceFrg(frgTop);
                    toolbar.setTitle("TOP 10 SÁCH MƯỢN NHIỀU");
                } else if (item.getItemId() == R.id.nav_DoanhThu) {
                    frgDoanhThu frgDoanhThu = new frgDoanhThu();
                    replaceFrg(frgDoanhThu);
                    toolbar.setTitle("DOANH THU");
                } else if (item.getItemId() == R.id.nav_ThemNgDung) {
                    frgThemUser themUser = new frgThemUser();
                    replaceFrg(themUser);
                    toolbar.setTitle("THÊM NGƯỜI DÙNG");
                } else if (item.getItemId() == R.id.nav_DoiMK) {
                    frgDoiMk frgDoiMk = new frgDoiMk();
                    replaceFrg(frgDoiMk);
                    toolbar.setTitle("ĐỔI MẬT KHẨU");
                } else if (item.getItemId() == R.id.nav_Logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Log Out");
                    builder.setMessage("Do you want to log out ?");
                    builder.setNegativeButton("No", null);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.create().show();
                }
                drawerLayout.closeDrawer(nv); // đóng menu sau khi chọn một item
                return true;
            }
        });
    }

    public void replaceFrg(Fragment frg) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.flContent, frg).commit();
    }
    public void NavigationUser(){
        // Tìm TextView trong nav_header.xml
        NavigationView nv = findViewById(R.id.nvView);
        View headerView = nv.getHeaderView(0);
        TextView tvUser = headerView.findViewById(R.id.tvUser);
        // Lấy tên người dùng từ Intent
        Intent i = getIntent();
        String username = i.getStringExtra("USERNAME");
        // Cập nhật TextView với tên người dùng
        tvUser.setText("Welcome " + username);
        // điều hướng
        if (username.equals("admin")){
            nv.getMenu().findItem(R.id.nav_ThemNgDung).setVisible(true);
        }
    }
}