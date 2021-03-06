package com.triminh.comicpro.view.tentruyen;

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
import com.triminh.comicpro.api.ApiDonViTinh;
import com.triminh.comicpro.api.ApiLoaiBia;
import com.triminh.comicpro.api.ApiQuaTang;
import com.triminh.comicpro.api.ApiTenTruyen;
import com.triminh.comicpro.model.danhmuc.DonViTinh;
import com.triminh.comicpro.model.danhmuc.LoaiBia;
import com.triminh.comicpro.model.danhmuc.QuaTang;
import com.triminh.comicpro.model.tentruyen.TenTruyen;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.TM_Toast;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemTenTruyenActivity extends AppCompatActivity {
    ArrayList<LoaiBia> lstLoaiBia;
    ArrayList<QuaTang> lstQuaTang;
    ArrayList<DonViTinh> lstDonViTinh;

    Context mContext;
    TextInputEditText txtTenTruyen, txtNgayXuatBan, txtTap, txtGiaBia, txtSoTrang;
    AutoCompleteTextView txtLoaiBia, txtDonViTinh, txtQuaTang;
    Button btnLuu;

    Integer intMaLoaiBia, intDVT, tap, sotrang;
    double giabia;
    String strMaQuaTang, strNgayThang = "";
    static boolean load = false;
    static String strTenTruyen, strNgayXuatBan, strGiaBia;

    ArrayAdapter adapterLoaiBia;
    ArrayAdapter adapterDonViTinh;
    ArrayAdapter adapterQuaTang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ten_truyen);

        mContext = this;
        //Home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Th??m T??n Truy???n");

        txtTenTruyen = findViewById(R.id.txtTenTruyen);
        txtNgayXuatBan = findViewById(R.id.txtNgayXuatBan);
        txtTap = findViewById(R.id.txtTap);
        txtGiaBia = findViewById(R.id.txtGiaBia);
        txtSoTrang = findViewById(R.id.txtSoTrang);
        txtLoaiBia = findViewById(R.id.txtLoaiBia);
        txtDonViTinh = findViewById(R.id.txtDonViTinh);
        txtQuaTang = findViewById(R.id.txtQuaTang);
        btnLuu = findViewById(R.id.btnLuu);

        if (viewTenTruyenActivity.edit == false) {
            intMaLoaiBia = ComicPro.objTuaTruyen.getMaloaibia();
            txtLoaiBia.setText(ComicPro.objTuaTruyen.getLoaibia());
            giabia = ComicPro.objTuaTruyen.getGiabia();
            txtGiaBia.setText(ComicPro.objTuaTruyen.getGiabia().toString());
            intDVT = ComicPro.objTuaTruyen.getMadvt();
            txtDonViTinh.setText(ComicPro.objTuaTruyen.getDonvitinh());
            txtTap.setText(ComicPro.objTuaTruyen.getTap().toString());
        } else {
            txtTenTruyen.setText(ComicPro.objTenTruyen.getTentruyen());
            txtLoaiBia.setText(ComicPro.objTenTruyen.getLoaibia());
            intMaLoaiBia = ComicPro.objTenTruyen.getMaloaibia();
            txtDonViTinh.setText(ComicPro.objTenTruyen.getDonvitinh());
            intDVT = ComicPro.objTenTruyen.getMadvt();

            if (ComicPro.objTenTruyen.getNgayxuatban_text().equals("-")) {
                txtNgayXuatBan.setText("");
            } else {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
                strNgayThang = dateFormat2.format(ComicPro.objTenTruyen.getNgayxuatban());
                txtNgayXuatBan.setText(dateFormat.format(ComicPro.objTenTruyen.getNgayxuatban()));
            }
            txtTap.setText(ComicPro.objTenTruyen.getTap().toString());

            DecimalFormat format = new DecimalFormat("#,##0");
            double dblGiaBia = ComicPro.objTenTruyen.getGiabia();
//            holder.txtTongSoCuon.setText(format2.format(intSoCuon));

            txtGiaBia.setText(format.format(dblGiaBia));
            txtSoTrang.setText(ComicPro.objTenTruyen.getSotrang());
            txtQuaTang.setText(ComicPro.objTenTruyen.getQuatang());
            strMaQuaTang = ComicPro.objTenTruyen.getMaquatang();
        }

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
                        //L???y gi?? tr??? g???i l??n server
                        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                        strNgayThang = formatter2.format(calendar.getTime());

                    }, year, month, dayOfMonth);

            datePickerDialog.show();
        });

        GetLoaiBia();
        GetQuaTang();
        GetDonViTinh();

        btnLuu.setOnClickListener(v -> {
            if (viewTenTruyenActivity.edit == true) {
                Edit();
            } else {
                Update();
            }
        });
    }

    public void GetLoaiBia() {
        ApiLoaiBia.apiLoaiBia.GetLoaiBia().enqueue(new Callback<List<LoaiBia>>() {
            @Override
            public void onResponse(Call<List<LoaiBia>> call, Response<List<LoaiBia>> response) {
                List<LoaiBia> loaiBias = response.body();
                if (loaiBias != null) {
                    lstLoaiBia = new ArrayList<>();
                    lstLoaiBia.addAll(loaiBias);

                    String[] arrayLoaiBia = new String[lstLoaiBia.size()];
                    int i = 0;
                    for (LoaiBia loaiBia : lstLoaiBia) {
                        arrayLoaiBia[i] = loaiBia.getLoaibia();
                        i++;
                    }

                    adapterLoaiBia = new ArrayAdapter<>(mContext, R.layout.dropdown_item, arrayLoaiBia);
                    txtLoaiBia.setAdapter(adapterLoaiBia);

                    txtLoaiBia.setOnItemClickListener((parent, view, position, id) -> {
                        LoaiBia loaiBia = lstLoaiBia.get((int) id);
                        intMaLoaiBia = loaiBia.getId();
                    });
                }
            }

            @Override
            public void onFailure(Call<List<LoaiBia>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetQuaTang() {
        ApiQuaTang.apiQuaTang.GetQuaTang().enqueue(new Callback<List<QuaTang>>() {
            @Override
            public void onResponse(Call<List<QuaTang>> call, Response<List<QuaTang>> response) {
                List<QuaTang> quaTangs = response.body();
                if (quaTangs != null) {
                    lstQuaTang = new ArrayList<>();
                    lstQuaTang.addAll(quaTangs);
                    String[] arrayQuaTang = new String[lstQuaTang.size()];
                    int i = 0;
                    for (QuaTang quaTang : lstQuaTang) {
                        arrayQuaTang[i] = quaTang.getQuatang();
                        i++;
                    }
                    adapterQuaTang = new ArrayAdapter<>(mContext, R.layout.dropdown_item, arrayQuaTang);
                    txtQuaTang.setAdapter(adapterQuaTang);

                    txtQuaTang.setOnItemClickListener((parent, view, position, id) -> {
                        QuaTang quaTang = lstQuaTang.get((int) id);
                        strMaQuaTang = quaTang.getMaquatang();
                    });

                } else {
                    TM_Toast.makeText(mContext, "Kh??ng t???i ???????c d??? li???u.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false);
                }
            }

            @Override
            public void onFailure(Call<List<QuaTang>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetDonViTinh() {
        ApiDonViTinh.apiDonViTinh.Unit("GETDATA", 0, "", "", "").enqueue(new Callback<List<DonViTinh>>() {
            @Override
            public void onResponse(Call<List<DonViTinh>> call, Response<List<DonViTinh>> response) {
                List<DonViTinh> donViTinhs = response.body();
                if (donViTinhs != null) {
                    lstDonViTinh = new ArrayList<>();
                    lstDonViTinh.addAll(donViTinhs);

                    String[] arrayDonViTinh = new String[lstDonViTinh.size()];
                    int i = 0;
                    for (DonViTinh donViTinh : lstDonViTinh) {
                        arrayDonViTinh[i] = donViTinh.getDonvitinh();
                        i++;
                    }
                    adapterDonViTinh = new ArrayAdapter<>(mContext, R.layout.dropdown_item, arrayDonViTinh);
                    txtDonViTinh.setAdapter(adapterDonViTinh);

                    txtDonViTinh.setOnItemClickListener((parent, view, position, id) -> {
                        DonViTinh donViTinh = lstDonViTinh.get((int) id);
                        intDVT = donViTinh.getId();
                    });
                }
            }

            @Override
            public void onFailure(Call<List<DonViTinh>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void Update() {
        if (txtTenTruyen.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "B???n vui l??ng nh???p v??o t??n truy???n.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (intMaLoaiBia < 0) {
            TM_Toast.makeText(mContext, "B???n vui l??ng nh???p v??o lo???i b??a truy???n.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (txtTap.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "B???n vui l??ng nh???p v??o s??? t???p.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        } else {
            tap = Integer.parseInt(txtTap.getText().toString());
        }
        if (intDVT < 0) {
            TM_Toast.makeText(mContext, "B???n vui l??ng nh???p v??o ????n v??? t??nh.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (txtGiaBia.getText().toString().equals("")) {
            giabia = 0.0;
        } else {
            giabia = Double.parseDouble(txtGiaBia.getText().toString());
        }
        if (txtSoTrang.getText().toString().equals("")) {
            sotrang = 0;
        } else {
            sotrang = Integer.parseInt(txtSoTrang.getText().toString());
        }
        if (txtQuaTang.getText().length() == 0) {
            strMaQuaTang = "00";
        }
        ApiTenTruyen.apiTenTruyen.Insert(txtTenTruyen.getText().toString(), ComicPro.objTuaTruyen.getMatua(), Integer.parseInt(txtTap.getText().toString()), intDVT, intMaLoaiBia, "", ComicPro.tendangnhap, strMaQuaTang, sotrang, strNgayThang, giabia).enqueue(new Callback<List<TenTruyen>>() {
            @Override
            public void onResponse(Call<List<TenTruyen>> call, Response<List<TenTruyen>> response) {
                List<TenTruyen> tenTruyens = response.body();
                if (tenTruyens != null) {
                    load = true;
                    TM_Toast.makeText(mContext, "Th??m m???i t??n truy???n (" + txtTenTruyen.getText() + ") th??nh c??ng.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                } else {
                    TM_Toast.makeText(mContext, "C?? l???i trong qu?? tr??nh th???c hi???n", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<List<TenTruyen>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void Edit() {
        if (txtTenTruyen.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "B???n vui l??ng nh???p v??o t??n truy???n.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (intMaLoaiBia < 0) {
            TM_Toast.makeText(mContext, "B???n vui l??ng nh???p v??o lo???i b??a truy???n.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (txtTap.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "B???n vui l??ng nh???p v??o s??? t???p.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        } else {
            tap = Integer.parseInt(txtTap.getText().toString());
        }
        if (intDVT < 0) {
            TM_Toast.makeText(mContext, "B???n vui l??ng nh???p v??o ????n v??? t??nh.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (txtGiaBia.getText().toString().equals("")) {
            giabia = 0.0;
        } else {
            String a = String.valueOf(txtGiaBia.getText());
            giabia = Double.parseDouble(a.replace(",", ""));
            // giabia = Double.parseDouble(txtGiaBia.getText().toString());
        }
        if (txtSoTrang.getText().toString().equals("")) {
            sotrang = 0;
        } else {
            sotrang = Integer.parseInt(txtSoTrang.getText().toString());
        }
        if (strMaQuaTang.length() == 0) {
            strMaQuaTang = "00";
        }
        ApiTenTruyen.apiTenTruyen.Update(txtTenTruyen.getText().toString(), intMaLoaiBia, ComicPro.objTenTruyen.getMatruyen(), tap, intDVT, giabia, strMaQuaTang, strNgayThang, "", sotrang, ComicPro.tendangnhap).enqueue(new Callback<List<TenTruyen>>() {
            @Override
            public void onResponse(Call<List<TenTruyen>> call, Response<List<TenTruyen>> response) {
                List<TenTruyen> tenTruyens = response.body();
                if (tenTruyens.size() > 0) {
                    load = true;
                    TM_Toast.makeText(mContext, "C???p nh???t t??n truy???n (" + txtTenTruyen.getText() + ") th??nh c??ng.", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                    strTenTruyen = txtTenTruyen.getText().toString();
                    strNgayXuatBan = txtNgayXuatBan.getText().toString();
                    strGiaBia = txtGiaBia.getText().toString();
                    //????ng v?? quay tr??? l???i
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("name", "tentruyen");
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    TM_Toast.makeText(mContext, "C???p nh???t l???i.", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<List<TenTruyen>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //????ng v?? quay tr??? l???i
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", "tentruyen");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}