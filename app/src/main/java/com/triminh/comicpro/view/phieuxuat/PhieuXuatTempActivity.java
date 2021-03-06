package com.triminh.comicpro.view.phieuxuat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.triminh.comicpro.adapter.phieunhap.Adapter_PhieuNhapTemp;
import com.triminh.comicpro.api.ApiNhapXuatTemp;
import com.triminh.comicpro.model.phieuxuat.NhapXuatTemp;
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
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhieuXuatTempActivity extends AppCompatActivity {

    List <NhapXuatTemp> lstNhapXuatTemp;
    Adapter_PhieuNhapTemp adapter;

    private Unbinder unbinder;
    Context mConText;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;


    TextInputEditText txtTentruyen, txtGiaBan, txtSoLuong;
    Button btnLuu, btnDong;
    Context mContext;
    double dblTongTien = 0;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private static final int THANHTOAN_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieunhaptemp);
        mContext = this;
        unbinder = ButterKnife.bind(this);

        setTitle("Phi???u Xu???t");

        GetPhieuXuatTemp();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                NhapXuatTemp nhapXuatTemp = lstNhapXuatTemp.get(position);
                updatePhieuNhapTemp(nhapXuatTemp, position);
            }

            @Override
            public void onLongClick(View view, int position) {
                NhapXuatTemp nhapXuatTemp = lstNhapXuatTemp.get(position);
                Delete(nhapXuatTemp, position);
            }
        }));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetPhieuXuatTemp();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnThanhToan:
                GetThanhToan();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void GetThanhToan() {
        ApiNhapXuatTemp.apiNhapXuatTemp.NhapXuatTemp("GET_DATA", 0, "", 0, 0.0,  "",1,"","").enqueue(new Callback < List < NhapXuatTemp > >() {
            @Override
            public void onResponse(Call < List < NhapXuatTemp > > call, Response < List < NhapXuatTemp > > response) {
                List < NhapXuatTemp > nhapXuatTemps = response.body();
                if (nhapXuatTemps.size() > 0) {
                    Intent intent = new Intent(PhieuXuatTempActivity.this, ThanhToanPhieuXuatActivity.class);
                    startActivityForResult(intent, THANHTOAN_ACTIVITY_REQUEST_CODE);
                } else {
                    TM_Toast.makeText(PhieuXuatTempActivity.this, "Kh??ng t???n t???i ????n h??ng ????? thanh to??n.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                }
            }

            @Override
            public void onFailure(Call < List < NhapXuatTemp > > call, Throwable t) {
                TM_Toast.makeText(PhieuXuatTempActivity.this, "Call API fail.", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void GetPhieuXuatTemp() {
        ApiNhapXuatTemp.apiNhapXuatTemp.NhapXuatTemp("GET_DATA", 0, "", 0, 0.0, ComicPro.tendangnhap, 1,"","").enqueue(new Callback < List < NhapXuatTemp > >() {
            @Override
            public void onResponse(Call < List < NhapXuatTemp > > call, Response < List < NhapXuatTemp > > response) {
                List < NhapXuatTemp > nhapXuatTemps = response.body();
                if (nhapXuatTemps.size() > 0) {
                    lstNhapXuatTemp = new ArrayList < NhapXuatTemp >();
                    lstNhapXuatTemp.addAll(nhapXuatTemps);
                    adapter = new Adapter_PhieuNhapTemp(mConText, lstNhapXuatTemp);

                    recycleView.setLayoutManager(new GridLayoutManager(mConText, 2));
                    recycleView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    TM_Toast.makeText(getApplication(), "Kh??ng t??m th???y ????n h??ng.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false);
                }
            }

            @Override
            public void onFailure(Call < List < NhapXuatTemp > > call, Throwable t) {
                TM_Toast.makeText(getApplication(), "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final NhapXuatTemp nhapXuatTemp, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("X??c Nh???n")
                .setMessage("B???n c?? mu???n x??a t??n truy???n (" + nhapXuatTemp.getTentruyen() + ") n??y kh??ng?")
                .setCancelable(false)
                .setPositiveButton("X??a", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        ApiNhapXuatTemp.apiNhapXuatTemp.NhapXuatTemp("DELETE", nhapXuatTemp.getId(), "", 0, 0.0, "", 0,"","").enqueue(new Callback < List < NhapXuatTemp > >() {
                            @Override
                            public void onResponse(Call < List < NhapXuatTemp > > call, Response < List < NhapXuatTemp > > response) {
                                List < NhapXuatTemp > status = response.body();
                                if (status != null) {
                                    lstNhapXuatTemp.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(getApplication(), "???? x??a t??n truy???n (" + nhapXuatTemp.getTentruyen() + ") trong ????n h??ng th??nh c??ng.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                    if (status.size() == 0) {
                                        finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call < List < NhapXuatTemp > > call, Throwable t) {
                                TM_Toast.makeText(getApplication(), "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                            }
                        });
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("????ng", R.drawable.ic_close_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        mBottomSheetDialog.show();
    }

    public void updatePhieuNhapTemp(NhapXuatTemp nhapXuatTemp, int position) {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_phieunhaptem, null);

        txtGiaBan = view_bottom_sheet.findViewById(R.id.txtDonGia);
        txtSoLuong = view_bottom_sheet.findViewById(R.id.txtSoLuong);
        txtTentruyen = view_bottom_sheet.findViewById(R.id.txtTenTruyen);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        txtTentruyen.setText(nhapXuatTemp.getTentruyen());
        txtGiaBan.setText(nhapXuatTemp.getDongia().toString());
        txtSoLuong.setText(nhapXuatTemp.getSoluong().toString());

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();
        txtTentruyen.setEnabled(false);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Integer.parseInt(txtSoLuong.getText().toString()) <= 0) {
                    TM_Toast.makeText(getApplication(), "Vui l??ng nh???p v??o s??? l?????ng.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }

                if (Double.parseDouble(txtGiaBan.getText().toString()) <= 0) {
                    TM_Toast.makeText(getApplication(), "Vui l??ng nh???p v??o ????n gi??.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                ApiNhapXuatTemp.apiNhapXuatTemp.NhapXuatTemp("UPDATE", nhapXuatTemp.getId(), "", Integer.parseInt(txtSoLuong.getText().toString()), Double.parseDouble(txtGiaBan.getText().toString()), "", 0,"","").enqueue(new Callback < List < NhapXuatTemp > >() {
                    @Override
                    public void onResponse(Call < List < NhapXuatTemp > > call, Response < List < NhapXuatTemp > > response) {
                        List < NhapXuatTemp > nhapXuatTemps = response.body();
                        if (nhapXuatTemp != null) {
                            lstNhapXuatTemp.get(position).setDongia(Double.parseDouble(txtGiaBan.getText().toString()));
                            lstNhapXuatTemp.get(position).setSoluong(Integer.parseInt(txtSoLuong.getText().toString()));
                            adapter.notifyDataSetChanged();
                            TM_Toast.makeText(getApplication(), "C???p nh???t ????n h??ng th??nh c??ng.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                        }
                    }

                    @Override
                    public void onFailure(Call < List < NhapXuatTemp > > call, Throwable t) {
                        TM_Toast.makeText(mConText, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                    }
                });
                dialog.setCancelable(true);
                dialog.dismiss();
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
        getMenuInflater().inflate(R.menu.menu_item_thanhtoan, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == THANHTOAN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                GetPhieuXuatTemp();
                finish();
            }
        }
    }
}