package mezlogo.jknife.websocketevent;

import java.nio.ByteBuffer;
import java.util.Date;

public record WebsocketBinaryMessageEvent<CTX>(Date date, CTX ctx, ByteBuffer data) implements WebsocketEvent<CTX> {
}
