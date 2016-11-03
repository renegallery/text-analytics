package java_src;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.util.CoreMap;

public class NormalizeBio {

	public void normbio() throws IOException{
    	try{
    		// get file
    		String bio = "/Users/Rene/Documents/msia/16 Fall/text/hw 2/q3/classbio_clean.txt";
    		File myInputeFile = new File(bio);
    		// scan in file
    		@SuppressWarnings("resource")
    		Scanner in = new Scanner(myInputeFile);
    		PrintWriter writer = new PrintWriter("/Users/Rene/Documents/msia/16 Fall/text/hw 2/q3/classbio_norm.txt", "UTF-8");
    		
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
