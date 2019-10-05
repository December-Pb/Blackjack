/**
 * Author Jiaqian Sun
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJackDealer extends Player {
    private DealerHand dealerHand;

    public BlackJackDealer(String name, int wallet) {
        super(name, wallet);
    }

    /**
     * When dealer receive the card, the card will be added to the dealerhand and then the method will judge whether dealer is bust or blackjack
     * @param card
     * @param currentNumber The first time when dealer receive a card, it will be -1, otherwise, it will be 0 because dealer can only has one hand of card
     * @return The status of this hand of card, including BUST, BLACKJACK and DEFAULT.
     */
    @Override
    public StatusCode receiveCard(Card card, int currentNumber) {
        if(currentNumber == -1) {
            List<Card> cards = new ArrayList<>();
            cards.add(card);
            dealerHand = new DealerHand(cards);
            return StatusCode.DEFAULT;
        }
        dealerHand.getCards().add(card);
        dealerHand.getTotalNumber();
        dealerHand.isBlackJack();
        dealerHand.isBust();
        return dealerHand.getStatus();
    }

    public DealerHand getDealerHand() {
        return dealerHand;
    }

    public void clearHand() {
        dealerHand = null;
    }

    /**
     * When the dealer hits, the dealerhand will add the card to the arraylist and get the total number of hand.
     * @param card
     */
    public void hit(Card card) {
        dealerHand.getCards().add(card);
        dealerHand.getTotalNumber();
    }

    /**
     * The action of hit for a dealer
     * @param dealer
     * @param deck
     * @param scanner
     */
    public static void dealerHit(BlackJackDealer dealer, Deck deck, Scanner scanner) {
        String input = "";
        while (!dealer.getDealerHand().isBlackJack() && dealer.getDealerHand().isBust()) {
            dealer.hit(deck.dealCard());
            System.out.println("Dealer hit card");
            if(dealer.getDealerHand().isBust())
                break;
            if(dealer.getDealerHand().isBlackJack())
                break;
            System.out.println("Do you want to still hit? Enter Y/y to hit, enter other key to stand");
            input = scanner.next();
            if(input == "\n")
                input = scanner.nextLine();
            if (input.equals("y") || input.equals("Y"))
                continue;
            break;
        }
        if(dealer.getDealerHand().isBlackJack())
            System.out.println("Dealer is BlackJack");
        if(dealer.getDealerHand().isBust())
            System.out.println("Dealer is Bust");
        return;
    }
}
