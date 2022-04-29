package com.triminh.comicpro.adapter.thongke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.triminh.comicpro.model.thongke.ThongKeTheoTuaTruyen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_ThongKeTheoTuaTruyen extends RecyclerView.Adapter<Adapter_ThongKeTheoTuaTruyen.RecyclerViewHolder> {
    private Context mContext;
    private List<ThongKeTheoTuaTruyen> data = new ArrayList<>();
    private List< ThongKeTheoTuaTruyen > temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_ThongKeTheoTuaTruyen(Context mContext, List<ThongKeTheoTuaTruyen> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_thongke_tuatruyen, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtSTT.setText(data.get(position).getStt().toString());
        holder.txtTuaTruyen.setText(data.get(position).getTuatruyen());
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

        @BindView(R.id.txtTuaTruyen)
        TextView txtTuaTruyen;

        @BindView(R.id.txtSoCuon)
        TextView txtSoCuon;

        @BindView(R.id.txtThanhTien)
        TextView txtThanhTien;


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
            for (ThongKeTheoTuaTruyen thongKeTheoTuaTruyen : temp) {
                if (String.valueOf(thongKeTheoTuaTruyen.getTuatruyen()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(thongKeTheoTuaTruyen);
                }
            }
        }
        notifyDataSetChanged();
    }
}