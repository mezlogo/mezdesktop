package mezlogo.jknife.websocketevent;

import java.util.Date;

public record WebsocketCloseEvent<CTX>(Date date, CTX ctx, int code, String reason) implements WebsocketEvent<CTX> {
}
