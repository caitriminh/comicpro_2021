package com.example.comicpro.view.menu;

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


import com.example.comicpro.R;
import com.example.comicpro.adapter.menu.Adapter_Menu;
import com.example.comicpro.api.ApiMenu;
import com.example.comicpro.model.menu.Menu;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.RecyclerTouchListener;
import com.example.comicpro.view.lichphathanh.LichPhatHanhActivity;
import com.example.comicpro.view.moinhat.MoiNhatActivity;
import com.example.comicpro.view.phieunhap.PhieuNhapActivity;
import com.example.comicpro.view.phieuxuat.PhieuXuatActivity;
import com.example.comicpro.view.tonkho.TonKhoActivity;
import com.example.comicpro.view.tusach.TuSachChiTietActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenụNghiepVuFragment extends Fragment {

    ArrayList < Menu > lstMenu;
    Adapter_Menu adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    private Unbinder unbinder;


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
                if (ComicPro.objMenu.getMamenu().equals("01")) {
                    Intent intent = new Intent(mContext, MoiNhatActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("02")) {
                    Intent intent = new Intent(mContext, PhieuNhapActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("03")) {
                    Intent intent = new Intent(mContext, PhieuXuatActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("04")) {
                    Intent intent = new Intent(mContext, TonKhoActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("05")) {
                    Intent intent = new Intent(mContext, LichPhatHanhActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("28")) {
                    Intent intent = new Intent(mContext, TuSachChiTietActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void GetMenu() {
        ApiMenu.apiMenu.Menu(1).enqueue(new Callback < List < Menu > >() {
            @Override
            public void onResponse(Call < List < Menu > > call, Response < List < Menu > > response) {
                List < Menu > menus = response.body();
                if (menus != null) {
                    lstMenu = new ArrayList < Menu >();
                    lstMenu.addAll(menus);
                    adapter = new Adapter_Menu(mContext, lstMenu);
                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    recycleView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call < List < Menu > > call, Throwable t) {
                Toast.makeText(mContext, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}