package gunstar.api;

import gunstar.GunstarContext;
import gunstar.api.trading.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TradingIT {

    public final GunstarContext gunstarContext = new GunstarContext("api_key_here");

    public final DedicatedTradingAPI api = new DedicatedTradingAPI(new TradingAPI(gunstarContext),
                    QAHelper.TEST_VENUE, QAHelper.TEST_STOCK, QAHelper.TEST_ACCOUNT);

    @Test
    public void serverIsUp() {
        ServerHeartbeat serverHeartbeat = api.heartbeat();
        QAHelper.assertOk(serverHeartbeat);
    }

    @Test
    public void venueIsUp() {
        VenueHeartbeat venueHeartbeat = api.venueHeartbeat();
        QAHelper.assertOk(venueHeartbeat);
    }

    @Test
    public void stockIsListed() {
        StockSymbolList stockSymbolList = api.listStocks();
        QAHelper.assertOk(stockSymbolList);
        StockSymbol[] expected = new StockSymbol[] {
                new StockSymbol("Foreign Owned Occluded Bridge Architecture Resources", QAHelper.TEST_STOCK)
        };
        assertArrayEquals(expected, stockSymbolList.symbols);
    }

    @Test
    public void canViewOrderBook() {
        OrderBookSummary orderBookSummary = api.getOrderBook();
        QAHelper.assertOk(orderBookSummary);
    }

    @Test
    public void canGetQuote() {
        Quote quote = api.getQuote();
        QAHelper.assertOk(quote);
    }

    @Test
    public void canGetAllOrders() {
        OrderStatusList orderStatusList = api.getAllOrders();
        QAHelper.assertOk(orderStatusList);
    }

    @Test
    public void canGetIndividualStockOrders() {
        OrderStatusList orderStatusList = api.getOrders();
        QAHelper.assertOk(orderStatusList);
    }

    @Test
    public void canPlaceOrder() {
        Order order = api.placeOrder(api.request(0, 1, OrderDirection.BUY, OrderType.MARKET));
        QAHelper.assertOk(order);
    }

    @Test
    public void orderLifecycle() {
        Order initialOrder = api.placeOrder(api.request(1000, 1, OrderDirection.BUY, OrderType.LIMIT));
        QAHelper.assertOk(initialOrder);
        Order refreshedOrder = api.getOrderStatus(initialOrder);
        QAHelper.assertOk(refreshedOrder);
        Order finalStatus = api.cancelOrder(refreshedOrder);
        QAHelper.assertOk(finalStatus);
    }
}
