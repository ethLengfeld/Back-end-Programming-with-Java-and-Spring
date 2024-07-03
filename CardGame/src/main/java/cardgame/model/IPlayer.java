package cardgame.model;

public interface IPlayer {
    public String getName();
    public void addCardToHand(PlayingCard playingCard);
    public PlayingCard getCard(int index);
    public PlayingCard removeCard();
}
