package mezlogo.jknife.stomp;

import java.io.Closeable;
import java.io.IOException;
import java.net.http.WebSocket;

public record WebsocketClosableAdapter(WebSocket webSocket) implements Closeable {
    @Override
    public void close() throws IOException {
        System.out.printf("IDK what Im doint in websocket::close");
    }
}
