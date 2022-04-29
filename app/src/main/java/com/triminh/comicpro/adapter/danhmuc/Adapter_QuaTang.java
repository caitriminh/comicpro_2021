package com.triminh.comicpro.adapter.danhmuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.triminh.comicpro.model.danhmuc.QuaTang;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_QuaTang extends RecyclerView.Adapter<Adapter_QuaTang.RecyclerViewHolder> {
    private Context mContext;
    private List< QuaTang > data = new ArrayList<>();
    private Unbinder unbinder;
    public Adapter_QuaTang(Context mContext, List<QuaTang> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_quatang, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaQuaTang.setText(data.get(position).getMaquatang().toString());
        holder.txtQuaTang.setText(data.get(position).getQuatang());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class RecyclerViewHolder extends  RecyclerView.ViewHolder {

        @BindView(R.id.txtMaQuaTang)
        TextView txtMaQuaTang;

        @BindView(R.id.txtQuaTang)
        TextView txtQuaTang;



        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}