package pong.hapra.pong03;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

/**
 * Activity des Spiels
 * @author Jan Bauerdick
 *
 */
public class PongActivity extends Activity {

    private static Context CONTEXT;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(new Pong(this));
        CONTEXT = this;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public static Context getContext() {
        return CONTEXT;
    }

}
