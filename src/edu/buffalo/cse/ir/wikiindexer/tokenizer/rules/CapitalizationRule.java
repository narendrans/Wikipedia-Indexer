package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.WHITESPACE)
public class CapitalizationRule implements TokenizerRule {
	
	private final static String changelessNouns = "Francisco,HIV,iOS,Apple,California";
			private final static List<String> changelessNounList = Arrays.asList(changelessNouns.split(","));

	public static String regChecker(String regExp, String text) {
		
		String result = text;
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(text);
		//if (m.find())
			//result = result.replaceAll(str1, str2);
		//return result;
		while(m.find()){
			if(m.group().length() !=0){
				//result = result.replaceAll(m.group(), m.group().toLowerCase());
				System.out.println(m.group());
			}
		}
		return result;
	}

	public static String lowercaseCapital(String text) {
		String[] splitText = text.split("(\\s+)|(')");
		String lastWord = splitText[splitText.length - 1];
		lastWord = lastWord.replaceAll("[.!?]", "");
		if (!isChangelessNoun(lastWord)) {
			text = text.replaceAll(lastWord, lastWord.toLowerCase());
		}
		boolean fullOfChangelessNouns = true;
		for (String str : splitText) {
			str = str.replaceAll("[.!?]", "");
			if (!isChangelessNoun(str)) {
				text = text.replaceAll(str, str.toLowerCase());
			} else
				fullOfChangelessNouns = false;
		}
		if (fullOfChangelessNouns == true) {
			//text = "";
			return text;
		}
		text = text.replaceAll("\\s+", " ");
		text = text.trim();

		return text;
	}
	public static void main(String[] args) {
		System.out.println(lowercaseCapital("THis Is A teSt iOS 49A9s San Francisco California."));
	}
	
	private static boolean isChangelessNoun(String word) {
		return changelessNounList.contains(word);
	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();
				stream.previous();
				if (token != null) {
					token = lowercaseCapital(token);
					stream.set(token);
					stream.next();
				}
			}
		}
		stream.reset();
	}
}