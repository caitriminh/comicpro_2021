package com.example.comicpro.view.bieudo;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;


import com.example.comicpro.R;
import com.example.comicpro.api.ApiBieuDo;
import com.example.comicpro.model.thongke.ThongKeTheoThang;
import com.example.comicpro.system.TM_Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheoThangActivity extends AppCompatActivity {

    ArrayList < ThongKeTheoThang > lstThongKe;

    ArrayList SoTien = new ArrayList();
    ArrayList ThangNam = new ArrayList();
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theo_thang);
        mContext = this;
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Biểu Đồ Thống Kê Theo Tháng");
        GetBieuDo();
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

    public void GetBieuDo() {

        ApiBieuDo.apiBieuDo.GetBieuDoTheoThang(3).enqueue(new Callback < List < ThongKeTheoThang > >() {
            @Override
            public void onResponse(Call < List < ThongKeTheoThang > > call, Response < List < ThongKeTheoThang > > response) {
                List < ThongKeTheoThang > thongKeTheoThangs = response.body();
                if (thongKeTheoThangs.size() > 0) {
                    lstThongKe = new ArrayList < ThongKeTheoThang >();
                    lstThongKe.addAll(thongKeTheoThangs);

                    if (lstThongKe == null)
                        return;
                    int i = 0;
                    for (ThongKeTheoThang thongKeTheoThang : lstThongKe) {
                        SoTien.add(new BarEntry(Float.parseFloat(thongKeTheoThang.getThanhtien().toString()), i));
                        ThangNam.add(thongKeTheoThang.getThang() + "/" + thongKeTheoThang.getNam());
                        i++;
                    }
                    BarChart chart = findViewById(R.id.barchart);
                    BarDataSet bardataset = new BarDataSet(SoTien, "THÔNG KÊ THEO THÁNG");
                    chart.animateY(3000);
                    BarData data = new BarData(ThangNam, bardataset);
                    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    chart.setData(data);
                }
            }

            @Override
            public void onFailure(Call < List < ThongKeTheoThang > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }


}