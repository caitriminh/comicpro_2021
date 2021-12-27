package com.example.comicpro.adapter.lichphathanh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicpro.R;

import com.example.comicpro.model.lichphathanh.LichPhatHanh;
import com.example.comicpro.system.ComicPro;
import com.example.comicpro.system.FTP_CMD;
import com.example.comicpro.system.IOnIntentReceived;
import com.example.comicpro.system.TM_Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Adapter_LichPhatHanh extends RecyclerView.Adapter<Adapter_LichPhatHanh.RecyclerViewHolder> implements IOnIntentReceived {
    private Context mContext;
    private List<LichPhatHanh> data = new ArrayList<>();
    private List< LichPhatHanh > temp = new ArrayList<>();
    private Unbinder unbinder;

    public static String image_manv = "";
    public LichPhatHanh lichPhatHanhs;




    public Adapter_LichPhatHanh(Context mContext, List<LichPhatHanh> data) {
        this.data = data;
        temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_item_lichphathanh, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtNgayThang.setText(data.get(position).getNgaythang().toString());
        holder.txtNhaXuatBan.setText(data.get(position).getNhaxuatban());

        //load hinh anh
        String url = data.get(position).getHinh();
        Picasso.get()
                .load(url)
                 .resize(195, 240)
                .error(R.drawable.no_image)//hien thi hinh mac dinh khi ko co hinh
                //  .centerCrop()
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.drawable.no_image)
                .into(holder.imgView);

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ComicPro.objLichPhatHanh = data.get(position);
//                Intent intent = new Intent(mContext, CTLichPhatHanhActivity.class);
//                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
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


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtNgayThang)
        TextView txtNgayThang;

        @BindView(R.id.txtNhaXuatBan)
        TextView txtNhaXuatBan;

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
            for (LichPhatHanh lichPhatHanh : temp) {
                if (String.valueOf(lichPhatHanh.getNgaythang()).toLowerCase(Locale.getDefault()).contains(charText) || String.valueOf(lichPhatHanh.getMalich()).toLowerCase(Locale.getDefault()).contains(charText) || String.valueOf(lichPhatHanh.getNhaxuatban()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(lichPhatHanh);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void uploadImage(Bitmap bitmap) {
        new Thread() {
            @Override
            public void run() {
                String dirname = "/httpdocs/img/lichphathanh";
                FTP_CMD.makeDir(dirname);
                String filename = dirname + "/" + ComicPro.objLichPhatHanh.getMalich() + ".jpg";
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