package com.example.gametrack.data.model.local;

import java.util.List;

public class DiaLayout {
    private String data;
    private List<MetaLayout> metas;

    public DiaLayout(String data, List<MetaLayout> metas) {
        this.data = data;
        this.metas = metas;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<MetaLayout> getMetas() {
        return metas;
    }

    public void setMetas(List<MetaLayout> metas) {
        this.metas = metas;
    }
}
