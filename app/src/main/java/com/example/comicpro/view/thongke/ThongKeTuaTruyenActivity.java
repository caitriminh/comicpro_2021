package com.example.comicpro.view.thongke;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.example.comicpro.adapter.thongke.Adapter_ThongKeTheoTuaTruyen;
import com.example.comicpro.api.ApiThongKe;
import com.example.comicpro.model.thongke.ThongKeTheoTuaTruyen;
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


public class ThongKeTuaTruyenActivity extends AppCompatActivity {

    ArrayList < ThongKeTheoTuaTruyen > lstThongKe;
    Adapter_ThongKeTheoTuaTruyen adapter;
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
        setContentView(R.layout.activity_thongke_tuatruyen);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Thống Kê Tựa Truyện");
        GetThongKe();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                ComicPro.objThongKeTenTruyen = lstThongKe.get(position);
                Intent intent = new Intent(ThongKeTuaTruyenActivity.this, navTenTuyenActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void GetThongKe() {
        ApiThongKe.apiThongKe.GetThongKeTuaTruyen().enqueue(new Callback < List < ThongKeTheoTuaTruyen > >() {
            @Override
            public void onResponse(Call < List < ThongKeTheoTuaTruyen > > call, Response < List < ThongKeTheoTuaTruyen > > response) {
                List < ThongKeTheoTuaTruyen > thongKeTheoTuaTruyens = response.body();
                if (thongKeTheoTuaTruyens.size() > 0) {

                    lstThongKe = new ArrayList < ThongKeTheoTuaTruyen >();
                    lstThongKe.addAll(thongKeTheoTuaTruyens);
                    adapter = new Adapter_ThongKeTheoTuaTruyen(mContext, lstThongKe);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    //Tổng thành tiền
                    txtTongThanhTien.setText(lstThongKe.get(0).getThanhtien2());

                    DecimalFormat format = new DecimalFormat("#,##0");
                    Integer intSoCuon = lstThongKe.get(0).getTongsocuon();
                    txtTongSoCuon.setText(format.format(intSoCuon));
                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call < List < ThongKeTheoTuaTruyen > > call, Throwable t) {
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