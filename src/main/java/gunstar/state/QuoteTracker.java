package gunstar.state;

import gunstar.api.DetailedDate;
import gunstar.api.trading.Order;
import gunstar.api.trading.Quote;
import gunstar.api.ws.EventConsumer;
import gunstar.api.ws.QuoteMessage;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class QuoteTracker implements EventConsumer<QuoteMessage> {

    // venue -> stock -> quote
    private final ConcurrentMap<String,ConcurrentMap<String,MarketHistory>> stocksByVenue = new ConcurrentHashMap<>();

    @Override
    public void onEvent(QuoteMessage quoteMessage) {
        Quote quote = quoteMessage.quote;
        if(quote == null || !quoteMessage.ok) {
            return;
        }
        onEvent(quote);
    }

    public void onEvent(Quote quote) {
        if(quote.quoteTime == null) {
            return;
        }

        ConcurrentMap<String,MarketHistory> stocksMap = stocksByVenue.get(quote.venue);
        if(stocksMap == null) {
            stocksByVenue.putIfAbsent(quote.venue, new ConcurrentHashMap<>());
            stocksMap = stocksByVenue.get(quote.venue);
        }

        MarketHistory marketHistory = stocksMap.get(quote.symbol);
        if(marketHistory == null) {
            stocksMap.putIfAbsent(quote.symbol, new MarketHistory(quote.venue, quote.symbol, 150, 2000));
            marketHistory = stocksMap.get(quote.symbol);
        }

        marketHistory.apply(quote);
    }

    public Quote getQuoteAfter(String venue, String stock, Order order) {
        return getQuoteAfter(venue, stock, order.ts);
    }

    public Quote getQuoteAfter(String venue, String stock, DetailedDate date) {
        MarketHistory marketHistory = getMarketHistory(venue, stock);
        return marketHistory.getQuoteAfter(date);
    }

    public Quote getQuote(String venue, String stock) {
        MarketHistory marketHistory = getMarketHistory(venue, stock);
        return marketHistory.latestQuote;
    }

    public MarketHistory getMarketHistory(String venue, String stock) {

        ConcurrentMap<String,MarketHistory> stocksMap = stocksByVenue.get(venue);
        if(stocksMap == null) {
            stocksByVenue.putIfAbsent(venue, new ConcurrentHashMap<>());
            stocksMap = stocksByVenue.get(venue);
        }

        MarketHistory marketHistory = stocksMap.get(stock);
        if(marketHistory == null) {
            stocksMap.putIfAbsent(stock, new MarketHistory(venue, stock, 150, 2000));
            marketHistory = stocksMap.get(stock);
        }

        return marketHistory;
    }
}



