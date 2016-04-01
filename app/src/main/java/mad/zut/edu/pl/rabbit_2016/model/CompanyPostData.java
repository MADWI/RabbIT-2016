package mad.zut.edu.pl.rabbit_2016.model;

/**
 * Created by Marek Macko on 01.04.2016.
 */
public class CompanyPostData {
    private final int companyId;
    private final Byte[] opinions;
    private final int deviceUUID;
    private final Byte[] key;

    public CompanyPostData(int companyId, Byte[] opinions, int deviceUUID, Byte[] key) {
        this.companyId = companyId;
        this.opinions = opinions;
        this.deviceUUID = deviceUUID;
        this.key = key;
    }
}
