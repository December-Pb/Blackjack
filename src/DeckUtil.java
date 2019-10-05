/*
Author: Ziqi Tan
*/
public interface DeckUtil {
	Card[][] newCard();
	Card dealCard();
	int duel(DealerHand dealerHand, PlayerHand playerHand);
	void shuffle();
}
