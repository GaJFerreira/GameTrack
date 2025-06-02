// app/src/main/java/com/example/gametrack/fragments/UsuarioFragment.java
package com.example.gametrack.fragments; // Ou o pacote onde você colocou

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide; // Para carregar imagens
import com.example.gametrack.App; // Se você tem uma classe Application customizada
import com.example.gametrack.R;
import com.example.gametrack.data.model.local.Usuario;
import com.example.gametrack.data.repository.UsuarioRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsuarioFragment extends Fragment {

    private static final String TAG = "UsuarioFragment";

    private ImageView profileImageView;
    private TextView textViewNomeUsuario;
    private TextView textViewEmailUsuario;
    private TextView textViewSteamIdUsuario;

    private UsuarioRepository usuarioRepository;

    // Para executar operações de banco de dados fora da thread principal
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public UsuarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicialize o repositório aqui, pois o contexto estará disponível
        // Certifique-se que App.getContext() é seguro e não nulo
        if (getActivity() != null && App.getContext() != null) {
            usuarioRepository = new UsuarioRepository(App.getContext());
        } else {
            Log.e(TAG, "Contexto para UsuarioRepository não pôde ser obtido.");
            // Considere tratar este caso, talvez exibindo um erro ou não prosseguindo.
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Encontre as views do layout
        profileImageView = view.findViewById(R.id.profileImageView);
        textViewNomeUsuario = view.findViewById(R.id.textViewNomeUsuario);
        textViewEmailUsuario = view.findViewById(R.id.textViewEmailUsuario);
        textViewSteamIdUsuario = view.findViewById(R.id.textViewSteamIdUsuario);

        // Carregue os dados do usuário
        loadUserData();
    }

    private void loadUserData() {
        if (usuarioRepository == null) {
            Log.e(TAG, "UsuarioRepository não foi inicializado.");
            if(getContext() != null) {
                Toast.makeText(getContext(), "Erro ao carregar dados do usuário.", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        // TODO: Substitua "usuario.logado@email.com" pela lógica para obter o email do usuário logado
        // Por exemplo, de SharedPreferences
        String userEmailToLoad = "usuario.logado@email.com"; // EXEMPLO

        executorService.execute(() -> {

            final Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(userEmailToLoad);

            mainThreadHandler.post(() -> {

                if (usuario != null) {
                    updateUI(usuario);
                } else {
                    Log.w(TAG, "Usuário não encontrado com o email: " + userEmailToLoad);
                    if(getContext() != null) {
                        Toast.makeText(getContext(), "Usuário não encontrado.", Toast.LENGTH_SHORT).show();
                        // Você pode querer limpar os campos ou mostrar placeholders
                        textViewNomeUsuario.setText("Usuário não encontrado");
                        textViewEmailUsuario.setText("");
                        textViewSteamIdUsuario.setText("");
                        profileImageView.setImageResource(R.drawable.ic_profile_placeholder);
                    }
                }
            });
        });
    }

    private void updateUI(Usuario usuario) {
        textViewNomeUsuario.setText(usuario.getNome());
        textViewEmailUsuario.setText(usuario.getEmail());
        textViewSteamIdUsuario.setText("Steam ID: " + (usuario.getSteamId() != null ? usuario.getSteamId() : "Não informado"));

        // Carregar imagem de perfil usando Glide
        // Certifique-se de que getContext() não é nulo aqui.
        // O placeholder é o mesmo que você definiu no XML.
        if (getContext() != null && usuario.getImagemPerfil() != null && !usuario.getImagemPerfil().isEmpty()) {
            Glide.with(getContext())
                    .load(usuario.getImagemPerfil()) // URL ou caminho da imagem
                    .placeholder(R.drawable.ic_profile_placeholder) // Imagem enquanto carrega
                    .error(R.drawable.ic_profile_placeholder) // Imagem em caso de erro
                    .circleCrop() // Para deixar a imagem redonda (opcional)
                    .into(profileImageView);
        } else if (getContext() != null) {
            // Define um placeholder se não houver imagem de perfil
            Glide.with(getContext())
                    .load(R.drawable.ic_profile_placeholder)
                    .circleCrop()
                    .into(profileImageView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Limpar referências para evitar memory leaks
        profileImageView = null;
        textViewNomeUsuario = null;
        textViewEmailUsuario = null;
        textViewSteamIdUsuario = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Desligar o executor quando o fragmento for destruído
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}