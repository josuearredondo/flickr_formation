package com.josue.flickr;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FlickrServiceListener {
    private FlickrService boundService;
    boolean bound = false;
    ListView listView;
    FloatingActionButton fab;
    Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText editSearch;
    EditText editText;
    Context context;

    AdapterFlickr adapter;
    List<FlickrObjet> listFlickr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        editText = (EditText) findViewById(R.id.input_edit_text);
        adapter = new AdapterFlickr (this);
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
    }
    private void doSearch() {
        if (bound && !editSearch.getText().toString().equals("")) {
            boundService.getRetrofitFlickr(editSearch.getText().toString());
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, FlickrService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        context = this;
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FlickrService.ServiceBinder binder = (FlickrService.ServiceBinder) service;
            boundService = binder.getService();
            boundService.setFlickrServiceListener(MainActivity.this);
            boundService.setContext(context);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    public void addListenerOnButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!editText.getText().toString().equals("")) {
                    if (bound) {
                        boundService.getRetrofitFlickr(editText.getText().toString());
                    }
                    try  {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {

                    }
                } else {
                    try  {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    @Override
    public void onResponseListener(List<FlickrObjet> flickrObjetList) {
        adapter.setListImages(flickrObjetList);
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(android.R.drawable.ic_menu_search);

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            editSearch = (EditText)action.getCustomView().findViewById(R.id.editSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            editSearch.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editSearch, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(android.R.drawable.ic_menu_close_clear_cancel);

            isSearchOpened = true;
        }
    }
    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }


}
