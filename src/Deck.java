import java.util.ArrayList;
import java.util.Random;

/*
Author: Ziqi Tan
*/
public class Deck implements DeckUtil {

    private Card[][] cards;
    private BlackJackDealer dealer;
    private ArrayList<BlackJackPlayer> players;
    private int count;

    public Deck(ArrayList<BlackJackPlayer> players, BlackJackDealer dealer) {
        newCard();
        this.dealer = dealer;
        this.players = players;
    }

    @Override
    public Card[][] newCard() {
        cards = new Card[13][4];
        // 1~10, J, Q, K [13]
        // heart, spade, club, diamond [4]
        for (int i = 0; i < cards.length; i++) {
            cards[i][0] = new Card(Integer.toString(i + 1), "heart");
            cards[i][1] = new Card(Integer.toString(i + 1), "spade");
            cards[i][2] = new Card(Integer.toString(i + 1), "club");
            cards[i][3] = new Card(Integer.toString(i + 1), "diamond");
        }

        // Special A, J, Q, K
        cards[0][0].setFace("A");
        cards[0][1].setFace("A");
        cards[0][2].setFace("A");
        cards[0][3].setFace("A");
        cards[10][0].setFace("J");
        cards[10][1].setFace("J");
        cards[10][2].setFace("J");
        cards[10][3].setFace("J");
        cards[11][0].setFace("Q");
        cards[11][1].setFace("Q");
        cards[11][2].setFace("Q");
        cards[11][3].setFace("Q");
        cards[12][0].setFace("K");
        cards[12][1].setFace("K");
        cards[12][2].setFace("K");
        cards[12][3].setFace("K");

        return cards;
    }

    @Override
    public Card dealCard() {
        Random rand = new Random();
        int faceMin = 1;
        int faceMax = 13;

        int suitMin = 1;
        int suitMax = 4;

        Card card;
        String face;
        String suit;
        int randomFace;
        int randomSuit;

        if(count == 0)
            shuffle();

        while (true) {
            randomFace = rand.nextInt(faceMax - faceMin + 1) + faceMin;
            randomSuit = rand.nextInt(suitMax - suitMin + 1) + suitMin;
            if (!cards[randomFace - 1][randomSuit - 1].isDealt()) {
                break;
            }
            cards[randomFace][randomSuit].setDealt(false);
        }

        switch (randomFace) {
            case 1:
                face = "A";
                break;
            case 11:
                face = "J";
                break;
            case 12:
                face = "Q";
                break;
            case 13:
                face = "K";
                break;
            default:
                face = Integer.toString(randomFace);
        }

        switch (randomSuit) {
            // heart, spade, club, diamond
            case 1:
                suit = "heart";
                break;
            case 2:
                suit = "spade";
                break;
            case 3:
                suit = "club";
                break;
            case 4:
                suit = "diamond";
                break;
            default:
                suit = "heart";
        }

        card = new Card(face, suit);
        System.out.println(suit + " " + face);
        count --;
        return card;
    }

    public Card dealCard(int number) {
        if(number == 1)
            return new Card("A", "heart");
        else
            return new Card("2", "heart");
    }

    /**
     * Judge who will win in this round
     * @param dealerHand
     * @param playerHand
     * @return
     */
    @Override
    public int duel(DealerHand dealerHand, PlayerHand playerHand) {
        // 1 means dealer wins.
        // 0 means .
        // -1 means player wins.
        boolean dealerNatureBlackJack = false;
        boolean playerNatureBlackJack = false;
        if(dealerHand.isBlackJack() && dealerHand.getCards().size() == 2)
            dealerNatureBlackJack = true;
        if(playerHand.isBlackJack() && playerHand.getCards().size() == 2)
            playerNatureBlackJack = true;
        if (playerHand.isBust())
            return 1;
        if (dealerHand.isBust())
            return -1;
        if(dealerNatureBlackJack && !playerNatureBlackJack)
            return 1;
        if(!dealerNatureBlackJack && playerNatureBlackJack)
            return -1;
        if (dealerHand.getTotalNumber() > playerHand.getTotalNumber())
            return 1;
        else if (dealerHand.getTotalNumber() == playerHand.getTotalNumber())
            return 0;
        else
            return -1;
    }

    /**
     * Set all the "dealt" attribute of cards as false.
     */
    @Override
    public void shuffle() {
        if(count == 0) {
            System.out.println("Shuffle the card now");
            for (int i = 0; i < cards.length; i++) {
                for (int j = 0; j < cards[i].length; j++) {
                    cards[i][j].setDealt(false);
                }
            }
            count = cards.length * cards[0].length;
        }
    }
}
