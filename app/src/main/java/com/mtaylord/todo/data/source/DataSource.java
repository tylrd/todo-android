package com.mtaylord.todo.data.source;

import java.util.List;

/**
 * Created by taylordaugherty on 12/26/16.
 */

public interface DataSource<T> {

    T create(T data);

    T get(int id);

    List<T> getAll();

    void delete(int id);

    void deleteAll(List<T> data);

    T update(T data);

}
