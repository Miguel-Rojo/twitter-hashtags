package twitter.hashtags.core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import twitter.hashtags.constants.TwitterConstants;
import twitter.hashtags.exception.ValidationException;
import twitter.hashtags.manager.HttpConnectionManager;
import twitter.hashtags.models.Tweet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:application.properties")
public class RequestProcessor {

    @Autowired
    private HttpConnectionManager httpConnectionManager;

    @Value("${twitter.access.token}")
    private String twitterAccessToken;

    public ArrayList getTweets(String expression, String resultType) throws IOException {
        validateInput(resultType);
        JSONObject response = fetchResponse(expression, resultType);
        return parseOutput(response);
    }

    private ArrayList parseOutput(JSONObject response) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        if (!response.has("statuses"))
            return tweets;

        JSONArray statusArray = response.getJSONArray("statuses");

        statusArray.forEach(item -> {
            JSONObject obj = (JSONObject) item;              //org.json uses raw iterator, hence type conversion is required
            Tweet tweet = new Tweet(obj.getString("full_text"));
            tweets.add(tweet);
        });

        return tweets;
    }

    private JSONObject fetchResponse(String expression, String resultType) throws IOException {
        Map<String, String> queryParams = new HashMap<String, String>() {{
            put(TwitterConstants.BASE_QUERY_PARAMETER, expression);
            put(TwitterConstants.RESULT_TYPE_QUERY_PARAMETER, resultType);
            put(TwitterConstants.TWEET_MODE_QUERY_PARAMETER, TwitterConstants.EXTENDED_TWEET_MODE);
        }};

        ArrayList<Integer> expectedResponseCodes = new ArrayList<Integer>() {{
            add(HttpStatus.OK.value());
        }};

        return httpConnectionManager
                .buildUrl(TwitterConstants.BASE_URL, TwitterConstants.SEARCH_API_PATH, queryParams)
                .buildRequest(twitterAccessToken, HttpMethod.GET.toString(), null)
                .fetchJSONResponse(expectedResponseCodes);

    }

    private void validateInput(String resultType) {
        List<String> resultTypes = new ArrayList<String>() {{
            add("mixed");
            add("recent");
            add("popular");
        }};

        if (!resultTypes.contains(resultType))
            throw new ValidationException(
                    String.format("Invalid resultType \'%s\'. " +
                            "Please provide one from following" +
                            " : mixed,recent,popular", resultType));
    }

}
