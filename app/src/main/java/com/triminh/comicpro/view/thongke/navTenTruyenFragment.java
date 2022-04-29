package com.triminh.comicpro.view.thongke;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.comicpro.R;

import com.triminh.comicpro.adapter.thongke.Adapter_navTenTruyen;
import com.triminh.comicpro.api.ApiTenTruyen;
import com.triminh.comicpro.model.tentruyen.TenTruyen;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.system.TM_Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class navTenTruyenFragment extends Fragment {

    ArrayList <TenTruyen> lstTenTruyen;
    Adapter_navTenTruyen adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_navtentruyen, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetTenTruyen();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTenTruyen();
            }
        });
    }


    public void GetTenTruyen() {
        ApiTenTruyen.apiTenTruyen.GetTenTuyen(ComicPro.objThongKeTenTruyen.getMatua(), 1).enqueue(new Callback < List < TenTruyen > >() {
            @Override
            public void onResponse(Call < List < TenTruyen > > call, Response < List < TenTruyen > > response) {
                List < TenTruyen > tenTruyens = response.body();
                if (tenTruyens!=null) {
                    lstTenTruyen = new ArrayList < TenTruyen >();
                    lstTenTruyen.addAll(tenTruyens);
                    adapter = new Adapter_navTenTruyen(getActivity(), lstTenTruyen);

                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    recycleView.setAdapter(adapter);
                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call < List < TenTruyen > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_item, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
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
                try {
                    adapter.filter(newText);
                }
                catch (Exception e){

                }
                return false;
            }
        });
    }
}