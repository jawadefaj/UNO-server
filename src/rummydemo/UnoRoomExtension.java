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

public class UnoRoomExtension extends BaseTurnRoomAdaptor{
	
	private int k=1;
	String msg;
	private int curPlayerIndex, index;
	
	
	
	private IZone izone;
	private ITurnBasedRoom gameRoom;
	private byte GAME_STATUS;
	public ArrayList<Player> PlayerList= new ArrayList<Player>();
	public ArrayList<Card> possibleMoves= new ArrayList<Card>();
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
		/*if(message.startsWith("#$", 0))
		{
			message = message.substring(2);
			Room R= new Room();
			
			for(int i=0; i<RoomList.size(); i++)
			{
				if(RoomList.get(i).RoomName.equals(message))
				{
					if(RoomList.get(i).MaxPlayerSize > RoomList.get(i).playerList.size())
					{
						//RoomList.get(i).AddPlayer(Player);
					}
					
					else
					{
						
					}
				}
			}	
		}*/
		
		
		//Avik 7.4.2015
		System.out.println(sender.getName()+ message);
		curPlayerIndex = gameRoom.getJoinedUsers().indexOf(sender);
		
		if(message.equals("PM"))
		{
			GameLogic gm = new GameLogic();
			Card ob = new Card();
			
			ob.setColor_code(message.charAt(0));
			ob.setCard_no(Integer.parseInt(message.substring(1)));
			
			int rotation = gm.getRotation(ob, gm.curRotation());
			index = getCurPlayerIndex(curPlayerIndex);	
			
			possibleMoves = gm.possibleMoves(ob, PlayerList.get(index));
			String mesg = "*";
			for(int i=0; i<possibleMoves.size(); i++)
			{
				mesg = msg + possibleMoves.get(i) + "/";
			}
			gameRoom.getJoinedUsers().get(index).SendChatNotification("Server", mesg, gameRoom);
		}
		else if(message.equals("DC"))
		{
			String dc = "&";
			Card obc = new Card();
			CardControl obcc = new CardControl();
			obc = obcc.cardList.remove(0);
			PlayerList.get(curPlayerIndex).addCard(obc);
			dc = dc + obc.CardName();
			gameRoom.getJoinedUsers().get(curPlayerIndex).SendChatNotification("Server", dc, gameRoom);
			
		}
		
		
		
		
		
	}
	
	public int getCurPlayerIndex(int rotation)
	{
		Player obP = new Player();
		int nextPlayerIndex = 0;
		
		if(rotation == 1)
		{
			if(curPlayerIndex == gameRoom.getJoinedUsers().size())
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
				nextPlayerIndex=gameRoom.getJoinedUsers().size();
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
				/* Card c= new Card();
				 char CardColor = moveData[0];
				 c.se
				 
				 PlayerList.get(i).getList_of_cards().remove()
				 */
			 }
		 }
		 
	 }
	

	

}
