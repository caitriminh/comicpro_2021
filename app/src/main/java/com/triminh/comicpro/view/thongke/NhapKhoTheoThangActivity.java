package com.triminh.comicpro.view.thongke;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;

import com.triminh.comicpro.adapter.phieunhap.Adapter_MoiNhat;
import com.triminh.comicpro.api.ApiMoiNhat;
import com.triminh.comicpro.model.phieunhap.MoiNhat;
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

public class NhapKhoTheoThangActivity extends AppCompatActivity {


    ArrayList <MoiNhat> lstMoiNhat;
    Adapter_MoiNhat adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moinhat);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        LoadData();

        this.setTitle("Tháng " + ComicPro.objThongKeTheoThang.getThang() + "/" + ComicPro.objThongKeTheoThang.getNam() + " (" + ComicPro.objThongKeTheoThang.getSocuon() + " cuốn)");

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
        ApiMoiNhat.apiMoiNhat.MoiNhat(2, ComicPro.objThongKeTheoThang.getThang(), ComicPro.objThongKeTheoThang.getNam()).enqueue(new Callback < List < MoiNhat > >() {
            @Override
            public void onResponse(Call < List < MoiNhat > > call, Response < List < MoiNhat > > response) {
                List < MoiNhat > moiNhats = response.body();
                if (moiNhats.size() > 0) {
                    lstMoiNhat = new ArrayList < MoiNhat >();
                    lstMoiNhat.addAll(moiNhats);
                    adapter = new Adapter_MoiNhat(mContext, lstMoiNhat);
                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call < List < MoiNhat > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }


}