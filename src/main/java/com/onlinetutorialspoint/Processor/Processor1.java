package com.onlinetutorialspoint.Processor;

import com.onlinetutorialspoint.model.Item2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Processor1 implements ItemProcessor<Item2, Item2> {

    private static final Map<String, String> Item_Names = new HashMap<>();

    public Processor1() {
        Item_Names.put("10", "Food");
        Item_Names.put("20", "Money");

    }

    @Override
    public Item2 process(Item2 item) throws Exception {
        String id = String.valueOf(item.getId());
        String category = Item_Names.get(id);
        item.setCategory(category);
        item.setDate(new Date());
        return item;
    }
}
