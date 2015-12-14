package gunstar.api.trading;

public class DedicatedTradingAPI {

    public final TradingAPI api;

    public final String venue;
    public final String stock;
    public final String account;

    public DedicatedTradingAPI(TradingAPI api, String venue, String stock, String account) {
        this.api = api;
        this.venue = venue;
        this.stock = stock;
        this.account = account;
    }

    public ServerHeartbeat heartbeat() {
        return api.heartbeat();
    }

    public VenueHeartbeat venueHeartbeat() {
        return api.venueHeartbeat(venue);
    }

    public StockSymbolList listStocks() {
        return api.listStocks(venue);
    }

    public OrderBookSummary getOrderBook() {
        return api.getOrderBook(venue, stock);
    }

    public Order placeOrder(OrderRequest orderRequest) {
        return api.placeOrder(orderRequest);
    }

    public OrderRequest request(int price, int qty, OrderDirection orderDirection, OrderType orderType) {
        return new OrderRequest(account, venue, stock, price, qty, orderDirection, orderType);
    }

    public Quote getQuote() {
        return api.getQuote(venue, stock);
    }

    public Order getOrderStatus(int id) {
        return api.getOrderStatus(venue, stock, id);
    }

    public Order getOrderStatus(Order previous) {
        return api.getOrderStatus(previous);
    }

    public Order cancelOrder(int id) {
        return api.cancelOrder(venue, stock, id);
    }

    public Order cancelOrder(Order order) {
        return api.cancelOrder(order);
    }

    public OrderStatusList getAllOrders() {
        return api.getOrders(venue, account);
    }

    public OrderStatusList getOrders() {
        return api.getOrders(venue, account, stock);
    }
}
