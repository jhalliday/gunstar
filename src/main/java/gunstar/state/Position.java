package gunstar.state;

import gunstar.api.trading.Execution;
import gunstar.api.trading.Order;
import gunstar.api.trading.OrderResponseFill;
import org.glassfish.grizzly.utils.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Position {

    public final Set<Integer> closedOrderIds = Collections.synchronizedSet(new HashSet<>());
    public final ConcurrentMap<Integer,Order> openOrders = new ConcurrentHashMap<>();

    public volatile int cash;
    public volatile int shares;
    public volatile int price;

    public final List<Pair<Integer,Integer>> values = new LinkedList<>();

    public void apply(Execution execution) {
        if (!execution.ok) {
            return;
        }

        apply(execution.order);
    }

    public void apply(Order order) {
        if(order.open) {
            openOrders.put(order.id, order);
            return;
        }

        synchronized (closedOrderIds) {
            if (closedOrderIds.contains(order.id)) {
                return;
            }
            closedOrderIds.add(order.id);
        }
        openOrders.remove(order.id);

        int qty = 0;
        int value = 0;

        if(order.fills != null && order.fills.length > 0) {
            for (OrderResponseFill fill : order.fills) {
                qty += fill.qty;
                value += fill.qty * fill.price;
                if(order.isBid()) {
                    values.add(new Pair<>(qty, fill.price));
                } else {
                    values.add(new Pair<>(-1*qty, fill.price));
                }
                price = fill.price;
            }
        }

        synchronized (this) {
            if (order.isBid()) {
                shares += qty;
                cash -= value;
            } else {
                shares -= qty;
                cash += value;
            }
        }
    }


    @Override
    public String toString() {
        return "Position{" +
                "cash=" + cash +
                ", shares=" + shares + "@ "+price+" = "+(shares*price)+" NAV = "+(cash+(price*shares))+
                '}';
    }
}
