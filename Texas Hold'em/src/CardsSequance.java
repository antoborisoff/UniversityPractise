import java.util.*;
import java.io.*;

public class CardsSequance {
    private String sequance;
    private ArrayList<Card> splitcards;
    private boolean split;
    private boolean splitcardssotred;
    private MyList cardsgroupedbyvalue;
    private boolean groupedbyvalue;
    private ArrayList<Card> combination;
    private static String cards=" 2C 3C 4C 5C 6C 7C 8C 9C JC QC KC AC"
                              + " 2D 3D 4D 5D 6D 7D 8D 9D JD QD KD AD"
                              + " 2S 3S 4S 5S 6S 7S 8S 9S JS QS KS AS"
                              + " 2H 3H 4H 5H 6H 7H 8H 9H JH QH KH AH";
    private static int cardslength=2;
    private static int handcardsnum=2;
    private static int comblength=5;
    
    public CardsSequance(){
        sequance=new String();
        combination=new ArrayList<Card>();
        splitcards=new ArrayList<Card>();
        cardsgroupedbyvalue=new MyList();
    }
    public CardsSequance(String cards) throws MyException{
        sequance=this.checkSequance(cards);
        combination=new ArrayList<Card>();
        splitcards=new ArrayList<Card>();
        cardsgroupedbyvalue=new MyList();
    }
    public String checkSequance(String sequance) throws MyException{
        if(sequance==null){
            throw new MyException("No cards!");
        }
        StringTokenizer st=new StringTokenizer(sequance," ");
        String buf;
        StringBuilder availablecards=new StringBuilder(cards);
        StringBuilder ressequance=new StringBuilder();
        int pos=0;
        while(st.hasMoreTokens()){
            buf=st.nextToken();
            if(buf.length()!=cardslength){
                throw new MyException("Wrong card format(length)!");
            }
            pos=availablecards.indexOf(buf);
            if(pos==-1){
                throw new MyException(buf+" card is not available!");
            }
            else{
                ressequance.append(" ");
                ressequance.append(buf);
                availablecards.delete(pos,pos+cardslength+1);
            }
        }
        return new String(ressequance);
    }
    public String generateSequance(int numofcards) throws MyException{
        StringBuilder res=new StringBuilder();
        Random rand=new Random();
        boolean flag=false;
        StringBuilder availablecards=new StringBuilder(cards);
        if(availablecards.length()/(cardslength+1)<numofcards){
            throw new MyException("Not enough cards!");
        }
        int div=availablecards.length()/(cardslength+1);
        int cardpos;
        int cardnumber;
        while(numofcards>0){
            cardnumber=Math.abs(rand.nextInt()%div);
            int buf=availablecards.length();
            cardpos=(cardslength+1)*cardnumber;
            res=res.append(availablecards.substring(cardpos, cardpos+cardslength+1));
            availablecards.delete(cardpos, cardpos+cardslength+1);
            numofcards=numofcards-1;
            div=div-1;
        }
        return new String(res);
    }
    public String toString(){
        return sequance;
    }
    
    
    public void splitInCards() throws MyException{
        if(sequance==null){
            throw new MyException("No cadrs!");
        }
        StringTokenizer st=new StringTokenizer(sequance," ");
        int buf=handcardsnum;
        while(st.hasMoreTokens()){
            if (handcardsnum > 0) {
                splitcards.add(new Card(st.nextToken(), true));
                handcardsnum = handcardsnum - 1;
            } else {
                splitcards.add(new Card(st.nextToken(), false));
            }
        }
        split=true;
    }
    public void addSpecialCard() throws MyException{
        Iterator<Card> it=splitcards.iterator();
        Card buf;
        ArrayList<Card> specialcards=new ArrayList<Card>();
        while(it.hasNext()){
            buf=it.next();
            if(buf.getValue()==buf.getACEVALUE()){
                switch(buf.getSuit())
                {
                    case 1:
                        specialcards.add(new Card("1C",buf.isHandCard()));
                        break;
                    case 2:
                        specialcards.add(new Card("1S",buf.isHandCard()));
                        break;
                    case 3:
                        specialcards.add(new Card("1H", buf.isHandCard()));
                        break;
                    case 4:
                        specialcards.add(new Card("1D", buf.isHandCard()));
                        break;
                    default: 
                        throw new MyException("Impossible ace value!");
                }
            }
        }
        splitcards.addAll(specialcards);
    }
    public void sortSplitCardsBySuitValue(){
        Collections.sort(splitcards,new Comp());
        splitcardssotred=true;
    }
    public void printSplitCards(){
        Iterator<Card> it=splitcards.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
    
    
    public boolean checkForRoyalStreetFlash() throws MyException{
        if(sequance==null){
            throw new MyException("No cards!");
        }
        if(splitcards.size()<comblength){
            return false;
        }
        if(!splitcardssotred){
            this.sortSplitCardsBySuitValue();
        }
        Iterator<Card> it=splitcards.iterator();
        if(!it.hasNext()){
            throw new MyException("No cards!");
        }
        Card prev=it.next();
        Card cur=null;
        int prevsuit=prev.getSuit();
        int cursuit=0;
        int prevvalue=prev.getValue();
        int curvalue=0;
        boolean handcardfound=false;
        ArrayList<Card> buf=new ArrayList<Card>();
        buf.add(prev);
        while(it.hasNext()){
            cur=it.next();
            cursuit=cur.getSuit();
            curvalue=cur.getValue();
            if((curvalue-prevvalue==1)&&(cursuit==prevsuit)){
                buf.add(cur);
                prev=cur;
                prevsuit=cursuit;
                prevvalue=curvalue;
                if(cur.isHandCard()){
                    handcardfound=true;
                }
            }
            else{
                if(buf.size()<comblength){
                    buf.removeAll(buf);
                    handcardfound=false;
                }
                else{//так как эта комбинация будет только одна
                    while(it.hasNext()){
                        it.next();
                    }
                }
            }
        }
        //////////////////
        if(!handcardfound){
            return false;
        }
        else {
            it = buf.iterator();
            int count = 0;
            handcardfound = false;
            if (!it.hasNext()) {
                return false;
            } else {
                Card buffer = it.next();
                if (buffer.getValue() == buffer.getACEVALUE()) {
                    buf.add(buffer.specialCard(buffer));
                }
                while (it.hasNext() && count < comblength && !handcardfound) {
                    combination.add(it.next());
                    count = count + 1;
                }
                count = 0;
                while (it.hasNext() && !handcardfound) {
                    combination.add(it.next());
                    combination.remove(count);
                    count = count + 1;
                }
                return true;
            }
        }
    }
    
    
    public void groupCardsByValue() throws MyException{
        if(!split){
            this.splitInCards();
        }
        if(!splitcardssotred){
            this.sortSplitCardsBySuitValue();
        }
        Iterator<Card> it=splitcards.iterator();
        while(it.hasNext()){
            cardsgroupedbyvalue.add(it.next());
        }
        groupedbyvalue=true;
    }
    public boolean checkForFour() throws MyException{
        if(sequance==null){
            throw new MyException("No cards!");
        }
        if(splitcards.size()<comblength){
            return false;
        }
        if(!groupedbyvalue){
            this.groupCardsByValue();
        }
        ArrayList<Card> buf=cardsgroupedbyvalue.findFour();
        if(buf!=null){
            combination=buf;//4 карты!!!
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkForFullHouse() throws MyException{
        if(sequance==null){
            throw new MyException("No cards!");
        }
        if(splitcards.size()<comblength){
            return false;
        }
        if(!groupedbyvalue){
            this.groupCardsByValue();
        }
        ArrayList<Card> buf=cardsgroupedbyvalue.findFullHouse();
        if(buf!=null){
            combination=buf;//4 карты!!!
            return true;
        }
        else{
            return false;
        }
    }
    public boolean checkForFlash() throws MyException{
        if(sequance==null){
            throw new MyException("No cards!");
        }
        if(splitcards.size()<comblength){
            return false;
        }
        if(!splitcardssotred){
            this.sortSplitCardsBySuitValue();
        }
        Iterator<Card> it=splitcards.iterator();
        int suitcount=0;
        int suitcur=0;
        int suitprev=0;
        Card cur;
        Card prev;
        ArrayList<Card> buf=new ArrayList<Card>();
        if(!it.hasNext()){
            throw new MyException("No split cards!");
        }
        else{
            cur=it.next();
            suitcur=cur.getSuit();
        }
        while(it.hasNext()){
            prev=cur;
            suitprev=suitcur;
            cur=it.next();
            boolean hashandcard = false;
            if (suitprev == suitcur) {
                if (suitcount < comblength) {
                    suitcount = suitcount + 1;
                    buf.add(cur);
                    if (cur.isHandCard()) {
                        hashandcard = true;
                    }
                } else {
                    if (!hashandcard) {
                        suitcount = suitcount + 1;
                        buf.add(cur);
                        buf.remove(suitcount - comblength);
                        if (cur.isHandCard()) {
                            hashandcard = true;
                        }
                    }
                }
            }
            else{
                if(suitcount<comblength){
                    suitcount=0;
                    buf.removeAll(buf);
                    hashandcard = false;
                }
                else {
                    if (hashandcard) {
                        while (it.hasNext()) {
                            it.next();
                        }
                    }
                    else{
                        suitcount = 0;
                        buf.removeAll(buf);
                        hashandcard = false;
                    }
                }
            }
        }
        if(buf==null){
            return false;
        }
        else{
            combination=buf;
            return true;
        }
    }
    public static int getCombLength(){
        return comblength;
    }
   /* public boolean checkForStreet() throws MyException{
         if(sequance==null){
            throw new MyException("No cards!");
        }
        if(splitcards.size()<comblength){
            return false;
        }
        if(!groupedbyvalue){
            this.groupCardsByValue();
        }
        ArrayList<Card> buf=cardsgroupedbyvalue.findStreet();
        if(buf!=null){
            combination=buf;
            return true;
        }
        else{
            return false;
        }
    }*/
}
