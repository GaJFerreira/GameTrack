package com.example.gametrack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText; // Usando EditText conforme seu código Java
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gametrack.App;
import com.example.gametrack.R;
import com.example.gametrack.activity.DetalhesJogo; // Activity de detalhes
import com.example.gametrack.adapter.JogoAdapter;
import com.example.gametrack.data.model.local.Jogo;
import com.example.gametrack.data.model.local.JogoLayout;
import com.example.gametrack.data.repository.JogoRepository;

import java.util.ArrayList;
import java.util.List;

public class PesquisarFragment extends Fragment {

    private final List<JogoLayout> listaJogos = new ArrayList<>();
    private final List<JogoLayout> listaJogosCompleta = new ArrayList<>();
    private JogoAdapter itemAdapter;
    private EditText searchBar; // Alterado para EditText
    private RecyclerView recyclerView;
    private ImageButton btnVoltar;

    public PesquisarFragment() {
        // Construtor público vazio obrigatório
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla o layout para este fragment.
        // Se o seu XML se chama pesquisa_jogo.xml, use R.layout.pesquisa_jogo
        View view = inflater.inflate(R.layout.fragment_pesquisar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchBar = view.findViewById(R.id.search_bar);
        recyclerView = view.findViewById(R.id.recycler_view);
        btnVoltar = view.findViewById(R.id.btn_voltar);

        // Usar requireContext() para o contexto no Fragment
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        itemAdapter = new JogoAdapter(listaJogos, requireContext(), jogoLayout -> {
            // Ação de clique no item do RecyclerView
            // Aqui você pode navegar para a tela de DetalhesJogo.
            // Se DetalhesJogo também for um Fragment, use o NavController.
            // Se DetalhesJogo for uma Activity, você pode iniciar a Activity como antes,
            // mas considere se ela também deveria ser um Fragment para uma navegação mais coesa.

            // Exemplo: Navegando para uma Activity DetalhesJogo
            Intent intent = new Intent(requireActivity(), DetalhesJogo.class);
            intent.putExtra("appSteamId", jogoLayout.getAppSteamId());
            startActivity(intent);

            // Exemplo Alternativo: Navegando para um Fragment DetalhesJogo (se existir)
            // Bundle bundle = new Bundle();
            // bundle.putString("appSteamId", jogoLayout.getAppSteamId());
            // NavController navController = Navigation.findNavController(view);
            // navController.navigate(R.id.action_searchFragment_to_detalhesJogoFragment, bundle); // Crie esta ação no seu nav_graph
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
            // Voltar para o fragment anterior na pilha
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });

        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        // Certifique-se que App.getContext() é seguro aqui ou passe o contexto de outra forma
        JogoRepository jogoRepository = new JogoRepository(App.getContext());
        List<Jogo> jogos = jogoRepository.listarJogos();

        listaJogos.clear();
        listaJogosCompleta.clear();

        for (Jogo jogo : jogos) {
            JogoLayout layout = new JogoLayout(
                    jogo.getAppSteamId(),
                    jogo.getTitulo(),
                    // A URL da imagem estava incompleta no seu código original, adicionei o final
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

    // É uma boa prática limpar referências a views em onDestroyView
    // para evitar memory leaks, especialmente se você tiver listeners complexos
    // ou databinding.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Limpar referências se necessário, ex:
        // searchBar = null;
        // recyclerView.setAdapter(null); // Importante para RecyclerViews
        // recyclerView = null;
        // btnVoltar = null;
        // itemAdapter = null; // Se o adapter tiver referências ao contexto ou views
    }
}