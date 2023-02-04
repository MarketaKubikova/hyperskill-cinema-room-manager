package cinema;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        int purchasedTickets = 0;
        int currentIncome = 0;

        boolean exit = false;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of rows:");
        int numberOfRows = scanner.nextInt();
        System.out.print("Enter the number of seats in each row:");
        int numberOfSeats = scanner.nextInt();

        String[][] seatsArray = getSeats(numberOfRows, numberOfSeats);

        while (!exit) {
            showMenu();
            int choice = scanner.nextInt();
            int selectedRow = 0;
            int selectedSeat = 0;

            if (choice == 1) {
                drawSchema(numberOfRows, numberOfSeats, seatsArray);
            } else if (choice == 2) {
                int price;
                boolean blocked = false;

                selectedRow = selectRow(scanner, numberOfRows);
                selectedSeat = selectSeat(scanner, numberOfSeats);

                while (!blocked) {
                    if (isFree(seatsArray, selectedRow, selectedSeat)) {
                        blockSeat(seatsArray, selectedRow, selectedSeat);
                        price = getPrice(numberOfSeats, numberOfRows, selectedRow);
                        System.out.printf("Ticket price: %s%n", getFormattedAmount(price));

                        currentIncome += price;
                        purchasedTickets++;
                        blocked = true;
                    } else {
                        System.out.println("That ticket has already been purchased!");
                        selectedRow = selectRow(scanner, numberOfRows);
                        selectedSeat = selectSeat(scanner, numberOfSeats);
                    }
                }
            } else if (choice == 3) {
                showStatistics(purchasedTickets, numberOfRows, numberOfSeats, currentIncome);
            } else if (choice == 0){
                exit = true;
            } else {
                System.out.println("Wrong input!");
            }
        }
    }

    private static String[][] getSeats(int row, int col) {
        String[][] array = new String[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                array[i][j] = "S";
            }
        }
        return array;
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        System.out.println();
    }

    private static void drawSchema(int row, int col, String[][] seatsArray) {
        System.out.println("Cinema:");

        String[][] schemeArray = new String[row + 1][col + 1];

        for (int i = 0; i <= row; i++) {
            for (int j = 0; j <= col; j++) {
                if (i == 0) {
                    schemeArray[i][j] = String.valueOf(j);
                    if (j == 0) {
                        schemeArray[i][j] = " ";
                    }
                } else if (j == 0) {
                    schemeArray[i][j] = String.valueOf(i);
                    if (i == 0) {
                        schemeArray[i][j] = " ";
                    }
                } else {
                    schemeArray[i][j] = seatsArray[i - 1][j - 1];
                }
                System.out.print(schemeArray[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private static int selectRow(Scanner scanner, int numberOfRows) {
        boolean correctInput = false;
        int row = 0;

        while (!correctInput) {
            System.out.print("Enter a row number:");
            row = scanner.nextInt();
            if (row > numberOfRows || row < 0) {
                System.out.println("Wrong input!");
            } else {
                correctInput = true;
            }
        }
        return row;
    }

    private static int selectSeat(Scanner scanner, int numberOfSeats) {
        boolean correctInput = false;
        int seat = 0;

        while (!correctInput) {
            System.out.print("Enter a seat number in that row:");
            seat = scanner.nextInt();
            if (seat > numberOfSeats || seat < 0) {
                System.out.println("Wrong input!");
            } else {
                correctInput = true;
            }
        }

        return seat;
    }

    private static boolean isFree(String[][] seatsArray, int selectedRow, int selectedSeat) {
        return seatsArray[selectedRow - 1][selectedSeat - 1].equals("S");
    }

    private static void blockSeat(String[][] seatsArray, int selectedRow, int selectedSeat) {
        seatsArray[selectedRow - 1][selectedSeat - 1] = "B";
    }

    private static Integer getPrice(int numberOfSeats, int numberOfRows, int selectedRow) {
        int standardPrice = 10;
        int cheaperPrice = 8;
        int totalSeats = numberOfSeats * numberOfRows;
        int frontHalf = Math.floorDiv(numberOfRows, 2);

        if (totalSeats < 60 || selectedRow <= frontHalf) {
            return standardPrice;
        } else {
            return cheaperPrice;
        }
    }

    private static Integer getTotalIncome(int numberOfSeats, int numberOfRows) {
        int standardPrice = 10;
        int cheaperPrice = 8;
        int totalSeats = numberOfSeats * numberOfRows;
        int frontHalf = Math.floorDiv(numberOfRows, 2);
        int backHalf = numberOfRows - frontHalf;

        if (totalSeats < 60) {
            return totalSeats * standardPrice;
        } else {
            return numberOfSeats * frontHalf * standardPrice + numberOfSeats * backHalf * cheaperPrice;
        }
    }

    private static String getFormattedAmount(int amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount).replace(".00", "");
    }

    private static void showStatistics(int numberOfTickets, int numberOfRows,
                                       int numberOfSeats, int currentIncome) {
        int totalSeats = numberOfRows * numberOfSeats;
        double percentage = getPercentage(numberOfTickets, totalSeats);
        int totalIncome = getTotalIncome(numberOfSeats, numberOfRows);

        System.out.printf("Number of purchased tickets: %d%n", numberOfTickets);
        System.out.printf("Percentage: %.2f%%%n", percentage);
        System.out.printf("Current income: %s%n", getFormattedAmount(currentIncome));
        System.out.printf("Total income: %s%n", getFormattedAmount(totalIncome));
    }

    private static double getPercentage(int numberOfPurchasedTickets, int numberOfSeats) {
        if (numberOfSeats != 0) {
            return ((double) numberOfPurchasedTickets / (double) numberOfSeats) * 100;
        }
        return 0.00;
    }
}
