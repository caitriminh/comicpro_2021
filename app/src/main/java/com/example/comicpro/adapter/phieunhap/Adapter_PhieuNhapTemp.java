package com.example.comicpro.adapter.phieunhap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.example.comicpro.model.phieuxuat.NhapXuatTemp;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_PhieuNhapTemp extends RecyclerView.Adapter < Adapter_PhieuNhapTemp.RecyclerViewHolder > {
    private Context mContext;
    private List < NhapXuatTemp > data = new ArrayList <>();
    private Unbinder unbinder;
    View view;

    public Adapter_PhieuNhapTemp(Context mContext, List < NhapXuatTemp > data) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_phieunhaptemp, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtTuaTruyen.setText(data.get(position).getTuatruyen());
        holder.txtTap.setText("Tập " + data.get(position).getTap());

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        holder.txtSL_DonGia.setText("SL: "+ data.get(position).getSoluong() + " - Giá: " + decimalFormat.format(data.get(position).getDongia()) + " đ");
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

        @BindView(R.id.txtSL_DonGia)
        TextView txtSL_DonGia;

        @BindView(R.id.imgView)
        ImageView imgView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }


}