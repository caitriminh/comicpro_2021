package com.example.comicpro.view.thongke;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;

import com.example.comicpro.adapter.thongke.Adapter_TenTruyenNXB;
import com.example.comicpro.api.ApiThongKe;
import com.example.comicpro.model.tentruyen.ChiTietTenTruyen_TheoNXB;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.RecyclerTouchListener;
import com.example.comicpro.system.TM_Toast;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TenTruyenTheoNXBActivity extends AppCompatActivity {

    ArrayList < ChiTietTenTruyen_TheoNXB > lsChiTietTenTruyen;
    Adapter_TenTruyenNXB adapter;
    private Unbinder unbinder;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    TextView txtTongThanhTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(ComicPro.objThongKeTheoNXB.getNhaxuatban());
        mContext = this;
        setContentView(R.layout.activity_tentruyen_theonxb);
        unbinder = ButterKnife.bind(this);
        txtTongThanhTien = findViewById(R.id.txtTongThanhTien);
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
        ApiThongKe.apiThongKe.GetTenTruyenNXB(ComicPro.objThongKeTheoNXB.getManxb()).enqueue(new Callback < List < ChiTietTenTruyen_TheoNXB > >() {
            @Override
            public void onResponse(Call < List < ChiTietTenTruyen_TheoNXB > > call, Response < List < ChiTietTenTruyen_TheoNXB > > response) {
                List < ChiTietTenTruyen_TheoNXB > chiTietTenTruyen_theoNXBS = response.body();
                lsChiTietTenTruyen = new ArrayList < ChiTietTenTruyen_TheoNXB >();
                lsChiTietTenTruyen.addAll(chiTietTenTruyen_theoNXBS);
                adapter = new Adapter_TenTruyenNXB(mContext, lsChiTietTenTruyen);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                recycleView.setLayoutManager(layoutManager);
                recycleView.setAdapter(adapter);

                DecimalFormat format = new DecimalFormat("#,##0");
                Double dblTongTien = lsChiTietTenTruyen.get(0).getTongTien();
                txtTongThanhTien.setText(format.format(dblTongTien));
            }

            @Override
            public void onFailure(Call < List < ChiTietTenTruyen_TheoNXB > > call, Throwable t) {
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