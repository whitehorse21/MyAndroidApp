package txlabz.com.geoconfess.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by canopus on 5/5/16.
 */
public class Priest {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("surname")
    private String surname;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
