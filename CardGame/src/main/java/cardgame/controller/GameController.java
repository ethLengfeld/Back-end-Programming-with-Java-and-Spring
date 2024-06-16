package cardgame.controller;

import cardgame.model.Deck;
import cardgame.model.Player;
import cardgame.model.PlayingCard;
import cardgame.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    enum GameState {
        AddingPlayers,
        CardsDealt,
        WinnerRevealed;
    }

    private Deck deck;
    private List<Player> players;
    private Player winner;
    private View view;
    private GameState gameState;

    public GameController(View view, Deck deck) {
        this.view = view;
        this.deck = deck;
        this.players = new ArrayList<>();
        this.gameState = GameState.AddingPlayers;
        this.view.setController(this);
    }

    public void run() {
        while(true) {
            switch(this.gameState) {
                case AddingPlayers:
                    this.view.promptForPlayerName();
                    break;
                case CardsDealt:
                    this.view.promptForFlip();
                    break;
                case WinnerRevealed:
                    this.view.promptForNewGame();
                    break;
            }
        }
    }

    public void addPlayer(String playerName) {
        if(this.gameState == GameState.AddingPlayers) {
            this.players.add(new Player(playerName));
            this.view.showPlayerName(this.players.size(), playerName);
        }
    }

    public void startGame() {
        if(this.gameState != GameState.CardsDealt) {
            this.deck.shuffle();
            int playerIndex = 1;
            for(Player currPlayer: this.players) {
                currPlayer.addCardToHand(deck.removeTopCard());
                this.view.showFaceDownCardForPlayer(playerIndex++, currPlayer.getName());
            }
            this.gameState = GameState.CardsDealt;
        }
    }

    public void flipCards() {
        int playerIndex = 1;
        for(Player currPlayer:this.players) {
            PlayingCard playingCard = currPlayer.getCard(0);
            playingCard.flip();
            this.view.showCardForPlayer(playerIndex++, currPlayer.getName(), playingCard.getRank().toString(), playingCard.getSuit().toString());
        }

        this.evaluateWinner();
        this.displayWinner();
        this.rebuildDeck();
        this.gameState = GameState.WinnerRevealed;
    }

    private void evaluateWinner() {
        Player bestPlayer = null;
        int bestRank = -1;
        int bestSuit = -1;

        for(Player currPlayer:this.players) {
            boolean newBestPlayer = false;

            if(bestPlayer == null) {
                newBestPlayer = true;
            } else {
                PlayingCard playingCard = currPlayer.getCard(0);
                int currRank = playingCard.getRank().value();
                if(currRank >= bestRank) {
                    if(currRank > bestRank) {
                        newBestPlayer = true;
                    } else {
                        if(playingCard.getSuit().value() > bestSuit) {
                            newBestPlayer = true;
                        }
                    }
                }
            }
            if(newBestPlayer) {
                bestPlayer = currPlayer;
                PlayingCard playingCard = bestPlayer.getCard(0);
                bestRank = playingCard.getRank().value();
                bestSuit = playingCard.getSuit().value();
            }
        }
        winner = bestPlayer;
    }

    private void displayWinner() {
        view.showWinner(this.winner.getName());
    }

    private void rebuildDeck() {
        for(Player currPlayer:this.players) {
            this.deck.returnCardToDeck(currPlayer.removeCard());
        }
    }
}
