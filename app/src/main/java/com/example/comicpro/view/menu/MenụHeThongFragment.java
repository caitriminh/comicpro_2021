package com.example.comicpro.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.comicpro.view.hethong.LoginActivity;
import com.example.comicpro.view.nguoidung.NguoiDungActivity;
import com.example.comicpro.view.tonkho.KetChuyenActivity;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class MenụHeThongFragment extends Fragment {

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
                if (ComicPro.objMenu.getMamenu().equals("19")) {
                    Intent intent = new Intent(mContext, NguoiDungActivity.class);
                    startActivity(intent);
                } else if (ComicPro.objMenu.getMamenu().equals("21")) {
                    Intent intent = new Intent(mContext, KetChuyenActivity.class);
                    startActivity(intent);
                }
                else if (ComicPro.objMenu.getMamenu().equals("25")) {
                    BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                            .setTitle("Xác Nhận")
                            .setMessage("Bạn có muốn thoát khỏi phần mềm không?")
                            .setCancelable(false)
                            .setPositiveButton("Xác Nhận", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                                @Override
                                public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                    SharedPreferences pref = getActivity().getSharedPreferences("SESSION", MODE_PRIVATE);
                                    pref.edit().clear().commit();
                                    Intent intent_loyout = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent_loyout);

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
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void GetMenu() {
        ApiMenu.apiMenu.Menu(4).enqueue(new Callback < List < Menu > >() {
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