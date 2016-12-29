package com.mtaylord.todo.data.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by taylor on 12/29/16.
 */

@Data
@NoArgsConstructor
public class TodoList {
    private int id;
    private String name;
    private Date created;
    private Date updated;
    private List<Item> doneItems;
    private List<Item> todoItems;
}
