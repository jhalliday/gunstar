package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.BaseResponse;
import gunstar.api.DetailedDate;

public class OrderBookSummary extends BaseResponse {

    public final String venue;
    public final String symbol;

    public final OrderBookRecord[] bids;
    public final OrderBookRecord[] asks;

    public final DetailedDate ts;

    public OrderBookSummary(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error,

            @JsonProperty("venue") String venue,
            @JsonProperty("symbol") String symbol,
            @JsonProperty("bids") OrderBookRecord[] bids,
            @JsonProperty("asks") OrderBookRecord[] asks,
            @JsonProperty("ts") String ts
    ) {
        super(ok, error);

        this.venue = venue;
        this.symbol = symbol;
        this.bids = bids;
        this.asks = asks;
        this.ts = parse(ts);
    }
}
