package com.onlinetutorialspoint.Service;

import com.onlinetutorialspoint.model.Item2;
import com.onlinetutorialspoint.repository.Item2Repository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbWriter1 implements ItemWriter<Item2> {

    @Autowired
    private Item2Repository itemRepository;

    @Override
    public void write(List<? extends Item2> items) throws Exception {
        itemRepository.saveAll(items);
        System.out.println(items);
    }
}
