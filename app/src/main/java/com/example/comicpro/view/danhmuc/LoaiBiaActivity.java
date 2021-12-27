package com.example.comicpro.view.danhmuc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.example.comicpro.adapter.danhmuc.Adapter_LoaiBia;
import com.example.comicpro.api.ApiLoaiBia;
import com.example.comicpro.model.danhmuc.LoaiBia;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.RecyclerTouchListener;
import com.example.comicpro.system.TM_Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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


public class LoaiBiaActivity extends AppCompatActivity {

    ArrayList < LoaiBia > lstLoaiBia;
    Adapter_LoaiBia adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    TextInputEditText txtLoaiBia;
    Button btnLuu, btnDong;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaibia);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Loại Bìa");
        GetLoaiBia();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                LoaiBia loaiBia = lstLoaiBia.get(position);
                Delete(loaiBia, position);
            }
        }));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetLoaiBia() {
        ApiLoaiBia.apiLoaiBia.GetLoaiBia().enqueue(new Callback < List < LoaiBia > >() {
            @Override
            public void onResponse(Call < List < LoaiBia > > call, Response < List < LoaiBia > > response) {
                List < LoaiBia > loaiBias = response.body();
                if (loaiBias.size() > 0) {
                    lstLoaiBia = new ArrayList < LoaiBia >();
                    lstLoaiBia.addAll(loaiBias);
                    adapter = new Adapter_LoaiBia(mContext, lstLoaiBia);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                } else {
                    TM_Toast.makeText(mContext, "Không tải được dữ liệu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false);
                }
            }

            @Override
            public void onFailure(Call < List < LoaiBia > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final LoaiBia loaiBia, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa loại bìa (" + loaiBia.getLoaibia() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiLoaiBia.apiLoaiBia.Delete(loaiBia.getId()).enqueue(new Callback < List < LoaiBia > >() {
                            @Override
                            public void onResponse(Call < List < LoaiBia > > call, Response < List < LoaiBia > > response) {
                                List < LoaiBia > status = response.body();
                                if (status.get(0).getStatus().equals("OK")) {
                                    lstLoaiBia.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa loại bìa (" + loaiBia.getLoaibia() + ") thành công.", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                                } else {
                                    TM_Toast.makeText(mContext, "Loại bìa (" + loaiBia.getLoaibia() + ") không thể xóa.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call < List < LoaiBia > > call, Throwable t) {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnThem:
                ThemLoaiBia();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ThemLoaiBia() {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_loaibia, null);
        txtLoaiBia = view_bottom_sheet.findViewById(R.id.txtLoaiBia);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loaibia = txtLoaiBia.getText().toString();
                if (loaibia.length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào loại bìa.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                } else {
                    ApiLoaiBia.apiLoaiBia.Insert(txtLoaiBia.getText().toString(), ComicPro.tendangnhap).enqueue(new Callback < List < LoaiBia > >() {
                        @Override
                        public void onResponse(Call < List < LoaiBia > > call, Response < List < LoaiBia > > response) {
                            List < LoaiBia > status = response.body();
                            if (status.size() > 0) {
                                GetLoaiBia();
                                TM_Toast.makeText(mContext, "Thêm mới loại bìa (" + txtLoaiBia.getText() + ") thành công.", Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                                txtLoaiBia.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call < List < LoaiBia > > call, Throwable t) {
                            TM_Toast.makeText(mContext, "Call API fail", Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                        }
                    });
                }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
        return true;
    }

}