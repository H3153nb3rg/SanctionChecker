package at.jps.sanction.model;

public class OptimizationRecord {

    public final static String   OPTI_STATUS_NEW       = "N";
    public final static String   OPTI_STATUS_PENDING   = "P";
    public final static String   OPTI_STATUS_CONFIRMED = "C";

    public final static String[] statusCode            = { OPTI_STATUS_NEW, OPTI_STATUS_PENDING, OPTI_STATUS_CONFIRMED };

    private String               fieldName;
    private String               token;
    private String               watchListName;
    private String               watchListId;
    private String               status;

    public OptimizationRecord(final String fieldName, final String token, final String watchListName, final String watchListId, final String status) {
        this.fieldName = fieldName;
        this.token = token;
        this.watchListName = watchListName;
        this.watchListId = watchListId;
        this.status = status;

    }

    public OptimizationRecord() {

    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWatchListName() {
        return watchListName;
    }

    public void setWatchListName(String watchListName) {
        this.watchListName = watchListName;
    }

    public String getWatchListId() {
        return watchListId;
    }

    public void setWatchListId(String watchListId) {
        this.watchListId = watchListId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {

        StringBuilder txt = new StringBuilder(128);
        txt.append(getFieldName()).append(";").append(getToken()).append(";").append(getWatchListName()).append(";").append(getWatchListId()).append(";").append(getStatus());

        return txt.toString();
    }

    @Override
    public boolean equals(Object other) {

        return getFieldName().equals(((OptimizationRecord) other).getFieldName()) && getToken().equals(((OptimizationRecord) other).getToken())
                && getWatchListId().equals(((OptimizationRecord) other).getWatchListId()) && getWatchListName().equals(((OptimizationRecord) other).getWatchListName())
                && getStatus().equals(((OptimizationRecord) other).getStatus());
    }

}
