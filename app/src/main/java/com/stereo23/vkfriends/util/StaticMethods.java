package com.stereo23.vkfriends.util;

import com.stereo23.vkfriends.Person;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Username on 14.10.2014.
 */
public class StaticMethods {
    public static String streamToString(InputStream inputStream){
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }
    public static ArrayList<Person> parseJSON(JSONArray jsonArray){
        ArrayList<Person> list = new ArrayList<Person>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                int id = jsonArray.getJSONObject(i).getInt("id");
                String firstName = jsonArray.getJSONObject(i).getString("first_name");
                String lastName = jsonArray.getJSONObject(i).getString("last_name");
                Person friend = new Person(id,firstName,lastName);
                list.add(friend);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
