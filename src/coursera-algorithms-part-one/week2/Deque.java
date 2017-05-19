import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue 
 * that supports adding and removing items from either the front or the back of the data structure.
 */
public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
        public Node(Item item) {
            this.item = item;
        }
    }
    
    private Node firstElement;
    private Node lastElement;
    private int size = 0;
    
    public boolean isEmpty() {
        return (size() == 0);
    }
    
    public int size() {
        return size;
    }
    
    public void addFirst(Item item) {        
        if (item == null) { 
            throw new NullPointerException("The item to be inserted cannot be null.");
        }
        
        Node newNode = new Node(item);
        
        if (isEmpty()) {
            firstElement = newNode;
            lastElement = firstElement;
        } else {
            firstElement.previous = newNode;            
            newNode.next = firstElement;       
            firstElement = newNode;    
        }
                
        size++;
    }
    
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("The item to be inserted cannot be null.");
        }
        
        Node newNode = new Node(item);
        
        if (isEmpty()) {
            firstElement = newNode;
            lastElement = firstElement;
        } else {
            lastElement.next = newNode;    
            lastElement = newNode;
        }
        
        size++;
    }
    
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }
        
        Item removedItem = firstElement.item;
        firstElement = firstElement.next;
        
        if (firstElement == null) {
            lastElement = null;
        } else {
            firstElement.previous = null;   
        }
        size--;
        return removedItem;
    }
    
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The deque is empty.");
        }
        
        Item removedItem = lastElement.item;
        
        if (lastElement == firstElement) {
            firstElement = null;
            lastElement = null;
        } else {
            lastElement.previous.next = null;
            lastElement.previous = lastElement;
        }
        size--;
        return removedItem;
    }
    
    /**
     * Iterates the Deque from the front to the end
     */
    public Iterator<Item> iterator() {
        return new DequeIterator(firstElement);
    }

    private class DequeIterator implements Iterator<Item> {

        private Node firstElement;
        
        public DequeIterator(Node firstElement) {
            this.firstElement = firstElement;
        }

        @Override
        public boolean hasNext() {
            return (firstElement != null);
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There are no more items in the Deque.");
            }
            Item nextItem = firstElement.item;
            firstElement = firstElement.next;
            return nextItem;
        }    
    }
}