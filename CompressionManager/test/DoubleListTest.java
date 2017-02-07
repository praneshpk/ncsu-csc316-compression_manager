

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import edu.ncsu.csc316.compression_manager.manager.DoubleList;

public class DoubleListTest {

	private DoubleList<String> wordlist;
	
	@Test
	public void testDoubleList() {
		wordlist = new DoubleList<>();
	}

	@Test
	public void testAdd() {
		testDoubleList();
		for(int i = 0; i < 5; i++ )
			wordlist.add(i+"");
	}
	
	@Test
	public void testIterator() {
		testAdd();
		Iterator<String> it = wordlist.iterator();
		try {
			it.remove();
			fail("UnsupportedOperationException was not thrown");
		} catch(UnsupportedOperationException e){
			// Supposed to throw exception
		}
		try {
			for(int i = 0; i < 8; i++ )
				it.next();
			fail("NoSuchElementException was not thrown");
		} catch( NoSuchElementException e ){
			// Supposed to throw exception
		}
	}

	@Test
	public void testSize() {
		testAdd();
		wordlist.size();
		
	}

	@Test
	public void testMoveToFront() {
		testAdd();
		Iterator<String> it = wordlist.iterator();
		for( int i = 0; i < wordlist.size() / 2; i++ )
			it.next();
		wordlist.moveToFront(it);
	}

}
