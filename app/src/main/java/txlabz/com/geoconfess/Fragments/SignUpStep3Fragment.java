package txlabz.com.geoconfess.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import txlabz.com.geoconfess.DialogUtility;
import txlabz.com.geoconfess.GeneralUtility;
import txlabz.com.geoconfess.MainActivity;
import txlabz.com.geoconfess.R;
import txlabz.com.geoconfess.web.AppApiController;

/**
 * Created by irfanelahi on 27/04/2016.
 */
public class SignUpStep3Fragment extends Fragment implements View.OnClickListener{

    EditText et_password;
    EditText et_confirmpassword;
    LinearLayout picturebtn;
    Button bottombtn;
    String role;
    ImageView check;
    String imageurl;
    String pathOfImage1;
    boolean ischeck=false;
    String notifictaion="0";
    private static final int SELECT_PICTURE = 8;
    private static final int SELECT_PICTURE_Camera = 9;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_step3, container, false);

        et_password = (EditText) view.findViewById(R.id.et_password);

        et_confirmpassword = (EditText) view.findViewById(R.id.et_confirmpassword);

        picturebtn=(LinearLayout)view.findViewById(R.id.picturebtn);
        picturebtn.setOnClickListener(this);
        String ishow=getArguments().getString("isshow");
        check=(ImageView)view.findViewById(R.id.check);
        check.setOnClickListener(this);
        bottombtn=(Button)view.findViewById(R.id.bottombtn);
        bottombtn.setOnClickListener(this);
        if(ishow.equalsIgnoreCase("0"))
        {

            role="user";
            picturebtn.setVisibility(View.GONE);
        }
        else
        {
            picturebtn.setVisibility(View.VISIBLE);
            role="priest";

        }



        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        InputStream istream = null;
        Bitmap map = null;
        String imgname = "userpic" + ".jpg";
        String path1 = getActivity().getFilesDir().toString();

        if(requestCode==SELECT_PICTURE||requestCode==SELECT_PICTURE_Camera)
        {
            if (resultCode == Activity.RESULT_OK) {
                try {

                    Uri photo ;
                    String path=" ";

                    if(data.getData()==null)
                    {

                        photo= getImageUri(getActivity(), (Bitmap)data.getExtras().get("data"));
                    }
                    else
                    {

                        photo = data.getData();

                    }

                    if(requestCode==SELECT_PICTURE)
                    {
                        path=getPath(getActivity(),photo);

                    }
                    else
                    {
                        path=getPath(getActivity(), photo);


                    }

                    istream = getActivity().getContentResolver()
                            .openInputStream(photo);


                    //    int oritation = getOrientation(photo);
                    //  System.out.println("file orietation: " + oritation);
                    Matrix matrix = new Matrix();


                    Bitmap map1 = BitmapFactory.decodeStream(istream);

                    // map= Utility.decodeSampledBitmapFromInputStream(istream,
                    // 300,300);
                    map1 = Bitmap.createScaledBitmap(map1, 400, 400, true);

                    map1 = Bitmap.createBitmap(map1, 0, 0, 400, 400, matrix,
                            true);


                    pathOfImage1=path;

                    S3Example s = new S3Example(pathOfImage1);
                    s.execute();

                } catch (Exception e) {
                    e.printStackTrace();

                }

            }


        }
    }




    @Override
    public void onResume() {
        super.onResume();

        //    handleOnTouchSurname();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                break;
            case R.id.ForgotPasswordlabel:

                break;

            case R.id.signUplabel:
                ((MainActivity)getActivity()).loadFragment(new SignUpStep1Fragment(), true);
                break;
            case R.id.picturebtn:
                Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");

                startActivityForResult(intent2, SELECT_PICTURE_Camera);
                break;

            case R.id.check:
                if(ischeck)
                {
                    check.setImageResource(R.drawable.checkbox);
                    ischeck=false;
                    notifictaion="0";
                }
                else
                {
                    check.setImageResource(R.drawable.checked);
                    ischeck=true;
                    notifictaion="1";

                }

                break;


            case R.id.bottombtn:

                if(validate()) {
                    Call<ResponseBody> oathAPICall = AppApiController.getApiInstance().SignUp(role, getArguments().getString("email"), et_password.getText().toString(), getArguments().getString("password"), getArguments().getString("phon"),imageurl,notifictaion,"true", getArguments().getString("surname"));

                    ((MainActivity) getActivity()).showDialog();
                    oathAPICall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            ((MainActivity) getActivity()).hideDialog();
                            if (response.isSuccessful()) {
                                DialogUtility.showDialog(getActivity(), "Message", "Success.");
                            } else {
                                DialogUtility.showDialog(getActivity(), "Message", "Error.");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            ((MainActivity) getActivity()).hideDialog();
                            DialogUtility.showDialog(getActivity(), "Message", "Error.");
                        }
                    });
                    GeneralUtility.hideKeyBoard(getActivity());

                }
                break;


        }


    }

    public boolean validate()
    {
        if(et_password.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(getActivity(),"Please enter a password",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!et_confirmpassword.getText().toString().equalsIgnoreCase(et_password.getText().toString()))
        {
            Toast.makeText(getActivity(),"Password miss match",Toast.LENGTH_LONG).show();
            return false;


        }
        return true;
    }

    private class S3Example extends AsyncTask<Void,Void,Void> {

        String filePath;
        S3Example(String filePath){
            this.filePath = filePath;
            ((MainActivity)getActivity()).showDialog();

        }
        @Override
        protected Void doInBackground(Void... params) {
            URL url = null;
            String urlStr = "";
            try {
                AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials(GeneralUtility.AWS_ACCESS_KEY_ID, GeneralUtility.AWS_SECRET_ACCESS_ID));
                s3Client.createBucket(GeneralUtility.MY_PICTURE_BUCKET);
                String pictureName="";

                filePath = pathOfImage1;
                pictureName = "image"+"-"+System.currentTimeMillis()+"/id.jpg";
                PutObjectRequest por = new PutObjectRequest(GeneralUtility.MY_PICTURE_BUCKET, pictureName, new java.io.File(filePath));
                s3Client.putObject(por);

                ResponseHeaderOverrides override = new ResponseHeaderOverrides();
                override.setContentType("image/jpeg");

                GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(GeneralUtility.MY_PICTURE_BUCKET, pictureName);
                urlRequest.setExpiration(new Date(System.currentTimeMillis() + (63115200 * 10)));  // Added an hour's worth of milliseconds to the current time.
                urlRequest.setResponseHeaders(override);

                url = s3Client.generatePresignedUrl(urlRequest);

                imageurl = url.toURI().toString();


            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void obj){
            super.onPostExecute(obj);

            ((MainActivity)getActivity()).hideDialog();
            //sendDataToServer();
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    @SuppressLint("NewApi")
    public static String getPath(Context context,Uri uri)
    {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;


    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


}
