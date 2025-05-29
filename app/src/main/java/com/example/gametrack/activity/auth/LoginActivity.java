package com.example.gametrack.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gametrack.activity.MainActivity;
import com.example.gametrack.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticacao = FirebaseAuth.getInstance();

        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();

//        if (usuarioAtual != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//            return;
//        }

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText senhaEditText = findViewById(R.id.senhaEditText);
        Button btnLogin = findViewById(R.id.loginButton);
        TextView cadastroText = findViewById(R.id.cadastroText);

        btnLogin.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String senha = senhaEditText.getText().toString().trim();

            if (!email.isEmpty() && !senha.isEmpty()) {
                btnLogin.setEnabled(false);
                entrar(email, senha, btnLogin);
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        cadastroText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
            startActivity(intent);
        });
    }

    private void entrar(String email, String senha, Button btnLogin) {
        autenticacao.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, tarefa -> {
                    btnLogin.setEnabled(true);
                    if (tarefa.isSuccessful()) {
                        Log.d(TAG, "entrarComEmail:sucesso");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Log.w(TAG, "entrarComEmail:falha", tarefa.getException());
                        Toast.makeText(LoginActivity.this, "Falha na autenticação.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
