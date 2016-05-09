package hr.fer.zemris.java.custom.collections;

/**
 * 
 * @author Ivan
 *
 */
public class ArrayBackedIndexedCollection {
	private int size;
	private int capacity;
	private Object[] elements;

	/**
	 * Kreira novi <code>ArrayBackedIndexedCollection</code> kapaciteta 16
	 */
	public ArrayBackedIndexedCollection() {
		this.capacity = 16;
		this.size=0;
		this.elements = new Object[16];	
	}
	
	/**
	 * Kreira novi <code>ArrayBackedIndexedCollection</code> veliciine 
	 * <code>initialCapacity</code>
	 * @param initialCapacity Pocetna velicina
	 * @throws Greska ako je kapacitet manji od 1
	 */
	public ArrayBackedIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Kapacitet ne smije biti manji od 1");
		}
		this.capacity=initialCapacity;
		this.elements= new Object[initialCapacity];
	}
	
	/**
	 * Provjerava je li <code>ArrayBackedIndexedCollection</code> prazan
	 * @return Vraca <code>true</code> ako je prazan inaec <code>false</code>
	 */
	public boolean isEmpty() {
		if(this.size==0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Vraca broj pohranjenih podataka
	 * @return Velicinu
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Dodaje value na prvo slobodno mjesto u <code>ArrayBackedIndexedCollection</code>
	 * @param value Vrijednost koja se sprema
	 * @throws Greska ako je vrijednost <code>null</code>
	 */
	public void add(Object value) {
		if(value == null) {
			throw new IllegalArgumentException("Vrijednost ne moze biti null");
		}
		
		if(this.size == this.capacity) {
			reallocate();
		}
		elements[size]=value;
		size++;
	}
	
	/**
	 * Ako je broj pohranjenih elemenata jednak kapacitetu kapacitet se povecava 2 puta
	 */
	public void reallocate() {
		Object[] temp= new Object[2*capacity];
		for(int i=0; i < size; i++) {
			temp[i]=elements[i];
		}
		capacity *= 2;
		elements=temp;
	}
	
	/**
	 * Vraca podataka pohranjen na <code>index</code>
	 * @param index Mjesto na kojem je podatak
	 * @return Vraca trazeni podatak
	 */
	public Object get(int index) {
		if( index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Index ne odgovara");
		}
		return elements[index];
	}
	
	/**
	 * Brise podatak koji je pohranjen na <code>index</code>
	 * @param index Mjesto s kojeg se brise podatak
	 */
	public void remove(int index) {
		for(int i=index; i<size-1;i++) {
			elements[i] = elements[i+1];
		}
		elements[size-1]=null;
		size--;
	}
	
	/**
	 * Umece novi podatak na poziciju <code>position</code>
	 * @param value Podatak koji se umece
	 * @param position Pozicija na koju se umece
	 */
	public void insert (Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Pozicija ne odgovara");
		}
		if(value == null) {
			throw new IllegalArgumentException("Vrijednost ne moze biti null");
		}
		
		if(this.size == this.capacity) {
			reallocate();
		}
		for(int i=position+1; i< size;i++) {
			elements[i]=elements[i-1];
		}
		elements[position] = value;
		size++;
	}
	
	/**
	 * Vraca mjesto prvog pojavljivanja tracenog podatka
	 * @param value Podatak koji se trazi
	 * @return Ako podatak postoji vraca poziciju, inace vraca -1
	 */
	public int indexOf(Object value) {
		for(int i=0; i < size ;i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Provjerava je li se odredjeni podatak nalazi u <code>ArrayBackedIndexedCollection</code>
	 * @param value Podatak koji se trazi
	 * @return Ako se nalazi vraca <code>true</code>, inace vraca <code>false</code>
	 */
	public boolean contains(Object value) {
		for(int i=0; i < size; i++) {
			if(elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Brise sve elemente
	 */
	public void clear() {
		for(int i=0; i < size;i++) {
			elements[i]=null;
		}
		size=0;
	}
}
