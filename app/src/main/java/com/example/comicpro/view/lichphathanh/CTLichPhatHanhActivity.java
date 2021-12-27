package com.example.comicpro.view.lichphathanh;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.comicpro.R;


import com.example.comicpro.adapter.lichphathanh.Adapter_CTLichPhatHanh;
import com.example.comicpro.api.ApiCTLichPhatHanh;
import com.example.comicpro.model.lichphathanh.CTLichPhatHanh;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.RecyclerTouchListener;
import com.example.comicpro.system.TM_Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CTLichPhatHanhActivity extends AppCompatActivity {

    ArrayList < CTLichPhatHanh > lstCTLichPhatHanh;
    Adapter_CTLichPhatHanh adapter;

    public static final int REQUEST_IMAGE = 100;
    private Unbinder unbinder;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    Context mContext;

    String strNgayThang = "";

    TextInputEditText txtTuaTruyen, txtGiaBia, txtNgayXuatBan;
    Button btnLuu, btnDong;
    @BindView(R.id.txtTongGiaBia)
    TextView txtTongGiaBia;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(ComicPro.objLichPhatHanh.getNgaythang());
        mContext = this;
        setContentView(R.layout.activity_c_t_lich_phat_hanh);
        unbinder = ButterKnife.bind(this);

        GetCTLichPhatHanh();

        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                CTLichPhatHanh ctLichPhatHanh = (CTLichPhatHanh) lstCTLichPhatHanh.get(position);
                UpdateDaMua(ctLichPhatHanh, position);
            }

            @Override
            public void onLongClick(View view, int position) {
                CTLichPhatHanh ctLichPhatHanh = (CTLichPhatHanh) lstCTLichPhatHanh.get(position);
                Delete(ctLichPhatHanh, position);
            }
        }));
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetCTLichPhatHanh();
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
                ThemCTLichPhatHanh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void GetCTLichPhatHanh() {
        ApiCTLichPhatHanh.apiCtLichPhatHanh.GetCTLichPhatHanh(ComicPro.objLichPhatHanh.getMalich()).enqueue(new Callback < List < CTLichPhatHanh > >() {
            @Override
            public void onResponse(Call < List < CTLichPhatHanh > > call, Response < List < CTLichPhatHanh > > response) {
                List < CTLichPhatHanh > ctLichPhatHanhs = response.body();
                if (ctLichPhatHanhs != null) {
                    lstCTLichPhatHanh = new ArrayList < CTLichPhatHanh >();
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
            public void onFailure(Call < List < CTLichPhatHanh > > call, Throwable t) {

            }
        });
    }

    public void UpdateDaMua(CTLichPhatHanh ctLichPhatHanh, int position) {
        ApiCTLichPhatHanh.apiCtLichPhatHanh.DaMua(ctLichPhatHanh.getId()).enqueue(new Callback < List < CTLichPhatHanh > >() {
            @Override
            public void onResponse(Call < List < CTLichPhatHanh > > call, Response < List < CTLichPhatHanh > > response) {
                List < CTLichPhatHanh > ctLichPhatHanhs = response.body();
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
            public void onFailure(Call < List < CTLichPhatHanh > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }


    public void ThemCTLichPhatHanh() {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_ct_lichphathanh, null);

        txtTuaTruyen = view_bottom_sheet.findViewById(R.id.txtTuaTruyen);
        txtNgayXuatBan = view_bottom_sheet.findViewById(R.id.txtNgayXuatBan);
        txtGiaBia = view_bottom_sheet.findViewById(R.id.txtGiaBia);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        txtNgayXuatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                calendar.set(year, month, day);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = formatter.format(calendar.getTime());

                                txtNgayXuatBan.setText(strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
                                strNgayThang = formatter2.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });


        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtTuaTruyen.getText().toString().equals("")) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (strNgayThang.equals("")) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập ngày phát hành.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (txtGiaBia.getText().toString().equals("")) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập giá bìa.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }

                ApiCTLichPhatHanh.apiCtLichPhatHanh.Insert("SAVE", 0, ComicPro.objLichPhatHanh.getMalich(), strNgayThang, txtTuaTruyen.getText().toString(), Integer.parseInt(txtGiaBia.getText().toString()), "", ComicPro.tendangnhap, "").enqueue(new Callback < List < CTLichPhatHanh > >() {
                    @Override
                    public void onResponse(Call < List < CTLichPhatHanh > > call, Response < List < CTLichPhatHanh > > response) {
                        List < CTLichPhatHanh > ctLichPhatHanhs = response.body();
                        if (ctLichPhatHanhs != null) {
                            GetCTLichPhatHanh();
                            TM_Toast.makeText(mContext, "Đã thêm lịch phát hành tựa truyện (" + txtTuaTruyen.getText() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                            txtTuaTruyen.setText("");
                            txtGiaBia.setText("0");
                            txtTuaTruyen.setFocusable(true);
                        }
                    }

                    @Override
                    public void onFailure(Call < List < CTLichPhatHanh > > call, Throwable t) {
                        TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                    }
                });
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

    private void Delete(final CTLichPhatHanh ctLichPhatHanh, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa lịch phát hành (" + ctLichPhatHanh.getTuatruyen() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiCTLichPhatHanh.apiCtLichPhatHanh.Delete(ctLichPhatHanh.getId()).enqueue(new Callback < List < CTLichPhatHanh > >() {
                            @Override
                            public void onResponse(Call < List < CTLichPhatHanh > > call, Response < List < CTLichPhatHanh > > response) {
                                List < CTLichPhatHanh > ctLichPhatHanhs = response.body();
                                if (ctLichPhatHanhs.size() > 0) {
                                    lstCTLichPhatHanh.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa chi tiết lịch phát hành (" + ctLichPhatHanh.getTuatruyen() + ") thành công", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call < List < CTLichPhatHanh > > call, Throwable t) {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
        return true;
    }


}