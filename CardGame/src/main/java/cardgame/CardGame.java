package cardgame;

import cardgame.controller.GameController;
import cardgame.factory.DeckFactory;
import cardgame.games.GameEvaluator;
import cardgame.model.Deck;
//import cardgame.view.CommandLineView;
import cardgame.view.GameSwing;

public class CardGame {
    public static void main(String[] args) {
        GameSwing view = new GameSwing();
        view.createAndShowGUI();
        GameController game = new GameController(view, DeckFactory.makeDeck(DeckFactory.DeckType.Normal), new GameEvaluator());
        game.run();
    }
}
