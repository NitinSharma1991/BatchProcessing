package com.onlinetutorialspoint.Service;

import com.onlinetutorialspoint.model.Item;
import com.onlinetutorialspoint.repository.ItemRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class DBWriter<I> implements ItemWriter<Item> {

    File file = new File("C:\\Intellij Code\\BatchProcessing\\src\\main\\resources\\Item1.csv");
    @Value("${input2}")
    Resource resource;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void write(List<? extends Item> items) throws Exception {
        itemRepository.saveAll(items);
        FlatFileItemWriter itemWriter = new FlatFileItemWriter();
        itemWriter.setResource(resource);
        DelimitedLineAggregator<Item> aggregator = new DelimitedLineAggregator<Item>();
        aggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<Item> fieldExtractor = new BeanWrapperFieldExtractor<Item>();
        fieldExtractor.setNames(new String[]{"id", "name", "category"});
        aggregator.setFieldExtractor(fieldExtractor);
        itemWriter.setLineAggregator(aggregator);
        /*FileWriter fileWriter = new FileWriter(file);
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
        System.out.println(items);*/
    }
}
