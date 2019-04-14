package Engine.src.ECS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * An iterable, unordered pair with a robust check for equality.
 */
public class Pair<T> implements Iterable<T> {
    ArrayList<T> myItems;

    public Pair(T item1, T item2) {
        myItems = new ArrayList<>();
        myItems.add(item1);
        myItems.add(item2);
    }

    public T getItem1() {
        return myItems.get(0);
    }

    public T getItem2() {
        return myItems.get(1);
    }

    @Override
    public boolean equals(Object other) {
        if (this.getClass().isInstance(other)) {
            var otherPair = (Pair<T>) other;
            boolean currentOrder = getItem1().equals(otherPair.getItem1()) && getItem2().equals(otherPair.getItem2());
            boolean reversedOrder = getItem1().equals(otherPair.getItem2()) && getItem2().equals(otherPair.getItem1());
            return currentOrder || reversedOrder;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getItem1().hashCode() + getItem2().hashCode();
    }

    @Override
    public Iterator<T> iterator() {
        return myItems.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        myItems.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return myItems.spliterator();
    }
}
