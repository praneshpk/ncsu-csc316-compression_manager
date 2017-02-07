package edu.ncsu.csc316.compression_manager.test;


import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import edu.ncsu.csc316.compression_manager.manager.DoubleList;
/**
 * The DoubleListTest class maintains all the functions
 * for testing the DoubleList data structure
 * @author Pranesh Kamalakanthan
 *
 */
public class DoubleListTest {
	
	/**
	 * Tests if the DoubleList object was created
	 */
	@Test
	public void testDoubleList() {
		DoubleList<String> wordlist = new DoubleList<>();
		assertFalse( wordlist.equals(null) );
	}

	/**
	 * Tests if the add function works
	 */
	@Test
	public void testAdd() {
		DoubleList<String> wordlist = new DoubleList<>();
		for(int i = 0; i <= 5; i++ )
			wordlist.add(i + "");
		Iterator<String> it = wordlist.iterator();
		for(int i = 5; i >= 0; i-- )
			assertEquals( it.next(), i + "");
	}
	
	/**
	 * Tests if the DoubleList Iterator works and tests all of its functions
	 */
	@Test
	public void testIterator() {
		DoubleList<String> wordlist = new DoubleList<>();
		for(int i = 0; i < 5; i++ )
			wordlist.add(i + "");
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

	/**
	 * Tests if the size function gives the correct data
	 */
	@Test
	public void testSize() {
		DoubleList<String> wordlist = new DoubleList<>();
		for(int i = 0; i < 5; i++ )
			wordlist.add(i + "");
		assertTrue( wordlist.size() == 5 );
	}

	/**
	 * Tests if the MoveToFront operation works as expected
	 */
	@Test
	public void testMoveToFront() {
		DoubleList<String> wordlist = new DoubleList<>();
		for(int i = 0; i <= 5; i++ )
			wordlist.add(i + "");
		
		Iterator<String> it = wordlist.iterator();
		for( int i = 0; i < wordlist.size() / 2; i++ )
			it.next();
		String temp = it.next();
		
		wordlist.moveToFront(it);
		it = wordlist.iterator();
		assertEquals( temp, it.next() );
	}

}
