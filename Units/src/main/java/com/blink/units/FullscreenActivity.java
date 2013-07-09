package com.blink.units;

import com.blink.units.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.widget.Toast;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.JavascriptInterface;
import android.support.v4.app.NotificationCompat;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;
    WebAppInterface mInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        final WebView contentView = (WebView) findViewById(R.id.webview);
        contentView.clearCache(true);
        WebSettings webSettings = contentView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mInterface = new WebAppInterface(this);
        contentView.addJavascriptInterface(mInterface, "MyAndroidInterface");

        //contentView.loadUrl("http://dev.blinkux.net/labs/units/");
        contentView.loadUrl("http://192.168.16.174/Soft/isotope/");

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //mSystemUiHider.hide();
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        public void showNotification(String title, String message) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message);

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(mContext.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on.
            mNotificationManager.notify(3, mBuilder.build());
        }
    }
}
