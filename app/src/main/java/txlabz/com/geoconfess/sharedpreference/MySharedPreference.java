package txlabz.com.geoconfess.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class used to handle the apps Shared Preferences
 *
 * @author CanopusInfoSystems
 * @version 1.0
 * @since 2016-05-04
 */

public class MySharedPreference {

    /*
    *  Required Variables Declaration
    * */
    private static SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private static MySharedPreference mRef;


    /*
    * Singleton method return the instance
    * */
    public static MySharedPreference getInstance(Context context)
    {
        if(mRef==null)
        {
            mRef = new MySharedPreference();
            mPref = context.getSharedPreferences("MyAndroidAppPreference",0);
            return  mRef;
        }
        return  mRef;
    }


    /*
    *   Get String value from Shared Preference
    * */
    public  String getString(String key)
    {
        try{
            String lValue;
            lValue = mPref.getString(key,"");
            return lValue;
        }catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }


    /*
    *   Put String value into Shared Preference
    * */
    public void putString(String key,String value)
    {
        try{
            mEditor = mPref.edit();
            mEditor.putString(key,value);
            mEditor.commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
