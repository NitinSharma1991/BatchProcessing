package com.onlinetutorialspoint.Service;

import com.onlinetutorialspoint.model.Item;
import com.onlinetutorialspoint.repository.ItemRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<Item> {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void write(List<? extends Item> items) throws Exception {
        itemRepository.saveAll(items);
        System.out.println(items);
    }
}
