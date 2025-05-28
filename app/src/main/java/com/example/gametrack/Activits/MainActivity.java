package com.example.gametrack.Activits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gametrack.R;
import com.example.gametrack.dao.UsuarioDao;
import com.example.gametrack.entidades.Usuario;
import com.example.gametrack.utils.ConexaoDb;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        EditText nomeUsuario;
        EditText emailUsuario;
        EditText senhaUsuario;

        Button btnCadastro;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        UsuarioDao usuarioDao = new UsuarioDao();

        nomeUsuario = findViewById(R.id.nomeUsuario);
        emailUsuario = findViewById(R.id.emailUsuario);
        senhaUsuario = findViewById(R.id.senhaUsuario);


        btnCadastro = findViewById(R.id.btnCadastro);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario usuario = new Usuario();

                String nome = nomeUsuario.getText().toString();
                usuario.setNome(nome);
                String email = emailUsuario.getText().toString();
                usuario.setEmail(email);
                String senha = senhaUsuario.getText().toString();
                usuario.setSenha(senha);

                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                    Toast.makeText(MainActivity.this,"Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(usuarioDao.buscarUsuarioPorEmail(email) != null){
                    Toast.makeText(MainActivity.this,"Email já cadastrado", Toast.LENGTH_SHORT).show();
                    return;
                }


                usuarioDao.salvarUsuario(usuario);
                Toast.makeText(MainActivity.this,"Adicionao", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(MainActivity.this, UsuarioActivity.class);
                startActivity(intent);
            }
        });


        new ConexaoDb(getApplicationContext(), "gametrack.db", null, 1);
    }
}