package com.example.gametrack.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.gametrack.R;
import com.example.gametrack.data.dao.UsuarioDao;
import com.example.gametrack.data.model.local.Usuario;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioFragment extends Fragment {

    public UsuarioFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView profileImageView = view.findViewById(R.id.profileImageView);
        TextView textViewNomeUsuario = view.findViewById(R.id.textViewNomeUsuario);
        TextView textViewEmailUsuario = view.findViewById(R.id.textViewEmailUsuario);
        TextView textViewSteamIdUsuario = view.findViewById(R.id.textViewSteamIdUsuario);

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        UsuarioDao usuarioRepository = new UsuarioDao(getContext());
        Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(usuarioAtual.getEmail());

        if (usuarioAtual != null) {
            String nomeUsuario = usuario.getNome();
            String emailUsuario = usuario.getEmail();
            String steamIdUsuario = usuario.getSteamId();
            String imagemPerfil = usuario.getImagemPerfil();

            textViewNomeUsuario.setText(nomeUsuario);
            textViewEmailUsuario.setText(emailUsuario);
            textViewSteamIdUsuario.setText(steamIdUsuario);

            if (getContext() != null && imagemPerfil != null && !imagemPerfil.isEmpty()) {
                Glide.with(getContext()) // Ou Glide.with(this) se estiver em Activity, ou Glide.with(view)
                        .load(imagemPerfil) // A URL da imagem da Steam
                        .placeholder(R.drawable.ic_profile_placeholder) // Imagem enquanto carrega (opcional)
                        .error(R.drawable.ic_profile_placeholder) // Imagem se der erro ao carregar (opcional)
                        .circleCrop() // Para deixar a imagem redonda (opcional)
                        .into(profileImageView); // O ImageView onde a imagem será exibida
            } else if (getContext() != null) {
                // Se não houver URL, carrega o placeholder
                Glide.with(getContext())
                        .load(R.drawable.ic_profile_placeholder)
                        .circleCrop()
                        .into(profileImageView);
            }

            profileImageView.setImageURI(Uri.parse(imagemPerfil));
            }

        else {
            textViewNomeUsuario.setText("Nome do Usuário");
            textViewEmailUsuario.setText("email@example.com");
            textViewSteamIdUsuario.setText("Steam ID: 123456789");
        }

        Button logoutButton = view.findViewById(R.id.logoutButton);

        //logoutButton.setOnClickListener();

    }
}