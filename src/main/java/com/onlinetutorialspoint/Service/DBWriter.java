package com.onlinetutorialspoint.Service;

import com.onlinetutorialspoint.model.Item;
import com.onlinetutorialspoint.repository.ItemRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class DBWriter implements ItemWriter<Item> {

    File file = new File("C:\\Intellij Code\\BatchProcessing\\src\\main\\resources\\Item1.csv");

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void write(List<? extends Item> items) throws Exception {
        itemRepository.saveAll(items);
        FileWriter fileWriter = new FileWriter(file);
        items.forEach(a -> {
            try {
                fileWriter.append(String.valueOf(a.getId()));
                fileWriter.append(",");
                fileWriter.append(a.getCategory());
                fileWriter.append(",");
                fileWriter.append(a.getName());
                fileWriter.append("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileWriter.flush();
        fileWriter.close();
        System.out.println(items);
    }
}
