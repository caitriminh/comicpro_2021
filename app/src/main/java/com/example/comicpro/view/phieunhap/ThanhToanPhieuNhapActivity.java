package com.example.comicpro.view.phieunhap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comicpro.R;
import com.example.comicpro.api.ApiDonVi;
import com.example.comicpro.api.ApiNhapXuatTemp;
import com.example.comicpro.model.danhmuc.DonVi;
import com.example.comicpro.model.danhmuc.TacGia;
import com.example.comicpro.model.phieuxuat.NhapXuatTemp;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.TM_Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToanPhieuNhapActivity extends AppCompatActivity {
    List<NhapXuatTemp> lstNhapHangTemps;
    List<DonVi> lstDonVi;
    Context mConText;
    double dblTongTien = 0;
    String strMaKH;

    Button btnXacNhan;

    AutoCompleteTextView txtDonVi;
    TextInputEditText txtDienGiai, txtDiaChi, txtSoDT, txtTongTien;

    ArrayAdapter adapterNCC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_phieunhap2);
        mConText = this;
        setTitle("Thanh Toán");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btnXacNhan = findViewById(R.id.btnXacNhan);
        txtDonVi = findViewById(R.id.txtDonVi);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtSoDT = findViewById(R.id.txtSoDT);
        txtDienGiai = findViewById(R.id.txtDienGiai);
        txtTongTien = findViewById(R.id.txtTongTien);
        GetNhaCungCap();
        GetThongTinThanhToan();
        btnXacNhan.setOnClickListener(v -> GetThanhToan());
    }

    private void GetThanhToan() {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mConText)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn thanh toán đơn hàng này không?")
                .setCancelable(false)
                .setPositiveButton("Xác Nhận", R.drawable.ic_xacnhan_white, (dialogInterface, which) -> {
                    ApiNhapXuatTemp.apiNhapXuatTemp.NhapXuatTemp("THANHTOAN", 0, "", 0, 0.0, ComicPro.tendangnhap, 0, strMaKH, txtDienGiai.getText().toString()).enqueue(new Callback<List<NhapXuatTemp>>() {
                        @Override
                        public void onResponse(Call<List<NhapXuatTemp>> call, Response<List<NhapXuatTemp>> response) {
                            List<NhapXuatTemp> nhapXuatTemps = response.body();
                            if (nhapXuatTemps != null) {
                                TM_Toast.makeText(mConText, "Thanh toán thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();

                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("name", "your_editext_value");
                                setResult(Activity.RESULT_OK, resultIntent);

                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<NhapXuatTemp>> call, Throwable t) {
                            TM_Toast.makeText(mConText, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                        }
                    });


                    dialogInterface.dismiss();

                })
                .setNegativeButton("Đóng", R.drawable.ic_close_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        mBottomSheetDialog.show();


    }

    public void GetNhaCungCap() {
        ApiDonVi.apiDonVi.GetDonVi(1, "", 1).enqueue(new Callback<List<DonVi>>() {
            @Override
            public void onResponse(Call<List<DonVi>> call, Response<List<DonVi>> response) {
                List<DonVi> donVis = response.body();
                if (donVis.size() > 0) {
                    lstDonVi = new ArrayList<>();
                    lstDonVi.addAll(donVis);

                    String[] arrayKhachHang = new String[lstDonVi.size()];
                    int i = 0;

                    for (DonVi donVi : lstDonVi) {
                        arrayKhachHang[i] = donVi.getDonvi();
                        i++;
                    }
                    adapterNCC = new ArrayAdapter<>(mConText, R.layout.dropdown_item, arrayKhachHang);
                    txtDonVi.setAdapter(adapterNCC);

                    txtDonVi.setOnItemClickListener((parent, view, position, id) -> {
                        DonVi donVi = lstDonVi.get((int) id);
                        strMaKH = donVi.getMadonvi();
                        txtDiaChi.setText(donVi.getDiachi());
                        txtSoDT.setText(donVi.getSodt());
                    });

                } else {
                    TM_Toast.makeText(mConText, "Không tải được danh sách người bán.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                }
            }

            @Override
            public void onFailure(Call<List<DonVi>> call, Throwable t) {
                TM_Toast.makeText(mConText, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });

    }

    private void GetThongTinThanhToan() {
        ApiNhapXuatTemp.apiNhapXuatTemp.NhapXuatTemp("GET_DATA", 0, "", 0, 0.0, ComicPro.tendangnhap, 0, "", "").enqueue(new Callback<List<NhapXuatTemp>>() {
            @Override
            public void onResponse(Call<List<NhapXuatTemp>> call, Response<List<NhapXuatTemp>> response) {
                List<NhapXuatTemp> nhapXuatTemps = response.body();
                if (nhapXuatTemps.size() > 0) {
                    lstNhapHangTemps = new ArrayList<>();
                    lstNhapHangTemps.addAll(nhapXuatTemps);
                    if (lstNhapHangTemps.size() > 0) {
                        int i = 0;
                        dblTongTien = 0;
                        for (NhapXuatTemp ignored : lstNhapHangTemps) {
                            dblTongTien += lstNhapHangTemps.get(i).getDongia() * lstNhapHangTemps.get(i).getSoluong();
                            i++;
                        }
                        DecimalFormat formatTongTien = new DecimalFormat("#,##0");
                        txtTongTien.setText(formatTongTien.format(dblTongTien));
                    }
                } else {
                    TM_Toast.makeText(mConText, "Không tải được thông tin thanh toán.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                }
            }

            @Override
            public void onFailure(Call<List<NhapXuatTemp>> call, Throwable t) {
                TM_Toast.makeText(mConText, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
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