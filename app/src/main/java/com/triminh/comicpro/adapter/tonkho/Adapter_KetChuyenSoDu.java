package com.triminh.comicpro.adapter.tonkho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.triminh.comicpro.model.tonkho.KetChuyenSoDu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_KetChuyenSoDu extends RecyclerView.Adapter<Adapter_KetChuyenSoDu.RecyclerViewHolder> {
    private Context mContext;
    private List< KetChuyenSoDu > data = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_KetChuyenSoDu(Context mContext, List<KetChuyenSoDu> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_ketchuyensodu, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtKy.setText(data.get(position).getKy2());
        holder.txtSoDuDauKy.setText(data.get(position).getSodauky().toString());
        holder.txtNhap.setText(data.get(position).getSlnhap().toString());
        holder.txtXuat.setText(data.get(position).getSlxuat().toString());
        holder.txtSoDuCuoiKy.setText(data.get(position).getSlton().toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtKy)
        TextView txtKy;

        @BindView(R.id.txtSoDuDauKy)
        TextView txtSoDuDauKy;

        @BindView(R.id.txtNhap)
        TextView txtNhap;

        @BindView(R.id.txtXuat)
        TextView txtXuat;

        @BindView(R.id.txtSoDuCuoiKy)
        TextView txtSoDuCuoiKy;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}