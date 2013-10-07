package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.FileInputStream;
import java.util.Collection;
import java.util.Properties;

public class IndexReaderTesting {
	public IndexReaderTesting() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Properties pr = new Properties();
		try {
			pr.load(new FileInputStream(
					"/Users/naren/git/UB_IR/files/properties.config"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		IndexReader r = new IndexReader(pr, INDEXFIELD.TERM);

		System.out.println(r.query("english","british"));
	}
}
