package com.triminh.comicpro.view.tonkho;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.triminh.comicpro.adapter.tonkho.Adapter_ChiTietTonKho;
import com.triminh.comicpro.api.ApiChiTietTonKho;
import com.triminh.comicpro.model.tonkho.ChiTietTonKho;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
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

public class ChiTietTonKhoActivity extends AppCompatActivity {


    ArrayList <ChiTietTonKho> lstCtTonKho;
    Adapter_ChiTietTonKho adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_tonkho);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        GetChiTietTonKho();
        this.setTitle(ComicPro.objTonKho.getTuatruyen());
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                ChiTietTonKho chiTietTonKho = lstCtTonKho.get(position);
                String url = chiTietTonKho.getHinh();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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

    public void GetChiTietTonKho() {
        ApiChiTietTonKho.apiChiTietTonKho.GetTonKho(ComicPro.objTonKho.getMatua()).enqueue(new Callback < List < ChiTietTonKho > >() {
            @Override
            public void onResponse(Call < List < ChiTietTonKho > > call, Response < List < ChiTietTonKho > > response) {
                List < ChiTietTonKho > chiTietTonKhos = response.body();
                if (chiTietTonKhos.size() > 0) {
                    lstCtTonKho = new ArrayList < ChiTietTonKho >();
                    lstCtTonKho.addAll(chiTietTonKhos);
                    adapter = new Adapter_ChiTietTonKho(mContext, lstCtTonKho);

                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call < List < ChiTietTonKho > > call, Throwable t) {
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