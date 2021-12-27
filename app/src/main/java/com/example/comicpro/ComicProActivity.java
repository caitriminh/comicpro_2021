package com.example.comicpro;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.comicpro.view.tuatruyen.TuaTruyenFragment;
import com.example.comicpro.view.menu.MenuFragment;
import com.example.comicpro.view.menu.MenụHeThongFragment;
import com.example.comicpro.view.menu.MenụNghiepVuFragment;
import com.example.comicpro.view.menu.MenụThongKeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ComicProActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comicpro);
        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // load the store fragment by default
        toolbar.setTitle("Tựa Truyện");
        OpenFragment(new TuaTruyenFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    toolbar.setTitle("Tựa Truyện");
                    fragment = new TuaTruyenFragment();
                    OpenFragment(fragment);
                    return true;

                case R.id.nav_nghiepvu:
                    toolbar.setTitle("Nghiệp Vụ");
                    fragment = new MenụNghiepVuFragment();
                    OpenFragment(fragment);
                    return true;

                case R.id.nav_thongke:
                    toolbar.setTitle("Thống Kê");
                    fragment = new MenụThongKeFragment();
                    OpenFragment(fragment);
                    return true;

                case R.id.nav_danhmuc:
                    toolbar.setTitle("Danh Mục");
                    fragment = new MenuFragment();
                    OpenFragment(fragment);
                    return true;

                case R.id.nav_hethong:
                    toolbar.setTitle("Hệ Thống");
                    fragment = new MenụHeThongFragment();
                    OpenFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void OpenFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

//            case R.id.btnDonHang:
//                Intent intent = new Intent(MotorBikerActivity.this, DonHangTempActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.btnLogout:
//                BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) this)
//                        .setTitle("Xác Nhận")
//                        .setMessage("Bạn có muốn thoát khỏi phần mềm không?")
//                        .setCancelable(false)
//                        .setPositiveButton("Xác Nhận", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
//                            @Override
//                            public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
//
//                                SharedPreferences pref = getSharedPreferences("SESSION", MODE_PRIVATE);
//                                pref.edit().clear().commit();
//                                Intent intent_loyout = new Intent(MotorBikerActivity.this, LoginActivity.class);
//                                startActivity(intent_loyout);
//                                finish();
//
//                                dialogInterface.dismiss();
//                            }
//                        })
//                        .setNegativeButton("Đóng", R.drawable.ic_close_white, new BottomSheetMaterialDialog.OnClickListener() {
//                            @Override
//                            public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
//                                dialogInterface.dismiss();
//                            }
//                        })
//                        .build();
//                mBottomSheetDialog.show();
//
//                break;
//            case android.R.id.home:
//                finish();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater findMenuItems = getMenuInflater();
//        findMenuItems.inflate(R.menu.menu_item, menu);
//
        return super.onCreateOptionsMenu(menu);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
