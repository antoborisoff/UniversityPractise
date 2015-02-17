import java.util.*;
public class Comp implements Comparator<Card> {
    @Override
    public int compare(Card A, Card B) {
        try {
            int suitA = A.getSuit();
            int suitB = B.getSuit();
            if (suitA != suitB) {
                return suitB - suitA;
            } else {
                return B.getValue() - A.getValue();
            }
        } catch (MyException e) {
            System.err.println("Comaprison error!");
            return -1;
        }
    }
}
