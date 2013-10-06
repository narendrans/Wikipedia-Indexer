package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.DATES)
public class DatesRule implements TokenizerRule {

	private final static String months = "January,February,March,April,May,June,July,August,September,October,November,December";
	private final static List<String> monthList = Arrays.asList(months
			.split(","));

	public static String regChecker(String regExp, String text, int order,
			String monthNumber) {

		String result = text;
		String clearDigit;
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(text);
		if (m.find()) {
			clearDigit = m.group();
			clearDigit = clearDigit.replaceAll("[,.!?]", "");
			String[] splitText = clearDigit.split("\\s+");
			String dateOrder;
			int dayNumber;
			switch (order) {
			case 1:
				dayNumber = Integer.parseInt(splitText[0]);
				splitText[0] = dateFix(dayNumber);

				dateOrder = splitText[2] + monthNumber + splitText[0];
				result = result.replaceAll(regExp, dateOrder);
				break;
			case 2:
				dayNumber = Integer.parseInt(splitText[1]);
				splitText[1] = dateFix(dayNumber);

				dateOrder = splitText[2] + monthNumber + splitText[1];

				result = result.replaceAll(regExp, dateOrder);
				break;
			case 3:
				dayNumber = Integer.parseInt(splitText[1]);
				splitText[1] = dateFix(dayNumber);

				dateOrder = "1900" + monthNumber + splitText[1];

				result = result.replaceAll(regExp, dateOrder);
				break;
			default:
				break;
			}
		}
		return result;
	}

	public static String dateFix(int date) {
		if (date < 10) {
			return "0" + date;
		} else {
			return date + "";
		}
	}

	public static String AD_BC_Checker(String regExp, String text) {

		String result = text;
		String clearDigit;
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(text);
		if (m.find()) {
			clearDigit = m.group();
			clearDigit = clearDigit.replaceAll("[,.!?]", "");
			String[] splitText = clearDigit.split("\\s+");
			int dateNumber;

			dateNumber = Integer.parseInt(splitText[0]);
			String sign = "";
			if (splitText[1].equals("BC"))
				sign = "-";

			if (100 > dateNumber && dateNumber >= 10) {
				splitText[0] = sign + "00" + splitText[0] + "0101";
			} else if (1000 > dateNumber && dateNumber >= 100) {
				splitText[0] = sign + "0" + splitText[0] + "0101";
			} else if (10000 > dateNumber && dateNumber >= 100) {
				splitText[0] = sign + splitText[0] + "0101";
			} else {
				splitText[0] = sign + "000" + splitText[0] + "0101";
			}

			result = result.replaceAll(regExp, splitText[0]);
		}
		return result;
	}

	public static String yearChecker(String regExp, String text) {

		String result = text;
		String tempString;
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(text);
		if (m.find()) {
			tempString = m.group();
			tempString = tempString.replaceAll("\\s+", "");
			if (tempString.substring(tempString.length() - 1).equals("–")) {
				result = result.replaceAll(
						regExp,
						" " + tempString.substring(0, 4) + "0101"
								+ tempString.substring(4));
			} else {
				result = result.replaceAll(regExp, " " + tempString + "0101 ");
			}

		}
		return result;
	}

	public static String timeChecker(String regExp, String text) {

		String temp;
		int pm;
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(text);
		if (m.find()) {
			temp = m.group().toLowerCase();
			temp = temp.replaceAll("\\s+", "");
			if (temp.substring(temp.length() - 2).equals("am")) {
				temp = temp.replaceAll("am", ":00");
			} else if (temp.substring(temp.length() - 2).equals("pm")) {

				temp = temp.replaceAll("pm", ":00");
				if (temp.substring(1, 2).equals(":")) {
					pm = Integer.parseInt(temp.substring(0, 1)) + 12;
					temp = pm + temp.substring(1);
				} else if (temp.substring(2, 3).equals(":")) {
					pm = Integer.parseInt(temp.substring(0, 2)) + 12;
					temp = pm + temp.substring(2);
				}
			}
			text = text.replaceAll(m.group(), temp);

		}
		return text;
	}

	public static String dateCheck(String text, String month, String monthNumber) {
		text = regChecker("[0-9]{1,2}\\s+" + month + "\\s+[0-9]{4}", text, 1,
				monthNumber);
		text = regChecker(month + "\\s+[0-9]{1,2},\\s+[0-9]{4}", text, 2,
				monthNumber);
		text = regChecker(month + "\\s+[0-9]{1,2}", text, 3, monthNumber);
		return text;
	}

	// 1 swap year - day
	// 2 move year to the left

	public static String clearUnnecessary(String text) {
		String regExp = "on (Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)(,)?\\s+[0-9]{8}";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(text);
		String temp;
		if (m.find()) {
			// System.out.println(m.group());
			temp = m.group();
			temp = temp.substring(temp.length() - 8);
			text = text.replaceAll(m.group(), temp);
		}
		text = text.replaceAll("\\s+UTC|GMT(,)", "");

		p = Pattern.compile("[0-9]{8},");
		m = p.matcher(text);
		if (m.find()) {
			temp = m.group().replaceAll(",", "");
			text = text.replaceAll(m.group(), temp);
		}

		p = Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{2}\\s+[0-9]{8}");
		m = p.matcher(text);
		if (m.find()) {
			temp = m.group();
			temp = temp.substring(temp.length() - 9) + " "
					+ temp.substring(0, 8);
			text = text.replaceAll(m.group(), temp);
		}

		p = Pattern.compile("–[0-9]{2}");
		m = p.matcher(text);
		int converter;
		if (m.find()) {
			temp = m.group();
			converter = Integer.parseInt(temp.substring(1));
			temp = "–20" + converter + "0101";
			text = text.replaceAll(m.group(), temp);
		}

		return text;
	}

	public static String replaceDate(String text) {

		String[] splitText = text.split("\\s+");
		int monthIndex;
		String monthNumber;
		for (String str : splitText) {
			str = str.replaceAll("[,.!?]", "");
			if (isMonth(str) != -1) {
				monthIndex = isMonth(str) + 1;
				monthNumber = dateFix(monthIndex);
				text = dateCheck(text, str, monthNumber);
			}
		}

		text = AD_BC_Checker("[0-9]{1,4}\\s+BC", text);
		text = AD_BC_Checker("[0-9]{1,4}\\s+AD", text);
		text = yearChecker("\\s+[0-9]{4}+\\s", text);
		text = yearChecker("\\s+[0-9]{4}–", text);
		text = timeChecker("[0-9]{1,2}:[0-9]{1,2}(\\s+)?(am|AM|pm|PM)", text);
		text = clearUnnecessary(text);

		text = text.replaceAll("\\s+", " ");
		text = text.trim();

		return text;
	}

	private static int isMonth(String month) {
		if (monthList.contains(month)) {
			return monthList.indexOf(month);
		} else
			return -1;
	}

	public static void main(String[] args) {
		System.out
				.println(replaceDate("President Franklin D. Roosevelt to proclaim December 7, 1941, 'a date which will live in infamy'"));
		System.out.println(replaceDate("It was now about 10:15 am."));
		System.out
				.println(replaceDate("The 2004 Indian Ocean earthquake was an undersea megathrust earthquake that occurred at 00:58:53 UTC on Sunday, 26 December 2004"));
		System.out
				.println(replaceDate("Apple is one of the world's most valuable publicly traded companies in 2011–12."));
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				stream.previous();
				if (token != null) {
					token = replaceDate(token);
					if (token.isEmpty())
						stream.remove();
					else {
						stream.set(token);
						stream.next();
					}

				}
			}
		}
		stream.reset();
	}

}
