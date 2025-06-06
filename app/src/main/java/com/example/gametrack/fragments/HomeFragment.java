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

import com.example.gametrack.R;
import com.example.gametrack.adapter.DiaAdapter;
import com.example.gametrack.data.model.local.DiaLayout;
import com.example.gametrack.data.model.local.Meta;
import com.example.gametrack.data.model.local.MetaLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class HomeFragment extends Fragment {

    private static final String TAG = "InicioFragment";

    private ExecutorService executorService;
    private Handler mainThreadHandler;

    public HomeFragment() {
        // Construtor vazio requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar o layout da fragment (crie um layout com RecyclerView id recyclerDias)
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerDias = view.findViewById(R.id.recyclerDias);
        recyclerDias.setLayoutManager(new LinearLayoutManager(getContext()));

        // Gerar dados mockados sem biblioteca
        List<DiaLayout> diasMock = gerarDiasMock();

        // Criar adapter e setar no recycler
        DiaAdapter diaAdapter = new DiaAdapter(diasMock);
        recyclerDias.setAdapter(diaAdapter);
    }

    private List<DiaLayout> gerarDiasMock() {
        List<DiaLayout> dias = new ArrayList<>();

        // Criar metas para o dia 1
        List<MetaLayout> metasDia1 = new ArrayList<>();
        metasDia1.add(new MetaLayout("Comprar pão", "Ir à padaria às 8h"));
        metasDia1.add(new MetaLayout("Reunião", "Call com o time às 10h"));

        // Criar metas para o dia 2
        List<MetaLayout> metasDia2 = new ArrayList<>();
        metasDia2.add(new MetaLayout("Exercício", "Corrida de 5km"));
        metasDia2.add(new MetaLayout("Estudo", "Ler capítulo 4 do livro"));
        metasDia2.add(new MetaLayout("Almoço", "Comer com amigos"));

        // Montar os dias
        dias.add(new DiaLayout("05/06/2025", metasDia1));
        dias.add(new DiaLayout("06/06/2025", metasDia2));

        return dias;
    }
}
