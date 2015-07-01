package rummydemo;
import java.util.ArrayList;


public class GameLogic {
	private int Rotation; // Clockwise rotation = -1 and anti clockwise rotation =1
	
	public GameLogic() {
		super();
		this.Rotation = -1;
	}

	public void setRotation(int rotation) {
		Rotation = rotation;
	}

	public int getRotation(Card DiscardPile, int curRotation )
	{
		CardControl obcardControl = new CardControl();
		if(obcardControl.getDiscardpile().getCard_no() == 11)
		{
			setRotation(curRotation*-1);
		}
		
		return Rotation;
	}
	
	public ArrayList<Card> possibleMoves(Card DiscardPile, Player curPlayer)
	{
		ArrayList<Card> possibleCards = new ArrayList<Card>();
		ArrayList<Card> curPlayerCards = new ArrayList<Card>();
		ArrayList<Integer> possibleCardIndex = new ArrayList<Integer>();
		curPlayerCards = curPlayer.getList_of_cards();
		
		if(DiscardPile.getCard_no() == 12)
		{
			ChallengeHandling obCH = new ChallengeHandling();
			obCH.punishment("REVERSE", null, curPlayer);
		}
		
		else if(DiscardPile.getCard_no() == 2 && DiscardPile.getColor_code() == 'W')
		{
			ChallengeHandling obCH = new ChallengeHandling();
			obCH.punishment("WILD4", null, curPlayer);
		}
		
		
		for(int i=0; i<curPlayerCards.size(); i++)
		{
			if(DiscardPile.getCard_no() == curPlayerCards.get(i).getCard_no() || 
					DiscardPile.getColor_code() == curPlayerCards.get(i).getColor_code() || 
					curPlayerCards.get(i).getColor_code() == 'W')
			{
				possibleCards.add(curPlayerCards.get(i));
			}
		}
		
		return possibleCards;
	}
	
	
	
}
