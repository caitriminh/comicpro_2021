package com.triminh.comicpro.adapter.thongke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.triminh.comicpro.model.tentruyen.TenTruyen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_navTenTruyenDanhSach extends RecyclerView.Adapter<Adapter_navTenTruyenDanhSach.RecyclerViewHolder> {
    private Context mContext;
    private List<TenTruyen> data = new ArrayList<>();
    private List<TenTruyen> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_navTenTruyenDanhSach(Context mContext, List<TenTruyen> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_navtentruyen_danhsach, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtQuaTang.setText(data.get(position).getQuatang());
        holder.txtTenTruyen.setText(data.get(position).getTentruyen());
        holder.txtTap.setText(data.get(position).getTap() + "");
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        Double dblGiaBia = data.get(position).getGiabia();
        holder.txtGia.setText(decimalFormat.format(dblGiaBia));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtQuaTang)
        TextView txtQuaTang;

        @BindView(R.id.txtTenTruyen)
        TextView txtTenTruyen;

        @BindView(R.id.txtTap)
        TextView txtTap;

        @BindView(R.id.txtGia)
        TextView txtGia;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    public void filter(String keys) {
        data.clear();
        String charText = keys.toLowerCase(Locale.getDefault());
        if (charText.length() == 0) {
            data.addAll(temp);
        } else {
            for (TenTruyen tenTruyen : temp) {
                if (String.valueOf(tenTruyen.getTentruyen()).toLowerCase(Locale.getDefault()).contains(charText) || String.valueOf(tenTruyen.getTap()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(tenTruyen);
                }
            }
        }
        notifyDataSetChanged();
    }
}