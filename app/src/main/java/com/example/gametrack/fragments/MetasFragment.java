// app/src/main/java/com/example/gametrack/fragments/MetasFragment.java
package com.example.gametrack.fragments;

import android.os.Bundle;
import android.os.Handler; // Necessário
import android.os.Looper;  // Necessário
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gametrack.R;
import com.example.gametrack.adapter.MetaAdapter;
import com.example.gametrack.data.model.local.Meta;
import com.example.gametrack.data.repository.MetaRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList; // Para inicializar a lista vazia
import java.util.List;
import java.util.concurrent.ExecutorService; // Necessário
import java.util.concurrent.Executors;   // Necessário

public class MetasFragment extends Fragment {

    private static final String TAG = "MetasFragment";

    private RecyclerView recyclerViewMetas;
    private MetaAdapter metaAdapter;
    private MetaRepository metaRepository;
    private FloatingActionButton fabAdicionarMeta;
    private TextView textViewMetasVazio;

    // Executor para rodar tarefas em background
    private ExecutorService executorService;
    // Handler para postar resultados na UI thread
    private Handler mainThreadHandler;

    public MetasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        metaRepository = new MetaRepository(requireContext());
        metaAdapter = new MetaAdapter(requireContext());

        // Inicializar Executor e Handler
        executorService = Executors.newSingleThreadExecutor();
        mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meta, container, false); // Use o nome correto do seu layout
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewMetas = view.findViewById(R.id.recyclerViewMetas);
        fabAdicionarMeta = view.findViewById(R.id.fabAdicionarMeta);
        textViewMetasVazio = view.findViewById(R.id.textViewMetasVazio);

        setupRecyclerView();

        fabAdicionarMeta.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_metasFragment_to_criarMetaFragment);
        });
    }

    private void setupRecyclerView() {
        recyclerViewMetas.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewMetas.setAdapter(metaAdapter);
    }

    private void loadMetas() {
        recyclerViewMetas.setVisibility(View.VISIBLE); // Otimismo inicial
        textViewMetasVazio.setVisibility(View.GONE);

        executorService.execute(() -> { // Executa a chamada ao repositório em background
            try {
                final List<Meta> metas = metaRepository.listarMetas(); // Chamada síncrona

                mainThreadHandler.post(() -> { // Posta o resultado para a UI thread
                    if (!isAdded()) return;

                    if (metas != null && !metas.isEmpty()) {
                        metaAdapter.setMetas(metas);
                        recyclerViewMetas.setVisibility(View.VISIBLE);
                        textViewMetasVazio.setVisibility(View.GONE);
                    } else {
                        Log.d(TAG, "Nenhuma meta encontrada.");
                        metaAdapter.setMetas(new ArrayList<>()); // Passa lista vazia para limpar adapter
                        recyclerViewMetas.setVisibility(View.GONE);
                        textViewMetasVazio.setText("Nenhuma meta cadastrada.");
                        textViewMetasVazio.setVisibility(View.VISIBLE);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Erro ao carregar metas do repositório", e);
                mainThreadHandler.post(() -> { // Posta o erro para a UI thread
                    if (!isAdded()) return;
                    Toast.makeText(requireContext(), "Erro ao carregar metas.", Toast.LENGTH_SHORT).show();
                    recyclerViewMetas.setVisibility(View.GONE);
                    textViewMetasVazio.setText("Erro ao carregar metas.");
                    textViewMetasVazio.setVisibility(View.VISIBLE);
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMetas();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Desligar o executorService para liberar recursos
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}