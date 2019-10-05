/**
 * Author Jiaqian Sun
 */

import java.util.ArrayList;
import java.util.List;

public class BlackJackHand extends Hand{
    public BlackJackHand(List<Card> cards) {
        super(cards);
        getTotalNumber();
    }

    public boolean isBust() {
        if(getCurrentNumber() > 21)
            setStatus(StatusCode.BUST);
        return getCurrentNumber() > 21;
    }

    public boolean isBlackJack() {
        if(getCards().size() == 2 && getTotalNumber() == 21)
            setStatus(StatusCode.BLACKJACK);
        return getCards().size() == 2 && getCurrentNumber() == 21;
    }

    public void addCard(Card card) {
        getCards().add(card);
        getTotalNumber();
        isBlackJack();
        isBust();
    }

    /**
     * Get total number of the cards in hand, "A" can be regarded as two different points
     * @return
     */
    public int getTotalNumber() {
        List<Card> aceCards = new ArrayList<>();
        List<Card> normalCards = new ArrayList<>();
        setCurrentNumber(0);
        for(int i=0;i<getCards().size();i++) {
            if(getCards().get(i).getFace() == "A")
                aceCards.add(getCards().get(i));
            else
                normalCards.add(getCards().get(i));
        }
        for(Card card : normalCards) {
            setCurrentNumber(getCurrentNumber() + card.getCardNumber());
        }
        for(Card card : aceCards) {
            if(getCurrentNumber() + 10 > 21)
                setCurrentNumber(getCurrentNumber() + 1);
            else
                setCurrentNumber(getCurrentNumber() + 11);
        }
        return getCurrentNumber();
    }
}
