package com.triminh.comicpro.view.phieunhap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;

import com.triminh.comicpro.adapter.phieunhap.Adapter_ChiTietPhieuNhap;
import com.triminh.comicpro.api.ApiChiTietPhieuNhap;
import com.triminh.comicpro.model.phieunhap.ChiTietPhieuNhap;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.system.TM_Toast;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietPhieuNhapActivity extends AppCompatActivity {
    ArrayList <ChiTietPhieuNhap> lstCtPhieuNhap;
    Adapter_ChiTietPhieuNhap adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_chi_tiet_phieu_nhap);
        unbinder = ButterKnife.bind(this);
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetChiTietPhieuNhap();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this, recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                ChiTietPhieuNhap chiTietPhieuNhap = (ChiTietPhieuNhap) lstCtPhieuNhap.get(position);
                Delete(chiTietPhieuNhap, position);
            }
        }));
        this.setTitle(ComicPro.objPhieuNhap.getMaphieu() + " (" + ComicPro.objPhieuNhap.getTongtien() + ")");
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetChiTietPhieuNhap();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void GetChiTietPhieuNhap() {
        ApiChiTietPhieuNhap.apiChiTietPhieuNhap.GetChiTietPhieuNhap(1, "", ComicPro.objPhieuNhap.getMaphieu()).enqueue(new Callback < List < ChiTietPhieuNhap > >() {
            @Override
            public void onResponse(Call < List < ChiTietPhieuNhap > > call, Response < List < ChiTietPhieuNhap > > response) {
                List < ChiTietPhieuNhap > chiTietPhieuNhaps = response.body();
                if (chiTietPhieuNhaps.size() > 0) {
                    lstCtPhieuNhap = new ArrayList < ChiTietPhieuNhap >();
                    lstCtPhieuNhap.addAll(chiTietPhieuNhaps);
                    adapter = new Adapter_ChiTietPhieuNhap(mContext, lstCtPhieuNhap);
                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                    swiperefresh.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call < List < ChiTietPhieuNhap > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final ChiTietPhieuNhap chiTietPhieuNhap, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa phiếu nhập (" + ComicPro.objPhieuNhap.getMaphieu() + ") có tên truyện (" + chiTietPhieuNhap.getTentruyen() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiChiTietPhieuNhap.apiChiTietPhieuNhap.Delete(chiTietPhieuNhap.getId()).enqueue(new Callback < List < ChiTietPhieuNhap > >() {
                            @Override
                            public void onResponse(Call < List < ChiTietPhieuNhap > > call, Response < List < ChiTietPhieuNhap > > response) {
                                List < ChiTietPhieuNhap > chiTietPhieuNhaps = response.body();
                                if (chiTietPhieuNhaps.size() > 0) {
                                    lstCtPhieuNhap.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa tên truyện (" + chiTietPhieuNhap.getTentruyen() + ") của phiếu nhập (" + ComicPro.objPhieuNhap.getMaphieu() + ") thành công", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call < List < ChiTietPhieuNhap > > call, Throwable t) {
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
}