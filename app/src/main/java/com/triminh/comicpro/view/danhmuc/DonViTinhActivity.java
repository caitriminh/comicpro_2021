package com.triminh.comicpro.view.danhmuc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.triminh.comicpro.adapter.danhmuc.Adapter_DonViTinh;
import com.triminh.comicpro.api.ApiDonViTinh;
import com.triminh.comicpro.model.danhmuc.DonViTinh;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.system.TM_Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class DonViTinhActivity extends AppCompatActivity {

    ArrayList <DonViTinh> lstDonViTinh;

    Adapter_DonViTinh adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    private Unbinder unbinder;

    TextInputEditText txtDonViTinh;
    Button btnLuu, btnDong;
    Integer position_item = -1;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donvitinh);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Đơn Vị Tính");
        GetUnit();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                position_item = position;
                DonViTinh donViTinh = (DonViTinh) lstDonViTinh.get(position);
                updateUnit(view, donViTinh);
            }

            @Override
            public void onLongClick(View view, int position) {
                DonViTinh donViTinh = (DonViTinh) lstDonViTinh.get(position);
                DeleteUnit(donViTinh, position);
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetUnit();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetUnit() {
        ApiDonViTinh.apiDonViTinh.Unit("GETDATA", 0, "", "", "").enqueue(new Callback < List < DonViTinh > >() {
            @Override
            public void onResponse(Call < List < DonViTinh > > call, Response < List < DonViTinh > > response) {
                List < DonViTinh > donViTinhs = response.body();
                if (donViTinhs.size() > 0) {
                    lstDonViTinh = new ArrayList < DonViTinh >();
                    lstDonViTinh.addAll(donViTinhs);
                    adapter = new Adapter_DonViTinh(mContext, lstDonViTinh);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);

                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    TM_Toast.makeText(mContext, "Không tải được dữ liệu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false);
                }
            }

            @Override
            public void onFailure(Call < List < DonViTinh > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void DeleteUnit(final DonViTinh donViTinh, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa đơn vị tính (" + donViTinh.getDonvitinh() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        Integer id = donViTinh.getId();
                        ApiDonViTinh.apiDonViTinh.Unit("DELETE", id, "", "", "").enqueue(new Callback < List < DonViTinh > >() {
                            @Override
                            public void onResponse(Call < List < DonViTinh > > call, Response < List < DonViTinh > > response) {
                                List < DonViTinh > lstStatus = response.body();
                                if (lstStatus != null) {
                                    if (lstStatus.get(0).getStatus().equals("OK")) {
                                        lstDonViTinh.remove(position);
                                        adapter.notifyDataSetChanged();
                                        TM_Toast.makeText(mContext, "Đã xóa", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                                    } else {
                                        TM_Toast.makeText(mContext, "Xóa không thành công.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call < List < DonViTinh > > call, Throwable t) {
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

    @OnClick(R.id.btnAdd)
    void AddUnit(View view) {
        addUnit(view);
    }

    public void addUnit(View view) {
        View view_bottom_sheet = LayoutInflater.from(view.getContext()).inflate(R.layout.bottomsheet_donvitinh, null);
        txtDonViTinh = view_bottom_sheet.findViewById(R.id.txtDonViTinh);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(view.getContext(), R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtDonViTinh.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào đơn vị tính.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                } else {

                    ApiDonViTinh.apiDonViTinh.Unit("SAVE", 0, txtDonViTinh.getText().toString(), ComicPro.tendangnhap, "").enqueue(new Callback < List < DonViTinh > >() {
                        @Override
                        public void onResponse(Call < List < DonViTinh > > call, Response < List < DonViTinh > > response) {
                            List < DonViTinh > donViTinhs = response.body();
                            if (donViTinhs.size() > 0) {
                                lstDonViTinh = new ArrayList < DonViTinh >();
                                lstDonViTinh.addAll(donViTinhs);
                                adapter = new Adapter_DonViTinh(mContext, lstDonViTinh);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                recycleView.setLayoutManager(layoutManager);
                                recycleView.setAdapter(adapter);
                                TM_Toast.makeText(mContext, "Đã thêm đơn vị tính (" + txtDonViTinh.getText() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                txtDonViTinh.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call < List < DonViTinh > > call, Throwable t) {
                            TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
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

    public void updateUnit(View view, DonViTinh donViTinh) {
        View view_bottom_sheet = LayoutInflater.from(view.getContext()).inflate(R.layout.bottomsheet_donvitinh, null);
        txtDonViTinh = view_bottom_sheet.findViewById(R.id.txtDonViTinh);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        txtDonViTinh.setText(donViTinh.getDonvitinh());
        BottomSheetDialog dialog = new BottomSheetDialog(view.getContext(), R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtDonViTinh.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào đơn vị tính.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                } else {

                    ApiDonViTinh.apiDonViTinh.Unit("UPDATE", donViTinh.getId(), txtDonViTinh.getText().toString(), "", ComicPro.tendangnhap).enqueue(new Callback < List < DonViTinh > >() {
                        @Override
                        public void onResponse(Call < List < DonViTinh > > call, Response < List < DonViTinh > > response) {
                            if (response.body() != null) {
                                lstDonViTinh.get(position_item).setDonvitinh(txtDonViTinh.getText().toString());
                                adapter.notifyItemChanged(position_item);
                                TM_Toast.makeText(mContext, "Cập nhật thành công.", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                            }
                        }

                        @Override
                        public void onFailure(Call < List < DonViTinh > > call, Throwable t) {
                            TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}