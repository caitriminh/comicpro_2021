package com.triminh.comicpro.view.lichphathanh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.comicpro.R;
import com.triminh.comicpro.api.ApiLichPhatHanh;
import com.triminh.comicpro.api.ApiNhaXuatBan;
import com.triminh.comicpro.model.danhmuc.NhaXuatBan;
import com.triminh.comicpro.model.lichphathanh.LichPhatHanh;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.TM_Toast;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemLichPhatHanhActivity extends AppCompatActivity {
    ArrayList<NhaXuatBan> lstNhaXuatBan;

    AutoCompleteTextView txtNhaXuatBan;
    TextInputEditText txtNgayXuatBan;
    Button btnXacNhan;

    Context mContext;

    String strMaNXB, strNgayThang;

    ArrayAdapter adapterNhaXuatBan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lich_phat_hanh);
        //Home
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Thêm Lịch Phát Hành");

        txtNhaXuatBan = findViewById(R.id.txtNhaXuatBan);
        txtNgayXuatBan = findViewById(R.id.txtNgayXuatBan);
        btnXacNhan = findViewById(R.id.btnXacNhan);

        GetNhaXuatBan();

        txtNgayXuatBan.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, AlertDialog.THEME_DEVICE_DEFAULT_DARK,
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

    private void GetNhaXuatBan() {
        ApiNhaXuatBan.apiNhaXuatBan.GetNhaXuatBan(1, "").enqueue(new Callback<List<NhaXuatBan>>() {
            @Override
            public void onResponse(Call<List<NhaXuatBan>> call, Response<List<NhaXuatBan>> response) {
                List<NhaXuatBan> nhaXuatBans = response.body();
                if (nhaXuatBans.size() > 0) {
                    lstNhaXuatBan = new ArrayList<>();
                    lstNhaXuatBan.addAll(nhaXuatBans);

                    String[] arrayNhaXuatBan = new String[lstNhaXuatBan.size()];
                    int i = 0;
                    for (NhaXuatBan nhaXuatBan : lstNhaXuatBan) {
                        arrayNhaXuatBan[i] = nhaXuatBan.getNhaxuatban();
                        i++;
                    }

                    adapterNhaXuatBan = new ArrayAdapter<>(mContext, R.layout.dropdown_item, arrayNhaXuatBan);
                    txtNhaXuatBan.setAdapter(adapterNhaXuatBan);

                    txtNhaXuatBan.setOnItemClickListener((parent, view, position, id) -> {
                        NhaXuatBan nhaXuatBan = lstNhaXuatBan.get((int) id);
                        strMaNXB = nhaXuatBan.getManxb();
                    });
                }
            }

            @Override
            public void onFailure(Call<List<NhaXuatBan>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });

    }

    public void Update() {
        if (strNgayThang.equals("")) {
            TM_Toast.makeText(mContext, "Vui lòng nhập vào lịch phát hành.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }

        if (strMaNXB.equals("")) {
            TM_Toast.makeText(mContext, "Vui lòng nhập vào nhà xuất bản.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        ApiLichPhatHanh.apiLichPhatHanh.Insert(strNgayThang, strMaNXB, "", ComicPro.tendangnhap).enqueue(new Callback<List<LichPhatHanh>>() {
            @Override
            public void onResponse(Call<List<LichPhatHanh>> call, Response<List<LichPhatHanh>> response) {
                List<LichPhatHanh> lichPhatHanhs = response.body();
                if (lichPhatHanhs != null) {
                    TM_Toast.makeText(mContext, "Đã thêm mới lịch phát hành nhà xuất (" + txtNhaXuatBan.getText() + ") thành công", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                    //Đòng và quay trở lại
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("name", "lichphathanh");
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<LichPhatHanh>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}