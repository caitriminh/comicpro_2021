package com.triminh.comicpro.view.phieuxuat;

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
import com.triminh.comicpro.adapter.phieuxuat.Adapter_PhieuXuat;
import com.triminh.comicpro.api.ApiPhieuXuat;
import com.triminh.comicpro.model.phieuxuat.PhieuXuat;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.system.TM_Toast;
import com.triminh.comicpro.view.tuatruyen.TuaTruyenActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.text.DateFormat;
import java.text.DecimalFormat;
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


public class PhieuXuatActivity extends AppCompatActivity {

    ArrayList <PhieuXuat> lstPhieuXuat;
    Adapter_PhieuXuat adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    private Unbinder unbinder;

    String strTuNgay = "", strDenNgay = "";
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
        setTitle("Phiếu Xuất");
        GetPhieuXuat(strTuNgay, strDenNgay);
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                ComicPro.objPhieuXuat = lstPhieuXuat.get(position);
                Intent intent = new Intent(PhieuXuatActivity.this, ChiTietPhieuXuatActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                PhieuXuat phieuXuat = lstPhieuXuat.get(position);
                Delete(phieuXuat, position);
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetPhieuXuat(strTuNgay, strDenNgay);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetPhieuXuat(String tungay, String denngay) {
        ApiPhieuXuat.apiPhieuXuat.GetPhieuXuat(option, tungay, denngay).enqueue(new Callback < List < PhieuXuat > >() {
            @Override
            public void onResponse(Call < List < PhieuXuat > > call, Response < List < PhieuXuat > > response) {
                List < PhieuXuat > phieuXuats = response.body();
                if (phieuXuats.size() > 0) {
                    lstPhieuXuat = new ArrayList < PhieuXuat >();
                    lstPhieuXuat.addAll(phieuXuats);
                    adapter = new Adapter_PhieuXuat(mContext, lstPhieuXuat);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    //Tổng thành tiền
                    if (lstPhieuXuat.size() > 0) {
                        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                        txtTongThanhTien.setText(decimalFormat.format(lstPhieuXuat.get(0).getTongthanhtien()));
                    }
                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                    //Làm mới dữ liệu
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call < List < PhieuXuat > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }


    private void Delete(final PhieuXuat phieuXuat, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa phiếu xuất (" + phieuXuat.getMaphieu() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiPhieuXuat.apiPhieuXuat.Delete(phieuXuat.getMaphieu()).enqueue(new Callback < List < PhieuXuat > >() {
                            @Override
                            public void onResponse(Call < List < PhieuXuat > > call, Response < List < PhieuXuat > > response) {
                                List < PhieuXuat > phieuXuats = response.body();
                                if (phieuXuats.size() > 0) {
                                    lstPhieuXuat.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa phiếu xuất (" + phieuXuat.getMaphieu() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call < List < PhieuXuat > > call, Throwable t) {
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
                GetPhieuXuat(strTuNgay, strDenNgay);
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
                ComicPro.PhieuXuat = 1;
                Intent intent = new Intent(PhieuXuatActivity.this, TuaTruyenActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDonHang:
                ComicPro.PhieuXuat = 0;
                Intent intent2 = new Intent(PhieuXuatActivity.this, PhieuXuatTempActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_date, menu);
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
        getMenuInflater().inflate(R.menu.menu_item_donhang, menu);
        return true;
    }
}