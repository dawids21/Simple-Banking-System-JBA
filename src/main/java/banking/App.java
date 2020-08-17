package banking;

import java.util.Scanner;

public class App {

    private static final String IIN = "400000";

    public static void main(String[] args) {
        var state = AppStates.MENU;
        final var input = new Scanner(System.in);
        final var invoker = new CommandInvoker();
        final var bank = new Bank(IIN, new RandomCardGenerator());

        while (true) {
            System.out.println(getMenuText(state));
            var inputLine = input.nextLine();
            System.out.println();
            switch (state) {

                case MENU:
                    switch (inputLine) {
                        case "1":
                            invoker.setCommand(new CreateAccountCommand(bank));
                            invoker.execute();
                            break;
                        case "2":
                            break;
                        case "0":
                            input.close();
                            System.exit(0);
                    }
                    break;
                case LOGGED:
                    switch (inputLine) {
                        case "1":
                            break;
                        case "2":
                            break;
                        case "0":
                            input.close();
                            System.exit(0);
                    }
                    break;
            }
        }
    }

    //    private static Account login(Scanner input, HashMap<String, Account> accounts,
    //                                 CardValidator validator) {
    //        System.out.println("Enter your card number:");
    //        var cardNumber = input.nextLine();
    //        System.out.println("Enter your PIN");
    //        var cardPin = input.nextLine();
    //        if (validator.validate(cardNumber, cardPin)) {
    //            return accounts.get(cardNumber);
    //        } else {
    //            return null;
    //        }
    //    }

    private static String getMenuText(AppStates state) {
        String menu;
        switch (state) {

            case MENU:
                menu = "1. Create an account\n2. Log into account\n0. Exit";
                break;
            case LOGGED:
                menu = "1. Balance\n2.Log out\n0. Exit";
                break;
            default:
                menu = "";
        }
        return menu;
    }

    private enum AppStates {
        MENU, LOGGED
    }
}
