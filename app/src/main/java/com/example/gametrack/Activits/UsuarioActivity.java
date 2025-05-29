package com.example.gametrack.Activits;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gametrack.R;

public class UsuarioActivity extends AppCompatActivity {

    private EditText buscarId;
    private Button btnBuscarId, btnListar, btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
    }
}
