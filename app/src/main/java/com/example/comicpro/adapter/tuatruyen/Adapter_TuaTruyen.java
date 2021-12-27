package com.example.comicpro.adapter.tuatruyen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.example.comicpro.model.tuatruyen.TuaTruyen;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_TuaTruyen extends RecyclerView.Adapter < Adapter_TuaTruyen.RecyclerViewHolder > {
    private Context mContext;
    private List < TuaTruyen > data = new ArrayList <>();
    private List < TuaTruyen > temp = new ArrayList <>();
    private Unbinder unbinder;


    View view;


    public Adapter_TuaTruyen(Context mContext, List < TuaTruyen > data) {
        this.data = data;
        this.mContext = mContext;
        this.temp.addAll(data);

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_tuatruyen, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtTuaTruyen.setText(data.get(position).getTuatruyen());
        holder.txtTacGia.setText(data.get(position).getTacgia());
        holder.txtSoTap.setText("Số tập: " + data.get(position).getSotap());
        //load hinh anh
        String url = data.get(position).getHinh();
        Picasso.get()
                .load(url)
                .resize(195, 240)
                .error(R.drawable.no_image)//hien thi hinh mac dinh khi ko co hinh
                //  .centerCrop()
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.no_image)
                .into(holder.imgView);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTuaTruyen)
        TextView txtTuaTruyen;

        @BindView(R.id.txtTacGia)
        TextView txtTacGia;

        @BindView(R.id.txtSoTap)
        TextView txtSoTap;

        @BindView(R.id.imgView)
        ImageView imgView;


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
            for (TuaTruyen tuaTruyen : temp) {
                if (String.valueOf(tuaTruyen.getMatua()).toLowerCase(Locale.getDefault()).contains(charText)
                        || String.valueOf(tuaTruyen.getTuatruyen()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(tuaTruyen);
                }
            }
        }
        notifyDataSetChanged();
    }
}