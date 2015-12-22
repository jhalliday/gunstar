package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.DetailedDate;
import gunstar.api.JsonPOJO;

public class OrderResponseFill extends JsonPOJO {

    public final int price;
    public final int qty;
    public final DetailedDate ts;

    public OrderResponseFill(
            @JsonProperty("price") int price,
            @JsonProperty("qty") int qty,
            @JsonProperty("ts") String ts
    ) {
        this.price = price;
        this.qty = qty;
        this.ts = parse(ts);
    }
}
