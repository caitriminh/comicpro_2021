package com.triminh.comicpro.view.tuatruyen;

import static com.triminh.comicpro.view.tuatruyen.ThemTuaTruyenActivity.strMatacgia;
import static com.triminh.comicpro.view.tuatruyen.ThemTuaTruyenActivity.strSoTap;
import static com.triminh.comicpro.view.tuatruyen.ThemTuaTruyenActivity.strTenTG;
import static com.triminh.comicpro.view.tuatruyen.ThemTuaTruyenActivity.strTuaTruyen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.triminh.comicpro.adapter.tuatruyen.Adapter_TuaTruyen;
import com.triminh.comicpro.api.ApiTuaTruyen;
import com.triminh.comicpro.model.tuatruyen.TuaTruyen;
import com.triminh.comicpro.system.ClickListener;
import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.RecyclerTouchListener;
import com.triminh.comicpro.system.TM_Toast;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.triminh.comicpro.view.tentruyen.viewTenTruyenActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuaTruyenFragment extends Fragment {
    ArrayList<TuaTruyen> lstTuaTruyen;
    TuaTruyen tuaTruyen;
    Adapter_TuaTruyen adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    static boolean edit = false;
    public String straction = "GET_DATA", name = "tuatruyen";
    Integer position_temp;
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
        View rootView = inflater.inflate(R.layout.fragment_tuatruyen, container, false);
        Unbinder unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetTuaTruyen(straction);
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recycleView, new ClickListener() {


            @Override
            public void onClick(View view, int position) {
                ComicPro.objTuaTruyen = lstTuaTruyen.get(position);
                ComicPro.PhieuNhap = 0;
                ComicPro.PhieuXuat = 0;
                Intent intent = new Intent(getActivity(), viewTenTruyenActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                position_temp = position;
                tuaTruyen = lstTuaTruyen.get(position);
                GetChucNang(tuaTruyen, position);
            }
        }));
        //L??m m???i d??? li???u
        swiperefresh.setOnRefreshListener(() -> GetTuaTruyen(straction));
    }

    public void GetTuaTruyen(String straction) {
        ApiTuaTruyen.apiTuaTruyen.GetTuaTruyen(straction).enqueue(new Callback<List<TuaTruyen>>() {
            @Override
            public void onResponse(Call<List<TuaTruyen>> call, Response<List<TuaTruyen>> response) {
                List<TuaTruyen> tuaTruyens = response.body();
                if (tuaTruyens != null) {
                    lstTuaTruyen = new ArrayList<>();
                    lstTuaTruyen.addAll(tuaTruyens);
                    adapter = new Adapter_TuaTruyen(getActivity(), lstTuaTruyen);

                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    recycleView.setAdapter(adapter);
                    //L??m m???i d??? li???u
                    swiperefresh.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<List<TuaTruyen>> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final TuaTruyen tuaTruyen, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("X??c Nh???n")
                .setMessage("B???n c?? mu???n x??a t???a truy???n (" + tuaTruyen.getTuatruyen() + ") n??y kh??ng?")
                .setCancelable(false)
                .setPositiveButton("X??a", R.drawable.ic_delete_white, (dialogInterface, which) -> {

                    ApiTuaTruyen.apiTuaTruyen.Delete("DELETE", tuaTruyen.getMatua()).enqueue(new Callback<List<TuaTruyen>>() {
                        @Override
                        public void onResponse(Call<List<TuaTruyen>> call, Response<List<TuaTruyen>> response) {
                            List<TuaTruyen> tuaTruyens = response.body();
                            if (tuaTruyens != null) {
                                if (tuaTruyens.get(0).getStatus().equals("OK")) {
                                    TM_Toast.makeText(mContext, "???? x??a t???a truy???n (" + tuaTruyen.getTuatruyen() + ") th??nh c??ng.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                    GetTuaTruyen(straction);
                                } else {
                                    TM_Toast.makeText(mContext, "T???a truy???n (" + tuaTruyen.getTuatruyen() + ") ???? ???????c s??? d???ng.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                                }
                            }


                        }

                        @Override
                        public void onFailure(Call<List<TuaTruyen>> call, Throwable t) {

                        }
                    });

                    dialogInterface.dismiss();
                })
                .setNegativeButton("????ng", R.drawable.ic_close_white, (dialogInterface, which) -> dialogInterface.dismiss())
                .build();
        mBottomSheetDialog.show();
    }

    public void GetChucNang(TuaTruyen tuaTruyen, int position) {
        final String[] chucnangs = {"S???a", "X??a", "??ang theo d??i", "Kh??ng theo d??i", "T???t c???"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Ch???c n??ng");
        builder.setCancelable(false);

        builder.setItems(chucnangs, (dialogInterface, i1) -> {
            String chucnang = chucnangs[i1];
            dialogInterface.dismiss();
            if (chucnang.equals("S???a")) {
                edit = true;
                ComicPro.strMaTua = tuaTruyen.getMatua();

                Intent sub = new Intent(mContext, ThemTuaTruyenActivity.class);
                sub.putExtra("name", name);
                startActivityForResult(sub, 100);
            } else if (chucnang.equals("X??a")) {
                Delete(tuaTruyen, position);
            } else if (chucnang.equals("??ang theo d??i")) {
                straction = "GET_DATA";
                GetTuaTruyen(straction);
            } else if (chucnang.equals("Kh??ng theo d??i")) {
                straction = "GET_DATA_KHONGTHEODOI";
                GetTuaTruyen(straction);
            } else if (chucnang.equals("T???t c???")) {
                straction = "GET_DATA_ALL";
                GetTuaTruyen(straction);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnThem:
                edit = false;
                Intent sub = new Intent(mContext, ThemTuaTruyenActivity.class);
                sub.putExtra("name", name);
                startActivityForResult(sub, 100);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_item, menu);
        menuInflater.inflate(R.menu.menu_item_add, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("T??m ki???m...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (adapter == null) {
                        Log.w("adapter null", "null");
                    }
                    adapter.filter(newText);
                } catch (Exception e) {

                }

                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                if (edit == true) {
                    lstTuaTruyen.get(position_temp).setTuatruyen(strTuaTruyen);
                    lstTuaTruyen.get(position_temp).setTacgia(strTenTG);
                    lstTuaTruyen.get(position_temp).setSotap(strSoTap);
                    adapter.notifyItemChanged(position_temp);
                } else {
                    GetTuaTruyen(straction);
                }

            }
        }
    }
}