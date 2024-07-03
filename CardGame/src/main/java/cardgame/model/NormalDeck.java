package cardgame.model;

import java.util.ArrayList;

public class NormalDeck extends Deck {
    public NormalDeck() {
        this.cards = new ArrayList<>();
        for(Rank currRank: Rank.values()) {
            for(Suit currSuit: Suit.values()) {
                System.out.println(String.format("Creating card [%s] of [%s]", currRank, currSuit));
                this.cards.add(new PlayingCard(currRank, currSuit));
            }
        }
        this.shuffle();
    }
}
