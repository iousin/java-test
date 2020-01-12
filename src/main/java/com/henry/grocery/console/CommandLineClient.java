package com.henry.grocery.console;

import com.henry.grocery.basket.Basket;
import com.henry.grocery.basket.BasketService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class CommandLineClient {

    private final BasketService basketService;
    private Basket basket;

    public CommandLineClient(BasketService basketService) {
        this.basketService = basketService;
    }

    public void startCommandLineInterface() {
        basket = new Basket();
        printUsage();
        scanForCommands();
    }

    private void scanForCommands() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            try {
                processCommand(command);
            } catch (Exception ex) {
                System.out.printf("An error occurred while processing command - %s%n", ex.getMessage());
                printUsage();
            }
        }
    }

    private void processCommand(String command) {
        if (command.startsWith("exit")) {
            System.exit(0);
        } else if (command.startsWith("checkout")) {
            checkout(command);
        } else if (command.startsWith("reset")) {
            resetBasket();
        } else {
            addItem(command);
        }
    }

    private void printUsage() {
        System.out.println("Please enter one of the following options followed by an Enter Key:");
        System.out.println("<product name> <quantity> - e.g, apples 5");
        System.out.println("checkout <day> - The day on which to checkout. 0 is today, 1 is tomorrow and so on");
        System.out.println("reset - To clear basket and start over");
        System.out.println("exit - To close the application and exit");
    }

    private void addItem(String itemCommand) {
        String[] commandItems = itemCommand.split(" ");
        String productName = commandItems[0];
        int quantity = Integer.parseInt(commandItems[1]);
        basketService.addItem(basket, productName, quantity);
        System.out.printf("%s with quantity %d added successfully%n", productName, quantity);
    }

    private void checkout(String checkoutCommand) {
        int checkoutDay = Integer.parseInt(checkoutCommand.split(" ")[1]);
        LocalDateTime checkoutDate = LocalDateTime.now().plusDays(checkoutDay);
        basket = basketService.checkout(basket, checkoutDate);
        printBasketState();
        resetBasket();
    }

    private void resetBasket() {
        basket = new Basket();
        System.out.println("Basket has been reset successfully");
    }

    private void printBasketState() {
        System.out.printf("Total cost of basket is %.2f%n", basket.getTotal());
    }

}
