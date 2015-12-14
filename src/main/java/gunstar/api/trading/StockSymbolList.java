package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.BaseResponse;

public class StockSymbolList extends BaseResponse {

    public final StockSymbol[] symbols;

    public StockSymbolList(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error,

            @JsonProperty("symbols") StockSymbol[] symbols
    ) {
        super(ok, error);

        this.symbols = symbols;
    }
}
