package gunstar.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    private final String apiKey;

    public HeaderRequestInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders httpHeaders = request.getHeaders();
        httpHeaders.set("User-Agent", "Gunstar/1.0");
        if(apiKey != null) {
            httpHeaders.set("X-Starfighter-Authorization", apiKey);
        }

        if (httpHeaders.containsKey("Accept-Charset")) {
            httpHeaders.set("Accept-Charset", "UTF-8");
            httpHeaders.set("Content-Type", "UTF-8");
        }

        return execution.execute(request, body);
    }
}
