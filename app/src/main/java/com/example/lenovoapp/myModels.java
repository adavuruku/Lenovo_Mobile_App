package com.example.lenovoapp;

public class myModels {
    String itemId;
    String brand;
    String description;
    byte[] images;

    public myModels(String itemId, String brand, String description, byte[] images) {
        this.itemId = itemId;
        this.brand = brand;
        this.description = description;
        this.images = images;
    }

    public String getItemId() {
        return itemId;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImages() {
        return images;
    }
}
