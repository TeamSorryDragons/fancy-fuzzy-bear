import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author TeamSorryDragons
 *
 */
public class Deck {
	
	protected Card[] cards;
	protected int topCard;
	
	public Deck(String lang) throws FileNotFoundException{
		FileReader fr=new FileReader(lang+".txt");
		cards=new Card[45];
		Scanner in= new Scanner(fr);
		in.reset();

		Card t=new Card(1,in.nextLine());
		for(int i =0; i<5;i++)
			cards[i*11+0]=t;
		
		t=new Card(2,in.nextLine());
		for(int i =0; i<4;i++)
			cards[i*11+1]=t;
		t=new Card(3,in.nextLine());
		for(int i =0; i<4;i++)
			cards[i*11+2]=t;
		t=new Card(4,in.nextLine());
		for(int i =0; i<4;i++)
			cards[i*11+3]=t;
		t=new Card(5,in.nextLine());
		for(int i =0; i<4;i++)
			cards[i*11+4]=t;
		t=new Card(7,in.nextLine());
		for(int i =0; i<4;i++)
			cards[i*11+5]=t;
		t=new Card(8,in.nextLine());
		for(int i =0; i<4;i++)
			cards[i*11+6]=t;
		t=new Card(10,in.nextLine());
		for(int i =0; i<4;i++)
			cards[i*11+7]=t;
		t=new Card(11,in.nextLine());
		for(int i =0; i<4;i++)
			cards[i*11+8]=t;
		t=new Card(12,in.nextLine());
		for(int i =0; i<4;i++)
			cards[i*11+9]=t;
		t=new Card(13,in.nextLine());
		for(int i =0; i<4;i++)
			cards[i*11+10]=t;

		this.shuffle();
	}
	public Card getTopCard(){
		Card c= cards[topCard];
		topCard++;
		if(topCard==cards.length){
			this.shuffle();
		}

		return c;
	}
	private void shuffle(){
		topCard=0;
		Random rand= new Random();
		for(int i=0; i<cards.length;i++){
			Card temp=cards[0];
			int randIndex=rand.nextInt(cards.length);
			cards[0]=cards[randIndex];
			cards[randIndex]=temp;
		}
	}
	@Override
	public String toString(){
		String s="";
		for(Card c: cards)
			s+=c.cardNum + " ";
		return s;
	}
}
