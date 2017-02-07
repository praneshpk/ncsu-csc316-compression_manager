

import static org.junit.Assert.*;

import java.util.Iterator;

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
		} catch (UnsupportedOperationException e){}
		for(int i = 0; i < 6; i++ )
			it.next();
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
