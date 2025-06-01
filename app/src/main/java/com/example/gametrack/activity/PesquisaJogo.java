package com.example.gametrack.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import com.example.gametrack.App;
import com.example.gametrack.R;
import com.example.gametrack.adapter.JogoAdapter;
import com.example.gametrack.data.model.local.Jogo;
import com.example.gametrack.data.model.local.JogoLayout;
import com.example.gametrack.data.repository.JogoRepository;

import java.util.ArrayList;
import java.util.List;

public class PesquisaJogo extends AppCompatActivity {

    private final List<JogoLayout> listaJogos = new ArrayList<>();
    private final List<JogoLayout> listaJogosCompleta = new ArrayList<>();
    private JogoAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisa_jogo);

        EditText searchBar = findViewById(R.id.search_bar);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(App.getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        itemAdapter = new JogoAdapter(listaJogos, App.getContext(), jogo -> {
            Intent intent = new Intent(PesquisaJogo.this, DetalhesJogo.class);
            System.out.println(jogo.toString());
            intent.putExtra("appSteamId", jogo.getAppSteamId());
            startActivity(intent);
        });

        recyclerView.setAdapter(itemAdapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarJogos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ImageButton btnVoltar = findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(PesquisaJogo.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        JogoRepository jogoRepository = new JogoRepository(App.getContext());
        List<Jogo> jogos = jogoRepository.listarJogos();

        for (Jogo jogo : jogos) {
            JogoLayout layout = new JogoLayout(
                    jogo.getAppSteamId(),
                    jogo.getTitulo(),
                    "https://steamcdn-a.akamaihd.net/steam/apps/" + jogo.getAppSteamId()
            );
            listaJogos.add(layout);
            listaJogosCompleta.add(layout);
        }

        itemAdapter.notifyDataSetChanged();
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
        itemAdapter.notifyDataSetChanged();
    }
}