package com.example.gametrack.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gametrack.App;
import com.example.gametrack.R;
import com.example.gametrack.data.model.remote.SteamResponseDetalhesJogo;
import com.example.gametrack.service.SteamService;
import com.google.gson.Gson;

import org.json.JSONObject;

public class DetalhesJogo extends AppCompatActivity {

    private PlayerView playerView;
    private ExoPlayer player;
    ImageView imagemTopo;
    private TextView textoTitulo;
    private TextView textoDescricao;
    private TextView textoAno;
    private TextView textoDesenvolvedora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_jogo);

        playerView = findViewById(R.id.player_view);
        textoTitulo = findViewById(R.id.texto_titulo);
        textoAno = findViewById(R.id.texto_ano);
        textoDesenvolvedora = findViewById(R.id.texto_desenvolvedora);
        textoDescricao = findViewById(R.id.texto_descricao);

        long steamId = getIntent().getLongExtra("appSteamId", -1);

        buscarDetalhesDoJogo(steamId);

        ImageButton btnVoltar = findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(DetalhesJogo.this, PesquisaJogo.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void buscarDetalhesDoJogo(long appSteamId) {
        SteamService steamValidator = new SteamService(App.getContext());

        steamValidator.buscarDetalhesDoJogo(appSteamId, new SteamService.SteamValidationCallback() {
            @Override
            public void onValid(JSONObject steamData) {
                Gson gson = new Gson();
                SteamResponseDetalhesJogo steamResponse = gson.fromJson(steamData.toString(), SteamResponseDetalhesJogo.class);

                player = new ExoPlayer.Builder(App.getContext()).build();
                playerView.setPlayer(player);

                String videoUrl = "";

                if (steamResponse.movies != null && !steamResponse.movies.isEmpty() && steamResponse.movies.get(0).mp4 != null) {
                    videoUrl = steamResponse.movies.get(0).mp4.max;
                }

                if (videoUrl == null || videoUrl.isEmpty()) {
                    imagemTopo = findViewById(R.id.imagem_topo);

                    playerView.setVisibility(View.GONE);
                    imagemTopo.setVisibility(View.VISIBLE);

                    Glide.with(App.getContext()).load(steamResponse.header_image)
                            .into(imagemTopo);
                } else {
                    MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));
                    player.setVolume(0f);
                    player.setMediaItem(mediaItem);
                    player.prepare();
                    player.play();
                }

                textoTitulo.setText(steamResponse.name);

                textoDescricao.setText(steamResponse.short_description);

                if (steamResponse.release_date != null && steamResponse.release_date.date != null) {
                    String[] parts = steamResponse.release_date.date.split(" ");
                    String lastPart = parts.length > 0 ? parts[parts.length - 1] : "";
                    textoAno.setText(lastPart);
                } else {
                    textoAno.setText("");
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                    textoDesenvolvedora.setText(steamResponse.developers.getFirst());
                }
            }

            @Override
            public void onInvalid(String errorMessage) {
                Toast.makeText(DetalhesJogo.this, "Erro ao validar Steam ID: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
