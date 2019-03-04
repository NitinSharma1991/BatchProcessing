package com.onlinetutorialspoint.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Item1")
@NoArgsConstructor
@AllArgsConstructor
public class Item2 {

    @Temporal(TemporalType.TIMESTAMP)
    Date date;
    @Id
    private int id;
    private String name;
    private String category;

}
