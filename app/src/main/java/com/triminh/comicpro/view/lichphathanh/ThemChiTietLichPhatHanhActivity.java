package com.triminh.comicpro.view.lichphathanh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.comicpro.R;
import com.triminh.comicpro.api.ApiCTLichPhatHanh;
import com.triminh.comicpro.model.lichphathanh.CTLichPhatHanh;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.TM_Toast;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemChiTietLichPhatHanhActivity extends AppCompatActivity {

    Context mContext;
    TextInputEditText txtTuaTruyen, txtNgayXuatBan, txtGiaBia;
    Button btnXacNhan;

    String strNgayThang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_chi_tiet_lich_phat_hanh);
        mContext = this;
        //Home
        this.setTitle("Thêm Chi Tiết");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTuaTruyen = findViewById(R.id.txtTuaTruyen);
        txtNgayXuatBan = findViewById(R.id.txtNgayXuatBan);
        txtGiaBia = findViewById(R.id.txtGiaBia);
        btnXacNhan = findViewById(R.id.btnXacNhan);

        txtNgayXuatBan.setOnClickListener(view -> {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                    (datePicker, year1, month1, day) -> {

                        calendar.set(year1, month1, day);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = formatter.format(calendar.getTime());

                        txtNgayXuatBan.setText(strDate);
                        //Lấy giá trị gửi lên server
                        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                        strNgayThang = formatter2.format(calendar.getTime());

                    }, year, month, dayOfMonth);

            datePickerDialog.show();
        });

        btnXacNhan.setOnClickListener(v -> Update());
    }

    public void Update() {
        if (txtTuaTruyen.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (strNgayThang.equals("")) {
            TM_Toast.makeText(mContext, "Bạn vui lòng nhập ngày phát hành.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (txtGiaBia.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "Bạn vui lòng nhập giá bìa.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }

        ApiCTLichPhatHanh.apiCtLichPhatHanh.Insert("SAVE", 0, ComicPro.objLichPhatHanh.getMalich(), strNgayThang, txtTuaTruyen.getText().toString(), Integer.parseInt(txtGiaBia.getText().toString()), "", ComicPro.tendangnhap, "").enqueue(new Callback<List<CTLichPhatHanh>>() {
            @Override
            public void onResponse(Call<List<CTLichPhatHanh>> call, Response<List<CTLichPhatHanh>> response) {
                List<CTLichPhatHanh> ctLichPhatHanhs = response.body();
                if (ctLichPhatHanhs != null) {
                    TM_Toast.makeText(mContext, "Đã thêm lịch phát hành tựa truyện (" + txtTuaTruyen.getText() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                    txtTuaTruyen.setText("");
                    txtGiaBia.setText("0");
                    txtTuaTruyen.setFocusable(true);
                }
            }

            @Override
            public void onFailure(Call<List<CTLichPhatHanh>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Đòng và quay trở lại
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", "ct_lichphathanh");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}