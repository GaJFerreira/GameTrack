package com.example.gametrack.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SecurePreferences {

    private static final String PREF_NAME = "secure_user_prefs";
    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        if (sharedPreferences == null) {
            try {
                String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                sharedPreferences = EncryptedSharedPreferences.create(
                        PREF_NAME,
                        masterKeyAlias,
                        context,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException("Erro ao inicializar EncryptedSharedPreferences", e);
            }
        }
    }

    public static void save(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String get(String key) {
        return sharedPreferences.getString(key, null);
    }

    public static void clear() {
        sharedPreferences.edit().clear().apply();
    }
}
