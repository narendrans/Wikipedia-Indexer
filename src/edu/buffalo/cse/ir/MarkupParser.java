/* 
 * Team Name: Infinite Loop
 * Project: UB_IR
 * File name: MarkupParser.java
 */
package edu.buffalo.cse.ir;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;

public class MarkupParser {

	public static void main(String[] args) throws ParseException {
		System.out.println("<random> I should not vanish </random>".replaceAll("\\u0020*<[^>]*>\\u0020*", ""));
		System.out.println("&lt;painful attr1='yes' attr2='no' &gt;Did you get me right?&lt;/pain&gt;".replaceAll("&lt;([^.]*?)&gt;", "$1"));
		System.out.println("watch  disappear".replaceAll("  ", " "));

	}

	private final static String texts = "{{redirects here|Person (law)|other uses|Legal personality|other uses|Corporate personhood}}\n"
			+ "{{pp-move-indef}}\n"
			+ "[[File:Persons.svg|190 px|thumb|Two silhouetted people]]\n"
			+ "\n"
			+ "A '''person''' is a being, such as a [[human]], that has certain capacities or attributes constituting [[personhood]], which in turn is defined differently by different authors in different disciplines, and by different cultures in different times and places. In ancient Rome, the word &quot;[[persona]]&quot; (Latin) or &quot;[[prosopon]]&quot; (πρόσωπον: Greek) originally referred to the masks worn by actors on stage. The various masks represented the various &quot;personae&quot; in the stage play.&lt;ref name=&quot;CathEnc&quot;&gt;{{cite encyclopedia|last=Geddes|first=Leonard|title=Person|encyclopedia=[[Catholic Encyclopedia]]|publisher=Robert Appleton Company|location=New York|year=1911|volume=11|url=http://www.newadvent.org/cathen/11726a.htm|accessdate=2011-03-09|quote=The Latin word persona was originally used to denote the mask worn by an actor. From this it was applied to the role he assumed, and, finally, to any character on the stage of life, to any individual.}}&lt;/ref&gt; \n"
			+ "\n"
			+ "The current concept of person was developed during the [[Trinitarianism|Trinitarian]] and [[Christology|Christological]] debates of the 4th and 5th centuries in contrast to the word nature.&lt;ref&gt;Thisleton NIGNTC commentary on 1 Corinthians &quot;Thinkers in ancient times had a difficulty in expressing the notion of personality&quot;; Barfield in History of English Words “Take, for instance, the word person...Its present meaning of an individual human being is largely due to the theologians who hit upon it when they were looking for some term that would enable them to assert the trinity of Godhead without admitting more than one 'substance'&quot;; John Zizioulas in Being as Communion 1985 New York:St Vladimirs Press p27writes: &quot;although the person and “personal identity” are Widely discussed nowadays as a supreme ideal, nobody seems to recognize that historically as well as existentially the concept of the person is indissolubly bound up with theology&quot; &lt;/ref&gt; \n"
			+ "During the theological debates, some'philosophical tools (concepts) were needed so that the debates could be held on common basis to all theological schools. The purpose of the debate was to establish the relation, similarities and differences between the Λóγος/&quot;Verbum&quot; and God. The philosophical concept of person arose, taking the word &quot;[[prosopon]]&quot; (πρόσωπον) from the [[Theatre of ancient Greece|Greek theatre]]. Therefore, Christus (the Λóγος/&quot;Verbum&quot;) and God were defined as different &quot;persons&quot;. This concept was applied later to the Holy Ghost, the angels and to all human beings.  \n"
			+ "\n"
			+ "Since then, a number of important changes to the word's meaning and use have taken place, and attempts have been made to redefine the word with varying degrees of adoption and influence. In addition to the question of personhood, of what makes a being count as a person to begin with, there are further questions about [[personal identity]]: both about what makes any particular person that particular person instead of another, and about what makes a person at one time the same person as he or she was or will be at another time despite any intervening changes. The common plural of &quot;person&quot;, &quot;[[people]]&quot;, is often used to refer to an entire [[nation]] or [[ethnic group]] (as in &quot;a people&quot;). The plural &quot;persons&quot; is often used in [[philosophy|philosophical]] and [[law|legal]] writing.\n"
			+ "\n"
			+ "==Personhood==\n"
			+ "[[File:Paul Klee WI (In Memoriam) 1938.jpg|thumb|An abstract painting of a person by [[Paul Klee]]. The concept of a person can be very challenging to define.]]\n"
			+ "{{main|Personhood}}\n"
			+ "\n"
			+ "{{quotation|The criteria for being a person... are designed to capture those attributes which are the subject of our most humane concern with ourselves and the source of what we regard as most important and most problematical in our lives.|Harry G. Frankfurt|}}\n"
			+ "Personhood is the status of being a person. Defining personhood is a controversial topic in [[philosophy]] and [[law]], and is closely tied to legal and [[political]] concepts of [[citizenship]], [[equality before the law|equality]], and [[liberty]]. According to law, only a [[natural person]] or [[legal personality]] has [[rights]], protections, privileges, responsibilities, and [[legal liability]].\n"
			+ "Personhood continues to be a topic of international debate, and has been questioned during the abolition of [[slavery]] and the fight for [[women's rights]], in debates about [[abortion]], [[fetal rights]] and [[reproductive rights]], and in [[animal rights]] advocacy.&lt;ref&gt;For a discussion of non-human personhood, see Midgley, Mary. [http://www.animal-rights-library.com/texts-m/midgley01.htm &quot;Persons and non-persons&quot;], in Peter Singer (ed.) ''In Defense of Animals''. Basil Blackwell, 1985, pp. 52-62.&lt;/ref&gt;\n"
			+ "\n"
			+ "Various debates have focused on questions about the personhood of different classes of entities. Historically, the personhood of animals, women, and slaves has been a catalyst of social upheaval. In most societies today, living adult humans are usually considered persons, but depending on the context, theory or definition, the category of &quot;person&quot; may be taken to include such [[Non human|non-human]] entities as [[animals]], [[artificial intelligences]], or [[extraterrestrial life]], as well as legal entities such as [[corporations]], [[sovereign states]] and other [[polities]], or [[estate]]s in [[probate]].&lt;ref&gt;For corporations, see [http://www.nytimes.com/2010/01/22/us/politics/22scotus.html &quot;Justices, 5-4, Reject Corporate Spending Limit&quot;], ''The New York Times'', January 21, 2010.&lt;/ref&gt; The category may exclude some human entities in [[prenatal]] development, and those with extreme mental impairment.\n"
			+ "\n"
			+ "==Personal identity==\n"
			+ "[[Image:MontreGousset001.jpg|thumb|What does it take for individuals to persist from moment to moment – or in other words, for the same individual to exist at different moments?]]\n"
			+ "{{main|Personal identity}}\n"
			+ "\n"
			+ "Personal identity is the [[Identity (philosophy)|unique identity]] of persons through time. That is to say, the necessary and sufficient conditions under which a person at one time and a person at another time can be said to be the ''same'' person, persisting through time. In the modern [[philosophy of mind]], this concept of personal identity is sometimes referred to as the ''[[wikt:diachronic|diachronic]]'' problem of personal identity. The ''[[wikt:synchronic|synchronic]]'' problem is grounded in the question of what features or traits characterize a given person at one time.\n"
			+ "\n"
			+ "Identity is an issue for both [[continental philosophy]]{{citation needed|date=October 2012}} and [[analytic philosophy]]{{citation needed|date=October 2012}}. A key question in continental philosophy is in what sense we can maintain the modern conception of identity, while realizing many of our prior assumptions about the world are incorrect{{citation needed|date=October 2012}}.\n"
			+ "\n"
			+ "Proposed solutions to the problem of personal identity include: continuity of the physical body, continuity of an immaterial mind or soul, continuity of consciousness or memory{{citation needed|date=October 2012}}, the [[bundle theory]] of self{{citation needed|date=October 2012}}, continuity of personality after the death of the physical body,&lt;ref&gt;For a discussion of post-mortal personhood, see Roth, S. (2013) ''Dying is only human. The case death makes for the immortality of the person''. Tamara Journal for Critical Organization Inquiry, Vol. 11, No. 2, pp. 35-39. [http://ssrn.com/abstract=2260793]&lt;/ref&gt; and proposals that there are actually no persons or selves which persist over time at all{{citation needed|date=October 2012}}.\n"
			+ "\n"
			+ "==See also==\n"
			+ "{{columns-list|3|\n"
			+ "* [[Animal rights]]\n"
			+ "* [[Anthropocentrism]]\n"
			+ "* [[Anthropology]]\n"
			+ "* [[Beginning of human personhood]]\n"
			+ "* [[Being]]\n"
			+ "* ''[[Capitis deminutio]]''\n"
			+ "* [[Moral character|Character]]\n"
			+ "* [[Citizenship]]\n"
			+ "* [[Consciousness]]\n"
			+ "* [[Corporate personhood]]\n"
			+ "* [[Great Ape personhood]]\n"
			+ "* [[Juridical person]]\n"
			+ "* [[Juristic person]]\n"
			+ "* [[Human]]\n"
			+ "* [[Identity (social science)|Identity]]\n"
			+ "* [[Individual]]\n"
			+ "* [[Immanuel Kant]]\n"
			+ "* [[Legal fiction]]\n"
			+ "* [[Nonperson]]\n"
			+ "* [[People]]\n"
			+ "* [[Personality psychology|Personality]]\n"
			+ "* [[Personhood movement]]\n"
			+ "* [[Personoid]]\n"
			+ "* [[Phenomenology (philosophy)|Phenomenology]]\n"
			+ "* [[Subject (philosophy)]]\n"
			+ "* [[Surety]]\n"
			+ "* [[Theory of mind]]\n"
			+ "}}\n"
			+ "\n"
			+ "==References==\n"
			+ "{{reflist}}\n"
			+ "\n"
			+ "==Further reading==\n"
			+ "*{{cite book|editor1-last=Lukes|editor1-first=Steven|editor2-last=Carrithers|editor2-first=Michael|editor3-last=Collins|editor3-first=Steven|title=The category of the person: Anthropology, philosophy, history|year=1987|publisher=Cambridge University Press|location=Cambridge|isbn=0-521-27757-4}}\n"
			+ "* Cornelia J.de Vogel ''The concept of personality in Greek and Christian thought''. In Studies in philosophy and the history of philosophy. Vol. 2. Edited by J. K. Ryan, Washington: Catholic University of America Press 1963. pp.&amp;nbsp;20–60\n"
			+ "* {{cite book|last=Puccetti|first=Roland|title=Persons: A Study of Possible Moral Agents in the Universe|year=1968|publisher=Macmillan and Company|location=London}}\n"
			+ "* {{CathEncy|wstitle=Person}}\n"
			+ "* {{cite encyclopedia|last=Korfmacher|first=Carsten|title=Personal Identity|encyclopedia=The [[Internet Encyclopedia of Philosophy]]|date=May 29, 2006|url=http://www.iep.utm.edu/p/person-i.htm|accessdate=2011-03-09}}\n"
			+ "\n"
			+ "==External links==\n"
			+ "{{Wiktionary|person}}\n"
			+ "* [http://ieet.org/index.php/IEET/RNHP Rights of Non-Human Persons Program] (Institute for Ethics and Emerging Technologies)\n"
			+ "\n"
			+ "[[Category:Concepts in ethics]]\n"
			+ "[[Category:Humans]]\n"
			+ "[[Category:People| ]]\n"
			+ "[[Category:Personal life]]\n"
			+ "[[Category:Personhood]]\n"
			+ "[[Category:Self]]";
}
