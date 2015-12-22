package gunstar.api.gm;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.JsonPOJO;

public class InstanceStatusFlash extends JsonPOJO {

    public final String warning;

    public InstanceStatusFlash(
            @JsonProperty("warning") String warning
    ) {
        this.warning = warning;
    }
}
