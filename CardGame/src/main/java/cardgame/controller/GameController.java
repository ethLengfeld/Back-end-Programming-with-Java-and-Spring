package cardgame.controller;

import cardgame.model.Deck;
import cardgame.model.Player;

import java.util.List;

class GameableView {
    public void doSomething() { }
    public void setController(GameController gc) {

    }
}
public class GameController {
    enum GameState {
        AddingPlayers,
        CardsDealt,
        WinnerRevealed;
    }

    private Deck deck;
    private List<Player> players;
    private Player winner;
    private GameableView view;
    private GameState;

    public GameController(GameableView view, Deck deck) {

    }
}
