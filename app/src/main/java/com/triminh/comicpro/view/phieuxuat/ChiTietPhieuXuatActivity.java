package com.triminh.comicpro.view.phieuxuat;

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
import com.triminh.comicpro.adapter.phieuxuat.Adapter_ChiTietPhieuXuat;
import com.triminh.comicpro.api.ApiChiTietPhieuXuat;
import com.triminh.comicpro.model.phieuxuat.ChiTietPhieuXuat;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.system.TM_Toast;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietPhieuXuatActivity extends AppCompatActivity {
    ArrayList <ChiTietPhieuXuat> lstCtPhieuXuat;
    Adapter_ChiTietPhieuXuat adapter;
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
        setContentView(R.layout.activity_chi_tiet_phieu_xuat);
        unbinder = ButterKnife.bind(this);
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetChiTietPhieuXuat();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this, recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                ChiTietPhieuXuat chiTietPhieuXuat =lstCtPhieuXuat.get(position);
                Delete(chiTietPhieuXuat, position);
            }
        }));
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        this.setTitle(ComicPro.objPhieuXuat.getMaphieu() + " (" + decimalFormat.format(ComicPro.objPhieuXuat.getTongthanhtien()) + ")");
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetChiTietPhieuXuat();
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

    public void GetChiTietPhieuXuat() {
        ApiChiTietPhieuXuat.apiChiTietPhieuXuat.GetChiTietPhieuXuat(3, "", ComicPro.objPhieuXuat.getMaphieu()).enqueue(new Callback < List < ChiTietPhieuXuat > >() {
            @Override
            public void onResponse(Call < List < ChiTietPhieuXuat > > call, Response < List < ChiTietPhieuXuat > > response) {
                List < ChiTietPhieuXuat > chiTietPhieuXuats = response.body();
                if (chiTietPhieuXuats.size() > 0) {
                    lstCtPhieuXuat = new ArrayList < ChiTietPhieuXuat >();
                    lstCtPhieuXuat.addAll(chiTietPhieuXuats);
                    adapter = new Adapter_ChiTietPhieuXuat(mContext, lstCtPhieuXuat);
                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                    swiperefresh.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call < List < ChiTietPhieuXuat > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final ChiTietPhieuXuat chiTietPhieuXuat, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa phiếu xuất (" + ComicPro.objPhieuXuat.getMaphieu() + ") có tên truyện (" + chiTietPhieuXuat.getTentruyen() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiChiTietPhieuXuat.apiChiTietPhieuXuat.Delete(chiTietPhieuXuat.getId()).enqueue(new Callback < List < ChiTietPhieuXuat > >() {
                            @Override
                            public void onResponse(Call < List < ChiTietPhieuXuat > > call, Response < List < ChiTietPhieuXuat > > response) {
                                List < ChiTietPhieuXuat > chiTietPhieuXuats = response.body();
                                if (chiTietPhieuXuats.size()>0) {
                                    lstCtPhieuXuat.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa tên truyện (" + chiTietPhieuXuat.getTentruyen() + ") của phiếu xuất (" + ComicPro.objPhieuXuat.getMaphieu() + ") thành công", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call < List < ChiTietPhieuXuat > > call, Throwable t) {
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