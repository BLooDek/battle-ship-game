import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // Write your code here
        Game game = new Game("Player 1");
        Game game2 = new Game("Player 2");




        game.printGameBoard();
        System.out.println("Player 1, place your ships to the game field");
        for (Ships a : Ships.values()) {
            game.enterCoordinates(a.enuLen, a.enuName);
            game.printGameBoard();
        }
        promptEnterKey();
        System.out.println("Player 1, place your ships to the game field");
        for (Ships a : Ships.values()) {
            game2.enterCoordinates(a.enuLen, a.enuName);
            game2.printGameBoard();
        }
        promptEnterKey();
        System.out.println("The game starts!");
        while (game.numberOfShips!=0 || game2.numberOfShips!=0){
            System.out.println("Player 1, it's your turn:");
            game2.printFogBoard();
            System.out.println("---------------------");
            game.printGameBoard();
            game2.takeAShot();
            if(game2.numberOfShips==0){
                break;
            }
            promptEnterKey();
            System.out.println("Player 2, it's your turn:");
            game.printFogBoard();
            System.out.println("---------------------");
            game2.printGameBoard();
            game.takeAShot();
            if(game.numberOfShips==0){
                break;
            }
            promptEnterKey();
        }

        if(game.numberOfShips==0){
            System.out.println("You sank the last ship. You won. Congratulations Player 1!");
        }else {
            System.out.println("You sank the last ship. You won. Congratulations Player 2!");
        }

    }
    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
