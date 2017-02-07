package edu.ncsu.csc316.compression_manager.manager;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

/**
 * The CompressionManager class has all of the required functions
 * and variables to compress / decompress the given file
 * @author Pranesh Kamalakanthan
 *
 */
public class CompressionManager {
	
	/** List where all words are stored */
	private DoubleList<String> wordlist;
	
	/** Default constructor */
	public CompressionManager() {
		wordlist = new DoubleList<>();
	}
	
	/**
	* The process method accepts a file name as a parameter. The
	* method then compresses or decompresses the file, based
	* on whether the file begins with "0 " (if so, decompress the file;
	* if not, compress the file).
	* @param filename the name of the file to process
	* @return    "DECOMPRESS" if the file was decompressed
	*            "COMPRESS" if the file was compressed
	*            "EMPTY" if the file is empty (has no contents)
	*/
	public String process(String filename) {
		
		// Modifying filename to allow for renaming within method
	    String res = new String();
	    
	    // Reads in file and checks if file is to be compressed or decompressed
		try( Scanner in = new Scanner( new FileInputStream( filename ), "UTF8") )
	    {
	    	if( in.hasNextLine() ){
	    		String line = in.nextLine();
	    		in.close();
	    		
	    		if( line.length() > 1 && line.substring(0, 2).equals("0 ") ) {
	    			
	    			decompress( filename );
	    			res = "DECOMPRESS";
	    		}
	    		else {
	    			compress( filename );
	    			res = "COMPRESS";
	    		}
	    	}
	    	else {
	    		res = "EMPTY";
	    	}
	    } catch (IOException e) {
			System.out.println("Error: File " + filename + " not found!");
		} catch (RuntimeException e ) {
			System.out.println("Error: Compressed file is corrupt!");
		}
	    return res;
	}
	
	/**
	 * The lookUp method looks for the given word by iterating 
	 * through wordlist. The method then either returns the word if it
	 * is not found and adds it to the list, or it returns the index
	 * of the word and moves it to the front of wordlist.
	 * 
	 * @param word the word that is to be found
	 * @return the word if it is not found
	 * 		   the index if it is found
	 */
	private String lookUp( String word ) {
		// Create new iterator object / variable
		Iterator<String> it = wordlist.iterator();
		int index = 1;
		
		// Looks for word in wordlist and moves it to the front
		while( it.hasNext() ) {
			if(it.next().equals(word)) {
				wordlist.moveToFront(it);
				return index + "";
			}
			index++;
		}
		wordlist.add( word );
		
		return word;
	}
	
	/**
	 * The lookUp method looks for a word in wordlist by the given 
	 * index. The method then returns the word and moves it to 
	 * the front of wordlist.
	 * 
	 * @param index the index of the word that is to be found
	 * @return the word found in wordlist
	 */
	private String lookUp( int index ) {
		// Create new iterator object
		Iterator<String> it = wordlist.iterator();
		
		// Looks for word by index and moves it to the front
		for( int i = 1; i < index; i++ )
			it.next();
		String word = it.next();
		wordlist.moveToFront(it);
		
		return word;
	}
	
	/**
	 * Compresses the given file to output/compressed/
	 * @param filename the given filename w/o file ext
	 * @throws IOException if file is not found
	 */
	private void compress( String filename ) throws IOException {
		// Creates new input file stream
		FileInputStream input = new FileInputStream( filename );
		try( Scanner in = new Scanner( input , "UTF8") )
		{
			// Creates output directory if not already made
			String path = "output/compressed/";
			File f = new File(path);
			f.mkdirs();
			
		    File f2 = new File( filename );
		    String file = f2.getName();
		    file = file.substring(0, file.length() - ".txt".length() );
			
			// Creates new output file stream
			try( FileOutputStream output = new FileOutputStream( path + file + "-compressed.txt" );
					Writer w = new OutputStreamWriter( output, "UTF8" ))
			{
				// Initializes compressed file
				String line, word;
				w.write( "0 " );
				
				// Iterates through the input file
				while( in.hasNextLine() ) {
					line = in.nextLine();
					word = "";
					
					// Iterates through a line, writing to the file / storing in word buffer
					for( int i = 0; i < line.length(); i++ ) {
						if( Character.isLetter(line.charAt(i)) )
							word += line.charAt(i);
						else if( !word.equals("") ) {
							w.write( lookUp( word ) + line.charAt(i) );
							word = "";
						}
						else
							w.write( line.charAt(i) );
					} // for
					
					// Writes what's left in the word buffer
					if( !word.equals("") )
						w.write( lookUp(word) );
					w.write( "\n" );
				} // while
				
				// Prints out information about the compression results
				w.flush();
				w.write("0 Uncompressed: " + input.getChannel().size() + 
						" bytes;  Compressed: " + ( output.getChannel().size() - 3) + " bytes");
			}
		}
	}
	
	/**
	 * Decompresses the given file to output/decompressed/
	 * @param filename the given filename w/o file ext.
	 * @throws IOException if file is not found
	 */
	private void decompress( String filename ) throws IOException {
		try( Scanner in = new Scanner( new FileInputStream( filename ), "UTF8") )
		{
			// Creates output directory if not already made
			String path = "output/decompressed/";
			File f = new File(path);
			f.mkdirs();
			
			// Rename file and create new output file stream
			File f2 = new File( filename );
			String file = f2.getName();
			file = file.substring(0, file.length() - ".txt".length() );
			try( FileOutputStream fos = new FileOutputStream( path + file + ".txt", false );
					Writer w = new OutputStreamWriter( fos, "UTF8" ))
			{
				// Iterates through the input file
				while( in.hasNextLine() ) {
					String line = in.nextLine();
					String word = "";
					int start = 0;
					
					// Determines which lines/characters to skip
					if( line.isEmpty() ) {
						w.write("\n");
						continue;
					}
					else if( line.substring(0, 2).equals("0 ") ){
						if( in.hasNextLine() )
							start = 2;
						else
							break;
					}
					else
						w.write("\n");
					
					// Iterates through a line, writing to the file / storing in word buffer
					for( int i = start; i < line.length(); i++ ) {
						// Checks for tokens
						int index;
						if( Character.isLetter(line.charAt(i)) )
							word += line.charAt(i);
						else if( Character.isDigit(line.charAt(i)) ) {
							// Adds support for multiple digit numbers
							String temp = line.charAt(i) + "";
							int j = i + 1;
							for( ; j < line.length(); j++ ) {
								if( Character.isDigit(line.charAt(j)) )
									temp += line.charAt(j);
								else {
									j--;
									break;
								}
							}
							i = j;
							index = Integer.parseInt(temp);
							
							// Checks if index is in wordlist and write corresponding word
							if( index > wordlist.size() )
								throw new RuntimeException();
							w.write( lookUp(index));
						}
						else if( !word.equals("") ) {
							// Writes word left in buffer
							lookUp( word );
							w.write( word + line.charAt(i) );
							word = "";
						}
						else
							w.write( line.charAt(i) );
					} // for
					
					// Writes word left in buffer
					if( !word.equals("") ) {
						lookUp( word );
						w.write( word );
						word = "";
					}
				} // while
			} // try-with-resources
		} // try-with-resources
	}
	
}
