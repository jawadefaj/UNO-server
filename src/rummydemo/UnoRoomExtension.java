package rummydemo;
import java.awt.List;
import java.util.ArrayList;

import com.shephertz.app42.server.idomain.BaseTurnRoomAdaptor;
import com.shephertz.app42.server.idomain.HandlingResult;
import com.shephertz.app42.server.idomain.IRoom;
import com.shephertz.app42.server.idomain.ITurnBasedRoom;
import com.shephertz.app42.server.idomain.IUser;
import com.shephertz.app42.server.idomain.IZone;

import java.util.Collections;

import javassist.util.proxy.RuntimeSupport;

public class UnoRoomExtension extends BaseTurnRoomAdaptor{
	
	private int k=1;
	String msg;
	public int curPlayerIndex;
	public static int index ;
	
	
	private IZone izone;
	private ITurnBasedRoom gameRoom;
	private byte GAME_STATUS;
	public static ArrayList<Player> PlayerList= new ArrayList<Player>();
	public ArrayList<Card> posblMov= new ArrayList<Card>();
	
	private String discard;
	private String dscrd;
	
	public UnoRoomExtension(IZone izone, ITurnBasedRoom room){
        this.izone = izone;
        this.gameRoom = room;
        //GAME_STATUS = CardsConstants.STOPPED;
        //System.out.println("Zone app key "+izone.getAppKey()+ "Turnbased Room name "+ room.getName()+ " turnroom turntime "+ room.getTurnTime() );
        
    }
	@Override
    public void handleChatRequest(IUser sender, String message, HandlingResult result){
		
		System.out.println(sender.getName()+ message);
		
		curPlayerIndex = gameRoom.getJoinedUsers().indexOf(sender);
		
		if(message.substring(0, 2).equals("PM"))
		{
		
			GameLogic gm = new GameLogic();
			Player obp = new Player();
			dscrd = message.substring(2);
			System.out.println(dscrd);
			Card ob = new Card();
			
			ob.setColor_code(message.substring(2,3).charAt(0));
			ob.setCard_no(Integer.parseInt(message.substring(3))); 
			
			
			int rotation = gm.getRotation(dscrd);
			index = gm.getNextPlayerIndex(rotation, curPlayerIndex);	
			System.out.println("Index in PM "+index);
			//PlayerList.get(curPlayerIndex).getList_of_cards().remove(ob);
		
				posblMov = gm.possibleMoves(PlayerList.get(index));
				System.out.println("In else");
				String mesg = "*";
				for(int i=0; i<posblMov.size(); i++)
				{
					mesg = mesg + posblMov.get(i).CardName() + "/";
				}
				
				gameRoom.getJoinedUsers().get(index).SendChatNotification("Server", mesg, gameRoom);
				
				if(dscrd.charAt(1) == '1' && dscrd.charAt(2) == '2')
				{
					 System.out.println("Insind draw 2 card cond");
					 ChallengeHandling chHandle = new ChallengeHandling();
					 Player player = new Player();
					 for(int i=0; i<gameRoom.getJoinedUsers().size(); i++)
					 {
						 if(PlayerList.get(i).getName() == sender.getName())
						 {
							 chHandle.punishment("PLUS2", null, PlayerList.get(i));
							 break;
						 }
					 }
					 
					 String drwcrd = "$$";
					 for(int i=0; i<2; i++)
					 {
						 drwcrd += chHandle.draw2card.get(i).CardName() + "##";
					 }
					 System.out.println("Draw cards" + drwcrd);
					 gameRoom.getJoinedUsers().get(index).SendChatNotification("Server", drwcrd, gameRoom);
				} 
				 
				/*else if(dscrd.charAt(0) == 'W' && dscrd.charAt(1) == '2')
				{
					 System.out.println("Insind draw 4 card cond");
					 ChallengeHandling chHandle = new ChallengeHandling();
					 Player player = new Player();
					 for(int i=0; i<gameRoom.getJoinedUsers().size(); i++)
					 {
						 if(PlayerList.get(i).getName() == sender.getName())
						 {
							 chHandle.punishment("WILD4", null, PlayerList.get(i));
							 break;
						 }
					 }
					 
					 String drwcrd = "$$";
					 for(int i=0; i<2; i++)
					 {
						 drwcrd += chHandle.draw2card.get(i).CardName() + "##";
					 }
					 System.out.println("Draw cards" + drwcrd);
					 gameRoom.getJoinedUsers().get(index).SendChatNotification("Server", drwcrd, gameRoom);
				} */
		//	}
		}
		
		else if(message.substring(0).equals("DC"))
		{
			String dc = "$$";
			Card obc = new Card();
			CardControl obcc = new CardControl();
			obc = obcc.cardList.remove(0); 
			
			PlayerList.get(curPlayerIndex).addCard(obc);
			dc = dc + obc.CardName()+"##";
			gameRoom.getJoinedUsers().get(curPlayerIndex).SendChatNotification("Server", dc, gameRoom);
			
		}
	}
	
	
	 @Override
	    public void onTimerTick(long time){
		
		 //System.out.println("Nothing else matters");
		 //System.out.println(gameRoom.getJoinedUsers().size()+" ....."+ gameRoom.getMaxUsers());
		 if( gameRoom.getJoinedUsers().size() == gameRoom.getMaxUsers() && k==1)
		 {
			 //GAME_STATUS = CardsConstants.RUNNING;
			 k=0;
			 //CardControl CardCon= new CardControl();
			 //System.out.println(gameRoom.getJoinedUsers().size()+"<><><>"+ gameRoom.getMaxUsers());
			 for(int i=0; i< gameRoom.getMaxUsers(); i++)
			 {
				 Player p= new Player();
				 p.setName(gameRoom.getJoinedUsers().get(i).getName());
				 PlayerList.add(p);
			 }
			/* for (Player player : PlayerList) {
				 System.out.println(player.getName());
				
			}*/
			 
			 CardControl CardCon= new CardControl();
			 CardCon.initialize();
			 PlayerList = CardCon.distributeCards(PlayerList);
			 int j=0;
			 for (Player player : PlayerList)
			 {
				//System.out.println("this is the player name"+player.getName());
				msg="$$";
				for(int i=0;i<player.getList_of_cards().size();i++)
				{
					//System.out.println(player.getList_of_cards().get(i).CardName());
					msg= msg + player.getList_of_cards().get(i).CardName()+"##";
				}
				//System.out.println(msg);
				gameRoom.getJoinedUsers().get(j).SendChatNotification("Server", msg, gameRoom);
				discard = "D" + CardCon.getDiscardpile().CardName();
				gameRoom.getJoinedUsers().get(j).SendChatNotification("Server", discard, gameRoom);
				j++;
				
				
				//gameRoom.getJoinedUsers().get(0).SendChatNotification("Server", "this is a string", gameRoom);
				//gameRoom.getJoinedUsers().get(1).SendChatNotification("Server", "this is a string", gameRoom);
				//gameRoom.BroadcastChat("MasterServer", "Just chatting");
			}
			gameRoom.startGame("game");
			System.out.println("game started");
			
			// for the first player possible moves 
			
			Card c = new Card();
			c = CardCon.getDiscardpile();
			ArrayList<Card> Pcards = new ArrayList<Card>();
			GameLogic G = new GameLogic();
			System.out.println("above possible move  Player List at 0  "+ PlayerList.get(0).getName());
			Pcards = G.possibleMoves(PlayerList.get(0));
			
			String cards= "*";
			for (Card card : Pcards) 
			{
				cards = cards + card.CardName() + "/";
			}
			
			System.out.println("First player possible Moves::" + cards);
			System.out.println("Whoes turn " + gameRoom.getNextTurnUser().getName());
			gameRoom.getJoinedUsers().get(0).SendChatNotification("Server", cards, gameRoom);
		 }
		 
	 }
	 @Override
	    public void handleMoveRequest(IUser sender, String moveData, HandlingResult result){
		 System.out.println(sender.getName() +" send data "+ moveData);
		 System.out.println("Card No: " + moveData.substring(1));
		 for(int i=0; i<PlayerList.size(); i++)
		 {
			 if(PlayerList.get(i).getName()== sender.getName())
			 {
				Card c= new Card();
				 c.setColor_code(moveData.charAt(0)); 
				 c.setCard_no(Integer.parseInt(moveData.substring(1)));
				 System.out.println("Card to be removed "+ c.CardName());
				 PlayerList.get(i).remove_Cards(moveData);
				System.out.println("After Removing the cards Player Name "+ PlayerList.get(i).getName());
				 
				 for(int j=0; j<PlayerList.get(i).getList_of_cards().size(); j++)
				 {
					 System.out.println("####"+PlayerList.get(i).getList_of_cards().get(j).CardName());
				 }
				 
				 CardControl CardControl = new CardControl();
				 CardControl.setDiscardpile(c);
				 String Move= "@"+ moveData;
				 for(int j=0; j<PlayerList.size(); j++)
				 {
					 gameRoom.getJoinedUsers().get(j).SendChatNotification("Server", Move, gameRoom);
				 }
			 }
		 }
		
		 
	 }
}
