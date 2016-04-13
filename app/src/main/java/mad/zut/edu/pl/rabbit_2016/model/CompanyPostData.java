package mad.zut.edu.pl.rabbit_2016.model;

/**
 * Created by Marek Macko on 01.04.2016.
 */
public class CompanyPostData {
    private final int companyId;
    private final Ratings opinions;
    private final String deviceUUID;
    private final String key;

    public CompanyPostData(int companyId, Ratings opinions, String deviceUUID, String key) {
        this.companyId = companyId;
        this.opinions = opinions;
        this.deviceUUID = deviceUUID;
        this.key = key;
    }

    public static class Ratings {
        final byte op1;

        public Ratings(byte op1) {
            this.op1 = op1;
        }
    }

}
