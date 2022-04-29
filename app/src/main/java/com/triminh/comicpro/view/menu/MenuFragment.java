package com.triminh.comicpro.view.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.comicpro.R;
import com.triminh.comicpro.adapter.menu.Adapter_Menu;
import com.triminh.comicpro.api.ApiMenu;
import com.triminh.comicpro.model.menu.Menu;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.view.danhmuc.DonViTinhActivity;
import com.triminh.comicpro.view.danhmuc.KhachHangActivity;
import com.triminh.comicpro.view.danhmuc.LoaiBiaActivity;
import com.triminh.comicpro.view.danhmuc.LoaiTruyenActivity;
import com.triminh.comicpro.view.danhmuc.NhaCungCapActivity;
import com.triminh.comicpro.view.danhmuc.NhaXuatBanActivity;
import com.triminh.comicpro.view.danhmuc.QuaTangActivity;
import com.triminh.comicpro.view.danhmuc.QuocGiaActivity;
import com.triminh.comicpro.view.danhmuc.TacGiaActivity;
import com.triminh.comicpro.view.tusach.TuSachActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuFragment extends Fragment {

    ArrayList < Menu > lstMenu;
    Adapter_Menu adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    private Unbinder unbinder;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetMenu();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                ComicPro.objMenu = lstMenu.get(position);

                if (ComicPro.objMenu.getMamenu().equals("10")) {
                    Intent intent = new Intent(mContext, TacGiaActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("11")) {
                    Intent intent = new Intent(mContext, NhaXuatBanActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("12")) {
                    Intent intent = new Intent(mContext, NhaCungCapActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("13")) {
                    Intent intent = new Intent(mContext, KhachHangActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("14")) {
                    Intent intent = new Intent(mContext, LoaiBiaActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("15")) {
                    Intent intent = new Intent(mContext, QuocGiaActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("16")) {
                    Intent intent = new Intent(mContext, DonViTinhActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("17")) {
                    Intent intent = new Intent(mContext, LoaiTruyenActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("18")) {
                    Intent intent = new Intent(mContext, QuaTangActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("27")) {
                    Intent intent = new Intent(mContext, TuSachActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetMenu();
            }
        });
    }

    private void GetMenu() {
        ApiMenu.apiMenu.Menu(3).enqueue(new Callback < List < Menu > >() {
            @Override
            public void onResponse(Call < List < Menu > > call, Response < List < Menu > > response) {
                List < Menu > menus = response.body();
                if (menus != null) {
                    lstMenu = new ArrayList < Menu >();
                    lstMenu.addAll(menus);
                    adapter = new Adapter_Menu(mContext, lstMenu);
                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    recycleView.setAdapter(adapter);
                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call < List < Menu > > call, Throwable t) {
                Toast.makeText(mContext, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}