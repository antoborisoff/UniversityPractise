import java.util.*;

public class Vertex {
    private Vertex prev;
    private Vertex next;
    private ArrayList<Card> cards;
    private boolean hashandcard;
    private int handcardindex;
    
    Vertex(Card card) throws MyException{
        if(cards==null){
            cards=new ArrayList<Card>();
        }
        cards.add(card);
    }
    public Vertex getPrev(){
        return prev;
    }
    public Vertex getNext(){
        return next;
    }
    public void setPrev(Vertex prev){
        this.prev=prev;
    }
    public void setNext(Vertex next){
        this.next=next;
    }
    public int getValue() throws MyException{
        if(cards.size()==0){
            throw new MyException("No cards!");
        }
        else{
            return cards.get(0).getValue();
        }
    }
    public void addCard(Card card) throws MyException{
        if(cards==null){
            cards=new ArrayList<Card>();
            cards.add(card);
            if(card.isHandCard()){
                hashandcard=true;
                handcardindex=0;
            }
        }
        else{
            if(this.getValue()!=card.getValue()){
                throw new MyException("Cannot add a card!");
            }
            else {
                cards.add(card);
                if (card.isHandCard()) {
                    hashandcard = true;
                    handcardindex = cards.size();
                }
            }
        }
        
    }
    public boolean getHasHandCard(){
        return hashandcard;
    }
    public void setHasHandCard(boolean value){
        hashandcard=value;
    }
    public int getNumOfSuits(){
        return cards.size();
    }
    public ArrayList<Card> getCards(){
       return cards;
    }
    public Card getCardByIndex(int i) throws MyException{
        if(i>0&&i<cards.size()-1){
            return cards.get(i);
        }
        else{
            throw new MyException("Weong index!");
        }
    }
    public Card getHandCard() throws MyException{
        return this.getCardByIndex(handcardindex);
    }
}
