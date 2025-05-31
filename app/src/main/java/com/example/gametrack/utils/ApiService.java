package com.example.gametrack.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiService {

    public interface ApiCallback {
        void onSuccess(JSONObject response);
        void onError(String errorMessage);
    }

    private static ApiService instance;
    private final RequestQueue requestQueue;

    private ApiService(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized ApiService getInstance(Context context) {
        if (instance == null) {
            instance = new ApiService(context);
        }
        return instance;
    }

    public void get(String url, Map<String, String> headers, ApiCallback callback) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> callback.onSuccess(response),
                error -> callback.onError(parseVolleyError(error))
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return headers != null ? headers : new HashMap<>();
            }
        };

        requestQueue.add(request);
    }

    public void post(String url, JSONObject jsonBody, Map<String, String> headers, ApiCallback callback) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> callback.onSuccess(response),
                error -> callback.onError(parseVolleyError(error))
        ) {
            @Override
            public Map<String, String> getHeaders() {
                return headers != null ? headers : new HashMap<>();
            }
        };

        requestQueue.add(request);
    }

    private String parseVolleyError(VolleyError error) {
        if (error.networkResponse != null) {
            return "Erro " + error.networkResponse.statusCode;
        } else if (error.getCause() != null) {
            return error.getCause().getMessage();
        } else {
            return "Erro desconhecido";
        }
    }
}
