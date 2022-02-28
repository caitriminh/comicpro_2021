package com.example.comicpro.view.tentruyen;

import static com.example.comicpro.view.tentruyen.ThemTenTruyenActivity.load;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.comicpro.R;

import com.example.comicpro.adapter.tentruyen.Adapter_TenTruyen;
import com.example.comicpro.api.ApiDonViTinh;
import com.example.comicpro.api.ApiLoaiBia;
import com.example.comicpro.api.ApiNhapXuatTemp;
import com.example.comicpro.api.ApiQuaTang;
import com.example.comicpro.api.ApiTenTruyen;
import com.example.comicpro.model.danhmuc.DonViTinh;
import com.example.comicpro.model.danhmuc.LoaiBia;
import com.example.comicpro.model.danhmuc.QuaTang;
import com.example.comicpro.model.phieuxuat.NhapXuatTemp;
import com.example.comicpro.model.tentruyen.TenTruyen;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.IOnIntentReceived;
import com.example.comicpro.system.ImagePickerActivity;
import com.example.comicpro.system.RecyclerTouchListener;
import com.example.comicpro.system.TM_Toast;
import com.example.comicpro.view.tuatruyen.ThemTuaTruyenActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TenTruyenActivity extends AppCompatActivity {

    ArrayList<TenTruyen> lstTenTruyen;
    Adapter_TenTruyen adapter;

    public static final int REQUEST_IMAGE = 100;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    Context mContext;
    String name = "tentruyen";
    Integer position_temp;

    static Boolean edit = false;

    private IOnIntentReceived mIntentListener;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(ComicPro.objTuaTruyen.getTuatruyen());
        mContext = this;
        setContentView(R.layout.activity_ten_truyen);
        unbinder = ButterKnife.bind(this);
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetTenTruyen();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TenTruyen tenTruyen = lstTenTruyen.get(position);
                if (ComicPro.PhieuNhap == 0 && ComicPro.PhieuXuat == 0) {
                    edit = true;
                    ComicPro.objTenTruyen = lstTenTruyen.get(position);
//                    SuaTenTruyen(tenTruyen, position);
                } else if (ComicPro.PhieuNhap == 1) {
                    InsertPhieuNhapXuat(tenTruyen, 0);
                } else if (ComicPro.PhieuXuat == 1) {
                    InsertPhieuNhapXuat(tenTruyen, 1);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if (ComicPro.PhieuNhap == 0 && ComicPro.PhieuXuat == 0) {
                    position_temp = position;
                    ComicPro.objTenTruyen = lstTenTruyen.get(position);
                    GetChucNang(ComicPro.objTenTruyen, position);
                }
            }
        }));
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(() -> GetTenTruyen());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (mIntentListener != null) {
                mIntentListener.onIntent(data, resultCode);
            }
        } else if (requestCode == 101 && load == true) {
            if (edit = false) {
                GetTenTruyen();
            } else {
                lstTenTruyen.get(position_temp).setTentruyen(ComicPro.objTenTruyen.getTentruyen());

                lstTenTruyen.get(position_temp).setNgayxuatban_text(ComicPro.objTenTruyen.getNgayxuatban_text());
                lstTenTruyen.get(position_temp).setGiabia(ComicPro.objTenTruyen.getGiabia());
                lstTenTruyen.get(position_temp).setQuatang(ComicPro.objTenTruyen.getQuatang());
                lstTenTruyen.get(position_temp).setSotrang(ComicPro.objTenTruyen.getSotrang());
                lstTenTruyen.get(position_temp).setTap(ComicPro.objTenTruyen.getTap());
                lstTenTruyen.get(position_temp).setLoaibia(ComicPro.objTenTruyen.getLoaibia());
                adapter.notifyItemChanged(position_temp);
            }
        }
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(mContext, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnThem:
                edit = false;
                Intent sub = new Intent(mContext, ThemTenTruyenActivity.class);
                sub.putExtra("name", name);
                startActivityForResult(sub, 101);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void GetTenTruyen() {
        ApiTenTruyen.apiTenTruyen.GetTenTuyen(ComicPro.objTuaTruyen.getMatua(), 1).enqueue(new Callback<List<TenTruyen>>() {
            @Override
            public void onResponse(Call<List<TenTruyen>> call, Response<List<TenTruyen>> response) {
                List<TenTruyen> tenTruyens = response.body();
                if (tenTruyens != null) {
                    lstTenTruyen = new ArrayList<>();
                    lstTenTruyen.addAll(tenTruyens);
                    adapter = new Adapter_TenTruyen(mContext, lstTenTruyen);

                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                    mIntentListener = adapter;
                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<TenTruyen>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final TenTruyen tenTruyen, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa tên truyện (" + tenTruyen.getTentruyen() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiTenTruyen.apiTenTruyen.Delete(tenTruyen.getMatruyen()).enqueue(new Callback<List<TenTruyen>>() {
                            @Override
                            public void onResponse(Call<List<TenTruyen>> call, Response<List<TenTruyen>> response) {
                                List<TenTruyen> tenTruyens = response.body();
                                if (tenTruyens.size() > 0) {
                                    lstTenTruyen.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa tên truyện (" + tenTruyen.getTentruyen() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<TenTruyen>> call, Throwable t) {

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

    private void InsertPhieuNhapXuat(TenTruyen tenTruyen, Integer loai) {
        ApiNhapXuatTemp.apiNhapXuatTemp.NhapXuatTemp("SAVE", 0, tenTruyen.getMatruyen(), 1, 0.0, ComicPro.tendangnhap, loai, "", "").enqueue(new Callback<List<NhapXuatTemp>>() {
            @Override
            public void onResponse(Call<List<NhapXuatTemp>> call, Response<List<NhapXuatTemp>> response) {
                String s = tenTruyen.getMatruyen();
                List<NhapXuatTemp> nhapXuatTemps = response.body();
                if (nhapXuatTemps != null) {
                    String strMessenger = "";
                    if (loai == 0) {
                        strMessenger = "Thêm phiếu nhập tên truyện (" + tenTruyen.getTentruyen() + ") thành công";
                    } else {
                        strMessenger = "Thêm phiếu xuất tên truyện (" + tenTruyen.getTentruyen() + ") thành công";
                    }
                    TM_Toast.makeText(mContext, strMessenger, TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                }
            }

            @Override
            public void onFailure(Call<List<NhapXuatTemp>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
            }
        });
    }

    public void GetChucNang(TenTruyen tenTruyen, int position) {
        final String[] chucnangs = {"Upload ảnh", "Sửa", "Xóa", "Thoát"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Chức năng");
        builder.setCancelable(false);
        int i = 0;

        builder.setItems(chucnangs, (dialogInterface, i1) -> {
            String chucnang = chucnangs[i1];
            dialogInterface.dismiss(); // Close Dialog
            if (chucnang.equals("Xóa")) {
                Delete(tenTruyen, position);
            } else if (chucnang.equals("Upload ảnh")) {
                launchGalleryIntent();
            } else if (chucnang.equals("Sửa")) {
                edit = true;
                Intent sub = new Intent(mContext, ThemTenTruyenActivity.class);
                sub.putExtra("name", name);
                startActivityForResult(sub, 101);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_item, menu);
        getMenuInflater().inflate(R.menu.menu_item_add, menu);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setQueryHint("Tìm kiếm...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter == null) {
                    Log.w("adapter null", "null");
                }
                adapter.filter(newText);
                return false;
            }
        });
        return true;
    }
}