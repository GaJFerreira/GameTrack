package com.example.gametrack.data.repository;

import android.content.Context;

import com.example.gametrack.data.dao.JogoDao;
import com.example.gametrack.data.model.local.Jogo;

import java.util.List;

public class JogoRepository {

    private final JogoDao jogoDao;

    public JogoRepository(Context context) {
        this.jogoDao = new JogoDao(context);
    }

    public void salvarJogo(Jogo jogo) {
        jogoDao.salvarJogo(jogo);
    }

    public void atualizarJogo(Jogo jogo) {
        jogoDao.atualizarJogo(jogo);
    }

    public void excluirJogo(long id) {
        jogoDao.excluirJogo(id);
    }

    public Jogo buscarPorId(long id) {
        return jogoDao.buscarPorId(id);
    }

    public List<Jogo> listarJogos() {
        return jogoDao.listarJogos();
    }
}
