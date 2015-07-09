package rummydemo;
import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;


public class CardControl {
	//*lets assume there are four players
	public Card[] drawpile;
	public Card discardpile;
	public Card getDiscardpile() {
		//System.out.println("Inside get discard pile " + discardpile.CardName());
		return discardpile;
	}

	public void setDiscardpile(Card discardpile) {
		this.discardpile = discardpile;
		discardpilelist.add(discardpile);
		//System.out.println("Inside sET discard pile " + discardpile.CardName());
	}

	public int total_player;
	int MAX_CARD = 108;
	private int j;
	public ArrayList<ArrayList<Card>> tempCardarrayList = new ArrayList<ArrayList<Card>>();
	public ArrayList<Card> templist = new ArrayList<Card>();
	//public static ArrayList<Card> cardList = new ArrayList<Card>();
	public ArrayList<Card> cardList = new ArrayList<Card>();
	public ArrayList<Card> discardpilelist = new ArrayList<Card>();
	//public static ArrayList<Player> playerList = new ArrayList<Player>();
	public char[] color = {'B', 'G', 'Y', 'R', 'W'};
	
	
	
	public void initialize()
	{
		
		for(int k=0; k<4; k++)
		{
			for(int i=0; i<25; i++)
			{

				Card c= new Card();
				
				if(i>=13){
					j = 12;
				}
				else{
					j = 0;
				}
				
					c.setColor_code(color[k]);
					c.setCard_no(i-j);
					cardList.add(c);	
			}
		}
		
		for(j=0; j<2; j++)
		{
			for(int i=0; i<4; i++)
			{
				Card c = new Card();
				c.setColor_code(color[4]);
				c.setCard_no(j+1);
				cardList.add(c);
			}
		}
		
		/*for(int i=0; i<cardList.size(); i++)
		{
			System.out.println(cardList.get(i).CardName());
		}*/
			
		//System.out.println("Card Initialize and cardlist size is  "+cardList.size());
		cardShuffle();
	}
	
	//Shuffle cards
	public void cardShuffle()
	{
		Collections.shuffle(cardList);
		
		/*for(int i=0; i<cardList.size(); i++)
		{
			System.out.println(cardList.get(i).CardName());
		}*/
		System.out.println("Card Shuffled");
	}
	
	public ArrayList<Player> distributeCards( ArrayList<Player> p)
	{
		for(int i=0; i<p.size(); i++)
		{
			
			tempCardarrayList.add(new ArrayList<Card>());
			for(int j=0; j<7; j++)
			{
				tempCardarrayList.get(i).add(cardList.remove(0));
			}
			
			p.get(i).setList_of_cards(tempCardarrayList.get(i));
			
		}
		
		/*for(int i=0; i<p.size(); i++)
		{
			System.out.println(p.get(i).getName() + " got the following cards: ");
			for(int j=0; j<7; j++)
			{
				System.out.println(p.get(i).getList_of_cards().get(j).CardName());
			}
			//System.out.println("Lets find out the error");
		}*/
		while(!cardList.isEmpty())
		{
			if(cardList.get(0).getColor_code() == 'W' || cardList.get(0).getCard_no() >= 10)
			{
				//System.out.println("not empty" + cardList.get(0).CardName());
				cardList.add(cardList.remove(0));
				//System.out.println("Not empty");
				continue;
			}
			else
				break;
		}
		setDiscardpile( cardList.remove(0));
		//System.out.println("Discard Pile Card: " + getDiscardpile().CardName());
		return p;
		
	}
	
	public void isEmptyCardList()
	{
		if(cardList.isEmpty())
		{
			cardList = discardpilelist;
			Collections.shuffle(cardList);
		}
	}
	public Card PickACard()
	{
		Card c = new Card();
		c = cardList.get(0);
		cardList.remove(c);
		return c; 
	}
}
