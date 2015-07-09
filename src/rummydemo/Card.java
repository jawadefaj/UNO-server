package rummydemo;
/*
	color_code = 'b', 'g', 'y', 'r', 'p';
	card_NO = 0,1,2,3,4,5,6,7,8,9,10,11,12;
	Say
	b0 = blue 0
	.. = ......
	b9 = blue 9
	b10 = blue skip;
	b11 = blue reverse;
	b12 = blue +2;
	W1 = color change wild card
	W2 = Wild +4
*/
public class Card {
	
	private char color_code;
	private int card_no;

	
	public Card()
	{
		this.card_no = 0;
		this.color_code = ' ';
	}
	
	
	public Card(char color_code, int card_no) {
		super();
		this.color_code = color_code;
		this.card_no = card_no;
	}


	public int getCard_no() {
		return card_no;
	}
	public void setCard_no(int card_no) {
		this.card_no = card_no;
	}
	public char getColor_code() {
		return color_code;
	}
	public void setColor_code(char color_code) {
		this.color_code = color_code;
	}
	public String CardName()
	{
		return "" + getColor_code() + getCard_no();
	}
	public Card MakeCard(String CardName)
	{
		Card c = new Card();
		c.setColor_code(CardName.charAt(0));
		c.setCard_no(Integer.parseInt(CardName.substring(1)));
		return c;
	}
	
}
