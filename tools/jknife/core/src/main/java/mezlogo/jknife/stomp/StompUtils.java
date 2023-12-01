package mezlogo.jknife.stomp;

public interface StompUtils {
    String PING = "[\"\\n\"]";
    String PONG = "a[\"\\n\"]";
    int HEARTBEAT = 10_000;

    static String createConnectFrame(int heartbeat) {
        return "[\"CONNECT\\naccept-version:1.0,1.1,1.2\\nheart-beat:%d,%d\\n\\n\\u0000\"]".formatted(heartbeat, heartbeat);
    }
}
