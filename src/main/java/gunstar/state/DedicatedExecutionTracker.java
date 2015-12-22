package gunstar.state;

import gunstar.api.trading.Execution;
import gunstar.api.ws.EventConsumer;

public class DedicatedExecutionTracker implements EventConsumer<Execution> {

    public final String venue;
    public final String account;
    public final String stock;

    public final Position position;

    public DedicatedExecutionTracker(String venue, String account, String stock, Position position) {
        this.venue = venue;
        this.account = account;
        this.stock = stock;
        this.position = position;
    }

    @Override
    public void onEvent(Execution execution) {
        if(!execution.ok) {
            return;
        }

        if(!(execution.venue.equals(venue) && execution.account.equals(account) && execution.symbol.equals(stock))) {
            throw new IllegalArgumentException();
        }

        position.apply(execution);
    }

    public Position getPosition() {
        return position;
    }
}
