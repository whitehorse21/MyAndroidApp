package txlabz.com.geoconfess.web;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by irfanelahi on 20/04/2016.
 */
public class AppApiController {

    private static AppAPI apiInstance;
    private static void init() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://geoconfess.herokuapp.com/")
                    .build();

            apiInstance = retrofit.create(AppAPI.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static AppAPI getApiInstance() {
        if(apiInstance == null) {
            init();
        }
        return apiInstance;
    }



}
