package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.BaseResponse;
import gunstar.api.DetailedDate;

public class Order extends BaseResponse {

    public final String symbol;
    public final String venue;
    public final OrderDirection direction;
    public final int originalQty;
    public final int qty;
    public final int price;
    public final OrderType type;
    public final int id;
    public final String account;
    public final DetailedDate ts;
    public final OrderResponseFill[] fills;
    public final int totalFilled;
    public final boolean open;

    public Order(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error,

            @JsonProperty("symbol") String symbol,
            @JsonProperty("venue") String venue,
            @JsonProperty("direction") OrderDirection direction,
            @JsonProperty("originalQty") int originalQty,
            @JsonProperty("qty") int qty,
            @JsonProperty("price") int price,
            @JsonProperty("orderType") OrderType type,
            @JsonProperty("id") int id,
            @JsonProperty("account") String account,
            @JsonProperty("ts") String ts,
            @JsonProperty("fills") OrderResponseFill[] fills,
            @JsonProperty("totalFilled") int totalFilled,
            @JsonProperty("open") boolean open
    ) {
        super(ok, error);

        this.symbol = symbol;
        this.venue = venue;
        this.direction = direction;
        this.originalQty = originalQty;
        this.qty = qty;
        this.price = price;
        this.type = type;
        this.id = id;
        this.account = account;
        this.ts = parse(ts);
        this.fills = fills;
        this.totalFilled = totalFilled;
        this.open = open;
    }

    public boolean isBid() {
        return direction == OrderDirection.BUY;
    }

    public boolean isAsk() {
        return direction == OrderDirection.SELL;
    }
}
