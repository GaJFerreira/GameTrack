package com.example.gametrack.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.gametrack.R;
import com.example.gametrack.activity.auth.LoginActivity;
import com.example.gametrack.data.dao.UsuarioDao;
import com.example.gametrack.data.model.local.Usuario;
import com.example.gametrack.data.storage.SecurePreferences;
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

        if (usuario != null) {
            String nomeUsuario = usuario.getNome();
            String emailUsuario = usuario.getEmail();
            String steamIdUsuario = usuario.getSteamId();
            String imagemPerfil = usuario.getImagemPerfil();

            textViewNomeUsuario.setText(nomeUsuario);
            textViewEmailUsuario.setText(emailUsuario);
            textViewSteamIdUsuario.setText(steamIdUsuario);

            if (getContext() != null && imagemPerfil != null && !imagemPerfil.isEmpty()) {
                Glide.with(getContext())
                        .load(imagemPerfil)
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

            profileImageView.setImageURI(Uri.parse(imagemPerfil));
            }

        else {
            textViewNomeUsuario.setText("Nome do Usuário");
            textViewEmailUsuario.setText("email@example.com");
            textViewSteamIdUsuario.setText("Steam ID: 123456789");

            Toast.makeText(getContext(), "Usuario não encontrado", Toast.LENGTH_SHORT).show();
        }

        Button logoutButton = view.findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(V -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(getContext(), "Logout realizado com sucesso", Toast.LENGTH_SHORT).show();

            SecurePreferences.clear();

            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

    }
}