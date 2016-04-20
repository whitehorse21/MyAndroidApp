package txlabz.com.geoconfess;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import txlabz.com.geoconfess.web.AppApiController;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username;
    EditText password;
    Button loginButton;
    View usernameDivider;
    View passwordDivider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);

        usernameDivider = findViewById(R.id.usernameDivider);
        passwordDivider = findViewById(R.id.passwordDivider);

        loginButton.setOnClickListener(this);

        setOnTouchListenerForPassword(password);
        setOnTouchListenerForUserName(username);
        handleOnTouchUserName();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                Call<ResponseBody> oathAPICall = AppApiController.getApiInstance().oathToken("password", username.getText().toString(), password.getText().toString(),
                        "android", "3kjh123iu42i314g123");
                oathAPICall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            DialogUtility.showDialog(LoginActivity.this, "Message", "Success.");
                        } else {
                            DialogUtility.showDialog(LoginActivity.this, "Message", "Error.");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        DialogUtility.showDialog(LoginActivity.this, "Message", "Error.");
                    }
                });
                GeneralUtility.hideKeyBoard(this);
                break;
        }
    }


    private void setOnTouchListenerForPassword(final EditText edit_Text) {
        edit_Text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    handleOnTouchPassword();
                }
                return false;
            }
        });
    }

    private void setOnTouchListenerForUserName(final EditText edit_Text) {
        edit_Text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    handleOnTouchUserName();
                }
                return false;
            }
        });
    }

    private void handleOnTouchUserName() {
        username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_identification_on, 0, 0, 0);
        password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_password_off, 0, 0, 0);

        password.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));
        username.setHintTextColor(getResources().getColor(R.color.white));

        username.setTextColor(getResources().getColor(R.color.white));
        password.setTextColor(getResources().getColor(R.color.colorGreyTextHint));

        passwordDivider.setVisibility(View.VISIBLE);
        usernameDivider.setVisibility(View.GONE);
    }

    private void handleOnTouchPassword() {
        password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_password_on, 0, 0, 0);
        username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_identification_off, 0, 0, 0);

        password.setHintTextColor(getResources().getColor(R.color.white));
        username.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));

        username.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        password.setTextColor(getResources().getColor(R.color.white));

        passwordDivider.setVisibility(View.GONE);
        usernameDivider.setVisibility(View.VISIBLE);
    }
}

