package com.triminh.comicpro.view.tusach;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.triminh.comicpro.adapter.tusach.Adapter_TuSach;
import com.triminh.comicpro.api.ApiTuSach;
import com.triminh.comicpro.model.tusach.TuSach;
import com.triminh.comicpro.system.ClickListener;
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


public class TuSachActivity extends AppCompatActivity {

    ArrayList<TuSach> lstTuSach;

    Adapter_TuSach adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    private Unbinder unbinder;

    TextInputEditText txtTuSach;
    Button btnLuu, btnDong;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tusach);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Tủ Sách");
        GetTuSach();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                TuSach tuSach = lstTuSach.get(position);
                UpdateTuSach(tuSach, position);
            }

            @Override
            public void onLongClick(View view, int position) {
                TuSach tuSach = lstTuSach.get(position);
                Delete(tuSach, position);
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTuSach();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetTuSach() {
        ApiTuSach.apiTuSach.TuSach("GET_DATA", 0, "").enqueue(new Callback<List<TuSach>>() {
            @Override
            public void onResponse(Call<List<TuSach>> call, Response<List<TuSach>> response) {
                List<TuSach> tuSaches = response.body();
                if (tuSaches.size() > 0) {
                    lstTuSach = new ArrayList<TuSach>();
                    lstTuSach.addAll(tuSaches);
                    adapter = new Adapter_TuSach(mContext, lstTuSach);
                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    TM_Toast.makeText(mContext, "Không tải được dữ liệu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false);
                }
            }

            @Override
            public void onFailure(Call<List<TuSach>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final TuSach tuSach, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa tủ sách (" + tuSach.getTusach() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        ApiTuSach.apiTuSach.TuSach("DELETE", tuSach.getId(), "").enqueue(new Callback<List<TuSach>>() {
                            @Override
                            public void onResponse(Call<List<TuSach>> call, Response<List<TuSach>> response) {
                                List<TuSach> lstStatus = response.body();
                                if (lstStatus.size() > 0) {
                                    if (lstStatus.get(0).getStatus().equals("OK")) {
                                        lstTuSach.remove(position);
                                        adapter.notifyDataSetChanged();
                                        TM_Toast.makeText(mContext, "Đã xóa", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                                    } else {
                                        TM_Toast.makeText(mContext, "Xóa không thành công.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<TuSach>> call, Throwable t) {
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


    public void ThemTuSach() {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_tusach, null);
        txtTuSach = view_bottom_sheet.findViewById(R.id.txtTuSach);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTuSach.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào tủ sách.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                } else {

                    ApiTuSach.apiTuSach.TuSach("SAVE", 0, txtTuSach.getText().toString()).enqueue(new Callback<List<TuSach>>() {
                        @Override
                        public void onResponse(Call<List<TuSach>> call, Response<List<TuSach>> response) {
                            List<TuSach> tuSaches = response.body();
                            if (tuSaches.size() > 0) {
                                lstTuSach = new ArrayList<TuSach>();
                                lstTuSach.addAll(tuSaches);
                                adapter = new Adapter_TuSach(mContext, lstTuSach);
                                recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                                recycleView.setAdapter(adapter);
                                swipeRefreshLayout.setRefreshing(false);

                                TM_Toast.makeText(mContext, "Đã thêm tủ sách (" + txtTuSach.getText() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                txtTuSach.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<TuSach>> call, Throwable t) {
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

    public void UpdateTuSach(TuSach tuSach, int position) {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_tusach, null);
        txtTuSach = view_bottom_sheet.findViewById(R.id.txtTuSach);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();
        txtTuSach.setText(tuSach.getTusach());
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTuSach.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào tủ sách.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                } else {

                    ApiTuSach.apiTuSach.TuSach("UPDATE", tuSach.getId(), txtTuSach.getText().toString()).enqueue(new Callback<List<TuSach>>() {
                        @Override
                        public void onResponse(Call<List<TuSach>> call, Response<List<TuSach>> response) {
                            List<TuSach> tuSaches = response.body();
                            if (tuSaches.size() > 0) {
                                lstTuSach.get(position).setTusach(txtTuSach.getText().toString());
                                adapter.notifyItemChanged(position);
                                TM_Toast.makeText(mContext, "Đã cập nhật tủ sách (" + txtTuSach.getText() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                txtTuSach.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<TuSach>> call, Throwable t) {
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
                ThemTuSach();
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