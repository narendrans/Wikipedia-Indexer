/* 
 * Team Name: Infinite Loop
 * Project: UB_IR
 * File name: PageHandler.java
 */
package edu.buffalo.cse.ir;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
// testing
// testing
// added by tsasaa
public class PageHandler extends DefaultHandler {
	boolean currentElement = false;
	private ArrayList<Page> listOfPages;
	private Page page;
	private String inputXmlFileName;
	private String tempString = null;
	private final Stack<String> tagsStack = new Stack<String>();

	public PageHandler(String inputXmlFileName) {
		this.inputXmlFileName = inputXmlFileName;
		listOfPages = new ArrayList<Page>();
		parseDocument();
		printDatas();
	}

	private void parseDocument() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(inputXmlFileName, this);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printDatas() {
		for (Page pages : listOfPages) {
			System.out.println("------START OF PAGE-----");
			System.out.println("Page ID: " + pages.getId());
			System.out.println("Page Title: " + pages.getTitle());
			System.out.println("Page Time Stamp: " + pages.getTimeStamp());
			System.out.println("Page Author: " + pages.getUserName());
			System.out.println("Page Comment: " + pages.getText());
			System.out.println("------END OF PAGE-----");
		}
		//System.out.println(listOfPages.get(0).getTitle());
	}

	public void startElement(String s, String s1, String elementName,
			Attributes attributes) throws SAXException {
		currentElement = true;
		pushTag(elementName);
		if (elementName.equalsIgnoreCase("page")) {
			page = new Page();
			page.setId(attributes.getValue("id"));
			page.setTitle(attributes.getValue("title"));
		}
		if (elementName.equalsIgnoreCase("contributor")) {
			page.setUserName(attributes.getValue("username"));
		}
	}

	@Override
	public void endElement(String s, String s1, String element) {
		currentElement = false;
		String tString = tempString;
		tempString = "";

		String tag = peekTag();
		if (!element.equals(tag)) {
			throw new InternalError();
		}
		popTag();
		String parentTag = peekTag();

		if ("page".equalsIgnoreCase(parentTag)) {
			if (element.equalsIgnoreCase("id")) {
				page.setId(tString);
			}
		}
		if (element.equals("page")) {
			listOfPages.add(page);
		}
		if (element.equalsIgnoreCase("title")) {
			page.setTitle(tString);
		}
		if (element.equalsIgnoreCase("comment")) {
			page.setText(tString);
		}
		if (element.equalsIgnoreCase("username")) {
			page.setUserName(tString);
		}
		if (element.equalsIgnoreCase("timestamp")) {
			page.setTimeStamp(tString);
		}
		currentElement = false;
	}

	@Override
	public void characters(char[] c, int i, int j) throws SAXException {
		if (currentElement) {
			tempString = new String(c, i, j);
			if (tempString == null)
				tempString = "";

		}
		if(tempString.length()>1000)
		tempString += new String(c, i, j);
		currentElement = false;
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		new PageHandler("/Users/naren/Documents/ir/WikiDump_1600.xml"); 
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		//Collaborative_Editing.xml ~50 ms
		//WikiDump_1600.xml ~800
	}

	public void startDocument() {
		pushTag("");
	}

	private void pushTag(String tag) {
		tagsStack.push(tag);
	}

	private String popTag() {
		return tagsStack.pop();
	}

	private String peekTag() {
		return tagsStack.peek();
	}

}
