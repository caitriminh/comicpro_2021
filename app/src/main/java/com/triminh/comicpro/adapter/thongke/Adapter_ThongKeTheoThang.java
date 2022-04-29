package com.triminh.comicpro.adapter.thongke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.triminh.comicpro.model.thongke.ThongKeTheoThang;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_ThongKeTheoThang extends RecyclerView.Adapter<Adapter_ThongKeTheoThang.RecyclerViewHolder> {
    private Context mContext;
    private List< ThongKeTheoThang > data = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_ThongKeTheoThang(Context mContext, List<ThongKeTheoThang> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_thongke_theothang, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtSTT.setText(data.get(position).getStt().toString());
        holder.txtThangNam.setText(data.get(position).getThangnam());
        DecimalFormat format2=new DecimalFormat("#,##0");
        Integer intSoCuon=data.get(position).getSocuon();
        holder.txtTongSoCuon.setText(format2.format(intSoCuon));

        DecimalFormat format1=new DecimalFormat("#,##0");
        Double dblThanhTien=data.get(position).getThanhtien();
        holder.txtThanhTien.setText(format1.format(dblThanhTien));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtSTT)
        TextView txtSTT;

        @BindView(R.id.txtThangNam)
        TextView txtThangNam;

        @BindView(R.id.txtTongSoCuon)
        TextView txtTongSoCuon;

        @BindView(R.id.txtThanhTien)
        TextView txtThanhTien;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}