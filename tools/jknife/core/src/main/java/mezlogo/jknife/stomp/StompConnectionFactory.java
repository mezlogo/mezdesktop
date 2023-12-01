package mezlogo.jknife.stomp;

import mezlogo.jknife.common.Pair;
import mezlogo.jknife.resubsciber.ResubscriberPair;
import mezlogo.jknife.websocketevent.WebsocketEvent;
import mezlogo.jknife.websocketevent.WebsocketOpeningEvent;
import mezlogo.jknife.websocketevent.WebsocketSendMessageEvent;

import java.net.ConnectException;
import java.net.http.HttpClient;
import java.net.http.WebSocketHandshakeException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class StompConnectionFactory implements Function<StompLabContext, CompletableFuture<ResubscriberPair<WebsocketClosableAdapter, StompLabContext>>> {
    private final Consumer<WebsocketEvent<StompLabContext>> eventConsumer;
    private final String connectFrame;

    public StompConnectionFactory(Consumer<WebsocketEvent<StompLabContext>> eventConsumer, String connectFrame) {
        this.eventConsumer = eventConsumer;
        this.connectFrame = connectFrame;
    }

    @Override
    public CompletableFuture<ResubscriberPair<WebsocketClosableAdapter, StompLabContext>> apply(StompLabContext ctx) {
        eventConsumer.accept(new WebsocketOpeningEvent<>(new Date(), ctx));
        var listener = new EventConsumerWebsocketListener<>(ctx, eventConsumer);
        listener.onOpen.thenAccept(ws -> {
            eventConsumer.accept(new WebsocketSendMessageEvent<>(new Date(), ctx, connectFrame));
            ws.first().sendText(connectFrame, true);
        });
        var onClose = listener.onClose.thenApply(Pair::second);

        return HttpClient.newHttpClient()
                .newWebSocketBuilder()
                .buildAsync(ctx.uri(), listener)
                .exceptionally(exc -> {
                    var original = exc.getCause();
                    if (original instanceof WebSocketHandshakeException handshakeException) {
                        System.out.println("handshake exception for: " + ctx);
                    } else if (original instanceof ConnectException connectException) {
                        System.out.println("connection error: " + connectException);
                    } else {
                        System.out.println("generic err: " + exc);
                    }
                    return null;
                })
                .thenApply(webSocket -> new ResubscriberPair<>(new WebsocketClosableAdapter(webSocket), onClose));
    }
}
