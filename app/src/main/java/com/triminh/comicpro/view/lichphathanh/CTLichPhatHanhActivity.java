package com.triminh.comicpro.view.lichphathanh;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.comicpro.R;


import com.triminh.comicpro.adapter.lichphathanh.Adapter_CTLichPhatHanh;
import com.triminh.comicpro.api.ApiCTLichPhatHanh;
import com.triminh.comicpro.model.lichphathanh.CTLichPhatHanh;
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

public class CTLichPhatHanhActivity extends AppCompatActivity {

    ArrayList<CTLichPhatHanh> lstCTLichPhatHanh;
    Adapter_CTLichPhatHanh adapter;
    Context mContext;

    private Unbinder unbinder;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    String name = "ct_lichphathanh";
    @BindView(R.id.txtTongGiaBia)
    TextView txtTongGiaBia;

    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_t_lich_phat_hanh);
        //Thêm nút back
        unbinder = ButterKnife.bind(this);
        mContext = this;
        this.setTitle(ComicPro.objLichPhatHanh.getNgaythang());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GetCTLichPhatHanh();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                ComicPro.objCTLichPhatHanh = lstCTLichPhatHanh.get(position);
                GetChucNang(position);
            }
        }));

        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(() -> GetCTLichPhatHanh());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnThem:
                ComicPro.edit = false;
                Intent sub = new Intent(mContext, ThemChiTietLichPhatHanhActivity.class);
                sub.putExtra("name", name);
                startActivityForResult(sub, 100);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void GetCTLichPhatHanh() {
        ApiCTLichPhatHanh.apiCtLichPhatHanh.GetCTLichPhatHanh(ComicPro.objLichPhatHanh.getMalich()).enqueue(new Callback<List<CTLichPhatHanh>>() {
            @Override
            public void onResponse(Call<List<CTLichPhatHanh>> call, Response<List<CTLichPhatHanh>> response) {
                List<CTLichPhatHanh> ctLichPhatHanhs = response.body();
                if (ctLichPhatHanhs != null) {
                    lstCTLichPhatHanh = new ArrayList<>();
                    lstCTLichPhatHanh.addAll(ctLichPhatHanhs);
                    adapter = new Adapter_CTLichPhatHanh(mContext, lstCTLichPhatHanh);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    //Tổng thành tiền
                    if (lstCTLichPhatHanh.size() > 0) {
                        txtTongGiaBia.setText(lstCTLichPhatHanh.get(0).getTonggiabia());
                    }
                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                    swiperefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<CTLichPhatHanh>> call, Throwable t) {

            }
        });
    }

    public void UpdateDaMua(CTLichPhatHanh ctLichPhatHanh, int position) {
        ApiCTLichPhatHanh.apiCtLichPhatHanh.DaMua(ctLichPhatHanh.getId()).enqueue(new Callback<List<CTLichPhatHanh>>() {
            @Override
            public void onResponse(Call<List<CTLichPhatHanh>> call, Response<List<CTLichPhatHanh>> response) {
                List<CTLichPhatHanh> ctLichPhatHanhs = response.body();
                if (ctLichPhatHanhs.size() > 0) {
                    if (lstCTLichPhatHanh.get(position).getDamua() == 1) {
                        lstCTLichPhatHanh.get(position).setDamua(0);
                    } else {
                        lstCTLichPhatHanh.get(position).setDamua(1);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CTLichPhatHanh>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            GetCTLichPhatHanh();
        }
    }

    private void Delete(final CTLichPhatHanh ctLichPhatHanh, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa lịch phát hành (" + ctLichPhatHanh.getTuatruyen() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiCTLichPhatHanh.apiCtLichPhatHanh.Delete(ctLichPhatHanh.getId()).enqueue(new Callback<List<CTLichPhatHanh>>() {
                            @Override
                            public void onResponse(Call<List<CTLichPhatHanh>> call, Response<List<CTLichPhatHanh>> response) {
                                List<CTLichPhatHanh> ctLichPhatHanhs = response.body();
                                if (ctLichPhatHanhs.size() > 0) {
                                    lstCTLichPhatHanh.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa chi tiết lịch phát hành (" + ctLichPhatHanh.getTuatruyen() + ") thành công", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<CTLichPhatHanh>> call, Throwable t) {

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

    public void GetChucNang(int position) {
        final String[] chucnangs = {"Đã mua", "Sửa", "Xóa", "Hủy"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Chức năng");
        builder.setCancelable(false);

        builder.setItems(chucnangs, (dialogInterface, i1) -> {
            String chucnang = chucnangs[i1];
            dialogInterface.dismiss();
            if (chucnang.equals("Đã mua")) {
                CTLichPhatHanh ctLichPhatHanh = lstCTLichPhatHanh.get(position);
                UpdateDaMua(ctLichPhatHanh, position);
            } else if (chucnang.equals("Sửa")) {
                ComicPro.edit = true;
                Intent sub = new Intent(mContext, ThemChiTietLichPhatHanhActivity.class);
                sub.putExtra("name", "name");
                startActivityForResult(sub, 100);
            } else if (chucnang.equals("Xóa")) {
                CTLichPhatHanh ctLichPhatHanh = lstCTLichPhatHanh.get(position);
                Delete(ctLichPhatHanh, position);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
        return true;
    }


}