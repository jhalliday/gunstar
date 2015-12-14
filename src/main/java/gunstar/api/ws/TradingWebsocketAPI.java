package gunstar.api.ws;

import gunstar.GunstarContext;
import gunstar.api.BaseResponse;
import gunstar.api.trading.Execution;
import gunstar.api.trading.Quote;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Stockfighter Websocket API.
 */
public class TradingWebsocketAPI {

    private static final Logger apiLogger = LoggerFactory.getLogger(TradingWebsocketAPI.class);

    public static final String API_ROOT_URL = "wss://api.stockfighter.io/ob/api/ws/";

    private static final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();

    private final class QueueMessageFeedConsumer implements EventConsumer<QuoteMessage> {
        public final BlockingQueue<Quote> queue = new LinkedBlockingQueue<>();

        @Override
        public void onEvent(QuoteMessage quoteMessage) {
            if(quoteMessage.quote != null) {
                queue.add(quoteMessage.quote);
            }
        }
    }

    private final class ExecutionFeedConsumer implements EventConsumer<Execution> {
        public final BlockingQueue<Execution> queue = new LinkedBlockingQueue<>();

        @Override
        public void onEvent(Execution execution) {
            queue.add(execution);
        }
    }

    /**
     * @param account account
     * @param venue   venue
     * @return queue
     * @see <a href="https://starfighter.readme.io/docs/quotes-ticker-tape-websocket">Quotes (Ticker Tape) Websocket</a>
     */
    public BlockingQueue<Quote> quotes(String account, String venue) {
        QueueMessageFeedConsumer consumer = new QueueMessageFeedConsumer();
        quotes(account, venue, consumer);
        return consumer.queue;
    }

    public void quotes(String account, String venue, EventConsumer<QuoteMessage> consumer) {
        String url = API_ROOT_URL + account + "/venues/" + venue + "/tickertape";
        connect(url, QuoteMessage.class, consumer);
    }

    /**
     * @param account account
     * @param venue   venue
     * @param stock   stock
     * @return queue
     * @see <a href="https://starfighter.readme.io/docs/quotes-ticker-tape-websocket">Quotes (Ticker Tape) Websocket</a>
     */
    public BlockingQueue<Quote> quotes(String account, String venue, String stock) {
        QueueMessageFeedConsumer consumer = new QueueMessageFeedConsumer();
        quotes(account, venue, stock, consumer);
        return consumer.queue;
    }

    public void quotes(String account, String venue, String stock, EventConsumer<QuoteMessage> consumer) {
        String url = API_ROOT_URL + account + "/venues/" + venue + "/tickertape/stocks/" + stock;
        connect(url, QuoteMessage.class, consumer);
    }

    /**
     * @param account account
     * @param venue   venue
     * @return queue
     * @see <a href="https://starfighter.readme.io/docs/executions-fills-websocket">Executions (Fills) Websocket</a>
     */
    public BlockingQueue<Execution> executions(String account, String venue) {
        ExecutionFeedConsumer consumer = new ExecutionFeedConsumer();
        executions(account, venue, consumer);
        return consumer.queue;
    }

    public void executions(String account, String venue, EventConsumer<Execution> consumer) {
        String url = API_ROOT_URL + account + "/venues/" + venue + "/executions";
        connect(url, Execution.class, consumer);
    }

    /**
     * @param account account
     * @param venue   venue
     * @param stock   stock
     * @return queue
     * @see <a href="https://starfighter.readme.io/docs/executions-fills-websocket">Executions (Fills) Websocket</a>
     */
    public BlockingQueue<Execution> executions(String account, String venue, String stock) {
        ExecutionFeedConsumer consumer = new ExecutionFeedConsumer();
        executions(account, venue, stock, consumer);
        return consumer.queue;
    }

    public void executions(String account, String venue, String stock, EventConsumer<Execution> consumer) {
        String url = API_ROOT_URL + account + "/venues/" + venue + "/executions/stocks/" + stock;
        connect(url, Execution.class, consumer);
    }

    private <T extends BaseResponse> void connect(String url, Class<T> clazz, EventConsumer<T> consumer) {
        try {

            ClientManager.ReconnectHandler reconnectHandler = new ClientManager.ReconnectHandler() {
                @Override
                public boolean onDisconnect(CloseReason closeReason) {
                    return true;
                }
            };

            final ClientManager client = ClientManager.createClient();
            client.getProperties().put(ClientProperties.RECONNECT_HANDLER, reconnectHandler);

            client.connectToServer(new Endpoint() {
                @Override
                public void onOpen(Session session, EndpointConfig config) {
                    session.addMessageHandler(new MessageHandler.Whole<String>() {
                        @Override
                        public void onMessage(String message) {

                            T value;
                            try {
                                value = GunstarContext.objectMapper.readValue(message, clazz);
                                apiLogger.debug(" <<< "+message);
                                consumer.onEvent(value);
                            } catch(IOException e) {
                                apiLogger.warn("ignoring websocket message that can't be deserialized: "+message, e);
                            }
                        }
                    });
                }
            }, cec, new URI(url));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
