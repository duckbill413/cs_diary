package priority_queue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JavaPriorityQueue {
    public static void main(String[] args) throws ParseException {
        PriorityQueue<Integer> ascendingPriorityQueue = new PriorityQueue<>(); // 오름차순
        PriorityQueue<Integer> descendingPriorityQueue = new PriorityQueue<>(
                Collections.reverseOrder()
        ); // 내림차순

        for (int i = 1; i <= 10; i++) {
            ascendingPriorityQueue.add(i);
            descendingPriorityQueue.add(i);
        }
        ascendingPriorityQueue.offer(100);
        descendingPriorityQueue.offer(200);

        for (int i = 1; i <= 11; i++) {
            System.out.println(ascendingPriorityQueue.poll() + " " + descendingPriorityQueue.poll());
        }

        ascendingPriorityQueue.add(10);
        System.out.println("peek: " + ascendingPriorityQueue.peek());
        System.out.println("element: " + ascendingPriorityQueue.element());
        try {
            System.out.println("remove: " + ascendingPriorityQueue.remove());
            System.out.println("remove: " + ascendingPriorityQueue.remove());
        } catch (Exception e) {
            System.out.println("Empty: " + e.getMessage());
        }
        ascendingPriorityQueue.clear();
        System.out.println("size: " + ascendingPriorityQueue.size());

        PriorityQueue<Book> books = new PriorityQueue<>(new Book());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        books.add(new Book(656454384, "소년만화", dateFormat.parse("2022-01-23")));
        books.add(new Book(656454383, "오컬트", dateFormat.parse("2022-01-24")));

        System.out.println(books);
    }
}
