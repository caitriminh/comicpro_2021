package com.triminh.comicpro.view.moinhat;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.triminh.comicpro.adapter.phieunhap.Adapter_MoiNhat;
import com.triminh.comicpro.api.ApiMoiNhat;
import com.triminh.comicpro.model.phieunhap.MoiNhat;
import com.triminh.comicpro.system.ClickListener;
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


public class MoiNhatActivity extends AppCompatActivity {

    ArrayList <MoiNhat> lstMoiNhat;

    Adapter_MoiNhat adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    private Unbinder unbinder;


    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moinhat);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Mới Nhất");
        GetMoiNhat();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetMoiNhat();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetMoiNhat() {
        ApiMoiNhat.apiMoiNhat.MoiNhat(1, 0, 0).enqueue(new Callback < List < MoiNhat > >() {
            @Override
            public void onResponse(Call < List < MoiNhat > > call, Response < List < MoiNhat > > response) {
                List < MoiNhat > moiNhats = response.body();
                if (moiNhats.size() > 0) {
                    lstMoiNhat = new ArrayList < MoiNhat >();
                    lstMoiNhat.addAll(moiNhats);
                    adapter = new Adapter_MoiNhat(mContext, lstMoiNhat);
                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call < List < MoiNhat > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
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


}