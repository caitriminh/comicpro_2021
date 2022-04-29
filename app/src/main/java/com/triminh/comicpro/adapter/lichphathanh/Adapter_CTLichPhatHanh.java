package com.triminh.comicpro.adapter.lichphathanh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.triminh.comicpro.model.lichphathanh.CTLichPhatHanh;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_CTLichPhatHanh extends RecyclerView.Adapter<Adapter_CTLichPhatHanh.RecyclerViewHolder> {
    private Context mContext;
    private List< CTLichPhatHanh > data = new ArrayList<>();
    private List<CTLichPhatHanh> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_CTLichPhatHanh(Context mContext, List<CTLichPhatHanh> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_ct_lichphathanh, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtNgayThang.setText(data.get(position).getNgayphathanh());
        holder.txtTuaTruyen.setText(data.get(position).getTuatruyen());
        holder.txtGiaBia.setText(data.get(position).getGiabia().toString());
        Integer damua=data.get(position).getDamua();
        if(damua==1){
            holder.div_container3.setBackgroundColor(mContext.getResources().getColor(R.color.color_damua));
            holder.swcom.setChecked(true);
        }
        else{
            holder.div_container3.setBackgroundColor(mContext.getResources().getColor(R.color.color_chuamua));
            holder.swcom.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class RecyclerViewHolder extends  RecyclerView.ViewHolder {

        @BindView(R.id.txtNgayThang)
        TextView txtNgayThang;

        @BindView(R.id.txtTuaTruyen)
        TextView txtTuaTruyen;

        @BindView(R.id.txtGiaBia)
        TextView txtGiaBia;

        @BindView(R.id.div_container3)
        LinearLayout div_container3;

        @BindView(R.id.swcom)
        SwitchCompat swcom;

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
            for (CTLichPhatHanh ctLichPhatHanh : temp) {
                if ( String.valueOf(ctLichPhatHanh.getTuatruyen()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(ctLichPhatHanh);
                }
            }
        }
        notifyDataSetChanged();
    }
}