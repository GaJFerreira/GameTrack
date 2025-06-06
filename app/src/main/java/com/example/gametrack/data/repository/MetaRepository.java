package com.example.gametrack.data.repository;

import android.content.Context;
import android.util.Log;

import com.example.gametrack.data.dao.MetaDao;
import com.example.gametrack.data.model.local.Meta;

import java.util.List;

public class MetaRepository {

    private final MetaDao metaDao;

    public MetaRepository(Context context) {
        this.metaDao = new MetaDao(context);
    }

    public void salvarMeta(Meta meta) {
        metaDao.salvarMeta(meta);
    }

    public void atualizarMeta(Meta meta) {
        metaDao.atualizarMeta(meta);
    }

    public void excluirMeta(long id) {
        metaDao.excluirMeta(id);
    }

    public List<Meta> listarMetas() {
        return metaDao.listarMetas();
    }


}
