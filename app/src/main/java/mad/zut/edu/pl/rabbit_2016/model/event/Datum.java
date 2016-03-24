
package mad.zut.edu.pl.rabbit_2016.model.event;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("speaker")
    @Expose
    private String speaker;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("speakerPictureUrl")
    @Expose
    private String speakerPictureUrl;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The speaker
     */
    public String getSpeaker() {
        return speaker;
    }

    /**
     * 
     * @param speaker
     *     The speaker
     */
    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    /**
     * 
     * @return
     *     The room
     */
    public String getRoom() {
        return room;
    }

    /**
     * 
     * @param room
     *     The room
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The companyId
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 
     * @param companyId
     *     The companyId
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 
     * @return
     *     The speakerPictureUrl
     */
    public String getSpeakerPictureUrl() {
        return speakerPictureUrl;
    }

    /**
     * 
     * @param speakerPictureUrl
     *     The speakerPictureUrl
     */
    public void setSpeakerPictureUrl(String speakerPictureUrl) {
        this.speakerPictureUrl = speakerPictureUrl;
    }

}
