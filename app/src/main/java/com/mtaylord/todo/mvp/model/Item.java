package com.mtaylord.todo.mvp.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private String name;
    private String description;
    private boolean complete;
    private Date created;
    private Date updated;
}
