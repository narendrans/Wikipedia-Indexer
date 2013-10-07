package edu.buffalo.cse.ir.wikiindexer.indexer;

public class TitleTerm {
	private String title;
	private int lowerBound;
	private int upperBound;

	public TitleTerm(String a, int b, int c) {
		this.title = a;
		this.lowerBound = b;
		this.upperBound = c;
	}

	public String getTitle() {
		return title;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public int getUpperBound() {
		return upperBound;
	}
}
