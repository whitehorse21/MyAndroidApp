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
public class SignUpStep2Fragment extends Fragment implements View.OnClickListener{

    EditText et_surname;
    EditText et_name;
    EditText et_email;
    EditText et_phon;

    Button loginButton;
    View surnameDivider;
    View emailDivider;
    View phonDivider;
    View nameDivider;

    TextView forgotPassword;
    TextView signUp;
    RelativeLayout activityRootView;
    Button bottombtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_step2, container, false);


        et_email = (EditText) view.findViewById(R.id.et_email);

        et_surname = (EditText) view.findViewById(R.id.et_surname);
        et_phon=(EditText)view.findViewById(R.id.et_phon);

        et_name = (EditText) view.findViewById(R.id.et_name);
        activityRootView =(RelativeLayout) view.findViewById(R.id.parentview);

        bottombtn=(Button)view.findViewById(R.id.bottombtn);
        bottombtn.setOnClickListener(this);
        nameDivider = view.findViewById(R.id.nameDivider);
        surnameDivider = view.findViewById(R.id.et_surnameDivider);
        emailDivider = view.findViewById(R.id.emailDivider);
        phonDivider = view.findViewById(R.id.phonDivider);


        setOnTouchListenerForEmail(et_email);
        setOnTouchListenerForName(et_name);
        setOnTouchListenerForPhone(et_phon);
        setOnTouchListenerForSurname(et_surname);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        handleOnTouchSurname();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                break;
            case R.id.ForgotPasswordlabel:

                break;

            case R.id.bottombtn:
                if(validation()) {
                    Bundle bundle=new Bundle();
                    bundle.putString("name",et_name.getText().toString());
                    bundle.putString("surname", et_surname.getText().toString());
                    bundle.putString("email", et_email.getText().toString());
                    bundle.putString("phon", et_phon.getText().toString());

                    bundle.putString("isshow", getArguments().getString("isshow"));


                    SignUpStep3Fragment fragment=new SignUpStep3Fragment();
                    fragment.setArguments(bundle);
                    ((MainActivity) getActivity()).loadFragment(fragment, true);
                }
                break;


            case R.id.signUplabel:
                ((MainActivity)getActivity()).loadFragment(new SignUpStep1Fragment(),true);
                break;
        }    }


    private void setOnTouchListenerForEmail(final EditText edit_Text) {
        edit_Text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    handleOnTouchEmail();
                }
                return false;
            }
        });
    }

    private void setOnTouchListenerForName(final EditText edit_Text) {
        edit_Text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    handleOnTouchName();
                }
                return false;
            }
        });
    }
    private void setOnTouchListenerForSurname(final EditText edit_Text) {
        edit_Text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    handleOnTouchSurname();   }
                return false;
            }
        });
    }
    private void setOnTouchListenerForPhone(final EditText edit_Text) {
        edit_Text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    handleOnTouchPhone();
                }
                return false;
            }
        });
    }

    private void handleOnTouchName() {

        et_phon.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_surname.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_email.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));

        et_name.setHintTextColor(getResources().getColor(R.color.white));
        et_name.setTextColor(getResources().getColor(R.color.white));
        et_phon.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_surname.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_email.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        surnameDivider.setVisibility(View.VISIBLE);
        emailDivider.setVisibility(View.VISIBLE);
        phonDivider.setVisibility(View.VISIBLE);

        nameDivider.setVisibility(View.GONE);

        et_name.setBackgroundResource(R.color.colorAccent);
        et_phon.setBackgroundResource(R.color.white);
        et_email.setBackgroundResource(R.color.white);
        et_surname.setBackgroundResource(R.color.white);



    }
    private void handleOnTouchSurname() {

        et_phon.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_name.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_email.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));

        et_surname.setHintTextColor(getResources().getColor(R.color.white));

        et_surname.setTextColor(getResources().getColor(R.color.white));
        et_phon.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_name.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_email.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        nameDivider.setVisibility(View.VISIBLE);
        emailDivider.setVisibility(View.VISIBLE);
        phonDivider.setVisibility(View.VISIBLE);

        surnameDivider.setVisibility(View.GONE);
        et_surname.setBackgroundResource(R.color.colorAccent);
        et_phon.setBackgroundResource(R.color.white);
        et_email.setBackgroundResource(R.color.white);
        et_name.setBackgroundResource(R.color.white);


    }
    private void handleOnTouchPhone() {

        et_surname.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_name.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_email.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));

        et_phon.setHintTextColor(getResources().getColor(R.color.white));

        et_phon.setTextColor(getResources().getColor(R.color.white));
        et_surname.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_name.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_email.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        nameDivider.setVisibility(View.VISIBLE);
        emailDivider.setVisibility(View.VISIBLE);
        surnameDivider.setVisibility(View.VISIBLE);

        phonDivider.setVisibility(View.GONE);

        et_phon.setBackgroundResource(R.color.colorAccent);
        et_surname.setBackgroundResource(R.color.white);
        et_email.setBackgroundResource(R.color.white);
        et_name.setBackgroundResource(R.color.white);

    }
    private void handleOnTouchEmail() {

        et_surname.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_name.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_phon.setHintTextColor(getResources().getColor(R.color.colorGreyTextHint));

        et_email.setHintTextColor(getResources().getColor(R.color.white));

        et_email.setTextColor(getResources().getColor(R.color.white));
        et_surname.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_name.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        et_phon.setTextColor(getResources().getColor(R.color.colorGreyTextHint));
        nameDivider.setVisibility(View.VISIBLE);
        phonDivider.setVisibility(View.VISIBLE);
        surnameDivider.setVisibility(View.VISIBLE);

        emailDivider.setVisibility(View.GONE);

        et_email.setBackgroundResource(R.color.colorAccent);
        et_surname.setBackgroundResource(R.color.white);
        et_phon.setBackgroundResource(R.color.white);
        et_name.setBackgroundResource(R.color.white);

    }

    private boolean validation() {

        if (et_surname.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter a SurName", Toast.LENGTH_LONG).show();
            return false;
        } else if (et_name.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter a Name", Toast.LENGTH_LONG).show();
            return false;
        } else if (et_email.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getActivity(), "Please enter an Email", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }



}
