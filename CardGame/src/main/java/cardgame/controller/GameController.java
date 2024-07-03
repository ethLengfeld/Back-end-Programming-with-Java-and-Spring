package cardgame.controller;

import cardgame.games.GameEvaluator;
import cardgame.model.*;
import cardgame.view.GameSwing;
import cardgame.view.GameViewable;
import cardgame.view.GameViewables;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    enum GameState {
        AddingPlayers,
        CardsDealt,
        WinnerRevealed,
        AddingView;
    }

    private Deck deck;
    private List<IPlayer> IPlayers;
    private IPlayer winner;
    private GameViewables views;
    private GameState gameState;
    private GameEvaluator evaluator;

    public GameController(GameViewable view, Deck deck, GameEvaluator evaluator) {
        this.views = new GameViewables();
        this.deck = deck;
        this.IPlayers = new ArrayList<>();
        this.gameState = GameState.AddingPlayers;
        this.evaluator = evaluator;
        addViewable(view);
    }

    public void addViewable(GameViewable newView) {
        GameState currState = gameState;
        gameState = GameState.AddingView;
        newView.setController(this);
        views.addViewable(newView);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameState = currState;
    }


    public void run() {
        while(true) {
            switch(this.gameState) {
                case AddingPlayers:
                    this.views.promptForPlayerName();
                    break;
                case CardsDealt:
                    this.views.promptForFlip();
                    break;
                case WinnerRevealed:
                    this.views.promptForNewGame();
                    break;
                case AddingView:
                    break;
            }
        }
    }

    public void addPlayer(String playerName) {
        if(this.gameState == GameState.AddingPlayers) {
            this.IPlayers.add(new Player(playerName));
            this.views.showPlayerName(this.IPlayers.size(), playerName);
        }
    }

    public void startGame() {
        if(this.gameState != GameState.CardsDealt) {
            this.deck.shuffle();
            int playerIndex = 1;
            for(IPlayer currIPlayer : this.IPlayers) {
                currIPlayer.addCardToHand(deck.removeTopCard());
                this.views.showFaceDownCardForPlayer(playerIndex++, currIPlayer.getName());
            }
            this.gameState = GameState.CardsDealt;
        }
    }

    public void flipCards() {
        int playerIndex = 1;
        for(IPlayer currIPlayer :this.IPlayers) {
            PlayingCard playingCard = currIPlayer.getCard(0);
            playingCard.flip();
            this.views.showCardForPlayer(playerIndex++, currIPlayer.getName(), playingCard.getRank().toString(), playingCard.getSuit().toString());
        }

        this.evaluateWinner();
        this.displayWinner();
        this.rebuildDeck();
        this.gameState = GameState.WinnerRevealed;
    }

    public void restartGame() {
        this.rebuildDeck();
        this.gameState = GameState.AddingPlayers;
    }

    private void evaluateWinner() {
        winner = new WinningPlayer(this.evaluator.evaluateWinner(this.IPlayers));
    }

    private void displayWinner() {
        views.showWinner(this.winner.getName());
    }

    private void rebuildDeck() {
        for(IPlayer currIPlayer :this.IPlayers) {
            this.deck.returnCardToDeck(currIPlayer.removeCard());
        }
    }
}
