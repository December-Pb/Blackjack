/**
 * Author: Jiaqian Sun, since the player can split the card, this program define a class named hand to save the information of each hands of card splited
 * by players
 */
import java.util.List;

public abstract class Hand {
    private int currentNumber = 0; //Total credit of this hand of card
    private List<Card> cards; //This hand of cards
    private StatusCode status; //1. blackjack 2. bust 3. stand
    private int chips = 0; //The chips on this hand of card

    public Hand(List<Card> cards) {
        this.cards = cards;
        status = StatusCode.DEFAULT;
    }

    public abstract void addCard(Card card);

    public List<Card> getCards(){
        return this.cards;
    }

    public int getChips() {
        return this.chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public void setStatus(StatusCode status) {
        this.status = status;
    }

    public StatusCode getStatus() {
        return this.status;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }
}
