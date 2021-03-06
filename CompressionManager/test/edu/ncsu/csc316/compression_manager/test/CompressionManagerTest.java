package edu.ncsu.csc316.compression_manager.test;


import static org.junit.Assert.*;

import java.io.*;

import org.junit.Test;

import edu.ncsu.csc316.compression_manager.manager.CompressionManager;

/**
 * The CompressionManagerTest class maintains all the functions
 * for testing the CompressionManager class
 * @author Pranesh Kamalakanthan
 *
 */
public class CompressionManagerTest {

	/**
	 * Tests the compression / decompression operations of CompressionManager
	 * testing all possible return values
	 */
	@Test
	public void testCompressionManager() {
		CompressionManager c = new CompressionManager();
		assertEquals(c.process("input/DeclarationOfIndependence.txt"), "COMPRESS");
		assertEquals(c.process("input/DeclarationOfIndependence-compressed.txt"), "DECOMPRESS");
		assertEquals(c.process("input/empty.txt"), "EMPTY");
	}
	
	/**
	 * Tests the try-catch statement for FileNotFoundException
	 */
	@Test
	public void testNonexistentFile() {
		// Allows System.out.print to go to output stream
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		CompressionManager c = new CompressionManager();
		c.process("input/random.txt");
		assertEquals("Error: File input/random.txt not found!\n", output.toString());
	}
	
	/**
	 * Tests the try-catch statement for RuntimeException, resulting
	 * from an incorrectly compressed file
	 */
	@Test
	public void testCorruptFile() {
		// Allows System.out.print to go to output stream
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		
		CompressionManager c = new CompressionManager();
		c.process("input/invalid-compressed.txt");
		assertEquals("Error: Compressed file input/invalid-compressed.txt is corrupt!\n", output.toString());
	}

}
