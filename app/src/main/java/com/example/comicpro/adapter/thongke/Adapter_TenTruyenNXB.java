package com.example.comicpro.adapter.thongke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.example.comicpro.model.tentruyen.ChiTietTenTruyen_TheoNXB;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_TenTruyenNXB extends RecyclerView.Adapter < Adapter_TenTruyenNXB.RecyclerViewHolder > {
    private Context mContext;
    private List < ChiTietTenTruyen_TheoNXB > data = new ArrayList <>();
    private List < ChiTietTenTruyen_TheoNXB > temp = new ArrayList <>();
    private Unbinder unbinder;

    public Adapter_TenTruyenNXB(Context mContext, List < ChiTietTenTruyen_TheoNXB > data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_thongke_tentruyen_nxb, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtNgayNhap.setText(data.get(position).getNgaynhap().toString());
        holder.txtTuaTruyen.setText(data.get(position).getTuatruyen().toString());
        holder.txtTap.setText(data.get(position).getTap().toString());
        holder.txtGia.setText(data.get(position).getDongia().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtNgayNhap)
        TextView txtNgayNhap;

        @BindView(R.id.txtTuaTruyen)
        TextView txtTuaTruyen;

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
            for (ChiTietTenTruyen_TheoNXB chiTietTenTruyen_theoNXB : temp) {
                if (String.valueOf(chiTietTenTruyen_theoNXB.getTuatruyen()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(chiTietTenTruyen_theoNXB.getTap()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(chiTietTenTruyen_theoNXB);
                }
            }
        }
        notifyDataSetChanged();
    }
}