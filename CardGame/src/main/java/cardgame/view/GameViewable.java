package cardgame.view;

import cardgame.controller.GameController;

public interface GameViewable {

    public void setController(GameController gc);

    public void promptForPlayerName();

    public void promptForFlip();

    public void promptForNewGame();

    public void showPlayerName(int size, String playerName);

    public void showFaceDownCardForPlayer(int i, String playerName);

    public void showCardForPlayer(int i, String playerName, String rank, String suit);

    public void showWinner(String playerName);
}
