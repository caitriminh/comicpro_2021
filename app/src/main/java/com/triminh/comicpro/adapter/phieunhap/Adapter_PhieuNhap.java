package com.triminh.comicpro.adapter.phieunhap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.triminh.comicpro.model.phieunhap.PhieuNhap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_PhieuNhap extends RecyclerView.Adapter<Adapter_PhieuNhap.RecyclerViewHolder> {
    private Context mContext;
    private List< PhieuNhap > data = new ArrayList<>();
    private List<PhieuNhap> temp = new ArrayList<>();

    private Unbinder unbinder;
    View view;

    public Adapter_PhieuNhap(Context mContext, List<PhieuNhap> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_row_phieunhap, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaPhieu.setText(data.get(position).getMaphieu());
        holder.txtDonVi.setText(data.get(position).getDonvi());
        holder.txtNgayNhap.setText(data.get(position).getNgaynhap());
        holder.txtTongThanhTien.setText(data.get(position).getTongtien());
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
            for (PhieuNhap phieuNhap : temp) {
                if (String.valueOf(phieuNhap.getMaphieu()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(phieuNhap.getDonvi()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(phieuNhap.getDiengiai()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(phieuNhap.getNgaynhap()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(phieuNhap);
                }
            }
        }
        notifyDataSetChanged();
    }
}