package com.example.comicpro.view.danhmuc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.example.comicpro.adapter.danhmuc.Adapter_NhaXuatBan;
import com.example.comicpro.api.ApiNhaXuatBan;
import com.example.comicpro.model.danhmuc.NhaXuatBan;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.RecyclerTouchListener;
import com.example.comicpro.system.TM_Toast;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NhaXuatBanActivity extends AppCompatActivity {

    ArrayList < NhaXuatBan > lstNhaXuatBan;
    Adapter_NhaXuatBan adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    TextInputEditText txtNXB, txtDiaChi, txtSoDT;
    Button btnLuu, btnDong;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhaxuatban);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Nhà Xuất Bản");
        GetNhaXuatBan();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                ComicPro.objNXB = lstNhaXuatBan.get(position);
                Intent intent = new Intent(NhaXuatBanActivity.this, TuaTruyenNXBActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetNhaXuatBan();
            }
        });
    }

    public void GetNhaXuatBan() {
        ApiNhaXuatBan.apiNhaXuatBan.GetNhaXuatBan(1, "").enqueue(new Callback < List < NhaXuatBan > >() {
            @Override
            public void onResponse(Call < List < NhaXuatBan > > call, Response < List < NhaXuatBan > > response) {
                List < NhaXuatBan > nhaXuatBans = response.body();
                if (nhaXuatBans != null) {
                    lstNhaXuatBan = new ArrayList < NhaXuatBan >();
                    lstNhaXuatBan.addAll(nhaXuatBans);
                    adapter = new Adapter_NhaXuatBan(mContext, lstNhaXuatBan);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                    swiperefresh.setRefreshing(false);
                } else {
                    TM_Toast.makeText(mContext, "Không tải được dữ liệu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false);
                }
            }

            @Override
            public void onFailure(Call < List < NhaXuatBan > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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