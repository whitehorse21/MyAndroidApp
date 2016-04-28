package txlabz.com.geoconfess.Fragments;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
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

import org.w3c.dom.Text;

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
public class ForgotPassword extends Fragment implements View.OnClickListener{

    Button confirmButton;
    boolean ischeck=false;
    String notifictaion="0";
    EditText username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.forgot_password, container, false);

        username = (EditText) view.findViewById(R.id.username);

        confirmButton=(Button)view.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        handleOnTouchUserName();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.confirmButton:

                if(validate()) {
                    Call<ResponseBody> oathAPICall = AppApiController.getApiInstance().forgot(username.getText().toString());

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
        if(username.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(getActivity(),"Please enter an Adresse email",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private void handleOnTouchUserName() {

        username.setBackgroundResource(R.color.colorAccent);

        username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_identification_on, 0, 0, 0);

        username.setHintTextColor(getResources().getColor(R.color.white));

        username.setTextColor(getResources().getColor(R.color.white));
    }


}
