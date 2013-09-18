package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.DATES)
public class DatesRule implements TokenizerRule {

	private static String formatDate(String inputDate){
		String s;
		Format formatter;
		Date date = new Date();

		// 2012-12-01
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		s = formatter.format(date);
		return s;
	}
	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		// TODO Auto-generated method stub

	}

}
