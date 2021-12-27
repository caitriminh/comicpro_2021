package com.example.comicpro.view.thongke;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.comicpro.R;
import com.example.comicpro.system.ComicPro;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class navTenTuyenActivity extends AppCompatActivity {

    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navtentruyen);
//Thêm nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar.setTitle(ComicPro.objThongKeTenTruyen.getTuatruyen());
        loadFragment(new navTenTruyenFragment());
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_tentruyen_hinhanh:

                    toolbar.setTitle(ComicPro.objThongKeTenTruyen.getTuatruyen());
                    fragment = new navTenTruyenFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_tentruyen_danhsach:
                    toolbar.setTitle(ComicPro.objThongKeTenTruyen.getTuatruyen());
                    fragment = new navTenTruyenDanhSachFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
              FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
