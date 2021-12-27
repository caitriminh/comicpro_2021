package com.example.comicpro.view.phieunhap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.example.comicpro.adapter.phieunhap.Adapter_PhieuNhap;
import com.example.comicpro.api.ApiPhieuNhap;
import com.example.comicpro.model.phieunhap.PhieuNhap;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.RecyclerTouchListener;
import com.example.comicpro.system.TM_Toast;
import com.example.comicpro.view.tuatruyen.TuaTruyenActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhieuNhapActivity extends AppCompatActivity {

    ArrayList < PhieuNhap > lstPhieuNhap;

    Adapter_PhieuNhap adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    private Unbinder unbinder;

    String name = "",  strTuNgay = "", strDenNgay = "";
    @BindView(R.id.txtTongThanhTien)
    TextView txtTongThanhTien;
    Integer option = 1;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieunhap);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Phiếu Nhập");
        GetPhieuNhap(strTuNgay, strDenNgay);
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                ComicPro.objPhieuNhap = lstPhieuNhap.get(position);
                Intent intent = new Intent(PhieuNhapActivity.this, ChiTietPhieuNhapActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                PhieuNhap phieuNhap = lstPhieuNhap.get(position);
                Delete(phieuNhap, position);
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetPhieuNhap(strTuNgay, strDenNgay);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetPhieuNhap(String tungay, String denngay) {
        ApiPhieuNhap.apiPhieuNhap.GetPhieuNhap(option, tungay, denngay, "").enqueue(new Callback < List < PhieuNhap > >() {
            @Override
            public void onResponse(Call < List < PhieuNhap > > call, Response < List < PhieuNhap > > response) {
                List < PhieuNhap > phieuNhaps = response.body();
                if (phieuNhaps.size() > 0) {
                    lstPhieuNhap = new ArrayList < PhieuNhap >();
                    lstPhieuNhap.addAll(phieuNhaps);
                    adapter = new Adapter_PhieuNhap(mContext, lstPhieuNhap);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    //Tổng thành tiền
                    if (lstPhieuNhap.size() > 0) {
                        txtTongThanhTien.setText(lstPhieuNhap.get(0).getTongthanhtien());
                    }
                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                    //Làm mới dữ liệu
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call < List < PhieuNhap > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }


    private void Delete(final PhieuNhap phieuNhap, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa phiếu nhập (" + phieuNhap.getMaphieu() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiPhieuNhap.apiPhieuNhap.Delete(phieuNhap.getMaphieu()).enqueue(new Callback < List < PhieuNhap > >() {
                            @Override
                            public void onResponse(Call < List < PhieuNhap > > call, Response < List < PhieuNhap > > response) {
                                List < PhieuNhap > phieuNhaps = response.body();
                                if (phieuNhaps != null) {
                                    lstPhieuNhap.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa phiếu nhập (" + phieuNhap.getMaphieu() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call < List < PhieuNhap > > call, Throwable t) {
                                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
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


    public void TimKiemTheoNgay() {
        MaterialDatePicker.Builder < Pair < Long, Long > > builder = MaterialDatePicker.Builder.dateRangePicker();
        MaterialDatePicker picker = builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar).build();
        picker.show(getSupportFragmentManager(), picker.toString());
        picker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.dismiss();
            }
        });

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Object data = selection;
                Pair < Long, Long > result = (Pair < Long, Long >) selection;
                long startDate = result.first;
                long endDate = result.second;
                DateFormat formatter = new SimpleDateFormat("yyyyMMdd");

                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTimeInMillis(startDate);

                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTimeInMillis(endDate);
                option = 1;
                strTuNgay = formatter.format(calendarStart.getTime());
                strDenNgay = formatter.format(calendarEnd.getTime());
                GetPhieuNhap(strTuNgay, strDenNgay);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnTimKiemTheoNgay:
                TimKiemTheoNgay();
                break;
            case R.id.btnThem:
                ComicPro.PhieuNhap = 1;
                Intent intent = new Intent(PhieuNhapActivity.this, TuaTruyenActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDonHang:
                ComicPro.PhieuNhap = 0;
//                Intent intent2 = new Intent(PhieuNhapActivity.this, PhieuNhapTempActivity.class);
//                startActivity(intent2);

                Intent sub = new Intent(PhieuNhapActivity.this, PhieuNhapTempActivity.class);
                sub.putExtra("name", name);
                startActivityForResult(sub, 100);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 100) {
                GetPhieuNhap(strTuNgay, strDenNgay);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_date, menu);
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
        getMenuInflater().inflate(R.menu.menu_item_donhang, menu);
        return true;
    }
}