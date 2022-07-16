package com.triminh.comicpro.view.tentruyen;

import static com.triminh.comicpro.view.tentruyen.ThemTenTruyenActivity.load;
import static com.triminh.comicpro.view.tentruyen.ThemTenTruyenActivity.strGiaBia;
import static com.triminh.comicpro.view.tentruyen.ThemTenTruyenActivity.strNgayXuatBan;
import static com.triminh.comicpro.view.tentruyen.ThemTenTruyenActivity.strTenTruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.comicpro.R;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.triminh.comicpro.adapter.tentruyen.Adapter_TenTruyen;
import com.triminh.comicpro.api.ApiNhapXuatTemp;
import com.triminh.comicpro.api.ApiTenTruyen;
import com.triminh.comicpro.model.phieuxuat.NhapXuatTemp;
import com.triminh.comicpro.model.tentruyen.TenTruyen;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.IOnIntentReceived;
import com.triminh.comicpro.system.ImagePickerActivity;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.system.TM_Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class viewTenTruyenActivity extends AppCompatActivity {

    ArrayList<TenTruyen> lstTenTruyen;
    Adapter_TenTruyen adapter;
    public static final int REQUEST_IMAGE = 100;
    Context mContext;
    String name = "tentruyen";
    Integer position_temp;

    static Boolean edit = false;
    private IOnIntentReceived mIntentListener;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ten_truyen);
        this.setTitle(ComicPro.objTuaTruyen.getTuatruyen());
        Unbinder unbinder = ButterKnife.bind(this);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetTenTruyen();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TenTruyen tenTruyen = lstTenTruyen.get(position);
                if (ComicPro.PhieuNhap == 0 && ComicPro.PhieuXuat == 0) {
                    edit = true;
                    ComicPro.objTenTruyen = lstTenTruyen.get(position);
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
//        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(() -> GetTenTruyen());
    }

    public void GetTenTruyen() {
        ApiTenTruyen.apiTenTruyen.GetTenTuyen(ComicPro.objTuaTruyen.getMatua(), 1).enqueue(new Callback<List<TenTruyen>>() {
            @Override
            public void onResponse(Call<List<TenTruyen>> call, Response<List<TenTruyen>> response) {
                List<TenTruyen> tenTruyens = response.body();
                if (tenTruyens.size() > 0) {
                    lstTenTruyen = new ArrayList<>();
                    lstTenTruyen.addAll(tenTruyens);
                    adapter = new Adapter_TenTruyen(mContext, lstTenTruyen);
                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
//                    mIntentListener = adapter;
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
                position_temp = position;
                edit = true;
                Intent sub = new Intent(mContext, ThemTenTruyenActivity.class);
                sub.putExtra("name", name);
                startActivityForResult(sub, 101);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 101) {
                if (load == false) {
                    return;
                }
                if (edit == true) {
                    lstTenTruyen.get(position_temp).setTentruyen(strTenTruyen);
                    lstTenTruyen.get(position_temp).setNgayxuatban_text("Ngày xuất bản: " + strNgayXuatBan);
                    //lstTenTruyen.get(position_temp).setGiabia("Giá bìa: " + strGiaBia);
                    adapter.notifyItemChanged(position_temp);
                } else {
                    GetTenTruyen();
                }

            }
        }
    }
}