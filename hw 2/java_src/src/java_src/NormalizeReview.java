package java_src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Scanner;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class NormalizeReview {
	public void norm(File file, String writepath) throws IOException{
    	try{
    		// get file
    		//String bio = filepath;//"/Users/Rene/Documents/msia/16 Fall/text/hw 2/classbio_clean.txt";
    		File myInputeFile = file;
    		// scan in file
    		@SuppressWarnings("resource")
    		Scanner in = new Scanner(myInputeFile);
    		PrintWriter writer = new PrintWriter(writepath, "UTF-8");
    		
    		while (in.hasNextLine()){
    			String line = in.nextLine();
    			if (line.equals("")) {
    				continue;
    			}
    			
    			writer.println("\n");
    			// writer.println(line);
    			@SuppressWarnings("deprecation")
    			
    			// lucene standard tokenizer and english analyzer
    		    
    			StandardTokenizer tokenizer = new StandardTokenizer();		
    			// a. English analyzer
    			EnglishAnalyzer englishanalyzer = new EnglishAnalyzer();
    			tokenizer.setReader(new StringReader(line));
    			TokenStream tokenstream = new StandardFilter(tokenizer);
    			tokenstream = englishanalyzer.tokenStream("english", new StringReader(line));
    			CharTermAttribute charTermAttrib1 = tokenstream.getAttribute(CharTermAttribute.class);
    			String lucenestring0 = "";
    			tokenstream.reset();
    			while (tokenstream.incrementToken()) {
    			  lucenestring0 += charTermAttrib1.toString() + " " ;
    			}
    			tokenstream.end();
    			tokenstream.close();
    			writer.println(lucenestring0);

    		}
    	    writer.close();
    		} 
    		catch (FileNotFoundException e){
    			System.out.println("Error" + e);
    		}
		
	}

}
