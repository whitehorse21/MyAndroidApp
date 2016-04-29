package txlabz.com.geoconfess.models.response;

import com.google.gson.annotations.SerializedName;

public class AuthResponse{

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("refresh_token")
    private String refresh_token;

    @SerializedName("token_type")
    private String token_type;

    public String getAccessToken() {
        return accessToken;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }
}
