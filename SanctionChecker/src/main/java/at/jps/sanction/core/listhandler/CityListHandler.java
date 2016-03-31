package at.jps.sanction.core.listhandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CityListHandler {

    static final Logger logger = LoggerFactory.getLogger(CityListHandler.class);

    private String      filename;

    class CityInfo {
        String name;
        String country;
        int    population;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(final String country) {
            this.country = country;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(final int population) {
            this.population = population;
        }
    }

    private ArrayList<CityInfo> cityList;

    private void parseCity(final String line) {

        final String[] items = line.split("\t");

        // final StringTokenizer tokenizer = new StringTokenizer(line, "\t");
        //
        // Integer.parseInt(items[0]);
        // final String name = tokenizer.nextToken();
        // tokenizer.nextToken();
        // tokenizer.nextToken();
        // tokenizer.nextToken();
        // tokenizer.nextToken();
        // tokenizer.nextToken();
        // tokenizer.nextToken();
        // final String country = tokenizer.nextToken();
        // tokenizer.nextToken();
        // tokenizer.nextToken();
        // tokenizer.nextToken();
        // tokenizer.nextToken();
        // tokenizer.nextToken();
        // final int population = Integer.parseInt(tokenizer.nextToken());
        // Integer.parseInt(tokenizer.nextToken());
        // tokenizer.nextToken();
        // tokenizer.nextToken();

        final CityInfo cityInfo = new CityInfo();

        cityInfo.setCountry(items[8]);
        cityInfo.setName(items[1]);
        cityInfo.setPopulation(Integer.parseInt(items[14]));

        getCityList().add(cityInfo);

    }

    private void readFile() {
        BufferedReader input = null;

        try {
            input = new BufferedReader(new FileReader(getFilename()));

            String line = null;
            do {
                line = input.readLine();
                if (line != null) {

                    line = line.trim();

                    parseCity(line);

                }

            } while (line != null);

        }
        catch (final Exception x) {
            logger.error("parsing failed reading " + LISTNAME + " file:" + filename + " Exception: " + x.toString());
            logger.debug("Exception : ", x);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (final IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        logger.info("finished reading " + LISTNAME + " file:" + filename + " Records: " + getCityList().size());

    }

    public void initialize() {
        readFile();
    }

    final static String LISTNAME = "CITYLIST";

    public String getFilename() {
        return filename;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public ArrayList<CityInfo> getCityList() {

        if (cityList == null) {
            cityList = new ArrayList<CityInfo>();
        }
        return cityList;
    }

    public void setCityList(final ArrayList<CityInfo> cityList) {
        this.cityList = cityList;
    }

    public static void main(final String[] args) {

        final CityListHandler clh = new CityListHandler();

        clh.setFilename("C:\\Users\\johannes\\Sanctionlists\\geo\\cities1000.csv");
        clh.initialize();
    }

}
