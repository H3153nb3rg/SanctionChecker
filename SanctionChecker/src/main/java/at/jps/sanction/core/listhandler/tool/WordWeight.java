package at.jps.sanction.core.listhandler.tool;

public class WordWeight {

    String token;
    int    counter;

    WordWeight(String token) {
        this.token = token;
        counter = 0;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

}
