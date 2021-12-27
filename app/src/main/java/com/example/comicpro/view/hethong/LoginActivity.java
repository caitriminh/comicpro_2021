package com.example.comicpro.view.hethong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.comicpro.ComicProActivity;
import com.example.comicpro.R;
import com.example.comicpro.api.ApiUser;
import com.example.comicpro.model.nguoidung.User;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.TM_Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    TextView txtTenDangNhap, txtMatKhau, btnLogin;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences("SESSION", MODE_PRIVATE);
        boolean isLogin = pref.getBoolean("isLogin", false);
        if (isLogin) {
            ComicPro.tendangnhap = pref.getString("username", "").toUpperCase();
            //Mở chương trình
            Intent intent = new Intent(LoginActivity.this, ComicProActivity.class);
            startActivity(intent);
            finish();
        }
        txtTenDangNhap = findViewById(R.id.txtTenDangNhap);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTenDangNhap.getText().equals("") || txtMatKhau.getText().equals("")) {
                    TM_Toast.makeText(LoginActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                Login();
            }
        });
    }

    private void Login() {
        ApiUser.apiUser.GetUser("LOGIN", txtTenDangNhap.getText().toString(), "", txtMatKhau.getText().toString(), false, "").enqueue(new Callback < List < User > >() {
            @Override
            public void onResponse(Call < List < User > > call, Response < List < User > > response) {
                List < User > lstUser = response.body();
                if (lstUser.size() > 0) {
                    ComicPro.tendangnhap = lstUser.get(0).getTendangnhap().toUpperCase();
                    TM_Toast.makeText(LoginActivity.this, "User (" + lstUser.get(0).getTendangnhap() + ") đã đăng nhập thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();

                    pref.edit().putBoolean("isLogin", true).commit();
                    pref.edit().putString("username", lstUser.get(0).getTendangnhap()).commit();

                    //Mở chương trình
                    Intent intent = new Intent(LoginActivity.this, ComicProActivity.class);
                    startActivity(intent);
                    finish();
                } else
                    TM_Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            }

            @Override
            public void onFailure(Call < List < User > > call, Throwable t) {
                TM_Toast.makeText(LoginActivity.this, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }
}