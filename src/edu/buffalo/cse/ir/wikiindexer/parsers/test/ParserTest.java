/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.parsers.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.buffalo.cse.ir.wikiindexer.parsers.Parser;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;

/**
 * @author nikhillo
 *
 */
public class ParserTest {
	private static Properties idxProps;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass(Properties props) throws Exception {
		idxProps = props;
	}

	/**
	 * Test method for {@link edu.buffalo.cse.ir.wikiindexer.parsers.Parser#parse(java.lang.String, java.util.Collection)}.
	 */
	@Test
	public final void testParse() {
		Parser testClass = new Parser(idxProps);
		ArrayList<WikipediaDocument> list = new ArrayList<WikipediaDocument>();
		
		//null file
		testClass.parse(null, list);
		assertTrue(list.isEmpty());
		
		//empty filename
		testClass.parse("", list);
		assertTrue(list.isEmpty());
		
		//invalid filename
		testClass.parse("abc.xml", list);
		assertTrue(list.isEmpty());
	}

}
