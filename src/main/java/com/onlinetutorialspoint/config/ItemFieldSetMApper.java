package com.onlinetutorialspoint.config;

import com.onlinetutorialspoint.model.Item;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ItemFieldSetMApper implements org.springframework.batch.item.file.mapping.FieldSetMapper<com.onlinetutorialspoint.model.Item> {

    @Override
    public Item mapFieldSet(FieldSet fieldSet) throws BindException {

        Item item = new Item();
        item.setId(fieldSet.readInt("id"));
        item.setName(fieldSet.readString("name"));
        item.setCategory(fieldSet.readString("category"));
        return item;

    }
}
