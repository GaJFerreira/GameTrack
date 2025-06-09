package com.example.gametrack.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gametrack.App;
import com.example.gametrack.R;
import com.example.gametrack.adapter.DiaAdapter;
import com.example.gametrack.data.dao.MetaDao;
import com.example.gametrack.data.model.local.DiaLayout;
import com.example.gametrack.data.model.local.Meta;
import com.example.gametrack.data.model.local.MetaLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class HomeFragment extends Fragment {

    private final ExecutorService executorService;
    private final Handler mainThreadHandler;

    public HomeFragment() {
        executorService = java.util.concurrent.Executors.newSingleThreadExecutor();
        mainThreadHandler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerDias = view.findViewById(R.id.recyclerDias);
        recyclerDias.setLayoutManager(new LinearLayoutManager(getContext()));

        executorService.execute(() -> {
            Map<String, List<Meta>> metasAgrupadas = buscarTodasMetasDoBanco();

            List<DiaLayout> dias = new ArrayList<>();
            for (Map.Entry<String, List<Meta>> entry : metasAgrupadas.entrySet()) {
                String dataFormatada = formatarData(entry.getKey());

                List<MetaLayout> metasDoDia = new ArrayList<>();
                for (Meta meta : entry.getValue()) {
                    metasDoDia.add(new MetaLayout(
                            meta.getJogo().getTitulo(),
                            "Prioridade: " + meta.getPrioridade().name(),
                            meta.getJogo().getIcone()
                    ));
                }

                dias.add(new DiaLayout(dataFormatada, metasDoDia));
            }

            mainThreadHandler.post(() -> {
                DiaAdapter diaAdapter = new DiaAdapter(dias);
                recyclerDias.setAdapter(diaAdapter);
            });
        });
    }

    private Map<String, List<Meta>> buscarTodasMetasDoBanco() {
        MetaDao metaDao = new MetaDao(App.getContext());
        return metaDao.listarMetasPorUsuarioAgrupadasPorDia(1);
    }

    private String formatarData(String dataISO) {
        try {
            SimpleDateFormat sdfBanco = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date data = sdfBanco.parse(dataISO);
            SimpleDateFormat sdfDisplay = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return sdfDisplay.format(Objects.requireNonNull(data));
        } catch (Exception e) {
            e.printStackTrace();
            return dataISO;
        }
    }
}

