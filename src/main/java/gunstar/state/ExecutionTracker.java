package gunstar.state;

import gunstar.api.trading.Execution;
import gunstar.api.ws.EventConsumer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ExecutionTracker implements EventConsumer<Execution> {

    // venue -> stock -> account -> position
    private final ConcurrentMap<String,ConcurrentMap<String,ConcurrentMap<String,Position>>> stocksByVenue = new ConcurrentHashMap<>();

    @Override
    public void onEvent(Execution execution) {
        if(!execution.ok) {
            return;
        }

        Position position = getPosition(execution.venue, execution.account, execution.symbol);
        position.apply(execution);
    }

    public Position getPosition(String venue, String account, String stock) {

        ConcurrentMap<String,ConcurrentMap<String,Position>> stocksMap = stocksByVenue.get(venue);
        if(stocksMap == null) {
            stocksByVenue.putIfAbsent(venue, new ConcurrentHashMap<>());
            stocksMap = stocksByVenue.get(venue);
        }

        ConcurrentMap<String,Position> accountsMap = stocksMap.get(stock);
        if(accountsMap == null) {
            stocksMap.putIfAbsent(stock, new ConcurrentHashMap<>());
            accountsMap = stocksMap.get(stock);
        }

        Position position = accountsMap.get(account);
        if(position == null) {
            accountsMap.putIfAbsent(account, new Position());
            position = accountsMap.get(account);
        }

        return position;
    }
}
