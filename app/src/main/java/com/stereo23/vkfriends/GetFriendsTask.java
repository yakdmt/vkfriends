package com.stereo23.vkfriends;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.stereo23.vkfriends.util.Constants;
import com.stereo23.vkfriends.util.StaticMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Username on 13.10.2014.
 */
public class GetFriendsTask extends AsyncTask<Integer, Void, ArrayList<Person>> {
    ProgressDialog progress;
    Context context;
    public GetFriendsTaskResponse delegate;

    public GetFriendsTask (Context _context, GetFriendsTaskResponse _delegate) {
        this.context = _context;
        this.delegate = _delegate;
    }
    @Override
    protected void onPreExecute(){
        progress = ProgressDialog.show(context, context.getString(R.string.search),
                context.getString(R.string.please_wait), true);
    }


    @Override
    protected ArrayList<Person> doInBackground(Integer... params) {
        String urlString = Constants.APIURL+Constants.METHOD+params[0]+Constants.FIELDS+Constants.VERSION;
        ArrayList<Person> friendsList = null;
        try {
            URL url = new URL(urlString);
            InputStream inputStream = url.openConnection().getInputStream();
            String response = StaticMethods.streamToString(inputStream);
            JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
            JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("items");
            friendsList = StaticMethods.parseJSON(jsonArray);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friendsList;
    }

    @Override
    protected void onPostExecute(ArrayList<Person> result){
        progress.dismiss();
        delegate.onSearchComplete(result);
    }
}

