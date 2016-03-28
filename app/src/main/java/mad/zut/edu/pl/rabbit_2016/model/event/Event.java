
package mad.zut.edu.pl.rabbit_2016.model.event;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Event {

    @SerializedName("companyId")
    @Expose
    private String companyId;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("speaker")
    @Expose
    private String speaker;
    @SerializedName("speakerPictureUrl")
    @Expose
    private String speakerPictureUrl;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("title")
    @Expose
    private String title;

    /**
     *
     * @return
     * The companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     *
     * @param companyId
     * The companyId
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     *
     * @return
     * The data
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The speaker
     */
    public String getSpeaker() {
        return speaker;
    }

    /**
     *
     * @param speaker
     * The speaker
     */
    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    /**
     *
     * @return
     * The speakerPictureUrl
     */
    public String getSpeakerPictureUrl() {
        return speakerPictureUrl;
    }

    /**
     *
     * @param speakerPictureUrl
     * The speakerPictureUrl
     */
    public void setSpeakerPictureUrl(String speakerPictureUrl) {
        this.speakerPictureUrl = speakerPictureUrl;
    }

    /**
     *
     * @return
     * The time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The room
     */
    public String getRoom() {
        return room;
    }

    /**
     *
     * @param room
     * The room
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
