package com.example.gametrack.service;

import android.widget.Toast;

import com.example.gametrack.App;
import com.example.gametrack.data.model.local.Jogo;
import com.example.gametrack.data.model.remote.SteamResponseBiblioteca;
import com.example.gametrack.data.repository.JogoRepository;
import com.example.gametrack.data.storage.SecurePreferences;
import com.example.gametrack.utils.AppExecutors;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

public class SincronizarBibliotecaSteamService {

    private final SteamService steamValidator;
    private final String steamId;
    private final JogoRepository jogoRepository;

    public SincronizarBibliotecaSteamService() {
        this.steamValidator = new SteamService(App.getContext());
        this.steamId = SecurePreferences.get("steamid");
        this.jogoRepository = new JogoRepository(App.getContext());
    }

    public void sincronizar() {
        AppExecutors.getDatabaseExecutor().execute(() -> {
            steamValidator.buscarBibliotecaDoUsuario(steamId, new SteamService.SteamValidationCallback() {
                @Override
                public void onValid(JSONObject steamData) {
                    Gson gson = new Gson();
                    SteamResponseBiblioteca steamResponse = gson.fromJson(steamData.toString(), SteamResponseBiblioteca.class);

                    List<SteamResponseBiblioteca.Game> jogos = steamResponse.getResponse().getGames();

                    for (SteamResponseBiblioteca.Game game : jogos) {
                        Jogo jogo = new Jogo(
                                game.getName(),
                                game.getAppid(),
                                "https://media.steampowered.com/steamcommunity/public/images/apps/"
                                        + game.getAppid() + "/" + game.getImgIconUrl() + ".jpg"
                        );

                        jogoRepository.salvarJogo(jogo);
                    }
                }

                @Override
                public void onInvalid(String errorMessage) {
                    Toast.makeText(App.getContext(), "Erro ao validar Steam ID: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
