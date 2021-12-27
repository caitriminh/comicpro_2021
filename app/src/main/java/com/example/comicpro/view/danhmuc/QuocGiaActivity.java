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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.comicpro.R;
import com.example.comicpro.adapter.danhmuc.Adapter_QuocGia;
import com.example.comicpro.api.ApiQuocGia;
import com.example.comicpro.model.danhmuc.QuocGia;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.RecyclerTouchListener;
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


public class QuocGiaActivity extends AppCompatActivity {

    ArrayList < QuocGia > lstQuocGia;

    Adapter_QuocGia adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    TextInputEditText txtQuocGia;
    Button btnLuu, btnDong;
    Integer position_item = -1;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quocgia);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Xuất Xứ");
        GetNation();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                position_item = position;
                QuocGia quocGia = (QuocGia) lstQuocGia.get(position);
                updateNation(view, quocGia);
            }

            @Override
            public void onLongClick(View view, int position) {
                QuocGia quocGia = (QuocGia) lstQuocGia.get(position);
                Delete(quocGia, position);
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetNation();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetNation() {
        ApiQuocGia.apiQuocGia.QuocGia("GET_DATA_COMIC", 0, "", "", "").enqueue(new Callback < List < QuocGia > >() {
            @Override
            public void onResponse(Call < List < QuocGia > > call, Response < List < QuocGia > > response) {
                List < QuocGia > quocGias = response.body();
                if (quocGias.size() > 0) {
                    lstQuocGia = new ArrayList < QuocGia >();
                    lstQuocGia.addAll(quocGias);
                    adapter = new Adapter_QuocGia(mContext, lstQuocGia);
                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call < List < QuocGia > > call, Throwable t) {
                Toast.makeText(mContext, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Delete(final QuocGia quocGia, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa quốc gia (" + quocGia.getQuocgia() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        ApiQuocGia.apiQuocGia.QuocGia("DELETE", quocGia.getId(), "", "", "").enqueue(new Callback < List < QuocGia > >() {
                            @Override
                            public void onResponse(Call < List < QuocGia > > call, Response < List < QuocGia > > response) {
                                List < QuocGia > status = response.body();
                                if (status != null) {
                                    lstQuocGia.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call < List < QuocGia > > call, Throwable t) {

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

    public void addNation() {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_quocgia, null);

        txtQuocGia = view_bottom_sheet.findViewById(R.id.txtQuocGia);
        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtQuocGia.getText().toString().length() == 0) {
                    Toast.makeText(mContext, "Vui lòng nhập vào tên quốc gia.", Toast.LENGTH_LONG).show();
                } else {
                    ApiQuocGia.apiQuocGia.QuocGia("SAVE", 0, txtQuocGia.getText().toString(), ComicPro.tendangnhap, "").enqueue(new Callback < List < QuocGia > >() {
                        @Override
                        public void onResponse(Call < List < QuocGia > > call, Response < List < QuocGia > > response) {
                            List < QuocGia > quocGias = response.body();
                            if (quocGias.size() > 0) {
                                lstQuocGia = new ArrayList < QuocGia >();
                                lstQuocGia.addAll(quocGias);
                                adapter = new Adapter_QuocGia(mContext, lstQuocGia);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                recycleView.setLayoutManager(layoutManager);
                                recycleView.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call < List < QuocGia > > call, Throwable t) {
                            Toast.makeText(mContext, "Call Api Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialog.setCancelable(true);
                    dialog.dismiss();
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

    public void updateNation(View view, QuocGia quocGia) {
        View view_bottom_sheet = LayoutInflater.from(view.getContext()).inflate(R.layout.bottomsheet_quocgia, null);

        txtQuocGia = view_bottom_sheet.findViewById(R.id.txtQuocGia);
        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        txtQuocGia.setText(quocGia.getQuocgia());
        BottomSheetDialog dialog = new BottomSheetDialog(view.getContext(), R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtQuocGia.getText().toString().length() == 0) {
                    Toast.makeText(mContext, "Vui lòng nhập vào tên quốc gia.", Toast.LENGTH_LONG).show();
                } else {
                    ApiQuocGia.apiQuocGia.QuocGia("UPDATE", quocGia.getId(), txtQuocGia.getText().toString(), "", ComicPro.tendangnhap).enqueue(new Callback < List < QuocGia > >() {
                        @Override
                        public void onResponse(Call < List < QuocGia > > call, Response < List < QuocGia > > response) {

                            if (response.body() != null) {
                                lstQuocGia.get(position_item).setQuocgia(txtQuocGia.getText().toString());
                                adapter.notifyItemChanged(position_item);
                                Toast.makeText(mContext, "Cập nhật thành công.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call < List < QuocGia > > call, Throwable t) {
                            Toast.makeText(mContext, "Call Api Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialog.setCancelable(true);
                    dialog.dismiss();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnThem:
                addNation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
        return true;
    }

}