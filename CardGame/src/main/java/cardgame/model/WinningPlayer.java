package cardgame.model;

public class WinningPlayer implements IPlayer {
    IPlayer winner;
    public WinningPlayer (IPlayer player) {
        winner = player;
    }

    public String getName() {
        return "***** " + winner.getName() + " *****";
    }

    /**
     * @param playingCard
     */
    @Override
    public void addCardToHand(PlayingCard playingCard) {

    }

    /**
     * @param index
     * @return
     */
    @Override
    public PlayingCard getCard(int index) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public PlayingCard removeCard() {
        return null;
    }
}
