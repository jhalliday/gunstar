package gunstar.api.gm;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.BaseResponse;

public class InstanceStatus extends BaseResponse {

    public final int id;
    public final String state;
    public final boolean done;
    public final InstanceStatusDetails details;
    public final InstanceStatusFlash flash;

    public InstanceStatus(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error,

            @JsonProperty("id") int id,
            @JsonProperty("state") String state,
            @JsonProperty("done") boolean done,
            @JsonProperty("details") InstanceStatusDetails details,
            @JsonProperty("flash") InstanceStatusFlash flash
    ) {
        super(ok, error);

        this.id = id;
        this.state = state;
        this.done = done;
        this.details = details;
        this.flash = flash;
    }
}