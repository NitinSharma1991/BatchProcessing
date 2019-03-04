package com.onlinetutorialspoint.Processor;

import com.onlinetutorialspoint.model.Item;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<Item, Item> {

    private static final Map<String, String> Item_Names = new HashMap<>();

    public Processor() {
        Item_Names.put("10", "Food");
        Item_Names.put("20", "Money");

    }

    @Override
    public Item process(Item item) throws Exception {
        String id = String.valueOf(item.getId());
        String category = Item_Names.get(id);
        item.setCategory(category);
        return item;
    }
}
