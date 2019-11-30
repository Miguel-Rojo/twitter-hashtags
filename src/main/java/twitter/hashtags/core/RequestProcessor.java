package twitter.hashtags.core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter.hashtags.manager.TwitterConnectionManager;
import twitter.hashtags.models.Tweet;

import java.util.ArrayList;

@Component
public class RequestProcessor {

    @Autowired
    private TwitterConnectionManager twitterConnectionManager;

    public ArrayList getTweets(String expression) throws Exception{

        ArrayList<Tweet> tweets = new ArrayList<>();

        JSONObject response  = twitterConnectionManager.get(expression);
        JSONArray statusArray = response.getJSONArray("statuses");

        statusArray.forEach(item -> {
            JSONObject obj = (JSONObject)item;
            Tweet tweet = new Tweet(obj.getString("text"));
            tweets.add(tweet);
        });

        tweets.forEach(System.out::println);
        return tweets;
    }
}
