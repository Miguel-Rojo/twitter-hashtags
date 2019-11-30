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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:application.properties")
public class RequestProcessorImpl implements RequestProcessor{

    @Autowired
    private HttpConnectionManager httpConnectionManager;

    @Value("${twitter.access.token}")
    private String twitterAccessToken;


    public ArrayList getTweets(String expression, String resultType) throws Exception{

        validateInput(resultType);

        ArrayList<Tweet> tweets = new ArrayList<>();

        Map<String, String>queryParams = new HashMap<String, String>(){{
            put(TwitterConstants.BASE_QUERY_PARAMETER,expression);
            put(TwitterConstants.RESULT_TYPE_QUERY_PARAMETER, resultType);
        }};

        ArrayList<Integer> expectedResponseCodes = new ArrayList<Integer>(){{
            add(HttpStatus.OK.value());
        }};

        JSONObject response  = httpConnectionManager
                .buildUrl(TwitterConstants.BASE_URL,TwitterConstants.SEARCH_API_PATH,queryParams)
                .buildRequest(twitterAccessToken, HttpMethod.GET.toString(), null)
                .fetchJSONResponse(expectedResponseCodes);

        JSONArray statusArray = response.getJSONArray("statuses");

        statusArray.forEach(item -> {
            JSONObject obj = (JSONObject)item;              //org.json uses raw iterator, hence type conversion is required
            Tweet tweet = new Tweet(obj.getString("text"));
            tweets.add(tweet);
        });

        return tweets;
    }

    private void validateInput(String resultType){
        List<String> resultTypes = new ArrayList<String>(){{
                add("mixed");
                add("recent");
                add("popular");
        }};

        if(!resultTypes.contains(resultType))
            throw new ValidationException(
                    String.format("Invalid resultType \'%s\'. " +
                            "Please provide one from following" +
                            " : mixed,recent,popular",resultType));
    }
}
