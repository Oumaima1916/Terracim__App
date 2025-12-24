package com.myapp.model;

import javafx.beans.property.*;
import javafx.beans.binding.Bindings;

public class MaterialRow {

    private final StringProperty material = new SimpleStringProperty("");
    private final StringProperty supplier = new SimpleStringProperty("");
    private final DoubleProperty quantity = new SimpleDoubleProperty(0);
    private final DoubleProperty unitPrice = new SimpleDoubleProperty(0);
    private final DoubleProperty total = new SimpleDoubleProperty(0);

    public MaterialRow() {
        total.bind(quantity.multiply(unitPrice));
    }

    /* ================= BIND TEXTFIELDS ================= */
    public void bind(StringProperty qtyText, StringProperty priceText) {

        quantity.bind(
                Bindings.createDoubleBinding(() -> {
                    try {
                        return Double.parseDouble(qtyText.get());
                    } catch (Exception e) {
                        return 0.0;
                    }
                }, qtyText)
        );

        unitPrice.bind(
                Bindings.createDoubleBinding(() -> {
                    try {
                        return Double.parseDouble(priceText.get());
                    } catch (Exception e) {
                        return 0.0;
                    }
                }, priceText)
        );
    }

    /* ================ GETTERS================= */

    public StringProperty materialProperty() {
        return material;
    }

    public StringProperty supplierProperty() {
        return supplier;
    }

    public DoubleProperty quantityProperty() {
        return quantity;
    }

    public DoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public double getTotal() {
        return total.get();
    }
}
