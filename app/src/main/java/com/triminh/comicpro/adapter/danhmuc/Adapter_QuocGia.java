package com.triminh.comicpro.adapter.danhmuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.triminh.comicpro.model.danhmuc.QuocGia;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_QuocGia extends RecyclerView.Adapter<Adapter_QuocGia.RecyclerViewHolder> {
    private Context mContext;
    private List< QuocGia > data = new ArrayList<>();
    private Unbinder unbinder;
    public Adapter_QuocGia(Context mContext, List<QuocGia> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_xuatxu, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtQuocGia.setText(data.get(position).getQuocgia());
        //load hinh anh
        String url = data.get(position).getHinh();
        Picasso.get()
                .load(url)
                .resize(195, 135)
                .error(R.drawable.no_image)//hien thi hinh mac dinh khi ko co hinh
                //.centerCrop()
                .placeholder(R.drawable.no_image)
                .into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends  RecyclerView.ViewHolder {


        @BindView(R.id.txtQuocGia)
        TextView txtQuocGia;

        @BindView(R.id.imgView)
        ImageView imgView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}