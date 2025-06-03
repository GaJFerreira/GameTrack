package com.example.gametrack.activity;

import android.content.Intent;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.gametrack.R;
import com.example.gametrack.databinding.ActivityMainBinding;
import com.example.gametrack.service.SincronizarBibliotecaSteamService;
import com.example.gametrack.data.local.ConexaoDb;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;

    private ConexaoDb conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Button btnCadastro;

      // conexao = new ConexaoDb(getApplicationContext(), "gametrack.db", null, 2);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        initNavigation();
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

//        Intent intent = new Intent(MainActivity.this, PesquisaJogo.class);
//        startActivity(intent);
//            }
//        });
//
//

    }

    private void initNavigation(){
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController  = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
    }


}