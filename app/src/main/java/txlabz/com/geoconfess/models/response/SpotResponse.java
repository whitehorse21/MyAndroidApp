package txlabz.com.geoconfess.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by canopus on 5/5/16.
 */
public class SpotResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("activity_type")
    private String activity_type;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("priest")
    private Priest priest;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Priest getPriest() {
        return priest;
    }
}
