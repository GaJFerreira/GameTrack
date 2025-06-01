// app/src/main/java/com/example/gametrack/fragment/SearchFragment.java
// (É uma boa prática colocar fragments em um subpacote 'fragment')
package com.example.gametrack.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gametrack.App; // Se você tem uma classe Application customizada
import com.example.gametrack.R;
import com.example.gametrack.adapter.JogoAdapter;
import com.example.gametrack.data.model.local.Jogo;
import com.example.gametrack.data.model.local.JogoLayout;
import com.example.gametrack.data.repository.JogoRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class FragmentPesquisar extends Fragment {

    private final List<JogoLayout> listaJogos = new ArrayList<>();
    private final List<JogoLayout> listaJogosCompleta = new ArrayList<>();
    private JogoAdapter itemAdapter;
    private TextInputEditText searchBar;
    private RecyclerView recyclerView;
    private ImageButton btnVoltar;

    public FragmentPesquisar() {
        // Construtor público vazio obrigatório
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla o layout para este fragment
        // Se você não renomeou o XML, use R.layout.pesquisa_jogo
        View view = inflater.inflate(R.layout.fragment_pesquisar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicialize as views aqui, usando a 'view' do fragment
        searchBar = view.findViewById(R.id.search_bar);
        recyclerView = view.findViewById(R.id.recycler_view);
        btnVoltar = view.findViewById(R.id.btn_voltar);

        // O contexto para o LayoutManager e Adapter agora é requireContext()
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        itemAdapter = new JogoAdapter(listaJogos, requireContext());
        recyclerView.setAdapter(itemAdapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarJogos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Ação do botão voltar:
        // Em vez de finish() ou iniciar uma nova Activity, usamos o NavController
        // para voltar na pilha de navegação.
        btnVoltar.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
            // Se você quiser garantir que ele volte para um destino específico (como o "home"),
            // e limpar a pilha acima dele, você pode usar:
            // navController.navigate(R.id.navigation_home, null, new NavOptions.Builder().setPopUpTo(navController.getGraph().getStartDestinationId(), true).build());
            // Mas popBackStack() é geralmente o que você quer para um botão "voltar" dentro de um fragment.
        });

        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        // Certifique-se que App.getContext() está funcionando como esperado.
        // Se App é sua classe Application, ela deve ser registrada no Manifest
        // e ter um método estático para fornecer o contexto.
        JogoRepository jogoRepository = new JogoRepository(App.getContext());
        List<Jogo> jogos = jogoRepository.listarJogos();

        // Limpar listas antes de adicionar para evitar duplicatas se onViewCreated for chamado novamente
        listaJogos.clear();
        listaJogosCompleta.clear();

        for (Jogo jogo : jogos) {
            JogoLayout layout = new JogoLayout(
                    jogo.getAppSteamId(),
                    jogo.getTitulo(),
                    "https://steamcdn-a.akamaihd.net/steam/apps/" + jogo.getAppSteamId() + "/library_600x900_2x.jpg"
            );
            listaJogos.add(layout);
            listaJogosCompleta.add(layout);
        }

        if (itemAdapter != null) {
            itemAdapter.notifyDataSetChanged();
        }
    }

    private void filtrarJogos(String texto) {
        List<JogoLayout> listaFiltrada = new ArrayList<>();

        for (JogoLayout jogo : listaJogosCompleta) {
            if (jogo.getTitulo().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(jogo);
            }
        }

        listaJogos.clear();
        listaJogos.addAll(listaFiltrada);
        if (itemAdapter != null) {
            itemAdapter.notifyDataSetChanged();
        }
    }
}