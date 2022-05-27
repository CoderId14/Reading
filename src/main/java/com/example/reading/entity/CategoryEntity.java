package com.example.reading.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="category")
@Data
public class CategoryEntity extends BaseEntity{
    @Column
    private String code;
    @Column
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<NewEntity> news;


}
