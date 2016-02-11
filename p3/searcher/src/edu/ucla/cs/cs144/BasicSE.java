package edu.ucla.cs.cs144;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

//

/**
 * Represents a basic search engine for use in basicSearch() at 
 * edu.ucla.cs.cs144.AuctionSearch.
**/

public class BasicSE {

	private IndexSearcher ids = null;
	private QueryParser qp = null;
	private String indexDir = "/var/lib/lucene/p3_index";

	//cstr
	public BasicSE() throws IOException {
		ids = new IndexSearcher(DirectoryReader.open(FSDirectory.open(new File(indexDir))));
		qp = new QueryParser("searchable", new StandardAnalyzer());
	}

	//perform the search, returning max number of items
	public TopDocs search(String query, int max) throws IOException, ParseException {
		Query q = qp.parse(query); 	//parse input query
		return ids.search(q, max);
	}

	public Document getDoc(int docID) throws IOException {
		return ids.doc(docID);
	}

}