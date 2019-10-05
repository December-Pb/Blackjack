/**
 * Author: Jiaqian Sun
 *
 */
public abstract class Player {
    private String name;
    private int wallet;

    /**
     * Initialize the name and wallet of player
     * @param name
     * @param wallet
     */
    public Player(String name, int wallet) {
        this.name = name;
        this.wallet = wallet;
    }

    /**
     * Get the status code of current hand of player, the statuses are recorded in StatusCode
     * @param card
     * @param currentNumber
     * @return
     */
    public abstract StatusCode receiveCard(Card card, int currentNumber);

    public void setWallet(int wallet) {
        this.wallet += wallet;
    }

    public int getWallet() {
        return this.wallet;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: " + name + " Wallet: " + wallet;
    }
}
