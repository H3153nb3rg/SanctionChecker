/*******************************************************************************
 * Copyright (c) 2015 Jim Fandango (The Last Guy Coding) Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package at.jps.sanction.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.codec.language.ColognePhonetic;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.RefinedSoundex;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenTool {

    static final Logger logger = LoggerFactory.getLogger(TokenTool.class);

    public static String buildTokenString(final List<String> textTokens, final String delimiter) {
        final StringBuilder text = new StringBuilder();
        for (final String token : textTokens) {
            text.append(token).append(delimiter);
        }
        return text.toString();
    }

    /**
     * simple plain text exists in text search without LSD
     *
     * @param textTokens1
     * @param textTokens2
     * @return true if shorter in longer
     */
    public static boolean checkContains(final List<String> textTokens1, final List<String> textTokens2, final String delimiter) {

        final String text1 = buildTokenString(textTokens1, delimiter);
        final String text2 = buildTokenString(textTokens2, delimiter);

        final boolean contains = (((text1.length() > 0) && (text2.length() > 0)) ? checkContains(text1, text2) : false);

        return contains;
    }

    public static boolean checkContains(final String text1, final String text2) {

        boolean contains = false;

        if (text1.length() > text2.length()) {
            contains = text1.contains(text2);
        }
        else {
            contains = text2.contains(text1);
        }

        return contains;
    }

    public static float compareCheck(final List<String> textTokens, final String text, final boolean fuzzy, final int minlen) {

        float deltaValue = 100;
        float percentHitrate = 0;

        // +/- fuzzy string compare

        if (textTokens.size() > 0) {
            for (final String token : textTokens) {
                if (token.length() >= minlen) {

                    if (fuzzy) {
                        final float lsHitValue = StringUtils.getLevenshteinDistance(text, token);

                        if (lsHitValue < deltaValue) {
                            deltaValue = lsHitValue;
                        }
                    }
                    else {
                        deltaValue = (text.equals(token) ? 0 : 100);
                    }
                }
            }
            percentHitrate = 100 - ((100 / ((float) (text.length()))) * deltaValue);
        }

        return percentHitrate;
    }

    /**
     * compare single token with list of tokens no modification of tokens is done
     *
     * @param text1
     * @param text2
     * @param fuzzy
     *            ( if true) LevenshteinDistance is used
     * @param minlen
     * @param fuzzyValue
     *            ( 20 - 80 % )
     * @return Percentage of of equality ( 0 - 100 %)
     */

    public static int compareCheck(final String text1, final String text2, final boolean fuzzy, final int minlen, final double fuzzyValue) {

        int deltaValue = text1.length();
        int minWordLen = deltaValue;
        int percentHitrate = 0;

        // +/- fuzzy string compare

        // only compare meaningfull !! deltalength > 80%
        // TODO: calc length to fuzzyness

        if (((text1.length() >= minlen) && (text2.length() >= minlen)) && (Math.abs(text1.length() - text2.length()) <= minlen)) {

            if (fuzzy) {

                minWordLen = Math.min(text1.length(), text2.length());
                final int threshold = (int) (minWordLen * fuzzyValue) + 1;

                deltaValue = StringUtils.getLevenshteinDistance(text1, text2, threshold);

                if (deltaValue == -1)  // threshold cutoff
                {
                    deltaValue = minWordLen;
                }
            }
            else {
                if (text1.equalsIgnoreCase(text2)) {  // TODO: ignoreCase ?
                    deltaValue = 0;
                }
            }
        }

        percentHitrate = (int) (1 - ((double) deltaValue / minWordLen)) * 100;

        // percentHitrate = 100 - ((100 / ((float) (text1.length()))) * deltaValue);
        // percentHitrate = 100 - ((100 / minWordLen) * deltaValue);

        return percentHitrate;
    }

    public static float compareCheckColognePhonetic(final String text1, final String text2, final boolean fuzzy, final int minlen, final double fuzzyValue) {

        final ColognePhonetic encoder = new ColognePhonetic(); // TODO: in reallife
        // make
        // this go away !!

        return (compareCheck(encoder.colognePhonetic(text1), encoder.colognePhonetic(text2), fuzzy, minlen, fuzzyValue));

    }

    public static float compareCheckDoubleMetaphone(final String text1, final String text2, final boolean fuzzy, final int minlen, final double fuzzyValue) {

        final DoubleMetaphone encoder = new DoubleMetaphone(); // TODO: in reallife
        // make
        // this go away !!

        return (compareCheck(encoder.doubleMetaphone(text1), encoder.doubleMetaphone(text2), fuzzy, minlen, fuzzyValue));

    }

    public static float compareCheckMetaphone(final String text1, final String text2, final boolean fuzzy, final int minlen, final double fuzzyValue) {

        final Metaphone encoder = new Metaphone(); // TODO: in reallife make this go
        // away
        // !!

        return (compareCheck(encoder.metaphone(text1), encoder.metaphone(text2), fuzzy, minlen, fuzzyValue));

    }

    public static float compareRefinedSoundex(final String text1, final String text2, final boolean fuzzy, final int minlen, final double fuzzyValue) {

        final RefinedSoundex encoder = new RefinedSoundex(); // TODO: in reallife make
        // this
        // go away !!

        return (compareCheck(encoder.encode(text1), encoder.encode(text2), fuzzy, minlen, fuzzyValue));

    }

    public static float compareSoundex(final String text1, final String text2, final boolean fuzzy, final int minlen, final double fuzzyValue) {

        final Soundex encoder = new Soundex(); // TODO: in reallife make this go away
        // !!

        return (compareCheck(encoder.encode(text1), encoder.encode(text2), fuzzy, minlen, fuzzyValue));

    }

    /**
     * @param list1
     *            ( uppercase entries presumed ! )
     * @param list2
     * @return number of elements contained in both lists
     */
    public static int compareTokenLists(final Collection<String> list1, final Collection<String> list2) {
        int count = 0;

        for (final String element : list2) {
            if (list1.contains(element.toUpperCase())) {
                count++;
            }
        }

        return count;
    }

    // filterTokens --> tokens will be checked against list
    // else pretokens will be checked against list
    public static List<String> getTokenList(final String text, final String delimiters, final String deadCharacters, final int minlength, final Collection<String> excludeList, final boolean filterTokens) {

        final List<String> textTokens = new ArrayList<>(10);

        if ((text != null) && (text.length() > minlength)) {

            String oriText = text.toUpperCase();

            // remove dead characters

            if (deadCharacters != null) {
                for (int i = 0; i < deadCharacters.length(); i++) {
                    oriText = StringUtils.remove(oriText, deadCharacters.charAt(i)); // oriText.replace(deadCharacters.charAt(i), ' ');
                }
            }

            // TODO: transliterate

            // remove stopwords - !! dead characters MUST NOT exist in stopwords !!
            // must be sorted by length !!
            if (!filterTokens) {
                if (excludeList != null) {
                    for (final String stopword : excludeList) {
                        oriText = oriText.replace(stopword.toUpperCase(), "");
                    }
                }
            }

            // maybe preserve original formatted text ?

            // StrTokenizer textTokenizer = new StrTokenizer(oriText, delimiters);
            // textTokenizer.setEmptyTokenAsNull(false);
            // textTokenizer.setIgnoreEmptyTokens(true); // feature !!

            if (delimiters != null) {
                final StringTokenizer textTokenizer = new StringTokenizer(oriText, delimiters);

                while (textTokenizer.hasMoreTokens()) {

                    final String token = textTokenizer.nextToken().trim();

                    if ((token != null) && (token.length() >= minlength)) {
                        // TODO: clear tokens ( deadchars...)

                        if (filterTokens) {
                            if ((excludeList == null) || (!excludeList.contains(token))) {
                                if (!textTokens.contains(token)) {
                                    textTokens.add(token);
                                }
                            }
                        }
                        else {
                            if (!textTokens.contains(token)) {
                                textTokens.add(token);
                            }
                        }
                    }
                }
            }
            else {
                textTokens.add(oriText);
            }
        }

        return textTokens;
    }
}
