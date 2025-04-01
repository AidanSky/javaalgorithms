import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // initialize necessary variables
    private int size = 0;
    private Node first = null;
    private Node last = null;

    // create a doubly linked list
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("cannot be null");
        }
        
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;

        if (isEmpty()) {
            last = first;
        } else {
            oldfirst.prev = first;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("cannot be null");
        }

        Node oldlast = last;
        last = new Node();
        last.next = null;
        last.prev = oldlast;

        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException("nothing to remove");
        }

        Item item = first.item;
        first = first.next;
        size--;

        // is htis necessaqry since erroR?
        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException("nothing to remove");
        }

        Item item = last.item;
        last = last.prev;
        size--;

        // is htis necessaqry since erroR?
        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }

        return item;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null; 
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("No remove function");
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(0);
        deque.addLast(3);

        System.out.println("Size: " + deque.size()); // Output: 4

        System.out.println("Remove First: " + deque.removeFirst()); // Output: 0
        System.out.println("Remove Last: " + deque.removeLast()); // Output: 3

        System.out.println("Remaining elements:");
        for (int x : deque) {
            System.out.print(x + " "); // Output: 1 2
        }
    }
}