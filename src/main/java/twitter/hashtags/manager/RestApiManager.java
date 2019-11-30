package twitter.hashtags.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter.hashtags.core.RequestProcessor;

import java.util.ArrayList;

@RestController
public class RestApiManager {

    @Autowired
    private RequestProcessor requestProcessorImpl;


    @GetMapping(value = "/tweets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList getTweets(
            @RequestParam(name = "source") String source,
            @RequestParam(name = "resultType", defaultValue = "mixed") String resultType
            ) throws Exception{
        return requestProcessorImpl.getTweets(source, resultType);
    }
}
