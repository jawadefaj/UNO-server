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
	
	
	
	
	private IZone izone;
	private ITurnBasedRoom gameRoom;
	private byte GAME_STATUS;
	public ArrayList<Player> PlayerList= new ArrayList<Player>();
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
		 
		 
	 }
	

	

}
