package com.triminh.comicpro.adapter.phieuxuat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.triminh.comicpro.model.phieuxuat.PhieuXuat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_PhieuXuat extends RecyclerView.Adapter < Adapter_PhieuXuat.RecyclerViewHolder > {
    private Context mContext;
    private List < PhieuXuat > data = new ArrayList <>();
    private List < PhieuXuat > temp = new ArrayList <>();

    private Unbinder unbinder;
    View view;

    public Adapter_PhieuXuat(Context mContext, List < PhieuXuat > data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_row_phieuxuat, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaPhieu.setText(data.get(position).getMaphieu());
        holder.txtDonVi.setText(data.get(position).getDonvi());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            holder.txtNgayNhap.setText(formatter.format(data.get(position).getNgaynhap()));
        } catch (Exception e) {

        }
        DecimalFormat format = new DecimalFormat("#,##0");
        Double dboThanhTien = data.get(position).getThanhtien();
        holder.txtTongThanhTien.setText(format.format(dboThanhTien));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaPhieu)
        TextView txtMaPhieu;

        @BindView(R.id.txtDonVi)
        TextView txtDonVi;

        @BindView(R.id.txtNgayNhap)
        TextView txtNgayNhap;

        @BindView(R.id.txtTongThanhTien)
        TextView txtTongThanhTien;

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
            for (PhieuXuat phieuXuat : temp) {
                if (String.valueOf(phieuXuat.getMaphieu()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(phieuXuat.getDonvi()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(phieuXuat.getDiengiai()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(phieuXuat.getNgaynhap()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(phieuXuat);
                }
            }
        }
        notifyDataSetChanged();
    }
}