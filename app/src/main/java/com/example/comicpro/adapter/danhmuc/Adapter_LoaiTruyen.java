package com.example.comicpro.adapter.danhmuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.example.comicpro.model.danhmuc.LoaiTruyen;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_LoaiTruyen extends RecyclerView.Adapter<Adapter_LoaiTruyen.RecyclerViewHolder> {

    private List< LoaiTruyen > data = new ArrayList<>();
    private Unbinder unbinder;
    public Adapter_LoaiTruyen(Context mContext, List<LoaiTruyen> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_loaitruyen, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaLoai.setText(data.get(position).getMaloai());
        holder.txtLoaiTruyen.setText(data.get(position).getLoaitruyen());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class RecyclerViewHolder extends  RecyclerView.ViewHolder {

        @BindView(R.id.txtMaLoai)
        TextView txtMaLoai;

        @BindView(R.id.txtLoaiTruyen)
        TextView txtLoaiTruyen;



        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}