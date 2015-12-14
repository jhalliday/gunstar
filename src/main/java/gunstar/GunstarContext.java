package gunstar;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import gunstar.api.CustomResponseErrorHandler;
import gunstar.api.HeaderRequestInterceptor;

import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Heavyweight utility object instances. Usually a singleton.
 */
public class GunstarContext {

    public static final String DEFAULT_GM_API_ROOT_URL = "https://api.stockfighter.io/gm";
    public static final String DEFAULT_TRADE_API_ROOT_URL = "https://api.stockfighter.io/ob/api";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public final String apiKey;
    public final String tradingRootUrl;
    public final String gmRootUrl;

    public final RestTemplate restTemplate;

    public GunstarContext(String apiKey) {
        this(apiKey, DEFAULT_TRADE_API_ROOT_URL, DEFAULT_GM_API_ROOT_URL);
    }

    public GunstarContext(String apiKey, String tradingRootUrl, String gmRootUrl) {
        this.apiKey = apiKey;
        this.tradingRootUrl = tradingRootUrl;
        this.gmRootUrl = gmRootUrl;

        restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor(apiKey));
        restTemplate.setInterceptors(interceptors);

        restTemplate.setErrorHandler(new CustomResponseErrorHandler());

        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                List<MediaType> mediaTypes = new LinkedList<>(converter.getSupportedMediaTypes());
                mediaTypes.add(MediaType.TEXT_PLAIN);
                MappingJackson2HttpMessageConverter jackson = (MappingJackson2HttpMessageConverter)converter;
                jackson.setObjectMapper(objectMapper);
                jackson.setSupportedMediaTypes(mediaTypes);
            }
        }
    }
}
