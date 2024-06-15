package cardgame.model;

public enum Suit {
    DIAMONDS (1),
    HEARTS (2),
    SPADES (3),
    CLUBS (4)
    ;

    private int suit;

    Suit(int value) {
        this.suit = value;
    }

    public int value() {
        return this.suit;
    }
}
