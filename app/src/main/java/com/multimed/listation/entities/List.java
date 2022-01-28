package com.multimed.listation.entities;

public class List {

    private Integer id;
    private String name;

    public List(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // GETTERS & SETTERS

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
