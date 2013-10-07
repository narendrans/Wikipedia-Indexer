package edu.buffalo.cse.ir.wikiindexer.indexer;

public class QueryObject {
	private String key;
	private int value;
	private int occurances;
	
	public QueryObject(String k,int v, int c) {
		this.key = k;
		this.value = v;
		this.occurances = c;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getOccurances() {
		return occurances;
	}
	public void setOccurances(int occurances) {
		this.occurances = occurances;
	}
}
