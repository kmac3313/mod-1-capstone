package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileLoader {
    private static final String DEFAULT_INVENTORY = "vendingmachine.csv";
    private Scanner fileScanner;

    public FileLoader() {
        try {
            this.fileScanner = new Scanner((new File(DEFAULT_INVENTORY)));

        } catch (FileNotFoundException fnex) {
            System.out.println(fnex.getMessage());
        }
    }

    public String[] loadStockList() {
        List<String> dataList = new ArrayList<>();
        try {

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                String[] data = line.split("\\|");
                String itemDetails = data[0] + "|" + data[1] + "|" + data[2];
                dataList.add(itemDetails);
            }

            for (String item : dataList) {
                if (!item.contains("***")) {
                    System.out.println(item);
                }
            }
            System.out.println("\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dataList.toArray(new String[0]);
    }
}
