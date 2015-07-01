package rummydemo;
import java.awt.List;
import java.util.ArrayList;

import com.shephertz.app42.server.idomain.BaseTurnRoomAdaptor;
import com.shephertz.app42.server.idomain.HandlingResult;
import com.shephertz.app42.server.idomain.ITurnBasedRoom;
import com.shephertz.app42.server.idomain.IUser;
import com.shephertz.app42.server.idomain.IZone;

import java.util.Collections;

public class UnoRoomExtension extends BaseTurnRoomAdaptor{
	
	private IZone izone;
	private ITurnBasedRoom gameRoom;
	private byte GAME_STATUS;
	public ArrayList<Player> PlayerList= new ArrayList<Player>();
	

	public UnoRoomExtension(IZone izone, ITurnBasedRoom room){
        this.izone = izone;
        this.gameRoom = room;
        //GAME_STATUS = CardsConstants.STOPPED;
        System.out.println("Zone app key "+izone.getAppKey()+ "Turnbased Room name "+ room.getName()+ " turnroom turntime "+ room.getTurnTime() );
        
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
		 if( gameRoom.getJoinedUsers().size() == gameRoom.getMaxUsers() )
		 {
			 //GAME_STATUS = CardsConstants.RUNNING;
			 
			 //CardControl CardCon= new CardControl();
			 //System.out.println(gameRoom.getJoinedUsers().size()+"<><><>"+ gameRoom.getMaxUsers());
			 for(int i=0; i< gameRoom.getMaxUsers(); i++)
			 {
				 Player p= new Player();
				 p.setName(gameRoom.getJoinedUsers().get(i).getName());
				 PlayerList.add(p);
			 }
			 for (Player player : PlayerList) {
				 System.out.println(player.getName());
				
			}
		 }
		 
	 }
	 
	

	

}