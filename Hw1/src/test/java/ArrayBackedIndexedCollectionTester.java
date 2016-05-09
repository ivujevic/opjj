import static org.junit.Assert.*;
import junit.framework.Assert;
import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;
import org.junit.*;
public class ArrayBackedIndexedCollectionTester {

	
	@Test
	public void testIsEmptyPrazan() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		//Assert.assertTrue(kolekcija.isEmpty());
		assertTrue(kolekcija.isEmpty());
	}
	@Test
	public void testIsEmptyPun() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		kolekcija.add(new Integer(2));
		//Assert.assertFalse(kolekcija.isEmpty());
		assertFalse(kolekcija.isEmpty());
	}
	
	
	@Test
	public void testSize() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		assertEquals(0, kolekcija.size());
	}

	
	@Test
	public void testAdd() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i = 0;i < 1000;i++) {
			kolekcija.add(new Integer(i));
		}
		assertEquals(1000, kolekcija.size());
	}
	
	public void testAddVise() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i = 0;i < 18;i++) {
			kolekcija.add(new Integer(i));
		}
		assertEquals(18, kolekcija.get(18));
	}
	
	@Test
	public void testAddNull() {
		Throwable uhvati= null;
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		
		try {
			kolekcija.add(null);
		} catch(Throwable e) {
			uhvati=e;
		}
		assertNotNull(uhvati);
	}
	
	@Test
	public void testReallocate() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 20; i++) {
			kolekcija.add(new Integer(i));
		}
		assertEquals(20, kolekcija.size());
	}

	@Test
	public void testGet() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		assertEquals(50, kolekcija.get(50));
	}
	
	@Test
	public void testGetManjiOdNula() {
		Throwable uhvati= null;
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		try {
			kolekcija.get(-5);
		} catch(Throwable e) {
			uhvati=e;
		}
		assertNotNull(uhvati);
	}
	
	@Test
	public void testGetVeciOdSize_1() {
		Throwable uhvati= null;
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		try {
			kolekcija.get(kolekcija.size());
		} catch(Throwable e) {
			uhvati=e;
		}
		assertNotNull(uhvati);
	}
	
	@Test
	public void testRemove() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		kolekcija.remove(50);
		assertEquals(51, kolekcija.get(50));
	}

	@Test
	public void testInsert() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		kolekcija.insert(100, 50);
		assertEquals(100, kolekcija.get(50));
	}

	@Test
	public void testInsertNull() {
		Throwable uhvati= null;
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		try {
			kolekcija.insert(null,4);
		} catch(Throwable e) {
			uhvati=e;
		}
		assertNotNull(uhvati);
	}
	@Test
	public void testInsertManjeOdNula() {
		Throwable uhvati= null;
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		try {
			kolekcija.insert(5, -2);
		} catch(Throwable e) {
			uhvati=e;
		}
		assertNotNull(uhvati);
	}
	
	@Test
	public void testInsertVeceOdSize() {
		Throwable uhvati= null;
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		try {
			kolekcija.insert(5, kolekcija.size()+1);
		} catch(Throwable e) {
			uhvati=e;
		}
		assertNotNull(uhvati);
	}
	
	@Test
	public void testIndexOf() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		assertEquals(10,10);
	}
	
	public void testIndexOfNijeUnutra() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		assertEquals(-1,452);
	}


	
	@Test
	public void testContains() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		assertTrue(kolekcija.contains(2));
	}
	
	@Test
	public void testContainsNijeUnutra() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		assertFalse(kolekcija.contains(452));
	}

	@Test
	public void testClear() {
		ArrayBackedIndexedCollection kolekcija= new ArrayBackedIndexedCollection();
		for(int i=0; i < 100; i++) {
			kolekcija.add(new Integer(i));
		}
		kolekcija.clear();
		assertEquals(0, kolekcija.size());
	}
}
