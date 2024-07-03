package cardgame.model;

import java.util.ArrayList;

public class SmallDeck extends Deck {
    public SmallDeck() {
        this.cards = new ArrayList<>();
        for(Rank currRank: Rank.values()) {
            for(Suit currSuit: Suit.values()) {
                if(currRank.value() >= 7) {
                    System.out.println(String.format("Creating card [%s] of [%s]", currRank, currSuit));
                    this.cards.add(new PlayingCard(currRank, currSuit));
                }
            }
        }
        this.shuffle();
    }
}
