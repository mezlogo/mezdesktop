package mezlogo.jknife.stomp;

import mezlogo.jknife.resubsciber.Resubscriber;
import mezlogo.jknife.websocketevent.WebsocketEvent;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class StompManager {
    private final Resubscriber<StompLabContext, WebsocketClosableAdapter> resubscriber;

    public StompManager(Resubscriber<StompLabContext, WebsocketClosableAdapter> resubscriber) {
        this.resubscriber = resubscriber;
    }

    public static StompManager lab(Collection<URI> uris, int count, int heartbeat, Consumer<WebsocketEvent<StompLabContext>> eventConsumer) {
        String connectFrame = StompUtils.createConnectFrame(heartbeat);
        Resubscriber<StompLabContext, WebsocketClosableAdapter> resubscriber = new Resubscriber<>(new StompConnectionFactory(eventConsumer, connectFrame));

        var contexts = new ArrayList<StompLabContext>();
        var counter = new AtomicInteger();

        for (URI uri : uris) {
            for (int i = 0; i < count; i++) {
                var id = counter.incrementAndGet();
                var name = "%s:%d_%d".formatted(uri.getHost(), uri.getPort(), id);
                var ctx = new StompLabContext(id, name, uri);
                contexts.add(ctx);
            }
        }

        resubscriber.init(contexts);
        return new StompManager(resubscriber);
    }

    public void ping() {
        for (WebsocketClosableAdapter adapter : resubscriber.values()) {
            WebSocket webSocket = adapter.webSocket();
            webSocket.sendText(StompUtils.PING, true);
        }
    }
}
