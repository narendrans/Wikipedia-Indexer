package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7367814686561324032L;
	private int value;
	private String keyId;
	private int occurences;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String key) {
		this.keyId = key;
	}

	public int getOccurences() {
		return occurences;
	}

	public void setOccurences(int occurences) {
		this.occurences = occurences;
	}
}
