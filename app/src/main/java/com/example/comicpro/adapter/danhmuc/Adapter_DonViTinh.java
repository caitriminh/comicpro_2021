package com.example.comicpro.adapter.danhmuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.example.comicpro.model.danhmuc.DonViTinh;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_DonViTinh extends RecyclerView.Adapter<Adapter_DonViTinh.RecyclerViewHolder> {
    private Context mContext;
    private List< DonViTinh > data = new ArrayList<>();
    private Unbinder unbinder;
    public Adapter_DonViTinh(Context mContext, List<DonViTinh> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_donvitinh, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtId.setText(data.get(position).getId().toString());
        holder.txtDonViTinh.setText(data.get(position).getDonvitinh());
        holder.txtNguoiTao.setText(data.get(position).getNguoitd());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends  RecyclerView.ViewHolder {

        @BindView(R.id.txtId)
        TextView txtId;

        @BindView(R.id.txtDonViTinh)
        TextView txtDonViTinh;

        @BindView(R.id.txtNguoiTao)
        TextView txtNguoiTao;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}