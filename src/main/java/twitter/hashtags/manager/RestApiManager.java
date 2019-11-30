package twitter.hashtags.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import twitter.hashtags.core.RequestProcessor;

import java.util.ArrayList;

@RestController
public class RestApiManager {

    @Autowired
    private RequestProcessor requestProcessor;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping(value = "/tweets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList getTweets(@RequestParam String source) throws Exception{
        return requestProcessor.getTweets(source);
    }
}
