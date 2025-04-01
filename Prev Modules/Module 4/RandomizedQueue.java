import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // initialize necessary variables
    private int size = 0;
    private Node first = null;

    // initialize node structure
    private class Node {
        Item item;
        Node next;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        // pick random number for [size] and iterate through list until reach that number, then remove
        size = 0;
        first = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        Node dummy = new Node();
        dummy.item = item;
        dummy.next = first; // is first initialized at this point?
        first = dummy;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        // doesnt work cuz this remove from the front, not random
        // need to set item as x after finding via rand, then remove from middle of list and set list to look to next
        // Item item = first.item;
        // first = first.next;
        if (size == 0) {
            throw new java.util.NoSuchElementException("queue is empty");
        }

        Node traverse = first;
        int rand = StdRandom.uniformInt(size);
        Node prev = null;

        // // initialize a dummy node to start at beginning and traverse a random amount of nodes whenever dequeue is called?
        // Node dummy = first;
        // Node random = dummy;

        for (int i = 0; i < rand; i++) {
            prev = traverse;
            traverse = traverse.next;
        }

        Item item = traverse.item;

        // pop whatever was chosen
        if (prev == null) { // ***does this work or do I need to clarify first?
            first = first.next;
        } else {
            prev.next = traverse.next;
        }
        size--; 

        return item;
        // traverse through list a random amount of times from the beginning and return something
    }

    // return a random item (but do not remove it)
    public Item sample() {

        if (size == 0) {
            throw new java.util.NoSuchElementException("queue is empty");
        }

        Node traverse = first;
        int rand = StdRandom.uniformInt(size);

        // // initialize a dummy node to start at beginning and traverse a random amount of nodes whenever dequeue is called?
        // Node dummy = first;
        // Node random = dummy;

        for (int i = 0; i < rand; i++) {
            traverse = traverse.next;
        }

        Item item = traverse.item;
        
        return item;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null; 
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No next item");
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
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        System.out.println("Empty: " + queue.isEmpty());  // true
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Size: " + queue.size());     // 3
        System.out.println("Sample: " + queue.sample()); // random number
        System.out.println("Dequeue: " + queue.dequeue()); // random number
        System.out.println("Size: " + queue.size());     // 2
    }
}