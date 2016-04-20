package txlabz.com.geoconfess;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by irfanelahi on 20/04/2016.
 */
public class GeneralUtility {

    public static void hideKeyBoard(Context context) {
        Activity act = (Activity) context;
        if (act == null) {
            return;
        }

        InputMethodManager inputManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (act.getCurrentFocus() != null) {
            inputManager.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
