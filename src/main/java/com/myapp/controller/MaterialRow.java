package com.myapp.model;
import javafx.beans.property.*;

public class MaterialRow {

    private final StringProperty material = new SimpleStringProperty("");
    private final StringProperty supplier = new SimpleStringProperty("");
    private final DoubleProperty quantity = new SimpleDoubleProperty(0);
    private final DoubleProperty unitPrice = new SimpleDoubleProperty(0);
    private final DoubleProperty total = new SimpleDoubleProperty(0);

    public MaterialRow() {
        total.bind(quantity.multiply(unitPrice));
    }

    public StringProperty materialProperty() { return material; }
    public StringProperty supplierProperty() { return supplier; }
    public DoubleProperty quantityProperty() { return quantity; }
    public DoubleProperty unitPriceProperty() { return unitPrice; }
    public DoubleProperty totalProperty() { return total; }

    public double getTotal() {
        return total.get();
    }
}
