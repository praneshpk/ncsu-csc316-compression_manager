package edu.ncsu.csc316.compression_manager.manager;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class CompressionManager {
	private DoubleList<String> wordlist;
	
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
	    String res = new String();
	    filename = filename.substring( 0, filename.length() - ".txt".length()  );
		try( Scanner in = new Scanner( new FileInputStream( "input/"+filename+".txt" ), "UTF8") )
	    {
	    	if( in.hasNextLine() ){
	    		String line = in.nextLine();
	    		in.close();
	    		
	    		if( line.length() > 1 &&
	    				line.substring(0, 2).equals("0 ") ) {
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
	    } catch (FileNotFoundException e) {
			System.out.println("Error: File not found!");
			e.printStackTrace();
		}
	    return res;
	}
	
	private String lookUp( String word ) {
		Iterator<String> it = wordlist.iterator();
		int index = 1;
		while( it.hasNext() ) {
			if(it.next().equals(word)) {
				wordlist.moveToFront(it);
				return index + "";
			}
			index ++;
		}
		wordlist.add( word );
		return word;
	}
	private String lookUp( int index ) {
		Iterator<String> it = wordlist.iterator();
		for( int i = 1; i < index; i++ )
			it.next();
		String word = it.next();
		wordlist.moveToFront(it);
		return word;
	}
	
	private void compress( String filename ) throws FileNotFoundException {
		FileInputStream input = new FileInputStream( "input/"+filename+".txt" );
		try( Scanner in = new Scanner( input , "UTF8") )
		{
			String path = "output/compressed/";
			File f = new File(path);
			f.mkdirs();
			try( FileOutputStream output = new FileOutputStream( path + filename + "-compressed.txt" );
					Writer w = new OutputStreamWriter( output, "UTF8" ))
			{
				String line, word;
				w.write( "0 " );
				while( in.hasNextLine() ) {
					line = in.nextLine();
					word = "";
					
					for( int i = 0; i < line.length(); i++ ) {
						if( Character.isLetter(line.charAt(i)) )
							word += line.charAt(i);
						else if( word != "" ) {
							w.write( lookUp( word ) + line.charAt(i) );
							word = "";
						}
						else
							w.write( line.charAt(i) );
					} // for
					
					if( word != "" )
						w.write( lookUp(word) );
					w.write( "\n" );
				} // while
				w.flush();
				w.write("0 Uncompressed: " + input.getChannel().size() + 
						" bytes;  Compressed: " + ( output.getChannel().size() - 3) +" bytes");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void decompress( String filename ) throws FileNotFoundException {
		try( Scanner in = new Scanner( new FileInputStream( "input/"+filename+".txt" ), "UTF8") )
		{
			String filePath = "output/decompressed/";
			File f = new File(filePath);
			f.mkdirs();
			filename = filename.substring( 0, filename.length() - "-compressed".length() );
			try( FileOutputStream fos = new FileOutputStream( "output/decompressed/"+ filename + ".txt", false );
					Writer w = new OutputStreamWriter( fos, "UTF8" ))
			{
				while( in.hasNextLine() ) {
					String line;
					String word = "";
					int start = 0;
					
					if( ( line = in.nextLine() ).isEmpty() ) {
						w.write("\n");
						continue;
					}
					else if( line.substring(0,2).equals("0 ") ){
						if( in.hasNextLine() )
							start = 2;
						else
							break;
					}
					else
						w.write("\n");
					for( int i = start; i < line.length(); i++ ) {
						int index = 0;
						if( Character.isLetter(line.charAt(i)) )
							word += line.charAt(i);
						else if( Character.isDigit(line.charAt(i)) ) {
							String temp = line.charAt(i) +"";
							for( i = i+1; i < line.length(); i++ ) {
								if( Character.isDigit(line.charAt(i)) )
									temp += line.charAt(i);
								else {
									i--;
									break;
								}
							}
							index = Integer.parseInt(temp);
							if( index > wordlist.size() ) {
								System.out.println("Error: Compressed file is corrupt!");
								System.exit(0);
							}
							w.write( lookUp(index));
						}
						else if( word != "" ) {
							w.write( lookUp( word ) + line.charAt(i) );
							word = "";
						}
						else
							w.write( line.charAt(i) );
					}
					if( word != "" )
						w.write( lookUp( word ) );
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
