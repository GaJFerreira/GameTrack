package com.example.gametrack.data.interfaces;

import com.example.gametrack.data.model.local.Jogo;

import java.util.List;

public interface iJogo {

    void salvarJogo(Jogo jogo);

    void atualizarJogo(Jogo jogo);

    void excluirJogo(long id);

    Jogo buscarPorId(long id);

    List<Jogo> listarJogos();
}
