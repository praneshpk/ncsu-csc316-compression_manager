

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import edu.ncsu.csc316.compression_manager.manager.CompressionManager;

public class CompressionManagerTest {

	@Test
	public void testCompressionManager() {
		CompressionManager c = new CompressionManager();
		assertEquals(c.process("DeclarationOfIndependence.txt"), "COMPRESS");
		assertEquals(c.process("DeclarationOfIndependence-compressed.txt"), "DECOMPRESS");
		assertEquals(c.process("empty.txt"), "EMPTY");
	}
	
	@Test
	public void testNonexistentFile() {
		CompressionManager c = new CompressionManager();
		c.process("DeclarationOfIndependence");
	}

	@Test
	public void testCorruptFile() {
		CompressionManager c = new CompressionManager();
		c.process("invalid-compressed.txt");
	}

}
