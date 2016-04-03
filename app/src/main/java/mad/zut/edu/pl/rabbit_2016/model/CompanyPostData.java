package mad.zut.edu.pl.rabbit_2016.model;

/**
 * Created by Marek Macko on 01.04.2016.
 */
public class CompanyPostData {
    private final int companyId;
    private final byte[] opinions;
    private final String deviceUUID;
    private final String key;

    public CompanyPostData(int companyId, byte[] opinions, String deviceUUID, String key) {
        this.companyId = companyId;
        this.opinions = opinions;
        this.deviceUUID = deviceUUID;
        this.key = key;
    }
}
