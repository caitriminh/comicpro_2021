package com.triminh.comicpro.view.tusach;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.triminh.comicpro.adapter.tusach.Adapter_TuSachChiTiet;
import com.triminh.comicpro.api.ApiTuSach;
import com.triminh.comicpro.api.ApiTuSachChiTiet;
import com.triminh.comicpro.api.ApiTuaTruyen;
import com.triminh.comicpro.model.tuatruyen.TuaTruyen;
import com.triminh.comicpro.model.tusach.TuSach;
import com.triminh.comicpro.model.tusach.TuSachChiTiet;
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


public class TuSachChiTietActivity extends AppCompatActivity {

    ArrayList<TuSachChiTiet> lstTuSachChiTiet;
    ArrayList<TuSach> lstTuSach;
    ArrayList<TuaTruyen> lstTuaTruyen;
    Adapter_TuSachChiTiet adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    private Unbinder unbinder;
    private Integer id_tusach = 0;
    private String matua;

    TextInputEditText txtTuSach, txtTuaTruyen;
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
        GetTuSachChiTiet();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                TuSachChiTiet tuSachChiTiet = lstTuSachChiTiet.get(position);
                Delete(tuSachChiTiet, position);
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTuSachChiTiet();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetTuSachChiTiet() {
        ApiTuSachChiTiet.apiTuSachChiTiet.TuSachChiTiet("GET_DATA", 0, "").enqueue(new Callback<List<TuSachChiTiet>>() {
            @Override
            public void onResponse(Call<List<TuSachChiTiet>> call, Response<List<TuSachChiTiet>> response) {
                List<TuSachChiTiet> tuSachChiTiets = response.body();
                if (tuSachChiTiets.size() > 0) {
                    lstTuSachChiTiet = new ArrayList<TuSachChiTiet>();
                    lstTuSachChiTiet.addAll(tuSachChiTiets);
                    adapter = new Adapter_TuSachChiTiet(mContext, lstTuSachChiTiet);
                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    TM_Toast.makeText(mContext, "Không tải được dữ liệu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false);
                }
            }

            @Override
            public void onFailure(Call<List<TuSachChiTiet>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetTuSach(TextView txtTuSach) {
        ApiTuSach.apiTuSach.TuSach("GET_DATA", 0, "").enqueue(new Callback<List<TuSach>>() {
            @Override
            public void onResponse(Call<List<TuSach>> call, Response<List<TuSach>> response) {
                List<TuSach> tuSaches = response.body();
                if (tuSaches != null) {
                    lstTuSach = new ArrayList<TuSach>();
                    lstTuSach.addAll(tuSaches);
                    txtTuSach.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("Chọn tử sách");
                            builder.setCancelable(false);
                            String[] arrayTuSach = new String[lstTuSach.size()];
                            int i = 0;
                            for (TuSach tuSach : lstTuSach) {
                                arrayTuSach[i] = tuSach.getTusach();
                                i++;
                            }
                            ;
                            builder.setSingleChoiceItems(arrayTuSach, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    TuSach tuSach = lstTuSach.get(i);
                                    txtTuSach.setText(tuSach.getTusach());
                                    id_tusach = tuSach.getId();
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<TuSach>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetTuaTruyen(TextView txtTuaTruyen) {
        ApiTuaTruyen.apiTuaTruyen.GetTuaTruyen("GET_DATA_TUSACH").enqueue(new Callback<List<TuaTruyen>>() {
            @Override
            public void onResponse(Call<List<TuaTruyen>> call, Response<List<TuaTruyen>> response) {
                List<TuaTruyen> tuaTruyens = response.body();
                if (tuaTruyens != null) {
                    lstTuaTruyen = new ArrayList<TuaTruyen>();
                    lstTuaTruyen.addAll(tuaTruyens);
                    txtTuaTruyen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("Chọn tựa truyện");
                            builder.setCancelable(false);
                            String[] arrayTuaTruyen = new String[lstTuaTruyen.size()];
                            int i = 0;
                            for (TuaTruyen tuaTruyen : lstTuaTruyen) {
                                arrayTuaTruyen[i] = tuaTruyen.getTuatruyen();
                                i++;
                            }
                            ;
                            builder.setSingleChoiceItems(arrayTuaTruyen, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    TuaTruyen tuaTruyen = lstTuaTruyen.get(i);
                                    txtTuaTruyen.setText(tuaTruyen.getTuatruyen());
                                    matua = tuaTruyen.getMatua();
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<TuaTruyen>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final TuSachChiTiet tuSachChiTiet, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa tủ sách (" + tuSachChiTiet.getTusach() + ") có tựa truyện (" + tuSachChiTiet.getTuatruyen() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        ApiTuSachChiTiet.apiTuSachChiTiet.TuSachChiTiet("DELETE", tuSachChiTiet.getId_tusach(), tuSachChiTiet.getMatua()).enqueue(new Callback<List<TuSachChiTiet>>() {
                            @Override
                            public void onResponse(Call<List<TuSachChiTiet>> call, Response<List<TuSachChiTiet>> response) {
                                List<TuSachChiTiet> lstStatus = response.body();
                                if (lstStatus.size() > 0) {
                                    if (lstStatus.get(0).getStatus().equals("OK")) {
                                        lstTuSachChiTiet.remove(position);
                                        adapter.notifyDataSetChanged();
                                        TM_Toast.makeText(mContext, "Đã xóa", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                                    } else {
                                        TM_Toast.makeText(mContext, "Xóa không thành công.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<TuSachChiTiet>> call, Throwable t) {
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
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_tusachchitiet, null);
        txtTuSach = view_bottom_sheet.findViewById(R.id.txtTuSach);
        txtTuaTruyen = view_bottom_sheet.findViewById(R.id.txtTuaTruyen);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();
        GetTuSach(txtTuSach);
        GetTuaTruyen(txtTuaTruyen);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTuSach.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào tủ sách.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (txtTuaTruyen.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào tên truyện.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                ApiTuSachChiTiet.apiTuSachChiTiet.TuSachChiTiet("SAVE", id_tusach, matua).enqueue(new Callback<List<TuSachChiTiet>>() {
                    @Override
                    public void onResponse(Call<List<TuSachChiTiet>> call, Response<List<TuSachChiTiet>> response) {
                        List<TuSachChiTiet> tuSaches = response.body();
                        if (tuSaches.size() > 0) {
                            lstTuSachChiTiet = new ArrayList<TuSachChiTiet>();
                            lstTuSachChiTiet.addAll(tuSaches);
                            adapter = new Adapter_TuSachChiTiet(mContext, lstTuSachChiTiet);
                            recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                            recycleView.setAdapter(adapter);
                            swipeRefreshLayout.setRefreshing(false);

                            TM_Toast.makeText(mContext, "Đã thêm tủ sách chi tiết (" + txtTuSach.getText() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                            txtTuaTruyen.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TuSachChiTiet>> call, Throwable t) {
                        TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
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