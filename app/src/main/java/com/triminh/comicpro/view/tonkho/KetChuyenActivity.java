package com.triminh.comicpro.view.tonkho;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.triminh.comicpro.adapter.tonkho.Adapter_KetChuyenSoDu;
import com.triminh.comicpro.api.ApiTonKho;
import com.triminh.comicpro.model.tonkho.KetChuyenSoDu;
import com.triminh.comicpro.system.ClickListener;
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


public class KetChuyenActivity extends AppCompatActivity {

    ArrayList <KetChuyenSoDu> lstKetChuyenSoDu;
    Adapter_KetChuyenSoDu adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ketchuyensodu);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Kết Chuyển");
        GetKetChuyen();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                KetChuyenSoDu ketChuyenSoDu = lstKetChuyenSoDu.get(position);
                Delete(ketChuyenSoDu, position);
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetKetChuyen();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetKetChuyen() {
        ApiTonKho.apiTonKho.GetKetChuyen().enqueue(new Callback < List < KetChuyenSoDu > >() {
            @Override
            public void onResponse(Call < List < KetChuyenSoDu > > call, Response < List < KetChuyenSoDu > > response) {
                List < KetChuyenSoDu > ketChuyenSoDus = response.body();
                if (ketChuyenSoDus.size() > 0) {
                    lstKetChuyenSoDu = new ArrayList < KetChuyenSoDu >();
                    lstKetChuyenSoDu.addAll(ketChuyenSoDus);
                    adapter = new Adapter_KetChuyenSoDu(mContext, lstKetChuyenSoDu);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                    //Làm mới dữ liệu
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call < List < KetChuyenSoDu > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final KetChuyenSoDu ketChuyenSoDu, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa kỳ kết chuyển (" + ketChuyenSoDu.getKy() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiTonKho.apiTonKho.DeleteKetChuyen(ketChuyenSoDu.getKy()).enqueue(new Callback < List < KetChuyenSoDu > >() {
                            @Override
                            public void onResponse(Call < List < KetChuyenSoDu > > call, Response < List < KetChuyenSoDu > > response) {
                                List < KetChuyenSoDu > ketChuyenSoDus = response.body();
                                if (ketChuyenSoDus != null) {

                                }
                            }

                            @Override
                            public void onFailure(Call < List < KetChuyenSoDu > > call, Throwable t) {
                              //  TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                            }
                        });
                        lstKetChuyenSoDu.remove(position);
                        adapter.notifyDataSetChanged();
                        TM_Toast.makeText(mContext, "Đã xóa kết chuyển kỳ (" + ketChuyenSoDu.getKy() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
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

    private void KetChuyenSoDu() {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn kết chuyển số dư sang tháng sau không?")
                .setCancelable(false)
                .setPositiveButton("Kết chuyển", R.drawable.ic_ketchuyen_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiTonKho.apiTonKho.ThucHienKetChuyen().enqueue(new Callback < List < KetChuyenSoDu > >() {
                            @Override
                            public void onResponse(Call < List < KetChuyenSoDu > > call, Response < List < KetChuyenSoDu > > response) {
                                List < KetChuyenSoDu > ketChuyenSoDus = response.body();
                                if (ketChuyenSoDus != null) {

                                }
                            }

                            @Override
                            public void onFailure(Call < List < KetChuyenSoDu > > call, Throwable t) {
                                //TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                            }
                        });
                        GetKetChuyen();
                        TM_Toast.makeText(mContext, "Đã kết chuyển kỳ thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnKetChuyen:
                KetChuyenSoDu();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_ketchuyen, menu);

        return true;
    }
}