package com.example.comicpro.view.thongke;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.example.comicpro.adapter.thongke.Adapter_TenTruyenNCC;
import com.example.comicpro.api.ApiThongKe;
import com.example.comicpro.model.thongke.ThongKeTheoNCC;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.RecyclerTouchListener;
import com.example.comicpro.system.TM_Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TenTruyenTheoNCCActivity extends AppCompatActivity {

    ArrayList < ThongKeTheoNCC > lsCTenTruyenNCC;
    Adapter_TenTruyenNCC adapter;
    private Unbinder unbinder;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(ComicPro.objThongKeTheoNCC.getDonvi());
        mContext = this;
        setContentView(R.layout.activity_chi_tiet_phieu_nhap);
        unbinder = ButterKnife.bind(this);
        LoadData();

        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadData() {
        ApiThongKe.apiThongKe.GetThongKeNCC("TENTRUYEN_NCC", ComicPro.objThongKeTheoNCC.getMadonvi()).enqueue(new Callback < List < ThongKeTheoNCC > >() {
            @Override
            public void onResponse(Call < List < ThongKeTheoNCC > > call, Response < List < ThongKeTheoNCC > > response) {
                List < ThongKeTheoNCC > thongKeTheoNCCS = response.body();
                if (thongKeTheoNCCS.size() > 0) {
                    lsCTenTruyenNCC = new ArrayList < ThongKeTheoNCC >();
                    lsCTenTruyenNCC.addAll(thongKeTheoNCCS);
                    adapter = new Adapter_TenTruyenNCC(mContext, lsCTenTruyenNCC);

                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                    //Làm mới dữ liệu
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call < List < ThongKeTheoNCC > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_item, menu);
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