package txlabz.com.geoconfess.web;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by irfanelahi on 20/04/2016.
 */
public interface AppAPI {

    @POST("oauth/token")
    Call<ResponseBody> oathToken(@Query("grant_type") String grant_type,
                                     @Query("username") String username,
                                     @Query("password") String password,
                                     @Query("os") String os,
                                     @Query("push_token") String push_token
    );

}
