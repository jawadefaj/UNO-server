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
	private int curPlayerIndex, index;
	
	
	
	private IZone izone;
	private ITurnBasedRoom gameRoom;
	private byte GAME_STATUS;
	public ArrayList<Player> PlayerList= new ArrayList<Player>();
	public ArrayList<Card> posblMov= new ArrayList<Card>();
	private String discard;
	

	public UnoRoomExtension(IZone izone, ITurnBasedRoom room){
        this.izone = izone;
        this.gameRoom = room;
        //GAME_STATUS = CardsConstants.STOPPED;
        //System.out.println("Zone app key "+izone.getAppKey()+ "Turnbased Room name "+ room.getName()+ " turnroom turntime "+ room.getTurnTime() );
        
    }
	@Override
    public void handleChatRequest(IUser sender, String message, HandlingResult result){
		
		System.out.println(sender.getName()+ message);
		
		//Avik 7.4.2015
		
		
		System.out.println(sender.getName()+" "+ message);
		curPlayerIndex = gameRoom.getJoinedUsers().indexOf(sender);
		//System.out.println(message.substring(0, 2));
		//System.out.println(message.substring(2, 3));
		//System.out.println(message.substring(3));
		
		if(message.substring(0, 2).equals("PM"))
		{
			//System.out.println(message.substring(0, 2));
			GameLogic gm = new GameLogic();
			Player obp = new Player();
			//System.out.println("Prev Rotation "+gm.Rotation);
			Card ob = new Card();
			
			ob.setColor_code(message.substring(2,3).charAt(0));
			ob.setCard_no(Integer.parseInt(message.substring(3)));
			//System.out.println(ob.CardName());
			
			
			int rotation = gm.getRotation(ob, gm.Rotation);
			//System.out.println("Rotation "+rotation);
			index = getNextPlayerIndex(rotation);	
			//System.out.println("Index "+index);
			PlayerList.get(curPlayerIndex).remove_Cards(ob);
			
			
			posblMov = gm.possibleMoves(ob, PlayerList.get(index));
			String mesg = "*";
			for(int i=0; i<posblMov.size(); i++)
			{
				System.out.println(posblMov.size());
				mesg = mesg + posblMov.get(i).CardName() + "/";
			}
			System.out.println( "Possible cards "+mesg);
			gameRoom.getJoinedUsers().get(index).SendChatNotification("Server", mesg, gameRoom);
		}
		if(message.substring(0).equals("DC"))
		{
			String dc = "$$";
			Card obc = new Card();
			CardControl obcc = new CardControl();
			obc = obcc.cardList.remove(0); 
			
			System.out.println("Bullshit");
			//obc = obcc.cardList.remove(0);
			System.out.println("Random "+obc.CardName());
			PlayerList.get(curPlayerIndex).addCard(obc);
			System.out.println("Player "+PlayerList.get(curPlayerIndex).getName());
			dc = dc + obc.CardName()+"##";
			gameRoom.getJoinedUsers().get(curPlayerIndex).SendChatNotification("Server", dc, gameRoom);
			
		}
	}
	
	public int getNextPlayerIndex(int rotation)
	{
		Player obP = new Player();
		int nextPlayerIndex = 0;
		
		if(rotation == 1)
		{
			if(curPlayerIndex == gameRoom.getJoinedUsers().size()-1)
			{
				nextPlayerIndex=0;
			}
			else{
				nextPlayerIndex = curPlayerIndex +1;
			}
		}
		
		else if(rotation == -1)
		{
			if(curPlayerIndex == 0)
			{
				nextPlayerIndex = gameRoom.getJoinedUsers().size()-1;
			}
			else{
				nextPlayerIndex = curPlayerIndex - 1;
			}
		}
		
		return nextPlayerIndex;
		
		
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
			 for (Player player : PlayerList) {
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
		 }
		 
	 }
	 @Override
	    public void handleMoveRequest(IUser sender, String moveData, HandlingResult result){
		 System.out.println(sender.getName() +" send data "+ moveData);
		 for(int i=0; i<PlayerList.size(); i++)
		 {
			 if(PlayerList.get(i).getName()== sender.getName())
			 {
				Card c= new Card();
				 c.setColor_code(moveData.charAt(0)); 
				 c.setCard_no(Integer.parseInt(moveData.substring(1)));
				 PlayerList.get(i).getList_of_cards().remove(c);
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
