package gunstar.api.gm;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.JsonPOJO;

public class InstanceStatusDetails extends JsonPOJO {

    // usually one game day == 5 real seconds.
    public final int endOfTheWorldDay; // when we'll timeout
    public final int tradingDay; // game time elapsed

    public InstanceStatusDetails(
            @JsonProperty("endOfTheWorldDay") int endOfTheWorldDay,
            @JsonProperty("tradingDay") int tradingDay
    ) {
        this.endOfTheWorldDay = endOfTheWorldDay;
        this.tradingDay = tradingDay;
    }
}