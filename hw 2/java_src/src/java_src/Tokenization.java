package java_src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class Tokenization {
    public static void token() throws IOException{
    	try{
    		// get file
    		String wsj63 = "/Users/Rene/Documents/msia/16 Fall/text/hw 2/wsj_0063";
    		File myInputeFile = new File(wsj63);
    		// scan in file
    		@SuppressWarnings("resource")
    		Scanner in = new Scanner(myInputeFile);
    		PrintWriter writer = new PrintWriter("/Users/Rene/Documents/msia/16 Fall/text/hw 2/q1.txt", "UTF-8");
    		
    		while (in.hasNextLine()){
    			String line = in.nextLine();
    			if (line.equals("")) {
    				continue;
    			}
    			
    			// original line
    			writer.println("----------------------------------");
    			writer.println("original: " + line);
    			@SuppressWarnings("deprecation")
    			
    			// 1. stanford core nlp    
    			PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new StringReader(line), new CoreLabelTokenFactory(), "");
    		    String nlpstring = "";
    			while (ptbt.hasNext()) {
    		        CoreLabel label = ptbt.next();
    		        nlpstring += label + "/";
    		        }
    		    writer.println("CoreNLP: "+nlpstring);
    			
    			// 2. lucene standard tokenizer
    			StandardTokenizer tokenizer = new StandardTokenizer();
    			tokenizer.setReader(new StringReader(line));
    			CharTermAttribute charTermAttrib = tokenizer.getAttribute(CharTermAttribute.class);	
    			String lucenestring = "";
    			tokenizer.reset();
    			while (tokenizer.incrementToken()) {
    			  lucenestring += charTermAttrib.toString() + "/" ;
    			}
    			tokenizer.end();
    			tokenizer.close();
    			writer.println("Lucene: " + lucenestring);
    		}
    	    writer.close();
    		} 
    		catch (FileNotFoundException e){
    			System.out.println("Error" + e);
    		}
    		
    }
}
