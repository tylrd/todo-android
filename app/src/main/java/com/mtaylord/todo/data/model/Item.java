package com.mtaylord.todo.data.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private int id;
    private String name;
    private String description;
    private boolean complete;
    private Date created;
    private Date updated;

    private boolean selected;

    public Item(String name, boolean complete) {
        this.name = name;
        this.complete = complete;
    }
}
