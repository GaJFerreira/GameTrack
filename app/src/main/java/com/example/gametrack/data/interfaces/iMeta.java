package com.example.gametrack.data.interfaces;

import com.example.gametrack.data.model.local.Meta;

import java.util.List;
import java.util.Map;

public interface iMeta {

    void salvarMeta(Meta meta);

    void atualizarMeta(Meta meta);

    void excluirMeta(long id);

    Meta buscarMetaPorId(long id);

    Map<String, List<Meta>> listarMetasPorUsuarioAgrupadasPorDia(long id);
}
