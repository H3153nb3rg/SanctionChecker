package at.jps.sanction.core.listhandler;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PanamaListHandler {

    static final Logger logger = LoggerFactory.getLogger(PanamaListHandler.class);

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
        Reader in;
        try {
            in = new FileReader(getFilename());
            final Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(in);

            for (final CSVRecord record : records) {

                final StringBuilder line = new StringBuilder();
                for (int i = 0; i < record.size(); i++) {

                    final String column = record.get(i);
                    line.append(column).append(" | ");

                }
                System.out.println(line.toString());
            }
            // logger.info("finished reading " + LISTNAME + " file:" + filename + " Records: " + getCityList().size());
        }
        catch (final Exception e) {

            e.printStackTrace();
        }

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

        final PanamaListHandler clh = new PanamaListHandler();

        clh.setFilename("C:\\Users\\johannes\\Sanctionlists\\panama\\Entities.csv");
        clh.initialize();
    }

}
