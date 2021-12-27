package com.example.comicpro.adapter.thongke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.example.comicpro.model.thongke.ThongKeTheoNCC;
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

public class Adapter_TenTruyenNCC extends RecyclerView.Adapter < Adapter_TenTruyenNCC.RecyclerViewHolder > {
    private Context mContext;
    private List < ThongKeTheoNCC > data = new ArrayList <>();
    private List < ThongKeTheoNCC > temp = new ArrayList <>();
    private Unbinder unbinder;

    public Adapter_TenTruyenNCC(Context mContext, List < ThongKeTheoNCC > data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_ctphieunhap, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            holder.txtNgayNhap.setText("Ngày nhập: " + formatter.format(data.get(position).getNgaynhap()));
        } catch (Exception e) {
            holder.txtNgayNhap.setText("-");
        }

        holder.txtTuaTruyen.setText(data.get(position).getTuatruyen());
        holder.txtTap.setText("Tập " + data.get(position).getTap().toString());
        DecimalFormat format = new DecimalFormat("#,##0");
        Integer intSLNhap = data.get(position).getSlnhap();

        DecimalFormat format2 = new DecimalFormat("#,##0");
        Double dblDonGia = data.get(position).getDongia();

        holder.txtSL_DonGia.setText("SL: " + format.format(intSLNhap) + " - Giá: " + format2.format(dblDonGia) + " đ");

        String url = data.get(position).getHinh();
        Picasso.get()
                .load(url)
                .resize(195, 240)
                .error(R.drawable.no_image)//hien thi hinh mac dinh khi ko co hinh
                // .centerCrop()
                .placeholder(R.drawable.no_image)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtNgayNhap)
        TextView txtNgayNhap;

        @BindView(R.id.txtTuaTruyen)
        TextView txtTuaTruyen;

        @BindView(R.id.txtTap)
        TextView txtTap;

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
            for (ThongKeTheoNCC thongKeTheoNCC : temp) {
                if (String.valueOf(thongKeTheoNCC.getTuatruyen()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(thongKeTheoNCC.getTap()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(thongKeTheoNCC);
                }
            }
        }
        notifyDataSetChanged();
    }
}