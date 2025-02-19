package cardgame.model;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Deck {
    List<PlayingCard> cards;

//    public Deck() {
//        this.cards = new ArrayList<>();
//
//        for(Rank currRank: Rank.values()) {
//            for(Suit currSuit: Suit.values()) {
//                System.out.println(String.format("Creating card [%s] of [%s]", currRank, currSuit));
//                this.cards.add(new PlayingCard(currRank, currSuit));
//            }
//        }
//        this.shuffle();
//    }

    public void shuffle() {
        Random random = new Random();
        for(int i = 0; i < this.cards.size(); i++) {
            Collections.swap(this.cards, i, random.nextInt(this.cards.size()));
        }
    }

    public PlayingCard removeTopCard() {
        return this.cards.remove(0);
    }

    public void returnCardToDeck(PlayingCard playingCard) {
        this.cards.add(playingCard);
    }
}
