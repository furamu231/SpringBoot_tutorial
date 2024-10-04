package com.example.sample1app;

import java.io.Serializable;
import java.util.List;

public interface PersonDAO <T> extends Serializable {

    // 全て取得
    public List<T> getAll();    

    // idで検索
    public T findById(long id);

    // 名前で検索
    public List<T> findByName(String name);

    // JPQLの使用
    public List<T> find(String fste);

}

