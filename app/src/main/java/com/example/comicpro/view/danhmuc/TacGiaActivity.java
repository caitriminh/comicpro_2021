package com.example.comicpro.view.danhmuc;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.example.comicpro.adapter.danhmuc.Adapter_TacGia;
import com.example.comicpro.api.ApiTacGia;
import com.example.comicpro.model.danhmuc.TacGia;
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


public class TacGiaActivity extends AppCompatActivity {

    ArrayList < TacGia > lstTacGia;
    Adapter_TacGia adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    TextInputEditText txtTacGia;
    Button btnLuu, btnDong;
    private Unbinder unbinder;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tacgia);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Tác Giả");
        GetTacGia();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                ComicPro.objTacgia = lstTacGia.get(position);
                Intent intent = new Intent(TacGiaActivity.this, TuaTruyenTacGiaActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                TacGia tacGia = lstTacGia.get(position);
                Delete(tacGia, position);
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTacGia();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetTacGia() {
        ApiTacGia.apiTacGia.GetTacGia().enqueue(new Callback < List < TacGia > >() {
            @Override
            public void onResponse(Call < List < TacGia > > call, Response < List < TacGia > > response) {
                List < TacGia > tacGias = response.body();
                if (tacGias.size() > 0) {
                    lstTacGia = new ArrayList < TacGia >();
                    lstTacGia.addAll(tacGias);
                    adapter = new Adapter_TacGia(mContext, lstTacGia);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                } else {
                    TM_Toast.makeText(mContext, "Không tải được dữ liệu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false);
                }
            }

            @Override
            public void onFailure(Call < List < TacGia > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final TacGia tacGia, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa tác hỉa (" + tacGia.getTacgia() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        ApiTacGia.apiTacGia.Delete(tacGia.getMatacgia()).enqueue(new Callback < List < TacGia > >() {
                            @Override
                            public void onResponse(Call < List < TacGia > > call, Response < List < TacGia > > response) {
                                List < TacGia > status = response.body();
                                if (status.get(0).getStatus().equals("OK")) {
                                    lstTacGia.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa tác giả (" + tacGia.getTacgia() + ")", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                                } else {
                                    TM_Toast.makeText(mContext, "Tác giả (" + tacGia.getTacgia() + ") không thể xóa", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call < List < TacGia > > call, Throwable t) {
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

    public void ThemTacGia() {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_tacgia, null);

        txtTacGia = view_bottom_sheet.findViewById(R.id.txtTacGia);
        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);
        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTacGia.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào tên tác giả.", Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                } else {
                    ApiTacGia.apiTacGia.Insert(txtTacGia.getText().toString(), ComicPro.tendangnhap).enqueue(new Callback < List < TacGia > >() {
                        @Override
                        public void onResponse(Call < List < TacGia > > call, Response < List < TacGia > > response) {
                            List < TacGia > tacGias = response.body();
                            if (tacGias != null) {
                                GetTacGia();
                                TM_Toast.makeText(mContext, "Đã thêm tác giả (" + txtTacGia.getText().toString() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                txtTacGia.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call < List < TacGia > > call, Throwable t) {
                            TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
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
                ThemTacGia();
                break;
        }
        return super.onOptionsItemSelected(item);
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