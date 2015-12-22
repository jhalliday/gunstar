package gunstar.state;

import gunstar.api.DetailedDate;
import gunstar.api.trading.Order;
import gunstar.api.trading.Quote;
import gunstar.api.ws.EventConsumer;
import gunstar.api.ws.QuoteMessage;

import java.util.Date;

public class DedicatedQuoteTracker implements EventConsumer<QuoteMessage> {

    public final MarketHistory marketHistory;

    public DedicatedQuoteTracker(MarketHistory marketHistory) {
        this.marketHistory = marketHistory;
    }

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

        if(!(quote.venue.equals(marketHistory.venue) && quote.symbol.equals(marketHistory.stock))) {
            throw new IllegalArgumentException();
        }

        marketHistory.apply(quote);
    }

    public Quote getQuoteAfter(Order order) {
        return getQuoteAfter(order.ts);
    }

    public Quote getQuoteAfter(DetailedDate date) {
        MarketHistory marketHistory = getMarketHistory();
        return marketHistory.getQuoteAfter(date);
    }

    public Quote getQuote() {
        return marketHistory.latestQuote;
    }

    public MarketHistory getMarketHistory() {
        return marketHistory;
    }
}
