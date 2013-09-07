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

public class PageHandler extends DefaultHandler {
	boolean currentElement = false;
	private ArrayList<Page> listOfPages;
	private Page page;
	private String inputXmlFileName;
	private StringBuffer sb;
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
		// System.out.println(listOfPages.get(0).getTitle());
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
		sb.setLength(0);
	}

	@Override
	public void endElement(String s, String s1, String element) {

		String tag = peekTag();
		if (!element.equals(tag)) {
			throw new InternalError();
		}
		popTag();
		String parentTag = peekTag();

		if ("page".equalsIgnoreCase(parentTag)) {
			if (element.equalsIgnoreCase("id")) {
				page.setId(sb.toString());
			}
		}
		if (element.equals("page")) {
			listOfPages.add(page);
		}
		if (element.equalsIgnoreCase("title")) {
			page.setTitle(sb.toString());
		}
		if (element.equalsIgnoreCase("comment")) { // TODO: Replace with text
			page.setText(sb.toString());
		}
		if (element.equalsIgnoreCase("username")
				|| element.equalsIgnoreCase("ip")) {
			page.setUserName(sb.toString());
		}
		if (element.equalsIgnoreCase("timestamp")) {
			page.setTimeStamp(sb.toString());
		}
		sb.setLength(0);
	}

	@Override
	public void characters(char[] c, int i, int j) throws SAXException {
		try {
			super.characters(c, i, j);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append(c, i, j);
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		new PageHandler("/Users/naren/Documents/ir/WikiDump_1600.xml");
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		// Collaborative_Editing.xml ~50 ms
		// WikiDump_1600.xml ~800
	}

	public void startDocument() throws SAXException {
		super.startDocument();

		sb = new StringBuffer();
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

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

}
