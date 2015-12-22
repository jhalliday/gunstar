package gunstar.state;

import gunstar.GunstarContext;
import gunstar.api.trading.*;

public class WrappedAPI extends TradingAPI {

    public final ExecutionTracker executionTracker;
    public final QuoteTracker quoteTracker;

    public WrappedAPI(GunstarContext context, ExecutionTracker executionTracker, QuoteTracker quoteTracker) {
        super(context);
        this.executionTracker = executionTracker;
        this.quoteTracker = quoteTracker;
    }

    @Override
    public Order placeOrder(OrderRequest orderRequest) {
        return apply(super.placeOrder(orderRequest));
    }

    @Override
    public Quote getQuote(String venue, String stock) {
        Quote quote = super.getQuote(venue, stock);
        quoteTracker.onEvent(quote);
        return quote;
    }

    @Override
    public Order getOrderStatus(String venue, String stock, int id) {
        return apply(super.getOrderStatus(venue, stock, id));
    }

    @Override
    public Order getOrderStatus(Order previous) {
        return apply(super.getOrderStatus(previous));
    }

    @Override
    public Order cancelOrder(String venue, String stock, int id) {
        return apply(super.cancelOrder(venue, stock, id));
    }

    @Override
    public Order cancelOrder(Order order) {
        return apply(super.cancelOrder(order));
    }

    @Override
    public OrderStatusList getOrders(String venue, String account) {
        return apply(super.getOrders(venue, account));
    }

    @Override
    public OrderStatusList getOrders(String venue, String account, String stock) {
        return apply(super.getOrders(venue, account, stock));
    }

    public Order apply(Order order) {
        if(order.ok) {
            Position position = executionTracker.getPosition(order.venue, order.account, order.symbol);
            position.apply(order);
        }
        return order;
    }

    public OrderStatusList apply(OrderStatusList orderStatusList) {
        if(!orderStatusList.ok) {
            if(orderStatusList.orders != null && orderStatusList.orders.length > 0) {
                Order order = orderStatusList.orders[0];
                Position position = executionTracker.getPosition(order.venue, order.account, order.symbol);
                for(Order o : orderStatusList.orders) {
                    // don't call apply(order) because the internal object don't have ok=true even is the wrapper does
                    position.apply(o);
                }
            }
        }

        return orderStatusList;
    }
}
