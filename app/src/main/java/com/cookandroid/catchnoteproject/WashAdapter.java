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

public class WashAdapter extends RecyclerView.Adapter<WashAdapter.WashViewHolder> {

    private ArrayList<WashingMachine> arrayList;
    private Context context;

    public WashAdapter(ArrayList<WashingMachine> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public WashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_washing_machine,parent,false);
        WashViewHolder holder = new WashViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WashViewHolder holder, int position) {

        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);

        holder.id.setText(arrayList.get(position).getId());         //리스트에 디비넣어온다음 뿌려주는역할
        holder.money.setText(String.valueOf(arrayList.get(position).getMoney()));         //리스트에 디비넣어온다음 뿌려주는역할
        holder.spec.setText(arrayList.get(position).getSpec());         //리스트에 디비넣어온다음 뿌려주는역할




    }

    @Override
    public int getItemCount() {
        return (arrayList!=null?arrayList.size():0);
    }


    public class WashViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_profile;
        TextView id;
        TextView money;
        TextView spec;


        public WashViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.id= itemView.findViewById(R.id.tv_id);
            this.money=itemView.findViewById(R.id.tv_pw);
            this.spec=itemView.findViewById(R.id.tv_Username);

        }
    }
}
