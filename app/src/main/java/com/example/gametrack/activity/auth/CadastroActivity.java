package com.example.gametrack.activity.auth;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gametrack.R;
import com.example.gametrack.data.model.remote.SteamResponseUsurario;
import com.example.gametrack.data.model.local.Usuario;
import com.example.gametrack.data.repository.UsuarioRepository;
import com.example.gametrack.service.SteamService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {

    private final FirebaseAuth autenticacao = FirebaseAuth.getInstance();
    private UsuarioRepository usuarioRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        usuarioRepository = new UsuarioRepository(getApplicationContext());

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText senhaEditText = findViewById(R.id.senhaEditText);
        EditText confirmarSenhaEditText = findViewById(R.id.confirmarSenhaEditText);

        EditText nomeEditText = findViewById(R.id.nomeEditText);
        EditText steamIdEditText = findViewById(R.id.steamIdEditText);

        Button cadastrarButton = findViewById(R.id.cadastrarButton);
        TextView loginText = findViewById(R.id.loginText);

        cadastrarButton.setOnClickListener(v -> {
            String nome = nomeEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String steamId = steamIdEditText.getText().toString().trim();
            String senha = senhaEditText.getText().toString().trim();
            String confirmarSenha = confirmarSenhaEditText.getText().toString().trim();

            if (email.isEmpty() || nome.isEmpty() || steamId.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            SteamService steamValidator = new SteamService(this);

            steamValidator.bucarUsuario(steamId, new SteamService.SteamValidationCallback() {
                @Override
                public void onValid(JSONObject steamData) {
                    Gson gson = new Gson();
                    SteamResponseUsurario steamResponse = gson.fromJson(steamData.toString(), SteamResponseUsurario.class);

                    List<SteamResponseUsurario.Player> players = steamResponse.getResponse().getPlayers();

                    if (!senha.equals(confirmarSenha)) {
                        Toast.makeText(CadastroActivity.this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (players == null || players.isEmpty()) {
                        Toast.makeText(CadastroActivity.this, "Nenhum jogador encontrado para o Steam ID informado.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Usuario usuario = null;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                        usuario = new Usuario(nome, steamId, email, players.getFirst().getAvatarmedium());
                    }

                    usuarioRepository.salvarUsuario(usuario);

                    cadastrarUsuario(email, senha);
                }

                @Override
                public void onInvalid(String errorMessage) {
                    Toast.makeText(CadastroActivity.this, "Erro ao validar Steam ID: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        });

        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void cadastrarUsuario(String email, String senha) {
        autenticacao.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, tarefa -> {
                    if (tarefa.isSuccessful()) {
//                        FirebaseUser usuario = autenticacao.getCurrentUser();
                        Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CadastroActivity.this, "Erro ao cadastrar: " + Objects.requireNonNull(tarefa.getException()).getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("Cadastro", "Erro ao cadastrar", tarefa.getException());
                    }
                });
    }
}
