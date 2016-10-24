package com.josue.flickr;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DetailFragment extends Fragment {
    public Context context;
    private ProgressDialog dialog;
    //Interface via which we communicate to hosting Activity
    private FragmentDetailCommunicator fragmentDetailCommunicator;
    private String activityAssignedValue ="";
    private static final String STRING_VALUE ="stringValue";
    //As per Android Fragment documentation an empty constructor
    public DetailFragment(){
    }
    //Since Fragment is Activity dependent we need Activity context in various cases
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
        fragmentDetailCommunicator =(FragmentDetailCommunicator)context;
    }
    //Now on the entire fragment I use context rather than getActivity()
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null)
            activityAssignedValue = savedInstanceState.getString(STRING_VALUE);
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(STRING_VALUE,activityAssignedValue);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }
    public void init() {
        dialog = new ProgressDialog(context);
        dialog.setCancelable(true);
        //Communicating to activity via ActivityCommunicator interface

        View.OnClickListener myClickButtonListener= new View.OnClickListener() {
            public void onClick(View v) {
                //String tag = (String) v.getTag();
                //fragmentDetailCommunicator.passDataToDetailFragment("","");
            }
        };
        //btnSIN.setOnClickListener(myClickButtonListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,
                container, false);

        //btnSIN = (Button) view.findViewById(R.id.btnSIN);
        //btnSIN.setTag("sin");

        setRetainInstance(true);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}