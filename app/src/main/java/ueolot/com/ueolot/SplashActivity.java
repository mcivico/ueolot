package ueolot.com.ueolot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "7B9KPkmlAm4AtvqYsfEhAYvqi";
    private static final String TWITTER_SECRET = "o3g3Nd0fCnK2VbGFGw7nFo1jNLkpSuST3GHv7sGWgjap9DI0lt";

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("JXOPwK50LDQIDq4s9hpUysErF","WOUiCi7Fa8PshUzuY8jFArjDnQ0osxQz20PClBYyPaMnXSsK8z"))
                .debug(true)
                .build();
        Twitter.initialize(config);*/

        //TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);

       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
