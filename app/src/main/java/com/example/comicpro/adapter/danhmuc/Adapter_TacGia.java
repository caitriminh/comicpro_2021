package com.example.comicpro.adapter.danhmuc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.comicpro.R;
import com.example.comicpro.model.danhmuc.TacGia;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_TacGia extends RecyclerView.Adapter<Adapter_TacGia.RecyclerViewHolder> {
    private Context mContext;
    private List< TacGia > data = new ArrayList<>();
    private List<TacGia> temp = new ArrayList<>();
    private Unbinder unbinder;
    public Adapter_TacGia(Context mContext, List<TacGia> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_tacgia, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtMaTacGia.setText(data.get(position).getMatacgia());
        holder.txtTenTacGia.setText(data.get(position).getTacgia());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public class RecyclerViewHolder extends  RecyclerView.ViewHolder {

        @BindView(R.id.txtMaTacGia)
        TextView txtMaTacGia;

        @BindView(R.id.txtTenTacGia)
        TextView txtTenTacGia;

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
            for (TacGia tacGia : temp) {
                if ( String.valueOf(tacGia.getTacgia()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(tacGia);
                }
            }
        }
        notifyDataSetChanged();
    }
}