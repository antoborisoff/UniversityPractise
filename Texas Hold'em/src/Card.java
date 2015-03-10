import java.util.*;
import java.io.*;

public class Card {
    private String card;
    private boolean ishandcard;
    private static  int cardslength=2;
    private static int JACK_VALUE=10;
    private static int QUEEN_VALUE=11;
    private static int KING_VALUE=12;
    private static int ACE_VALUE=13;
    private static int CLUBS_VALUE=1;
    private static int SHAPES_VALUE=2;
    private static int HEARTS_VALUE=3;
    private static int DIAMONDS_VALUE=4;
    
    public Card(){
        card=null;
    }
    public Card(Card B){
        card=new String(B.card);
    }
    public Card specialCard(Card B) throws MyException{
        if (B.getValue() == ACE_VALUE) {
            Card res = new Card(B);
            StringBuilder buf = new StringBuilder(res.card);
            buf.setCharAt(0, '1');
            res.card = new String(buf);
            return res;
        } else {
            return null;
        }
        
    }
    public Card(String card,boolean ishandcard){
        this.card=new String(card);
        this.ishandcard=ishandcard;
    }
    public int getValue() throws MyException {
        if (card == null) {
            throw new MyException("No card!");
        }
        char buf = card.charAt(0);
        if (buf > '0' && buf <= '9') {
            return (buf - '0');
        } else {
            switch (buf) {
                case 'J':
                    return JACK_VALUE;
                case 'Q':
                    return QUEEN_VALUE;
                case 'K':
                    return KING_VALUE;
                case 'A':
                    return ACE_VALUE;
            }
        }
        throw new MyException("Invalid value!");
    }
    public int getSuit() throws MyException {
        if (card == null) {
            throw new MyException("No card!");
        }
        switch (card.charAt(1)) {
            case 'S':
                return SHAPES_VALUE;
            case 'C':
                return CLUBS_VALUE;
            case 'H':
                return HEARTS_VALUE;
            case 'D':
                return DIAMONDS_VALUE;
            default:
                throw new MyException("Invalid suit!");
        }
    }
    public int difFrom(Card B) throws MyException{
        return this.getValue()-B.getValue();
    }
    public boolean isHandCard(){
        return ishandcard;
    }
    public int getACEVALUE(){
        return ACE_VALUE;
    }
    
    public String toString(){
        return card;
    }
}

    
    


