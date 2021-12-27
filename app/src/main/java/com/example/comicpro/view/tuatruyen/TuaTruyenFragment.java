package com.example.comicpro.view.tuatruyen;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.comicpro.R;

import com.example.comicpro.adapter.tuatruyen.Adapter_TuaTruyen;
import com.example.comicpro.api.ApiLoaiTruyen;
import com.example.comicpro.api.ApiNhaXuatBan;
import com.example.comicpro.api.ApiQuocGia;
import com.example.comicpro.api.ApiTacGia;
import com.example.comicpro.api.ApiTuaTruyen;
import com.example.comicpro.model.danhmuc.LoaiTruyen;
import com.example.comicpro.model.danhmuc.NhaXuatBan;
import com.example.comicpro.model.danhmuc.QuocGia;
import com.example.comicpro.model.danhmuc.TacGia;
import com.example.comicpro.model.tuatruyen.TuaTruyen;
import com.example.comicpro.system.ClickListener;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.RecyclerTouchListener;
import com.example.comicpro.system.TM_Toast;
import com.example.comicpro.view.tentruyen.TenTruyenActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TuaTruyenFragment extends Fragment {
    ArrayList < TuaTruyen > lstTuaTruyen;
    TuaTruyen tuaTruyen;
    ArrayList < TacGia > lstTacGia;
    ArrayList < LoaiTruyen > lstLoaiTruyen;
    ArrayList < NhaXuatBan > lstNhaXuatBan;
    ArrayList < QuocGia > lstQuocGia;

    Adapter_TuaTruyen adapter;
    private Unbinder unbinder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    public String strMatacgia = "", strMaloaitruyen = "", strMaNXB = "", straction = "GET_DATA";
    Integer namxuatban, sotap, taiban, manuoc = -1;
    Boolean theodoi;
    TextInputEditText txtTuaTruyen, txtSoTap, txtNamXuatBan, txtTheoDoi, txtTaiBan, txtMaTacGia, txtLoaiTruyen, txtNhaXuatBan, txtMaNuoc;
    Button btnLuu, btnDong;
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
        unbinder = ButterKnife.bind(this, rootView);
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
                Intent intent = new Intent(getActivity(), TenTruyenActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                tuaTruyen = (TuaTruyen) lstTuaTruyen.get(position);
                SuaTuaTruyen(view, position);

            }
        }));
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTuaTruyen(straction);
            }
        });
    }


    public void ThemTuaTruyen() {
        View view_bottom_sheet = LayoutInflater.from(mContext).inflate(R.layout.bottomsheet_ct_tuatruyen, null);
        txtTuaTruyen = view_bottom_sheet.findViewById(R.id.txtTuaTruyen);
        txtLoaiTruyen = view_bottom_sheet.findViewById(R.id.txtLoaiTruyen);
        txtNamXuatBan = view_bottom_sheet.findViewById(R.id.txtNamXuatBan);
        txtSoTap = view_bottom_sheet.findViewById(R.id.txtSoTap);
        txtTaiBan = view_bottom_sheet.findViewById(R.id.txtTaiBan);
        txtMaNuoc = view_bottom_sheet.findViewById(R.id.txtMaNuoc);
        txtMaTacGia = view_bottom_sheet.findViewById(R.id.txtMaTacGia);
        txtNhaXuatBan = view_bottom_sheet.findViewById(R.id.txtNhaXuatBan);
        txtTheoDoi = view_bottom_sheet.findViewById(R.id.txtTheoDoi);
        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(mContext, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        GetTacgia(txtMaTacGia);
        GetLoaiTruyen(txtLoaiTruyen);
        GetNhaXuatBan(txtNhaXuatBan);
        GetXuatXu(txtMaNuoc);
        txtTheoDoi.setEnabled(false);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTuaTruyen.getText().toString().equals("")) {
                    TM_Toast.makeText(getActivity(), "Vui lòng nhập vào tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (strMaloaitruyen.equals("")) {
                    TM_Toast.makeText(getActivity(), "Vui lòng nhập vào mã loại tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (strMatacgia.equals("")) {
                    TM_Toast.makeText(getActivity(), "Vui lòng nhập vào tác giả tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (strMaNXB.equals("")) {
                    TM_Toast.makeText(getActivity(), "Vui lòng nhập vào nhà xuất bản.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (manuoc < 0) {
                    TM_Toast.makeText(getActivity(), "Vui lòng nhập vào xuất xứ.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (txtSoTap.getText().toString().equals("")) {
                    sotap = 0;
                } else {
                    sotap = Integer.parseInt(txtSoTap.getText().toString());
                }
                if (txtNamXuatBan.getText().toString().equals("")) {
                    namxuatban = 0;
                } else {
                    namxuatban = Integer.parseInt(txtNamXuatBan.getText().toString());
                }
                if (txtTaiBan.getText().toString().equals("")) {
                    taiban = 0;
                } else {
                    taiban = Integer.parseInt(txtTaiBan.getText().toString());
                }

                ApiTuaTruyen.apiTuaTruyen.Insert(txtTuaTruyen.getText().toString(), strMaloaitruyen, strMatacgia, strMaNXB, manuoc, sotap, namxuatban, taiban, ComicPro.tendangnhap).enqueue(new Callback < List < TuaTruyen > >() {
                    @Override
                    public void onResponse(Call < List < TuaTruyen > > call, Response < List < TuaTruyen > > response) {
                        List < TuaTruyen > status = response.body();
                        if (status.size() > 0) {

                        }
                    }

                    @Override
                    public void onFailure(Call < List < TuaTruyen > > call, Throwable t) {
//                        TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                    }
                });
                GetTuaTruyen(straction);
                TM_Toast.makeText(mContext, "Thêm mới tựa truyện (" + txtTuaTruyen.getText() + ") thành công", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
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

    public void SuaTuaTruyen(View view, int position) {
        View view_bottom_sheet = LayoutInflater.from(view.getContext()).inflate(R.layout.bottomsheet_ct_tuatruyen, null);
        txtTuaTruyen = view_bottom_sheet.findViewById(R.id.txtTuaTruyen);
        txtLoaiTruyen = view_bottom_sheet.findViewById(R.id.txtLoaiTruyen);
        txtNamXuatBan = view_bottom_sheet.findViewById(R.id.txtNamXuatBan);
        txtSoTap = view_bottom_sheet.findViewById(R.id.txtSoTap);
        txtTaiBan = view_bottom_sheet.findViewById(R.id.txtTaiBan);
        txtMaNuoc = view_bottom_sheet.findViewById(R.id.txtMaNuoc);
        txtMaTacGia = view_bottom_sheet.findViewById(R.id.txtMaTacGia);
        txtNhaXuatBan = view_bottom_sheet.findViewById(R.id.txtNhaXuatBan);
        txtTheoDoi = view_bottom_sheet.findViewById(R.id.txtTheoDoi);
        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(view.getContext(), R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        GetTacgia(txtMaTacGia);
        GetLoaiTruyen(txtLoaiTruyen);
        GetNhaXuatBan(txtNhaXuatBan);
        GetXuatXu(txtMaNuoc);
        GetTheoDoi(txtTheoDoi);

        txtTuaTruyen.setText(tuaTruyen.getTuatruyen());
        txtLoaiTruyen.setText(tuaTruyen.getLoaitruyen());
        strMaloaitruyen = tuaTruyen.getMaloai();
        txtNamXuatBan.setText(tuaTruyen.getNamxuatban());
        txtSoTap.setText(tuaTruyen.getSotap());
        txtTaiBan.setText(tuaTruyen.getTaiban());
        strMatacgia = tuaTruyen.getMatacgia();
        txtMaNuoc.setText(tuaTruyen.getQuocgia());
        manuoc = tuaTruyen.getMaquocgia();
        txtMaTacGia.setText(tuaTruyen.getTacgia());
        txtNhaXuatBan.setText(tuaTruyen.getNhaxuatban());
        strMaNXB = tuaTruyen.getManxb();
        txtTheoDoi.setText(tuaTruyen.getTheodoi2());

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTuaTruyen.getText().toString().equals("")) {
                    TM_Toast.makeText(getActivity(), "Vui lòng nhập vào tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (strMaloaitruyen.equals("")) {
                    TM_Toast.makeText(getActivity(), "Vui lòng nhập vào mã loại tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (strMatacgia.equals("")) {
                    TM_Toast.makeText(getActivity(), "Vui lòng nhập vào tác giả tựa truyện.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (strMaNXB.equals("")) {
                    TM_Toast.makeText(getActivity(), "Vui lòng nhập vào nhà xuất bản.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (manuoc < 0) {
                    TM_Toast.makeText(getActivity(), "Vui lòng nhập vào xuất xứ.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                if (txtSoTap.getText().toString().equals("")) {
                    sotap = 0;
                } else {
                    sotap = Integer.parseInt(txtSoTap.getText().toString());
                }
                if (txtNamXuatBan.getText().toString().equals("")) {
                    namxuatban = 0;
                } else {
                    namxuatban = Integer.parseInt(txtNamXuatBan.getText().toString());
                }
                if (txtTaiBan.getText().toString().equals("")) {
                    taiban = 0;
                } else {
                    taiban = Integer.parseInt(txtTaiBan.getText().toString());
                }
                String a = txtTheoDoi.getText().toString();
                if (a.equals("Đang theo dõi")) {
                    theodoi = true;
                } else {
                    theodoi = false;
                }
                ApiTuaTruyen.apiTuaTruyen.Updare(txtTuaTruyen.getText().toString(), strMatacgia, tuaTruyen.getMatua(), strMaNXB, manuoc, Integer.parseInt(txtSoTap.getText().toString()), Integer.parseInt(txtNamXuatBan.getText().toString()), Integer.parseInt(txtTaiBan.getText().toString()), "", ComicPro.tendangnhap, theodoi).enqueue(new Callback < List < TuaTruyen > >() {
                    @Override
                    public void onResponse(Call < List < TuaTruyen > > call, Response < List < TuaTruyen > > response) {
                        List < TuaTruyen > tuaTruyens = response.body();
                        if (tuaTruyens != null) {

                        }
                    }

                    @Override
                    public void onFailure(Call < List < TuaTruyen > > call, Throwable t) {

                        //TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                    }
                });
                lstTuaTruyen.get(position).setTuatruyen(txtTuaTruyen.getText().toString());
                lstTuaTruyen.get(position).setTacgia(txtMaTacGia.getText().toString());
                lstTuaTruyen.get(position).setSotap(txtSoTap.getText().toString());
                adapter.notifyItemChanged(position);
                TM_Toast.makeText(mContext, "Cập nhật thành công", TM_Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
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

    public void GetTacgia(TextView txtMaTacGia) {
        ApiTacGia.apiTacGia.GetTacGia().enqueue(new Callback < List < TacGia > >() {
            @Override
            public void onResponse(Call < List < TacGia > > call, Response < List < TacGia > > response) {
                List < TacGia > tacGias = response.body();
                if (tacGias.size() > 0) {
                    lstTacGia = new ArrayList < TacGia >();
                    lstTacGia.addAll(tacGias);
                    txtMaTacGia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Chọn tác giả");
                            builder.setCancelable(false);
                            String[] arrayTacgia = new String[lstTacGia.size()];
                            int i = 0;
                            for (TacGia tacGia : lstTacGia) {
                                arrayTacgia[i] = tacGia.getTacgia();
                                i++;
                            }
                            ;
                            builder.setSingleChoiceItems(arrayTacgia, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    TacGia tacGia = lstTacGia.get(i);
                                    txtMaTacGia.setText(tacGia.getTacgia());
                                    strMatacgia = tacGia.getMatacgia();
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call < List < TacGia > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });

    }

    private void GetXuatXu(TextView txtMaNuoc) {
        ApiQuocGia.apiQuocGia.QuocGia("GET_DATA", 0, "", "", "").enqueue(new Callback < List < QuocGia > >() {
            @Override
            public void onResponse(Call < List < QuocGia > > call, Response < List < QuocGia > > response) {
                List < QuocGia > quocGias = response.body();
                if (quocGias.size() > 0) {
                    lstQuocGia = new ArrayList < QuocGia >();
                    lstQuocGia.addAll(quocGias);

                    txtMaNuoc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Chọn quốc gia");
                            builder.setCancelable(false);
                            String[] arrayQuocGia = new String[lstQuocGia.size()];
                            int i = 0;
                            for (QuocGia quocGia : lstQuocGia) {
                                arrayQuocGia[i] = quocGia.getQuocgia();
                                i++;
                            }
                            ;
                            builder.setSingleChoiceItems(arrayQuocGia, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    QuocGia quocGia = lstQuocGia.get(i);
                                    txtMaNuoc.setText(quocGia.getQuocgia());
                                    manuoc = quocGia.getId();
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call < List < QuocGia > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });

    }

    private void GetLoaiTruyen(TextView txtLoaiTruyen) {
        ApiLoaiTruyen.apiLoaiTruyen.GetLoaiTruyen().enqueue(new Callback < List < LoaiTruyen > >() {
            @Override
            public void onResponse(Call < List < LoaiTruyen > > call, Response < List < LoaiTruyen > > response) {
                List < LoaiTruyen > loaiTruyens = response.body();
                if (loaiTruyens.size() > 0) {
                    lstLoaiTruyen = new ArrayList < LoaiTruyen >();
                    lstLoaiTruyen.addAll(loaiTruyens);

                    txtLoaiTruyen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Chọn loại truyện");
                            builder.setCancelable(false);
                            String[] arrayLoaitruyen = new String[lstLoaiTruyen.size()];
                            int i = 0;
                            for (LoaiTruyen loaiTruyen : lstLoaiTruyen) {
                                arrayLoaitruyen[i] = loaiTruyen.getLoaitruyen();
                                i++;
                            }
                            ;
                            builder.setSingleChoiceItems(arrayLoaitruyen, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    LoaiTruyen loaiTruyen = lstLoaiTruyen.get(i);
                                    txtLoaiTruyen.setText(loaiTruyen.getLoaitruyen());
                                    strMaloaitruyen = loaiTruyen.getMaloai();
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call < List < LoaiTruyen > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });

    }

    private void GetNhaXuatBan(TextView txtNhaXuatBan) {
        ApiNhaXuatBan.apiNhaXuatBan.GetNhaXuatBan(1, "").enqueue(new Callback < List < NhaXuatBan > >() {
            @Override
            public void onResponse(Call < List < NhaXuatBan > > call, Response < List < NhaXuatBan > > response) {
                List < NhaXuatBan > nhaXuatBans = response.body();
                if (nhaXuatBans.size() > 0) {
                    lstNhaXuatBan = new ArrayList < NhaXuatBan >();
                    lstNhaXuatBan.addAll(nhaXuatBans);
                    txtNhaXuatBan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Chọn nhà xuất bản");
                            builder.setCancelable(false);
                            String[] arrayNhaXuatBan = new String[lstNhaXuatBan.size()];
                            int i = 0;
                            for (NhaXuatBan nhaXuatBan : lstNhaXuatBan) {
                                arrayNhaXuatBan[i] = nhaXuatBan.getNhaxuatban();
                                i++;
                            }
                            ;
                            builder.setSingleChoiceItems(arrayNhaXuatBan, -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NhaXuatBan nhaXuatBan = lstNhaXuatBan.get(i);
                                    txtNhaXuatBan.setText(nhaXuatBan.getNhaxuatban());
                                    strMaNXB = nhaXuatBan.getManxb();
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call < List < NhaXuatBan > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });

    }

    public void GetTuaTruyen(String straction) {
        ApiTuaTruyen.apiTuaTruyen.GetTuaTruyen(straction).enqueue(new Callback < List < TuaTruyen > >() {
            @Override
            public void onResponse(Call < List < TuaTruyen > > call, Response < List < TuaTruyen > > response) {
                List < TuaTruyen > tuaTruyens = response.body();
                if (tuaTruyens != null) {
                    lstTuaTruyen = new ArrayList < TuaTruyen >();
                    lstTuaTruyen.addAll(tuaTruyens);
                    adapter = new Adapter_TuaTruyen(getActivity(), lstTuaTruyen);

                    recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    recycleView.setAdapter(adapter);
                    //Làm mới dữ liệu
                    swiperefresh.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call < List < TuaTruyen > > call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail", TM_Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void GetTheoDoi(TextView txtBaoGia) {
        txtBaoGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = txtTheoDoi.getText().toString();
                if (s.equals("Không theo dõi")) {
                    txtTheoDoi.setText("Đang theo dõi");
                } else {
                    txtTheoDoi.setText("Không theo dõi");
                }
            }
        });
    }

    public void GetChucNang() {
        final String[] chucnangs = {"Tất cả", "Đang theo dõi", "Không theo dõi"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Chức năng");
        builder.setCancelable(false);
        int i = 0;

        builder.setItems(chucnangs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String chucnang = chucnangs[i];
                dialogInterface.dismiss(); // Close Dialog
                if (chucnang.equals("Tất cả")) {
                    straction = "GET_DATA_ALL";
                } else if (chucnang.equals("Đang theo dõi")) {
                    straction = "GET_DATA";
                } else if (chucnang.equals("Không theo dõi")) {
                    straction = "GET_DATA_KHONGTHEODOI";
                }
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
                ThemTuaTruyen();
                break;
            case R.id.btnLoc:
                GetChucNang();
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
        menuInflater.inflate(R.menu.menu_item_filter, menu);

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
}