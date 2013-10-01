/* 
 * Team Name: Infinite Loop
 * Project: UB_IR
 * File name: PageHandler.java
 */
package edu.buffalo.cse.ir;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser;

public class PageHandler extends DefaultHandler {
	boolean currentElement = false;
	private ArrayList<Page> listOfPages;
	private Page page;
	private String inputXmlFileName;
	private StringBuffer sb;
	private String pageTitle;
	private String pageText;
	private final Stack<String> tagsStack = new Stack<String>();

	private String text;

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

	public ArrayList<WikipediaDocument> fetchDocuments(String inputXmlFileName)
			throws ParseException {
		this.inputXmlFileName = inputXmlFileName;
		listOfPages = new ArrayList<Page>();
		ArrayList<WikipediaDocument> docs = new ArrayList<WikipediaDocument>();

		if (inputXmlFileName == null || inputXmlFileName.equalsIgnoreCase("")) {
			docs.clear();
			return docs;
		} else {

			parseDocument();
			for (Page pages : listOfPages) {

				WikipediaDocument doc = new WikipediaDocument(
						Integer.parseInt(pages.getId()), pages.getTimeStamp(),
						pages.getUserName(), pages.getTitle());

				if (pages.getText() != null) {
					pageText = pages.getText();

					// Adding all the categories of the current page
					doc.addCategoriesP(getCategories(pageText));

					// Add all sections in the page
					List<LocalSection> ls = getSections(pageText);
					for (LocalSection localSection : ls) {
						doc.addSectionP(localSection.getTitle(),
								localSection.getText());
					}

					// Parse the contents of text

				}
				docs.add(doc);

			}

			return docs;
		}
	}

	public List<LocalSection> getSections(String input) {
		List<LocalSection> lsList = new ArrayList<LocalSection>();

		List<String> titles = new ArrayList<String>();
		titles.add("Default");
		Pattern pattern = Pattern.compile(".*==+(.+)==+.*");

		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			titles.add(matcher.group(1).replaceAll("=", ""));
		}

		String[] sections = input.split("==+.+==+");

		for (int i = 0; i < titles.size(); i++) {

			LocalSection ls = new LocalSection(titles.get(i), sections[i]);
			lsList.add(ls);
		}

		// String text = WikipediaParser.parseTagFormatting(sections[1]);
		// remove all references
		// String text = sections[1].replaceAll("\\<ref.+\\/ref\\>", "");
		// text = text.replaceAll("\\<ref.+\\/\\>", "");
		// remove all citations
		// text = text.replaceAll("\\{\\{.+\\}\\}", "");
		// remove all special characters
		// text = text.replaceAll("[^\\w\\s]+", " ");
		// text = text.replaceAll("''+", "");
		// text = text.replaceAll("\\s+", " ");
		// text = text.trim();
		// System.out.println(text);
		return lsList;
	}

	public Collection<String> getCategories(String input) {
		Collection<String> categories = new ArrayList<String>();

		if (input.contains("Category")) {
			Pattern pattern = Pattern.compile("\\[\\[Category:(.+)\\]\\]");

			Matcher matcher = pattern.matcher(input);
			while (matcher.find()) {
				categories.add(matcher.group(1));
			}
		}
		return categories;
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
		if (element.equalsIgnoreCase("text")) {
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
			e.printStackTrace();
		}
		sb.append(c, i, j);
	}

	public static void main(String[] args) throws ParseException {
		// long startTime = System.currentTimeMillis();
		PageHandler hl = new PageHandler();
		hl.fetchDocuments("/Users/naren/git/UB_IR/files/five_entries.xml");
		// long endTime = System.currentTimeMillis();
		// long totalTime = endTime - startTime;
		// System.out.println(totalTime);
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
