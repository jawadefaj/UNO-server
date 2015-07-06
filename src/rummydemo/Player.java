package rummydemo;
import java.util.ArrayList;


public class Player {
	private String Name;
	private ArrayList<Card> list_of_cards = new ArrayList<Card>();
	private int coins;
	private int tokens;
	
	//Getters and setters of private variables
	public void addCard(Card card)
	{
		this.list_of_cards.add(card);
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public ArrayList<Card> getList_of_cards() {
		return list_of_cards;
	}

	public void setList_of_cards(ArrayList<Card> list_of_cards) {
		this.list_of_cards = list_of_cards;
		System.out.println(list_of_cards.size());
		//ArrayList<Card> c= new ArrayList<Card>();
		/*for(int i=0; i<7; i++)
		{			
			System.out.println("ListofCards"+list_of_cards.get(i).CardName());		
		}*/
	}
	
	public void remove_Cards(String card)
	{
		for(int i=0; i<list_of_cards.size(); i++)
		{
			if(list_of_cards.get(i).CardName().equals(card))
			{
				list_of_cards.remove(i);
			}
		}
	}
	
	public int getCoins() {
		return coins;
	}
	public void setCoins(int coins) {
		this.coins = coins;
	}
	public int getTokens() {
		return tokens;
	}
	public void setTokens(int tokens) {
		this.tokens = tokens;
	}
	
	// Hi I am avik and I am also confused.
	//conflict overloaded
}
