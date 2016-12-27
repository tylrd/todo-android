package com.mtaylord.todo.list;

import com.mtaylord.todo.data.model.Item;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by taylordaugherty on 12/24/16.
 */

@Data
@AllArgsConstructor
public class ItemsCompleteEvent {
    private final List<Item> items;
}
