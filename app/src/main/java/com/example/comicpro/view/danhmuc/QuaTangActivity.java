package com.example.comicpro.view.danhmuc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.example.comicpro.adapter.danhmuc.Adapter_QuaTang;
import com.example.comicpro.api.ApiQuaTang;
import com.example.comicpro.model.danhmuc.QuaTang;
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


public class QuaTangActivity extends AppCompatActivity {

    ArrayList < QuaTang > lstQuaTang;
    Adapter_QuaTang adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    private Unbinder unbinder;

    TextInputEditText txtQuaTang;
    Button btnLuu, btnDong;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quatang);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Quà Tặng");
        GetQuaTang();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                QuaTang quaTang = lstQuaTang.get(position);
                UpdateQuaTang(quaTang, position);
            }

            @Override
            public void onLongClick(View view, int position) {
                QuaTang quaTang = lstQuaTang.get(position);
                Delete(quaTang, position);
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetQuaTang();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetQuaTang() {
        ApiQuaTang.apiQuaTang.GetQuaTang().enqueue(new Callback < List < QuaTang > >() {
            @Override
            public void onResponse(Call < List < QuaTang > > call, Response < List < QuaTang > > response) {
                List < QuaTang > quaTangs = response.body();
                if (quaTangs.size() > 0) {
                    lstQuaTang = new ArrayList < QuaTang >();
                    lstQuaTang.addAll(quaTangs);
                    adapter = new Adapter_QuaTang(mContext, lstQuaTang);
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
            public void onFailure(Call < List < QuaTang > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final QuaTang quaTang, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa quà tặng (" + quaTang.getQuatang() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiQuaTang.apiQuaTang.Delete(quaTang.getMaquatang()).enqueue(new Callback < List < QuaTang > >() {
                            @Override
                            public void onResponse(Call < List < QuaTang > > call, Response < List < QuaTang > > response) {
                                List < QuaTang > status = response.body();
                                if (status.size() > 0) {
                                    if (status.get(0).getStatus().equals("OK")) {
                                        lstQuaTang.remove(position);
                                        adapter.notifyDataSetChanged();
                                        TM_Toast.makeText(mContext, "Đã xóa quà tặng (" + quaTang.getQuatang() + ") thành công.", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                                    } else {
                                        TM_Toast.makeText(mContext, "Xóa quà tặng (" + quaTang.getQuatang() + ") không thành công.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call < List < QuaTang > > call, Throwable t) {
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

    public void ThemQuaTang() {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_quatang, null);
        txtQuaTang = view_bottom_sheet.findViewById(R.id.txtQuaTang);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtQuaTang.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào tên quà tặng.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                } else {

                    ApiQuaTang.apiQuaTang.Insert("SAVE", "", txtQuaTang.getText().toString(), ComicPro.tendangnhap, "").enqueue(new Callback < List < QuaTang > >() {
                        @Override
                        public void onResponse(Call < List < QuaTang > > call, Response < List < QuaTang > > response) {
                            List < QuaTang > donViTinhs = response.body();
                            if (donViTinhs.size() > 0) {
                                lstQuaTang = new ArrayList < QuaTang >();
                                lstQuaTang.addAll(donViTinhs);
                                adapter = new Adapter_QuaTang(mContext, lstQuaTang);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                                recycleView.setLayoutManager(layoutManager);
                                recycleView.setAdapter(adapter);
                                TM_Toast.makeText(mContext, "Đã thêm quà tặng (" + txtQuaTang.getText() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                txtQuaTang.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call < List < QuaTang > > call, Throwable t) {
                            TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
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

    public void UpdateQuaTang(QuaTang quaTang, int position) {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_quatang, null);
        txtQuaTang = view_bottom_sheet.findViewById(R.id.txtQuaTang);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();
        txtQuaTang.setText(quaTang.getQuatang());
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtQuaTang.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào tên quà tặng.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                } else {

                    ApiQuaTang.apiQuaTang.Insert("UPDATE", quaTang.getMaquatang(), txtQuaTang.getText().toString(), "", ComicPro.tendangnhap).enqueue(new Callback < List < QuaTang > >() {
                        @Override
                        public void onResponse(Call < List < QuaTang > > call, Response < List < QuaTang > > response) {
                            List < QuaTang > quaTangs = response.body();
                            if (quaTangs.size() > 0) {
                                lstQuaTang.get(position).setQuatang(txtQuaTang.getText().toString());
                                adapter.notifyItemChanged(position);
                                TM_Toast.makeText(mContext, "Đã cập nhật quà tặng (" + txtQuaTang.getText() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                            }
                        }

                        @Override
                        public void onFailure(Call < List < QuaTang > > call, Throwable t) {
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
                break;
            case R.id.btnThem:
                ThemQuaTang();
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