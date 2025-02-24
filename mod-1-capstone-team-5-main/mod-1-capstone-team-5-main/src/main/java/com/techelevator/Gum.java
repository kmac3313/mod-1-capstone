package com.techelevator;

import java.math.BigDecimal;

public class Gum extends Item {
    public Gum(String id, String name, BigDecimal price, int quantity) {
        super(id, name, price, quantity);
    }

    @Override
    public String getMessage() {
        return "Chew Chew, Yum!";
    }
    public static Gum[] gumProducts() {
        Gum[] gums = new Gum[4];
        gums[0] = new Gum("D1", "U-Chews", BigDecimal.valueOf(0.85), 5);
        gums[1] = new Gum("D2", "Little League Chew", BigDecimal.valueOf(0.95), 5);
        gums[2] = new Gum("D3", "Chiclets", BigDecimal.valueOf(0.75), 5);
        gums[3] = new Gum("D4", "Triplemint", BigDecimal.valueOf(0.75), 5);

        return gums;
    }
}
