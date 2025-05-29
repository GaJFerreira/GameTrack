package com.example.gametrack.interfaces;

import com.example.gametrack.entidades.Meta;

import java.util.List;

public interface iMeta {

    void salvarMeta(Meta meta);

    void atualizarMeta(Meta meta);

    void excluirMeta(long id);

    Meta biscarPorId(long id);

    List<Meta> listarMetas();

}
