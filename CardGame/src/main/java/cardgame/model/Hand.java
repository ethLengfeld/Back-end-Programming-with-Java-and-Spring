package cardgame.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<PlayingCard> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(PlayingCard playingCard) {
        this.cards.add(playingCard);
    }

    public PlayingCard getCard(int index) {
        return this.cards.get(index);
    }

    public PlayingCard removeCard() {
        return this.cards.remove(0);
    }
}
