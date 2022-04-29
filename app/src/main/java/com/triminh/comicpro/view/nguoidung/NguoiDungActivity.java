package com.triminh.comicpro.view.nguoidung;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.comicpro.R;
import com.triminh.comicpro.adapter.nguoidung.Adapter_NguoiDung;
import com.triminh.comicpro.api.ApiUser;
import com.triminh.comicpro.model.nguoidung.User;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.system.TM_Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NguoiDungActivity extends AppCompatActivity {

    ArrayList <User> lstUser;
    Adapter_NguoiDung adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;
    TextInputEditText txtTenDangNhap, txtHoTen, txtMatKhau, txtXacNhanMatKhau;
    SwitchCompat swTruyCap;
    Button btnLuu, btnDong;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoidung);
        mContext = this;
        unbinder = ButterKnife.bind(this);
        setTitle("Người Dùng");
        GetUser();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                User user = lstUser.get(position);
                UpdateNguoiDung(user, position);
            }

            @Override
            public void onLongClick(View view, int position) {
                User user = lstUser.get(position);
                Delete(user, position);
            }
        }));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetUser();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void GetUser() {
        ApiUser.apiUser.GetUser("GET_DATA", "", "", "", false, "").enqueue(new Callback < List < User > >() {
            @Override
            public void onResponse(Call < List < User > > call, Response < List < User > > response) {
                List < User > users = response.body();
                if (users.size() > 0) {
                    lstUser = new ArrayList < User >();
                    lstUser.addAll(users);
                    adapter = new Adapter_NguoiDung(mContext, lstUser);
                    recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                    recycleView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call < List < User > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call Api Error", Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final User user, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa người dùng (" + user.getTendangnhap() + ") này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        ApiUser.apiUser.GetUser("DELETE", user.getTendangnhap(), "", "", false, "").enqueue(new Callback < List < User > >() {
                            @Override
                            public void onResponse(Call < List < User > > call, Response < List < User > > response) {
                                List < User > status = response.body();
                                if (status.size() > 0) {
                                    lstUser.remove(position);
                                    adapter.notifyDataSetChanged();
                                    TM_Toast.makeText(mContext, "Đã xóa người dùng (" + user.getTendangnhap() + ") thành công", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false);
                                }
                            }

                            @Override
                            public void onFailure(Call < List < User > > call, Throwable t) {
                                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                            }
                        });
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Đóng", R.drawable.ic_close_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        mBottomSheetDialog.show();
    }

    private void ThemNguoiDung() {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_nguoidung, null);

        txtTenDangNhap = view_bottom_sheet.findViewById(R.id.txtTenDangNhap);
        txtHoTen = view_bottom_sheet.findViewById(R.id.txtHoTen);
        txtMatKhau = view_bottom_sheet.findViewById(R.id.txtMatKhau);
        txtXacNhanMatKhau = view_bottom_sheet.findViewById(R.id.txtXacNhanMatKhau);
        swTruyCap = view_bottom_sheet.findViewById(R.id.swTruyCap);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();
        swTruyCap.setChecked(true);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTenDangNhap.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào tên đăng nhập.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (txtHoTen.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào họ tên đăng nhập.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (txtMatKhau.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào mật khẩu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                boolean truycap;
                if (swTruyCap.isChecked() == true) {
                    truycap = true;
                } else {
                    truycap = false;
                }

                String matkhau, xacnhanmatkhau;
                matkhau = txtMatKhau.getText().toString();
                xacnhanmatkhau = txtXacNhanMatKhau.getText().toString();
                if (matkhau.equals(xacnhanmatkhau)) {
                    ApiUser.apiUser.GetUser("SAVE", txtTenDangNhap.getText().toString(), txtHoTen.getText().toString(), txtMatKhau.getText().toString(), truycap, ComicPro.tendangnhap).enqueue(new Callback < List < User > >() {
                        @Override
                        public void onResponse(Call < List < User > > call, Response < List < User > > response) {
                            List < User > users = response.body();
                            if (users.size() > 0) {
                                lstUser = new ArrayList < User >();
                                lstUser.addAll(users);
                                adapter = new Adapter_NguoiDung(mContext, lstUser);
                                recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
                                recycleView.setAdapter(adapter);
                                TM_Toast.makeText(mContext, "Đã thêm người dùng (" + txtTenDangNhap.getText().toString() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                txtTenDangNhap.setText("");
                                txtHoTen.setText("");
                                txtMatKhau.setText("");
                                txtXacNhanMatKhau.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call < List < User > > call, Throwable t) {
                            TM_Toast.makeText(mContext, "Call Api Error", Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                        }
                    });
                } else {
                    TM_Toast.makeText(mContext, "Xác nhận mật khẩu không đúng", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                }
            }
        });

        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setCancelable(true);
                dialog.dismiss();
            }
        });
    }

    private void UpdateNguoiDung(User user, int position) {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_nguoidung, null);
        txtTenDangNhap = view_bottom_sheet.findViewById(R.id.txtTenDangNhap);
        txtHoTen = view_bottom_sheet.findViewById(R.id.txtHoTen);
        txtMatKhau = view_bottom_sheet.findViewById(R.id.txtMatKhau);
        txtXacNhanMatKhau = view_bottom_sheet.findViewById(R.id.txtXacNhanMatKhau);
        swTruyCap = view_bottom_sheet.findViewById(R.id.swTruyCap);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();
        txtTenDangNhap.setText(user.getTendangnhap());
        txtHoTen.setText(user.getHoten());
        if (user.getTruycap().equals("Cho phép")) {
            swTruyCap.setChecked(true);
        } else {
            swTruyCap.setChecked(false);
        }
        txtMatKhau.setEnabled(false);
        txtXacNhanMatKhau.setEnabled(false);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTenDangNhap.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào tên đăng nhập.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (txtHoTen.getText().toString().length() == 0) {
                    TM_Toast.makeText(mContext, "Vui lòng nhập vào họ tên đăng nhập.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }

                boolean truycap;
                if (swTruyCap.isChecked() == true) {
                    truycap = true;
                } else {
                    truycap = false;
                }

                ApiUser.apiUser.GetUser("UPDATE", txtTenDangNhap.getText().toString(), txtHoTen.getText().toString(), "", truycap, ComicPro.tendangnhap).enqueue(new Callback < List < User > >() {
                    @Override
                    public void onResponse(Call < List < User > > call, Response < List < User > > response) {
                        List < User > users = response.body();
                        if (users.size() > 0) {
                            lstUser.get(position).setHoten(txtHoTen.getText().toString());
                            lstUser.get(position).setTendangnhap(txtTenDangNhap.getText().toString());
                            adapter.notifyItemChanged(position);
                            TM_Toast.makeText(mContext, "Đã thêm người dùng (" + txtTenDangNhap.getText().toString() + ") thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                        }
                    }

                    @Override
                    public void onFailure(Call < List < User > > call, Throwable t) {
                        TM_Toast.makeText(mContext, "Call Api Error", Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                    }
                });
                dialog.setCancelable(true);
                dialog.dismiss();
            }

        });

        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setCancelable(true);
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnThem:
                ThemNguoiDung();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
        return true;
    }

}