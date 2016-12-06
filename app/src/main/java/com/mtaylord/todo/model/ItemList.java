package com.mtaylord.todo.model;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
    private static List<Item> list = new ArrayList<>();

    public static List<Item> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
}
