package com.company;

public class Item {
    private String itemName, manufacturerName, manufacturerPAN; //PAN - Payer Account Number (Учетный Номер Плательщика)
    private String itemsInStock, stockAddress;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerPAN() {
        return manufacturerPAN;
    }

    public void setManufacturerPAN(String manufacturerPAN) {
        this.manufacturerPAN = manufacturerPAN;
    }

    public String getItemsInStock() {
        return itemsInStock;
    }

    public void setItemsInStock(String itemsInStock) {
        this.itemsInStock = itemsInStock;
    }

    public String getStockAddress() {
        return stockAddress;
    }

    public void setStockAddress (String stockAddress) {
        this.stockAddress = stockAddress;
    }
}
