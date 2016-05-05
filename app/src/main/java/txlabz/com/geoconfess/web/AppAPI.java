package txlabz.com.geoconfess.web;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import txlabz.com.geoconfess.models.response.AuthResponse;
import txlabz.com.geoconfess.models.response.SpotResponse;

/**
 * Created by irfanelahi on 20/04/2016.
 */
public interface AppAPI {

    @POST("oauth/token")
    Call<AuthResponse> oathToken(@Query("grant_type") String grant_type,
                                 @Query("username") String username,
                                 @Query("password") String password,
                                 @Query("os") String os,
                                 @Query("push_token") String push_token
    );


    @POST("api/v1/registrations")
    Call<ResponseBody> SignUp(
            @Query("user[role]") String role,
            @Query("user[email]") String email,
            @Query("user[password]") String password,
            @Query("user[name]") String name,
            @Query("user[phone]") String phon,
            @Query("user[celebret_url]") String celebret_url,
            @Query("user[notification]") String notification,
            @Query("user[newsletter]") String newsletter,
            @Query("user[surname") String surname
    );

    @POST("api/v1/passwords")
    Call<ResponseBody> forgot(
            @Query("user[email]") String email
    );


    /*
   *  Defining Retrofit interface for calling spot creation Api with the requested parameters
   * */
    @POST("api/v1/spots")
    Call<SpotResponse> createSpot(
            @Query("spot[name]") String name,
            @Query("spot[activity_type]") String activity_type,
            @Query("spot[latitude]") String latitude,
            @Query("spot[longitude]") String longitude,
            @Query("access_token") String access_token

    );


    /*
   *  Defining Retrofit interface for calling spot updation Api with the requested parameters
   * */
    @PATCH("api/v1/spots/{id}")
    Call<ResponseBody> updateSpot(
            @Path("id") int spotId,
            @Query("spot[latitude]") String latitude,
            @Query("spot[longitude]") String longitude,
            @Query("access_token") String access_token

    );


    /*
  *  Defining Retrofit interface for Deleting spot
  * */
    @DELETE("api/v1/spots/{id}")
    Call<ResponseBody> deleteSpot(
            @Path("id") int spotId,
            @Query("access_token") String access_token

    );

}
