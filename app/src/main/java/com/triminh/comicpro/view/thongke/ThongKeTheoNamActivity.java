package com.triminh.comicpro.view.thongke;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.triminh.comicpro.adapter.thongke.Adapter_ThongKeTheoNam;
import com.triminh.comicpro.api.ApiThongKe;
import com.triminh.comicpro.model.thongke.ThongKeTheoNam;
import com.triminh.comicpro.system.ClickListener;
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


public class ThongKeTheoNamActivity extends AppCompatActivity {

    ArrayList <ThongKeTheoNam> lstThongKe;
    Adapter_ThongKeTheoNam adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    private Unbinder unbinder;
    @BindView(R.id.txtTongThanhTien)
    TextView txtTongThanhTien;
    @BindView(R.id.txtTongSoCuon)
    TextView txtTongSoCuon;
    Double dblTongTien = 0.0;
    Integer intSoCuon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongke_theonam);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Thống Kê Theo Năm");
        GetThongKe();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetThongKe() {
        ApiThongKe.apiThongKe.GetThongKeTheoNam("GET_DATA").enqueue(new Callback < List < ThongKeTheoNam > >() {
            @Override
            public void onResponse(Call < List < ThongKeTheoNam > > call, Response < List < ThongKeTheoNam > > response) {
                List < ThongKeTheoNam > thongKeTheoNams = response.body();
                if (thongKeTheoNams != null) {
                    lstThongKe = new ArrayList < ThongKeTheoNam >();
                    lstThongKe.addAll(thongKeTheoNams);
                    adapter = new Adapter_ThongKeTheoNam(mContext, lstThongKe);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);

                    //Tổng thành tiền
                    DecimalFormat formatTongTien = new DecimalFormat("#,##0");
                    double dblThanhTien = lstThongKe.get(0).getThanhtien2();
                    txtTongThanhTien.setText(formatTongTien.format(dblThanhTien));

                    DecimalFormat formatTongSo = new DecimalFormat("#,##0");
                    Integer intTongSoCuon = lstThongKe.get(0).getTongSocuon();
                    txtTongSoCuon.setText(formatTongSo.format(intTongSoCuon));
                }
            }

            @Override
            public void onFailure(Call < List < ThongKeTheoNam > > call, Throwable t) {
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