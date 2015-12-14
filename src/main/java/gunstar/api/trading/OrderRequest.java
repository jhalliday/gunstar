package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.JsonPOJO;

public class OrderRequest extends JsonPOJO {

    public final String account;
    public final String venue;
    public final String stock;
    public final int price;
    public final int qty;
    public final OrderDirection direction;
    public final OrderType orderType;

    public OrderRequest(
            @JsonProperty("account") String account,
            @JsonProperty("venue") String venue,
            @JsonProperty("stock") String stock,
            @JsonProperty("price") int price,
            @JsonProperty("qty") int qty,
            @JsonProperty("direction") OrderDirection direction,
            @JsonProperty("orderType") OrderType orderType
    ) {
        this.account = account;
        this.venue = venue;
        this.stock = stock;
        this.price = price;
        this.qty = qty;
        this.direction = direction;
        this.orderType = orderType;
    }
}
