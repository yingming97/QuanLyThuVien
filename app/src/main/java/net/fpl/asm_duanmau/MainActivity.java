package net.fpl.asm_duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import net.fpl.asm_duanmau.Fragment.AddUserFragment;
import net.fpl.asm_duanmau.Fragment.ChangePassFragment;
import net.fpl.asm_duanmau.Fragment.DoanhThuFragment;
import net.fpl.asm_duanmau.Fragment.LoaiSachFragment;
import net.fpl.asm_duanmau.Fragment.PhieuMuonFragment;
import net.fpl.asm_duanmau.Fragment.SachFragment;
import net.fpl.asm_duanmau.Fragment.ThanhVienFragment;
import net.fpl.asm_duanmau.Fragment.TopFragment;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    View view;
    TextView textView;
    ActionBarDrawerToggle toggle;
    private static final int FRAGMENT_PHIEUMUON = 0;
    private static final int FRAGMENT_LOAISACH = 1;
    private static final int FRAGMENT_SACH = 2;
    private static final int FRAGMENT_THANHVIEN = 3;
    private static final int FRAGMENT_TOP = 4;
    private static final int FRAGMENT_DOANHTHU = 5;
    private static final int FRAGMENT_ADDUSER = 6;
    private static final int FRAGMENT_CHANGEPASS = 7;
    int current = FRAGMENT_PHIEUMUON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.draw_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nvView);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setTitle("PHIẾU MƯỢN");
        replaceFragment(new PhieuMuonFragment());

        view = navigationView.getHeaderView(0);
        textView = view.findViewById(R.id.tv_wellcome);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        textView.setText(user);
        if (user.equals("admin")) {
            navigationView.getMenu().findItem(R.id.sub_AddUser).setVisible(true);
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_PhieuMuon:
                setTitle("QUẢN LÝ PHIẾU MƯỢN");
                if (current != FRAGMENT_PHIEUMUON) {
                    replaceFragment(new PhieuMuonFragment());
                    current = FRAGMENT_PHIEUMUON;
                }
                break;
            case R.id.nav_LoaiSach:
                setTitle("QUẢN LÝ LOẠI SÁCH");
                if (current != FRAGMENT_LOAISACH) {
                    replaceFragment(new LoaiSachFragment());
                    current = FRAGMENT_LOAISACH;
                }
                break;
            case R.id.nav_Sach:
                setTitle("QUẢN LÝ SÁCH");
                if (current != FRAGMENT_SACH) {
                    replaceFragment(new SachFragment());
                    current = FRAGMENT_SACH;
                }
                break;
            case R.id.nav_ThanhVien:
                setTitle("QUẢN LÝ THÀNH VIÊN");
                if (current != FRAGMENT_THANHVIEN) {
                    replaceFragment(new ThanhVienFragment());
                    current = FRAGMENT_THANHVIEN;
                }
                break;
            case R.id.sub_Top:
                setTitle("Top 10 SÁCH");
                if (current != FRAGMENT_TOP) {
                    replaceFragment(new TopFragment());
                    current = FRAGMENT_TOP;
                }
                break;
            case R.id.sub_DoanhThu:
                setTitle("DOANH THU");
                if (current != FRAGMENT_DOANHTHU) {
                    replaceFragment(new DoanhThuFragment());
                    current = FRAGMENT_DOANHTHU;
                }
                break;
            case R.id.sub_AddUser:
                setTitle("THÊM TÀI KHOẢN THỦ THƯ");
                if (current != FRAGMENT_ADDUSER) {
                    replaceFragment(new AddUserFragment());
                    current = FRAGMENT_ADDUSER;
                }
                break;
            case R.id.sub_Pass:
                setTitle("ĐỔI MẬT KHẨU");
                if (current != FRAGMENT_CHANGEPASS) {
                    replaceFragment(new ChangePassFragment());
                    current = FRAGMENT_CHANGEPASS;
                }
                break;
            case R.id.sub_Logout:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flContent, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}