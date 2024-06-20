package cardgame;

import cardgame.controller.GameController;
import cardgame.games.GameEvaluator;
import cardgame.model.Deck;
//import cardgame.view.CommandLineView;
import cardgame.view.GameSwing;

public class CardGame {
    public static void main(String[] args) {
        GameSwing view = new GameSwing();
        view.createAndShowGUI();
        GameController game = new GameController(view, new Deck(), new GameEvaluator());
        game.run();
    }
}
