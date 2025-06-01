package com.example.gametrack.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gametrack.R;
import com.example.gametrack.data.model.local.JogoLayout;

import java.util.List;

public class JogoAdapter extends RecyclerView.Adapter<JogoAdapter.ItemViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(JogoLayout jogo);
    }

    private final List<JogoLayout> jogoList;
    private final Context context;
    private final OnItemClickListener listener;

    public JogoAdapter(List<JogoLayout> jogoList, Context context, OnItemClickListener listener) {
        this.jogoList = jogoList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.jogo_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        JogoLayout item = jogoList.get(position);
        Glide.with(context)
                .load(item.getImagemUrl() + "/library_600x900_2x.jpg")
                .error(item.getImagemUrl() + "/header.jpg")
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jogoList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }
}
