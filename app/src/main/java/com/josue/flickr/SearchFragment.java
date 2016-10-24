package com.josue.flickr;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class SearchFragment extends Fragment implements FlickrServiceListener {
    private ProgressDialog dialog;
    private FlickrService boundService;
    boolean bound = false;
    ListView listView;
    Button searchButton;
    EditText editText;
    Context context;

    AdapterFlickr adapter;
    List<FlickrObjet> listFlickr;

    //Interface via which we communicate to hosting Activity
    private FragmentDetailCommunicator fragmentDetailCommunicator;
    private String activityAssignedValue ="";
    private static final String STRING_VALUE ="stringValue";
    //As per Android Fragment documentation an empty constructor
    public SearchFragment(){
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
                String tag = (String) v.getTag();
                fragmentDetailCommunicator.passDataToDetailFragment("Title", "URL");
            }
        };
        //btnDiv.setOnClickListener(myClickButtonListener);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,
                container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        searchButton = (Button) view.findViewById(R.id.buttonChange);
        editText = (EditText) view.findViewById(R.id.input_edit_text);
        adapter = new AdapterFlickr (view.getContext());
        listView.setAdapter(adapter);
        addListenerOnButton();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (bound && !editText.getText().toString().equals("")) {
                    boundService.getRetrofitFlickr(editText.getText().toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //btnDiv = (Button) view.findViewById(R.id.btnDiv);
        //btnDiv.setTag("/");

        setRetainInstance(true);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(context, FlickrService.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bound) {
            context.unbindService(connection);
            bound = false;
        }
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FlickrService.ServiceBinder binder = (FlickrService.ServiceBinder) service;
            boundService = binder.getService();
            boundService.setFlickrServiceListener(SearchFragment.this);
            boundService.setContext(context);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    public void addListenerOnButton() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (bound) {
                    boundService.getRetrofitFlickr(editText.getText().toString());
                }
            }
        });
    }

    @Override
    public void onResponseListener(List<FlickrObjet> flickrObjetList) {
        //Log.e("response", String.valueOf(flickrObjetList.size()));
        adapter.setListImages(flickrObjetList);
    }
}