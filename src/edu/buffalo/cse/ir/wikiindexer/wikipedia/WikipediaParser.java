/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nikhillo This class implements Wikipedia markup processing. Wikipedia
 *         markup details are presented here:
 *         http://en.wikipedia.org/wiki/Help:Wiki_markup It is expected that all
 *         methods marked "todo" will be implemented by students. All methods
 *         are static as the class is not expected to maintain any state.
 */
public class WikipediaParser {
	/* TODO */
	/**
	 * Method to parse section titles or headings. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Sections
	 * 
	 * @param titleStr
	 *            : The string to be parsed
	 * @return The parsed string with the markup removed
	 */
	public static String parseSectionTitle(String titleStr) {
		if (titleStr == null)
			return null;
		else if (titleStr.isEmpty())
			return "";
		Pattern regex = Pattern.compile("=+ (.+?) =+");
		Matcher matcher = regex.matcher(titleStr);
		matcher.find();
		return matcher.group(1);
	}

	/* TODO */
	/**
	 * Method to parse list items (ordered, unordered and definition lists).
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Lists
	 * 
	 * @param itemText
	 *            : The string to be parsed
	 * @return The parsed string with markup removed
	 */
	public static String parseListItem(String itemText) {
		if (itemText == null)
			return null;
		else if (itemText.isEmpty())
			return "";
		Pattern regex = Pattern.compile("(?:\\*+|#+|:+) (.+)");
		Matcher matcher = regex.matcher(itemText);
		matcher.find();
		return matcher.group(1);
	}

	/* TODO */
	/**
	 * Method to parse text formatting: bold and italics. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Text_formatting first point
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTextFormatting(String text) {
		if (text == null)
			return null;
		else if (text.isEmpty())
			return "";
		return text.replaceAll("'+", "");

	}

	/* TODO */
	/**
	 * Method to parse *any* HTML style tags like: <xyz ...> </xyz> For most
	 * cases, simply removing the tags should work.
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return The parsed text with the markup removed.
	 */
	public static String parseTagFormatting(String text) {
		if (text == null)
			return null;
		else if (text.isEmpty())
			return "";
		text = text.replaceAll("<[^>]*>", "");
		text = text.replaceAll("&lt;([^.]*?)&gt;", "");
		text = text.replace("  ", " ");

		if (text.startsWith(" "))
			text = text.replaceFirst(" ", "");

		if (text.endsWith(" "))
			text = text.substring(0, text.length() - 1);

		/*
		 * if(text.contains("  ")) text = text.replace("  ", " ");
		 * if(text.startsWith(" ")) text = text.replaceFirst(" ", "");
		 * if(text.endsWith(" ")) text = text.re
		 */
		return text;

	}

	/* TODO */
	/**
	 * Method to parse wikipedia templates. These are *any* {{xyz}} tags For
	 * most cases, simply removing the tags should work.
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTemplates(String text) {
		if (text == null)
			return null;
		else if (text.isEmpty())
			return "";
		return text.replaceAll("\\{\\{(.+?)\\}\\}", "");
	}

	/* TODO */
	/**
	 * Method to parse links and URLs. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Links_and_URLs
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return An array containing two elements as follows - The 0th element is
	 *         the parsed text as visible to the user on the page The 1st
	 *         element is the link url
	 */
	public static String[] parseLinks(String text) {
		String[] parsedLink = new String[2];
		if (text == null)
			return null;
		else if (text.isEmpty())
			return parsedLink;
		// (?:\\*+|#+|:+) (.+)

		Pattern regex = Pattern.compile("\\[\\[(.+?),\\u0020(.+?)\\|\\]\\]");
		Matcher matcher = regex.matcher(text);
		matcher.find();
		parsedLink[0] = matcher.group(1);
		StringBuilder str = new StringBuilder(parsedLink[0]);
		str.insert(0, "http://en.wikipedia.org/wiki/");
		parsedLink[1] = str.toString();
		return parsedLink;
	}

}
