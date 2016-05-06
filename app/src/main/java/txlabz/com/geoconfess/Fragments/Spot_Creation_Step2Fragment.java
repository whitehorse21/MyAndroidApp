package txlabz.com.geoconfess.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;

import txlabz.com.geoconfess.Adapters.Spot_List_Adapter;
import txlabz.com.geoconfess.HomeActivity;
import txlabz.com.geoconfess.R;
import txlabz.com.geoconfess.Utils;

/**
 * Created by arslan on 5/4/2016.
 */
public class Spot_Creation_Step2Fragment extends Fragment implements View.OnClickListener {
    private RecyclerView mrecyclerView;
    private Utils utils;
    private ArrayList<HashMap<String,String>> list;
    private LinearLayout bottombtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spotcreationlayout, container, false);

        mrecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        list=new ArrayList<>();
        mrecyclerView.setLayoutManager(linearLayoutManager);
        bottombtn=(LinearLayout)view.findViewById(R.id.bottombtn);
        bottombtn.setOnClickListener(this);
        HashMap<String,String>map=new HashMap<>();
        list.add(map);

        HashMap<String,String>map2=new HashMap<>();
        list.add(map2);

        Spot_List_Adapter adp=new Spot_List_Adapter(list);

        mrecyclerView.setAdapter(adp);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottombtn:
                Spot_Creation_Step3Fragment f=new Spot_Creation_Step3Fragment();
                ((HomeActivity) getActivity()).loadFragment(f,true,false);
                break;
            case R.id.btn_priest:

                break;


        }
    }

}
