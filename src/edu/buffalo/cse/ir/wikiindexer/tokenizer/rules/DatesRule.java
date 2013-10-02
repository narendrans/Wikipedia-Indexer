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
			private final static List<String> monthList = Arrays.asList(months.split(","));
			
public static String regChecker(String regExp, String text, int order, String monthNumber) {
		
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
			switch (order){
			case 1: 
				dayNumber = Integer.parseInt(splitText[0]);
				splitText[0] = dateFix(dayNumber);
				
				dateOrder = splitText[2] + monthNumber + splitText[0];
				result = result.replaceAll(regExp, dateOrder); break;
			case 2:
				dayNumber = Integer.parseInt(splitText[1]);
				splitText[1] = dateFix(dayNumber);
				
				dateOrder = splitText[2] + monthNumber + splitText[1];
				
				result = result.replaceAll(regExp, dateOrder); break;
				default: break;
			}
		}
		return result;
	}

	public static String dateFix(int date) {
		if (date < 10){
			return "0" + date;
		}
		else {
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
			String dateOrder;
			int dateNumber;
			switch (splitText[1]){
			case "BC": 
				dateNumber = Integer.parseInt(splitText[0]);
				if (100 > dateNumber && dateNumber >= 10) {
					splitText[0] = "-00" + splitText[0] + "0101";
				}
				else if (1000 > dateNumber && dateNumber >= 100) {
					splitText[0] = "-0" + splitText[0] + "0101";
				}
				
				
				dateOrder = splitText[2] + monthNumber + splitText[0];
				result = result.replaceAll(regExp, dateOrder); break;
			case "AD":
				dateNumber = Integer.parseInt(splitText[1]);
				splitText[1] = dateFix(dateNumber);
				
				dateOrder = splitText[2] + monthNumber + splitText[1];
				
				result = result.replaceAll(regExp, dateOrder); break;
				default: break;
			}
		}
		return result;
	}
	
	public static String dateCheck(String text, String month, String monthNumber){
		text = regChecker("[0-9]{1,2}\\s+"+month+"\\s+[0-9]{4}", text, 1, monthNumber);
		text = regChecker(month+"\\s+[0-9]{1,2},\\s+[0-9]{4}", text, 2, monthNumber);
		text = AD_BC_Checker("\\s+[0-9]{1,2},\\s+[0-9]{4}", text);
		return text;
	}
	
	// 1 swap year - day
	// 2 move year to the left
	
	public static String replaceDate(String text) {
		
		//text = text.replaceAll("[,]", "");
		String[] splitText = text.split("\\s+");
		int monthIndex;
		String monthNumber;
		for (String str : splitText) {
			str = str.replaceAll("[,.!?]", "");
			if (isMonth(str) != -1) {
				monthIndex = isMonth(str) + 1;
				monthNumber = dateFix(monthIndex);
				text = dateCheck(text, str, monthNumber);
				//Main
			}
		}
		
		text = text.replaceAll(",", "");
		text = text.replaceAll("\\s+", " ");
		text = text.trim();
		
		return text;
	}
	
	private static int isMonth(String month) {
		if (monthList.contains(month)) {
			return monthList.indexOf(month);
		}
		else return -1;
	}
	
	public static void main(String[] args) {
		System.out.println(replaceDate("President Franklin D. Roosevelt to proclaim December 7, 1941, 'a date which will live in infamy'"));
	}

	@Override
	/*public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				if (token != null) {
					token = replaceDate(token);
					//stream.set(token.split(" "));
				}
			}
		}
		stream.reset();
	}*/
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				if (token != null) {
					token = replaceDate(token);
					if (token.isEmpty())
						stream.remove();
					else
						stream.set(token);
					//stream.set(token.split(" "));
				}
			}
		}
		stream.reset();
	}

}
