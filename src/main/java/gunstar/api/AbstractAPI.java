package gunstar.api;

import org.slf4j.Logger;
import org.springframework.web.client.RestTemplate;

public abstract class AbstractAPI {

    protected final Logger logger;

    protected final RestTemplate restTemplate;
    protected final String apiRootUrl;


    protected AbstractAPI(RestTemplate restTemplate, String apiRootUrl, Logger logger) {
        this.logger = logger;
        this.restTemplate = restTemplate;
        this.apiRootUrl = apiRootUrl;
    }

    protected <T extends BaseResponse> T get(String url, Class<T> clazz) {
        if (logger != null && logger.isDebugEnabled()) {
            logger.debug(" >>> GET " + url);
        }

        T response = restTemplate.getForObject(url, clazz);

        if (logger != null && logger.isDebugEnabled()) {
            logger.debug(" <<< " + response);
        }

        return response;
    }

    protected <T extends BaseResponse> T post(String url, Class<T> clazz) {
        return post(url, null, clazz);
    }

    protected <T extends BaseResponse> T post(String url, String request, Class<T> clazz) {
        if (logger != null && logger.isDebugEnabled()) {
            logger.debug(" >>> POST " + url);
            if(request != null) {
                logger.debug(" >>> BODY " + request);
            }
        }

        T response = restTemplate.postForObject(url, request, clazz);

        if (logger != null && logger.isDebugEnabled()) {
            logger.debug(" <<< " + response);
        }

        return response;
    }
}
