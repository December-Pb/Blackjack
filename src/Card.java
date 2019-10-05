/**
 * Author Ziqi Tan
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Card {
    private String face; // 1, 2, 3, 4, 5, 6, 7, 8 ,9, 10, J, Q, K
    private String suit; // heart, spade, club, diamond
    private boolean dealt;

    public Card(String f, String s) {
        this.face = f;
        this.suit = s;
        this.dealt = false;
    }

    public String getFace() {
        return this.face;
    }

    public String getSuit() {
        return this.suit;
    }

    public boolean isDealt() {
        return dealt;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public void setDealt(boolean dealt) {
        this.dealt = dealt;
    }

    public int getCardNumber() {
        if(face == "J" || face == "Q" || face == "K")
            return 10;
        return Integer.valueOf(face);
    }

    /**
     * Compare whether two cards have the same number, J, Q, K and 10 have the same number
     * @param card
     * @return
     */
    public boolean isTwoCardSame(Card card) {
        if(face == card.getFace())
            return true;
        return ((isNumeric(face) && Integer.valueOf(face) == 10) || face == "J" || face == "Q" || face == "K")
                && ((isNumeric(card.getFace()) && Integer.valueOf(card.getFace()) == 10) || card.getFace() == "J" || card.getFace() == "Q" || card.getFace() == "K");
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
