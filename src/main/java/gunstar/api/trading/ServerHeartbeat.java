package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.BaseResponse;

public class ServerHeartbeat extends BaseResponse {

    public ServerHeartbeat(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error
    ) {
        super(ok, error);
    }
}
