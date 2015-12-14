package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.BaseResponse;

import java.util.Date;

public class Quote extends BaseResponse {

    public final String symbol;
    public final String venue;

    public final int bid;
    public final int ask;
    public final int bidSize;
    public final int askSize;
    public final int bidDepth;
    public final int askDepth;

    public final int last;
    public final int lastSize;
    public final Date lastTrade;

    public final Date quoteTime;

    public Quote(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error,

            @JsonProperty("symbol") String symbol,
            @JsonProperty("venue") String venue,
            @JsonProperty("bid") int bid,
            @JsonProperty("ask") int ask,
            @JsonProperty("bidSize") int bidSize,
            @JsonProperty("askSize") int askSize,
            @JsonProperty("bidDepth") int bidDepth,
            @JsonProperty("askDepth") int askDepth,
            @JsonProperty("last") int last,
            @JsonProperty("lastSize") int lastSize,
            @JsonProperty("lastTrade") Date lastTrade,
            @JsonProperty("quoteTime") Date quoteTime
    ) {
        super(ok, error);

        this.symbol = symbol;
        this.venue = venue;
        this.bid = bid;
        this.ask = ask;
        this.bidSize = bidSize;
        this.askSize = askSize;
        this.bidDepth = bidDepth;
        this.askDepth = askDepth;
        this.last = last;
        this.lastSize = lastSize;
        this.lastTrade = lastTrade;
        this.quoteTime = quoteTime;
    }
}
