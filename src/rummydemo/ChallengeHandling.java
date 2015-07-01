package rummydemo;
import java.util.ArrayList;


public class ChallengeHandling {
	private String challengeType; //uno challenge,wild+4 card challenge
	private Player challenger;
	private Player challengedPlayer;
	
	public String getChallengeType() {
		return challengeType;
	}
	public void setChallengeType(String challengeType) {
		this.challengeType = challengeType;
	}
	public Player getChallenger() {
		return challenger;
	}
	public void setChallenger(Player challenger) {
		this.challenger = challenger;
	}
	public Player getChallengedPlayer() {
		return challengedPlayer;
	}
	public void setChallengedPlayer(Player challengedPlayer) {
		this.challengedPlayer = challengedPlayer;
	}
	
	public void punishment(String challengeTyp, Player challenger, Player Challenged)
	{
		CardControl obCard = new CardControl();
		//Player obPlayer = new Player();
		ArrayList<Card> extraCards = new ArrayList<Card>();
		extraCards = Challenged.getList_of_cards();
		
		//UNO Challenge
		if(challengeTyp.equals("UNO"))
		{
			//Challenged player has to draw four cards			
			for(int i=0; i<2; i++)
			{
				Challenged.addCard(obCard.cardList.remove(0));
			}
		}
		
		else if(challengeTyp == "REVERSE")
		{
			//If there is +2 card in discard pile,then the player has to draw two cards			
			for(int i=0; i<2; i++)
			{
				Challenged.addCard(obCard.cardList.remove(0));
			}
		}
		
		//Wild4 without challenge
		else if(challengeTyp == "WILD4")
		{		
			for(int i=0; i<4; i++)
			{
				Challenged.addCard(obCard.cardList.remove(0));
			}
		}
		
		//Wild4 card challenge
		
		else if(challengeTyp == "WILD4C")
		{	
			extraCards = challenger.getList_of_cards();
			Card Discard = obCard.getDiscardpile();
			boolean isWin = true;
			for(int i=0; i<extraCards.size(); i++)
			{
				if(Discard.getCard_no() == extraCards.get(i).getCard_no() || 
					Discard.getColor_code() == extraCards.get(i).getColor_code())
				{
					isWin = false;
				}
			}
			
			if(!isWin)
			{
				for(int i=0; i<4; i++)
				{
					challenger.addCard(obCard.cardList.remove(0));
				}
			}
			
			else if(isWin)
			{
				for(int i=0; i<6; i++)
				{
					Challenged.addCard(obCard.cardList.remove(0));
				}
			}
		}
	}
}
