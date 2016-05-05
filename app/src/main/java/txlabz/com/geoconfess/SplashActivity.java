package txlabz.com.geoconfess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import txlabz.com.geoconfess.views.PlayGifView;

public class SplashActivity extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
private String accesstoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PlayGifView pGif = (PlayGifView) findViewById(R.id.loader);
        pGif.setImageResource(R.drawable.load);
        accesstoken=Utils.getDatastring("token","",SplashActivity.this);
        waitAndNavigateToOnboardingTutorial();
    }

    private void waitAndNavigateToOnboardingTutorial() {
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                /*TODO: Check here if user has already logged, if yes then navigate to
                main screen instead of login*/

               if(accesstoken.equalsIgnoreCase("")) {
                   Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                   startActivity(intent);
               }
                else
               {
                   Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                   startActivity(intent);

               }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
