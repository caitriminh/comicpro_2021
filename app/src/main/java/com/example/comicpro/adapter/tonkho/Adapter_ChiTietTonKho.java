package com.example.comicpro.adapter.tonkho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.example.comicpro.model.tonkho.ChiTietTonKho;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_ChiTietTonKho extends RecyclerView.Adapter<Adapter_ChiTietTonKho.RecyclerViewHolder> {
    private Context mContext;
    private List<ChiTietTonKho> data = new ArrayList<>();
    private List<ChiTietTonKho> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_ChiTietTonKho(Context mContext, List<ChiTietTonKho> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_chitiet_tonkho, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String quatang = data.get(position).getQuatang();
        if (quatang.equals("-")) {
            if (data.get(position).getSotap() == data.get(position).getTap()) {
                holder.txtTap.setText("Tập " + data.get(position).getTap() + " (HẾT)");
            } else {
                holder.txtTap.setText("Tập " + data.get(position).getTap());
            }
        } else {
            if (data.get(position).getSotap() == data.get(position).getTap()) {
                holder.txtTap.setText("Tập " + data.get(position).getTap() + " (HẾT) (" + quatang + ")");
            } else {
                holder.txtTap.setText("Tập " + data.get(position).getTap() + " (" + quatang + ")");
            }
        }
        holder.txtSLTon.setText("Số lượng: " + data.get(position).getSoducuoiky());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            holder.txtNgayXuatBan.setText("Ngày xuất bản: "+ formatter.format(data.get(position).getNgayxuatban()));
        } catch (Exception e) {
            holder.txtNgayXuatBan.setText("Ngày xuất bản: (không xác định)");
        }
        //load hinh anh
        String url = data.get(position).getHinh();
        Picasso.get()
                .load(url)
                .resize(195, 240)
                .error(R.drawable.no_image)//hien thi hinh mac dinh khi ko co hinh
                // .centerCrop()
                .placeholder(R.drawable.no_image)
                .into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTap)
        TextView txtTap;

        @BindView(R.id.txtNgayXuatBan)
        TextView txtNgayXuatBan;

        @BindView(R.id.txtSLTon)
        TextView txtSLTon;

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
            for (ChiTietTonKho chiTietTonKho : temp) {
                if (String.valueOf(chiTietTonKho.getTuatruyen()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(chiTietTonKho.getTentruyen()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(chiTietTonKho);
                }
            }
        }
        notifyDataSetChanged();
    }
}