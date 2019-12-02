package twitter.hashtags.core;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import twitter.hashtags.exception.ValidationException;
import twitter.hashtags.manager.HttpConnectionManager;
import twitter.hashtags.models.Tweet;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestProcessorTest {

    @Mock
    HttpConnectionManager httpConnectionManager;

    @InjectMocks
    RequestProcessor requestProcessor;

    JSONObject jsonObject;

    String jsonResponse = "{" +
            "    \"statuses\" : [" +
            "        {" +
            "            \"text\" : \"YOLO\"" +
            "        }," +
            "        {" +
            "            \"text\" : \"Whoops\"" +
            "        }" +
            "    ]" +
            "}";
            

    @Before
    public void setup() throws Exception{
        Field twitterAccessToken = requestProcessor.getClass().getDeclaredField("twitterAccessToken");
        twitterAccessToken.setAccessible(true);
        twitterAccessToken.set(requestProcessor, "abcd");

         jsonObject = new JSONObject(jsonResponse);
    }

    @Test
    public void testValidateException() throws Exception{
        assertThrows(ValidationException.class, () -> {
            requestProcessor.getTweets("abc", "abc");
        });
    }

    @Ignore("WIP : Mock object returning null instead of itself, need to figure out why")
    @Test
    public void testgetTweets() throws Exception{
        List<Tweet> tweets =  requestProcessor.getTweets("abc", "mixed");
        tweets.forEach(e -> System.out.println(e.getTweet()));

        doReturn(httpConnectionManager).when(httpConnectionManager.buildUrl(any(),any(),any()));
        when(httpConnectionManager.buildRequest(any(),any(),null)).thenReturn(httpConnectionManager);
        when(httpConnectionManager.fetchJSONResponse(any())).thenReturn(jsonObject);
    }

}
