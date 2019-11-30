package twitter.hashtags.core;

import java.util.ArrayList;

public interface RequestProcessor {

    public ArrayList getTweets(String expression, String resultType) throws Exception;

}
