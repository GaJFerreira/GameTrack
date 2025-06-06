package com.example.gametrack.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gametrack.R;
import com.example.gametrack.data.model.local.DiaLayout;

import java.util.List;

public class DiaAdapter extends RecyclerView.Adapter<DiaAdapter.DiaViewHolder> {
    private final List<DiaLayout> dias;

    public DiaAdapter(List<DiaLayout> dias) {
        this.dias = dias;
    }

    @NonNull
    @Override
    public DiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dia_layout, parent, false);
        return new DiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaViewHolder holder, int position) {
        DiaLayout dia = dias.get(position);
        holder.txtData.setText(dia.getData());

        MetaAdapter metaAdapter = new MetaAdapter(dia.getMetas());
        holder.recyclerMetas.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerMetas.setAdapter(metaAdapter);
    }

    @Override
    public int getItemCount() {
        return dias.size();
    }

    static class DiaViewHolder extends RecyclerView.ViewHolder {
        TextView txtData;
        RecyclerView recyclerMetas;

        public DiaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtData = itemView.findViewById(R.id.txtData);
            recyclerMetas = itemView.findViewById(R.id.recyclerMetas);
        }
    }
}

