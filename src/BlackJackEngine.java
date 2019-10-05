/*
Author: Jiaqian Sun, Ziqi Tan
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlackJackEngine extends Game{

    private Scanner scanner;
    private String input;

    public BlackJackEngine() {
        PlayGame();
    }

    public void PlayGame() {
        scanner = new Scanner(System.in);
        input = "";
        BlackJackPlayer player = new BlackJackPlayer("John", 1000);
        BlackJackDealer dealer = new BlackJackDealer("Sam", 20000);
        ArrayList<BlackJackPlayer> players = new ArrayList<>();
        players.add(player);
        Deck deck = new Deck(players, dealer);
        while (true) {
            InitlizeGame(players, dealer, deck);
            System.out.println("Input 1 for Human Dealer - Human Player game; 2 for Human Dealer - Computer Player game" +
                    " 3 for Computer Dealer - Human Player");
            while (true) {
                input = scanner.nextLine();
                if(input == "\n")
                    input = scanner.nextLine();
                if (input.equals("1")) {
                    setGameMode(1);
                    break;
                } else if (input.equals("2")) {
                    setGameMode(2);
                    break;
                } else if (input.equals("3")) {
                    setGameMode(3);
                    break;
                } else
                    System.out.println("Please enter 1, 2 or 3");
            }
            if (getGameMode() == 1) {
                humanPlayerFirstHandAction(players, deck);
                System.out.println("Dealer receive the cards");
                dealer.receiveCard(deck.dealCard(), -1);
                dealer.receiveCard(deck.dealCard(), 0);
                humanPlayerOtherHandAction(players, deck);
                System.out.println("Dealer's Turn");
                humanDealerAction(dealer, deck);
                compareDealerandPlayer(players, dealer, deck);
                System.out.println(dealer.toString());
                for (int i = 0; i < players.size(); i++) {
                    System.out.println(players.get(i).toString());
                }
                System.out.println("Enter 1 to continue, others to exit");
                input = scanner.nextLine();
                if(input == "\n")
                    input = scanner.nextLine();
                if(showContinueScreen(players, dealer, input))
                    continue;
            }
            else if(getGameMode() == 2) {
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).receiveCard(deck.dealCard(), -1);
                    players.get(i).receiveCard(deck.dealCard(), 0);
                    players.get(i).getHands().get(0).setChips(200);
                }
                System.out.println("Dealer receive the cards");
                dealer.receiveCard(deck.dealCard(), -1);
                dealer.receiveCard(deck.dealCard(), 0);
                for(int i=0;i<players.size();i++) {
                    for(int j=0;j<players.get(i).getSize();j++) {
                        BlackJackPlayer.playerStand(players, i, j);
                    }
                }
                System.out.println("Dealer's Turn");
                humanDealerAction(dealer, deck);
                compareDealerandPlayer(players, dealer, deck);
                System.out.println(dealer.toString());
                for (int i = 0; i < players.size(); i++) {
                    System.out.println(players.get(i).toString());
                }
                System.out.println("Enter 1 to continue, others to exit");
                input = scanner.nextLine();
                if(input == "\n")
                    input = scanner.nextLine();
                if(showContinueScreen(players, dealer, input))
                    continue;
            }
            else {
                humanPlayerFirstHandAction(players, deck);
                System.out.println("Dealer receive the cards");
                dealer.receiveCard(deck.dealCard(), -1);
                dealer.receiveCard(deck.dealCard(), 0);
                humanPlayerOtherHandAction(players, deck);
                System.out.println("Dealer's Turn");
                while (true) {
                    if (dealer.getDealerHand().getTotalNumber() < 17) {
                        dealer.receiveCard(deck.dealCard(), 0);
                        continue;
                    }
                    if (dealer.getDealerHand().isBlackJack())
                        System.out.println("Dealer BlackJack!");
                    if (dealer.getDealerHand().isBust())
                        System.out.println("Dealer Bust");
                    break;
                }
                compareDealerandPlayer(players, dealer, deck);
                System.out.println(dealer.toString());
                for (int i = 0; i < players.size(); i++) {
                    System.out.println(players.get(i).toString());
                }
                System.out.println("Enter 1 to continue, others to exit");
                input = scanner.nextLine();
                if(input == "\n")
                    input = scanner.nextLine();
                if(showContinueScreen(players, dealer, input))
                    continue;
            }
        }
    }

    public void InitlizeGame(ArrayList<BlackJackPlayer> players, BlackJackDealer dealer, Deck deck) {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).getHands().clear();
            dealer.clearHand();
            deck.shuffle();
        }
    }

    public boolean showContinueScreen(ArrayList<BlackJackPlayer> players, BlackJackDealer dealer, String input) {
        if (input.equals("1")) {
            if(players.get(0).getWallet() <= 0) {
                System.out.println("Player has run out of money!");
                System.exit(0);
            }
            else if(dealer.getWallet() <= 0) {
                System.out.println("Dealer has run out of money!");
                System.exit(0);
            }
        }
        else
            System.exit(0);
        return true;
    }

    public void compareDealerandPlayer(ArrayList<BlackJackPlayer> players, BlackJackDealer dealer, Deck deck) {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.get(i).getSize(); j++) {
                if (deck.duel(dealer.getDealerHand(), players.get(i).getHands().get(j)) == 1) {
                    dealer.setWallet(players.get(i).getHands().get(j).getChips());
                    players.get(i).setWallet(-players.get(i).getHands().get(j).getChips());
                } else if (deck.duel(dealer.getDealerHand(), players.get(i).getHands().get(j)) == -1) {
                    dealer.setWallet(-players.get(i).getHands().get(j).getChips());
                    players.get(i).setWallet(players.get(i).getHands().get(j).getChips());
                } else
                    continue;
            }
        }
    }

    /**
     * When a human play the game as a players, this method will be called when he firstly receive the card, he can how many bets he will take
     * @param players
     * @param deck
     */
    public void humanPlayerFirstHandAction(ArrayList<BlackJackPlayer> players, Deck deck) {
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Players " + players.get(i).getName() + " receive the cards");
            players.get(i).receiveCard(deck.dealCard(), -1);
            players.get(i).receiveCard(deck.dealCard(), 0);
            System.out.println("Please enter the money");
            while (true) {
                input = scanner.nextLine();
                if(input == "\n")
                    input = scanner.nextLine();
                if (isNumeric(input)) {
                    players.get(i).getHands().get(0).setChips(Integer.valueOf(input));
                    break;
                } else
                    System.out.println("Please enter number");
            }
        }
    }

    /**
     * Besides the first hand's card, the player can stand, hit, doubleup and split the card.
     * @param players
     * @param deck
     */
    public void humanPlayerOtherHandAction(ArrayList<BlackJackPlayer> players, Deck deck) {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.get(i).getSize(); j++) {
                System.out.println("For player " + players.get(i).getName() + " hand " + (j + 1) + ": Enter 1 for stand; 2 for hit; 3 for double up; 4 for split");
                while (true) {
                    input = scanner.nextLine();
                    if(input == "\n")
                        input = scanner.nextLine();
                    if (input.equals("1")) {
                        BlackJackPlayer.playerStand(players, i, j);
                        break;
                    } else if (input.equals("2")) {
                        BlackJackPlayer.playerHit(players, i, j, scanner, deck);
                        break;
                    } else if (input.equals("3")) {
                        BlackJackPlayer.playerDoubleUp(players, i, j, deck);
                        break;
                    } else if (input.equals("4")) {
                        if (players.get(i).split(j)) {
                            BlackJackPlayer.playerSplit(players, i, j, scanner, deck);
                        } else {
                            System.out.println("Cannot split the card");
                            continue;
                        }
                        break;
                    } else
                        System.out.println("Please enter 1, 2, 3 or 4");
                }
            }
        }
    }

    /**
     * The interaction of between a user and command line when he is a dealer
     * @param dealer
     * @param deck
     */
    public void humanDealerAction(BlackJackDealer dealer, Deck deck) {
        while (true) {
            if (dealer.getDealerHand().getTotalNumber() < 17) {
                dealer.receiveCard(deck.dealCard(), 0);
                continue;
            }
            if (dealer.getDealerHand().isBlackJack()) {
                System.out.println("Dealer BlackJack!");
                break;
            }
            if (dealer.getDealerHand().isBust()) {
                System.out.println("Dealer Bust");
                break;
            }
            System.out.println("Dealer, enter 1 for hit, 2 for stand");
            input = scanner.nextLine();
            if(input == "\n")
                input = scanner.nextLine();
            if (input.equals("1")) {
                BlackJackDealer.dealerHit(dealer, deck, scanner);
                break;
            } else if (input.equals("2"))
                break;
            else {
                System.out.println("Please enter 1 or 2");
                continue;
            }
        }
    }

    /**
     * Judge whether an input is a number
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
