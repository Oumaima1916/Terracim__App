package com.myapp.model;


public class BudgetItem {

    private String material;
    private String quantity;
    private String price;
    private String supplier;
    private String total;

    public BudgetItem(String material, String quantity, String price,
                      String supplier, String total) {
        this.material = material;
        this.quantity = quantity;
        this.price = price;
        this.supplier = supplier;
        this.total = total;
    }

    public String getMaterial() { return material; }
    public String getQuantity() { return quantity; }
    public String getPrice() { return price; }
    public String getSupplier() { return supplier; }
    public String getTotal() { return total; }
}
