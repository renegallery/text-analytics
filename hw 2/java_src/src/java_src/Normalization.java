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

public class Normalization {
	public static void norm() throws IOException{
    	try{
    		// get file
    		String wsj63 = "/Users/Rene/Documents/msia/16 Fall/text/hw 2/wsj_0063";
    		File myInputeFile = new File(wsj63);
    		// scan in file
    		@SuppressWarnings("resource")
    		Scanner in = new Scanner(myInputeFile);
    		PrintWriter writer = new PrintWriter("/Users/Rene/Documents/msia/16 Fall/text/hw 2/q2.txt", "UTF-8");
    		
    		while (in.hasNextLine()){
    			String line = in.nextLine();
    			if (line.equals("")) {
    				continue;
    			}
    			
    			writer.println("----------------------------------");
    			writer.println("original: " + line);
    			@SuppressWarnings("deprecation")
    			
    			// 1. stanford core nlp    
    			Properties props = new Properties(); 
    	        props.put("annotators", "tokenize, ssplit, pos, lemma"); 
    	        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    	        String text = line; 
    	        String nlpstring = "";
    	        Annotation document = pipeline.process(text);  

    	        for(CoreMap sentence: document.get(SentencesAnnotation.class))
    	        {    
    	            for(CoreLabel token: sentence.get(TokensAnnotation.class))
    	            {       
    	                String word = token.get(TextAnnotation.class);      
    	                nlpstring += token.get(LemmaAnnotation.class) + "/" ; 
    	            }
    	        }
    	        writer.println("CoreNLP-Lemmatization:   " + nlpstring);
    	 
    			// 2. lucene 
    		    
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
    			  lucenestring0 += charTermAttrib1.toString() + "/" ;
    			}
    			tokenstream.end();
    			tokenstream.close();
    			writer.println("Lucene-EnglishAnalyzer:  " + lucenestring0);
    			// b. porter stem filter:
    			StandardTokenizer tokenizer1 = new StandardTokenizer();
    			tokenizer1.setReader(new StringReader(line));
    			PorterStemFilter porterstemfilter = new PorterStemFilter(tokenizer1);
    			CharTermAttribute charTermAttrib2 = porterstemfilter.getAttribute(CharTermAttribute.class);
    			String lucenestring = "";
    			porterstemfilter.reset();
    			while (porterstemfilter.incrementToken()) {
    			  lucenestring += charTermAttrib2.toString() + "/" ;
    			}
    			porterstemfilter.end();
    			porterstemfilter.close();
    			writer.println("Lucene-PorterStemFilter: " + lucenestring);
    			// c. K stem filter:
    			tokenizer = new StandardTokenizer();
    			tokenizer.setReader(new StringReader(line));
    			KStemFilter kstemfilter = new KStemFilter(tokenizer);
    			CharTermAttribute charTermAttrib3 = kstemfilter.getAttribute(CharTermAttribute.class);
    			String lucenestring1 = "";
    			kstemfilter.reset();
    			while (kstemfilter.incrementToken()) {
    			  lucenestring1 += charTermAttrib3.toString() + "/" ;
    			}
    			kstemfilter.end();
    			kstemfilter.close();
    			writer.println("Lucene-KStemFilter:      " + lucenestring1);

    		}
    	    writer.close();
    		} 
    		catch (FileNotFoundException e){
    			System.out.println("Error" + e);
    		}
	}
	
}
