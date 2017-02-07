

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc316.compression_manager.manager.CompressionManager;

public class CompressionManagerTest {

	@Test
	public void testCompressionManager() {
		CompressionManager c = new CompressionManager();
		c.process("DeclarationOfIndependence.txt");
		c.process("DeclarationOfIndependence-compressed.txt");
		c.process("empty.txt");
		try {
			c.process("DeclarationOfIndependence");
		} catch( Exception e) {}
		try {
			c.process("invalid.txt");
		} catch( Exception e ) {}
	}

}
