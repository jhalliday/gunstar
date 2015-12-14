package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.BaseResponse;

public class OrderStatusList extends BaseResponse {

    public final String venue;
    public final Order[] orders;

    public OrderStatusList(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error,

            @JsonProperty("venue") String venue,
            @JsonProperty("orders") Order[] orders
    ) {
        super(ok, error);

        this.venue = venue;
        this.orders = orders;
    }
}
