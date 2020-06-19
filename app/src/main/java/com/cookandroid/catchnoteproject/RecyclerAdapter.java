package com.cookandroid.catchnoteproject;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    private ArrayList<Data> listData = new ArrayList<>();
    private Context context;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    public RecyclerAdapter(){};

    public RecyclerAdapter(ArrayList<Data> arrayList, Context context) {
        this.listData = arrayList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수
        return listData.size();
    }

    void addItem(Data data) {
        listData.add(data);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void onBind(Data data) {
            textView1.setText(data.getTitle());
            textView2.setText(data.getContent());
            textView3.setText(data.getSpec());
            imageView.setImageResource(data.getResId());
        }
    }
}