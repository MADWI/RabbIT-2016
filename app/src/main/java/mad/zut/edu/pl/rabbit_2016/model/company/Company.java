
package mad.zut.edu.pl.rabbit_2016.model.company;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Company {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("websiteUrl")
    @Expose
    private String websiteUrl;
    @SerializedName("logoUrl")
    @Expose
    private String logoUrl;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
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
     *     The websiteUrl
     */
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    /**
     * 
     * @param websiteUrl
     *     The websiteUrl
     */
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    /**
     * 
     * @return
     *     The logoUrl
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * 
     * @param logoUrl
     *     The logoUrl
     */
    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
