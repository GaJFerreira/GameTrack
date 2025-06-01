package com.example.gametrack.activity;

import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gametrack.service.SincronizarBibliotecaSteamService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Button btnCadastro;
//
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//
//        UsuarioDao usuarioDao = new UsuarioDao();
//
//        nomeUsuario = findViewById(R.id.nomeUsuario);
//        emailUsuario = findViewById(R.id.emailUsuario);
//        senhaUsuario = findViewById(R.id.senhaUsuario);
//
//
//        btnCadastro = findViewById(R.id.btnCadastro);
//
//        btnCadastro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Usuario usuario = new Usuario();
//
//                String nome = nomeUsuario.getText().toString();
//                usuario.setNome(nome);
//                String email = emailUsuario.getText().toString();
//                usuario.setEmail(email);
//                String senha = senhaUsuario.getText().toString();
//                usuario.setSenha(senha);
//
//                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
//                    Toast.makeText(MainActivity.this,"Preencha todos os campos", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if(usuarioDao.buscarUsuarioPorEmail(email) != null){
//                    Toast.makeText(MainActivity.this,"Email já cadastrado", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//
//                usuarioDao.salvarUsuario(usuario);
//                Toast.makeText(MainActivity.this,"Adicionao", Toast.LENGTH_SHORT).show();

        SincronizarBibliotecaSteamService sincronizarBibliotecaSteamService = new SincronizarBibliotecaSteamService();
        sincronizarBibliotecaSteamService.sincronizar();

        Intent intent = new Intent(MainActivity.this, PesquisaJogo.class);
        startActivity(intent);
//            }
//        });
//
//
//        new ConexaoDb(getApplicationContext(), "gametrack.db", null, 1);
    }

}