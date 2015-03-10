import java.util.*;
import java.io.*;
public class CardsSequances {
    private ArrayList<CardsSequance> sequances;
    
    public CardsSequances(){
        sequances=new ArrayList<CardsSequance>();
    }
    public void readCardsSequances(String path) 
            throws FileNotFoundException,IOException,MyException{
        FileInputStream fis=new FileInputStream(path);
        Scanner sc=new Scanner(fis);
        while(sc.hasNext()){
            sequances.add(new CardsSequance(sc.nextLine()));
        }
        fis.close();
        sc.close();
    }
    
    public void generateSequances(int numberofsequances,int numberofcards,String path)
            throws FileNotFoundException,IOException,MyException{
        FileOutputStream fos=new FileOutputStream(path);
        PrintStream ps=new PrintStream(fos);
        CardsSequance cards = new CardsSequance();
        while (numberofsequances > 0) {
            ps.println(cards.generateSequance(numberofcards));
            numberofsequances = numberofsequances - 1;
        }
        fos.close();
        ps.close();
    }
    public void printSequances(){
        Iterator<CardsSequance> it=sequances.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
    
    public ArrayList<CardsSequance> getCardsSequances(){
        return sequances;
    }
}
