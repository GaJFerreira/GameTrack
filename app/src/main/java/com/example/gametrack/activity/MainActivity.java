package com.example.gametrack.activity;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gametrack.App;
import com.example.gametrack.data.model.local.Jogo;
import com.example.gametrack.data.model.remote.SteamResponseBiblioteca;
import com.example.gametrack.data.repository.JogoRepository;
import com.example.gametrack.data.storage.SecurePreferences;
import com.example.gametrack.service.SteamService;
import com.example.gametrack.utils.AppExecutors;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

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

//        this.sincronizarBibliotecaSteam();

        Intent intent = new Intent(MainActivity.this, PesquisaJogo.class);
        startActivity(intent);
//            }
//        });
//
//
//        new ConexaoDb(getApplicationContext(), "gametrack.db", null, 1);
    }

    private void sincronizarBibliotecaSteam() {
        SteamService steamValidator = new SteamService(App.getContext());
        String steamId = SecurePreferences.get("steamid");
        JogoRepository jogoRepository = new JogoRepository(App.getContext());

        AppExecutors.getDatabaseExecutor().execute(() -> {
            steamValidator.buscarBibliotecaDoUsuario(steamId, new SteamService.SteamValidationCallback() {
                @Override
                public void onValid(JSONObject steamData) {
                    Gson gson = new Gson();
                    SteamResponseBiblioteca steamResponse = gson.fromJson(steamData.toString(), SteamResponseBiblioteca.class);

                    List<SteamResponseBiblioteca.Game> jogos = steamResponse.getResponse().getGames();

                    for (SteamResponseBiblioteca.Game game : jogos) {
                        Jogo jogo = new Jogo(
                                game.getName(),                            // Nome do jogo
                                game.getAppid(),                           // ID Steam
                                "https://media.steampowered.com/steamcommunity/public/images/apps/"
                                        + game.getAppid() + "/" + game.getImgIconUrl() + ".jpg" // URL do ícone
                        );

                        jogoRepository.salvarJogo(jogo);
                    }
                }

                @Override
                public void onInvalid(String errorMessage) {
                    Toast.makeText(MainActivity.this, "Erro ao validar Steam ID: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}