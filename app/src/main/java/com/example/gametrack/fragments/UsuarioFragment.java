package com.example.gametrack.fragments;

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

import com.bumptech.glide.Glide;
import com.example.gametrack.App;
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

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public UsuarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null && App.getContext() != null) {
            usuarioRepository = new UsuarioRepository(App.getContext());
        } else {
            Log.e(TAG, "Contexto para UsuarioRepository não pôde ser obtido.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuario, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileImageView = view.findViewById(R.id.profileImageView);
        textViewNomeUsuario = view.findViewById(R.id.textViewNomeUsuario);
        textViewEmailUsuario = view.findViewById(R.id.textViewEmailUsuario);
        textViewSteamIdUsuario = view.findViewById(R.id.textViewSteamIdUsuario);

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

        String userEmailToLoad = "usuario.logado@email.com";

        executorService.execute(() -> {

            final Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(userEmailToLoad);

            mainThreadHandler.post(() -> {

                if (usuario != null) {
                    updateUI(usuario);
                } else {
                    Log.w(TAG, "Usuário não encontrado com o email: " + userEmailToLoad);
                    if(getContext() != null) {
                        Toast.makeText(getContext(), "Usuário não encontrado.", Toast.LENGTH_SHORT).show();

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

        if (getContext() != null && usuario.getImagemPerfil() != null && !usuario.getImagemPerfil().isEmpty()) {
            Glide.with(getContext())
                    .load(usuario.getImagemPerfil())
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .error(R.drawable.ic_profile_placeholder)
                    .circleCrop()
                    .into(profileImageView);
        } else if (getContext() != null) {

            Glide.with(getContext())
                    .load(R.drawable.ic_profile_placeholder)
                    .circleCrop()
                    .into(profileImageView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        profileImageView = null;
        textViewNomeUsuario = null;
        textViewEmailUsuario = null;
        textViewSteamIdUsuario = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}