package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.BaseResponse;

public class VenueHeartbeat extends BaseResponse {

    public final String venue;

    public VenueHeartbeat(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error,

            @JsonProperty("venue") String venue
    ) {
        super(ok, error);

        this.venue = venue;
    }
}
