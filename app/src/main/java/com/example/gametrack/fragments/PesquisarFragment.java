package com.example.gametrack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gametrack.App;
import com.example.gametrack.R;
import com.example.gametrack.activity.DetalhesJogo;
import com.example.gametrack.adapter.JogoAdapter;
import com.example.gametrack.data.model.local.Jogo;
import com.example.gametrack.data.model.local.JogoLayout;
import com.example.gametrack.data.repository.JogoRepository;
import com.example.gametrack.service.SincronizarBibliotecaSteamService;

import java.util.ArrayList;
import java.util.List;

public class PesquisarFragment extends Fragment {

    private final List<JogoLayout> listaJogos = new ArrayList<>();
    private final List<JogoLayout> listaJogosCompleta = new ArrayList<>();
    private JogoAdapter itemAdapter;
    private EditText searchBar;
    private RecyclerView recyclerView;
    private ImageButton btnVoltar;

    public PesquisarFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pesquisar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchBar = view.findViewById(R.id.search_bar);
        recyclerView = view.findViewById(R.id.recycler_view);
        btnVoltar = view.findViewById(R.id.btn_voltar);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        itemAdapter = new JogoAdapter(listaJogos, requireContext(), jogoLayout -> {

            Intent intent = new Intent(requireActivity(), DetalhesJogo.class);
            intent.putExtra("appSteamId", jogoLayout.getAppSteamId());
            startActivity(intent);

        });

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

        btnVoltar.setOnClickListener(v -> {

            SincronizarBibliotecaSteamService sincronizarBibliotecaSteamService = new SincronizarBibliotecaSteamService();
            sincronizarBibliotecaSteamService.sincronizar();

            Toast.makeText(requireContext()  , "Sincronizando biblioteca", Toast.LENGTH_SHORT).show();

//            NavController navController = Navigation.findNavController(view);
//            navController.popBackStack();
        });

        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        JogoRepository jogoRepository = new JogoRepository(App.getContext());
        List<Jogo> jogos = jogoRepository.listarJogos();

        listaJogos.clear();
        listaJogosCompleta.clear();

        for (Jogo jogo : jogos) {
            JogoLayout layout = new JogoLayout(
                    jogo.getAppSteamId(),
                    jogo.getTitulo(),
                    "https://steamcdn-a.akamaihd.net/steam/apps/" + jogo.getAppSteamId()
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