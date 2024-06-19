package cardgame;

import cardgame.controller.GameController;
import cardgame.games.GameEvaluator;
import cardgame.model.Deck;
import cardgame.view.View;

public class CardGame {
    public static void main(String[] args) {
        GameController game = new GameController(new View(), new Deck(), new GameEvaluator());
        game.run();
    }
}
