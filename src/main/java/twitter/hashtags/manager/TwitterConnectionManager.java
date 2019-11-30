package twitter.hashtags.manager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import twitter.hashtags.constants.TwitterConstants;

import java.nio.charset.StandardCharsets;

@Component
@PropertySource("classpath:application.properties")
public class TwitterConnectionManager {

    private String userToken;
    private OkHttpClient okHttpClient;

    @Autowired
    public TwitterConnectionManager(Environment environment){
        userToken = environment.getProperty("access.token");
        okHttpClient = new OkHttpClient.Builder().build();
    }

    public JSONObject get(String expression) throws Exception{

        String url = uriBuilder(expression);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization" , "Bearer " + userToken)
                .get()
                .build();

        Response response = null;
        response = okHttpClient.newCall(request).execute();
        System.out.print(response.code());

        JSONObject responsePayloadObject = new JSONObject(response.body().string());


        return responsePayloadObject;
    }

    private String uriBuilder (String expression){
        expression = UriUtils.encode(expression, StandardCharsets.UTF_8);

        return UriComponentsBuilder
                .fromUriString(TwitterConstants.BASE_URL)
                .path(TwitterConstants.SEARCH_API_PATH)
                .queryParam(TwitterConstants.QUERY_PARAMETER,expression)
                .build().toString();
    }

}
