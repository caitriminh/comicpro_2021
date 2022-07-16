package com.triminh.comicpro.view.tuatruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.comicpro.R;
import com.triminh.comicpro.api.ApiLoaiTruyen;
import com.triminh.comicpro.api.ApiNhaXuatBan;
import com.triminh.comicpro.api.ApiQuocGia;
import com.triminh.comicpro.api.ApiTacGia;
import com.triminh.comicpro.api.ApiTuaTruyen;
import com.triminh.comicpro.model.danhmuc.LoaiTruyen;
import com.triminh.comicpro.model.danhmuc.NhaXuatBan;
import com.triminh.comicpro.model.danhmuc.QuocGia;
import com.triminh.comicpro.model.danhmuc.TacGia;
import com.triminh.comicpro.model.tuatruyen.TuaTruyen;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.TM_Toast;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemTuaTruyenActivity extends AppCompatActivity {

    ArrayList<TacGia> lstTacGia;
    ArrayList<LoaiTruyen> lstLoaiTruyen;
    ArrayList<NhaXuatBan> lstNhaXuatBan;
    ArrayList<QuocGia> lstQuocGia;


    Context mContext;

    TextInputEditText txtTuaTruyen, txtSoTap, txtNamXuatBan, txtTaiBan, txtTheoDoi;
    AutoCompleteTextView txtTacGia, txtLoaiTruyen, txtNhaXuatBan, txtMaNuoc;
    Button btnXacNhan;

    ArrayAdapter adapterTacgia;
    ArrayAdapter adapterLoaiTruyen;
    ArrayAdapter adapterNhaXuatBan;
    ArrayAdapter adapterXuatXu;

    static String strMaNXB, strMatacgia, strMaloaitruyen, strSoTap, strTuaTruyen;
    static String strTenTG;
    Integer manuoc, namxuatban, taiban, sotap;
    boolean theodoi = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tua_truyen);
        mContext = this;
        //Home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Thêm Tựa Truyện");

        txtTuaTruyen = findViewById(R.id.txtTuaTruyen);
        txtSoTap = findViewById(R.id.txtSoTap);
        txtNamXuatBan = findViewById(R.id.txtNamXuatBan);
        txtTaiBan = findViewById(R.id.txtTaiBan);

        txtTacGia = findViewById(R.id.txtTacGia);
        txtLoaiTruyen = findViewById(R.id.txtLoaiTruyen);
        txtNhaXuatBan = findViewById(R.id.txtNhaXuatBan);
        txtMaNuoc = findViewById(R.id.txtMaNuoc);
        txtTheoDoi = findViewById(R.id.txtTheoDoi);
        btnXacNhan = findViewById(R.id.btnXacNhan);


        if (TuaTruyenFragment.edit == true) {
            GetTuaTruyen();
        } else {
            txtTheoDoi.setText("Đang theo dõi");
            theodoi = true;
        }

        GetTacgia();
        GetLoaiTruyen();
        GetNhaXuatBan();
        GetXuatXu();

        btnXacNhan.setOnClickListener(v -> {
            if (TuaTruyenFragment.edit == true) {
                Edit();
            } else {
                Update();
            }
        });

        txtTheoDoi.setOnClickListener(v -> {
            if (theodoi == true) {
                txtTheoDoi.setText("Không theo dõi");
                theodoi = false;
            } else {
                txtTheoDoi.setText("Đang theo dõi");
                theodoi = true;
            }
        });
    }

    public void GetTacgia() {
        ApiTacGia.apiTacGia.GetTacGia().enqueue(new Callback<List<TacGia>>() {
            @Override
            public void onResponse(Call<List<TacGia>> call, Response<List<TacGia>> response) {
                List<TacGia> tacGias = response.body();
                if (tacGias.size() > 0) {
                    lstTacGia = new ArrayList<>();
                    lstTacGia.addAll(tacGias);

                    String[] arrayTacgia = new String[lstTacGia.size()];
                    int i = 0;
                    for (TacGia tacGia : lstTacGia) {
                        arrayTacgia[i] = tacGia.getTacgia();
                        i++;
                    }

                    adapterTacgia = new ArrayAdapter<String>(mContext, R.layout.dropdown_item, arrayTacgia);
                    txtTacGia.setAdapter(adapterTacgia);

                    txtTacGia.setOnItemClickListener((parent, view, position, id) -> {
                        TacGia tacGia = lstTacGia.get((int) id);
                        strMatacgia = tacGia.getMatacgia();
                    });
                }
            }

            @Override
            public void onFailure(Call<List<TacGia>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });

    }

    private void GetLoaiTruyen() {
        ApiLoaiTruyen.apiLoaiTruyen.GetLoaiTruyen().enqueue(new Callback<List<LoaiTruyen>>() {
            @Override
            public void onResponse(Call<List<LoaiTruyen>> call, Response<List<LoaiTruyen>> response) {
                List<LoaiTruyen> loaiTruyens = response.body();
                if (loaiTruyens.size() > 0) {
                    lstLoaiTruyen = new ArrayList<>();
                    lstLoaiTruyen.addAll(loaiTruyens);

                    String[] arrayLoaitruyen = new String[lstLoaiTruyen.size()];
                    int i = 0;
                    for (LoaiTruyen loaiTruyen : lstLoaiTruyen) {
                        arrayLoaitruyen[i] = loaiTruyen.getLoaitruyen();
                        i++;
                    }

                    adapterLoaiTruyen = new ArrayAdapter<String>(mContext, R.layout.dropdown_item, arrayLoaitruyen);
                    txtLoaiTruyen.setAdapter(adapterLoaiTruyen);

                    txtLoaiTruyen.setOnItemClickListener((parent, view, position, id) -> {
                        LoaiTruyen loaiTruyen = lstLoaiTruyen.get((int) id);
                        strMaloaitruyen = loaiTruyen.getMaloai();
                    });
                }
            }

            @Override
            public void onFailure(Call<List<LoaiTruyen>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });

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

    private void GetXuatXu() {
        ApiQuocGia.apiQuocGia.QuocGia("GET_DATA", 0, "", "", "").enqueue(new Callback<List<QuocGia>>() {
            @Override
            public void onResponse(Call<List<QuocGia>> call, Response<List<QuocGia>> response) {
                List<QuocGia> quocGias = response.body();
                if (quocGias.size() > 0) {
                    lstQuocGia = new ArrayList<>();
                    lstQuocGia.addAll(quocGias);

                    String[] arrayQuocGia = new String[lstQuocGia.size()];
                    int i = 0;
                    for (QuocGia quocGia : lstQuocGia) {
                        arrayQuocGia[i] = quocGia.getQuocgia();
                        i++;
                    }

                    adapterXuatXu = new ArrayAdapter<>(mContext, R.layout.dropdown_item, arrayQuocGia);
                    txtMaNuoc.setAdapter(adapterXuatXu);

                    txtMaNuoc.setOnItemClickListener((parent, view, position, id) -> {
                        QuocGia quocGia = lstQuocGia.get((int) id);
                        manuoc = quocGia.getId();
                    });
                }
            }

            @Override
            public void onFailure(Call<List<QuocGia>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });

    }

    public void Update() {

        if (txtTuaTruyen.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "Vui lòng nhập vào tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (strMaloaitruyen.equals("")) {
            TM_Toast.makeText(mContext, "Vui lòng nhập vào mã loại tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (strMatacgia.equals("")) {
            TM_Toast.makeText(mContext, "Vui lòng nhập vào tác giả tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (strMaNXB.equals("")) {
            TM_Toast.makeText(mContext, "Vui lòng nhập vào nhà xuất bản.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (manuoc < 0) {
            TM_Toast.makeText(mContext, "Vui lòng nhập vào xuất xứ.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (txtSoTap.getText().toString().equals("")) {
            sotap = 0;
        } else {
            sotap = Integer.parseInt(txtSoTap.getText().toString());
        }
        if (txtNamXuatBan.getText().toString().equals("")) {
            namxuatban = 0;
        } else {
            namxuatban = Integer.parseInt(txtNamXuatBan.getText().toString());
        }
        if (txtTaiBan.getText().toString().equals("")) {
            taiban = 0;
        } else {
            taiban = Integer.parseInt(txtTaiBan.getText().toString());
        }

        ApiTuaTruyen.apiTuaTruyen.Insert(txtTuaTruyen.getText().toString(), strMaloaitruyen, strMatacgia, strMaNXB, manuoc, sotap, namxuatban, taiban, ComicPro.tendangnhap).enqueue(new Callback<List<TuaTruyen>>() {
            @Override
            public void onResponse(Call<List<TuaTruyen>> call, Response<List<TuaTruyen>> response) {
                List<TuaTruyen> status = response.body();
                if (status.size() > 0) {

                }
            }

            @Override
            public void onFailure(Call<List<TuaTruyen>> call, Throwable t) {

            }
        });

        TM_Toast.makeText(mContext, "Thêm mới tựa truyện (" + txtTuaTruyen.getText() + ") thành công", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
        //Đòng và quay trở lại
        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", "tuatruyen");
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }

    public void Edit() {
        if (txtTuaTruyen.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "Vui lòng nhập vào tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }

        if (strMatacgia.equals("")) {
            TM_Toast.makeText(mContext, "Vui lòng nhập vào tác giả tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (strMaNXB.equals("")) {
            TM_Toast.makeText(mContext, "Vui lòng nhập vào nhà xuất bản.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (manuoc < 0) {
            TM_Toast.makeText(mContext, "Vui lòng nhập vào xuất xứ.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (txtSoTap.getText().toString().equals("")) {
            sotap = 0;
        } else {
            sotap = Integer.parseInt(txtSoTap.getText().toString());
        }
        if (txtNamXuatBan.getText().toString().equals("")) {
            namxuatban = 0;
        } else {
            namxuatban = Integer.parseInt(txtNamXuatBan.getText().toString());
        }
        if (txtTaiBan.getText().toString().equals("")) {
            taiban = 0;
        } else {
            taiban = Integer.parseInt(txtTaiBan.getText().toString());
        }

        ApiTuaTruyen.apiTuaTruyen.Update(ComicPro.strMaTua, txtTuaTruyen.getText().toString(), strMatacgia, strMaNXB, manuoc, Integer.parseInt(txtSoTap.getText().toString()), Integer.parseInt(txtNamXuatBan.getText().toString()), Integer.parseInt(txtTaiBan.getText().toString()), "", ComicPro.tendangnhap, theodoi).enqueue(new Callback<List<TuaTruyen>>() {
            @Override
            public void onResponse(Call<List<TuaTruyen>> call, Response<List<TuaTruyen>> response) {
                List<TuaTruyen> tuaTruyens = response.body();
                if (tuaTruyens.size() > 0) {
                    TM_Toast.makeText(mContext, "Cập nhật thành công", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                    //Đòng và quay trở lại
                    strSoTap = txtSoTap.getText().toString();
                    strTuaTruyen = txtTuaTruyen.getText().toString();
                    strTenTG=txtTacGia.getText().toString();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("name", "tuatruyen");
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<TuaTruyen>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetTuaTruyen() {
        txtLoaiTruyen.setEnabled(false);
        ApiTuaTruyen.apiTuaTruyen.GetTuaTruyenByMaTua(ComicPro.strMaTua).enqueue(new Callback<List<TuaTruyen>>() {
            @Override
            public void onResponse(Call<List<TuaTruyen>> call, Response<List<TuaTruyen>> response) {
                List<TuaTruyen> tuaTruyens = response.body();
                if (tuaTruyens != null) {
                    txtTuaTruyen.setText(tuaTruyens.get(0).getTuatruyen());
                    txtLoaiTruyen.setText(tuaTruyens.get(0).getLoaitruyen());
                    txtTacGia.setText(tuaTruyens.get(0).getTacgia());
                    strMatacgia = tuaTruyens.get(0).getMatacgia();
                    txtNhaXuatBan.setText(tuaTruyens.get(0).getNhaxuatban());
                    strMaNXB = tuaTruyens.get(0).getManxb();
                    txtMaNuoc.setText(tuaTruyens.get(0).getQuocgia());
                    manuoc = tuaTruyens.get(0).getMaquocgia();
                    txtSoTap.setText(tuaTruyens.get(0).getSotap());
                    txtNamXuatBan.setText(tuaTruyens.get(0).getNamxuatban());
                    txtTaiBan.setText(tuaTruyens.get(0).getTaiban());
                    txtTheoDoi.setText(tuaTruyens.get(0).getTheodoi2());
                    theodoi = tuaTruyens.get(0).getTheodoi2().equals("Đang theo dõi") ? true : false;
                }

            }

            @Override
            public void onFailure(Call<List<TuaTruyen>> call, Throwable t) {
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