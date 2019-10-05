/**
 * Author Jiaqian Sun
 */

import java.util.List;

public class DealerHand extends BlackJackHand {

    public DealerHand(List<Card> cards) {
        super(cards);
    }

    /**
     * When the dealer firstly get the card, he can only show one card to others.
     */
    public void printFirstHand() {
        System.out.print("Card Suit: " + getCards().get(0).getSuit() + " Card Face: " + getCards().get(0).getFace() + " ");
    }
}
