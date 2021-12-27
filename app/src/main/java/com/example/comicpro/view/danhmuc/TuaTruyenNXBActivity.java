package com.example.comicpro.view.danhmuc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.example.comicpro.adapter.tuatruyen.Adapter_TuaTruyen;
import com.example.comicpro.api.ApiNhaXuatBan;
import com.example.comicpro.model.tuatruyen.TuaTruyen;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.RecyclerTouchListener;
import com.example.comicpro.system.TM_Toast;
import com.example.comicpro.view.tentruyen.TenTruyenActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuaTruyenNXBActivity extends AppCompatActivity {


    ArrayList < TuaTruyen > lstTuaTruyen;
    Adapter_TuaTruyen adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_tuatruyen);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetTuaTruyen();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                ComicPro.objTuaTruyen = lstTuaTruyen.get(position);
                Intent intent = new Intent(mContext, TenTruyenActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        this.setTitle(ComicPro.objNXB.getNhaxuatban());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTuaTruyen();
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

    public void GetTuaTruyen() {
        ApiNhaXuatBan.apiNhaXuatBan.GetTuaTruyen(ComicPro.objNXB.getManxb()).enqueue(new Callback < List < TuaTruyen > >() {
            @Override
            public void onResponse(Call < List < TuaTruyen > > call, Response < List < TuaTruyen > > response) {
                List < TuaTruyen > tuaTruyens = response.body();
                if (tuaTruyens != null) {
                    lstTuaTruyen = new ArrayList < TuaTruyen >();
                    lstTuaTruyen.addAll(tuaTruyens);
                    adapter = new Adapter_TuaTruyen(mContext, lstTuaTruyen);

                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call < List < TuaTruyen > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }


}