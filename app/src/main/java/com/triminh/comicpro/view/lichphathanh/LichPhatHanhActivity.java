package com.triminh.comicpro.view.lichphathanh;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.triminh.comicpro.adapter.lichphathanh.Adapter_LichPhatHanh;
import com.triminh.comicpro.api.ApiLichPhatHanh;
import com.triminh.comicpro.model.lichphathanh.LichPhatHanh;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.IOnIntentReceived;
import com.triminh.comicpro.system.ImagePickerActivity;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.system.TM_Toast;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LichPhatHanhActivity extends AppCompatActivity {

    ArrayList<LichPhatHanh> lstLichPhatHanh;
    Adapter_LichPhatHanh adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    private Unbinder unbinder;
    private IOnIntentReceived mIntentListener;

    public static final int REQUEST_IMAGE = 100;

    String name = "lichphathanh", strAction = "NAMNAY";;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    int intNam = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichphathanh);
        mContext = this;
        unbinder = ButterKnife.bind(this);

        Calendar calendar = Calendar.getInstance();
        intNam = calendar.get(Calendar.YEAR);

        setTitle("Lịch Phát Hành " + intNam);
        GetLichPhatHanh(strAction);
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                ComicPro.objLichPhatHanh = lstLichPhatHanh.get(position);
                Intent intent = new Intent(mContext, CTLichPhatHanhActivity.class);
                mContext.startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                ComicPro.objLichPhatHanh = lstLichPhatHanh.get(position);
                GetChucNang(ComicPro.objLichPhatHanh, position);
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(() -> GetLichPhatHanh(strAction));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (mIntentListener != null) {
                mIntentListener.onIntent(data, resultCode);
            }

        } else if (requestCode == 101) {
            GetLichPhatHanh(strAction);
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

    public void Delete(LichPhatHanh lichPhatHanh, int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa lịch phát hành nhà xuất bản (" + lichPhatHanh.getNhaxuatban() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiLichPhatHanh.apiLichPhatHanh.Delete(lichPhatHanh.getMalich()).enqueue(new Callback<List<LichPhatHanh>>() {
                            @Override
                            public void onResponse(Call<List<LichPhatHanh>> call, Response<List<LichPhatHanh>> response) {
                                List<LichPhatHanh> lichPhatHanhs = response.body();
                                if (lichPhatHanhs != null) {
                                    lstLichPhatHanh.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa lịch phát hành của nhà xuất bản (" + lichPhatHanh.getNhaxuatban() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<LichPhatHanh>> call, Throwable t) {
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

    public void GetLichPhatHanh(String strAction) {
        ApiLichPhatHanh.apiLichPhatHanh.GetLichPhatHanh(strAction, intNam).enqueue(new Callback<List<LichPhatHanh>>() {
            @Override
            public void onResponse(Call<List<LichPhatHanh>> call, Response<List<LichPhatHanh>> response) {
                List<LichPhatHanh> lichPhatHanhs = response.body();
                if (lichPhatHanhs.size() > 0) {
                    lstLichPhatHanh = new ArrayList<LichPhatHanh>();
                    lstLichPhatHanh.addAll(lichPhatHanhs);
                    adapter = new Adapter_LichPhatHanh(mContext, lstLichPhatHanh);
                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                    mIntentListener = adapter;
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<LichPhatHanh>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetNam() {
        ApiLichPhatHanh.apiLichPhatHanh.GetNam("GET_DATA").enqueue(new Callback<List<LichPhatHanh>>() {
            @Override
            public void onResponse(Call<List<LichPhatHanh>> call, Response<List<LichPhatHanh>> response) {
                List<LichPhatHanh> lichPhatHanhs = response.body();
                if (lichPhatHanhs.size() > 0) {
                    lstLichPhatHanh = new ArrayList<LichPhatHanh>();
                    lstLichPhatHanh.addAll(lichPhatHanhs);

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Chọn năm");
                    builder.setCancelable(false);
                    String[] arrayKhachHang = new String[lichPhatHanhs.size()];
                    int i = 0;

                    for (LichPhatHanh lichPhatHanh : lstLichPhatHanh) {
                        arrayKhachHang[i] = String.valueOf(lichPhatHanh.getNam());
                        i++;
                    }
                    ;
                    builder.setSingleChoiceItems(arrayKhachHang, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (lichPhatHanhs.size() > 0) {
                                LichPhatHanh lichPhatHanh = lichPhatHanhs.get(i);
                                intNam = lichPhatHanh.getNam();
                                GetLichPhatHanh("NAMNAY");
                                dialogInterface.dismiss();
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<List<LichPhatHanh>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetChucNang(LichPhatHanh lichPhatHanh, int position) {
        final String[] chucnangs = {"Upload ảnh", "Sửa", "Xóa", "Thoát"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Chức năng");
        builder.setCancelable(false);
        int i = 0;

        builder.setItems(chucnangs, (dialogInterface, i1) -> {
            String chucnang = chucnangs[i1];
            dialogInterface.dismiss(); // Close Dialog
            if (chucnang.equals("Xóa")) {
                Delete(lichPhatHanh, position);
            } else if (chucnang.equals("Upload ảnh")) {
                launchGalleryIntent();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnThem:
                Intent sub = new Intent(mContext, ThemLichPhatHanhActivity.class);
                sub.putExtra("name", name);
                startActivityForResult(sub, 101);
                break;
            case R.id.btnLoc:
                GetNam();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search_item, menu);
        getMenuInflater().inflate(R.menu.menu_item_filter, menu);
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
//        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
//        final SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
//        searchView.setQueryHint("Tìm kiếm...");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (adapter == null) {
//                    Log.w("adapter null", "null");
//                }
//                adapter.filter(newText);
//                return false;
//            }
//        });
        return true;
    }

}