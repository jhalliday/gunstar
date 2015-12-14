package gunstar.api.ws;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.BaseResponse;
import gunstar.api.trading.Quote;

public class QuoteMessage extends BaseResponse {

    public final Quote quote;

    public QuoteMessage(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error,

            @JsonProperty("quote") Quote quote
    ) {
        super(ok, error);

        this.quote = quote;
    }
}
