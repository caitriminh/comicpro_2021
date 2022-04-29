package com.triminh.comicpro.adapter.danhmuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.triminh.comicpro.model.danhmuc.NhaXuatBan;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_NhaXuatBan extends RecyclerView.Adapter<Adapter_NhaXuatBan.RecyclerViewHolder>  {
    private List< NhaXuatBan > data = new ArrayList<>();
    private List<NhaXuatBan> temp = new ArrayList<>();
    private Unbinder unbinder;

    View view;


    public Adapter_NhaXuatBan(Context mContext, List<NhaXuatBan> data) {
        this.data = data;
        this.temp.addAll(data);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_nhaxuatban, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtNXB.setText(data.get(position).getNhaxuatban());
        holder.txtDiaChi.setText(data.get(position).getDiachi());
        holder.txtSoDT.setText(data.get(position).getSodt());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtNXB)
        TextView txtNXB;

        @BindView(R.id.txtDiaChi)
        TextView txtDiaChi;

        @BindView(R.id.txtSoDT)
        TextView txtSoDT;

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
            for (NhaXuatBan nhaXuatBan : temp) {
                if (String.valueOf(nhaXuatBan.getNhaxuatban()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(nhaXuatBan);
                }
            }
        }
        notifyDataSetChanged();
    }
}