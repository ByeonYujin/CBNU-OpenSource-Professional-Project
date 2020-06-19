package com.cookandroid.catchnoteproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.KeepListViewHolder>  {

    private ArrayList<WishList_Item> arrayList;
    private Context context;
    public Integer listsize;

    public WishListAdapter(ArrayList<WishList_Item> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        listsize = arrayList.size();
    }

    @NonNull
    @Override
    public KeepListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tv,parent,false);
        KeepListViewHolder holder = new KeepListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull KeepListViewHolder holder, int position) {

        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.iv_profile);

        //숫자 돈으로 바꾸기
        /*DecimalFormat myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(arrayList.get(position).getMoney());

        holder.id.setText(arrayList.get(position).getId());         //리스트에 디비넣어온다음 뿌려주는역할
        //holder.money.setText(String.valueOf(arrayList.get(position).getMoney())+'원');         //리스트에 디비넣어온다음 뿌려주는역할
        holder.money.setText(formattedStringPrice+'원');
        holder.spec.setText(arrayList.get(position).getSpec());         //리스트에 디비넣어온다음 뿌려주는역할*/

        holder.onBind(arrayList.get(position));
    }

    @Override
    public int getItemCount()  {
        return (arrayList!=null?arrayList.size():0);
    }


    public class KeepListViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView id;
        TextView money;
        TextView spec;


        public KeepListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.id= itemView.findViewById(R.id.tv_id);
            this.money=itemView.findViewById(R.id.tv_pw);
            this.spec=itemView.findViewById(R.id.tv_Username);
        }

        void onBind(WishList_Item keepItem) {
            money.setText(keepItem.getMoney());
            id.setText(keepItem.getId());
            spec.setText(keepItem.getSpec());
        }
    }
}

