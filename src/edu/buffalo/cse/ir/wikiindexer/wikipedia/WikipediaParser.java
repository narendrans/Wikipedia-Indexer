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
	// TODO: First check if it is a section title, tag or whatever and then
	// proceed to parsing, the same text will be passed to all methods
	public static String parseSectionTitle(String titleStr) {
		if (titleStr == null)
			return null;
		else if (titleStr.isEmpty())
			return "";

		// =+ - one or more "="
		// .+ "." is any character -> one or more character
		// (.+) represents the string "text" inside "== text =="

		if (titleStr.contains(" ")) {
			Pattern regex = Pattern.compile("=+ (.+) =+");
			Matcher matcher = regex.matcher(titleStr);
			matcher.find();
			return matcher.group(1);
		} else {
			return titleStr.replaceAll("=", "");
		}

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

		text = text.trim();
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
	private static String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

	public static String[] parseLinks(String text) {

		String[] parsedLink = new String[2];
		parsedLink[0] = "";
		parsedLink[1] = "";
		if (text == null || text.isEmpty())
			return parsedLink;
		// [[Texas|Lone Star State]]

		// [[Wikipedia:Village pump|]]
		if (text.contains("wikipedia:") || text.contains("Wikipedia:")) {

			if (text.contains("(")) {
				text = text.replaceAll(" \\(.+\\)", "");
			}
			if (text.contains("#")) {
				text = text.replaceAll("\\[\\[|\\||\\]\\]", "");
				parsedLink[0] = text;
				return parsedLink;
			}

			text = text.replaceAll("\\[\\[|\\]\\]|\\||wikipedia|Wikipedia|\\:",
					"");
			parsedLink[0] = text;
			return parsedLink;

		}
		// [[Wiktionary:Hello]]
		else if (text.contains("wiktionary") || text.contains("Wiktionary")) {

			if (text.contains("(")) {
				text = text.replaceAll(" \\(.+\\)", "");
			}
			text = text.replaceAll("\\[\\[|\\]\\]|\\|", "");
			if (text.matches(".+\\:.+\\:.+"))
				text = text.replace("Wiktionary:", "");
			parsedLink[0] = text;
			return parsedLink;
		}
		// [[media:Classical guitar scale.ogg|Sound]]"
		else if (text.contains("media:")) {
			text = text.replaceAll("\\[\\[|\\]\\]", "");
			parsedLink[0] = text.replaceAll(".+\\|", "");
			return parsedLink;
		}
		// [[kingdom (biology)|]]
		else if (text.matches("\\[\\[.+\\|\\]\\]")) {
			text = text.replaceAll("\\[\\[|\\]\\]|\\|", "");
			String temp = text.replaceAll(" ", "_");
			parsedLink = temp.split("_");
			parsedLink[1] = capitalize(temp);
			parsedLink[0] = parsedLink[0].replace(",", "");
			return parsedLink;
		} else if (text.contains("File:") || text.contains("file:")) {
			if (text.matches("\\[\\[.+\\|.+\\|.+\\]\\]")) {
				Pattern regex = Pattern.compile("\\[\\[.+\\|.+\\|(.+)\\]\\]");
				Matcher matcher = regex.matcher(text);
				matcher.find();
				parsedLink[0] = matcher.group(1);
			} else if (text.matches("\\[\\[.+\\|.+\\|.+\\|.+\\]\\]")) {
				Pattern regex = Pattern
						.compile("\\[\\[.+\\|.+\\|.+\\|(.+)\\]\\]");
				Matcher matcher = regex.matcher(text);
				matcher.find();
				parsedLink[0] = matcher.group(1);
			}
			return parsedLink;
		}
		// [[Category:Character sets]]
		else if (text.matches("\\[\\[Category:.+\\]\\]")) {
			Pattern regex = Pattern.compile("\\[\\[Category:(.+)\\]\\]");
			Matcher matcher = regex.matcher(text);
			matcher.find();
			parsedLink[0] = matcher.group(1);
			return parsedLink;
		} else if (text.matches("\\[\\[\\:Category:.+\\]\\]")) {
			Pattern regex = Pattern.compile("\\[\\[\\:(.+)\\]\\]");
			Matcher matcher = regex.matcher(text);
			matcher.find();
			parsedLink[0] = matcher.group(1);
			return parsedLink;
		}
		// [[es:Plancton]]
		else if (text.matches("\\[\\[..\\:.+\\]\\]")) {
			Pattern regex = Pattern.compile("\\[\\[(.+)\\]\\]");
			Matcher matcher = regex.matcher(text);
			matcher.find();
			parsedLink[0] = matcher.group(1);
			return parsedLink;
		}

		// [http://www.wikipedia.org Wikipedia]

		else if (text.matches("\\[.+\\]") && text.contains("http:")) {
			if (text.contains(" ")) {
				Pattern regex = Pattern.compile("\\[(.+) (.+)\\]");
				Matcher matcher = regex.matcher(text);
				matcher.find();
				parsedLink[0] = matcher.group(2);
			} else
				return parsedLink;
		} else if (text.matches("\\[\\[.+\\|.+\\]\\]")) {
			text = text.replaceAll("\\[\\[|\\]\\]", "");
			parsedLink = text.split("\\|");
			text = parsedLink[0];
			parsedLink[0] = parsedLink[1];
			parsedLink[1] = text;
			return parsedLink;

		} else if (text.matches(".+\\[\\[.+\\|.+\\]\\]")) {
			Pattern regex = Pattern.compile("(.+)\\[\\[(.+)\\|(.+)\\]\\]");
			Matcher matcher = regex.matcher(text);
			matcher.find();
			parsedLink[0] = matcher.group(1) + matcher.group(3);
			parsedLink[1] = capitalize(matcher.group(2).replaceAll(" ", "_"));
			return parsedLink;

		}
		// San Francisco also has [[public transport]]ation

		// A [[micro-]]<nowiki />second
		else if (text.matches(".+\\[\\[.+\\]\\]<nowiki.*\\/\\>.+")) {
			Pattern regex = Pattern
					.compile("(.+)\\[\\[(.+)\\]\\]<nowiki.*\\/\\>(.+)");
			Matcher matcher = regex.matcher(text);
			matcher.find();
			parsedLink[0] = matcher.group(1) + matcher.group(2)
					+ matcher.group(3);
			parsedLink[1] = capitalize(matcher.group(2));
			return parsedLink;
		} else if (text.matches(".+\\[\\[.+\\]\\].+")) {
			Pattern regex = Pattern.compile("(.+)\\[\\[(.+)\\]\\](.+)");
			Matcher matcher = regex.matcher(text);
			matcher.find();
			parsedLink[0] = text.replaceAll("\\[\\[|\\]\\]", "");
			parsedLink[1] = capitalize(matcher.group(2).replaceAll(" ", "_"));
			return parsedLink;

		}
		return parsedLink;
	}
}
