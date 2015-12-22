package gunstar.state;

import gunstar.api.DetailedDate;
import gunstar.api.trading.Quote;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;

import java.util.Date;

public class MarketHistory {

    public final String venue;
    public final String stock;

    public final int windowSampleSize;
    public final int windowIntervalMillis;

    public volatile Quote latestQuote;

    public final DescriptiveStatistics bidStats;
    public final DescriptiveStatistics askStats;
    public final DescriptiveStatistics bidSizeStats;
    public final DescriptiveStatistics askSizeStats;
    public final DescriptiveStatistics bidDepthStats;
    public final DescriptiveStatistics askDepthStats;

    public final DescriptiveStatistics lastStats;
    public final DescriptiveStatistics lastSizeStats;


    public MarketHistory(String venue, String stock, int windowSampleSize, int windowIntervalMillis) {
        this.venue = venue;
        this.stock = stock;
        this.windowSampleSize = windowSampleSize;
        this.windowIntervalMillis = windowIntervalMillis;

        bidStats = new SynchronizedDescriptiveStatistics(windowSampleSize);
        askStats = new SynchronizedDescriptiveStatistics(windowSampleSize);
        bidSizeStats = new SynchronizedDescriptiveStatistics(windowSampleSize);
        askSizeStats = new SynchronizedDescriptiveStatistics(windowSampleSize);
        bidDepthStats = new SynchronizedDescriptiveStatistics(windowSampleSize);
        askDepthStats = new SynchronizedDescriptiveStatistics(windowSampleSize);

        lastStats = new SynchronizedDescriptiveStatistics(windowSampleSize);
        lastSizeStats = new SynchronizedDescriptiveStatistics(windowSampleSize);
    }

    public void apply(Quote quote) {
        if(quote == null || quote.quoteTime == null ||
                (latestQuote != null && quote.quoteTime.before(latestQuote.quoteTime))) {
            return;
        }

        // 20 secs is known to work ok

        long latestQuoteBucket = 0;
        if(latestQuote != null) {
            latestQuoteBucket = latestQuote.quoteTime.date.getTime()/windowIntervalMillis;
        }
        long quoteBucket = quote.quoteTime.date.getTime()/windowIntervalMillis;

        synchronized (this) {
            latestQuote = quote;
            notifyAll();
        }

        if(quoteBucket == latestQuoteBucket) {
            return;
        }

        bidStats.addValue(quote.bid);
        askStats.addValue(quote.ask);
        bidSizeStats.addValue(quote.bidSize);
        askSizeStats.addValue(quote.askSize);
        bidDepthStats.addValue(quote.bidDepth);
        askDepthStats.addValue(quote.askDepth);

        lastStats.addValue(quote.last);
        lastSizeStats.addValue(quote.lastSize);
    }

    public synchronized Quote getQuoteAfter(DetailedDate date) {
        try {
            while (latestQuote == null || latestQuote.quoteTime == null) {
                wait();
            }
            while(!latestQuote.quoteTime.after(date)) {
                wait();
            }

            return latestQuote;
        } catch(InterruptedException e) {
            return null;
        }
    }
}
