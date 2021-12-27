package com.example.comicpro.view.bieudo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;


import com.example.comicpro.R;

import com.example.comicpro.api.ApiBieuDo;
import com.example.comicpro.model.thongke.ThongKeTheoNXB;
import com.example.comicpro.system.TM_Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheoNhaXuatBanActivity extends AppCompatActivity {

    ArrayList < ThongKeTheoNXB > lstThongKe;
    ArrayList SoCuon = new ArrayList();
    ArrayList NXB = new ArrayList();
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theo_nha_xuat_ban);
        mContext = this;
        //Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Thống Kê Theo Nhà Xuất Bản");
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
        ApiBieuDo.apiBieuDo.GetBieuDoNXB().enqueue(new Callback < List < ThongKeTheoNXB > >() {
            @Override
            public void onResponse(Call < List < ThongKeTheoNXB > > call, Response < List < ThongKeTheoNXB > > response) {
                List < ThongKeTheoNXB > thongKeTheoNXBS = response.body();
                if (thongKeTheoNXBS.size() > 0) {
                    lstThongKe = new ArrayList < ThongKeTheoNXB >();
                    lstThongKe.addAll(thongKeTheoNXBS);
                    if (lstThongKe == null)
                        return;
                    int i = 0;
                    for (ThongKeTheoNXB thongKeTheoNXB : lstThongKe) {
                        SoCuon.add(new BarEntry((thongKeTheoNXB.getSocuon()), i));
                        NXB.add(thongKeTheoNXB.getNhaxuatban());
                        i++;
                    }
                    PieDataSet dataSet = new PieDataSet(SoCuon, "");
                    PieData data = new PieData(NXB, dataSet);

                    PieChart pieChart = findViewById(R.id.piechart);
                    pieChart.setUsePercentValues(true);
                    pieChart.setDescription("");

                    pieChart.setExtraOffsets(5, 10, 5, 5);
                    pieChart.setDragDecelerationFrictionCoef(0.95f);
                    pieChart.setCenterText(generateCenterSpannableText());

                    pieChart.setDrawHoleEnabled(true);
                    pieChart.setHoleColor(Color.WHITE);

                    pieChart.setTransparentCircleColor(Color.WHITE);
                    pieChart.setTransparentCircleAlpha(110);

                    pieChart.setHoleRadius(58f);
                    pieChart.setTransparentCircleRadius(61f);
                    pieChart.setDrawCenterText(true);
                    pieChart.setRotationAngle(0);
                    pieChart.setRotationEnabled(true);
                    pieChart.setHighlightPerTapEnabled(true);


                    pieChart.setData(data);
                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    data.setValueFormatter(new PercentFormatter());
                    data.setValueTextSize(9f);
                    data.setValueTextColor(Color.WHITE);
                    pieChart.setData(data);

                    // undo all highlights
                    pieChart.highlightValues(null);
                    pieChart.invalidate();
                    pieChart.animateXY(5000, 5000);
                }
            }

            @Override
            public void onFailure(Call < List < ThongKeTheoNXB > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("COMICPRO\ndeveloped by Cai Tri Minh");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 8, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 8, s.length() - 9, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 8, s.length() - 9, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 8, s.length() - 9, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 12, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 12, s.length(), 0);
        return s;
    }
}