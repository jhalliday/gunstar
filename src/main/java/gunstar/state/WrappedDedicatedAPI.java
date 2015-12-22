package gunstar.state;

import gunstar.api.trading.*;

public class WrappedDedicatedAPI extends DedicatedTradingAPI {

    public final DedicatedExecutionTracker executionTracker;
    public final DedicatedQuoteTracker quoteTracker;

    public WrappedDedicatedAPI(TradingAPI api, String venue, String stock, String account,
                               DedicatedExecutionTracker executionTracker, DedicatedQuoteTracker quoteTracker) {
        super(api, venue, stock, account);
        this.executionTracker = executionTracker;
        this.quoteTracker = quoteTracker;
    }

    @Override
    public Order placeOrder(OrderRequest orderRequest) {
        return apply(super.placeOrder(orderRequest));
    }

    @Override
    public Quote getQuote() {
        Quote quote = super.getQuote();
        quoteTracker.onEvent(quote);
        return quote;
    }

    @Override
    public Order getOrderStatus(int id) {
        return apply(super.getOrderStatus(id));
    }

    @Override
    public Order getOrderStatus(Order previous) {
        return apply(super.getOrderStatus(previous));
    }

    @Override
    public Order cancelOrder(int id) {
        return apply(super.cancelOrder(id));
    }

    @Override
    public Order cancelOrder(Order order) {
        return apply(super.cancelOrder(order));
    }

    @Override
    public OrderStatusList getAllOrders() {
        return apply(super.getAllOrders());
    }

    @Override
    public OrderStatusList getOrders() {
        return apply(super.getOrders());
    }

    public Order apply(Order order) {
        if(order.ok) {
            Position position = executionTracker.getPosition();
            position.apply(order);
        }
        return order;
    }

    public OrderStatusList apply(OrderStatusList orderStatusList) {
        if(!orderStatusList.ok) {
            if(orderStatusList.orders != null && orderStatusList.orders.length > 0) {
                Order order = orderStatusList.orders[0];
                Position position = executionTracker.getPosition();
                for(Order o : orderStatusList.orders) {
                    // don't call apply(order) because the internal object don't have ok=true even is the wrapper does
                    position.apply(o);
                }
            }
        }

        return orderStatusList;
    }
}
