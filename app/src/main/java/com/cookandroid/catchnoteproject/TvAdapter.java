package com.cookandroid.catchnoteproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder>  {
    private ArrayList<Tv> arrayList;
    private Context context;


    public TvAdapter(ArrayList<Tv> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }




    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tv,parent,false);
        TvViewHolder holder = new TvViewHolder(view);
        
        
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {

        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);

        holder.id.setText(arrayList.get(position).getId());         //리스트에 디비넣어온다음 뿌려주는역할
        holder.money.setText(String.valueOf(arrayList.get(position).getMoney()));         //리스트에 디비넣어온다음 뿌려주는역할
        holder.spec.setText(arrayList.get(position).getSpec());         //리스트에 디비넣어온다음 뿌려주는역할


    }

    @Override
    public int getItemCount()  {
        return (arrayList!=null?arrayList.size():0);
    }


    public class TvViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView id;
        TextView money;
        TextView spec;


        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.id= itemView.findViewById(R.id.tv_id);
            this.money=itemView.findViewById(R.id.tv_pw);
            this.spec=itemView.findViewById(R.id.tv_Username);
        }
    }
}
