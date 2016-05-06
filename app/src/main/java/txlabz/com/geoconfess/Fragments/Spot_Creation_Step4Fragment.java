package txlabz.com.geoconfess.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import txlabz.com.geoconfess.DialogUtility;
import txlabz.com.geoconfess.HomeActivity;
import txlabz.com.geoconfess.R;

/**
 * Created by arslan on 5/5/2016.
 */
public class Spot_Creation_Step4Fragment  extends Fragment implements View.OnClickListener {


    TextView fromhour,fromminute,tohour,tominute;
    static TextView date;
    Dialog dialog;
    Button btn_tostep5;
    ImageView drop1,drop2,drop3,drop4;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spotcreation_step4, container, false);


        drop1=(ImageView)view.findViewById(R.id.drop1);
        drop2=(ImageView)view.findViewById(R.id.drop2);
        drop3=(ImageView)view.findViewById(R.id.drop3);
        drop4=(ImageView)view.findViewById(R.id.drop4);

        drop1.setOnClickListener(this);
        drop2.setOnClickListener(this);
        drop3.setOnClickListener(this);
        drop4.setOnClickListener(this);

        fromhour=(TextView)view.findViewById(R.id.fromhour);
        fromminute=(TextView)view.findViewById(R.id.fromminute);
        tominute=(TextView)view.findViewById(R.id.tominute);
        tohour=(TextView)view.findViewById(R.id.tohour);
        date=(TextView)view.findViewById(R.id.date);
        date.setOnClickListener(this);
        btn_tostep5=(Button)view.findViewById(R.id.btn_tostep5);
        fromhour.setOnClickListener(this);
        fromminute.setOnClickListener(this);
        tohour.setOnClickListener(this);
        tominute.setOnClickListener(this);
        btn_tostep5.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fromhour:
                gethour(fromhour);
                break;
            case R.id.drop1:
                gethour(fromhour);
                break;

            case R.id.fromminute:
                getminute(fromminute);
                break;
            case R.id.drop2:
                getminute(fromminute);
                break;


            case R.id.tominute:
                getminute(tominute);
                break;

            case R.id.drop3:
                getminute(tominute);
                break;

            case R.id.tohour:
                gethour(tohour);
                break;
            case R.id.drop4:
                gethour(tohour);
                break;


            case R.id.date:
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
                break;
            case R.id.btn_tostep5:
                Spot_Creation_Step5Fragment fragment=new Spot_Creation_Step5Fragment();
                ((HomeActivity) getActivity()).loadFragment(fragment,true,false);
                break;


        }
    }


    public void gethour(final TextView hour)
    {
        ListView list=new ListView(getActivity());

        final String[] stringArray = new String[] { "1", "2","3","4","5","6","7","8","9","10","11","12" };

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hour.setText(stringArray[i]);
                dialog.dismiss();
            }
        });

        dialog= DialogUtility.showHourdialog(getActivity(), list, stringArray);



    }

    private void getminute(final TextView minute)
    {

        ListView list2=new ListView(getActivity());
        final   List<String>count=new ArrayList<>();

        for(int x=0;x<60;x++)
        {
            count.add(String.valueOf(x+1));

        }

        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                minute.setText(count.get(i));
                dialog.dismiss();
            }
        });

        dialog= DialogUtility.showMintdialog(getActivity(),list2,count);

    }

    public static class  SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day) {
            date.setText(month+"/"+day+"/"+year);
        }

    }

}
