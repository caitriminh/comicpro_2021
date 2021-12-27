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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.example.comicpro.adapter.danhmuc.Adapter_DonVi;
import com.example.comicpro.api.ApiDonVi;
import com.example.comicpro.model.danhmuc.DonVi;
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


public class KhachHangActivity extends AppCompatActivity {

    ArrayList < DonVi > lstDonVi;
    Adapter_DonVi adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    TextInputEditText txtDonVi, txtDiaChi, txtSoDT;
    Button btnLuu, btnDong;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    String strMaDonVi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donvi);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Khách Hàng");
        GetKhachHang();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                DonVi donVi = lstDonVi.get(position);
                UpdateKhachHang(donVi, position);
            }

            @Override
            public void onLongClick(View view, int position) {
                DonVi donVi = lstDonVi.get(position);
                Delete(donVi, position);
            }
        }));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetKhachHang();
            }
        });
    }

    public void GetKhachHang() {
        ApiDonVi.apiDonVi.GetDonVi(1, "", 2).enqueue(new Callback < List < DonVi > >() {
            @Override
            public void onResponse(Call < List < DonVi > > call, Response < List < DonVi > > response) {
                List < DonVi > donVis = response.body();
                if (donVis != null) {
                    lstDonVi = new ArrayList < DonVi >();
                    lstDonVi.addAll(donVis);
                    adapter = new Adapter_DonVi(mContext, lstDonVi);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                    swiperefresh.setRefreshing(false);
                } else {
                    TM_Toast.makeText(mContext, "Không tải được dữ liệu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false);
                }
            }

            @Override
            public void onFailure(Call < List < DonVi > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void ThemKhachHang() {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_donvi, null);
        txtDonVi = view_bottom_sheet.findViewById(R.id.txtDonVi);
        txtDiaChi = view_bottom_sheet.findViewById(R.id.txtDiaChi);
        txtSoDT = view_bottom_sheet.findViewById(R.id.txtSoDT);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtDonVi.getText().length() == 0) {
                    Toast.makeText(mContext, "Vui lòng nhập vào tên nhà cung cấp.", Toast.LENGTH_LONG).show();
                } else {

                    ApiDonVi.apiDonVi.Insert(txtDonVi.getText().toString(), txtDiaChi.getText().toString(), txtSoDT.getText().toString(), "", "", "", 2, ComicPro.tendangnhap).enqueue(new Callback < List < DonVi > >() {
                        @Override
                        public void onResponse(Call < List < DonVi > > call, Response < List < DonVi > > response) {
                            List < DonVi > status = response.body();
                            if (status.get(0).getStatus().equals("OK")) {
                                GetKhachHang();
                                TM_Toast.makeText(mContext, "Thêm khách hàng (" + txtDonVi.getText() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                            }
                        }

                        @Override
                        public void onFailure(Call < List < DonVi > > call, Throwable t) {
                            TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                        }
                    });

                    //Lưu xong đóng dialog
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

    public void UpdateKhachHang(DonVi donVi, int position) {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_donvi, null);
        txtDonVi = view_bottom_sheet.findViewById(R.id.txtDonVi);
        txtDiaChi = view_bottom_sheet.findViewById(R.id.txtDiaChi);
        txtSoDT = view_bottom_sheet.findViewById(R.id.txtSoDT);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();
        strMaDonVi = donVi.getMadonvi();
        txtDonVi.setText(donVi.getDonvi());
        txtDiaChi.setText(donVi.getDiachi());
        txtSoDT.setText(donVi.getSodt());

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtDonVi.getText().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào tên khách hàng.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                } else {

                    ApiDonVi.apiDonVi.Update(donVi.getMadonvi(), txtDonVi.getText().toString(), txtDiaChi.getText().toString(), txtSoDT.getText().toString(), "", "", "", ComicPro.tendangnhap).enqueue(new Callback < List < DonVi > >() {
                        @Override
                        public void onResponse(Call < List < DonVi > > call, Response < List < DonVi > > response) {
                            List < DonVi > status = response.body();
                            if (status.get(0).getStatus().equals("OK")) {
                                lstDonVi.get(position).setDonvi(txtDonVi.getText().toString());
                                lstDonVi.get(position).setDiachi(txtDiaChi.getText().toString());
                                lstDonVi.get(position).setSodt(txtSoDT.getText().toString());
                                adapter.notifyItemChanged(position);
                                TM_Toast.makeText(mContext, "Cập nhật khách hàng (" + txtDonVi.getText() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                            } else {
                                TM_Toast.makeText(mContext, "Cập nhật khách hàng (" + txtDonVi.getText() + ") không thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                            }
                        }

                        @Override
                        public void onFailure(Call < List < DonVi > > call, Throwable t) {
                            TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                        }
                    });

                    //Lưu xong đóng dialog
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

    private void Delete(final DonVi donVi, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa nhà cung cấp (" + donVi.getDonvi() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiDonVi.apiDonVi.Delete(donVi.getMadonvi()).enqueue(new Callback < List < DonVi > >() {
                            @Override
                            public void onResponse(Call < List < DonVi > > call, Response < List < DonVi > > response) {
                                List < DonVi > status = response.body();
                                if (status.get(0).getStatus().equals("OK")) {
                                    lstDonVi.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa khách hàng (" + donVi.getDonvi() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                } else {
                                    TM_Toast.makeText(mContext, "Khách hàng (" + donVi.getDonvi() + ") không thể xóa.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call < List < DonVi > > call, Throwable t) {
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
                ThemKhachHang();
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