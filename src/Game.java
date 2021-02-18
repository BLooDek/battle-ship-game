import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class Game {
    private char[][] gameBoard;
    private char[][] fogBoard;
    int numberOfShips = 5;
    String playerName;


    public Game(String playerName) {
        this.gameBoard = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameBoard[i][j] = '~';
            }
        }
        this.fogBoard = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fogBoard[i][j] = '~';
            }
        }
        this.playerName = playerName;
    }


    public void printGameBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char a = 'A';
        for (int i = 0; i < 10; i++) {
            System.out.print(a + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(this.gameBoard[i][j] + " ");
            }
            System.out.println();
            a = (char) (a + 1);
        }
    }

    public void printFogBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char a = 'A';
        for (int i = 0; i < 10; i++) {
            System.out.print(a + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(this.fogBoard[i][j] + " ");
            }
            System.out.println();
            a = (char) (a + 1);
        }
    }


    public void takeAShot() {
        System.out.println("Take a shot!");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        try {
            int a1 = input.charAt(0) - 65;
            int a2 = Integer.parseInt(input.substring(1)) - 1;
            if (a1 >= 0 && a1 < 10 && a2 >= 0 && a2 < 10) {
                if (gameBoard[a1][a2] == 'O') {
                    gameBoard[a1][a2] = 'X';
                    fogBoard[a1][a2] = 'X';
                    if (checkForSunk(a1, a2)) {
                        System.out.println("U hit a ship");
                    } else {
                        System.out.println("You sank a ship!");
                        numberOfShips--;
                    }

                }else if(gameBoard[a1][a2] == 'X'){
                    System.out.println("U hit a ship");
                }
                else {
                    gameBoard[a1][a2] = 'M';
                    fogBoard[a1][a2] = 'M';
                    System.out.println("You missed!");
                }
            } else {
                System.out.println("Error wrong cords");
                takeAShot();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error wrong coordinates");
            takeAShot();
        }
    }

    public void enterCoordinates(int shipLength, String name) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter the coordinates of the %s (%d cells):\n", name, shipLength);
        String[] userInput = scanner.nextLine().split(" ");
        if (validInputAsNums(userInput)) { //valid coordinates
            int a1 = userInput[0].charAt(0) - 65;
            int a2 = Integer.parseInt(userInput[0].substring(1)) - 1;
            int b1 = userInput[1].charAt(0) - 65;
            int b2 = Integer.parseInt(userInput[1].substring(1)) - 1;
            if ((Math.abs(a1 - b1) == shipLength - 1 && a2 - b2 == 0) || (a1 - b1 == 0 && Math.abs(a2 - b2) == shipLength - 1)) { //valid length and line
                if (a1 - b1 == 0 && validFreeSpace(a1, a2, b2, true)) { //poziom
                    placeShip(a1, a2, b2, true);
                } else if (validFreeSpace(a2, a1, b1, false)) { //pion
                    placeShip(a2, a1, b1, false);
                } else {
                    enterCoordinates(shipLength, name);
                }
            } else {
                System.out.println("Error wrong length or its not line");
                enterCoordinates(shipLength, name);
            }

        } else {
            System.out.println("Error wrong input");
            enterCoordinates(shipLength, name);
        }


    }

    private boolean validFreeSpace(int constLev, int start, int stop, boolean flat) {
        if (start > stop) {
            int temp = stop;
            stop = start;
            start = temp;
        }


        if (flat) {
            if ((start - 1 > 0 && gameBoard[constLev][start - 1] == 'O') || (stop + 1 < 10 && gameBoard[constLev][stop + 1] == 'O')) {
                System.out.println("at the end or start");
                return false;
            }
            for (int i = start; i <= stop; i++) {
                if (gameBoard[constLev][i] == 'O'
                        || constLev - 1 > -1 && gameBoard[constLev - 1][i] == 'O'
                        || constLev + 1 < 10 && gameBoard[constLev + 1][i] == 'O') {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    System.out.println("close to one");
                    return false;
                }
            }
        } else {
            if (start - 1 > 0 && gameBoard[start - 1][constLev] == 'O' || stop + 1 < 10 && gameBoard[stop + 1][constLev] == 'O') {
                System.out.println("Error at the end or start1");
                return false;
            }
            for (int i = start; i <= stop; i++) {
                if (gameBoard[i][constLev] == 'O'
                        || constLev - 1 > -1 && gameBoard[i][constLev - 1] == 'O'
                        || constLev + 1 < 10 && gameBoard[i][constLev + 1] == 'O') {
                    System.out.println("Error! You placed it too close to another one. Try again2:");
                    return false;
                }
            }
        }

        return true;
    }


    private boolean validInputAsNums(String[] userInput) {
        if (userInput.length != 2) {
            return false;
        }
        if (userInput[0].charAt(0) < 'A' || userInput[0].charAt(0) > 'J'
                || userInput[1].charAt(0) < 'A' || userInput[1].charAt(0) > 'J') {
            return false;
        }
        try {
            int a = Integer.parseInt(userInput[0].substring(1));
            int b = Integer.parseInt(userInput[1].substring(1));
            if (a < 1 || a > 10 || b < 1 || b > 10) {
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error wrong number format");
            return false;
        }
        return true;
    }

    private void placeShip(int con, int start, int stop, boolean flat) {
        if (start > stop) {
            int temp = stop;
            stop = start;
            start = temp;
        }

        if (flat) {
            for (int j = start; j <= stop; j++) {
                //System.out.println(j);
                char o = 'O';
                this.gameBoard[con][j] = 'O';
            }
        } else {
            for (int j = start; j <= stop; j++) {
                this.gameBoard[j][con] = 'O';
            }
        }
    }

    private boolean checkForSunk(int first, int second) {
        List<Character> ship = new ArrayList<>();
        int x = first, y = second;
        if (gameBoard[x][y] == 'X' || gameBoard[x][y] == 'O') {
            ship.add(gameBoard[x][y]);
        }
        y = y + 1;
        while (true) {
            if (y < 10 && (gameBoard[x][y] == 'X' || gameBoard[x][y] == 'O')) {
                ship.add(gameBoard[x][y]);

            } else break;
            y = y + 1;
        }
        x = first;
        y = second - 1;
        while (true) {
            if (y > -1 && (gameBoard[x][y] == 'X' || gameBoard[x][y] == 'O')) {
                ship.add(gameBoard[x][y]);

            } else break;
            y = y - 1;
        }
        //valid in level ^^^ if ship is sunk
        x = first + 1;
        y = second;
        while (true) {
            if (x < 10 && (gameBoard[x][y] == 'X' || gameBoard[x][y] == 'O')) {
                ship.add(gameBoard[x][y]);
            } else break;
            x = x + 1;
        }
        x = first - 1;
        y = second;
        while (true) {
            if (x > -1 && (gameBoard[x][y] == 'X' || gameBoard[x][y] == 'O')) {
                ship.add(gameBoard[x][y]);

            } else break;
            x = x - 1;
        } //VALID W PIONIE
        //System.out.println(ship);
        for (char a : ship) {
            if (a == 'O') {
                return true;
            }
        }
        return false;
    }


}

