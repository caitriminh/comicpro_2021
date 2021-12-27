package com.example.comicpro.view.thongke;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.example.comicpro.adapter.thongke.Adapter_ThongKeTheoNCC;
import com.example.comicpro.api.ApiThongKe;
import com.example.comicpro.model.thongke.ThongKeTheoNCC;
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


public class ThongKeTheoNCCActivity extends AppCompatActivity {

    ArrayList < ThongKeTheoNCC > lstThongKe;
    Adapter_ThongKeTheoNCC adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    private Unbinder unbinder;
    @BindView(R.id.txtTongThanhTien)
    TextView txtTongThanhTien;
    @BindView(R.id.txtTongSoCuon)
    TextView txtTongSoCuon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke_theoncc);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Thống Kê Theo Nhà Cung Cấp");
        GetThongKe();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                ComicPro.objThongKeTheoNCC = lstThongKe.get(position);
                Intent intent = new Intent(ThongKeTheoNCCActivity.this, TenTruyenTheoNCCActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetThongKe() {
        ApiThongKe.apiThongKe.GetThongKeNCC("GET_DATA","").enqueue(new Callback < List < ThongKeTheoNCC > >() {
            @Override
            public void onResponse(Call < List < ThongKeTheoNCC > > call, Response < List < ThongKeTheoNCC > > response) {
                List < ThongKeTheoNCC > thongKeTheoNCCS = response.body();
                if (thongKeTheoNCCS.size() > 0) {

                    lstThongKe = new ArrayList < ThongKeTheoNCC >();
                    lstThongKe.addAll(thongKeTheoNCCS);
                    adapter = new Adapter_ThongKeTheoNCC(mContext, lstThongKe);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    //Tổng thành tiền

                    DecimalFormat format = new DecimalFormat("#,##0");
                    Integer intSLNhap = lstThongKe.get(0).getTongso();

                    DecimalFormat format2 = new DecimalFormat("#,##0");
                    Double dblThanhTien = lstThongKe.get(0).getTongthanhtien();

                    txtTongSoCuon.setText(format.format(intSLNhap));
                    txtTongThanhTien.setText(format2.format(dblThanhTien));
                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call < List < ThongKeTheoNCC > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
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

}