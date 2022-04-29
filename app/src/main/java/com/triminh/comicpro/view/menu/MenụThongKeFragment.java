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


import com.example.comicpro.R;
import com.triminh.comicpro.adapter.menu.Adapter_Menu;
import com.triminh.comicpro.api.ApiMenu;
import com.triminh.comicpro.model.menu.Menu;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.view.bieudo.TheoNhaXuatBanActivity;
import com.triminh.comicpro.view.bieudo.TheoThangActivity;
import com.triminh.comicpro.view.thongke.ThongKeTheoNCCActivity;
import com.triminh.comicpro.view.thongke.ThongKeTheoNXBActivity;
import com.triminh.comicpro.view.thongke.ThongKeTheoNamActivity;
import com.triminh.comicpro.view.thongke.ThongKeTheoThangActivity;
import com.triminh.comicpro.view.thongke.ThongKeTuaTruyenActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenụThongKeFragment extends Fragment {

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
                if (ComicPro.objMenu.getMamenu().equals("06")) {
                    Intent intent = new Intent(mContext, ThongKeTheoThangActivity.class);
                    startActivity(intent);
                }
                else if (ComicPro.objMenu.getMamenu().equals("07")) {
                    Intent intent = new Intent(mContext, ThongKeTheoNamActivity.class);
                    startActivity(intent);
                }
                else if (ComicPro.objMenu.getMamenu().equals("08")) {
                    Intent intent = new Intent(mContext, ThongKeTuaTruyenActivity.class);
                    startActivity(intent);
                }
                else if (ComicPro.objMenu.getMamenu().equals("09")) {
                    Intent intent = new Intent(mContext, ThongKeTheoNXBActivity.class);
                    startActivity(intent);
                }
                else if (ComicPro.objMenu.getMamenu().equals("22")) {
                    Intent intent = new Intent(mContext, TheoNhaXuatBanActivity.class);
                    startActivity(intent);
                }
                else if (ComicPro.objMenu.getMamenu().equals("23")) {
                    Intent intent = new Intent(mContext, TheoThangActivity.class);
                    startActivity(intent);
                }
                else if (ComicPro.objMenu.getMamenu().equals("26")) {
                    Intent intent = new Intent(mContext, ThongKeTheoNCCActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void GetMenu() {
        ApiMenu.apiMenu.Menu(2).enqueue(new Callback < List < Menu > >() {
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