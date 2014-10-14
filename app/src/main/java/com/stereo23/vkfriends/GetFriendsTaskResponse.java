package com.stereo23.vkfriends;

import java.util.ArrayList;

/**
 * Created by Username on 13.10.2014.
 */
public interface GetFriendsTaskResponse {
    void onSearchComplete(ArrayList<Person> output);
}
