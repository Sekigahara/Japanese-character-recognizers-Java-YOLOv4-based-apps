package com.example.yoloapps.modules.Camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yoloapps.R;
import com.example.yoloapps.model.SubResponse;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RecyclerViewCamera extends RecyclerView.Adapter<RecyclerViewCamera.MyViewHolder> {
    private static ArrayList<SubResponse> mDataset;
    private static RecyclerViewCamera.MyClickListener myClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvRv;
        TextView tv3;
        ImageView ivRv;

        public MyViewHolder(View itemView){
            super(itemView);
            tvRv = (TextView) itemView.findViewById(R.id.tvRV);
            tv3 = (TextView) itemView.findViewById(R.id.textView3);
            ivRv = (ImageView) itemView.findViewById(R.id.ivRV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public RecyclerViewCamera(ArrayList<SubResponse> mDataset){
        this.mDataset = mDataset;
    }

    public RecyclerViewCamera.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_camera, parent, false);
        RecyclerViewCamera.MyViewHolder myViewHolder = new RecyclerViewCamera.MyViewHolder(view);

        return myViewHolder;
    }

    public void onBindViewHolder(RecyclerViewCamera.MyViewHolder holder, int position){
        holder.tv3.setText("Detected : ");
        holder.tvRv.setText(mDataset.get(position).label);
        holder.ivRv.setImageBitmap(mDataset.get(position).image);
    }

    public int getItemCount(){
        return mDataset.size();
    }

    public void setOnItemClickListener(RecyclerViewCamera.MyClickListener myClickListener){
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener{
        public void onItemClick(int position, View view);
    }

}
