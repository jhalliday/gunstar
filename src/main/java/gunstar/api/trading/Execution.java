package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.BaseResponse;

import java.util.Date;

public class Execution extends BaseResponse {

    public final String account;
    public final String venue;
    public final String symbol;
    public final Order order;
    public final int standingId;
    public final int incomingId;
    public final int price;
    public final int filled;
    public final Date filledAt;
    public final boolean standingComplete;
    public final boolean incomingComplete;

    public Execution(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error,

            @JsonProperty("account") String account,
            @JsonProperty("venue") String venue,
            @JsonProperty("symbol") String symbol,
            @JsonProperty("order") Order order,
            @JsonProperty("standingId") int standingId,
            @JsonProperty("incomingId") int incomingId,
            @JsonProperty("price") int price,
            @JsonProperty("filled") int filled,
            @JsonProperty("filledAt") Date filledAt,
            @JsonProperty("standingComplete") boolean standingComplete,
            @JsonProperty("incomingComplete") boolean incomingComplete
    ) {
        super(ok, error);

        this.account = account;
        this.venue = venue;
        this.symbol = symbol;
        this.order = order;
        this.standingId = standingId;
        this.incomingId = incomingId;
        this.price = price;
        this.filled = filled;
        this.filledAt = filledAt;
        this.standingComplete = standingComplete;
        this.incomingComplete = incomingComplete;
    }
}
