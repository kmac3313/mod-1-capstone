package com.techelevator;

import java.math.BigDecimal;

public class Drinks extends Item {
    public Drinks(String id, String name, BigDecimal price, int quantity) {
        super(id, name, price, quantity);
    }

    @Override
    public String getMessage() {
        return "Glug Glug, Yum!";
    }

    public static Drinks[] drinkProducts() {
        Drinks[] drinks = new Drinks[4];
        drinks[0] = new Drinks("C1", "Cola", BigDecimal.valueOf(1.25), 5);
        drinks[1] = new Drinks("C2", "Dr.Salt", BigDecimal.valueOf(1.50), 5);
        drinks[2] = new Drinks("C3", "Mountain Melter", BigDecimal.valueOf(1.50), 5);
        drinks[3] = new Drinks("C4", "Heavy", BigDecimal.valueOf(1.50), 5);

        return drinks;
    }
}
