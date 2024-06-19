package cardgame.view;

import cardgame.controller.GameController;

import java.util.Scanner;

public class CommandLineView implements GameViewable {
    private GameController gameController;
    Scanner scanner = new Scanner(System.in);

    public void setController(GameController gc) {
        this.gameController = gc;
    }

    public void promptForPlayerName() {
        System.out.println("Enter player name: ");
        String name = scanner.nextLine();
        if(name.isEmpty()) {
            gameController.startGame();
        } else {
            gameController.addPlayer(name);
        }
    }

    public void promptForFlip() {
        System.out.println("Press Enter to reveal cards!");
        scanner.nextLine();
        gameController.flipCards();
    }

    public void promptForNewGame() {
        System.out.println("Press Enter to deal again.");
        scanner.nextLine();
        gameController.startGame();
    }

    public void showPlayerName(int size, String playerName) {
        System.out.println("["+ size +"][" + playerName + "]");
    }

    public void showFaceDownCardForPlayer(int i, String playerName) {
        System.out.println("[" + playerName + "] [][]");
    }

    public void showCardForPlayer(int i, String playerName, String rank, String suit) {
        System.out.println("[" + playerName + "][" + rank + "][" + suit + "]");
    }

    public void showWinner(String playerName) {
        System.out.println("Winner is " + playerName + "!");
    }
}
