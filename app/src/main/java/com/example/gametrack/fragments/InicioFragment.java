// app/src/main/java/com/example/gametrack/fragments/HomeFragment.java
package com.example.gametrack.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button; // Import Button
import android.widget.Toast;  // Import Toast

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gametrack.App;    // Se usar App.getContext()
import com.example.gametrack.R;
import com.example.gametrack.data.model.local.Jogo; // Import Jogo
import com.example.gametrack.data.repository.JogoRepository; // Import JogoRepository

public class InicioFragment extends Fragment {

    private Button btnAdicionarJogosTeste;
    private JogoRepository jogoRepository;

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializar o repositório. É melhor usar requireContext() se disponível
        // ou garantir que App.getContext() seja seguro.
        if (getContext() != null) { // getContext() é geralmente seguro após onAttach
            jogoRepository = new JogoRepository(requireContext());
        } else if (App.getContext() != null) { // Fallback se App.getContext() for sua preferência
            jogoRepository = new JogoRepository(App.getContext());
        } else {
            Log.e("HomeFragment", "Contexto não disponível para JogoRepository");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAdicionarJogosTeste = view.findViewById(R.id.btnAdicionarJogosTeste);

        btnAdicionarJogosTeste.setOnClickListener(v -> {
            if (jogoRepository != null) {
                adicionarJogosDeTeste();
            } else {
                Toast.makeText(getContext(), "Erro: Repositório não inicializado.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adicionarJogosDeTeste() {
        // Crie novos objetos Jogo com os dados
        Jogo novoJogo1 = new Jogo(
                "Counter-Strike: Global Offensive",
                730, // appSteamId (ex: CS:GO)
                "https://steamcdn-a.akamaihd.net/steam/apps/730/library_600x900_2x.jpg"
        );

        Jogo novoJogo2 = new Jogo(
                "Dota 2",
                570, // appSteamId (ex: Dota 2)
                "https://steamcdn-a.akamaihd.net/steam/apps/570/library_600x900_2x.jpg"
        );

        Jogo novoJogo3 = new Jogo(
                 // appSteamId (ex: GTA V)
                "Grand Theft Auto V",
                271590,
                "https://steamcdn-a.akamaihd.net/steam/apps/271590/library_600x900_2x.jpg"
        );

        // Salve os jogos usando o repositório
        // O método salvarJogo no repositório já deve lidar com a execução em background
        jogoRepository.salvarJogo(novoJogo1);
        jogoRepository.salvarJogo(novoJogo2);
        jogoRepository.salvarJogo(novoJogo3);

        Log.d("HomeFragment", "Jogos de teste enviados para salvamento no banco de dados local.");

        // Feedback para o usuário
        if (getContext() != null) {
            Toast.makeText(getContext(), "Jogos de teste adicionados localmente!", Toast.LENGTH_LONG).show();
        }
    }
}