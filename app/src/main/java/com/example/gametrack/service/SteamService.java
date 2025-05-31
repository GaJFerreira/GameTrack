package com.example.gametrack.service;

import android.content.Context;
import android.util.Log;

import com.example.gametrack.utils.ApiService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SteamService {

    public interface SteamValidationCallback {
        void onValid(JSONObject steamData);
        void onInvalid(String errorMessage);
    }

    private final Context context;

    private final String TAG = "SteamService";

    public SteamService(Context context) {
        this.context = context;
    }

    public void bucarUsuario(String steamId, SteamValidationCallback callback) {
        String url = "http://10.0.2.2:8000/user/" + steamId;

        Map<String, String> headers = new HashMap<>();

        ApiService.getInstance(context).get(url, headers, new ApiService.ApiCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                callback.onValid(response);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("SteamValidator", "Erro ao validar Steam ID: " + errorMessage);
                callback.onInvalid(errorMessage);
            }
        });
    }

    public void buscarBibliotecaDoUsuario(String steamId, SteamValidationCallback callback) {
        String url = "http://10.0.2.2:8000/library/" + steamId;

        Map<String, String> headers = new HashMap<>();

        ApiService.getInstance(context).get(url, headers, new ApiService.ApiCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                callback.onValid(response);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Erro ao buscar biblioteca do usuario: " + errorMessage);
                callback.onInvalid(errorMessage);
            }
        });
    }
}
