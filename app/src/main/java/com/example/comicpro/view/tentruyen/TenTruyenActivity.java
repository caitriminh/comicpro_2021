package com.example.comicpro.view.tentruyen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.comicpro.R;

import com.example.comicpro.adapter.tentruyen.Adapter_TenTruyen;
import com.example.comicpro.api.ApiDonViTinh;
import com.example.comicpro.api.ApiLoaiBia;
import com.example.comicpro.api.ApiNhapXuatTemp;
import com.example.comicpro.api.ApiQuaTang;
import com.example.comicpro.api.ApiTenTruyen;
import com.example.comicpro.model.danhmuc.DonViTinh;
import com.example.comicpro.model.danhmuc.LoaiBia;
import com.example.comicpro.model.danhmuc.QuaTang;
import com.example.comicpro.model.phieuxuat.NhapXuatTemp;
import com.example.comicpro.model.tentruyen.TenTruyen;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.IOnIntentReceived;
import com.example.comicpro.system.ImagePickerActivity;
import com.example.comicpro.system.RecyclerTouchListener;
import com.example.comicpro.system.TM_Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TenTruyenActivity extends AppCompatActivity {

    ArrayList < TenTruyen > lstTenTruyen;
    Adapter_TenTruyen adapter;
    ArrayList < LoaiBia > lstLoaiBia;
    ArrayList < QuaTang > lstQuaTang;
    ArrayList < DonViTinh > lstDonViTinh;
    public static final int REQUEST_IMAGE = 100;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    Context mContext;
    public static String strNgayThang = "", strMaQuaTang = "";
    Integer tap, sotrang, intMaLoaiBia = -1, intDVT = -1;
    Double giabia = 0.0;
    private IOnIntentReceived mIntentListener;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    TextInputEditText txtTap, txtGiaBia, txtSoTrang, txtTenTruyen, txtLoaiBia, txtNgayXuatBan, txtDonViTinh, txtQuaTang;
    Button btnLuu, btnDong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(ComicPro.objTuaTruyen.getTuatruyen());
        mContext = this;
        setContentView(R.layout.activity_ten_truyen);
        unbinder = ButterKnife.bind(this);
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetTenTruyen();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TenTruyen tenTruyen = lstTenTruyen.get(position);
                if (ComicPro.PhieuNhap == 0 && ComicPro.PhieuXuat == 0) {
                    SuaTenTruyen(tenTruyen, position);
                } else if (ComicPro.PhieuNhap == 1) {
                    InsertPhieuNhapXuat(tenTruyen, 0);
                } else if (ComicPro.PhieuXuat == 1) {
                    InsertPhieuNhapXuat(tenTruyen, 1);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if (ComicPro.PhieuNhap == 0 && ComicPro.PhieuXuat == 0) {
                    ComicPro.objTenTruyen = lstTenTruyen.get(position);
                    GetChucNang(ComicPro.objTenTruyen, position);
                }
            }
        }));
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTenTruyen();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (mIntentListener != null) {
                mIntentListener.onIntent(data, resultCode);
            }
        }
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(mContext, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnThem:
                ThemTenTruyen();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void SuaTenTruyen(TenTruyen tenTruyen, int position) {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_tentruyen, null);
        txtTenTruyen = view_bottom_sheet.findViewById(R.id.txtTenTruyen);
        txtNgayXuatBan = view_bottom_sheet.findViewById(R.id.txtNgayXuatBan);
        txtTap = view_bottom_sheet.findViewById(R.id.txtTap);
        txtGiaBia = view_bottom_sheet.findViewById(R.id.txtGiaBia);
        txtSoTrang = view_bottom_sheet.findViewById(R.id.txtSoTrang);
        txtLoaiBia = view_bottom_sheet.findViewById(R.id.txtLoaiBia);
        txtDonViTinh = view_bottom_sheet.findViewById(R.id.txtDonViTinh);
        txtQuaTang = view_bottom_sheet.findViewById(R.id.txtQuaTang);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        GetLoaiBia(txtLoaiBia);
        GetDonViTinh(txtDonViTinh);

        GetQuaTang(txtQuaTang);
        txtTenTruyen.setText(tenTruyen.getTentruyen());
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            txtNgayXuatBan.setText(formatter.format(tenTruyen.getNgayxuatban()));
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
            strNgayThang = formatter2.format(tenTruyen.getNgayxuatban());
        } catch (Exception e) {
            txtNgayXuatBan.setText("");
            strNgayThang = "";
        }


        txtTap.setText(tenTruyen.getTap().toString());
        txtGiaBia.setText(tenTruyen.getGiabia().toString());
        txtLoaiBia.setText(tenTruyen.getLoaibia());
        intMaLoaiBia = tenTruyen.getMaloaibia();

        txtDonViTinh.setText(tenTruyen.getDonvitinh());
        intDVT = tenTruyen.getMadvt();
        txtSoTrang.setText(tenTruyen.getSotrang().toString());
        txtQuaTang.setText(tenTruyen.getQuatang());
        strMaQuaTang = tenTruyen.getMaquatang();

        txtNgayXuatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                calendar.set(year, month, day);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = formatter.format(calendar.getTime());

                                txtNgayXuatBan.setText(strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                                strNgayThang = formatter2.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtTenTruyen.getText().toString().equals("")) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào tên truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (intMaLoaiBia < 0) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào loại bìa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (txtTap.getText().toString().equals("")) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào số tập.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                } else {
                    tap = Integer.parseInt(txtTap.getText().toString());
                }
                if (intDVT < 0) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào đơn vị tính.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
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
                if (strMaQuaTang.length() == 0) {
                    strMaQuaTang = "00";
                }
                ApiTenTruyen.apiTenTruyen.Update(txtTenTruyen.getText().toString(), intMaLoaiBia, tenTruyen.getMatruyen(), tap, intDVT, giabia, strMaQuaTang, strNgayThang, "", sotrang, ComicPro.tendangnhap).enqueue(new Callback < List < TenTruyen > >() {
                    @Override
                    public void onResponse(Call < List < TenTruyen > > call, Response < List < TenTruyen > > response) {
                        List < TenTruyen > tenTruyens = response.body();
                        if (tenTruyens != null) {
                            lstTenTruyen.get(position).setTentruyen(txtTenTruyen.getText().toString());

                            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = null;
                            try {
                                date = format.parse(txtNgayXuatBan.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            lstTenTruyen.get(position).setNgayxuatban(date);
                            lstTenTruyen.get(position).setGiabia(Double.parseDouble(txtGiaBia.getText().toString()));
                            lstTenTruyen.get(position).setQuatang(txtQuaTang.getText().toString());
                            lstTenTruyen.get(position).setSotrang(Integer.parseInt(txtSoTrang.getText().toString()));
                            lstTenTruyen.get(position).setTap(Integer.parseInt(txtTap.getText().toString()));
                            lstTenTruyen.get(position).setLoaibia(txtLoaiBia.getText().toString());
                            adapter.notifyItemChanged(position);
                            TM_Toast.makeText(mContext, "Cập nhật tên truyện (" + txtTenTruyen.getText() + ") thành công.", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                        } else {
                            TM_Toast.makeText(mContext, "Cập nhật lỗi.", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                        }
                    }

                    @Override
                    public void onFailure(Call < List < TenTruyen > > call, Throwable t) {
                        TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                    }

                });
                dialog.setCancelable(true);
                dialog.dismiss();
            }
        });

        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setCancelable(true);
                dialog.dismiss();
            }
        });
    }

    public void GetTenTruyen() {
        ApiTenTruyen.apiTenTruyen.GetTenTuyen(ComicPro.objTuaTruyen.getMatua(), 1).enqueue(new Callback < List < TenTruyen > >() {
            @Override
            public void onResponse(Call < List < TenTruyen > > call, Response < List < TenTruyen > > response) {
                List < TenTruyen > tenTruyens = response.body();
                if (tenTruyens != null) {
                    lstTenTruyen = new ArrayList < TenTruyen >();
                    lstTenTruyen.addAll(tenTruyens);
                    adapter = new Adapter_TenTruyen(mContext, lstTenTruyen);

                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                    mIntentListener = adapter;
                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call < List < TenTruyen > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetLoaiBia(TextView txtLoaiBia) {
        ApiLoaiBia.apiLoaiBia.GetLoaiBia().enqueue(new Callback < List < LoaiBia > >() {
            @Override
            public void onResponse(Call < List < LoaiBia > > call, Response < List < LoaiBia > > response) {
                List < LoaiBia > loaiBias = response.body();
                if (loaiBias != null) {
                    lstLoaiBia = new ArrayList < LoaiBia >();
                    lstLoaiBia.addAll(loaiBias);
                    txtLoaiBia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("Chọn loại bìa");
                            builder.setCancelable(false);
                            String[] arrayLoaiBia = new String[lstLoaiBia.size()];
                            int i = 0;
                            for (LoaiBia loaiBia : lstLoaiBia) {
                                arrayLoaiBia[i] = loaiBia.getLoaibia();
                                i++;
                            }
                            ;
                            builder.setSingleChoiceItems(arrayLoaiBia, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    LoaiBia loaiBia = lstLoaiBia.get(i);
                                    txtLoaiBia.setText(loaiBia.getLoaibia());
                                    intMaLoaiBia = loaiBia.getId();
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call < List < LoaiBia > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetQuaTang(TextView txtQuaTang) {
        ApiQuaTang.apiQuaTang.GetQuaTang().enqueue(new Callback < List < QuaTang > >() {
            @Override
            public void onResponse(Call < List < QuaTang > > call, Response < List < QuaTang > > response) {
                List < QuaTang > quaTangs = response.body();
                if (quaTangs != null) {
                    lstQuaTang = new ArrayList < QuaTang >();
                    lstQuaTang.addAll(quaTangs);
                    txtQuaTang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("Chọn quà tặng");
                            builder.setCancelable(false);
                            String[] arrayQuaTang = new String[lstQuaTang.size()];
                            int i = 0;
                            for (QuaTang quaTang : lstQuaTang) {
                                arrayQuaTang[i] = quaTang.getQuatang();
                                i++;
                            }
                            ;
                            builder.setSingleChoiceItems(arrayQuaTang, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    QuaTang quaTang = lstQuaTang.get(i);
                                    txtQuaTang.setText(quaTang.getQuatang());
                                    strMaQuaTang = quaTang.getMaquatang();
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });

                } else {
                    TM_Toast.makeText(mContext, "Không tải được dữ liệu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false);
                }
            }

            @Override
            public void onFailure(Call < List < QuaTang > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetDonViTinh(TextView txtDonViTinh) {
        ApiDonViTinh.apiDonViTinh.Unit("GETDATA", 0, "", "", "").enqueue(new Callback < List < DonViTinh > >() {
            @Override
            public void onResponse(Call < List < DonViTinh > > call, Response < List < DonViTinh > > response) {
                List < DonViTinh > donViTinhs = response.body();
                if (donViTinhs != null) {
                    lstDonViTinh = new ArrayList < DonViTinh >();
                    lstDonViTinh.addAll(donViTinhs);
                    txtDonViTinh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("Chọn đơn vị tính");
                            builder.setCancelable(false);
                            String[] arrayDonViTinh = new String[lstDonViTinh.size()];
                            int i = 0;
                            for (DonViTinh donViTinh : lstDonViTinh) {
                                arrayDonViTinh[i] = donViTinh.getDonvitinh();
                                i++;
                            }
                            ;
                            builder.setSingleChoiceItems(arrayDonViTinh, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DonViTinh donViTinh = lstDonViTinh.get(i);
                                    txtDonViTinh.setText(donViTinh.getDonvitinh());
                                    intDVT = donViTinh.getId();
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call < List < DonViTinh > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void ThemTenTruyen() {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_tentruyen, null);
        txtTenTruyen = view_bottom_sheet.findViewById(R.id.txtTenTruyen);
        txtNgayXuatBan = view_bottom_sheet.findViewById(R.id.txtNgayXuatBan);
        txtTap = view_bottom_sheet.findViewById(R.id.txtTap);
        txtGiaBia = view_bottom_sheet.findViewById(R.id.txtGiaBia);
        txtSoTrang = view_bottom_sheet.findViewById(R.id.txtSoTrang);
        txtLoaiBia = view_bottom_sheet.findViewById(R.id.txtLoaiBia);
        txtDonViTinh = view_bottom_sheet.findViewById(R.id.txtDonViTinh);
        txtQuaTang = view_bottom_sheet.findViewById(R.id.txtQuaTang);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        GetLoaiBia(txtLoaiBia);
        GetDonViTinh(txtDonViTinh);
        GetQuaTang(txtQuaTang);
        intMaLoaiBia = ComicPro.objTuaTruyen.getMaloaibia();
        txtLoaiBia.setText(ComicPro.objTuaTruyen.getLoaibia());
        giabia = ComicPro.objTuaTruyen.getGiabia();
        txtGiaBia.setText(ComicPro.objTuaTruyen.getGiabia().toString());
        intDVT = ComicPro.objTuaTruyen.getMadvt();
        txtDonViTinh.setText(ComicPro.objTuaTruyen.getDonvitinh());


        txtNgayXuatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                calendar.set(year, month, day);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = formatter.format(calendar.getTime());

                                txtNgayXuatBan.setText(strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                                strNgayThang = formatter2.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtTenTruyen.getText().toString().equals("")) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào tên truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (intMaLoaiBia < 0) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào loại bìa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (txtTap.getText().toString().equals("")) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào số tập.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                } else {
                    tap = Integer.parseInt(txtTap.getText().toString());
                }
                if (intDVT < 0) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào đơn vị tính.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
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
                ApiTenTruyen.apiTenTruyen.Insert(txtTenTruyen.getText().toString(), ComicPro.objTuaTruyen.getMatua(), Integer.parseInt(txtTap.getText().toString()), intDVT, intMaLoaiBia, "", ComicPro.tendangnhap, strMaQuaTang, sotrang, strNgayThang, giabia).enqueue(new Callback < List < TenTruyen > >() {
                    @Override
                    public void onResponse(Call < List < TenTruyen > > call, Response < List < TenTruyen > > response) {
                        List < TenTruyen > tenTruyens = response.body();
                        if (tenTruyens.size() > 0) {
                            GetTenTruyen();
                            TM_Toast.makeText(mContext, "Thêm mới tên truyện (" + txtTenTruyen.getText() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                        }
                    }

                    @Override
                    public void onFailure(Call < List < TenTruyen > > call, Throwable t) {
                        TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                    }
                });

            }
        });

        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setCancelable(true);
                dialog.dismiss();
            }
        });
    }

    private void Delete(final TenTruyen tenTruyen, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa tên truyện (" + tenTruyen.getTentruyen() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiTenTruyen.apiTenTruyen.Delete(tenTruyen.getMatruyen()).enqueue(new Callback < List < TenTruyen > >() {
                            @Override
                            public void onResponse(Call < List < TenTruyen > > call, Response < List < TenTruyen > > response) {
                                List < TenTruyen > tenTruyens = response.body();
                                if (tenTruyens.size() > 0) {
                                    lstTenTruyen.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa tên truyện (" + tenTruyen.getTentruyen() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call < List < TenTruyen > > call, Throwable t) {

                            }
                        });
                        dialogInterface.dismiss();
                    }
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

    private void InsertPhieuNhapXuat(TenTruyen tenTruyen, Integer loai) {
        ApiNhapXuatTemp.apiNhapXuatTemp.NhapXuatTemp("SAVE", 0, tenTruyen.getMatruyen(), 1, 0.0, ComicPro.tendangnhap, loai, "", "").enqueue(new Callback < List < NhapXuatTemp > >() {
            @Override
            public void onResponse(Call < List < NhapXuatTemp > > call, Response < List < NhapXuatTemp > > response) {
                String s = tenTruyen.getMatruyen();
                List < NhapXuatTemp > nhapXuatTemps = response.body();
                if (nhapXuatTemps != null) {
                    String strMessenger = "";
                    if (loai == 0) {
                        strMessenger = "Thêm phiếu nhập tên truyện (" + tenTruyen.getTentruyen() + ") thành công";
                    } else {
                        strMessenger = "Thêm phiếu xuất tên truyện (" + tenTruyen.getTentruyen() + ") thành công";
                    }
                    TM_Toast.makeText(mContext, strMessenger, TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                }
            }

            @Override
            public void onFailure(Call < List < NhapXuatTemp > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
            }
        });
    }

    public void GetChucNang(TenTruyen tenTruyen, int position) {
        final String[] chucnangs = {"Upload ảnh", "Xóa"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Chức năng");
        builder.setCancelable(false);
        int i = 0;

        builder.setItems(chucnangs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String chucnang = chucnangs[i];
                dialogInterface.dismiss(); // Close Dialog
                if (chucnang.equals("Xóa")) {
                    Delete(tenTruyen, position);
                } else if (chucnang.equals("Upload ảnh")) {
                    launchGalleryIntent();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_item, menu);
        getMenuInflater().inflate(R.menu.menu_item_add, menu);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setQueryHint("Tìm kiếm...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter == null) {
                    Log.w("adapter null", "null");
                }
                adapter.filter(newText);
                return false;
            }
        });
        return true;
    }
}