package com.example.reading.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @OneToMany(mappedBy = "category")
    private List<NewEntity> news = new ArrayList<>();


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
