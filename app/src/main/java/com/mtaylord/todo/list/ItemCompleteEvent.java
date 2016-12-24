package com.mtaylord.todo.list;

import com.mtaylord.todo.data.model.Item;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by taylordaugherty on 12/24/16.
 */

@Data
@AllArgsConstructor
public class ItemCompleteEvent {
    private final Item item;
}
