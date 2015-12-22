package gunstar.api.gm;

import com.fasterxml.jackson.annotation.JsonProperty;
import gunstar.api.BaseResponse;

import java.util.Map;

public class LevelInfo extends BaseResponse {

    public final int instanceId;
    public final String account;
    public final Map<String, String> instructions;
    public final int secondsPerTradingDay;
    public final String[] tickers;
    public final String[] venues;
    public final Map<String,Long> balances;

    public LevelInfo(
            @JsonProperty("ok") boolean ok,
            @JsonProperty("error") String error,

            @JsonProperty("instanceId") int instanceId,
            @JsonProperty("account") String account,
            @JsonProperty("instructions") Map<String, String> instructions,
            @JsonProperty("secondsPerTradingDay") int secondsPerTradingDay,
            @JsonProperty("tickers") String[] tickers,
            @JsonProperty("venues") String[] venues,
            @JsonProperty("balances") Map<String,Long> balances
    ) {
        super(ok, error);

        this.instanceId = instanceId;
        this.account = account;
        this.instructions = instructions;
        this.secondsPerTradingDay = secondsPerTradingDay;
        this.tickers = tickers;
        this.venues = venues;
        this.balances = balances;
    }
}
