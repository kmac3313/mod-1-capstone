package com.techelevator;

import java.math.BigDecimal;

public class Candy extends Item {
    public Candy(String id, String name, BigDecimal price, int quantity) {
        super(id, name, price, quantity);
    }

    @Override
    public String getMessage() {
        return "Munch Munch, Yum!";
    }
    public static Candy[] candyProducts() {
        Candy[] candies = new Candy[4];
        candies[0] = new Candy("B1", "Moonpies", BigDecimal.valueOf(1.80), 5);
        candies[1] = new Candy("B2", "Cowtales", BigDecimal.valueOf(1.50), 5);
        candies[2] = new Candy("B3", "Wonka Bar", BigDecimal.valueOf(1.50), 5);
        candies[3] = new Candy("B4", "Crunchie", BigDecimal.valueOf(1.75), 5);

        return candies;
    }
}
