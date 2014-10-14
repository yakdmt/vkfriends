package com.stereo23.vkfriends;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.stereo23.vkfriends.util.PersonAdapter;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener{

    SearchView mSearchView;
    TextView mStatusView;
    public GetFriendsTaskResponse delegate;
    ListView listView;
    String currentQuery=null;
    PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusView = (TextView) findViewById(R.id.status_text);
        listView = (ListView) findViewById(R.id.list_view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchview_in_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        setupSearchView(searchItem);
        if (currentQuery!=null){
            Log.d("abc","in onCreateOpt currentQuery="+currentQuery);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate = new GetFriendsTaskResponse(){
            @Override
            public void onSearchComplete(ArrayList<Person> output) {
                if (output!=null){
                    adapter = new PersonAdapter(MainActivity.this, output);
                    listView.setAdapter(adapter);
                } else {
                    mStatusView.setText(getString(R.string.no_user_found));
                    //Toast.makeText(MainActivity.this,getString(R.string.no_user_found),Toast.LENGTH_LONG).show();
                }
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int userId = ((Person)listView.getAdapter().getItem(position)).getId();
                mStatusView.setText("UserID = " + userId);
                new GetFriendsTask(MainActivity.this, delegate).execute(userId);
            }
        });
        adapter = (PersonAdapter) getLastCustomNonConfigurationInstance(); // not work, adapter is always null;
        listView.setAdapter(adapter);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("abc","onsave"+currentQuery);
        outState.putString("query",currentQuery);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getString("query")!=null){
            currentQuery=savedInstanceState.getString("query");
        }
    }
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return adapter;
    }
    private void setupSearchView(MenuItem searchItem) {
        if (isAlwaysExpanded()) {
            mSearchView.setIconifiedByDefault(false);
        } else {
            MenuItemCompat.setShowAsAction(searchItem, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM
                    | MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }
        mSearchView.setOnQueryTextListener(MainActivity.this);
        if (currentQuery!=null){
            //MenuItemCompat.expandActionView(searchItem);
            mSearchView.setQuery(currentQuery,true);
        }
    }
    public boolean onQueryTextChange(String newText) {
        currentQuery = newText;
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        int userId = 0;
        try {
            userId = Integer.parseInt(query);
        }
        catch (NumberFormatException e){
            Toast.makeText(this,getString(R.string.please_enter),Toast.LENGTH_LONG).show();
        }
        if (userId>0) {
            new GetFriendsTask(this, delegate).execute(userId);
            mStatusView.setText("UserID = " + query);
        }
        return false;
    }

    protected boolean isAlwaysExpanded() {
        return true;
    }


}
