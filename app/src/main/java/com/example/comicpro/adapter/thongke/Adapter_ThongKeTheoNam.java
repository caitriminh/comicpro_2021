package com.example.comicpro.adapter.thongke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.example.comicpro.model.thongke.ThongKeTheoNam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_ThongKeTheoNam extends RecyclerView.Adapter < Adapter_ThongKeTheoNam.RecyclerViewHolder > {
    private Context mContext;
    private List < ThongKeTheoNam > data = new ArrayList <>();
    private Unbinder unbinder;

    public Adapter_ThongKeTheoNam(Context mContext, List < ThongKeTheoNam > data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_thongke_theonam, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtSTT.setText(data.get(position).getStt().toString());


        holder.txtNam.setText(data.get(position).getNam().toString());

        DecimalFormat format1 = new DecimalFormat("#,##0");
        Integer intSoCuon = data.get(position).getSocuon();
        holder.txtSoCuon.setText(format1.format(intSoCuon));


        DecimalFormat format2 = new DecimalFormat("#,##0");
        Double dblThanhTien = data.get(position).getThanhtien();
        holder.txtThanhTien.setText(format2.format(dblThanhTien));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtSTT)
        TextView txtSTT;

        @BindView(R.id.txtNam)
        TextView txtNam;

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