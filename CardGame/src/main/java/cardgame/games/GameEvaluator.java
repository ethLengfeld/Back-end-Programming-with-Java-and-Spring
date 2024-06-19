package cardgame.games;

import cardgame.model.Player;
import cardgame.model.PlayingCard;

import java.util.List;

public class GameEvaluator {

    public Player evaluateWinner(List<Player> players) {
        Player bestPlayer = null;
        int bestRank = -1;
        int bestSuit = -1;

        for(Player currPlayer:players) {
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
        return bestPlayer;
    }
}
