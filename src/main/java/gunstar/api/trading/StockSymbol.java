package gunstar.api.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.JsonPOJO;

public class StockSymbol extends JsonPOJO {

    public final String name;
    public final String symbol;

    public StockSymbol(
            @JsonProperty("name") String name,
            @JsonProperty("symbol") String symbol
    ) {
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockSymbol that = (StockSymbol) o;

        if (!name.equals(that.name)) return false;
        return symbol.equals(that.symbol);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + symbol.hashCode();
        return result;
    }
}
