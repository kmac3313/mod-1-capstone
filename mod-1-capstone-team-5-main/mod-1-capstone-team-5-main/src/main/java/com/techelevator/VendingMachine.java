package com.techelevator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {
    private static final String MAIN_MENU_LIST_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_PURCHASE_MENU = "Purchase";
    private static final String MAIN_MENU_EXIT = "Exit";
    private static final String MAIN_MENU_SALES_REPORT = "***Sales Report";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_LIST_ITEMS, MAIN_MENU_PURCHASE_MENU, MAIN_MENU_EXIT, MAIN_MENU_SALES_REPORT};
    private static final String PURCHASE_MENU_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_END_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_FEED_MONEY, PURCHASE_MENU_SELECT_PRODUCT, PURCHASE_MENU_END_TRANSACTION};
    private static final String[] PRODUCT_ID = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4", "D1", "D2", "D3", "D4"};
    private static final BigDecimal NICKEL_VALUE = BigDecimal.valueOf(0.05);
    private static final BigDecimal DIME_VALUE = BigDecimal.valueOf(0.10);
    private static final BigDecimal QUARTER_VALUE = BigDecimal.valueOf(0.25);
    private boolean running = false;
    private Map<String, Item> items;
    private BigDecimal balance = BigDecimal.valueOf(0);
    private String productID = "No Item Selected";
    private FileLoader fl;
    private String[] activeMenu = MAIN_MENU_OPTIONS;

    public VendingMachine() {
        items = new HashMap<>();
        initializeItems();
    }

    private void initializeItems() {
        Chips[] chips = Chips.chipProducts();
        for (Chips chip : chips) {
            items.put(chip.getId(), chip);
        }

        Candy[] candies = Candy.candyProducts();
        for (Candy candy : candies) {
            items.put(candy.getId(), candy);
        }

        Drinks[] drinks = Drinks.drinkProducts();
        for (Drinks drink : drinks) {
            items.put(drink.getId(), drink);
        }

        Gum[] gums = Gum.gumProducts();
        for (Gum gum : gums) {
            items.put(gum.getId(), gum);
        }
    }
    public static void main(String[] args) {
        VendingMachine app = new VendingMachine();

        app.run();
    }

    public void run() {
        Scanner userInput = new Scanner(System.in);
        this.running = true;
        this.fl = new FileLoader();

        while (running) {
            displayMenu();
            Integer userEntry = 0;

            try {
                userEntry = Integer.parseInt(userInput.nextLine());

            } catch (NumberFormatException nfe) {
                userEntry = 0;
            }

            String chosenOption = activeMenu[userEntry - 1];

            if (activeMenu == MAIN_MENU_OPTIONS) {
                switch (chosenOption) {
                    case MAIN_MENU_LIST_ITEMS:
                        fl.loadStockList();
                        break;

                    case MAIN_MENU_PURCHASE_MENU:
                        activeMenu = PURCHASE_MENU_OPTIONS;
                        break;

                    case MAIN_MENU_EXIT:
                        System.out.println("Goodbye!");
                        System.exit(0);
                        break;

                    case MAIN_MENU_SALES_REPORT:
                        //Make the sales report function
                        System.out.println("Sales Report... If I had one :c\n");
                        break;
                }
            } else if (activeMenu == PURCHASE_MENU_OPTIONS) {
                switch (chosenOption) {
                    case PURCHASE_MENU_FEED_MONEY:
                        BigDecimal beforeFeed = balance;
                        System.out.print("Insert your cash or coins!: ");
                        try {
                            BigDecimal moneyToAdd = new BigDecimal((userInput.nextLine()));
                            BigDecimal zero = new BigDecimal(0);

                            if (moneyToAdd.compareTo(zero) > 0) {
                                balance = moneyToAdd.add(balance);

                            } else {
                                System.out.println("Invalid entry. Try again.");

                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());

                        }
                        writeToLog("FEED MONEY", beforeFeed, balance);
                        activeMenu = PURCHASE_MENU_OPTIONS;
                        break;

                    case PURCHASE_MENU_SELECT_PRODUCT:
                        selectProduct(userInput);
                        activeMenu = PURCHASE_MENU_OPTIONS;
                        break;

                    case PURCHASE_MENU_END_TRANSACTION:
                        BigDecimal beforeChange = balance;
                        endTransaction();
                        writeToLog("GIVE CHANGE", beforeChange, balance);
                        activeMenu = MAIN_MENU_OPTIONS;
                        break;
                }
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n");
        System.out.println("Current Money Provided: $" + balance);
        System.out.println("Current Product Selected: " + productID);

        for (int i = 0; i < activeMenu.length; i++) {
            String menuOptionNumber = "(" + (i + 1) + ") ";

            if (!activeMenu[i].startsWith("***")) {
                System.out.println(menuOptionNumber + activeMenu[i]);
            }
        }
        System.out.println("\n");
    }

    private void selectProduct(Scanner userInput) {
        System.out.print("Please enter the item you want: ");
        String selectedProductID = userInput.nextLine();
        Item selectedProduct = items.get(selectedProductID);
        BigDecimal selectedProductPrice = selectedProduct.getPrice();

        if (selectedProduct == null) {
            System.out.println("Invalid Product ID");
        }else if(balance.compareTo(selectedProduct.getPrice()) < 0) {
            System.out.println("Insufficient funds : Insert More Money ");
        }else if(selectedProduct.getQuantity() == 0) {
            System.out.println("OUT OF STOCK");
        } else {
            BigDecimal beforePurchase = selectedProductPrice;
            balance = balance.subtract(selectedProductPrice);
            productID = selectedProduct.getName();
            System.out.println(selectedProduct.getName());
            System.out.println(selectedProduct.getMessage());
            selectedProduct.dispense();

            writeToLog(selectedProduct.getName() + " " + selectedProduct.getId(), beforePurchase, balance);
        }
    }

    private void endTransaction() {
        BigDecimal change = balance;
        balance = BigDecimal.valueOf(0);
        productID = "No Item Selected";
        System.out.println("Change: $" + change);

        int quarters = change.divide(QUARTER_VALUE, 0, BigDecimal.ROUND_DOWN).intValue();
        change = change.subtract(QUARTER_VALUE.multiply(BigDecimal.valueOf(quarters)));

        int dimes = change.divide(DIME_VALUE, BigDecimal.ROUND_DOWN).intValue();
        change = change.subtract(DIME_VALUE.multiply(BigDecimal.valueOf(dimes)));

        int nickels = change.divide(NICKEL_VALUE, BigDecimal.ROUND_DOWN).intValue();

        System.out.println("Quarters: " + quarters);
        System.out.println("Dimes: " + dimes);
        System.out.println("Nickels: " + nickels);
    }

    private void writeToLog(String action, BigDecimal beforeAmount, BigDecimal afterAmount) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss");
        String formattedDateTime = now.format(formatter);

        try (PrintWriter logWriter = new PrintWriter(new FileWriter("Log.txt", true))) {
            logWriter.println(formattedDateTime + " " + action + ": $" + beforeAmount + " $" + afterAmount);
        } catch (IOException iox) {
            System.out.println("Unable to write to file\n" + iox.getMessage());
        }
    }
}
