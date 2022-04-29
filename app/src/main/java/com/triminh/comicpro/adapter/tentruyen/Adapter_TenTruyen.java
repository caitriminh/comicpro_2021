package com.triminh.comicpro.adapter.tentruyen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicpro.R;

import com.triminh.comicpro.model.tentruyen.TenTruyen;

import com.triminh.comicpro.system.ComicPro;
import com.triminh.comicpro.system.FTP_CMD;
import com.triminh.comicpro.system.IOnIntentReceived;
import com.triminh.comicpro.system.TM_Toast;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Adapter_TenTruyen extends RecyclerView.Adapter<Adapter_TenTruyen.RecyclerViewHolder> implements IOnIntentReceived {
    private Context mContext;
    private List<TenTruyen> data = new ArrayList<>();
    private List<TenTruyen> temp = new ArrayList<>();
    private Unbinder unbinder;
    public static String image_manv = "";
    View view;

    public Adapter_TenTruyen(Context mContext, List<TenTruyen> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_tentruyen, parent, false);

        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        try {
            holder.txtNgayXuatBan.setText("Ngảy xuất bản: " +formatter.format(data.get(position).getNgayxuatban()));
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
            } else {
                holder.txtTap.setText("Tập " + data.get(position).getTap() + " (" + quatang + ")");
            }

        }

        holder.txtGiaBia.setText("Giá bìa: " + decimalFormat.format(data.get(position).getGiabia()));
        //load hinh anh
        String url = data.get(position).getHinh();
        String sohuu = data.get(position).getSohuu();
        Picasso.get()
                .load(url)
                .error(R.drawable.no_image)//hien thi hinh mac dinh khi ko co hinh
                .resize(195, 240)
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
                if (String.valueOf(tenTruyen.getTentruyen()).toLowerCase(Locale.getDefault()).contains(charText) || String.valueOf(tenTruyen.getTap()).toLowerCase(Locale.getDefault()).contains(charText)) {
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


    @Override
    public void onIntent(Intent data, int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            if (!image_manv.isEmpty()) {
                File fdelete = new File(image_manv);
                fdelete.deleteOnExit();
            }
            Uri uri = data.getParcelableExtra("path");
            try {
                final Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
                try {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        public void run() {
                            uploadImage(bitmap);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(Bitmap bitmap) {
        new Thread() {
            @Override
            public void run() {
                String dirname = "/httpdocs/img/thumb/" + ComicPro.objTenTruyen.getMatua();
                FTP_CMD.makeDir(dirname);
                String filename = dirname + "/" + ComicPro.objTenTruyen.getMatruyen() + ".jpg";
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                boolean success = FTP_CMD.uploadFile(filename, byteArray);
                if (success) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        public void run() {
                            TM_Toast.makeText(mContext, "Upload hình thành công!", Toast.LENGTH_LONG, TM_Toast.SUCCESS, false).show();
                            notifyDataSetChanged();
                        }
                    });

                } else {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        public void run() {
                            TM_Toast.makeText(mContext, "Upload hình thất bại!", Toast.LENGTH_LONG, TM_Toast.ERROR, false).show();
                        }
                    });
                }
            }

        }.start();

    }
}