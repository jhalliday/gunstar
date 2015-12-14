package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.JsonPOJO;

public class OrderBookRecord extends JsonPOJO {

    public final int price;
    public final int qty;
    public final boolean isBuy;

    public OrderBookRecord(
            @JsonProperty("price") int price,
            @JsonProperty("qty") int qty,
            @JsonProperty("isBuy") boolean isBuy
    ) {
        this.price = price;
        this.qty = qty;
        this.isBuy = isBuy;
    }
}
