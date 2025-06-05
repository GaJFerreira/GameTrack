package com.example.gametrack.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gametrack.R;
import com.example.gametrack.data.model.local.Meta;
import java.util.ArrayList;
import java.util.List;

public class MetaAdapter extends RecyclerView.Adapter<MetaAdapter.MetaViewHolder> {

    private List<Meta> metas = new ArrayList<>();
    private Context context; // Para obter cores

    public MetaAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meta_layout, parent, false);
        return new MetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MetaViewHolder holder, int position) {
        Meta metaAtual = metas.get(position);
        holder.bind(metaAtual);
    }

    @Override
    public int getItemCount() {
        return metas.size();
    }

    public void setMetas(List<Meta> novasMetas) {
        this.metas = novasMetas;
        notifyDataSetChanged(); // Ou usar DiffUtil para melhor performance
    }

    class MetaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMetaTipo;
        TextView textViewMetaStatus;
        TextView textViewMetaValor;
        TextView textViewMetaPrioridade;
        TextView textViewMetaObservacao;
        TextView textViewDataLimite;

        MetaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMetaTipo = itemView.findViewById(R.id.textViewMetaTipo);
            textViewMetaStatus = itemView.findViewById(R.id.textViewMetaStatus);
            textViewMetaValor = itemView.findViewById(R.id.textViewMetaValor);
            textViewMetaPrioridade = itemView.findViewById(R.id.textViewMetaPrioridade);
            textViewMetaObservacao = itemView.findViewById(R.id.textViewMetaObservacao);
            textViewDataLimite = itemView.findViewById(R.id.textViewDataLimite);
        }

        void bind(Meta meta) {
            textViewMetaTipo.setText(meta.getTipo() != null ? meta.getTipo().getDescricao() : "Tipo não definido");

            if (meta.getStatus() != null) {
                textViewMetaStatus.setText(meta.getStatus().getDescricao());
                // Aplicar cor de fundo dinamicamente baseada no status
                GradientDrawable background = (GradientDrawable) textViewMetaStatus.getBackground();
                if (background != null) { // Evita crash se o drawable não for um ShapeDrawable/GradientDrawable
                    switch (meta.getStatus()) {
                        case EM_ANDAMENTO:
                            background.setColor(ContextCompat.getColor(context, R.color.status_em_andamento_color)); // Defina essas cores em colors.xml
                            break;
                        case CONCLUIDO:
                            background.setColor(ContextCompat.getColor(context, R.color.status_concluido_color));
                            break;
                        case EXPIRADO:
                            background.setColor(ContextCompat.getColor(context, R.color.status_expirado_color));
                            break;
                    }
                } else { // Fallback se o drawable não for o esperado, ou crie drawables separados
                    // Ex: textViewMetaStatus.setBackgroundResource(R.drawable.background_status_em_andamento)
                }
            } else {
                textViewMetaStatus.setText("Status N/D");
            }


            textViewMetaValor.setText("Meta: " + meta.getValorMeta() + " | Progresso: " + meta.getProgressoAtual());
            textViewMetaPrioridade.setText("Prioridade: " + (meta.getPrioridade() != null ? meta.getPrioridade().getDescricao() : "N/D"));

            if (meta.getObservacao() != null && !meta.getObservacao().isEmpty()) {
                textViewMetaObservacao.setText("Obs: " + meta.getObservacao());
                textViewMetaObservacao.setVisibility(View.VISIBLE);
            } else {
                textViewMetaObservacao.setVisibility(View.GONE);
            }

            if (meta.getDataLimite() != null && !meta.getDataLimite().isEmpty()) {
                textViewDataLimite.setText("Limite: " + meta.getDataLimite());
                textViewDataLimite.setVisibility(View.VISIBLE);
            } else {
                textViewDataLimite.setVisibility(View.GONE);
            }
        }
    }
}