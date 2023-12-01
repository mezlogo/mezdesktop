package mezlogo.jknife.stomp;

import mezlogo.jknife.common.Pair;
import mezlogo.jknife.websocketevent.*;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

public class EventConsumerWebsocketListener<T> implements WebSocket.Listener {
    public final CompletableFuture<Pair<WebSocket, T>> onOpen = new CompletableFuture<>();
    public final CompletableFuture<Pair<Integer, T>> onClose = new CompletableFuture<>();
    private final T context;

    private final Consumer<WebsocketEvent<T>> eventConsumer;

    public EventConsumerWebsocketListener(T context, Consumer<WebsocketEvent<T>> eventConsumer) {
        this.context = context;
        this.eventConsumer = eventConsumer;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        eventConsumer.accept(new WebsocketOpenEvent<>(new Date(), context));
        onOpen.complete(new Pair<>(webSocket, context));
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        eventConsumer.accept(new WebsocketTextMessageEvent<>(new Date(), context, data.toString()));
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        eventConsumer.accept(new WebsocketCloseEvent<>(new Date(), context, statusCode, reason));
        onClose.complete(new Pair<>(statusCode, context));
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

    @Override
    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
        eventConsumer.accept(new WebsocketBinaryMessageEvent<>(new Date(), context, data));
        return WebSocket.Listener.super.onBinary(webSocket, data, last);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        eventConsumer.accept(new WebsocketErrorEvent<>(new Date(), context, error));
    }
}
