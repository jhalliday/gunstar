package gunstar.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseResponse extends JsonPOJO {

    public final boolean ok;
    public final String error;

    public BaseResponse(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error
    ) {
        this.ok = ok;
        this.error = error;
    }
}
