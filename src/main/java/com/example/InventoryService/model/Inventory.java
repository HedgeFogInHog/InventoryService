package com.example.InventoryService.model;

public class Inventory {
    private String name;
    private int amount;

    public Inventory(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }


    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
