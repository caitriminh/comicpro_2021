package com.example.comicpro.adapter.thongke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.example.comicpro.model.thongke.ThongKeTheoNXB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_ThongKeTheoNXB extends RecyclerView.Adapter<Adapter_ThongKeTheoNXB.RecyclerViewHolder> {
    private Context mContext;
    private List< ThongKeTheoNXB > data = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_ThongKeTheoNXB(Context mContext, List<ThongKeTheoNXB> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_thongke_nxb, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtSTT.setText(data.get(position).getStt().toString());
        holder.txtNhaXuatBan.setText(data.get(position).getNhaxuatban());
        holder.txtSoCuon.setText(data.get(position).getSocuon().toString());
        holder.txtThanhTien.setText(data.get(position).getThanhtien());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtSTT)
        TextView txtSTT;

        @BindView(R.id.txtNhaXuatBan)
        TextView txtNhaXuatBan;

        @BindView(R.id.txtSoCuon)
        TextView txtSoCuon;

        @BindView(R.id.txtThanhTien)
        TextView txtThanhTien;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}