package net.fpl.asm_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import net.fpl.asm_duanmau.DAO.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnCancel;
    CheckBox chk;
    EditText edUser, edPass;
    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login);
        btnCancel = findViewById(R.id.btn_cancel);
        chk = findViewById(R.id.chk_remember);
        edUser = findViewById(R.id.ed_username);
        edPass = findViewById(R.id.ed_password);

        thuThuDAO = new ThuThuDAO(getApplicationContext());

        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edUser.setText(sharedPreferences.getString("USERNAME", ""));
        edPass.setText(sharedPreferences.getString("PASS", ""));
        chk.setChecked(sharedPreferences.getBoolean("REMEMBER", false));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edUser.setText("");
                edPass.setText("");
            }
        });
    }

    private void login() {
        String strUser = edUser.getText().toString();
        String strPass = edPass.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
        } else {
            if (thuThuDAO.checkLogin(strUser, strPass) || strUser.equals("admin") && strPass.equals("admin")) {
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser, strPass, chk.isChecked());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user", strUser);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void rememberUser(String strUser, String strPass, boolean checked) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!checked) {
            editor.clear();
        } else {
            editor.putString("USERNAME", strUser);
            editor.putString("PASS", strPass);
            editor.putBoolean("REMEMBER", checked);
        }
        editor.commit();
    }
}