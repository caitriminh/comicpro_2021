package com.triminh.comicpro.view.danhmuc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.triminh.comicpro.adapter.danhmuc.Adapter_LoaiTruyen;
import com.triminh.comicpro.api.ApiLoaiTruyen;
import com.triminh.comicpro.model.danhmuc.LoaiTruyen;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.system.TM_Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoaiTruyenActivity extends AppCompatActivity {

    ArrayList <LoaiTruyen> lstLoaiTruyen;
    Adapter_LoaiTruyen adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    TextInputEditText txtTacGia;
    Button btnLuu, btnDong;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaitruyen);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Loại Truyện");
        GetLoaiTruyen();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                ComicPro.objLoaiTruyen = lstLoaiTruyen.get(position);
                Intent intent = new Intent(LoaiTruyenActivity.this, TuaTruyenLoaiTruyenActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                LoaiTruyen loaiTruyen = lstLoaiTruyen.get(position);
                Delete(loaiTruyen, position);
            }
        }));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetLoaiTruyen() {
        ApiLoaiTruyen.apiLoaiTruyen.GetLoaiTruyen().enqueue(new Callback < List < LoaiTruyen > >() {
            @Override
            public void onResponse(Call < List < LoaiTruyen > > call, Response < List < LoaiTruyen > > response) {
                List < LoaiTruyen > loaiTruyens = response.body();
                if (loaiTruyens.size() > 0) {
                    lstLoaiTruyen = new ArrayList < LoaiTruyen >();
                    lstLoaiTruyen.addAll(loaiTruyens);
                    adapter = new Adapter_LoaiTruyen(mContext, lstLoaiTruyen);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                } else {
                    TM_Toast.makeText(mContext, "Không tải được dữ liệu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false);
                }
            }

            @Override
            public void onFailure(Call < List < LoaiTruyen > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final LoaiTruyen loaiTruyen, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa loại truyện (" + loaiTruyen.getLoaitruyen() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiLoaiTruyen.apiLoaiTruyen.Delete(loaiTruyen.getMaloai()).enqueue(new Callback < List < LoaiTruyen > >() {
                            @Override
                            public void onResponse(Call < List < LoaiTruyen > > call, Response < List < LoaiTruyen > > response) {
                                List < LoaiTruyen > status = response.body();
                                if (status.get(0).getStatus().equals("OK")) {
                                    lstLoaiTruyen.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa loại truyện ("+loaiTruyen.getLoaitruyen()+")", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                }
                                else{
                                    TM_Toast.makeText(mContext, "Loại truyện ("+loaiTruyen.getLoaitruyen()+") không thể xóa", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call < List < LoaiTruyen > > call, Throwable t) {
                                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
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
//
//    public void ThemTacGia() {
//        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_tacgia, null);
//
//        txtTacGia = view_bottom_sheet.findViewById(R.id.txtTacGia);
//        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
//        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);
//        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
//        dialog.setContentView(view_bottom_sheet);
//        dialog.setCancelable(false);
//        dialog.show();
//
//        btnLuu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (txtTacGia.getText().toString().length() == 0) {
//                    TM_Toast.makeText(mContext, "Vui lòng nhập vào tên tác giả.", Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
//                } else {
//                    ApiTacGia.apiTacGia.Insert(txtTacGia.getText().toString(), ComicPro.tendangnhap).enqueue(new Callback < List < TacGia > >() {
//                        @Override
//                        public void onResponse(Call < List < TacGia > > call, Response < List < TacGia > > response) {
//                            List < TacGia > tacGias = response.body();
//                            if (tacGias != null) {
//                                GetTacGia();
//                                TM_Toast.makeText(mContext, "Đã thêm tác giả (" + txtTacGia.getText().toString() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
//                                txtTacGia.setText("");
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call < List < TacGia > > call, Throwable t) {
//                            TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
//                        }
//                    });
//                    dialog.setCancelable(true);
//                    dialog.dismiss();
//                }
//            }
//        });
//
//        btnDong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.setCancelable(true);
//                dialog.dismiss();
//            }
//        });
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
//            case R.id.btnThem:
//                ThemTacGia();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
        return true;
    }

}