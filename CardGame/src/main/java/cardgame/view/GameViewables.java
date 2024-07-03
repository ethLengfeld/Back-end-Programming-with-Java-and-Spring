package cardgame.view;

import cardgame.controller.GameController;

import java.util.Vector;

public class GameViewables implements GameViewable {
    Vector<GameViewable> views;

    public GameViewables () {
        views = new Vector<>();
    }

    public void addViewable (GameViewable view) {
        views.add(view);
    }

    @Override
    public void showPlayerName(int playerIndex, String playerName) {
        for (GameViewable view : views) {
            view.showPlayerName(playerIndex, playerName);
        }
    }
    /**
     * @param gc
     */
    @Override
    public void setController(GameController gc) {
        for(GameViewable view: this.views) {
            view.setController(gc);
        }
    }

    /**
     *
     */
    @Override
    public void promptForPlayerName() {
        for(GameViewable view: this.views) {
            view.promptForPlayerName();
        }
    }

    /**
     *
     */
    @Override
    public void promptForFlip() {
        for(GameViewable view: this.views) {
            view.promptForFlip();
        }
    }

    /**
     *
     */
    @Override
    public void promptForNewGame() {
        for(GameViewable view: this.views) {
            view.promptForNewGame();
        }
    }

    /**
     * @param i
     * @param playerName
     */
    @Override
    public void showFaceDownCardForPlayer(int i, String playerName) {
        for(GameViewable view: this.views) {
            view.showFaceDownCardForPlayer(i, playerName);
        }
    }

    /**
     * @param i
     * @param playerName
     * @param rank
     * @param suit
     */
    @Override
    public void showCardForPlayer(int i, String playerName, String rank, String suit) {
        for(GameViewable view: this.views) {
            view.showCardForPlayer(i, playerName, rank, suit);
        }
    }

    /**
     * @param playerName
     */
    @Override
    public void showWinner(String playerName) {
        for(GameViewable view: this.views) {
            view.showWinner(playerName);
        }
    }
}
