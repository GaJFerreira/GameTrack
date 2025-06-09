package com.example.gametrack.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.gametrack.App;
import com.example.gametrack.R;
import com.example.gametrack.data.model.local.Meta;
import com.example.gametrack.data.model.local.Jogo;
import com.example.gametrack.data.model.local.Usuario;
import com.example.gametrack.data.repository.JogoRepository;
import com.example.gametrack.data.repository.MetaRepository;
import com.example.gametrack.data.repository.UsuarioRepository;
import com.example.gametrack.data.storage.SecurePreferences;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CadastrarMetaFragment extends Fragment {
    private static final String TAG = "CadastrarMetaFragment";
    private Spinner spinnerTipoMeta, spinnerPrioridade;
    private EditText editTextValorMeta, editTextDataLimite, editTextObservacao;
    private MetaRepository metaRepository;
    private ExecutorService executorService;
    private Handler mainThreadHandler;
    private Calendar calendarioDataLimite;
    private Jogo jogoSelecionado;
    private Usuario usuarioCorrente;

    public CadastrarMetaFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        metaRepository = new MetaRepository(requireContext());
        executorService = Executors.newSingleThreadExecutor();
        mainThreadHandler = new Handler(Looper.getMainLooper());
        calendarioDataLimite = Calendar.getInstance();

        UsuarioRepository usuarioRepository = new UsuarioRepository(App.getContext());

        usuarioCorrente = usuarioRepository.buscarUsuarioPorEmail(SecurePreferences.get("emailUsuario"));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastrar_meta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey("appSteamId")) {
            long appSteamId = getArguments().getLong("appSteamId");
            JogoRepository jogoRepository = new JogoRepository(App.getContext());
            jogoSelecionado = jogoRepository.buscarPorSteamId(appSteamId);

            if (jogoSelecionado == null) {
                Toast.makeText(requireContext(), "Jogo não encontrado!", Toast.LENGTH_SHORT).show();
            }
        }

        spinnerTipoMeta = view.findViewById(R.id.spinnerTipoMeta);
        spinnerPrioridade = view.findViewById(R.id.spinnerPrioridade);
        editTextValorMeta = view.findViewById(R.id.editTextValorMeta);
        editTextDataLimite = view.findViewById(R.id.editTextDataLimite);
        editTextObservacao = view.findViewById(R.id.editTextObservacao);
        Button btnSalvarMeta = view.findViewById(R.id.btnSalvarMeta);

        setupSpinners();
        setupDatePicker();

        spinnerTipoMeta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (getTipoSelecionado() == Meta.Tipo.TEMPO) {
                    editTextValorMeta.setVisibility(View.VISIBLE);
                } else {
                    editTextValorMeta.setVisibility(View.GONE);
                    editTextValorMeta.setText("");
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {
                editTextValorMeta.setVisibility(View.GONE);
                editTextValorMeta.setText("");
            }
        });

        btnSalvarMeta.setOnClickListener(v -> salvarMeta());
    }

    private void setupSpinners() {
        List<String> tiposDescricao = Arrays.stream(Meta.Tipo.values())
                .map(Meta.Tipo::getDescricao).collect(Collectors.toList());
        spinnerTipoMeta.setAdapter(new ArrayAdapter<>(requireContext(), R.layout.spinner_item, tiposDescricao));

        List<String> prioridadesDescricao = Arrays.stream(Meta.Prioridade.values())
                .map(Meta.Prioridade::getDescricao).collect(Collectors.toList());
        spinnerPrioridade.setAdapter(new ArrayAdapter<>(requireContext(), R.layout.spinner_item, prioridadesDescricao));
    }

    private void setupDatePicker() {
        editTextDataLimite.setOnClickListener(view -> {
            Calendar hoje = Calendar.getInstance();
            DatePickerDialog dpd = new DatePickerDialog(requireContext(), (dp, year, month, day) -> {
                Calendar dataSelecionada = Calendar.getInstance();
                dataSelecionada.set(year, month, day);
                if (dataSelecionada.before(hoje)) {
                    Toast.makeText(requireContext(), "Data limite não pode ser anterior à data atual", Toast.LENGTH_SHORT).show();
                    return;
                }
                calendarioDataLimite.set(year, month, day);
                updateLabelDataLimite();
            }, calendarioDataLimite.get(Calendar.YEAR),
                    calendarioDataLimite.get(Calendar.MONTH),
                    calendarioDataLimite.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setMinDate(hoje.getTimeInMillis());
            dpd.show();
        });
    }

    private void updateLabelDataLimite() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        editTextDataLimite.setText(sdf.format(calendarioDataLimite.getTime()));
    }

    private Meta.Tipo getTipoSelecionado() {
        String descricao = (String) spinnerTipoMeta.getSelectedItem();
        for (Meta.Tipo tipo : Meta.Tipo.values()) {
            if (tipo.getDescricao().equals(descricao)) return tipo;
        }
        return null;
    }

    private Meta.Prioridade getPrioridadeSelecionada() {
        String descricao = (String) spinnerPrioridade.getSelectedItem();
        for (Meta.Prioridade prioridade : Meta.Prioridade.values()) {
            if (prioridade.getDescricao().equals(descricao)) return prioridade;
        }
        return null;
    }

    private void salvarMeta() {
        Meta.Tipo tipo = getTipoSelecionado();
        String valorMeta = editTextValorMeta.getText().toString().trim();
        String dataLimite = editTextDataLimite.getText().toString().trim();
        Meta.Prioridade prioridade = getPrioridadeSelecionada();
        String observacao = editTextObservacao.getText().toString().trim();

        if (tipo == null) {
            Toast.makeText(requireContext(), "Selecione um tipo de meta", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tipo == Meta.Tipo.TEMPO && TextUtils.isEmpty(valorMeta)) {
            editTextValorMeta.setError("Valor da meta é obrigatório para metas do tipo tempo");
            editTextValorMeta.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(dataLimite)) {
            editTextDataLimite.setError("Data limite é obrigatória");
            editTextDataLimite.requestFocus();
            return;
        }

        if (prioridade == null) {
            Toast.makeText(requireContext(), "Selecione uma prioridade", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tipo != Meta.Tipo.TEMPO) {
            valorMeta = "";
        }

        if (jogoSelecionado == null || usuarioCorrente == null) {
            Toast.makeText(requireContext(), "Erro: Jogo ou Usuário não disponível", Toast.LENGTH_LONG).show();
            return;
        }

        Meta novaMeta = new Meta();
        novaMeta.setTipo(tipo);
        novaMeta.setValorMeta(valorMeta);
        novaMeta.setDataLimite(dataLimite);
        novaMeta.setPrioridade(prioridade);
        novaMeta.setObservacao(observacao);
        novaMeta.setJogo(jogoSelecionado);
        novaMeta.setUsuario(usuarioCorrente);

        executorService.execute(() -> {
            try {
                metaRepository.salvarMeta(novaMeta);
                mainThreadHandler.post(() -> {
                    if (!isAdded()) return;
                    Toast.makeText(requireContext(), "Meta salva com sucesso!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireView()).popBackStack();
                });
            } catch (Exception e) {
                Log.e(TAG, "Erro ao salvar meta", e);
                mainThreadHandler.post(() -> {
                    if (!isAdded()) return;
                    Toast.makeText(requireContext(), "Erro ao salvar meta: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!executorService.isShutdown()) executorService.shutdown();
    }
}
