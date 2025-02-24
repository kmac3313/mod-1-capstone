package com.techelevator;

import java.math.BigDecimal;

public class Chips extends Item {
    public Chips(String id, String name, BigDecimal price, int quantity) {
        super(id, name, price, quantity);
    }

    @Override
    public String getMessage() {
        return "Crunch Crunch, Yum!";
    }

    public static Chips[] chipProducts() {
        Chips[] chips = new Chips[4];
        chips[0] = new Chips("A1", "Potato Crisps", BigDecimal.valueOf(3.05), 5);
        chips[1] = new Chips("A2", "Stackers", BigDecimal.valueOf(1.45), 5);
        chips[2] = new Chips("A3", "Grain Waves", BigDecimal.valueOf(2.75), 5);
        chips[3] = new Chips("A4", "Cloud Popcorn", BigDecimal.valueOf(3.65), 5);

        return chips;
    }
}
