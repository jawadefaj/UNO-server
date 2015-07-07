package rummydemo;
import java.util.ArrayList;


public class GameLogic {
	//public static int Rotation = -1; // Clockwise rotation = -1 and anti clockwise rotation =1
	public int Rotation = -1;
	
	/*public GameLogic() {
		super();
		this.Rotation = -1;
	} */



	public int getRotation()
	{
		//CardControl obcardControl = new CardControl();
		//Card card = new Card();
		//card.setColor_code(DiscardPile.charAt(0));
		//card.setCard_no(Integer.parseInt(DiscardPile.substring(1)));
		//if(Integer.parseInt(DiscardPile.substring(1)) == 11)
		//{
		//	Rotation = Rotation*(-1);
		//}
		//System.out.println("Inside the get rotation " + Rotation);
		return Rotation;
	}
	
	public int getNextPlayerIndex(int rotation, int curPlayerIndex, int size)
	{
		Player obP = new Player();
		int nextPlayerIndex = 0;
		//int size = UnoRoomExtension.PlayerList.size();
		//System.out.println("player list size " + size);
		
		if(rotation == 1)
		{
			if(curPlayerIndex == size-1)
			{
				nextPlayerIndex=0;
			}
			else{
				nextPlayerIndex = curPlayerIndex + 1;
			}
		}
		
		else if(rotation == -1)
		{
			if(curPlayerIndex == 0)
			{
				nextPlayerIndex = size-1;
			}
			else{
				nextPlayerIndex = curPlayerIndex - 1;
			}
		}
		
		return nextPlayerIndex;
		
		
	}
	
	public ArrayList<Card> possibleMoves(String DisCardPile ,Player curPlayer)
	{
		ArrayList<Card> possibleCards = new ArrayList<Card>();
		ArrayList<Card> curPlayerCards = new ArrayList<Card>();
		//ArrayList<Integer> possibleCardIndex = new ArrayList<Integer>();
		curPlayerCards = curPlayer.getList_of_cards();
		//CardControl cardControl = new CardControl();
		//System.out.println("Inside possible move");
		
		//System.out.println("Discard in possible move " + cardControl.getDiscardpile().CardName());
		char Color = DisCardPile.charAt(0);
		int CardNum = Integer.parseInt(DisCardPile.substring(1));
		for(int i=0; i<curPlayerCards.size(); i++)
		{
			/*if(cardControl.getDiscardpile().getCard_no() == curPlayerCards.get(i).getCard_no() || 
					cardControl.getDiscardpile().getColor_code() == curPlayerCards.get(i).getColor_code() || 
					curPlayerCards.get(i).getColor_code() == 'W')*/
			if(curPlayerCards.get(i).getCard_no() == CardNum || curPlayerCards.get(i).getColor_code() == Color ||
					curPlayerCards.get(i).getColor_code() == 'W')
			{
				possibleCards.add(curPlayerCards.get(i));
				//System.out.println("Current player ccards "+curPlayerCards.get(i).CardName());
			}
		}
		
		//System.out.println("In possible move function");
		return possibleCards;
	}
	
	
	
}
