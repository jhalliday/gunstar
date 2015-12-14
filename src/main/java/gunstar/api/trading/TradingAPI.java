package gunstar.api.trading;

import gunstar.GunstarContext;
import gunstar.api.AbstractAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stockfighter trading system REST API.
 */
public class TradingAPI extends AbstractAPI {

    private static final Logger logger = LoggerFactory.getLogger(TradingAPI.class);

    public TradingAPI(GunstarContext context) {
        super(context.restTemplate, context.tradingRootUrl, logger);
    }

    /**
     * @return ServerHeartbeat
     * @see <a href="https://starfighter.readme.io/docs/heartbeat">Check The API Is Up</a>
     */
    public ServerHeartbeat heartbeat() {
        return get(apiRootUrl + "/heartbeat", ServerHeartbeat.class);
    }

    /**
     * @param venue venue
     * @return VenueHeartbeat
     * @see <a href="https://starfighter.readme.io/docs/venue-healthcheck">Check A Venue Is Up</a>
     */
    public VenueHeartbeat venueHeartbeat(String venue) {
        return get(apiRootUrl + "/venues/" + venue + "/heartbeat", VenueHeartbeat.class);
    }

    /**
     * @param venue venue
     * @return StockSymbolList
     * @see <a href="https://starfighter.readme.io/docs/list-stocks-on-venue">Stocks on a Venue</a>
     */
    public StockSymbolList listStocks(String venue) {
        return get(apiRootUrl + "/venues/" + venue + "/stocks", StockSymbolList.class);
    }

    /**
     * @param venue venue
     * @param stock stock
     * @return OrderBook
     * @see <a href="https://starfighter.readme.io/docs/get-orderbook-for-stock">The Orderbook For A Stock</a>
     */
    public OrderBookSummary getOrderBook(String venue, String stock) {
        return get(apiRootUrl + "/venues/" + venue + "/stocks/" + stock, OrderBookSummary.class);
    }

    /**
     * @param orderRequest orderRequest
     * @return OrderStatus
     * @see <a href="https://starfighter.readme.io/docs/place-new-order">A New Order For A Stock</a>
     */
    public Order placeOrder(OrderRequest orderRequest) {
        String url = apiRootUrl + "/venues/" + orderRequest.venue + "/stocks/" + orderRequest.stock + "/orders";
        return post(url, orderRequest.toString(), Order.class);
    }

    /**
     * @param venue venue
     * @param stock stock
     * @return Quote
     * @see <a href="https://starfighter.readme.io/docs/a-quote-for-a-stock">A Quote For A Stock</a>
     */
    public Quote getQuote(String venue, String stock) {
        return get(apiRootUrl + "/venues/" + venue + "/stocks/" + stock + "/quote", Quote.class);
    }

    /**
     * @param venue venue
     * @param stock stock
     * @param id    id
     * @return OrderStatus
     * @see <a href="https://starfighter.readme.io/docs/status-for-an-existing-order">Status For An Existing Order</a>
     */
    public Order getOrderStatus(String venue, String stock, int id) {
        return get(apiRootUrl + "/venues/" + venue + "/stocks/" + stock + "/orders/" + id, Order.class);
    }

    public Order getOrderStatus(Order previous) {
        return getOrderStatus(previous.venue, previous.symbol, previous.id);
    }

    /**
     * @param venue venue
     * @param stock stock
     * @param id    id
     * @return OrderStatus
     * @see <a href="https://starfighter.readme.io/docs/cancel-an-order">Cancel An Order</a>
     */
    public Order cancelOrder(String venue, String stock, int id) {
        // RestTemplate does do delete, but doesn't expect a return value.
        return post(apiRootUrl + "/venues/" + venue + "/stocks/" + stock + "/orders/" + id + "/cancel", Order.class);
    }

    public Order cancelOrder(Order order) {
        return cancelOrder(order.venue, order.symbol, order.id);
    }

    /**
     * @param venue   venue
     * @param account account
     * @return OrderStatusList
     * @see <a href="https://starfighter.readme.io/docs/status-for-all-orders">Status For All Orders</a>
     */
    public OrderStatusList getOrders(String venue, String account) {
        return get(apiRootUrl + "/venues/" + venue + "/accounts/" + account + "/orders", OrderStatusList.class);
    }

    /**
     * @param venue   venue
     * @param account account
     * @param stock   stock
     * @return OrderStatusList
     * @see <a href="https://starfighter.readme.io/docs/status-for-all-orders-in-a-stock">Status For All Orders In A Stock</a>
     */
    public OrderStatusList getOrders(String venue, String account, String stock) {
        return get(apiRootUrl + "/venues/" + venue + "/accounts/" + account + "/stocks/" + stock + "/orders", OrderStatusList.class);
    }
}
