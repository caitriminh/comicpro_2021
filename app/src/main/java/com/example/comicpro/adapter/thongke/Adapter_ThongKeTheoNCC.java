package com.example.comicpro.adapter.thongke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.example.comicpro.model.thongke.ThongKeTheoNCC;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_ThongKeTheoNCC extends RecyclerView.Adapter< Adapter_ThongKeTheoNCC.RecyclerViewHolder> {
    private Context mContext;
    private List< ThongKeTheoNCC > data = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_ThongKeTheoNCC(Context mContext, List<ThongKeTheoNCC> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_thongke_theoncc, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtSTT.setText(data.get(position).getStt().toString());
        holder.txtDonVi.setText(data.get(position).getDonvi());

        DecimalFormat format1=new DecimalFormat("#,##0");
        Integer intSLNhap=data.get(position).getSlnhap();
        holder.txtSoLuong.setText(format1.format(intSLNhap));

        DecimalFormat format2=new DecimalFormat("#,##0");
        Double dblThanhTien=data.get(position).getThanhtien();
        holder.txtThanhTien.setText(format2.format(dblThanhTien));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtSTT)
        TextView txtSTT;

        @BindView(R.id.txtDonVi)
        TextView txtDonVi;

        @BindView(R.id.txtSoLuong)
        TextView txtSoLuong;

        @BindView(R.id.txtThanhTien)
        TextView txtThanhTien;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}