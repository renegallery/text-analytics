package com.tutorialspoint.lucene;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymMap.Parser;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
//import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
//import org.apache.lucene.search.PhraseQuery.Builder;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Queryer {
	private static IndexSearcher is;

	public Queryer(String indexDir) throws IOException{
	      //this directory will contain the indexes
		Path indexPath = Paths.get(indexDir);
    	IndexReader rdr = DirectoryReader.open(FSDirectory.open(indexPath));
    	is = new IndexSearcher(rdr);
	}
	
	public void close() throws CorruptIndexException, IOException{
		      ((Closeable) is).close();
	}
	
    public static void booleanQuery() throws IOException, ParseException {
    	org.apache.lucene.search.BooleanQuery.Builder builder = new BooleanQuery.Builder();
    	builder.add(new TermQuery(new Term("city_text", "greek")), BooleanClause.Occur.MUST);
    	builder.add(new TermQuery(new Term("city_text", "roman")), BooleanClause.Occur.MUST);
    	builder.add(new TermQuery(new Term("city_text", "persian")), BooleanClause.Occur.MUST_NOT);
    	Query query = builder.build();

    	ScoreDoc[] hits = is.search(query, 100).scoreDocs;
    	for(ScoreDoc scoreDoc : hits) {
    		Document doc = is.doc(scoreDoc.doc);
    		FileWriter writer = new FileWriter("/Users/Rene/Documents/msia/16 Fall/text/hw 3/q4/booleanQuery.txt", true);
    		writer.write(doc.get("city_name")+'\n');
    		writer.close();
    		}
    }
    
    public static void fuzzyQuery() throws IOException {
    	Query query = new FuzzyQuery(new Term("city_text", "shakespeare"));
    	ScoreDoc[] hits = is.search(query, 100).scoreDocs;
    	for(ScoreDoc scoreDoc : hits) {
    		Document doc = is.doc(scoreDoc.doc);
    		FileWriter writer = new FileWriter("/Users/Rene/Documents/msia/16 Fall/text/hw 3/q4/fuzzyQuery.txt", true);
    		writer.write(doc.get("city_name")+'\n');
    		writer.close();
    		}
    }
    
    public static void phraseQuery(String phrase) throws IOException, QueryNodeException {
    	PhraseQuery.Builder builder = new PhraseQuery.Builder();
    	builder.setSlop(10);
    	for(String t : phrase.split(" ")){
    		builder.add(new Term("city_text",t));
    	}
    	PhraseQuery query = builder.build();
    	Analyzer analyzer = new EnglishAnalyzer();
    	StandardQueryParser parser = new StandardQueryParser(analyzer);
    	Query parsedQuery = parser.parse(phrase, "city_text");
    	//
    	ScoreDoc[] hits = is.search(parsedQuery, 100).scoreDocs;
    	for(ScoreDoc scoreDoc : hits) {
    		Document doc = is.doc(scoreDoc.doc);
    		FileWriter writer = new FileWriter("/Users/Rene/Documents/msia/16 Fall/text/hw 3/q4/phraseQuery.txt", true);
    		writer.write(doc.get("city_name")+'\n');
    		writer.close();
    		//System.out.println(doc.get("city_name"));
    		}
    }
    
    public static void myQuery(String string) throws IOException, ParseException {
    	org.apache.lucene.search.BooleanQuery.Builder builder = new BooleanQuery.Builder();
    	builder.add(new TermQuery(new Term("city_text", string)), BooleanClause.Occur.MUST);
    	    	Query query = builder.build();

    	ScoreDoc[] hits = is.search(query, 100).scoreDocs;
    	for(ScoreDoc scoreDoc : hits) {
    		Document doc = is.doc(scoreDoc.doc);
    		FileWriter writer = new FileWriter("/Users/Rene/Documents/msia/16 Fall/text/hw 3/q4/myQuery.txt", true);
    		writer.write(doc.get("city_name")+'\n');
    		writer.close();
    		}
    }
    
    
    
}
