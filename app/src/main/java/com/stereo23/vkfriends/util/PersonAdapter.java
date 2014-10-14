package com.stereo23.vkfriends.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.stereo23.vkfriends.Person;
import com.stereo23.vkfriends.R;

import java.util.ArrayList;

/**
 * Created by Username on 14.10.2014.
 */
public class PersonAdapter extends BaseAdapter {
    Context context;
    ArrayList<Person> objects;

    public PersonAdapter(Context _context, ArrayList<Person> friends) {
        context = _context;
        objects = friends;
    }

    @Override
    public int getCount() {
        if (objects!=null){
            return objects.size();
        } else {
            return 0;
        }
    }

    @Override
    public Person getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.list_item, parent, false);
        }
        Person friend = getItem(position);
        TextView firstNameTextView = (TextView) view.findViewById(R.id.first_name);
        TextView lastNameTextView = (TextView) view.findViewById(R.id.last_name);
        firstNameTextView.setText(friend.getFirstName());
        lastNameTextView.setText(friend.getLastName());
        return view;
    }
}

