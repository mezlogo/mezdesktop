package mezlogo.jknife.websocketevent;

import java.util.Date;

public record WebsocketErrorEvent<CTX>(Date date, CTX ctx, Throwable throwable) implements WebsocketEvent<CTX> {
}
