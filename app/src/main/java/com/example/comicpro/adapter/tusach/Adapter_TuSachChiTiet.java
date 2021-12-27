package com.example.comicpro.adapter.tusach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;
import com.example.comicpro.model.tusach.TuSachChiTiet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_TuSachChiTiet extends RecyclerView.Adapter<Adapter_TuSachChiTiet.RecyclerViewHolder> {
    private Context mContext;
    private List<TuSachChiTiet> data = new ArrayList<>();
    private List<TuSachChiTiet> temp = new ArrayList<>();
    private Unbinder unbinder;

    public Adapter_TuSachChiTiet(Context mContext, List<TuSachChiTiet> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_tusachchitiet, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtTuaTruyen.setText(data.get(position).getTuatruyen());
        holder.txtTuSach.setText(data.get(position).getTusach());
        holder.txtSoTap.setText("Số tập: " + data.get(position).getSotap());
        String url = data.get(position).getHinh();
        Picasso.get()
                .load(url)
                .resize(195, 240)
                .error(R.drawable.no_image)//hien thi hinh mac dinh khi ko co hinh
                //.centerCrop()
                .placeholder(R.drawable.no_image)
                .into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtTuSach)
        TextView txtTuSach;

        @BindView(R.id.txtTuaTruyen)
        TextView txtTuaTruyen;

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
            for (TuSachChiTiet tuSachChiTiet : temp) {
                if (String.valueOf(tuSachChiTiet.getTusach()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(tuSachChiTiet.getTuatruyen()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(tuSachChiTiet);
                }
            }
        }
        notifyDataSetChanged();
    }
}