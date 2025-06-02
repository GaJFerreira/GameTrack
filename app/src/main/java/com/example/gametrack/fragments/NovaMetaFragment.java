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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.gametrack.R;
import com.example.gametrack.data.model.local.Meta;
import com.example.gametrack.data.repository.MetaRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class NovaMetaFragment extends Fragment {

    private static final String TAG = "CriarMetaFragment";

    private TextInputEditText editTextBibliotecaId;
    private Spinner spinnerTipoMeta;
    private TextInputEditText editTextValorMeta;
    private TextInputEditText editTextProgressoAtual;
    private TextInputEditText editTextDataLimite;
    private Spinner spinnerPrioridade;
    private Spinner spinnerStatus;
    private TextInputEditText editTextObservacao;
    private Button btnSalvarMeta;

    private MetaRepository metaRepository;
    private Calendar calendarioDataLimite;

    private ExecutorService executorService;
    private Handler mainThreadHandler;

    public NovaMetaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        metaRepository = new MetaRepository(requireContext());
        executorService = Executors.newSingleThreadExecutor();
        mainThreadHandler = new Handler(Looper.getMainLooper());
        calendarioDataLimite = Calendar.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nova_meta, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextBibliotecaId = view.findViewById(R.id.editTextBibliotecaId);
        spinnerTipoMeta = view.findViewById(R.id.spinnerTipoMeta);
        editTextValorMeta = view.findViewById(R.id.editTextValorMeta);
        editTextProgressoAtual = view.findViewById(R.id.editTextProgressoAtual);
        editTextDataLimite = view.findViewById(R.id.editTextDataLimite);
        spinnerPrioridade = view.findViewById(R.id.spinnerPrioridade);
        spinnerStatus = view.findViewById(R.id.spinnerStatus);
        editTextObservacao = view.findViewById(R.id.editTextObservacao);
        btnSalvarMeta = view.findViewById(R.id.btnSalvarMeta);

        setupSpinners();
        setupDatePicker();

        btnSalvarMeta.setOnClickListener(v -> salvarMeta());
    }

    private void setupSpinners() {
        // Spinner Tipo Meta
        // Para exibir a descrição, precisamos de uma lista de Strings
        List<String> tiposDescricao = Arrays.stream(Meta.Tipo.values())
                .map(Meta.Tipo::getDescricao)
                .collect(Collectors.toList());
        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, tiposDescricao);
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoMeta.setAdapter(tipoAdapter);

        // Spinner Prioridade
        List<String> prioridadesDescricao = Arrays.stream(Meta.Prioridade.values())
                .map(Meta.Prioridade::getDescricao)
                .collect(Collectors.toList());
        ArrayAdapter<String> prioridadeAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, prioridadesDescricao);
        prioridadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrioridade.setAdapter(prioridadeAdapter);

        // Spinner Status
        List<String> statusDescricao = Arrays.stream(Meta.Status.values())
                .map(Meta.Status::getDescricao)
                .collect(Collectors.toList());
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, statusDescricao);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);
        // Definir "Em Andamento" como padrão
        for (int i = 0; i < Meta.Status.values().length; i++) {
            if (Meta.Status.values()[i] == Meta.Status.EM_ANDAMENTO) {
                spinnerStatus.setSelection(i);
                break;
            }
        }
    }

    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            calendarioDataLimite.set(Calendar.YEAR, year);
            calendarioDataLimite.set(Calendar.MONTH, month);
            calendarioDataLimite.set(Calendar.DAY_OF_MONTH, day);
            updateLabelDataLimite();
        };

        editTextDataLimite.setOnClickListener(view -> {
            new DatePickerDialog(requireContext(), dateSetListener,
                    calendarioDataLimite.get(Calendar.YEAR),
                    calendarioDataLimite.get(Calendar.MONTH),
                    calendarioDataLimite.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void updateLabelDataLimite() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        editTextDataLimite.setText(sdf.format(calendarioDataLimite.getTime()));
    }

    private Meta.Tipo getTipoSelecionado() {
        String descricaoSelecionada = (String) spinnerTipoMeta.getSelectedItem();
        for (Meta.Tipo tipo : Meta.Tipo.values()) {
            if (tipo.getDescricao().equals(descricaoSelecionada)) {
                return tipo;
            }
        }
        return null; // Ou um valor padrão
    }

    private Meta.Prioridade getPrioridadeSelecionada() {
        String descricaoSelecionada = (String) spinnerPrioridade.getSelectedItem();
        for (Meta.Prioridade prioridade : Meta.Prioridade.values()) {
            if (prioridade.getDescricao().equals(descricaoSelecionada)) {
                return prioridade;
            }
        }
        return null; // Ou um valor padrão
    }

    private Meta.Status getStatusSelecionado() {
        String descricaoSelecionada = (String) spinnerStatus.getSelectedItem();
        for (Meta.Status status : Meta.Status.values()) {
            if (status.getDescricao().equals(descricaoSelecionada)) {
                return status;
            }
        }
        return Meta.Status.EM_ANDAMENTO; // Default
    }


    private void salvarMeta() {
        String bibliotecaIdStr = editTextBibliotecaId.getText().toString().trim();
        Meta.Tipo tipo = getTipoSelecionado();
        String valorMeta = editTextValorMeta.getText().toString().trim();
        String progressoAtual = editTextProgressoAtual.getText().toString().trim();
        String dataLimite = editTextDataLimite.getText().toString().trim();
        Meta.Prioridade prioridade = getPrioridadeSelecionada();
        Meta.Status status = getStatusSelecionado();
        String observacao = editTextObservacao.getText().toString().trim();

        if (TextUtils.isEmpty(bibliotecaIdStr)) {
            editTextBibliotecaId.setError("ID do Jogo é obrigatório");
            editTextBibliotecaId.requestFocus();
            return;
        }
        if (tipo == null) {
            Toast.makeText(requireContext(), "Selecione um tipo de meta", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(valorMeta)) {
            editTextValorMeta.setError("Valor da meta é obrigatório");
            editTextValorMeta.requestFocus();
            return;
        }
        // Adicione mais validações conforme necessário

        long bibliotecaId;
        try {
            bibliotecaId = Long.parseLong(bibliotecaIdStr);
        } catch (NumberFormatException e) {
            editTextBibliotecaId.setError("ID do Jogo inválido");
            editTextBibliotecaId.requestFocus();
            return;
        }

        Meta novaMeta = new Meta(); // Usando construtor vazio e setters
        novaMeta.setBibliotecaId(bibliotecaId);
        novaMeta.setTipo(tipo);
        novaMeta.setValorMeta(valorMeta);
        novaMeta.setProgressoAtual(progressoAtual);
        novaMeta.setDataLimite(dataLimite);
        novaMeta.setPrioridade(prioridade);
        novaMeta.setStatus(status);
        novaMeta.setObservacao(observacao);

        executorService.execute(() -> {
            try {
                metaRepository.salvarMeta(novaMeta); // Assumindo que salvarMeta é síncrono no repo
                mainThreadHandler.post(() -> {
                    if(!isAdded()) return;
                    Toast.makeText(requireContext(), "Meta salva com sucesso!", Toast.LENGTH_SHORT).show();
                    // Navegar de volta para a lista de metas ou fechar o fragment
                    NavController navController = Navigation.findNavController(requireView());
                    navController.popBackStack();
                });
            } catch (Exception e) {
                Log.e(TAG, "Erro ao salvar meta", e);
                mainThreadHandler.post(() -> {
                    if(!isAdded()) return;
                    Toast.makeText(requireContext(), "Erro ao salvar meta: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}