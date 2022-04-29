package com.triminh.comicpro.adapter.phieuxuat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.triminh.comicpro.model.phieuxuat.ChiTietPhieuXuat;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_ChiTietPhieuXuat extends RecyclerView.Adapter<Adapter_ChiTietPhieuXuat.RecyclerViewHolder> {
    private Context mContext;
    private List<ChiTietPhieuXuat> data = new ArrayList<>();
    private List<ChiTietPhieuXuat> temp = new ArrayList<>();
    private Unbinder unbinder;
    View view;

    public Adapter_ChiTietPhieuXuat(Context mContext, List<ChiTietPhieuXuat> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_ctphieuxuat, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtTuaTruyen.setText(data.get(position).getTuatruyen());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            holder.txtNgayNhap.setText("Ngày xuất: " + formatDate.format(data.get(position).getNgaynhap()));
        } catch (Exception e) {

        }
        DecimalFormat format1 = new DecimalFormat("#,##0");
        Integer intTap = data.get(position).getTap();

        holder.txtTap.setText("Tập " + format1.format(intTap));

        DecimalFormat format = new DecimalFormat("#,##0");
        Double dblDonGia = data.get(position).getDongia();

        DecimalFormat format2 = new DecimalFormat("#,##0");
        Integer intSoLuong = data.get(position).getSlxuat();

        holder.txtSL_DonGia.setText("SL: " + format2.format(intSoLuong) + " - Giá: " + format.format(dblDonGia));

//        //load hinh anh
        String url = data.get(position).getHinh();
        Picasso.get()
                .load(url)
                .resize(195, 240)
                .error(R.drawable.no_image)//hien thi hinh mac dinh khi ko co hinh
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

        @BindView(R.id.txtTap)
        TextView txtTap;

        @BindView(R.id.txtNgayNhap)
        TextView txtNgayNhap;

        @BindView(R.id.txtSL_DonGia)
        TextView txtSL_DonGia;

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
            for (ChiTietPhieuXuat chiTietPhieuXuat : temp) {
                if (String.valueOf(chiTietPhieuXuat.getTentruyen()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(chiTietPhieuXuat.getTuatruyen()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(chiTietPhieuXuat.getNgaynhap()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(chiTietPhieuXuat);
                }
            }
        }
        notifyDataSetChanged();
    }
}