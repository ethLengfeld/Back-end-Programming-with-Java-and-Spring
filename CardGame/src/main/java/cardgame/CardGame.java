package cardgame;

import cardgame.controller.GameController;
import cardgame.games.GameEvaluator;
import cardgame.model.Deck;
import cardgame.view.CommandLineView;

public class CardGame {
    public static void main(String[] args) {
        GameController game = new GameController(new CommandLineView(), new Deck(), new GameEvaluator());
        game.run();
    }
}
