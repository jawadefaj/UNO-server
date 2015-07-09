package rummydemo;
import java.awt.List;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Locale.Category;

import com.shephertz.app42.server.idomain.BaseTurnRoomAdaptor;
import com.shephertz.app42.server.idomain.HandlingResult;
import com.shephertz.app42.server.idomain.IRoom;
import com.shephertz.app42.server.idomain.ITurnBasedRoom;
import com.shephertz.app42.server.idomain.IUser;
import com.shephertz.app42.server.idomain.IZone;

import java.util.Collections;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import javassist.util.proxy.RuntimeSupport;

public class UnoRoomExtension extends BaseTurnRoomAdaptor{
	
	/*
	public int k=1;
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
	*/
	
	private IZone izone;
	private ITurnBasedRoom gameRoom;
	public String DisCardPile;
	public ArrayList<Player> PlayerList= new ArrayList<Player>();
	public int curPlayerIndex;
	public Boolean StartBool = true;
	public CardControl CardCon;
	public GameLogic GameLogic;
	public int size;
	
	
	public UnoRoomExtension(IZone izone, ITurnBasedRoom room){
        this.izone = izone;
        this.gameRoom = room;
        
    }
	
	
	@Override
    public void handleChatRequest(IUser sender, String message, HandlingResult result){
		
		System.out.println(sender.getName() +" chat " + message);
		if(message.charAt(0) == 'D' && message.charAt(1) == 'C')
		{
			gameRoom.getJoinedUsers().get(curPlayerIndex).SendChatNotification("Server",
					"$$"+ CardCon.PickACard().CardName()+ "##", gameRoom);
		}
		
	 	
	}
	
	
	 @Override
	    public void onTimerTick(long time){
		 if( gameRoom.getJoinedUsers().size() == gameRoom.getMaxUsers() && StartBool)
		 {
			 StartBool = false;
			 for(int i=0; i< gameRoom.getMaxUsers(); i++)
			 {
				 Player p= new Player();
				 p.setName(gameRoom.getJoinedUsers().get(i).getName());
				 PlayerList.add(p);
			 }
			 for (Player player : PlayerList) {
				 System.out.println(player.getName());
				
			}
			 
			 CardCon= new CardControl();
			 CardCon.initialize();
			 PlayerList = CardCon.distributeCards(PlayerList);
			 System.out.println("Discard pile !");
			 System.out.println("Discard pile" +CardCon.getDiscardpile().CardName());
			 DisCardPile = CardCon.getDiscardpile().CardName();
			 
			 System.out.println("After distribute card");
			 int j=0;
			 String msg;
			 String discard;
			 for (Player player : PlayerList)
			 {
				Print(player);
				msg="$$";
				for(int i=0;i<player.getList_of_cards().size();i++)
				{
					msg= msg + player.getList_of_cards().get(i).CardName()+"##";
				}
				gameRoom.getJoinedUsers().get(j).SendChatNotification("Server", msg, gameRoom);
				discard = "D" + DisCardPile;
				gameRoom.getJoinedUsers().get(j).SendChatNotification("Server", discard, gameRoom);
				j++;
			}
			gameRoom.startGame("game");
			// for the first player possible moves 
			curPlayerIndex = 0;
			size = PlayerList.size();
			ArrayList<Card> FirstPlayerCard = new ArrayList<Card>();
			GameLogic = new GameLogic();
			FirstPlayerCard = GameLogic.possibleMoves(DisCardPile, PlayerList.get(curPlayerIndex));
			String PossibleCard = "*";
			for(int i=0; i< FirstPlayerCard.size(); i++)
			{
				PossibleCard = PossibleCard + FirstPlayerCard.get(i).CardName()+ "/" ;
			}
			gameRoom.getJoinedUsers().get(curPlayerIndex).SendChatNotification("Server", PossibleCard, gameRoom);
			
			
			
		 }
		 
	 }
	 @Override
	    public void handleMoveRequest(IUser sender, String moveData, HandlingResult result){
		 	System.out.println(sender.getName()+ " Sended "+ moveData );
		 	result.doDefaultTurnLogic = false;
		 	if(moveData.charAt(0) == 'W')
		 	{
		 		System.out.println(sender.getName()+ " Sended "+ moveData );
		 		int nextPlayerIndex = GameLogic.getNextPlayerIndex(GameLogic.getRotation(), curPlayerIndex, size);
		 		char c = moveData.charAt(2);
		 		moveData = moveData.substring(0, 1);
		 		DisCardPile = moveData;
		 		SendUpdate(c, nextPlayerIndex);
		 		
		 	}
		 	System.out.println(sender.getName() + " Sended "+ moveData);
		 	curPlayerIndex = gameRoom.getJoinedUsers().indexOf(sender);
		 	PlayerList.get(curPlayerIndex).remove_Cards(moveData);
		 	DisCardPile = moveData;
		 	System.out.println("Card removed ");
		 	//Print(PlayerList.get(curPlayerIndex));
		 
		 // if( skip card and reverse card) else () 
		 
		 
		 if(moveData.charAt(1)=='1' && (moveData.charAt(2)=='0' || moveData.charAt(2)=='1' || moveData.charAt(2) == '2'))
		 {
			 if(moveData.charAt(1)=='1' && moveData.charAt(2)=='0')
			 {
				 int nextPlayerIndex = GameLogic.getNextPlayerIndex(GameLogic.getRotation(), 
						 GameLogic.getNextPlayerIndex(GameLogic.getRotation(), curPlayerIndex, size), size);
				 SendUpdate(nextPlayerIndex);
				 
			 }
			 else if(moveData.charAt(1)=='1' && moveData.charAt(2)=='1')
			 {
				 int nextPlayerIndex = GameLogic.getNextPlayerIndex(GameLogic.getRotation()* (-1), curPlayerIndex, size);
				 SendUpdate(nextPlayerIndex);
			 }
			 else 
			 {
				 int nextPlayerIndex = GameLogic.getNextPlayerIndex(GameLogic.getRotation(), curPlayerIndex, size);
				 String Add2Card ="$$" +CardCon.PickACard().CardName() + "##" + CardCon.PickACard().CardName()+"##";
				 System.out.println(Add2Card);
				 gameRoom.getJoinedUsers().get(nextPlayerIndex).SendChatNotification("Server", Add2Card, gameRoom);
				 nextPlayerIndex = GameLogic.getNextPlayerIndex(GameLogic.getRotation(), nextPlayerIndex, size);
				 SendUpdate(nextPlayerIndex);
			 }
		 }
		 else
		 {
			 int nextPlayerIndex = GameLogic.getNextPlayerIndex(GameLogic.getRotation(), curPlayerIndex, size);
			 SendUpdate(nextPlayerIndex);
		 }
		 
		 
	 }
	 void Print (Player p)
	 {
		 System.out.println(p.getName()+ "->");
		 for (int i = 0; i < p.getList_of_cards().size(); i++)
		 {
			 System.out.println(p.getList_of_cards().get(i).CardName());
		 }
	 }
	 void SendUpdate(int nextPlayerIndex)
	 {
		 ArrayList<Card> possibleCards = new ArrayList<Card>();
		 possibleCards = GameLogic.possibleMoves(DisCardPile, PlayerList.get(nextPlayerIndex));
		 String PossibleCards = "*";
		 for (int i = 0; i < possibleCards.size(); i++)
		 {
			 PossibleCards = PossibleCards + possibleCards.get(i).CardName() + "/";
		 }
		 gameRoom.getJoinedUsers().get(nextPlayerIndex).SendChatNotification("Server", PossibleCards, gameRoom);
		 gameRoom.sendMoveUpdate(gameRoom.getJoinedUsers().get(nextPlayerIndex), "D"+ DisCardPile , "Server");
		 curPlayerIndex = nextPlayerIndex;
	 }
	 
	 void SendUpdate(char c ,int nextPlayerIndex) // for color selection possible cards 
	 {
		 ArrayList<Card> possibleCards = new ArrayList<Card>();
		 possibleCards = GameLogic.possibleMoves(c, PlayerList.get(nextPlayerIndex)); // overloaded function
		 String PossibleCards = "*";
		 for (int i = 0; i < possibleCards.size(); i++)
		 {
			 PossibleCards = PossibleCards + possibleCards.get(i).CardName() + "/";
		 }
		 gameRoom.getJoinedUsers().get(nextPlayerIndex).SendChatNotification("Server", PossibleCards, gameRoom);
		 gameRoom.sendMoveUpdate(gameRoom.getJoinedUsers().get(nextPlayerIndex), "D"+ DisCardPile , "Server");
		 curPlayerIndex = nextPlayerIndex;
	 }
}
