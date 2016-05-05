package txlabz.com.geoconfess;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utils {

	ProgressDialog dialogs;
	ProgressDialog dialogs2;
	ProgressBar p;
	Context context;
	Dialog loadingDialog;
	Dialog loadingDialog2;

	private final static String TAG = "ODIN";
	private static LocationManager locationManager;

	private final static String SHA1_ALGORITHM = "SHA-1";
	private final static String CHAR_SET = "iso-8859-1";

	public static enum ScreenDensity {
		dpi,
		mdpi,
		hdpi,
		xhdpi,
		xxhdpi,
		xxxhdpi
	}
	public static ScreenDensity currentScreenDensity;


	public void showDialogs2()
	{

		dialogs = new ProgressDialog(context,AlertDialog.THEME_HOLO_LIGHT);
		dialogs.setMessage("Loading...");
		dialogs.setCanceledOnTouchOutside(false);

		dialogs.setCancelable(false);
		dialogs.show();

	}


	public void showDialogs(ProgressBar bar)
	{
		p=bar;

		p.setVisibility(View.VISIBLE);

		dialogs = new ProgressDialog(context);
		dialogs.setMessage("Loading");
		dialogs.setCanceledOnTouchOutside(false);

		dialogs.setCancelable(false);
		//	dialogs.show();

	}

	public void showEditDialogs()
	{

		dialogs = new ProgressDialog(context);
		dialogs.setMessage("Editing please wait...");
		dialogs.setCanceledOnTouchOutside(false);

		dialogs.setCancelable(false);
		dialogs.show();

	}


	public void dissmissdialog()
	{

		if(p!=null) {
			p.setVisibility(View.INVISIBLE);
		}
		if(dialogs!=null&&dialogs.isShowing())
		{
			dialogs.dismiss();


		}

	}
	public static int convertDpToPixel(float dp, Context context){
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int)px;
	}

	public static void initializeUtils(Context context) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		switch(metrics.densityDpi) {
			case 120:
				currentScreenDensity = ScreenDensity.dpi;
				break;
			case 160:
				currentScreenDensity = ScreenDensity.mdpi;
				break;
			case 240:
				currentScreenDensity = ScreenDensity.hdpi;
				break;
			case 320:
				currentScreenDensity = ScreenDensity.xhdpi;
				break;
			case 480:
				currentScreenDensity = ScreenDensity.xxhdpi;
				break;
			case 640:
				currentScreenDensity = ScreenDensity.xxhdpi;
				break;
		}


	}

//	public static Session openActiveSession(Activity activity,
//											boolean allowLoginUI, Session.StatusCallback callback,
//											List<String> permissions) {
//		Session.OpenRequest openRequest = new Session.OpenRequest(activity)
//				.setPermissions(permissions).setCallback(callback);
//		Session session = new Session.Builder(activity).build();
//
//		if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState())
//				|| allowLoginUI) {
//			Session.setActiveSession(session);
//			session.openForRead(openRequest);
//			return session;
//		}
//		return null;
//	}
//
//


	public Utils(Context context1) {
		// TODO Auto-generated constructor stub
		context=context1;
	}

	public Boolean isgpson(Context context)
	{
		locationManager = (LocationManager) context.getSystemService(
				Context.LOCATION_SERVICE);

		// getting GPS status
		boolean isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		System.out.println("isgps en:" + isGPSEnabled);





		return isGPSEnabled;



	}

	public static Bitmap takeScreenshot(Activity activity) {
		ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
		ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
		decorChild.setDrawingCacheEnabled(true);
		decorChild.buildDrawingCache();
		Bitmap drawingCache = decorChild.getDrawingCache(true);
		Bitmap bitmap = Bitmap.createBitmap(drawingCache);
		decorChild.setDrawingCacheEnabled(false);
		return bitmap;
	}

	/**
	 * Print hash key
	 */
	public static void printHashKey(Context context) {
		try {
			String TAG = "com.android.sport_app";
			PackageInfo info = context.getPackageManager().getPackageInfo(TAG, PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
				Log.d(TAG, "keyHash: " + keyHash);
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

	/**
	 * Update language
	 *
	 * @param code
	 *            The language code. Like: en, cz, iw, ...
	 */
	public static void updateLanguage(Context context, String code) {
		Locale locale = new Locale(code);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
	}

	/**
	 * Build alert dialog with properties and data
	 *
	 * @parampairs
	 * @return {@link AlertDialog}
	 */



	public static AlertDialog buildProfileResultDialog(Activity activity, Pair<String, String>... pairs) {
		StringBuilder stringBuilder = new StringBuilder();

		for (Pair<String, String> pair : pairs) {
			stringBuilder.append(String.format("<h3>%s</h3>", pair.first));
			stringBuilder.append(String.valueOf(pair.second));
			stringBuilder.append("<br><br>");
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(Html.fromHtml(stringBuilder.toString())).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});
		AlertDialog alertDialog = builder.create();
		return alertDialog;
	}
	public static List<String> getFaceBookPermmissions(){
		List<String> permissions = new ArrayList<String>();
		permissions.add("email");
		return permissions;
	}
	public static String checkNull(String text)
	{

		if (text == null)
		{
			return "";
		}

		return text;
	}
	public static String toHtml(Object object) {
		StringBuilder stringBuilder = new StringBuilder(256);
		try {
			for (Field field : object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				Object val = field.get(object);
				stringBuilder.append("<b>");
				stringBuilder.append(field.getName().substring(1, field.getName().length()));
				stringBuilder.append(": ");
				stringBuilder.append("</b>");
				stringBuilder.append(val);
				stringBuilder.append("<br>");
			}
		} catch (Exception e) {
			// Do nothing
		}
		return stringBuilder.toString();
	}

	public static byte[] getSamleVideo(Context context, String assetFile) {
		try {
			InputStream inputStream = context.getAssets().open(assetFile);
			return getBytes(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] getBytes(InputStream input) throws IOException {
		byte[] buffer = new byte[8192];
		int bytesRead;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		return output.toByteArray();
	}

	public static void changeEditTextUnderlineColor(EditText editText) {
		int color = Color.parseColor("#ffffff");
		Drawable drawable = editText.getBackground();
		drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			editText.setBackground(drawable);
		} else {
			editText.setCompoundDrawables(null, null, drawable, null);
		}
	}
	public static void saveDataint( String key, int value,Context context)
	{
		if(context!=null)
		{
			SharedPreferences.Editor editor = PreferenceManager
					.getDefaultSharedPreferences(context).edit();
			editor.putInt(key, value);
			editor.commit();

		}
	}
	public static void saveDataString( String key, String value,Context context)
	{
		if(context!=null)
		{
			SharedPreferences.Editor editor = PreferenceManager
					.getDefaultSharedPreferences(context).edit();
			editor.putString(key, value);
			editor.commit();
		}
	}







//	public void showLoadingDialog(){
//
//		 dismiss();
//
//		 loadingDialog = new Dialog(context);
//		 loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		 loadingDialog.setContentView(com.paid2save.android.R.layout.showloadingxml);
//
//		 loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//		 loadingDialog.setCanceledOnTouchOutside(false);
//		 loadingDialog.setCancelable(false);
//		 ImageView progress = (ImageView)loadingDialog.findViewById(com.paid2save.android.R.id.home_image_selector);
//		 progress.setBackgroundResource(com.paid2save.android.R.drawable.progressanimation);
//
//		 final AnimationDrawable anim =(AnimationDrawable)progress.getBackground();
//
//
//		 progress.post(new Runnable() {
//
//		  @Override
//		  public void run() {
//		   anim.start();
//
//		  }
//		 });
//
//		 loadingDialog.show();
//
//		 }
//
//
//	public void dismiss()
//	{
//		if(loadingDialog != null && loadingDialog.isShowing())
//		{
//			loadingDialog.dismiss();
//		}
//	}




	/*
	 * this method is used to get data from preferences
	 */
	public static int getDataint( String key, int defaultValue,Context context)
	{

		if(context!=null)
		{
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(context);
			int value = preferences.getInt(key, defaultValue);


			return value;
		}
		return 0;

	}

	public static String getDatastring( String key, String defaultValue,Context context)
	{

		if(context!=null)
		{
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(context);
			String value = preferences.getString(key, defaultValue);

			return value;
		}
		return " ";
	}

	public static boolean haveInternet(Context txt) {

		ConnectivityManager cm = (ConnectivityManager) txt
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = cm.getActiveNetworkInfo();
		if (network != null) {
			return network.isAvailable();
		}
		return false;
	}


	public static void alertbox (String message,Context context)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(
				context).create();

		// Setting Dialog Title


		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting Icon to Dialog
		//alertDialog.setIcon(R.drawable.tick);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {


			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	public static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}
	public static boolean isEmailValid(String s)
	{
		boolean   isValid=false;
		if (Patterns.EMAIL_ADDRESS.matcher(s).matches())
		{
			isValid = true;
		}
		return isValid;
	}


	public static void hideKeyboard2(Context ctx) {
		InputMethodManager inputManager = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		// check if no view has focus:
		View v = ((Activity) ctx).getCurrentFocus();
		if (v == null)
			return;

		inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public void hideKeyboard(Activity activity) {
		InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (activity.getCurrentFocus() != null) {
			inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	public static int calculateInSampleSize(BitmapFactory.Options options,
											int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}



	public static Bitmap decodeSampledBitmapFromResource(Resources res,
														 int reqWidth, int reqHeight,int id) {
		try
		{
			// First decode with inJustDecodeBounds=true to check dimensions
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;

			//	BitmapFactory.decodeFile(path, options);
			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, reqWidth,
					reqHeight);

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;
			return  BitmapFactory.decodeResource(res, id, options);
		}
		catch(OutOfMemoryError e){

			return null;
		}


	}

	private static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) ('0' + halfbyte));
				else
					buf.append((char) ('a' + (halfbyte - 10)));
				halfbyte = data[i] & 0x0F;
			} while(two_halfs++ < 1);
		}
		return buf.toString();
	}

	private static String SHA1(String text) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance(SHA1_ALGORITHM);
			byte[] sha1hash = new byte[40];
			md.update(text.getBytes(CHAR_SET), 0, text.length());
			sha1hash = md.digest();

			return convertToHex(sha1hash);
		} catch (Exception e) {
			Log.i(TAG, "Error generating generating SHA-1: ", e);
			return null;
		}
	}



	public static int calculateInSampleSize2(BitmapFactory.Options options,
											 int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(String path,
														 int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// BitmapFactory.decodeResource(res, resId, options);
		BitmapFactory.decodeFile(path, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize2(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public static String normalizeCommasInString(String input) {
		if(input == null)
			return input;
		String output = input.replace(",", ", ");
		return output;
	}




}