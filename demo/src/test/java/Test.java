import java.util.Deque;
import java.util.LinkedList;

public class Test {
    public static void main(String[] args) {
        Deque<Integer> deque = new LinkedList<Integer>();
        LinkedList<Integer> linkedList = (LinkedList)deque;
        linkedList.add(1);
        Deque<Integer> deque1 = (Deque)linkedList;
        deque1.add(1);
    }
}
