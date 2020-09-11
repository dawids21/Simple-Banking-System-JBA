package banking;

import banking.commands.*;

import java.util.Scanner;

public class Main {

    private static final String IIN = "400000";

    public static void main(String[] args) {
        var state = AppStates.MENU;
        final var input = new Scanner(System.in);
        final var invoker = new CommandInvoker();

        AccountsDatabase db;
        if (args.length == 2 && args[0].equals("-fileName")) {
            db = new AccountsDatabase("jdbc:sqlite:" + args[1]);
        } else {
            db = new AccountsDatabase("jdbc:sqlite:noname.db" + args[1]);
        }

        final var bank = new Bank(IIN, new RandomCardGenerator(IIN), db);

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
                            invoker.setCommand(new LoginCommand(input, bank));
                            invoker.execute();
                            if (bank.isLogged()) {
                                state = AppStates.LOGGED;
                            }
                            break;
                        case "0":
                            invoker.setCommand(new ExitCommand(input));
                            invoker.execute();
                            System.exit(0);
                    }
                    break;
                case LOGGED:
                    switch (inputLine) {
                        case "1":
                            invoker.setCommand(
                                     new BalanceCommand(bank.getLoggedAccount()));
                            invoker.execute();
                            break;
                        case "2":
                            break;
                        case "5":
                            invoker.setCommand(new LogoutCommand(bank));
                            invoker.execute();
                            state = AppStates.MENU;
                            break;
                        case "0":
                            invoker.setCommand(new ExitCommand(input));
                            invoker.execute();
                            System.exit(0);
                    }
                    break;
            }
        }
    }

    private static String getMenuText(AppStates state) {
        String menu;
        switch (state) {

            case MENU:
                menu = "1. Create an account\n2. Log into account\n0. Exit";
                break;
            case LOGGED:
                menu = "1. Balance\n2. Add income\n5. Log out\n0. Exit";
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
