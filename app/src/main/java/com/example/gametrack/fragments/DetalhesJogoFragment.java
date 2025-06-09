package com.example.gametrack.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.bumptech.glide.Glide;
import com.example.gametrack.App;
import com.example.gametrack.R;
import com.example.gametrack.data.model.remote.SteamResponseDetalhesJogo;
import com.example.gametrack.service.SteamService;
import com.google.gson.Gson;

import org.json.JSONObject;

public class DetalhesJogoFragment extends Fragment {

    private PlayerView playerView;
    private ExoPlayer player;
    private ImageView imagemTopo;
    private TextView textoTitulo;
    private TextView textoDescricao;
    private TextView textoAno;
    private TextView textoDesenvolvedora;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalhes_jogo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playerView = view.findViewById(R.id.player_view);
        textoTitulo = view.findViewById(R.id.texto_titulo);
        textoAno = view.findViewById(R.id.texto_ano);
        textoDesenvolvedora = view.findViewById(R.id.texto_desenvolvedora);
        textoDescricao = view.findViewById(R.id.texto_descricao);
        imagemTopo = view.findViewById(R.id.imagem_topo);

        long steamId = requireArguments().getLong("appSteamId", -1);

        buscarDetalhesDoJogo(steamId);

        ImageButton btnVoltar = view.findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(v ->
                NavHostFragment.findNavController(DetalhesJogoFragment.this).navigateUp()
        );

        LinearLayout definirMetaButton = view.findViewById(R.id.definirMetaButton);
        definirMetaButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("appSteamId", steamId);

            NavHostFragment.findNavController(DetalhesJogoFragment.this)
                    .navigate(R.id.action_detalhesFragment_to_novaMetaFragment, bundle);
        });
    }

    private void buscarDetalhesDoJogo(long appSteamId) {
        SteamService steamValidator = new SteamService(App.getContext());

        steamValidator.buscarDetalhesDoJogo(appSteamId, new SteamService.SteamValidationCallback() {
            @Override
            public void onValid(JSONObject steamData) {
                Gson gson = new Gson();
                SteamResponseDetalhesJogo steamResponse = gson.fromJson(steamData.toString(), SteamResponseDetalhesJogo.class);

                player = new ExoPlayer.Builder(requireContext()).build();
                playerView.setPlayer(player);

                String videoUrl = "";

                if (steamResponse.movies != null
                        && !steamResponse.movies.isEmpty()
                        && steamResponse.movies.get(0).mp4 != null
                        && steamResponse.movies.get(0).mp4.max != null
                        && !steamResponse.movies.get(0).mp4.max.isEmpty()) {
                    videoUrl = steamResponse.movies.get(0).mp4.max;
                }

                if (videoUrl.isEmpty()) {
                    playerView.setVisibility(View.GONE);
                    imagemTopo.setVisibility(View.VISIBLE);

                    Glide.with(requireContext()).load(steamResponse.header_image)
                            .into(imagemTopo);
                } else {
                    playerView.setVisibility(View.VISIBLE);
                    imagemTopo.setVisibility(View.GONE);

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

                if (steamResponse.developers != null && !steamResponse.developers.isEmpty()) {
                    textoDesenvolvedora.setText(steamResponse.developers.get(0));
                } else {
                    textoDesenvolvedora.setText("");
                }
            }

            @Override
            public void onInvalid(String errorMessage) {
                Toast.makeText(requireContext(), "Erro ao validar Steam ID: " + errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
