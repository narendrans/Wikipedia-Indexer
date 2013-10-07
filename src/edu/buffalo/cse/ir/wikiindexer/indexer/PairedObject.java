package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.util.HashSet;
import java.util.Set;

class PairedObject {
	private Set<String> v1 = new HashSet<String>();
	private int add;

	public Set<String> getKeys() {
		return v1;
	}

	public void adV1(String key) {
		v1.add(key);
	}

	public int getAdd() {
		return add;
	}

	public void setAdd(int num) {
		add += num;
	}
}
