package com.example.comicpro.adapter.thongke;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;
import com.example.comicpro.model.tentruyen.TenTruyen;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_navTenTruyen extends RecyclerView.Adapter<Adapter_navTenTruyen.RecyclerViewHolder> {
    private Context mContext;
    private List<TenTruyen> data = new ArrayList<>();
    private List<TenTruyen> temp = new ArrayList<>();
    private Unbinder unbinder;

    View view;


    public Adapter_navTenTruyen(Context mContext, List<TenTruyen> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_navitem_tentruyen, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        try {
            holder.txtNgayXuatBan.setText("Ngày xuất bản: "+formatter.format(data.get(position).getNgayxuatban()));
        } catch (Exception e) {
            holder.txtNgayXuatBan.setText("Ngày xuất bản: (không xác định)");
        }
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
            }
            else{
                holder.txtTap.setText("Tập " + data.get(position).getTap() + " (" + quatang + ")");
            }
        }
        holder.txtGiaBia.setText("Giá bìa: " + decimalFormat.format(data.get(position).getGiabia()));
//        //load hinh anh
        String url = data.get(position).getHinh();
        String sohuu = data.get(position).getSohuu();

        Picasso.get()
                .load(url)
                .resize(195, 240)
                .error(R.drawable.no_image)//hien thi hinh mac dinh khi ko co hinh
                // .centerCrop()
                .placeholder(R.drawable.no_image)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.imgView);

        if (sohuu.equals("NO")) {
            setBW(holder.imgView);
        } else {
            holder.imgView.setColorFilter(null);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.txtNgayXuatBan)
        TextView txtNgayXuatBan;

        @BindView(R.id.txtTap)
        TextView txtTap;

        @BindView(R.id.txtGiaBia)
        TextView txtGiaBia;

        @BindView(R.id.imgView)
        ImageView imgView;

        @BindView(R.id.cardview_id)
        CardView cardview_id;

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
            for (TenTruyen tenTruyen : temp) {
                if (String.valueOf(tenTruyen.getTentruyen()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(tenTruyen.getTap()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(tenTruyen);
                }
            }
        }
        notifyDataSetChanged();
    }

    //Set màu trắng đen cho Image View
    private void setBW(ImageView iv) {
        float brightness = 10; // change values to suite your need
        float[] colorMatrix = {
                0.33f, 0.33f, 0.33f, 0, brightness,
                0.33f, 0.33f, 0.33f, 0, brightness,
                0.33f, 0.33f, 0.33f, 0, brightness,
                0, 0, 0, 1, 0
        };
        ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        iv.setColorFilter(colorFilter);
    }
}