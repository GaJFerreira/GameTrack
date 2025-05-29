package com.example.gametrack.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gametrack.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        autenticacao = FirebaseAuth.getInstance();

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText senhaEditText = findViewById(R.id.senhaEditText);
        EditText confirmarSenhaEditText = findViewById(R.id.confirmarSenhaEditText);
        Button cadastrarButton = findViewById(R.id.cadastrarButton);
        TextView loginText = findViewById(R.id.loginText);

        cadastrarButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String senha = senhaEditText.getText().toString().trim();
            String confirmarSenha = confirmarSenhaEditText.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confirmarSenha)) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                return;
            }

            cadastrarUsuario(email, senha);
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
                        FirebaseUser usuario = autenticacao.getCurrentUser();
                        Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CadastroActivity.this, "Erro ao cadastrar: " + tarefa.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("Cadastro", "Erro ao cadastrar", tarefa.getException());
                    }
                });
    }
}
