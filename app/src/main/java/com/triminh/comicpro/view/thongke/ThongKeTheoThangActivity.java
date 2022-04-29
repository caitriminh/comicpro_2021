package com.triminh.comicpro.view.thongke;

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
import com.triminh.comicpro.adapter.thongke.Adapter_ThongKeTheoThang;
import com.triminh.comicpro.api.ApiThongKe;
import com.triminh.comicpro.model.thongke.ThongKeTheoThang;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.system.TM_Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ThongKeTheoThangActivity extends AppCompatActivity {

    ArrayList <ThongKeTheoThang> lstThongKe;
    Adapter_ThongKeTheoThang adapter;
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
        setContentView(R.layout.activity_thongke_theothang);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Thống Kê Theo Tháng");
        GetThongKe();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                ComicPro.objThongKeTheoThang = lstThongKe.get(position);
                Intent intent = new Intent(ThongKeTheoThangActivity.this, NhapKhoTheoThangActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetThongKe() {
        ApiThongKe.apiThongKe.GetThongKeTheoThang(2).enqueue(new Callback < List < ThongKeTheoThang > >() {
            @Override
            public void onResponse(Call < List < ThongKeTheoThang > > call, Response < List < ThongKeTheoThang > > response) {
                List < ThongKeTheoThang > thongKeTheoThangs = response.body();
                if (thongKeTheoThangs.size() > 0) {

                    lstThongKe = new ArrayList < ThongKeTheoThang >();
                    lstThongKe.addAll(thongKeTheoThangs);
                    adapter = new Adapter_ThongKeTheoThang(mContext, lstThongKe);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    //Tổng thành tiền
                    DecimalFormat format1=new DecimalFormat("#,##0");
                    Double dblThanhTien=lstThongKe.get(0).getThanhtien2();
                    txtTongThanhTien.setText(format1.format(dblThanhTien));

                    DecimalFormat format = new DecimalFormat("#,##0");
                    Integer intSoCuon = lstThongKe.get(0).getTongSocuon();
                    txtTongSoCuon.setText(format.format(intSoCuon));
                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call < List < ThongKeTheoThang > > call, Throwable t) {
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