package exercise.exercise4;

import javax.jws.Oneway;
import java.awt.event.ItemListener;
import java.util.Comparator;
import java.util.Iterator;

/**
 * You should implement from zero a data structure that acts as an ArrayList.
 * We have a default capacity of {@link MyImplementedList#DEFAULT_CAPACITY} elements of type <code>E</code>.
 * We have a {@link MyImplementedList#size} property that stores the number of elements of type <code>E</code> from the data structure.
 * We have an array property, {@link MyImplementedList#elementData}, that keeps the elements from the data structure.
 * We have a {@link MyImplementedList#LOAD_FACTOR} property that specify the maximum accepted load of the data structure.
 * We have a {@link MyImplementedList#INCREASE_SIZE_FACTOR} property to use it when we must increase the capacity of the data structure.
 * We have a {@link MyImplementedList#capacityAfterExtending} property to use it to retain the new capacity after increasing it.
 * <p>
 * Starting with this properties we must implement a data structure that acts ~ as an ArrayList for some objects of type <code>E</code>.
 * <p>
 * TODO if you need to throw some exceptions YOU MUST create them!
 * TODO if you get some warning from the compiler you can use @SuppressWarnings("all") before the method name!
 * TODO if you get this error "usage of api documented as @since 1.6+" you should go to File > Project Structure > Modules and make sure you have the Language level >= 1.6!
 * TODO you should expose as <code>public</code> only the methods that you usually use over a collection!
 * TODO if you need a getter/setter for the properties you must define then, but keep in mind the java concepts!
 * TODO make sure you cover all the possible use-cases for your data structure!
 *
 * @author Cristian.Dumitru
 * @since 7/3/2017.
 */
public class MyImplementedList<E> implements Iterable<E> {

    /**
     * The maximum accepted load property of the data structure.
     */
    private static final double LOAD_FACTOR = 0.75d;

    /**
     * The factor for increasing the size of the data structure.
     */
    private static final int INCREASE_SIZE_FACTOR = 2;

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * The size of the ArrayList (the number of elements it contains).
     */
    private int size;

    /**
     * The array buffer into which the elements of the {@link MyImplementedList} are stored.
     */
    private Object[] elementData;

    /**
     * Property to keep the extended capacity.
     * TODO if you choose another way to implement the extending capacity you can define your own properties,
     * TODO but the rest of them must remain as they are.
     */
    public int capacityAfterExtending;

    //TODO a) implement the empty constructor for the your data structure
    public MyImplementedList() {
        //TODO a) HINT - DEFAULT_CAPACITY, capacityAfterExtending and elementData properties
        this.capacityAfterExtending = DEFAULT_CAPACITY;
        this.size = 0;
        this.elementData = new Object[this.capacityAfterExtending];
    }

    //TODO b) create the int size() method that returns the size of the data structure
    public int size() {
        return this.size;
    }

    //TODO c) create the boolean add(E e) method that adds at the end of the data structure an element
    //TODO pay attention to the LOAD_FACTOR of the data structure
    public boolean add(E e) {
        this.elementData[this.size] = e;
        this.size++;
        if(this.size * this.capacityAfterExtending / 100 > this.LOAD_FACTOR) {
            expandCapacity();
        }
        return true;
    }

    //TODO d) create the boolean isEmpty() method that checks if the data structure have elements
    public boolean isEmpty() {
        if (this.size == 0)
            return true;
        else
            return false;
    }

    //TODO e) create the boolean contains(Object o_O) method that checks if the data structure contains the object o_O
    public boolean contains(Object o) {
        for (Object obj: elementData) {
            if(obj == o) {
                return true;
            }
        }
        return false;
    }

    //TODO f) create the int indexOf(Object o_O) method that returns the position in the data structure of the object o_O
    //TODO if exists, otherwise return -1
    public int indexOf(Object o) {
        for (int i = 0; i < this.size; i++) {
            if(elementData[i] == o) {
                return i;
            }
        }
        return -1;
    }

    //TODO g) create the int lastIndexOf(Object o_O) method that returns the last position in the data structure of the object o_O
    //TODO if exists, otherwise return -1
    public int lastIndexOf(Object o) {
        int lastIndex = -1;
        for (int i = 0; i < this.size; i++) {
            if(elementData[i] == o) {
                lastIndex = i;
            }
        }
        return lastIndex;
    }

    //TODO h) create the E get(int index) method that returns the object from the given index
    //TODO pay attention to the size property
    public E get(int index) throws MyException {

        if (index > this.size && index < 0) {
            System.out.println("<GET>: Trebuie sa arunc o eroare");
            throw new MyException("ArrayIndexOutOfBoundsException get");
        }

        for (int i = 0; i < this.size; i++) {
            if(i == index) {
                return (E)elementData[i];
            }
        }
        return null;
    }

    //TODO i) create the E set(int index, E element) method that updates the value of the element from the given index
    //TODO pay attention to the size property
    public E set(int index, E element) throws MyException {

        if(index > this.size && index < 0) {
            System.out.println("<SET>: Trebuie sa arunc o eroare");
            throw new MyException("ArrayIndexOutOfBoundsException set");
        }

        E prevElem = (E) this.elementData[index];
        this.elementData[index] = element;

        return prevElem;
    }


    //TODO j) create the E remove(int index) method that removes the element from the given index
    public E remove(int index) throws MyException {
        if (index < 0 && index > this.size) {
            System.out.println("<REMOVE>: Trebuie sa arunc o eroare");
            throw new MyException("ArrayIndexOutOfBoundsException remove");
        }

        int i;
        E elem = (E) this.elementData[index];

        for (i = index; i < this.size; i++) {
            this.elementData[i] = this.elementData[i + 1];
        }
        this.elementData[i - 1] = null;
        this.size--;

        return elem;
    }

    //TODO k) extend the current default capacity, if the number of elements in the data structure is > 75% of it
    //TODO you should name it: void extendCapacity(int capacity) - HINT use capacity, DEFAULT_CAPACITY, LOAD_FACTOR and INCREASE_SIZE_FACTOR
    private void expandCapacity() {
        this.capacityAfterExtending *= INCREASE_SIZE_FACTOR;
        Object[] elementDataCopy = new Object[this.capacityAfterExtending];

        for (int i = 0; i < this.size; i++) {
            elementDataCopy[i] = this.elementData[i];
        }
        this.elementData = new Object[this.capacityAfterExtending];
        this.elementData = elementDataCopy;
    }

    //TODO l) implement the iterator() method in order to use the foreach statement over your data structure - HINT Iterable interface
    //TODO and implement a custom iterator for your custom data structure - methods boolean hasNext(), Object next() and void remove()
    public Iterator<E> iterator() {
        Iterator it = new Iterator() {
            int index = 0;

            public void remove() {
                try {
                    MyImplementedList.this.remove(index);
                    index--;
                } catch (MyException e) {
                    e.printStackTrace();
                }
            }

            public boolean hasNext() {
                if (index < MyImplementedList.this.size)
                    return true;
                return false;
            }

            public Object next() {
                if (hasNext()) {
                    Object elem =  MyImplementedList.this.elementData[index];
                    index++;

                    return elem;
                }
                return null;
            }
        };
        return it;
    }

    //TODO m) implement a method, that uses a Comparator, for your data structure to sort the elements
    //TODO you should name it: void sort(Comparator<? super E> c)
    //TODO create a custom comparator that compares objects by their "what you want" :D - HINT Comparator interface
   /* public boolean comparator() {
        Comparator<E> comp = new Comparator<E>() {
            public int compare(E o1, E o2) {
                if(o1 > o2)
                    return 1;
                return 0;
            }
        }
    }*/
}
