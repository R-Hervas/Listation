package com.multimed.listation.entities;

import java.io.Serializable;

public class Item implements Serializable {

    private Integer id;
    private String name;
    private Integer amount;
    private Integer idList;

    public Item(Integer id, String name, Integer amount, Integer idList) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.idList = idList;
    }

    //GETTERS & SETTERS

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getIdList() {
        return idList;
    }

    public void setIdList(Integer idList) {
        this.idList = idList;
    }
}
