/**
 * Author: Jiaqian Sun
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJackPlayer extends Player{
    private List<PlayerHand> hands;
    public BlackJackPlayer(String name, int wallet) {
        super(name, wallet);
        hands = new ArrayList<>();
    }

    /**
     * When the BlackJackPlayer receive a card for the first time, the currentNumber is -1, a new card list will be initialized and sent to hands.
     * Every time after the BlackJackPlayer receive the card, this hand will be judged whether it is bust or blackjack.
     * @param card The card to be received
     * @param currentNumber The index of hand of card
     * @return The status of this hand of card
     */
    @Override
    public StatusCode receiveCard(Card card, int currentNumber) {
        if(currentNumber == -1) { //-1 represent the first time receive the card
            List<Card> cards = new ArrayList<>();
            cards.add(card);
            PlayerHand playerHand = new PlayerHand(cards);
            hands.add(playerHand);
            return StatusCode.DEFAULT;
        }
        StatusCode status = StatusCode.DEFAULT;
        List<Card> cards = hands.get(currentNumber).getCards();
        cards.add(card);
        hands.get(currentNumber).getTotalNumber();
        if(hands.get(currentNumber).isBust()) {
            hands.get(currentNumber).setStatus(StatusCode.BUST);
            return StatusCode.BUST;
        }
        if(hands.get(currentNumber).getTotalNumber() == 21 || hands.get(currentNumber).isBlackJack()) {
            hands.get(currentNumber).setStatus(StatusCode.BLACKJACK);
            return StatusCode.BLACKJACK;
        }
        if(canSplit(currentNumber))
            return StatusCode.SPLIT; //show hit, stand, doubleup, split on the screen
        return StatusCode.DEFAULT; //show hit, stand, doubleup on the screen
    }

    /**
     * Every time when player wants to hit, the card list will be judged whether it is bust or blackjack
     * @param card The card to be received
     * @param currentHandNumber The index of hand of card
     */
    public void hit(Card card, int currentHandNumber) {
        PlayerHand playerHand = hands.get(currentHandNumber);
        playerHand.addCard(card);
        playerHand.isBust();
        playerHand.isBust();
    }

    /**
     * Firstly use canSplit to judge whether BlackJackPlayer can split his card, then split the two cards into two arraylist.
     * @param currentHandNumber The index of hand of card
     * @return
     */
    public boolean split(int currentHandNumber) {
        if(canSplit(currentHandNumber)) {
            List<Card> cards = hands.get(currentHandNumber).getCards();
            List<Card> newHand = new ArrayList<>();
            newHand.add(cards.get(1));
            cards.remove(1);
            hands.get(currentHandNumber).getTotalNumber();
            PlayerHand playerHand = new PlayerHand(newHand);
            hands.add(playerHand);
            return true;
        }
        return false;
    }

    /**
     * Double the chips of this hand of card
     * @param currentHandNumber The index of hand of card
     * @return
     */
    public boolean doubleUp(int currentHandNumber) {
        if(canDouble(currentHandNumber)) {
            int preChips = hands.get(currentHandNumber).getChips();
            hands.get(currentHandNumber).setChips(2 * preChips);
            return true;
        }
        return false;
    }

    public void stand(int currentHandNumber) {
        PlayerHand playerHand = hands.get(currentHandNumber);
        playerHand.setStatus(StatusCode.STAND);
    }

    public List<PlayerHand> getHands() {
        return hands;
    }

    public int getSize() {
        return hands.size();
    }

    /**
     * Only when the BlackJackPlayer has enough chips and the two cards have the same face can he split the card.
     * @param currentHandNumber The index of hand of card
     * @return
     */
    private boolean canSplit(int currentHandNumber) {
        List<Card> cards = hands.get(currentHandNumber).getCards();
        int totalBet = 0;
        if(cards.size() == 2 && cards.get(0).isTwoCardSame(cards.get(1))) {
            for(int i=0;i<hands.size();i++) {
                totalBet += hands.get(i).getChips();
            }
            totalBet += hands.get(currentHandNumber).getChips();
            return totalBet <= getWallet();
        }
        return false;
    }

    /**
     * Only when the BlackJackPlayer has enough chips can he double up
     * @param currentHandNumber The index of hand of card
     * @return
     */
    private boolean canDouble(int currentHandNumber) {
        int totalBet = 0;
        for(int i=0;i<hands.size();i++) {
            totalBet += hands.get(i).getChips();
        }
        totalBet += hands.get(currentHandNumber).getChips();
        return totalBet <= getWallet();
    }

    /**
     * When a human play the game as a players, this method will be called when he wants to hit
     * @param players
     * @param currentPlayer
     * @param currentHand
     * @param scanner
     * @param deck
     */
    public static void playerHit(ArrayList<BlackJackPlayer> players, int currentPlayer, int currentHand, Scanner scanner, Deck deck) {
        String input = "";
        while (!(players.get(currentPlayer).getHands().get(currentHand).isBlackJack() && players.get(currentPlayer).getHands().get(currentHand).isBust())) {
            players.get(currentPlayer).hit(deck.dealCard(), currentHand);
            System.out.println("Player " + players.get(currentPlayer).getName() + " hit " + "hand " + (currentHand + 1));
            if (players.get(currentPlayer).getHands().get(currentHand).isBust())
                break;
            if (players.get(currentPlayer).getHands().get(currentHand).isBlackJack())
                break;
            System.out.println("Do you want to still hit? Enter Y/y to hit, enter other key to stand");
            input = scanner.next();
            if(input == "\n")
                input = scanner.nextLine();
            if (input.equals("y") || input.equals("Y"))
                continue;
            else {
                players.get(currentPlayer).stand(currentHand);
                break;
            }
        }
        if (players.get(currentPlayer).getHands().get(currentHand).isBlackJack()) {
            System.out.println("Player " + players.get(currentPlayer).getName() + " hand " + (currentHand + 1) + " BlackJack");
            return;
        } else if (players.get(currentPlayer).getHands().get(currentHand).isBust()) {
            System.out.println("Player " + players.get(currentPlayer).getName() + " hand " + (currentHand + 1) + " Bust");
            return;
        } else {
            System.out.println("Player " + players.get(currentPlayer).getName() + " stand " + (currentHand + 1) + " hand");
            return;
        }
    }

    /**
     * When a human play the game as a BlackJackPlayer, this method will be called when he wants to stand
     * @param players
     * @param currentPlayer
     * @param currentHand
     */
    public static void playerStand(ArrayList<BlackJackPlayer> players, int currentPlayer, int currentHand) {
        players.get(currentPlayer).stand(currentHand); //Stand the jth hand of the player i.
        System.out.println("Player " + players.get(currentPlayer).getName() + " stand " + (currentHand + 1) + " hand");
    }

    /**
     * When a human play the game as a players, this method will be called when he wants to doubleUp
     * @param players
     * @param currentPlayer
     * @param currentHand
     * @param deck
     */
    public static void playerDoubleUp(ArrayList<BlackJackPlayer> players, int currentPlayer, int currentHand, Deck deck) {
        if (players.get(currentPlayer).doubleUp(currentHand)) {
            System.out.println("Player " + players.get(currentPlayer).getName() + " double up " + (currentHand + 1) + " hand");
            players.get(currentPlayer).receiveCard(deck.dealCard(), currentHand);
            players.get(currentPlayer).stand(currentHand);
        } else
            System.out.println("Insufficient Money!");
    }

    /**
     * When a human play the game as a players, this method will be called when he wants to split
     * @param players
     * @param currentPlayer
     * @param currentHand
     * @param scanner
     * @param deck
     */
    public static void playerSplit(ArrayList<BlackJackPlayer> players, int currentPlayer, int currentHand, Scanner scanner, Deck deck) {
        String input = "";
        System.out.println("Player " + players.get(currentPlayer).getName() + " split " + (currentHand + 1) + " hand");
        int currentMoney = players.get(currentPlayer).getHands().get(currentHand).getChips();
        for (int k = currentHand; k < players.get(currentPlayer).getSize(); k++) {
            players.get(currentPlayer).getHands().get(k).setChips(currentMoney);
            players.get(currentPlayer).receiveCard(deck.dealCard(), k);
        }
        System.out.println("For hand " + (currentHand + 1) + ": Please enter 1 to stand, 2 to hit");
        input = scanner.nextLine();
        if(input == "\n")
            input = scanner.nextLine();
        while (true) {
            if (input.equals("1")) {
                BlackJackPlayer.playerStand(players, currentPlayer, currentHand);
                break;
            } else if (input.equals("2")) {
                BlackJackPlayer.playerHit(players, currentPlayer, currentHand, scanner, deck);
                break;
            } else {
                System.out.println("Please enter 1 or 2");
                continue;
            }
        }
    }
}
