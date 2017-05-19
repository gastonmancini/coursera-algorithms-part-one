import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly 
 * at random from items in the data structure.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items = (Item[]) new Object[1];
    private int size = 0;
    
    public boolean isEmpty() {
        return (size() == 0);
    }
    
    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("The item is null.");
        }
        
        // Double size of array items when it is full
        if (size() == items.length) {
            resizeItemsArray(2 * items.length);
        }
        items[size] = item;
        size++;
    }
    
    private void resizeItemsArray(int newArrayLength) {
        Item[] resizedItemsArray = (Item[]) new Object[newArrayLength];
        
        for (int i = 0; i < size(); i++) {
            resizedItemsArray[i] = items[i];
            items[i] = null;
        }
        
        items = resizedItemsArray;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("There are no elements in the Randomized Queue.");
        }
        
        int randomIndex = StdRandom.uniform(size);
        Item dequeuedItem = items[randomIndex];
        
        if (size == 1) {
            items[randomIndex] = null;
        } else {
            items[randomIndex] = items[size - 1];
        }
        
        // Halve size of array items when it is one-quarter full
        if (size > 0 && size == items.length / 4) {
            resizeItemsArray(items.length / 2);
        }
        
        size--;
        return dequeuedItem;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("There are no elements in the Randomized Queue.");
        }
        
        int randomIndex = StdRandom.uniform(size);
        return items[randomIndex];
    }
    
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(this);
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {

        private RandomizedQueue<Item> randomizedQueue;
        
        public RandomizedQueueIterator(RandomizedQueue<Item> randomizedQueue) {
            this.randomizedQueue = randomizedQueue;
        }
        
        @Override
        public boolean hasNext() {
            return !randomizedQueue.isEmpty();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There are no more items in the Randomized Queue.");
            }
            return randomizedQueue.dequeue();
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
