package deque;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class JavaDeque {
    public static void main(String[] args) {
        Deque<Integer> arrayDeque = new ArrayDeque<>();
        Deque<Integer> linkedBlockingDeque = new LinkedBlockingDeque<>();
        Deque<Integer> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
        Deque<Integer> linkedList = new LinkedList<>();

        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1); // 앞 쪽에 데이터를 삽입, 용량 초과시 Exception
        deque.offerFirst(2); // 앞 쪽에 데이터 삽입 후 True, 실패 시 False
        deque.addLast(3); // 뒤 쪽에 데이터를 삽입, 용량 초과시 Exception
        deque.add(4); // 뒤 쪽에 데이터를 삽입, 용량 초과시 Exception
        deque.offerLast(5); // 뒤 쪽에 데이터를 삽이 후 True, 실패시 False
        deque.offer(6); // offerLast

        deque.push(7); // addFirst

        System.out.println(deque.pop()); // removeFirst
        System.out.println(deque);
        System.out.println(deque.getFirst()); // 비어있으면 예외
        System.out.println(deque.peekFirst()); // 비어있으면 null 반환
        System.out.println(deque.peek()); // == peekFirst()
        System.out.println(deque.getLast()); // 비어있으면 예외
        System.out.println(deque.peekLast()); // 비어있으면 null 반환
        System.out.println("size: "+deque.size() + "\n");

        Iterator<Integer> reversedDequeIterator = deque.descendingIterator();
        while(reversedDequeIterator.hasNext())
            System.out.print(reversedDequeIterator.next() + " ");
    }
}
