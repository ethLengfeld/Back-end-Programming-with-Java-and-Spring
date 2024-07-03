package cardgame.games;

import cardgame.model.IPlayer;
import cardgame.model.PlayingCard;

import java.util.List;

public class GameEvaluator {

    public IPlayer evaluateWinner(List<IPlayer> IPlayers) {
        IPlayer bestIPlayer = null;
        int bestRank = -1;
        int bestSuit = -1;

        for(IPlayer currIPlayer : IPlayers) {
            boolean newBestPlayer = false;

            if(bestIPlayer == null) {
                newBestPlayer = true;
            } else {
                PlayingCard playingCard = currIPlayer.getCard(0);
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
                bestIPlayer = currIPlayer;
                PlayingCard playingCard = bestIPlayer.getCard(0);
                bestRank = playingCard.getRank().value();
                bestSuit = playingCard.getSuit().value();
            }
        }
        return bestIPlayer;
    }
}
