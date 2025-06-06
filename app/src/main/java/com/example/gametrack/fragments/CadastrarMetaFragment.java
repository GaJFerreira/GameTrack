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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class CadastrarMetaFragment extends Fragment {
    private static final String TAG = "CadastrarMetaFragment";
    private Spinner spinnerTipoMeta;
    private EditText editTextValorMeta;
    private EditText editTextDataLimite;
    private Spinner spinnerPrioridade;
    private EditText editTextObservacao;
    private MetaRepository metaRepository;
    private Calendar calendarioDataLimite;
    private ExecutorService executorService;
    private Handler mainThreadHandler;

    public CadastrarMetaFragment() {
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
        return inflater.inflate(R.layout.fragment_cadastrar_meta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerTipoMeta = view.findViewById(R.id.spinnerTipoMeta);
        editTextValorMeta = view.findViewById(R.id.editTextValorMeta);
        editTextDataLimite = view.findViewById(R.id.editTextDataLimite);
        spinnerPrioridade = view.findViewById(R.id.spinnerPrioridade);
        editTextObservacao = view.findViewById(R.id.editTextObservacao);
        Button btnSalvarMeta = view.findViewById(R.id.btnSalvarMeta);

        setupSpinners();
        setupDatePicker();

        spinnerTipoMeta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Meta.Tipo tipoSelecionado = getTipoSelecionado();
                if (tipoSelecionado == Meta.Tipo.TEMPO) {
                    editTextValorMeta.setVisibility(View.VISIBLE);
                } else {
                    editTextValorMeta.setVisibility(View.GONE);
                    editTextValorMeta.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editTextValorMeta.setVisibility(View.GONE);
                editTextValorMeta.setText("");
            }
        });

        btnSalvarMeta.setOnClickListener(v -> salvarMeta());
    }

    private void setupSpinners() {
        // Spinner Tipo Meta
        List<String> tiposDescricao = Arrays.stream(Meta.Tipo.values())
                .map(Meta.Tipo::getDescricao)
                .collect(Collectors.toList());
        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(requireContext(),
                R.layout.spinner_item, tiposDescricao);
        tipoAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerTipoMeta.setAdapter(tipoAdapter);

        // Spinner Prioridade
        List<String> prioridadesDescricao = Arrays.stream(Meta.Prioridade.values())
                .map(Meta.Prioridade::getDescricao)
                .collect(Collectors.toList());
        ArrayAdapter<String> prioridadeAdapter = new ArrayAdapter<>(requireContext(),
                R.layout.spinner_item, prioridadesDescricao);
        prioridadeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerPrioridade.setAdapter(prioridadeAdapter);
    }

    private void setupDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            Calendar hoje = Calendar.getInstance();
            Calendar dataSelecionada = Calendar.getInstance();
            dataSelecionada.set(year, month, day);

            // Verifica se a data selecionada é antes de hoje
            if (dataSelecionada.before(hoje)) {
                Toast.makeText(requireContext(), "Data limite não pode ser anterior à data atual", Toast.LENGTH_SHORT).show();
                return;
            }

            calendarioDataLimite.set(year, month, day);
            updateLabelDataLimite();
        };

        editTextDataLimite.setOnClickListener(view -> {
            Calendar hoje = Calendar.getInstance();
            DatePickerDialog dpd = new DatePickerDialog(requireContext(), dateSetListener,
                    calendarioDataLimite.get(Calendar.YEAR),
                    calendarioDataLimite.get(Calendar.MONTH),
                    calendarioDataLimite.get(Calendar.DAY_OF_MONTH));
            // Define data mínima como hoje para impedir seleção de datas anteriores
            dpd.getDatePicker().setMinDate(hoje.getTimeInMillis());
            dpd.show();
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
        return null;
    }

    private Meta.Prioridade getPrioridadeSelecionada() {
        String descricaoSelecionada = (String) spinnerPrioridade.getSelectedItem();
        for (Meta.Prioridade prioridade : Meta.Prioridade.values()) {
            if (prioridade.getDescricao().equals(descricaoSelecionada)) {
                return prioridade;
            }
        }
        return null;
    }

    private void salvarMeta() {
        Meta.Tipo tipo = getTipoSelecionado();
        String valorMeta = Objects.requireNonNull(editTextValorMeta.getText()).toString().trim();
        String dataLimite = Objects.requireNonNull(editTextDataLimite.getText()).toString().trim();
        Meta.Prioridade prioridade = getPrioridadeSelecionada();
        String observacao = Objects.requireNonNull(editTextObservacao.getText()).toString().trim();

        if (tipo == null) {
            Toast.makeText(requireContext(), "Selecione um tipo de meta", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tipo == Meta.Tipo.TEMPO) {
            if (TextUtils.isEmpty(valorMeta)) {
                editTextValorMeta.setError("Valor da meta é obrigatório para metas do tipo tempo");
                editTextValorMeta.requestFocus();
                return;
            }
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

        // Para os tipos que não são TEMPO, ignorar valorMeta (ou definir vazio)
        if (tipo != Meta.Tipo.TEMPO) {
            valorMeta = "";
        }

        Meta novaMeta = new Meta();
        novaMeta.setTipo(tipo);
        novaMeta.setValorMeta(valorMeta);
        novaMeta.setDataLimite(dataLimite);
        novaMeta.setPrioridade(prioridade);
        novaMeta.setObservacao(observacao);

        executorService.execute(() -> {
            try {
                metaRepository.salvarMeta(novaMeta);
                mainThreadHandler.post(() -> {
                    if (!isAdded()) return;
                    Toast.makeText(requireContext(), "Meta salva com sucesso!", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(requireView());
                    navController.popBackStack();
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
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
