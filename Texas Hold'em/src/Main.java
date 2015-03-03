import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        CardsSequances cs=new CardsSequances();
        try{
            //cs.generateSequances(10, 7, "in.txt");
            cs.readCardsSequances("in.txt");
            ArrayList<CardsSequance> buf=cs.getCardsSequances();
            Iterator<CardsSequance> it=buf.iterator();
            CardsSequance buffer;
            while(it.hasNext()){
                buffer=it.next();
                buffer.splitInCards();
                buffer.sortSplitCardsBySuitValue();
                //buffer.printSplitCards();
                System.out.println();
                buffer.addSpecialCard();
                buffer.sortSplitCardsBySuitValue();
                buffer.printSplitCards();
                System.out.println();
                //System.out.println(buffer.checkForRoyalStreetFlash());
            }
        }
        catch(MyException e){
            
        }
        catch(FileNotFoundException e){
            
        }
        catch(IOException e){
            
        }
    }
    
}
