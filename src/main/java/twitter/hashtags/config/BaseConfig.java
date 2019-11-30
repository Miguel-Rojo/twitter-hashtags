package twitter.hashtags.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import twitter.hashtags.manager.TwitterConnectionManager;

//@Configuration
//@ComponentScan("twitter.hashtags")
//@PropertySource("classpath:application.properties")
public class BaseConfig {

    //@Autowired
    private Environment environment;

    //@Bean
    /*public TwitterConnectionManager getTwitterConnectionManager(){
        System.out.println(environment.getProperty("access.token"));
        return new TwitterConnectionManager(environment.getProperty("access.token"));
    }*/

}
