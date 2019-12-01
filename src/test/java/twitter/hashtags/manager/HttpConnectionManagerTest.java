package twitter.hashtags.manager;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import twitter.hashtags.exception.HttpConnectionException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpConnectionManagerTest {

    @Mock
    OkHttpClient okHttpClient;
    @Mock
    Call call;

    @InjectMocks
    HttpConnectionManager httpConnectionManager;

    Response response;

    @Before
    public void setup() throws Exception{
        httpConnectionManager = new HttpConnectionManager();
        Field okHttpClient = httpConnectionManager.getClass().getDeclaredField("okHttpClient");
        okHttpClient.setAccessible(true);
        okHttpClient.set(httpConnectionManager, this.okHttpClient);

    }

    @Test
    public void testUrlBuilder() throws Exception{
        String expected = "https://base/path?param=%23qwerty&param2=%40nope";
        Map<String, String> queryParams = new HashMap<String, String>(){{
            put("param","#qwerty");
            put("param2","@nope");
        }};
        httpConnectionManager.buildUrl("https://base","path",queryParams);
        Field url = httpConnectionManager.getClass().getDeclaredField("url");
        url.setAccessible(true);
        String value = (String)url.get(httpConnectionManager);
        assertEquals(expected,value);
    }

    @Ignore("WIP : Figure out how to mock Response class")
    @Test
    public void testHttpConnectionException() throws Exception{

        response = mock(Response.class);

        List<Integer> expectedResponseCodes = new ArrayList<Integer>(){{
           add(200);
           add(202);
        }};
        when(okHttpClient.newCall(any())).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(response.code()).thenReturn(401);

        assertThrows(HttpConnectionException.class, () ->
                httpConnectionManager.fetchJSONResponse(expectedResponseCodes));
    }
}
