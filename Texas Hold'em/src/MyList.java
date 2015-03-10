import java.util.*;
public class MyList {
    private Vertex head;
    private Vertex tail;
    public MyList(){
    }
    public void add(Card card) throws MyException{
        if(card==null){
            throw new MyException("No card!");
        }
        else{
            if(head==null){
                head=new Vertex(card);
                head.setNext(tail);
            }
            else{
                int value=card.getValue();
                Vertex buf=head;
                while(buf.getValue()>value&&buf.getNext()!=null){
                    buf=buf.getNext();
                }
                if(buf.getValue()==value){
                    buf.addCard(card);
                    if(card.isHandCard()){
                        buf.setHasHandCard(true);
                    }
                }
                else{
                    if(buf.getPrev()==null){
                        buf.setPrev(new Vertex(card));
                        head=buf.getPrev();
                        head.setNext(buf);
                    }
                    else{
                        Vertex buf2=buf.getPrev();
                        buf.setPrev(new Vertex(card));
                        buf2.setNext(buf.getPrev());
                        buf.getPrev().setPrev(buf2);
                    }
                }
            }
        }
    }
    public ArrayList<Card> findFour() throws MyException {
        if (head == null) {
            return null;
        } else {
            Vertex buf = head;
            while ((buf != null) && !((buf.getNumOfSuits() == 4) && (buf.getHasHandCard()))) {
                buf = buf.getNext();
            }
            if(buf==null){
                return null;
            }
            else{
                return buf.getCards();
            }
        }
    }
    
    private Vertex  findMaxDouble() throws MyException{
        if (head == null) {
            return null;
        } else{
            Vertex buf=head;
            while(buf!=null&&buf.getNumOfSuits()!=2){
                    buf=buf.getNext();
            }
            if(buf==null){
                return null;
            }
            else{
                return buf;
            }
        }
    } 
    private Vertex  findMaxTriple() throws MyException{
        if (head == null) {
            return null;
        } else{
            Vertex buf=head;
            while(buf!=null&&buf.getNumOfSuits()!=3){
                    buf=buf.getNext();
            }
            if(buf==null){
                return null;
            }
            else{
                return buf;
            }
        }
    }
    private Vertex  findMaxHandDouble() throws MyException{
        if (head == null) {
            return null;
        } else{
            Vertex buf=head;
            while(buf!=null&&!(buf.getNumOfSuits()==2&&buf.getHasHandCard())){
                    buf=buf.getNext();
            }
            if(buf==null){
                return null;
            }
            else{
                return buf;
            }
        }
    }
    private Vertex  findMaxHandTriple() throws MyException{
        if (head == null) {
            return null;
        } else{
            Vertex buf=head;
            while(buf!=null&&!(buf.getNumOfSuits()==3&&buf.getHasHandCard())){
                    buf=buf.getNext();
            }
            if(buf==null){
                return null;
            }
            else{
                return buf;
            }
        }
    }
    
    public ArrayList<Card> findFullHouse() throws MyException{
        if (head == null) {
            return null;
        } else{
            Vertex maxdouble=this.findMaxDouble();
            Vertex maxhanddouble=this.findMaxHandDouble();
            Vertex maxtriple=this.findMaxTriple();
            Vertex maxhandtriple=this.findMaxHandTriple();
            if(maxdouble==null){
                return null;
            }
            if(maxtriple==null){
                return null;
            }
            ArrayList<Card> buf1;
            ArrayList<Card> buf2;
            if(maxhanddouble!=null){
                buf1=maxhanddouble.getCards();
                buf2=maxtriple.getCards();
                buf2.addAll(buf1);
                return buf2;
            }
            else{
                if(maxhandtriple!=null){
                    buf1=maxhandtriple.getCards();
                    buf2=maxdouble.getCards();
                    buf1.addAll(buf2);
                    return buf1;
                }
                else{
                    return null;
                }
                
            }
        }
    }
    public Card findMaxHandCard() throws MyException{
        if (head == null) {
            return null;
        } else{
            Vertex buf=head;
            while(buf!=null&&!buf.getHasHandCard()){
                    buf=buf.getNext();
            }
            if(buf==null){
                return null;
            }
            else{
                return buf.getHandCard();
            }
        }
    }
    public ArrayList<Card> findDouble() throws MyException{
        return this.findMaxHandDouble().getCards();
    }
    public ArrayList<Card> findTriple() throws MyException{
        return this.findMaxHandTriple().getCards();
    }
    //public ArrayList<Card> findStreet() throws MyException{}
}
