package com.example.gametrack.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gametrack.R;
import com.example.gametrack.data.model.local.MetaLayout;

import java.util.List;

public class MetaAdapter extends RecyclerView.Adapter<MetaAdapter.MetaViewHolder> {
    private final List<MetaLayout> metas;

    public MetaAdapter(List<MetaLayout> metas) {
        this.metas = metas;
    }

    @NonNull
    @Override
    public MetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meta_layout, parent, false);
        return new MetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MetaViewHolder holder, int position) {
        MetaLayout meta = metas.get(position);
        holder.txtTitulo.setText(meta.getTitulo());
        holder.txtDescricao.setText(meta.getDescricao());

        Glide.with(holder.itemView.getContext())
                .load(meta.getIconeJogo())
                .into(holder.imgIconeJogo);
    }

    @Override
    public int getItemCount() {
        return metas.size();
    }

    static class MetaViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtDescricao;
        ImageView imgIconeJogo;

        public MetaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloMeta);
            txtDescricao = itemView.findViewById(R.id.txtDescricaoMeta);
            imgIconeJogo = itemView.findViewById(R.id.imgIconeJogo);
        }
    }
}
