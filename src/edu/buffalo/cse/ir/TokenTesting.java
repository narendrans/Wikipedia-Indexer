package edu.buffalo.cse.ir;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.EnglishStemmer;

public class TokenTesting {
public TokenTesting() {
	// TODO Auto-generated constructor stub
}

public static void main(String[] args) {
	EnglishStemmer stemmer = new EnglishStemmer();
	TokenStream stream = new TokenStream("stemming is a techinque of removing stranded endings");
	
	try {
		stemmer.apply(stream);
	} catch (TokenizerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}
