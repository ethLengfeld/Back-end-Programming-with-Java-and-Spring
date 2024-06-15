package cardgame.model;

public class PlayingCard {
    private Rank rank;
    private Suit suit;
    private boolean isFaceUp;

    public PlayingCard(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        isFaceUp = false;
    }

    public Rank getRank() {
        return this.rank;
    }

    public boolean getIsFaceUp() {
        return this.isFaceUp;
    }

    public boolean flip() {
        this.isFaceUp = !this.isFaceUp;
        return this.isFaceUp;
    }
}
