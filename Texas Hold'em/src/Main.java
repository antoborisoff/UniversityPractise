import java.io.*;

public class Main {

    public static void main(String[] args) {
        CardsSequances cs=new CardsSequances();
        try{
            cs.generateSequances(10, 7, "in.txt");
            cs.readCardsSequances("in.txt");
            cs.printSequances();
        }
        catch(MyException e){
            
        }
        catch(FileNotFoundException e){
            
        }
        catch(IOException e){
            
        }
    }
    
}
