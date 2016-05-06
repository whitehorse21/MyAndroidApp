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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import txlabz.com.geoconfess.DialogUtility;
import txlabz.com.geoconfess.R;

/**
 * Created by arslan on 5/5/2016.
 */
public class Spot_Creation_Step5Fragment  extends Fragment implements View.OnClickListener {

    private TextView fromhour,fromminute,tohour,tominute;
    private Dialog dialog;
    private ImageView check1,check2,check3,check4,check5,check6,check7;
    private  Boolean Bcheck1=false,Bcheck2=false,Bcheck3=false,Bcheck4=false,Bcheck5=false,Bcheck6=false,Bcheck7=false;
    ImageView drop1,drop2,drop3,drop4;

    private LinearLayout checkspace1,checkspace2,checkspace3,checkspace4,checkspace5,checkspace6,checkspace7;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spotcreation_step5, container, false);



        drop1=(ImageView)view.findViewById(R.id.drop1);
        drop2=(ImageView)view.findViewById(R.id.drop2);
        drop3=(ImageView)view.findViewById(R.id.drop3);
        drop4=(ImageView)view.findViewById(R.id.drop4);

        drop1.setOnClickListener(this);
        drop2.setOnClickListener(this);
        drop3.setOnClickListener(this);
        drop4.setOnClickListener(this);


        check1=(ImageView)view.findViewById(R.id.check1);
        check2=(ImageView)view.findViewById(R.id.check2);
        check3=(ImageView)view.findViewById(R.id.check3);
        check4=(ImageView)view.findViewById(R.id.check4);
        check5=(ImageView)view.findViewById(R.id.check5);
        check6=(ImageView)view.findViewById(R.id.check6);
        check7=(ImageView)view.findViewById(R.id.check7);
        checkspace1=(LinearLayout)view.findViewById(R.id.checkspace1);
        checkspace2=(LinearLayout)view.findViewById(R.id.checkspace2);
        checkspace3=(LinearLayout)view.findViewById(R.id.checkspace3);
        checkspace4=(LinearLayout)view.findViewById(R.id.checkspace4);
        checkspace5=(LinearLayout)view.findViewById(R.id.checkspace5);
        checkspace6=(LinearLayout)view.findViewById(R.id.checkspace6);
        checkspace7=(LinearLayout)view.findViewById(R.id.checkspace7);

        fromhour=(TextView)view.findViewById(R.id.fromhour);
        fromminute=(TextView)view.findViewById(R.id.fromminute);
        tominute=(TextView)view.findViewById(R.id.tominute);
        tohour=(TextView)view.findViewById(R.id.tohour);
        fromhour.setOnClickListener(this);
        fromminute.setOnClickListener(this);
        tohour.setOnClickListener(this);
        tominute.setOnClickListener(this);
        checkspace1.setOnClickListener(this);
        checkspace2.setOnClickListener(this);
        checkspace3.setOnClickListener(this);
        checkspace4.setOnClickListener(this);
        checkspace5.setOnClickListener(this);
        checkspace6.setOnClickListener(this);
        checkspace7.setOnClickListener(this);

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

            case R.id.checkspace1:
                if(Bcheck1)
                {
                    check1.setImageResource(R.drawable.checkbox);
                    Bcheck1=false;
                }
                else
                {
                    check1.setImageResource(R.drawable.checked);
                    Bcheck1=true;

                }
                break;
            case R.id.checkspace2:

                if(Bcheck2)
                {
                    check2.setImageResource(R.drawable.checkbox);
                    Bcheck2=false;
                }
                else
                {
                    check2.setImageResource(R.drawable.checked);
                    Bcheck2=true;

                }


                break;
            case R.id.checkspace3:
                if(Bcheck3)
                {
                    check3.setImageResource(R.drawable.checkbox);
                    Bcheck3=false;
                }
                else
                {
                    check3.setImageResource(R.drawable.checked);
                    Bcheck3=true;

                }



                break;
            case R.id.checkspace4:
                if(Bcheck4)
                {
                    check4.setImageResource(R.drawable.checkbox);
                    Bcheck4=false;
                }
                else
                {
                    check4.setImageResource(R.drawable.checked);
                    Bcheck4=true;

                }


                break;
            case R.id.checkspace5:

                if(Bcheck5)
                {
                    check5.setImageResource(R.drawable.checkbox);
                    Bcheck5=false;
                }
                else
                {
                    check5.setImageResource(R.drawable.checked);
                    Bcheck5=true;

                }


                break;

            case R.id.checkspace6:
                if(Bcheck6)
                {
                    check6.setImageResource(R.drawable.checkbox);
                    Bcheck6=false;
                }
                else
                {
                    check6.setImageResource(R.drawable.checked);
                    Bcheck6=true;

                }

                break;

            case R.id.checkspace7:
                if(Bcheck7)
                {
                    check7.setImageResource(R.drawable.checkbox);
                    Bcheck7=false;
                }
                else
                {
                    check7.setImageResource(R.drawable.checked);
                    Bcheck7=true;

                }

                break;



        }
    }


    private void getminute(final TextView minute)
    {

        ListView list2=new ListView(getActivity());
        final List<String> count=new ArrayList<>();

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

        dialog= DialogUtility.showMintdialog(getActivity(), list2, count);

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



}
