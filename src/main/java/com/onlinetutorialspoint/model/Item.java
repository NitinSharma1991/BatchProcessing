package com.onlinetutorialspoint.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Item")
public class Item {
    @Id
    private int id;
    private String name;
    private String category;
    @Temporal(TemporalType.TIMESTAMP)
    Date date;

    public Item() {
    }

    public Item(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }


}
