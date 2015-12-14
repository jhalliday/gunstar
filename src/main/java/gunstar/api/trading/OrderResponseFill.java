package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.JsonPOJO;

import java.util.Date;

public class OrderResponseFill extends JsonPOJO {

    public final int price;
    public final int qty;
    public final Date ts;

    public OrderResponseFill(
            @JsonProperty("price") int price,
            @JsonProperty("qty") int qty,
            @JsonProperty("ts") Date ts
    ) {
        this.price = price;
        this.qty = qty;
        this.ts = ts;
    }
}
