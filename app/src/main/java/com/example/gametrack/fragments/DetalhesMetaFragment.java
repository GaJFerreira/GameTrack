package com.example.gametrack.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gametrack.App;
import com.example.gametrack.R;
import com.example.gametrack.data.model.local.Meta;
import com.example.gametrack.data.repository.MetaRepository;

public class DetalhesMetaFragment extends Fragment {
    private static final String TAG = "DetalhesMetaFragment";
    private static final String ARG_META_ID = "idMeta";

    public static DetalhesMetaFragment newInstance(long metaId) {
        DetalhesMetaFragment fragment = new DetalhesMetaFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_META_ID, metaId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalhes_meta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        long metaId = getArguments() != null ? getArguments().getLong(ARG_META_ID) : -1;

        if (metaId != -1) {
            Log.d(TAG, "Buscando meta com ID: " + metaId);
            MetaRepository metaRepository = new MetaRepository(App.getContext());
            Meta meta = metaRepository.buscarMetaPorId(metaId);

            if (meta != null) {
                Log.d(TAG, "Meta encontrada: jogo=" + meta.getJogo().getTitulo());

                ((TextView) view.findViewById(R.id.textTituloJogo)).setText(meta.getJogo().getTitulo());
                ((TextView) view.findViewById(R.id.textTipo)).setText("Tipo: " + meta.getTipo().name());

                TextView valorView = view.findViewById(R.id.textValor);
                if ("TEMPO".equalsIgnoreCase(meta.getTipo().name())) {
                    valorView.setVisibility(View.VISIBLE);
                    valorView.setText("Valor: " + meta.getValorMeta());
                } else {
                    valorView.setVisibility(View.GONE);
                }

                ((TextView) view.findViewById(R.id.textDataInicial)).setText("Data Inicial: " + meta.getDataInicial());
                ((TextView) view.findViewById(R.id.textDataFim)).setText("Data Final: " + meta.getDataLimite());
                ((TextView) view.findViewById(R.id.textPrioridade)).setText("Prioridade: " + meta.getPrioridade().name());

                String observacao = meta.getObservacao();
                if (observacao == null || observacao.trim().isEmpty()) {
                    observacao = "Nenhuma observação.";
                    Log.d(TAG, "Observação vazia ou nula, usando texto padrão.");
                }
                ((TextView) view.findViewById(R.id.textObservacao)).setText("Observação: " + observacao);

            } else {
                Log.w(TAG, "Nenhuma meta encontrada para o ID: " + metaId);
            }
        } else {
            Log.w(TAG, "ID da meta inválido: " + metaId);
        }

        ImageButton btnVoltar = view.findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(v ->
                NavHostFragment.findNavController(DetalhesMetaFragment.this).navigateUp()
        );
    }
}
