package at.jps.sanction.core.util.country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CountryCodeConverter {
    public static final int             ISO3166_2   = 1;
    public static final int             ISO3166_3   = 2;
    public static final int             FIPS_10_4   = 3;
    public static final int             FULLNAME    = 4;

    private static CountryCodeConverter ccc;
    private static int                  uniqueIndex = 0;
    private HashMap<Integer, String>    iso3166_2;
    private HashMap<Integer, String>    iso3166_3;
    private HashMap<Integer, String>    fips10_4;
    private HashMap<Integer, String>    fullName;
    private HashMap<String, Integer>    iso3166_2key;
    private HashMap<String, Integer>    iso3166_3key;
    private HashMap<String, Integer>    fips10_4key;
    private HashMap<String, Integer>    fullNamekey;

    private CountryCodeConverter() {
        iso3166_2 = new HashMap<Integer, String>();
        iso3166_3 = new HashMap<Integer, String>();
        fips10_4 = new HashMap<Integer, String>();
        fullName = new HashMap<Integer, String>();

        iso3166_2key = new HashMap<String, Integer>();
        iso3166_3key = new HashMap<String, Integer>();
        fips10_4key = new HashMap<String, Integer>();
        fullNamekey = new HashMap<String, Integer>();

        InputStream is = null;
        try {
            is = getClass().getResourceAsStream("countrycodes.csv");
            readFromFile(is, "ISO8859-1");
            if (is != null) {
                try {
                    is.close();
                }
                catch (final IOException e) {
                }
            }
            return;
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (final IOException e) {
                }
            }
        }
    }

    private static CountryCodeConverter getCCC() {
        if (ccc != null) {
            return ccc;
        }
        ccc = new CountryCodeConverter();
        return ccc;
    }

    public static String convert(int srcType, int targetType, String srcString) {
        String ret = null;
        if (srcString == null) {
            return null;
        }
        final Integer id = getCountryId(srcString.toLowerCase(), srcType);
        if (id != null) {
            ret = getCountryCode(id.intValue(), targetType);
        }
        else {
            ret = null;
        }
        if (ret != null) {
            for (int i = 0; i < ret.length(); i++) {
                final char c = ret.charAt(i);
                if (!Character.isLetter(c)) {
                    ret = null;
                    break;
                }
            }
        }
        return ret;
    }

    private static Integer getCountryId(String code, int type) {
        Integer o = null;
        final CountryCodeConverter ccc = getCCC();
        switch (type) {
            case 1:
                o = ccc.getIso3166_2key().get(code);
                break;
            case 2:
                o = ccc.getIso3166_3key().get(code);
                break;
            case 3:
                o = ccc.getFips10_4key().get(code);
                break;
            case 4:
                final HashMap<String, Integer> xx = ccc.getFullNamekey();
                o = xx.get(code);
        }
        return o;
    }

    private static String getCountryCode(int id, int type) {
        String o = null;
        final Integer idx = new Integer(id);
        final CountryCodeConverter ccc = getCCC();
        switch (type) {
            case 1:
                o = ccc.getIso3166_2().get(idx);
                break;
            case 2:
                o = ccc.getIso3166_3().get(idx);
                break;
            case 3:
                o = ccc.getFips10_4().get(idx);
                break;
            case 4:
                o = ccc.getFullName().get(idx);
        }
        return o;
    }

    private void readFromFile(InputStream is, String charSet) {
        BufferedReader br = null;
        InputStreamReader streamReader = null;
        try {
            streamReader = new InputStreamReader(is, charSet);
            br = new BufferedReader(streamReader);

            String line = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if ((line.length() >= 5) && (!line.startsWith("#"))) {
                    final String[] tokens = line.split(";");
                    if (tokens.length == 4) {
                        save4TokenValues(tokens);
                    }
                    else if (tokens.length == 3) {
                        save3TokenValues(tokens);
                    }
                }
            }
        }
        catch (final IOException ioe) {
            System.out.println("Can not read ISO3166 data file?");
        }
        finally {
            // FileUtils.close(streamReader, br);
        }
    }

    private void save4TokenValues(String[] tokens) {
        final Integer idx = new Integer(uniqueIndex++);

        iso3166_2.put(idx, tokens[0]);
        iso3166_2key.put(tokens[0].toLowerCase(), idx);

        iso3166_3.put(idx, tokens[1]);
        iso3166_3key.put(tokens[1].toLowerCase(), idx);

        fips10_4.put(idx, tokens[2]);
        fips10_4key.put(tokens[2].toLowerCase(), idx);

        fullName.put(idx, tokens[3]);
        fullNamekey.put(tokens[3].toLowerCase(), idx);
    }

    private void save3TokenValues(String[] tokens) {
        final Integer idx = new Integer(uniqueIndex++);

        iso3166_2.put(idx, tokens[0]);
        iso3166_2key.put(tokens[0].toLowerCase(), idx);

        iso3166_3.put(idx, tokens[1]);
        iso3166_3key.put(tokens[1].toLowerCase(), idx);

        fullName.put(idx, tokens[2]);
        fullNamekey.put(tokens[2].toLowerCase(), idx);
    }

    private HashMap<Integer, String> getFips10_4() {
        return fips10_4;
    }

    private HashMap<String, Integer> getFips10_4key() {
        return fips10_4key;
    }

    private HashMap<Integer, String> getFullName() {
        return fullName;
    }

    private HashMap<String, Integer> getFullNamekey() {
        return fullNamekey;
    }

    private HashMap<Integer, String> getIso3166_2() {
        return iso3166_2;
    }

    private HashMap<String, Integer> getIso3166_2key() {
        return iso3166_2key;
    }

    private HashMap<Integer, String> getIso3166_3() {
        return iso3166_3;
    }

    private HashMap<String, Integer> getIso3166_3key() {
        return iso3166_3key;
    }

}
